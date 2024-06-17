package com.matyrobbrt.mobcapturingtool.item;

import com.matyrobbrt.mobcapturingtool.reg.RegistrationProvider;
import com.matyrobbrt.mobcapturingtool.reg.RegistryObject;
import com.matyrobbrt.mobcapturingtool.util.Constants;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class MCTItems {
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(BuiltInRegistries.ITEM, Constants.MOD_ID);
    public static final RegistrationProvider<DataComponentType<?>> DATA_COMPONENTS = RegistrationProvider.get(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.MOD_ID);
    public static final RegistryObject<Item, CapturingToolItem> CAPTURING_TOOL = ITEMS.register("mob_capturing_tool",
            () -> new CapturingToolItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<DataComponentType<?>, DataComponentType<ResourceLocation>> CAPTURED_ENTITY_TYPE = DATA_COMPONENTS
            .register("captured_entity_type", () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC).build());
    public static final RegistryObject<DataComponentType<?>, DataComponentType<CompoundTag>> CAPTURED_ENTITY = DATA_COMPONENTS
            .register("captured_entity", () -> DataComponentType.<CompoundTag>builder().persistent(CompoundTag.CODEC).networkSynchronized(ByteBufCodecs.COMPOUND_TAG)
                    .build());

    public static void loadClass() {}
}
