package com.khosroabadi.myplantaqua.tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.dataModel.dm.product.ProductBean;
import com.khosroabadi.myplantaqua.dataModel.dm.properties.PropertiesBean;
import com.khosroabadi.myplantaqua.webservice.WSUtils;

import java.io.ByteArrayOutputStream;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Alireza on 1/17/2017.
 */

public class ShareDataUtils {

    private  Context mContext;

    public ShareDataUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void sendProductContent(final ProductBean.Product product , final String imageUrl){

        WSUtils wsUtils = new WSUtils(mContext);
        final StringBuilder stringContent = new StringBuilder();
        wsUtils.getProductPropertiesList(product.getId(), new WSUtils.PropertiesInterface() {
            @Override
            public void onSuccess(List<PropertiesBean> propertiesBeanList) {
                // setProductBeanList(productBeanList);
                if (propertiesBeanList != null && propertiesBeanList.size() > 0)
                    stringContent.append("\n");
                    stringContent.append(mContext.getString(R.string.share_content_product_name) + " : " + propertiesBeanList.get(0).getProductName());
                stringContent.append("\n");
                stringContent.append(mContext.getString(R.string.share_content_product_enName) + " : " + propertiesBeanList.get(0).getProductEnName());
                stringContent.append("\n");
                stringContent.append(propertiesBeanList.get(0).getDescription());
                stringContent.append("\n");
                for (PropertiesBean propertiesBean : propertiesBeanList) {

                    stringContent.append(propertiesBean.getPropertiesGroup() + " : " + propertiesBean.getPropertiesItemValue());
                    stringContent.append("\n");
                    stringContent.append("\n");
                    stringContent.append("\n");
                    stringContent.append(mContext.getString(R.string.send_from));
                    stringContent.append("\n");


                }

              //  final Bitmap productImageBitmap = null;
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Bitmap productImageBitmap = Glide.
                                        with(mContext).
                                        load(imageUrl).
                                        asBitmap().
                                        into(200, 200). // Width and height
                                        get();
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(mContext, productImageBitmap , product.getName()));
                                sendIntent.setType("image/*");
                                sendIntent.putExtra(Intent.EXTRA_TEXT , stringContent.toString());
                                sendIntent.setType("text/plain");
                                sendIntent.setPackage("org.telegram.messenger");
                                mContext.startActivity(sendIntent);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    });



            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(mContext , fetchErrorMessage(t) , Toast.LENGTH_LONG).show();
            }
        });

    }

    private String fetchErrorMessage(Throwable t) {
        String errorMsg = mContext.getResources().getString(R.string.unknow_error);
        WSUtils wsUtils = new WSUtils(mContext);
        if(!wsUtils.isNetworkAvailable()) {
            errorMsg = mContext.getResources().getString(R.string.unable_connect_to_internet);
        }
        if(t instanceof TimeoutException) {
            errorMsg = mContext.getResources().getString(R.string.error_msg_timeout);
        }
        if (t instanceof SocketTimeoutException){
            errorMsg = mContext.getResources().getString(R.string.connecting_server_fail);
        }

        return errorMsg;
    }

    private Uri getImageUri(Context inContext, Bitmap inImage , String productName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, productName, null);
        return Uri.parse(path);
    }

}
