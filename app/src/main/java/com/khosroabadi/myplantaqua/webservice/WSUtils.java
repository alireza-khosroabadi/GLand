package com.khosroabadi.myplantaqua.webservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.khosroabadi.myplantaqua.dataModel.dm.deviceInfo.DeviceInfoBean;
import com.khosroabadi.myplantaqua.dataModel.dm.product.ProductBean;
import com.khosroabadi.myplantaqua.dataModel.dm.productImage.ProductImageBean;
import com.khosroabadi.myplantaqua.dataModel.dm.properties.PropertiesBean;
import com.khosroabadi.myplantaqua.dataModel.dm.propertiesItem.PropertiesItemBean;
import com.khosroabadi.myplantaqua.dataModel.dm.propertyGroup.PropertyGroupBean;
import com.khosroabadi.myplantaqua.webservice.WsInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alireza on 12/19/2016.
 */

public class WSUtils {

    Context mCOntext;
    WsInterface wsInterface;


    public WSUtils(Context context) {
        this.mCOntext = context;
        wsInterface = WsClient.getClient().create(WsInterface.class);
    }


    public void raiting(Integer productId , Integer value , final RatingCallback result){
       // final Boolean[] result = {false};
        Call<Boolean> call = wsInterface.updateProductRate(productId , value);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
            //    result[0] = Boolean.valueOf(response.body().booleanValue());
             //   Log.d("WSRESULT" , result[0].);
                result.onSuccess(Boolean.valueOf(response.body().booleanValue()));
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
           //     result[0] = false;
                t.printStackTrace();
                result.onSuccess(false);
            }
        });
      //  return result[0];
    }

    public void getproductList(String category ,Integer pageNumber,String properties ,String orderBy , Integer orderDir , Integer rowNumber,String searchParam,  final productListInterface result){
        // final Boolean[] result = {false};
        Call<ProductBean> call = wsInterface.getProducts(category ,properties , orderBy,orderDir, pageNumber , rowNumber , searchParam);
        call.enqueue(new Callback<ProductBean>() {
            @Override
            public void onResponse(Call<ProductBean> call,  Response<ProductBean> response) {
                   Log.d("WSRESULT" , response.toString());
                   Log.d("WSRESULT" , response.message());
                if(response.body().getProductList() != null && response.body().getProductList().size()>0 ) {
                    try {
                        result.onSuccess(response.body().getProductList(), response.body().getTotalPage(), response.body().getTotalResult(), response.body().getPageNumber());

                    }catch (Exception e){
                        result.onSuccess(new ArrayList<ProductBean.Product>() , 0 , 0,0);
                    }
                }else {
                    result.onSuccess(new ArrayList<ProductBean.Product>() , 0 , 0,0);
                }

            }

            @Override
            public void onFailure(Call<ProductBean> call, Throwable t) {
                //     result[0] = false;
                t.printStackTrace();
                result.onFailure(t);
            }
        });
    }

    public void getPropertyGroupList(String category , final propertyGroupInterfce result){
        // final Boolean[] result = {false};
        Call<List<PropertyGroupBean>> call = wsInterface.getPropertGroup(category );
        call.enqueue(new Callback<List<PropertyGroupBean>>() {
            @Override
            public void onResponse(Call<List<PropertyGroupBean>> call,  Response<List<PropertyGroupBean>> response) {

                result.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<PropertyGroupBean>> call, Throwable t) {
                //     result[0] = false;
                t.printStackTrace();
                result.onSuccess(new ArrayList<PropertyGroupBean>());
            }
        });
    }

    public void getPropertyItemList(Integer groupId , final propertyItemInterfce result){
        // final Boolean[] result = {false};
        Call<List<PropertiesItemBean>> call = wsInterface.getPropertisItem(groupId );
        call.enqueue(new Callback<List<PropertiesItemBean>>() {
            @Override
            public void onResponse(Call<List<PropertiesItemBean>> call,  Response<List<PropertiesItemBean>> response) {

                result.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<PropertiesItemBean>> call, Throwable t) {
                //     result[0] = false;
                t.printStackTrace();
                result.onSuccess(new ArrayList<PropertiesItemBean>());
            }
        });
    }

    public void getProductPropertiesList(Integer productId , final PropertiesInterface result){
        // final Boolean[] result = {false};
        Call<List<PropertiesBean>> call = wsInterface.getProductProperties(productId );
        call.enqueue(new Callback<List<PropertiesBean>>() {
            @Override
            public void onResponse(Call<List<PropertiesBean>> call,  Response<List<PropertiesBean>> response) {

                result.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<PropertiesBean>> call, Throwable t) {
                //     result[0] = false;
                t.printStackTrace();
                result.onFailure(t);
            }
        });
    }


    public void getRandomProduct( final productListInterface result){
        // final Boolean[] result = {false};
        Call<ProductBean> call = wsInterface.getRandomProduct();
        call.enqueue(new Callback<ProductBean>() {
            @Override
            public void onResponse(Call<ProductBean> call,  Response<ProductBean> response) {
                if(response.body().getProductList() != null && response.body().getProductList().size()>0 ) {
                    try {
                        result.onSuccess(response.body().getProductList(), response.body().getTotalPage(), response.body().getTotalResult(), response.body().getPageNumber());

                    }catch (Exception e){
                        result.onSuccess(new ArrayList<ProductBean.Product>() , 0 , 0,0);
                    }
                }else {
                    result.onSuccess(new ArrayList<ProductBean.Product>() , 0 , 0,0);
                }

            }

            @Override
            public void onFailure(Call<ProductBean> call, Throwable t) {
                //     result[0] = false;
                t.printStackTrace();
               result.onFailure(t);
            }
        });
    }


    public void getProductImage(String imagePath, final ProductImageInterface result){
        // final Boolean[] result = {false};
        Call<ProductImageBean> call = wsInterface.getProductImages(imagePath);
        call.enqueue(new Callback<ProductImageBean>() {
            @Override
            public void onResponse(Call<ProductImageBean> call,  Response<ProductImageBean> response) {
                if(response.body().getImageNameList() != null && response.body().getImageNameList().size()>0 ) {
                    try {
                        result.onSuccess(response.body().getImageNameList());

                    }catch (Exception e){
                        result.onSuccess(new ArrayList<String>() );
                    }
                }else {
                    result.onSuccess(new ArrayList<String>());
                }

            }

            @Override
            public void onFailure(Call<ProductImageBean> call, Throwable t) {
                //     result[0] = false;
                t.printStackTrace();
                result.onFailure(t);
            }
        });
    }


    public void sendDeviceInfo(DeviceInfoBean deviceInfoBean, final RatingCallback result){
        // final Boolean[] result = {false};
        Call<Void> call = wsInterface.insertDeviceInfo(deviceInfoBean);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                //    result[0] = Boolean.valueOf(response.body().booleanValue());
                //   Log.d("WSRESULT" , result[0].);
                result.onSuccess(true);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //     result[0] = false;
                t.printStackTrace();
                result.onSuccess(false);
            }
        });
        //  return result[0];
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mCOntext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    public interface PropertiesInterface{
        void onSuccess(List<PropertiesBean> propertiesBeanList);
        void onFailure(Throwable t);
    }

    public interface ProductImageInterface{
        void onSuccess(List<String> productImageList);
        void onFailure(Throwable t);
    }

    public interface  propertyGroupInterfce{
        void onSuccess(List<PropertyGroupBean> propertyGroupList);
    }

    public interface  propertyItemInterfce{
        void onSuccess(List<PropertiesItemBean> propertyItemList);
    }

    public interface productListInterface{
        void onSuccess(List<ProductBean.Product> productBeanList , Integer totalPage , Integer totalResult , Integer pageNumber);
        void onFailure(Throwable t);
    }

    public interface RatingCallback{
        void onSuccess(Boolean result);
    }
}
