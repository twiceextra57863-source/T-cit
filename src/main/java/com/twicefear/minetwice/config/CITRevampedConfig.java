package com.twicefear.minetwice.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class CITRevampedConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("cit-revamped.json");
    private static ConfigData data = new ConfigData();

    public static class ConfigData {
        public boolean enabled = true;
        public boolean logCITLoading = false;
        public boolean enableIcons = true;
    }

    public static void load() {
        if (CONFIG_PATH.toFile().exists()) {
            try (Reader reader = new FileReader(CONFIG_PATH.toFile())) {
                data = GSON.fromJson(reader, ConfigData.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            save();
        }
    }

    public static void save() {
        try (Writer writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isEnabled() { return data.enabled; }
    public static boolean shouldLogCITLoading() { return data.logCITLoading; }
    public static boolean areIconsEnabled() { return data.enableIcons; }

    public static void setEnabled(boolean enabled) {
        data.enabled = enabled;
        save();
    }

    public static void setLogCITLoading(boolean log) {
        data.logCITLoading = log;
        save();
    }

    public static void setEnableIcons(boolean enable) {
        data.enableIcons = enable;
        save();
    }
}
