package zone.bears.chickenphysics.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(ChickenEntity.class)
public class ChickenPhysicsMixin {
	@Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = 
			"Lnet/minecraft/entity/passive/ChickenEntity;dropItem(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;"))
	private ItemEntity ChickenDropItemReplacement(ChickenEntity entity, ItemConvertible stack) {
		if(entity.hasCustomName() && 
				( entity.getCustomName().getString().equals("Dinnerbone") ||
						entity.getCustomName().getString().equals("Grumm")	))
		{
			EggEntity ee = new EggEntity(entity.getWorld(), entity);
			ee.setVelocity(entity, -90f, entity.getYaw(), 0.0F, 1.5F, 1.0F);
			ee.setItem(new ItemStack(Items.EGG));
			entity.getWorld().spawnEntity(ee);
			return null;
		}
		
		return entity.dropItem(stack);
	}
}