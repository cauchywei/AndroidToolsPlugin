package org.sssta.androidtools.action.generator;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.sssta.androidtools.model.ViewModel;
import org.sssta.androidtools.util.LayoutUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cauchywei on 15/8/18.
 */
public class FieldsFvbiGenerator extends AbstractFvbiGenerator {

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

    public List<PsiField> generatorFields(Project project, Editor editor, PsiFile xmlFile,PsiElement context) {

        List<ViewModel> views = LayoutUtil.getContainingIdViewsInXml(xmlFile);
        PsiClass psiClass = PsiTreeUtil.getParentOfType(context, PsiClass.class);
        String fieldFormat = "private %s %s;\n";
        List<PsiField> fieldStatements = new ArrayList<>();
        PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();

        for (ViewModel view:views){
            String fvbiStatement = String.format(fieldFormat,view.getClazz(),view.getFieldName());
            fieldStatements.add(factory.createFieldFromText(fvbiStatement, psiClass));
        }

        return  fieldStatements;
    }
}
