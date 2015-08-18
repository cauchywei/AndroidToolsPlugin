package org.sssta.androidtools.action.generator;

import com.intellij.codeInsight.template.Template;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.sssta.androidtools.model.ViewModel;
import org.sssta.androidtools.util.LayoutUtil;

import java.util.List;

/**
 * Created by cauchywei on 15/8/18.
 */
public class FieldFvbiGenerator extends AbstractFvbiGenerator {

    @Override
    public String getFvbiFormat() {
        return "%s = (%s) %sfindViewById(%s);\n";
    }

    @Override
    public String getFvbiStatementTemplate(String statementFormat,ViewModel view, String viewParentName) {
        String varName = view.getFieldName();
        String clazz = view.getFqClazz();
        String fqId = view.getFqId();
        return  String.format(statementFormat,varName,clazz,viewParentName,fqId);
    }

    public Template generatorFields(Project project, Editor editor, PsiFile xmlFile, String viewParentName) {


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
