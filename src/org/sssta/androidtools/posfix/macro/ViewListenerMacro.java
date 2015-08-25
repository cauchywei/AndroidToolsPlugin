package org.sssta.androidtools.posfix.macro;

import com.intellij.codeInsight.template.*;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.annotations.Nullable;

/**
 * Created by cauchywei on 15/8/25.
 */
public class ViewListenerMacro extends Macro {
    @Override
    public String getName() {
        return "new click listener";
    }

    @Override
    public String getPresentableName() {
        return "new click listener";
    }

    @Nullable
    @Override
    public Result calculateResult(Expression[] expressions, ExpressionContext expressionContext) {

        PsiElementFactory factory = JavaPsiFacade.getInstance(expressionContext.getProject()).getElementFactory();
        String listener = "new View.OnClickListener() {\n\t@Override\npublic void onClick(View v) {\n\n}\n}";
        PsiStatement statementFromText = factory.createStatementFromText(listener, null);
        statementFromText = (PsiStatement) CodeStyleManager.getInstance(expressionContext.getProject()).reformat(statementFromText);
        return new JavaPsiElementResult(statementFromText);
    }
}
