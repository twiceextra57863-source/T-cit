package com.twicefear.minetwice.mixin;

import com.twicefear.minetwice.cit.CITManager;
import com.twicefear.minetwice.cit.CITRule;
import com.twicefear.minetwice.config.CITRevampedConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"), argsOnly = true)
    private BakedModel replaceModel(BakedModel original, ItemStack stack) {
        if (!CITRevampedConfig.isEnabled() || stack == null || stack.isEmpty()) return original;

        CITRule rule = CITManager.getMatchingRule(stack);
        if (rule != null && rule.getModel() != null) {
            BakedModel customModel = MinecraftClient.getInstance().getBakedModelManager().getModel(rule.getModel());
            if (customModel != null) {
                return customModel;
            }
        }
        return original;
    }
}
