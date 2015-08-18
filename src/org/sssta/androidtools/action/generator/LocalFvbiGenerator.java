package org.sssta.androidtools.action.generator;

import org.sssta.androidtools.model.ViewModel;

/**
 * Created by cauchywei on 15/8/17.
 */
public class LocalFvbiGenerator extends AbstractFvbiGenerator {

    @Override
    public String getFvbiFormat() {
        return "%s %s = (%s) %sfindViewById(%s);\n";
    }

    @Override
    public String getFvbiStatementTemplate(String statementFormat,ViewModel view, String viewParentName) {
        String varName = view.getLocalVarName();
        String clazz = view.getFqClazz();
        return  String.format(statementFormat,clazz,varName,clazz,viewParentName,view.getFqId());
    }


}
