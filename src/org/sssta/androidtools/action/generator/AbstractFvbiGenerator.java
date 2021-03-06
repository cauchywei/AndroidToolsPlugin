package org.sssta.androidtools.action.generator;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.sssta.androidtools.model.ViewModel;
import org.sssta.androidtools.util.LayoutUtil;

import java.util.List;

/**
 * Created by cauchywei on 15/8/17.
 */
public abstract class AbstractFvbiGenerator {

    public abstract String getFvbiFormat();

    public abstract String getFvbiStatementTemplate(String format, ViewModel view,String viewParentName);

    public String processTemplateString(String template){
        return template;
    }

    public Template generateTemplate(Project project ,String template){
        TemplateManager templateManager = TemplateManager.getInstance(project);
        return templateManager.createTemplate("", "",processTemplateString(template));
    }

    public Template generator(Project project, Editor editor, PsiFile xmlFile, String viewParentName) {


        List<ViewModel> views = LayoutUtil.getContainingIdViewsInXml(xmlFile);
        viewParentName = viewParentName == null?"":viewParentName + ".";

        String statementFormat = getFvbiFormat();
        StringBuilder templateString = new StringBuilder("\n");

        for (ViewModel view:views){
            String fvbiStatement = getFvbiStatementTemplate(statementFormat,view,viewParentName);
            templateString.append(fvbiStatement);
        }

        templateString.append("$end$");

        return  generateTemplate(project,templateString.toString());
    }
}
