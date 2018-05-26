package com.dewey.music_video_526.model;

import com.dewey.music_video_526.bean.NewsBean;
import com.dewey.music_video_526.utils.APIGet_Post_Factory;
import com.dewey.music_video_526.utils.AbstractObserver;
import com.dewey.music_video_526.view.MyView;

/**
 * Created   by   Dewey .
 */
public class MyModel {
    //请求数据的方法 https://www.apiopen.top/satinApi?type=1&page=1
    public void getData(final MyView myView){
        //调用封装的请求方式的单例模式执行类请求接口,传入集合参数，使用被观察者发起定阅事件
        APIGet_Post_Factory.getInstance().get("/satinApi?type=1&page=1", new AbstractObserver<NewsBean>() {
            @Override
            public void onSuccess(NewsBean newsBean) {
                myView.onSuccess(newsBean);
            }

            @Override
            public void onFailure(Exception e) {
                myView.onFailure();
            }
        });
    }
}
