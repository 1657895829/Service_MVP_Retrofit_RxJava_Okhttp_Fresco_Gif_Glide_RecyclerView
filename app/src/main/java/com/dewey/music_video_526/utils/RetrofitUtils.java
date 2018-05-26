package com.dewey.music_video_526.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Retrofit请求网络的单例模式封装类
 */

public class RetrofitUtils {

    //静态公共请求接口参数
    private static APIServiceInterface service  = null ;

    //双重锁模式下的请求
    public static APIServiceInterface getInstance(){
        if(service == null){
            synchronized (RetrofitUtils.class){

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://www.apiopen.top")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(OkHttpUtils.getInstance())
                        .build();

                //实例化请求接口类
                service = retrofit.create(APIServiceInterface.class);
            }
        }
        return service;
    }

}
