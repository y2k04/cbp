package com.y2k4.cbp.mixin;

import com.y2k4.cbp.CBP;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.RideableInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.entity.vehicle.AbstractChestBoatEntity;
import net.minecraft.entity.vehicle.VehicleInventory;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(AbstractChestBoatEntity.class)
public abstract class AbstractChestBoatMixin extends AbstractBoatEntity implements RideableInventory, VehicleInventory {
    public AbstractChestBoatMixin(EntityType<? extends AbstractChestBoatEntity> entityType, World world, Supplier<Item> supplier) {
        super(entityType, world, supplier);
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void interact(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ActionResult actionResult;
        if (CBP.mountKeyPressed)
            actionResult = super.interact(player, hand);
        else {
            actionResult = open(player);
            if (actionResult.isAccepted())
                emitGameEvent(GameEvent.CONTAINER_OPEN, player);
        }
        cir.setReturnValue(actionResult);
    }
}
