package com.matyrobbrt.mobcapturingtool.item;

import com.matyrobbrt.mobcapturingtool.reg.RegistrationProvider;
import com.matyrobbrt.mobcapturingtool.reg.registries.DatapackRegistry;
import com.matyrobbrt.mobcapturingtool.reg.registries.RegistryFeatureType;
import com.matyrobbrt.mobcapturingtool.util.Constants;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class CapturingPredicates {
    public static final CapturingPredicate CHECK_TAG = new CapturingPredicate() {
        @Override
        public boolean canPickup(ItemStack stack, LivingEntity target, @Nullable Player player) {
            return !target.getType().is(Constants.BLACKLISTED_TAG);
        }

        @Override
        public MapCodec<? extends CapturingPredicate> getCodec() {
            return CHECK_TAG_CODEC;
        }
    };
    public static final MapCodec<? extends CapturingPredicate> CHECK_TAG_CODEC = MapCodec.unit(() -> CHECK_TAG);

    public static final ResourceKey<Registry<MapCodec<? extends CapturingPredicate>>> PREDICATE_TYPES = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "capturing_predicate_type"));
    private static final RegistrationProvider<MapCodec<? extends CapturingPredicate>> PREDICATE_TYPE_PROVIDER = RegistrationProvider.get(PREDICATE_TYPES, Constants.MOD_ID);
    public static final Registry<MapCodec<? extends CapturingPredicate>> PREDICATE_TYPE_REGISTRY = PREDICATE_TYPE_PROVIDER
            .registryBuilder()
            .withFeature(RegistryFeatureType.SYNCED)
            .withDefaultValue("check_tag", () -> CHECK_TAG_CODEC)
            .build();

    public static final Codec<CapturingPredicate> DIRECT_CODEC = PREDICATE_TYPE_REGISTRY.byNameCodec()
            .dispatch(CapturingPredicate::getCodec, Function.identity());

    public static final DatapackRegistry<CapturingPredicate> PREDICATES = DatapackRegistry.<CapturingPredicate>builder(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "capturing_predicate"))
            .withElementCodec(DIRECT_CODEC).withNetworkCodec(DIRECT_CODEC).build();

    static {
        register("true", (stack, livingEntity, player) -> true);
        register("false", (stack, livingEntity, player) -> false);

        record Not(CapturingPredicate predicate) implements CapturingPredicate {
            static final MapCodec<Not> CODEC = RecordCodecBuilder.mapCodec(in -> in.group(
                    DIRECT_CODEC.fieldOf("predicate").forGetter(Not::predicate)
            ).apply(in, Not::new));

            @Override
            public boolean canPickup(ItemStack stack, LivingEntity target, @Nullable Player player) {
                return !Not.this.predicate.canPickup(stack, target, player);
            }

            @Override
            public MapCodec<? extends CapturingPredicate> getCodec() {
                return CODEC;
            }
        }
        PREDICATE_TYPE_PROVIDER.register("not", () -> Not.CODEC);
    }

    public static void loadClass() {}

    private static void register(String name, TriPredicate<ItemStack, LivingEntity, Player> check) {
        final AtomicReference<MapCodec<? extends CapturingPredicate>> codec = new AtomicReference<>();
        codec.set(MapCodec.unit(() -> new CapturingPredicate() {
            @Override
            public boolean canPickup(ItemStack stack, LivingEntity target, @Nullable Player player) {
                return check.test(stack, target, player);
            }

            @Override
            public MapCodec<? extends CapturingPredicate> getCodec() {
                return codec.get();
            }
        }));
        PREDICATE_TYPE_PROVIDER.register(name, codec::get);
    }

    private interface TriPredicate<A, B, C> {
        boolean test(A a, B b, C c);
    }
}
