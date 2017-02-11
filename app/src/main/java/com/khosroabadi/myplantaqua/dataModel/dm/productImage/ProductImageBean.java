package com.khosroabadi.myplantaqua.dataModel.dm.productImage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alireza on 12/2/2016.
 */

public class ProductImageBean {

    @SerializedName("imageNameList")
   private List<String> imageNameList;


    public List<String> getImageNameList() {
        return imageNameList;
    }

    public void setImageNameList(List<String> imageNameList) {
        this.imageNameList = imageNameList;
    }
}
