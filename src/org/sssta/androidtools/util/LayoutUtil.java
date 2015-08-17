package org.sssta.androidtools.util;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;

/**
 * Created by cauchywei on 15/8/17.
 */
public class LayoutUtil {

    public static PsiFile findLayoutXmlFile(Project project, Editor editor){

        PsiElement layoutResourceElement = findLayoutResourceElement(project, editor);

        if (layoutResourceElement == null || !isValidLayoutResource(layoutResourceElement)) {
            return null;
        }

        return findXmlByLayoutResourceElement(layoutResourceElement);
    }

    public static PsiElement findLayoutResourceElement(Project project, Editor editor){
        return findLayoutResourceElementByMethodCallExpress(findLayoutSetMethod(project, editor));
    }

    public static PsiElement findLayoutResourceElementByMethodCallExpress(PsiMethodCallExpression methodCallExpression) {

        if (methodCallExpression == null) {
            return null;
        }

        PsiExpression[] expressions = methodCallExpression.getArgumentList().getExpressions();
        if (expressions.length == 0){
            return null;
        }
        return expressions[0];
    }

    public static PsiMethodCallExpression findLayoutSetMethod(Project project, Editor editor){

        if (project == null || editor == null) {
            return null;
        }

        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);

        if (psiFile == null) {
            return null;
        }

        PsiElement elementAtCaret = PsiUtilBase.getElementAtCaret(editor);
        return PsiTreeUtil.getParentOfType(elementAtCaret, PsiMethodCallExpression.class);
    }

    public static boolean isValidLayoutResource(PsiElement psiElement) {

        if (psiElement == null) {
            return false;
        }

        String name;
        if (psiElement instanceof PsiReferenceExpression){
            name = ((PsiReferenceExpression)psiElement).getReferenceName();
        }else {
            name = psiElement.getText();
        }


        return !"R.layout".startsWith(name);

    }

    public static PsiFile findXmlByLayoutResourceElement(PsiElement layoutResource){

        Project project = layoutResource.getProject();
        String xmlName = layoutResource.getLastChild().getText() + ".xml";
        PsiFile[] xmlFiles = FilenameIndex.getFilesByName(project, xmlName, GlobalSearchScope.allScope(project));

        if (xmlFiles == null || xmlFiles.length == 0){
            return null;
        }

        return xmlFiles[0];

    }


}
