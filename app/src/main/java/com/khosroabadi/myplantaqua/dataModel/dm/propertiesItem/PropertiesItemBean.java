package com.khosroabadi.myplantaqua.dataModel.dm.propertiesItem;

import com.google.gson.annotations.SerializedName;
import com.khosroabadi.myplantaqua.dataModel.dm.propertyGroup.PropertyGroupBean;

/**
 * Created by Alireza on 12/2/2016.
 */

public class PropertiesItemBean {

    @SerializedName("id")
    private Integer id;
    @SerializedName("propertyGroupId")
    private Integer propertyGroupId;
    @SerializedName("value")
    private String value;


    public PropertiesItemBean() {
    }

    public PropertiesItemBean(Integer id, Integer propertyGroupId, String value) {
        this.id = id;
        this.propertyGroupId = propertyGroupId;
        this.value = value;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPropertyGroupId() {
        return propertyGroupId;
    }

    public void setPropertyGroupId(Integer propertyGroupId) {
        this.propertyGroupId = propertyGroupId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
