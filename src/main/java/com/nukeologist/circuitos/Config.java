package com.nukeologist.circuitos;

import com.nukeologist.circuitos.proxy.CommonProxy;
import com.nukeologist.circuitos.utility.LogHelper;
import net.minecraftforge.common.config.Configuration;

public class Config {

    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_RESISTOR = "resistor";
    private static final String CATEGORY_GENERATOR = "generator";

    public static boolean debug = true;
    public static String testString = "ye tester";

    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
            initGeneratorConfig(cfg);
            initResistorConfig(cfg);

        }catch(Exception e) {
            LogHelper.logWarn("Problem while loading config!!", e);

        }finally {
            if(cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration conf) {

        conf.addCustomCategoryComment(CATEGORY_GENERAL, "General config");
        debug = conf.getBoolean("debug", CATEGORY_GENERAL, debug, "If debug is on, then more stuff to console...");
        testString = conf.getString("testString", CATEGORY_GENERAL, testString, "Just another test string");

    }

    private static void initResistorConfig(Configuration conf) {

    }

    private static void initGeneratorConfig(Configuration conf) {

    }
}
