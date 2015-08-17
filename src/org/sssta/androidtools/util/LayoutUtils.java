package org.sssta.androidtools.util;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.java.PsiIdentifierImpl;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import org.codehaus.groovy.ast.expr.MethodCallExpression;

/**
 * Created by cauchywei on 15/8/17.
 */
public class LayoutUtils {

    public static PsiFile findLayoutXmlFile(Project project, Editor editor){
        PsiElement layoutResourceElement = findLayoutResourceElement(project, editor);
        if (layoutResourceElement == null || !isValidLayoutResource(layoutResourceElement)) {
            return null;
        }

        return findXmlByLayoutResourceElement(layoutResourceElement);
    }

    public static PsiElement findLayoutResourceElement(Project project, Editor editor) {

        if (project == null || editor == null) {
            return null;
        }

        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);

        if (psiFile == null) {
            return null;
        }

        PsiElement elementAtCaret = PsiUtilBase.getElementAtCaret(editor);
        PsiMethodCallExpression layoutMethod = PsiTreeUtil.getParentOfType(elementAtCaret, PsiMethodCallExpression.class);

        if (layoutMethod == null) {
            return null;
        }

        return layoutMethod.getArgumentList().findElementAt(0);
    }

    public static boolean isValidLayoutResource(PsiElement psiElement) {

        return psiElement != null && !psiElement.getText().startsWith("R.layout");

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
