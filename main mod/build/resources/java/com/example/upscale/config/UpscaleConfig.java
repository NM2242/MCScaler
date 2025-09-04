package com.example.upscale.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import net.minecraft.client.MinecraftClient;

public class UpscaleConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static boolean debug = true;
    public static boolean sharpen = true;
    public static float scale = 0.67f; // 0.5 .. 1.0

    private static File getFile() {
        File dir = new File(MinecraftClient.getInstance().runDirectory, "config");
        if (!dir.exists()) dir.mkdirs();
        return new File(dir, "upscale.json");
    }

    public static void load() {
        try {
            File f = getFile();
            if (!f.exists()) return;
            try (Reader r = new InputStreamReader(new FileInputStream(f))) {
                UpscaleConfig loaded = GSON.fromJson(r, UpscaleConfig.class);
                if (loaded != null) {
                    debug = loaded.debug;
                    sharpen = loaded.sharpen;
                    scale = loaded.scale;
                }
            }
        } catch (Exception ignored) {}
    }

    public static void save() {
        try {
            File f = getFile();
            try (Writer w = new OutputStreamWriter(new FileOutputStream(f))) {
                GSON.toJson(new UpscaleConfig(), w);
            }
        } catch (Exception ignored) {}
    }
}