package com.khosroabadi.myplantaqua.dataModel.dm.favorits;

/**
 * Created by Alireza on 12/24/2016.
 */

public class FavoritsBean {

    private Integer id;
    private Integer productId;

    public static final String AttrId = "id";
    public static final String AttrProductId = "productId";


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
