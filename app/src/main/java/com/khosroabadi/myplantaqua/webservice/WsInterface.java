package com.khosroabadi.myplantaqua.webservice;

import com.khosroabadi.myplantaqua.dataModel.dm.deviceInfo.DeviceInfoBean;
import com.khosroabadi.myplantaqua.dataModel.dm.product.ProductBean;
import com.khosroabadi.myplantaqua.dataModel.dm.productImage.ProductImageBean;
import com.khosroabadi.myplantaqua.dataModel.dm.properties.PropertiesBean;
import com.khosroabadi.myplantaqua.dataModel.dm.propertiesItem.PropertiesItemBean;
import com.khosroabadi.myplantaqua.dataModel.dm.propertyGroup.PropertyGroupBean;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Alireza on 12/4/2016.
 */

public interface WsInterface {

    @GET("rest/ratingService/updaterate/{productId}")
    Call<Boolean> updateProductRate(@Path("productId") Integer id , @Query("value") Integer value);

    @GET("rest/productService/getproductlistbyfilter/{category}")
    Call<ProductBean> getProducts(@Path("category") String categoryId ,@Query("properties") String properties,@Query("orderBy") String orderBy,
                                  @Query("orderDir") Integer orderDir,@Query("pageNumber") Integer pageNumber,
                                  @Query("rowNumber") Integer rowNumber , @Query("searchParam") String searchParam );

    @GET("rest/propertyGroupService/getpropertygrouplist/{category}")
    Call<List<PropertyGroupBean>> getPropertGroup(@Path("category") String category );

    @GET("rest/propertyItemService/getpropertyitemlist/{groupId}")
    Call<List<PropertiesItemBean>> getPropertisItem(@Path("groupId") Integer groupId);

    @GET("rest/propertiesService/getproductpropertylist/{productId}")
    Call<List<PropertiesBean>> getProductProperties(@Path("productId") Integer productId);


    @GET("rest/productService/getRandomProduct")
    Call<ProductBean> getRandomProduct();

    @POST("rest/deviceInfoService/new")
    Call<Void> insertDeviceInfo(@Body DeviceInfoBean deviceInfoBean);

    @GET("rest/productImageService/getproductimage/{imageName}")
    Call<ProductImageBean> getProductImages(@Path("imageName") String imagePath);
}
