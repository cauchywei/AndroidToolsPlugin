package org.sssta.androidtools.generator;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cauchywei on 15/8/17.
 */
public abstract class AbstractFvbiGenerator {

    public static final List<PsiStatement> EMPTY_STATEMENT = new ArrayList<>(0);

    public abstract List<PsiStatement> generator(Project project, Editor editor, PsiFile xmlFile,String viewParentName);
    public List<PsiStatement> generatorFields(Project project, Editor editor, PsiFile xmlFile,String viewParentName){
        return EMPTY_STATEMENT;
    }
}
