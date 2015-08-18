package org.sssta.androidtools.action;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateEditingAdapter;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.impl.ActionManagerImpl;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.playback.commands.ActionCommand;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.sssta.androidtools.action.generator.FieldsFvbiGenerator;

import java.util.List;

/**
 * Created by cauchywei on 15/8/17.
 */
public class FieldsFvbiGeneratorAction extends AbstractFvbiGeneratorAction {

    FieldsFvbiGenerator fieldsFvbiGenerator = new FieldsFvbiGenerator();


    @Override
    public void insertStatement(@NotNull Project project,@NotNull Editor editor,PsiElement context,PsiFile xmlFile) {

        String viewParentName = null;
        PsiLocalVariable variable = PsiTreeUtil.getParentOfType(context, PsiLocalVariable.class);
        if (variable != null) {
            viewParentName = variable.getName();
        }

        Template template = fieldsFvbiGenerator.generator(project, editor, xmlFile, viewParentName);
        final List<PsiField> psiFields = fieldsFvbiGenerator.generatorFields(project, editor, xmlFile,context);
        final PsiClass psiClass = PsiTreeUtil.getTopmostParentOfType(context,PsiClass.class);

        WriteCommandAction.runWriteCommandAction(project, new Runnable() {
            @Override
            public void run() {
                PsiField[] fields = psiClass.getFields();

                for (PsiField newField : psiFields) {
                    boolean exist = false;
                    for (PsiField field : fields) {
                        if (field.getName().equals(newField.getName())) {
                            exist = true;
                            break;
                        }
                    }

                    if (!exist) {
                        psiClass.add(newField);
                    }
                }
            }
        });


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
