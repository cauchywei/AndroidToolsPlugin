package org.sssta.androidtools.action.generator;

import com.intellij.codeInsight.template.Template;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

/**
 * Created by cauchywei on 15/8/17.
 */
public abstract class AbstractFvbiGenerator {

    public abstract Template generator(Project project, Editor editor, PsiFile xmlFile,String viewParentName);
    public Template generatorFields(Project project, Editor editor, PsiFile xmlFile,String viewParentName){
        return null;
    }
}
