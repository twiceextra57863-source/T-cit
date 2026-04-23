package com.twicefear.minetwice;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CITRevamped implements ModInitializer {
    public static final String MOD_ID = "cit-revamped";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // This mod only runs on the client; common init can be empty.
    }
}
