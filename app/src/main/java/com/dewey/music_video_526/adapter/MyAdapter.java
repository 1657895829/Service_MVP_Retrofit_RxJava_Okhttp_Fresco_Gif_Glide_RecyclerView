package com.dewey.music_video_526.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.dewey.music_video_526.R;
import com.dewey.music_video_526.bean.NewsBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created   by   Dewey .
 * RecyclerView多条目展示数据
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<NewsBean.DataBean> list;

    public MyAdapter(Context context, List<NewsBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    //添加数据的方法
    public void addData(List<NewsBean.DataBean> data) {
        if (list != null) {
            list.clear();
            list.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        /**
         * 判断布局类型，加载不同数据
         * 条目类型根据type字段来决定， type=41指的是带有“发布时间”的布局
         */
        NewsBean.DataBean bean = list.get(position);
        String type = bean.getType();

        if (type.equals("41")) {
            return 1;
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view01 = View.inflate(context, R.layout.adapter_image, null);
            return new MyViewHolder01(view01);
        } else {
            View view02 = View.inflate(context, R.layout.adapter_text, null);
            return new MyViewHolder02(view02);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //判断加载布局的类型,设置参数数据

        //其他布局
        if (holder instanceof MyViewHolder01) {
            MyViewHolder01 holder01 = (MyViewHolder01) holder;
            holder01.name.setText(list.get(position).getName());
            holder01.time.setText(list.get(position).getPasstime());
            holder01.profileImage.setImageURI(list.get(position).getProfile_image());

            //判断是否为动图
            boolean is_gif = list.get(position).isIs_gif();
            if (!is_gif){
                Toast.makeText(context,"动起来   " + position,Toast.LENGTH_SHORT).show();

                DraweeController controller =  (DraweeController) Fresco.newDraweeControllerBuilder()
                        .setUri(list.get(position).getImage0())
                        .setAutoPlayAnimations(true)     //自动播放gif动画
                        .build();
                holder01.cdnImage.setController(controller);

            }else {
                Toast.makeText(context,"安静会   " + position,Toast.LENGTH_SHORT).show();
                holder01.cdnImage.setImageURI(list.get(position).getImage0());
            }
        }

        //type=41指的是带有“发布时间”的布局
        else if (holder instanceof MyViewHolder02) {
            MyViewHolder02 holder02 = (MyViewHolder02) holder;
            holder02.text.setText(list.get(position).getText());
            holder02.profileImage.setImageURI(list.get(position).getProfile_image());

            //普通页面播放视频，显示视频标题
            holder02.videoPlayer.setUp(list.get(position).getVideouri(), JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN);
            Glide.with(context).load(list.get(position).getCdn_img()).into(holder02.videoPlayer.thumbImageView);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class MyViewHolder01 extends RecyclerView.ViewHolder {
        @BindView(R.id.profileImage)
        SimpleDraweeView profileImage;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.cdnImage)
        SimpleDraweeView cdnImage;

        public MyViewHolder01(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class MyViewHolder02 extends RecyclerView.ViewHolder {
        @BindView(R.id.profileImage)
        SimpleDraweeView profileImage;
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.videoPlayer)
        JZVideoPlayerStandard videoPlayer;

        public MyViewHolder02(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
