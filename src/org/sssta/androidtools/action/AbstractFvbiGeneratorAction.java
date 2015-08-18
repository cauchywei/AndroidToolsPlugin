package org.sssta.androidtools.action;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import org.jetbrains.annotations.NotNull;
import org.sssta.androidtools.util.LayoutUtil;

/**
 * Created by cauchywei on 15/8/17.
 */
public abstract class AbstractFvbiGeneratorAction extends BaseGenerateAction {

    public AbstractFvbiGeneratorAction() {
        super(null);
    }

    public AbstractFvbiGeneratorAction(CodeInsightActionHandler handler) {
        super(handler);
    }


    @Override
    protected boolean isValidForFile(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {

        int offset = editor.getCaretModel().getOffset();
        PsiElement psiElement = file.findElementAt(offset);

        if(!PlatformPatterns.psiElement().inside(PsiMethodCallExpression.class).accepts(psiElement)) {
            return false;
        }

        PsiMethodCallExpression psiMethodCallExpression = PsiTreeUtil.getParentOfType(psiElement, PsiMethodCallExpression.class);
        if(psiMethodCallExpression == null) {
            return false;
        }

        PsiMethod psiMethod = psiMethodCallExpression.resolveMethod();
        if(psiMethod == null) {
            return false;
        }

        if (psiMethod.getName().equals("setContentView") || psiMethod.getName().equals("inflate")){
            return true;
        }

        return super.isValidForFile(project, editor, file);
    }

    @Override
    public void actionPerformedImpl(@NotNull Project project,@NotNull Editor editor) {

        PsiFile layoutXmlFile = LayoutUtil.findLayoutXmlFile(project, editor);
        generate(project,editor,layoutXmlFile);

    }

    public void generate(@NotNull Project project, @NotNull Editor editor, PsiFile xmlFile) {

        final PsiElement context = PsiUtilBase.getElementAtCaret(editor);


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


        insertStatement(project,editor,context,xmlFile);

    }

    public abstract void insertStatement(@NotNull Project project,@NotNull Editor editor,PsiElement context,PsiFile xmlFile);

}
