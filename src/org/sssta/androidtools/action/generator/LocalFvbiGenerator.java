package org.sssta.androidtools.action.generator;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
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
    public List<PsiStatement> generator(Project project, Editor editor, PsiFile xmlFile, String viewParentName) {

        String name = xmlFile.getName();


        final List<ViewModel> views = new ArrayList<>();
        xmlFile.accept(new XmlRecursiveElementVisitor() {
            @Override
            public void visitXmlTag(XmlTag tag) {
                super.visitXmlTag(tag);
                XmlAttribute idAttribute = tag.getAttribute("android:id");

                if (idAttribute != null) {
                    String id = idAttribute.getValue();
                    if (id != null && id.matches("^@\\+?id")){
                        views.add(new ViewModel(id,tag.getName()));
                    }
                }
            }
        });

        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();

        String statementFormat = null;
        viewParentName = viewParentName == null?"":viewParentName + ".";

        statementFormat = "%s %s = (%s)%sfindViewById(%);";

        for (ViewModel view:views){
            String varName = "";
//            String fvbiStatement = String.format(statementFormat,view.getType(),)
//            elementFactory.createStatementFromText(,)
        }

        List<PsiStatement> fvbiStatements = new ArrayList<>();



        return null;
    }
}
