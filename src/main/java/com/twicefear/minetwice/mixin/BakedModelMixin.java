package com.twicefear.minetwice.mixin;

import com.twicefear.minetwice.cit.CITManager;
import com.twicefear.minetwice.cit.CITRule;
import com.twicefear.minetwice.config.CITRevampedConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BakedModel.class)
public interface BakedModelMixin {
    // This method is called to get the particle sprite; we can override it per stack.
    // Note: This is a workaround; full texture replacement would require custom UnbakedModel baking.
    // For now, it's a demonstration of the concept.
    @Inject(method = "getParticleSprite(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/texture/Sprite;", at = @At("HEAD"), cancellable = true)
    default void injectParticleSprite(ItemStack stack, CallbackInfoReturnable<Sprite> cir) {
        if (!CITRevampedConfig.isEnabled() || stack == null || stack.isEmpty()) return;

        CITRule rule = CITManager.getMatchingRule(stack);
        if (rule != null && rule.getTexture() != null) {
            Sprite sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).apply(rule.getTexture());
            if (sprite != null) {
                cir.setReturnValue(sprite);
            }
        }
    }
}
