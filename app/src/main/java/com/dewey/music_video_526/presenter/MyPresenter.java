package com.dewey.music_video_526.presenter;

import com.dewey.music_video_526.bean.NewsBean;
import com.dewey.music_video_526.model.MyModel;
import com.dewey.music_video_526.view.MyView;

/**
 * Created   by   Dewey .
 */
public class MyPresenter {
    private MyView myView;
    private MyModel myModel;

    public MyPresenter(MyView myView) {
        this.myView = myView;
        myModel = new MyModel();
    }

    //请求数据
    public void get(){
        myModel.getData(new MyView() {
            @Override
            public void onSuccess(NewsBean newsBean) {
                if (myView != null){
                    myView.onSuccess(newsBean);
                }
            }

            @Override
            public void onFailure() {
                if (myView != null){
                    myView.onFailure();
                }
            }
        });
    }

    //避免内存泄露，解绑
    public void detach(){
        this.myView = null;
    }
}
