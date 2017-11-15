package tech.ericntd.githubsearch.utils;

import android.support.annotation.Nullable;

public class StringUtils {
    public static boolean isEmpty(@Nullable String string) {
        return string == null || string.length() == 0;
    }
}
