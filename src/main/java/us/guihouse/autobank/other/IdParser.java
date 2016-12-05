package us.guihouse.autobank.other;

/**
 * Created by guilherme on 05/12/16.
 */
public class IdParser {
    public static Long safeParse(String idString) {
        try {
            return Long.parseUnsignedLong(idString);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
