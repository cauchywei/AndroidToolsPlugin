package org.sssta.androidtools.posfix.template;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.impl.ConstantNode;
import com.intellij.codeInsight.template.impl.MacroCallNode;
import com.intellij.codeInsight.template.macro.VariableOfTypeMacro;
import com.intellij.codeInsight.template.postfix.templates.StringBasedPostfixTemplate;
import com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.util.InheritanceUtil;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sssta.androidtools.util.AndroidFQClass;
import org.sssta.androidtools.util.AndroidPostfixTemplatesUtils;

/**
 * Created by cauchywei on 15/8/25.
 */
public class ClickListenerPostfixTemplate extends StringBasedPostfixTemplate {

    private boolean isNewListener = true;

    public ClickListenerPostfixTemplate() {
        super("clk", "view.setOnClickListener(listener)",JavaPostfixTemplatesUtils.selectorTopmost(AndroidPostfixTemplatesUtils.IS_VIEW));
    }


    @Nullable
    @Override
    public String getTemplateString(@NotNull PsiElement psiElement) {

        PsiClass topmostParentOfType = PsiTreeUtil.getTopmostParentOfType(psiElement, PsiClass.class);

        if (topmostParentOfType != null) {
            PsiClassType[] implementsListTypes = topmostParentOfType.getImplementsListTypes();
            for (PsiClassType implementsListType : implementsListTypes) {
                if (InheritanceUtil.isInheritor(implementsListType,AndroidFQClass.VIEW_ON_CLICK_LISTENER)){
                    isNewListener = false;
                    break;
                }
            }

            if (isNewListener){
                PsiField[] fields = topmostParentOfType.getFields();
                for (PsiField field : fields) {
                    if (InheritanceUtil.isInheritor(field.getType(),AndroidFQClass.VIEW_ON_CLICK_LISTENER)) {
                        isNewListener = false;
                        break;
                    }
                }
            }
        }

        String listener;
        if(isNewListener){
            listener = "new View.OnClickListener() {\n\t@Override\npublic void onClick(View v) {\n$END$\n}\n}";
        }else {
            listener = "$listener$";
        }

        return "$expr$.setOnClickListener(" + listener + ");";

    }

    @Override
    public void setVariables(@NotNull Template template, @NotNull PsiElement element) {
        super.setVariables(template, element);
//old implement
//        MacroCallNode node = new MacroCallNode(new VariableOfTypeMacro());
//        node.addParameter(new ConstantNode(AndroidFQClass.VIEW_ON_CLICK_LISTENER));
//
//        MacroCallNode defNode = new MacroCallNode(new ViewListenerMacro());
//
//        template.addVariable("listener", node, defNode, true);
        if (!isNewListener){
            MacroCallNode node = new MacroCallNode(new VariableOfTypeMacro());
            node.addParameter(new ConstantNode(AndroidFQClass.VIEW_ON_CLICK_LISTENER));
            template.addVariable("listener", node, new ConstantNode(""), true);
        }
    }
}
