package com.khosroabadi.myplantaqua.dataModel.dm.propertyGroup;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alireza on 12/2/2016.
 */

public class PropertyGroupBean {

    @SerializedName("id")
    private Integer id;
    @SerializedName("category")
    private String category;
    @SerializedName("type")
    private String type;
    @SerializedName("value")
    private String value;

    public PropertyGroupBean() {
    }

    public PropertyGroupBean(Integer id, String category, String type, String value) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.value = value;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
