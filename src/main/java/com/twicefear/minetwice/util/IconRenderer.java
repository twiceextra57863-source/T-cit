package com.twicefear.minetwice.util;

import com.twicefear.minetwice.cit.CITManager;
import com.twicefear.minetwice.cit.CITRule;
import com.twicefear.minetwice.config.CITRevampedConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class IconRenderer {
    private static final Identifier ICON_FONT = new Identifier("minetwice", "icons");

    public static Text getIconText(ItemStack stack) {
        if (!CITRevampedConfig.areIconsEnabled() || stack.isEmpty()) return Text.empty();

        CITRule rule = CITManager.getMatchingRule(stack);
        if (rule != null && !rule.getIcon().isEmpty()) {
            // Return the literal icon character with our custom font style.
            // The font path is assets/minetwice/font/icons.json (see below)
            return Text.literal(rule.getIcon()).setStyle(Style.EMPTY.withFont(ICON_FONT));
        }
        return Text.empty();
    }
}
