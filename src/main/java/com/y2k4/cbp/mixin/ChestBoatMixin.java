package com.y2k4.cbp.mixin;

import com.y2k4.cbp.CBP;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.entity.vehicle.VehicleInventory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoatEntity.class)
public abstract class ChestBoatMixin extends BoatEntity implements VehicleInventory {
    public ChestBoatMixin(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void interact(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ActionResult actionResult;
        if (CBP.mountKeyPressed && canAddPassenger(player))
            actionResult = super.interact(player, hand);
        else {
            actionResult = open(player);
            if (actionResult.isAccepted())
                emitGameEvent(GameEvent.CONTAINER_OPEN, player);
        }
        cir.setReturnValue(actionResult);
    }
}
