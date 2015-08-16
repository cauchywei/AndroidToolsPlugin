package org.sssta.androidtools.posfix.template;

import com.intellij.codeInsight.template.JavaPsiElementResult;
import com.intellij.codeInsight.template.macro.MacroUtil;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiUtilBase;
import org.jetbrains.annotations.NotNull;
import org.sssta.androidtools.util.CommonUtil;

/**
 * Created by cauchywei on 15/8/15.
 */
public class ToastPostfixTemplate extends PostfixTemplate {

    public ToastPostfixTemplate() {
        super("toast", "Show a Toast","Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();");
    }

    @Override
    public boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset) {
        PsiExpression expr = JavaPostfixTemplatesUtils.getTopmostExpression(context);
        return expr != null && expr.getParent() instanceof PsiExpressionStatement && CommonUtil.isString(expr.getType());
    }

    @Override
    public void expand(@NotNull PsiElement psiElement, @NotNull Editor editor) {

        PsiExpression expr = JavaPostfixTemplatesUtils.getTopmostExpression(psiElement);
        PsiElement parent = expr != null ? expr.getParent() : null;
        assert parent instanceof PsiStatement;

        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, editor.getProject());

        PsiElement context = psiFile.findElementAt(editor.getCaretModel().getOffset());

        if (context == null)
            return;

        PsiType typeFromText = JavaPsiFacade.getInstance(editor.getProject()).getElementFactory().createTypeFromText("android.content.Context", context);
        PsiExpression[] var18 = MacroUtil.getStandardExpressionsOfType(context, typeFromText);

        if (var18.length == 0)
            return;

        JavaPsiElementResult javaPsiElementResult = new JavaPsiElementResult(var18[0]);
        String androidContext = javaPsiElementResult.toString();

        PsiElementFactory factory = JavaPsiFacade.getInstance(context.getProject()).getElementFactory();

        PsiStatement toastStatement = factory.createStatementFromText("Toast.makeText(" + androidContext + ", " + expr.getText() + ", Toast.LENGTH_SHORT).show();", psiElement);
        PsiElement replace = parent.replace(toastStatement);
        editor.getCaretModel().moveToOffset(replace.getTextRange().getEndOffset());

    }
}
