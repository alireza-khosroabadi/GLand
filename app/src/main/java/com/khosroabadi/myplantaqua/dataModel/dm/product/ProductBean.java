package com.khosroabadi.myplantaqua.dataModel.dm.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alireza on 12/23/2016.
 */

public class ProductBean {


    private Integer pageNumber;
    @SerializedName("totalPage")
    private Integer totalPage;
    @SerializedName("totalResult")
    private Integer totalResult;
    @SerializedName("productsBeanList")
    private List<Product> productList;

    public ProductBean() {
    }

    public ProductBean(Integer pageNumber, Integer totalPage, Integer totalResult, List<Product> productList) {
        this.pageNumber = pageNumber;
        this.totalPage = totalPage;
        this.totalResult = totalResult;
        this.productList = productList;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(Integer totalResult) {
        this.totalResult = totalResult;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public  class Product implements Parcelable {
        private Integer id;
        private Integer rate;
        private String name;
        private String enName;
        private Integer categoryId;
        private String imageName;

        public Product() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getRate() {
            return rate;
        }

        public void setRate(Integer rate) {
            this.rate = rate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

        }
    }
}
