package org.sssta.androidtools.model;

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

        if (id.matches("^@\\+?id/")){
            this.id = id.substring(id.indexOf('/')+1,id.length());
        }

        if (this.id.contains(".")){
            this.id = this.id.substring(this.id.lastIndexOf('.'),this.id.length());
        }

        setFqId("R.id."+id);
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
            this.fqClazz = "android.view.%s" + fqClazz;
        }else {
            this.fqClazz = "android.widget.%s" + fqClazz;
        }

        String[] split = fqClazz.split(".");
        setClazz(split[split.length - 1]);
    }

    public String getFieldName(){
        return null;
    }
}
