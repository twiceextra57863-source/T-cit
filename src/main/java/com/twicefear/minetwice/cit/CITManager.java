package com.twicefear.minetwice.cit;

import com.twicefear.minetwice.CITRevamped;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class CITManager {
    private static final Identifier CIT_FOLDER = new Identifier("citrevamped", "cit");
    private static final Map<Identifier, List<CITRule>> RULES = new HashMap<>();

    public static void reload(ResourceManager manager) {
        RULES.clear();
        // Find all .cit files under assets/*/citrevamped/cit
        Map<Identifier, Resource> resources = manager.findResources("cit", path -> path.getPath().endsWith(".cit"));
        if (resources.isEmpty()) {
            CITRevamped.LOGGER.warn("No .cit files found.");
            return;
        }

        for (Identifier fileId : resources.keySet()) {
            try (InputStream is = manager.getResource(fileId).get().getInputStream()) {
                Properties props = new Properties();
                props.load(is);
                CITRule rule = new CITRule(props, fileId);
                // Group by target item identifier
                Identifier itemId = rule.getTargetItem();
                RULES.computeIfAbsent(itemId, k -> new ArrayList<>()).add(rule);
                if (com.twicefear.minetwice.config.CITRevampedConfig.shouldLogCITLoading()) {
                    CITRevamped.LOGGER.info("Loaded CIT rule: {} -> {}", fileId, itemId);
                }
            } catch (Exception e) {
                CITRevamped.LOGGER.error("Failed to load CIT from {}: {}", fileId, e.getMessage());
            }
        }

        // Sort each list by weight (higher first)
        RULES.values().forEach(list -> list.sort(Comparator.comparingInt(CITRule::getWeight).reversed()));
        CITRevamped.LOGGER.info("Loaded {} CIT rules for {} items.", RULES.values().stream().mapToInt(List::size).sum(), RULES.size());
    }

    @Nullable
    public static CITRule getMatchingRule(ItemStack stack) {
        if (stack.isEmpty()) return null;
        Identifier itemId = stack.getItem().getRegistryEntry().getKey().get().getValue();
        List<CITRule> rules = RULES.get(itemId);
        if (rules == null) return null;

        for (CITRule rule : rules) {
            if (rule.matches(stack)) {
                return rule;
            }
        }
        return null;
    }

    public static void clear() {
        RULES.clear();
    }
}
