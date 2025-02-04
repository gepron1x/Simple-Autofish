package net.genesis.autofish.mixin;


import net.genesis.autofish.Autofish;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(FishingBobberEntity.class)
public abstract class AutofishMixin {
	@Shadow
	private boolean caughtFish;


	@Shadow
	public abstract PlayerEntity getPlayerOwner();



	@Inject(at = {@At("TAIL")}, method = {"onTrackedDataSet"})
	public void onTrackedDataSet(TrackedData<?> data, CallbackInfo ci) throws InterruptedException {
		if (Autofish.vkeyBinding.wasPressed()) {
			Autofish.on = !Autofish.on;

		}
		MinecraftClient client = MinecraftClient.getInstance();

		if (caughtFish && getPlayerOwner() instanceof ClientPlayerEntity) {
			if (!Autofish.on){
				client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
				Thread.sleep(5L);
				client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
			}

		}
	}
}
