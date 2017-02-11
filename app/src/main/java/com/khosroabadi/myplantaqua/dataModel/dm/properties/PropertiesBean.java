package com.khosroabadi.myplantaqua.dataModel.dm.properties;

import com.google.gson.annotations.SerializedName;
import com.khosroabadi.myplantaqua.dataModel.dm.product.ProductBean;
import com.khosroabadi.myplantaqua.dataModel.dm.propertiesItem.PropertiesItemBean;
import com.khosroabadi.myplantaqua.dataModel.dm.propertyGroup.PropertyGroupBean;

/**
 * Created by Alireza on 12/2/2016.
 */

public class PropertiesBean {

    @SerializedName("id")
    private Integer id;
    @SerializedName("categoryId")
    private Long categoryId;
    @SerializedName("productName")
    private String productName;
    @SerializedName("productEnName")
    private String productEnName;
    @SerializedName("propertiesItemValue")
    private String propertiesItemValue;
    @SerializedName("propertiesGroup")
    private String propertiesGroup;
    @SerializedName("rate")
    private Integer rate;
    @SerializedName("description")
    private String description;
    @SerializedName("imageName")
    private String imageName;

    public PropertiesBean() {
    }

    public PropertiesBean(Integer id, Long categoryId, String productName, String productEnName, String propertiesItemValue, String propertiesGroup, Integer rate, String description , String imageName) {
        this.id = id;
        this.categoryId = categoryId;
        this.productName = productName;
        this.productEnName = productEnName;
        this.propertiesItemValue = propertiesItemValue;
        this.propertiesGroup = propertiesGroup;
        this.rate = rate;
        this.description = description;
        this.imageName = imageName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductEnName() {
        return productEnName;
    }

    public void setProductEnName(String productEnName) {
        this.productEnName = productEnName;
    }

    public String getPropertiesItemValue() {
        return propertiesItemValue;
    }

    public void setPropertiesItemValue(String propertiesItemValue) {
        this.propertiesItemValue = propertiesItemValue;
    }

    public String getPropertiesGroup() {
        return propertiesGroup;
    }

    public void setPropertiesGroup(String propertiesGroup) {
        this.propertiesGroup = propertiesGroup;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
