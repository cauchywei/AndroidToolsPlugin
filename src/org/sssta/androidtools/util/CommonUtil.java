package org.sssta.androidtools.util;

import com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.InheritanceUtil;
import com.intellij.refactoring.util.CommonRefactoringUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cauchywei on 15/8/16.
 */
public class CommonUtil {

    public static void showErrorHint(Project project, Editor editor) {
        CommonRefactoringUtil.showErrorHint(project, editor, "Can't perform postfix completion", "Can't perform postfix completion", "");
    }

    public static void createSimpleStatement(@NotNull PsiElement context, @NotNull Editor editor, @NotNull String text) {
        PsiExpression expr = JavaPostfixTemplatesUtils.getTopmostExpression(context);
        PsiElement parent = expr != null ? expr.getParent() : null;
        assert parent instanceof PsiStatement;
        PsiElementFactory factory = JavaPsiFacade.getInstance(context.getProject()).getElementFactory();
        PsiStatement assertStatement = factory.createStatementFromText(text + " " + expr.getText() + ";", parent);
        PsiElement replace = parent.replace(assertStatement);
        editor.getCaretModel().moveToOffset(replace.getTextRange().getEndOffset());
    }

    @Contract("null -> false")
    public static boolean isIterable(@Nullable PsiType type) {
        return type != null && InheritanceUtil.isInheritor(type, CommonClassNames.JAVA_LANG_ITERABLE);
    }

    @Contract("null -> false")
    public static boolean isArray(@Nullable PsiType type) {
        return type != null && type instanceof PsiArrayType;
    }

    @Contract("null -> false")
    public static boolean isBoolean(@Nullable PsiType type) {
        return type != null && (PsiType.BOOLEAN.equals(type) || PsiType.BOOLEAN.equals(PsiPrimitiveType.getUnboxedType(type)));
    }

    @Contract("null -> false")
    public static boolean isNumber(@Nullable PsiType type) {
        if (type == null) {
            return false;
        }
        if (PsiType.INT.equals(type) || PsiType.BYTE.equals(type) || PsiType.LONG.equals(type)) {
            return true;
        }

        PsiPrimitiveType unboxedType = PsiPrimitiveType.getUnboxedType(type);
        return PsiType.INT.equals(unboxedType) || PsiType.BYTE.equals(unboxedType) || PsiType.LONG.equals(unboxedType);
    }

    @Contract("null -> false")
    public static boolean isString(@Nullable PsiType type) {
        return type != null && type.equalsToText(CommonClassNames.JAVA_LANG_STRING);
    }

//    public static List<String> splitViewId(String name){
//        if (name.contains("_")){
//            return splitUnderscoreName(name);
//        }else {
//            return splitCamelName(name);
//        }
//    }

    public static List<String> splitCamelName(String name){
        List<String> words = new LinkedList<>();

        int start = 0;
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (Character.isUpperCase(ch)){
                if (start != i){
                    words.add(name.substring(start,i));
                }
                start = i;
            }
        }

        if (start < name.length()){
            words.add(name.substring(start,name.length()));
        }

        return words;
    }

    public static List<String> splitUnderscoreName(final String name){
        return new LinkedList<String>(){{
            String[] names = name.split("_");
            for (String name:names){
                add(name);
            }
        }};
    }

    /**
     * name match means t is s' common substring
     * @param s
     * @param t
     * @return
     */
    public static boolean nameMatch(@NotNull String s, @NotNull String t){

        if (s.length() == 0 || t.length() == 0)
            return false;

        t = t.toLowerCase();
        s = s.toLowerCase();

        int i, j = 0;
        int minLen;

        if (s.length() < t.length()){
            String tmp = s;
            s = t;
            t = tmp;

        }

        for (i = 0; i < s.length(); i++) {
            if (s.charAt(i) == t.charAt(j)){
                j++;
                if (j == t.length())
                    return true;
            }
        }

        return false;
    }

}
