package com.nukeologist.circuitos.utility;

import com.nukeologist.circuitos.reference.Reference;

import org.apache.logging.log4j.*;

public class LogHelper {

    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(Reference.MOD_ID);

    private static final String mod = "[" + Reference.NAME + "] ";

    public static void log(Level level, String msg)
    {
        LOGGER.log(level, mod + msg);
    }

    public static void logInfo(String msg)
    {
        LOGGER.info(mod+ msg);
    }

    public static void logWarn(String msg)
    {
        LOGGER.warn(mod + msg);
    }

    public static void logFatal(String msg)
    {
        LOGGER.fatal(mod + msg);
    }

    public static void logDebug(String msg) {

        LOGGER.debug(mod + msg);
    }

    public static void logInfo(String msg, Object... args)
    {
        LOGGER.info(String.format(mod + msg, args));
    }

    public static void logWarn(String msg, Object... args)
    {
        LOGGER.warn(String.format(mod + msg, args));
    }

    public static void logFatal(String msg, Object... args)
    {
        LOGGER.fatal(String.format(mod + msg, args));
    }
}
