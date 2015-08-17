package org.sssta.androidtools.posfix.template;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.impl.ConstantNode;
import com.intellij.codeInsight.template.impl.MacroCallNode;
import com.intellij.codeInsight.template.macro.VariableOfTypeMacro;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.sssta.androidtools.posfix.internal.RichChooserStringBasedPostfixTemplate;
import org.sssta.androidtools.util.AndroidFQClass;
import org.sssta.androidtools.util.AndroidPostfixTemplatesUtils;

/**
 * Created by cauchywei on 15/8/15.
 */
public class ToastPostfixTemplate extends RichChooserStringBasedPostfixTemplate {

    public ToastPostfixTemplate() {
        this("toast");
    }

    public ToastPostfixTemplate(@NotNull String alias) {
        super(alias, "Toast.makeText(context, expr, Toast.LENGTH_SHORT).show();", AndroidPostfixTemplatesUtils.IS_NON_NULL);
    }

    @Override
    public String getTemplateString(@NotNull PsiElement element) {
        return getStaticMethodPrefix(AndroidFQClass.TOAST, "makeText", element) + "($context$, $expr$, Toast.LENGTH_SHORT).show()$END$";
    }

    @Override
    protected void setVariables(@NotNull Template template, @NotNull PsiElement element) {

        MacroCallNode node = new MacroCallNode(new VariableOfTypeMacro());
        node.addParameter(new ConstantNode(AndroidFQClass.CONTEXT));
        template.addVariable("context", node, new ConstantNode(""), false);

    }


}
