package org.sssta.androidtools.model;

import com.intellij.openapi.util.text.StringUtil;
import org.sssta.androidtools.util.CommonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by cauchywei on 15/8/17.
 */
public class ViewModel {

    private String id;
    private String fqId;

    private String clazz;
    private String fqClazz;

    private String from;


    public ViewModel(String from,String id, String fqClazz) {
        setFrom(from);
        setId(id);
        setFqClazz(fqClazz);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {

        if (id.matches("^@\\+?id/.*")){
            this.id = id.substring(id.indexOf('/')+1,id.length());
        }

        if (this.id.contains(".")){
            this.id = this.id.substring(this.id.lastIndexOf('.'),this.id.length());
        }

        setFqId("R.id."+this.id);
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        if (from.contains(".")){
            this.from = from.split("\\.")[0];
        }
        this.from = from;
    }

    public String getFqId() {
        return fqId;
    }

    public void setFqId(String fqId) {
       this.fqId = fqId;
    }

    public String getFqClazz() {
        return fqClazz;
    }

    public void setFqClazz(String fqClazz) {

        if (fqClazz.contains(".")) {
            this.fqClazz = fqClazz;
        }else if ((fqClazz.equals("View")) || (fqClazz.equals("ViewGroup"))) {
            this.fqClazz = "android.view." + fqClazz;
        }else {
            this.fqClazz = "android.widget." + fqClazz;
        }

        String[] split = fqClazz.split("\\.");
        setClazz(split[split.length - 1]);
    }

    public String getLocalVarName(){
//        List<String> clazzNames = CommonUtil.splitCamelName(getClazz());
        String clazzName = getClazz();
        List<String> splitIds = CommonUtil.splitUnderscoreName(getId());
        List<String> splitFrom = CommonUtil.splitUnderscoreName(getFrom());
        List<String> result;

        if (splitIds.isEmpty())
            return getClazz();

        do {

            //remove the type prefix  e.g. activity_login -> login
            if (!splitFrom.isEmpty()) {
                if (CommonUtil.nameMatch(splitFrom.get(0), "activity") || CommonUtil.nameMatch(splitFrom.get(0), "fragment") || CommonUtil.nameMatch(splitFrom.get(0), "item")) {
                    splitFrom.remove(0);
                }
            }

            result =  new ArrayList<>(Arrays.asList(new String[splitIds.size()]));
            Collections.copy(result,splitIds);

            //remove the type prefix e.g  TextView: textView_login_user_name -> login_user_name
            if (clazzName.toLowerCase().contains(splitIds.get(0).toLowerCase())){
                splitIds.remove(0);
            }
//            for (String clazzName : clazzNames) {
//                if (CommonUtil.nameMatch(splitIds.get(0), clazzName)) {
//                    splitIds.remove(0);
//                }
//            }


            if (splitIds.isEmpty()){
                break;
            }

            //remove scope name   e.g.  login_user_name (from activity_login)  -> user_name
            int minLen = Math.min(splitFrom.size(), splitIds.size());
            result =  new ArrayList<>(Arrays.asList(new String[splitIds.size()]));
            Collections.copy(result,splitIds);

            for (int i = 0; i < minLen; i++) {
                if (CommonUtil.nameMatch(splitFrom.get(0), splitIds.get(0))) {
                    splitIds.remove(0);
                } else {
                    break;
                }
            }

            if (splitIds.isEmpty()){
                break;
            }else {
                result = splitIds;
            }


        }while (false);

        StringBuilder stringBuilder = new StringBuilder();
        for (String s:result) {
            stringBuilder.append(StringUtil.capitalize(s));
        }

        stringBuilder.append(getClazz());

        return StringUtil.decapitalize(stringBuilder.toString());

    }

    public String getFieldName(){
        return "m" + StringUtil.capitalize(getLocalVarName());
    }
}
