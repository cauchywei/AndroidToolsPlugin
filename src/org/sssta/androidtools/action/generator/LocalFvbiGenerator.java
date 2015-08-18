package org.sssta.androidtools.action.generator;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.XmlRecursiveElementVisitor;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import org.sssta.androidtools.model.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cauchywei on 15/8/17.
 */
public class LocalFvbiGenerator extends AbstractFvbiGenerator {

    public LocalFvbiGenerator() {
    }

    @Override
    public Template generator(Project project, Editor editor, PsiFile xmlFile, String viewParentName) {

        final String name = xmlFile.getName();
        final List<ViewModel> views = new ArrayList<>();
        xmlFile.accept(new XmlRecursiveElementVisitor() {
            @Override
            public void visitXmlTag(XmlTag tag) {
                super.visitXmlTag(tag);
                XmlAttribute idAttribute = tag.getAttribute("android:id");

                if (idAttribute != null) {
                    String id = idAttribute.getValue();
                    if (id != null && id.matches("^@\\+?id/.*")){
                        views.add(new ViewModel(name,id,tag.getName()));
                    }
                }
            }
        });

        List<PsiStatement> fvbiStatements = new ArrayList<>();
        viewParentName = viewParentName == null?"":viewParentName + ".";

        String statementFormat = "%s %s = (%s) %sfindViewById(%s);\n";
        StringBuilder templateString = new StringBuilder("\n");

        for (ViewModel view:views){
            String varName = view.getLocalVarName();
            String clazz = view.getFqClazz();
            String fvbiStatement = String.format(statementFormat,clazz,varName,clazz,viewParentName,view.getFqId());
            templateString.append(fvbiStatement);
        }

        templateString.append("$end$");

        TemplateManager templateManager = TemplateManager.getInstance(project);

        return templateManager.createTemplate("", "",templateString.toString());
    }
}
