package org.reverence.jcommander.settings;

import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class I18n {
    private static final Logger log = Logger.getLogger(I18n.class.getName());
    private static final UResourceBundle i18nResource;
    static {
        i18nResource = UResourceBundle.getBundleInstance("language/i18n", ULocale.getDefault());
    }
    public static String getText(String... args){
        if(args == null || args.length == 0){
            throw new IllegalArgumentException(I18n.getText("error.parameter.type", "null"));
        }
        if(args.length == 1){
            return i18nResource.getString(args[0]);
        }
        String[] messages = new String[args.length];
        for (int i = args.length - 1; i > -1; i--) {
            messages[i] = i18nResource.getString(args[i]);
            int fieldCount = Pattern.compile("[^\\{]").matcher(messages[i]).replaceAll("").length();
            if(fieldCount > 0){
                String pattern = messages[i];
                MessageFormat msgFmt = new MessageFormat(pattern);
                messages[i] = msgFmt.format(Arrays.copyOfRange(args, i, i + fieldCount)); // replace with formatted string
                for (int j = 1; j < fieldCount; j++) {
                    messages[i + j] = ""; // remove the passed parameters
                }
            }
        }
        String out = "";
        for (int i = 0; i < messages.length; i++) {
            if (!messages[i].isBlank()) {
                out = i == 0 ? messages[i] : " " + messages[i];
            }
        }
        return out;
    }
}
