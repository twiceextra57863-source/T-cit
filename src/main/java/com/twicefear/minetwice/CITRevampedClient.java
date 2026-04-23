package com.twicefear.minetwice;

import com.twicefear.minetwice.cit.CITReloadListener;
import com.twicefear.minetwice.config.CITRevampedConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class CITRevampedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CITRevampedConfig.load();
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new CITReloadListener());
    }
}
