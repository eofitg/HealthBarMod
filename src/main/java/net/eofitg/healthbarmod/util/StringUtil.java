package net.eofitg.healthbarmod.util;

public class StringUtil {
    /**
     * Returns the last color code character
     * <p>
     * Returns '\0' if it can't find any color code
     * <p>
     * Returns a single character
     */
    public static char getLastColorCharOf(String text) {
        if (text == null || text.isEmpty()) return '\0';
        for (int i = text.length() - 2; i >= 0; i--) { // -2 保证 i+1 不越界
            if (text.charAt(i) == '§') {
                char code = Character.toLowerCase(text.charAt(i + 1));
                if ((code >= '0' && code <= '9') || (code >= 'a' && code <= 'f')) {
                    return code;
                }
            }
        }
        return '\0';
    }
}
