package Engine;

import java.util.logging.Logger;

public class LogManager {
    public static Logger getLogger(Class className) {
        return java.util.logging.LogManager.getLogManager().getLogger(String.valueOf(className));
    }
}
