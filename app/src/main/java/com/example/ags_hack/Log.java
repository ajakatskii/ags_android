package com.example.ags_hack;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

    public static void Debug(String msg) {
        Logger.getLogger("APP_NAME").log(Level.INFO, msg);
    }

}
