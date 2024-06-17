package com.matyrobbrt.mobcapturingtool.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface CapturingPredicate {
    boolean canPickup(ItemStack stack, LivingEntity target, @Nullable Player player);

    MapCodec<? extends CapturingPredicate> getCodec();
}
