package com.github.appcreatorguy.mods.modecorations;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModecorationsMain implements ModInitializer {
    public static final String MODECORATIONS = "modecorations";
    public static final Logger LOGGER = LogManager.getLogger("Modecorations Logger");

    public static Identifier id(String path){
        return new Identifier(MODECORATIONS, path);
    }

    @Override
    public void onInitialize() {

    }
}
