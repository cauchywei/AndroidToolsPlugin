package org.sssta.androidtools.action;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateEditingAdapter;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.impl.ActionManagerImpl;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.playback.commands.ActionCommand;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
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
        final PsiElement context = PsiUtilBase.getElementAtCaret(editor);
        String viewParentName = null;
        PsiLocalVariable variable = PsiTreeUtil.getParentOfType(context,PsiLocalVariable.class);
        if (variable != null) {
            viewParentName = variable.getName();
        }

        PsiMethodCallExpression callExpression = PsiTreeUtil.getParentOfType(context,PsiMethodCallExpression.class);

        boolean found = false;
        PsiElement psiElement = callExpression;

        while(psiElement.getNextSibling()!=null){
            psiElement = psiElement.getNextSibling();
            if (psiElement.getText().trim().equals(";")){
                found = true;
                break;
            }else if (!psiElement.getText().trim().equals("")){
                break;
            }
        }

        TextRange textRange = psiElement.getTextRange();
        editor.getCaretModel().moveToOffset(found?textRange.getEndOffset():textRange.getStartOffset());

        Template template = localFvbiGenerator.generator(project, editor, xmlFile, viewParentName);

        TemplateManager.getInstance(project).startTemplate(editor, template, new TemplateEditingAdapter() {
            @Override
            public void templateFinished(Template template, boolean brokenOff) {
//                // format and add ;
                final ActionManager actionManager = ActionManagerImpl.getInstance();
                final String editorCompleteStatementText = "EditorCompleteStatement";
                final AnAction action = actionManager.getAction(editorCompleteStatementText);
                actionManager.tryToExecute(action, ActionCommand.getInputEvent(editorCompleteStatementText), null, ActionPlaces.UNKNOWN, true);
            }
        });
    }
}
