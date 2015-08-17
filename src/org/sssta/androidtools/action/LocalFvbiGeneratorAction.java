package org.sssta.androidtools.action;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import org.jetbrains.annotations.NotNull;
import org.sssta.androidtools.action.generator.LocalFvbiGenerator;

/**
 * Created by cauchywei on 15/8/17.
 */
public class LocalFvbiGeneratorAction extends AbstractFvbiGeneratorAction {

    LocalFvbiGenerator localFvbiGenerator = new LocalFvbiGenerator();


    @Override
    public void generate(@NotNull Project project, @NotNull Editor editor, PsiFile xmlFile) {
        PsiElement context = PsiUtilBase.getElementAtCaret(editor);
        String viewParentName = null;
        PsiLocalVariable variable = PsiTreeUtil.getParentOfType(context,PsiLocalVariable.class);
        if (variable != null) {
            viewParentName = variable.getName();
        }

        localFvbiGenerator.generator(project,editor,xmlFile,viewParentName);

    }
}
