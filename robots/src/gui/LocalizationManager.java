package gui;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationManager {
    private static ResourceBundle bundle;
    private static ResourceBundle translitBundle;

    static {
        setLocale(new Locale("ru"));
        translitBundle = ResourceBundle.getBundle("messages_translit");
    }

    public static void setLocale(Locale locale) {
        if ("ru".equals(locale.getLanguage()) && "translit".equals(locale.getVariant())) {
            bundle = translitBundle;
        } else {
            bundle = ResourceBundle.getBundle("messages", locale);
        }
    }

    public static String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            System.err.println("Localization key not found: " + key);
            return key;
        }
    }
}