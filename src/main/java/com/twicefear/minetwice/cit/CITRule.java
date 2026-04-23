package com.twicefear.minetwice.cit;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

import java.util.Properties;
import java.util.regex.Pattern;

public class CITRule {
    private final Identifier targetItem;
    private final Identifier texture;
    private final Identifier model;
    private final Pattern namePattern;
    private final String icon;
    private final int weight;

    public CITRule(Properties props, Identifier sourceFile) {
        // Required: item
        String itemStr = props.getProperty("item");
        if (itemStr == null) {
            throw new IllegalArgumentException("Missing 'item' property in " + sourceFile);
        }
        this.targetItem = Identifier.tryParse(itemStr);
        if (this.targetItem == null) {
            throw new IllegalArgumentException("Invalid 'item' identifier: " + itemStr);
        }

        // Texture: can be absolute (namespace:path) or relative. We'll assume absolute.
        String texProp = props.getProperty("texture");
        this.texture = texProp != null ? Identifier.tryParse(texProp) : null;

        // Model
        String modelProp = props.getProperty("model");
        this.model = modelProp != null ? Identifier.tryParse(modelProp) : null;

        // NBT matching: nbt.display.Name (regex pattern)
        String nameRegex = props.getProperty("nbt.display.Name");
        this.namePattern = nameRegex != null ? Pattern.compile(nameRegex) : null;

        // Icon character (e.g., \uE001)
        this.icon = props.getProperty("icon", "");

        // Weight (for conflict resolution)
        try {
            this.weight = Integer.parseInt(props.getProperty("weight", "10"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid weight in " + sourceFile);
        }
    }

    public boolean matches(ItemStack stack) {
        // Check NBT display name pattern
        if (namePattern != null) {
            NbtCompound display = stack.getSubNbt("display");
            if (display == null || !display.contains("Name", NbtElement.STRING_TYPE)) {
                return false;
            }
            String itemName = display.getString("Name");
            if (!namePattern.matcher(itemName).matches()) {
                return false;
            }
        }
        // Add more condition checks if needed (custom_model_data, etc.)
        return true;
    }

    public Identifier getTargetItem() { return targetItem; }
    public Identifier getTexture() { return texture; }
    public Identifier getModel() { return model; }
    public String getIcon() { return icon; }
    public int getWeight() { return weight; }
}
