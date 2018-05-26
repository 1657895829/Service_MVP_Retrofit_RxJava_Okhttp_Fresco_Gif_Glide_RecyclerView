package com.dewey.music_video_526.view;

import com.dewey.music_video_526.bean.NewsBean;

/**
 * Created   by   Dewey .
 */

public interface MyView {
    void onSuccess(NewsBean newsBean);
    void onFailure();
}
