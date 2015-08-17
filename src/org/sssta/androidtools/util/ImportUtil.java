package org.sssta.androidtools.util;

import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.siyeh.ig.psiutils.ClassUtils;

public class ImportUtil {

    /**
     * Check whether the current context has a static member import, either on-demand or explicit.
     *
     * @param fqClassName The class to import from
     * @param memberName  The class member to import
     * @param context     The context to be imported into
     */
    public static boolean hasImportStatic(String fqClassName, String memberName, PsiElement context) {
        final PsiFile file = context.getContainingFile();
        if (!(file instanceof PsiJavaFile)) {
            return false;
        }
        final PsiJavaFile javaFile = (PsiJavaFile) file;
        final PsiImportList importList = javaFile.getImportList();
        if (importList == null) {
            return false;
        }
        final PsiImportStaticStatement[] importStaticStatements = importList.getImportStaticStatements();
        for (PsiImportStaticStatement importStaticStatement : importStaticStatements) {
            if (importStaticStatement.isOnDemand()) {
                PsiClass psiClass = ClassUtils.findClass(fqClassName, context);
                if (psiClass != null && psiClass.equals(importStaticStatement.resolveTargetClass())) {
                    return true;
                }
                continue;
            }
            final String name = importStaticStatement.getReferenceName();
            if (!memberName.equals(name)) {
                continue;
            }
            final PsiJavaCodeReferenceElement importReference = importStaticStatement.getImportReference();
            if (importReference == null) {
                continue;
            }
            final PsiElement qualifier = importReference.getQualifier();
            if (qualifier == null) {
                continue;
            }
            final String qualifierText = qualifier.getText();
            if (fqClassName.equals(qualifierText)) {
                return true;
            }
        }
        return false;
    }

    public static void importIfNot(final String fqClassName, final PsiElement context){

        final PsiFile file = context.getContainingFile();
        if (!(file instanceof PsiJavaFile)) {
            return;
        }
        final PsiJavaFile javaFile = (PsiJavaFile) file;
        final PsiImportList importList = javaFile.getImportList();
        if (importList == null) {
            return;
        }

        boolean imported = false;
        PsiImportStatement[] importStatements = importList.getImportStatements();
        for (PsiImportStatement importStatement : importStatements) {
            if (fqClassName.equals(importStatement.getQualifiedName())){
                imported = true;
                break;
            }
        }

        if (!imported){
//            UIUtil.invokeLaterIfNeeded(new Runnable() {
//                @Override
//                public void run() {
//                    importList.add(JavaPsiFacade.getInstance(context.getProject()).getElementFactory().createTypeElement(PsiType.getTypeByName(fqClassName, context.getProject(), GlobalSearchScope.allScope(context.getProject()))));
//                }
//            });
        }
    }

}
