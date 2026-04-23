package com.twicefear.minetwice.cit;

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class CITReloadListener implements SimpleSynchronousResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return new Identifier("citrevamped", "cit_reload");
    }

    @Override
    public void reload(ResourceManager manager) {
    }
}