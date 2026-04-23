package com.twicefear.minetwice.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new CITConfigScreen(parent);
    }

    private static class CITConfigScreen extends Screen {
        private final Screen parent;

        protected CITConfigScreen(Screen parent) {
            super(Text.literal("CIT Revamped Configuration"));
            this.parent = parent;
        }

        @Override
        protected void init() {
            int buttonWidth = 150;
            int centerX = this.width / 2 - buttonWidth / 2;
            int y = 40;

            // Enabled toggle
            this.addDrawableChild(CyclingButtonWidget.onOffBuilder(CITRevampedConfig.isEnabled())
                    .build(centerX, y, buttonWidth, 20, Text.literal("Mod Enabled"), (button, value) -> CITRevampedConfig.setEnabled(value)));
            
            // Logging toggle
            y += 25;
            this.addDrawableChild(CyclingButtonWidget.onOffBuilder(CITRevampedConfig.shouldLogCITLoading())
                    .build(centerX, y, buttonWidth, 20, Text.literal("Log CIT Loading"), (button, value) -> CITRevampedConfig.setLogCITLoading(value)));

            // Icons toggle
            y += 25;
            this.addDrawableChild(CyclingButtonWidget.onOffBuilder(CITRevampedConfig.areIconsEnabled())
                    .build(centerX, y, buttonWidth, 20, Text.literal("Enable Unicode Icons"), (button, value) -> CITRevampedConfig.setEnableIcons(value)));

            // Done button
            y += 35;
            this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), btn -> this.close()).dimensions(centerX, y, buttonWidth, 20).build());
        }

        @Override
        public void close() {
            if (this.client != null) {
                this.client.setScreen(parent);
            }
        }
    }
}
