package org.sssta.androidtools.model;

/**
 * Created by cauchywei on 15/8/17.
 */
public class ViewModel {

    private String id;
    private String type;
    private String name;


    public ViewModel(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {

        if (id.matches("^@\\+?id")){
            this.id = id.substring(id.indexOf('/')+1,id.length());
        }

        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
