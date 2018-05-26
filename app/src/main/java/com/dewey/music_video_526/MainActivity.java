package com.dewey.music_video_526;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;
import com.dewey.music_video_526.adapter.MyAdapter;
import com.dewey.music_video_526.bean.NewsBean;
import com.dewey.music_video_526.presenter.MyPresenter;
import com.dewey.music_video_526.view.MyView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\  =  /O
 * ____/`---'\____
 * .'  \\|     |//  `.
 * /  \\|||  :  |||//  \
 * /  _||||| -:- |||||-  \
 * |   | \\\  -  /// |   |
 * | \_|  ''\---/''  |   |
 * \  .-\__  `-`  ___/-. /
 * ___`. .'  /--.--\  `. . __
 * ."" '<  `.___\_<|>_/___.'  >'"".
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * \  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * <p>
 * 佛祖保佑        永无BUG        事业家庭两丰收
 */
public class MainActivity extends AppCompatActivity implements MyView{
    private List<NewsBean.DataBean>  list = new ArrayList<>();
    @BindView(R.id.btn_jump)
    Button btn_Jump;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MyPresenter presenter;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //实例化p层，请求数据
        presenter = new MyPresenter(this);
        presenter.get();

        //设置布局管理器以及数据适配器，添加分割线
        recyclerView.setLayoutManager( new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false) );
        adapter = new MyAdapter(MainActivity.this,list);
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_jump)
    public void onViewClicked() {
        //跳转至播放音乐
        Toast.makeText(MainActivity.this,"听首歌，放松一下吧 ",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, MusicActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess(NewsBean newsBean) {
        if (newsBean != null){
            adapter.addData(newsBean.getData());
        }
    }

    @Override
    public void onFailure() {
        Toast.makeText(MainActivity.this,"老哥，bug 来了 ",Toast.LENGTH_SHORT).show();
    }
}
