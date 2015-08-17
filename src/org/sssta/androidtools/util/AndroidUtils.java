package org.sssta.androidtools.util;

import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created by cauchywei on 15/8/17.
 */
public class AndroidUtils {

    public static String viewIdToVarName(@NotNull String id, boolean isFieldName){
        return viewIdToVarName("",id,isFieldName);
    }

    public static String viewIdToVarName(@NotNull String xmlName, @NotNull String id,boolean isFieldName){
        String name = "";

        StringUtil.getWordsIn(id);

        return name;
    }

}
