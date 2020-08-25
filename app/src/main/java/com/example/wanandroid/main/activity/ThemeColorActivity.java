package com.example.wanandroid.main.activity;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseActivity;
import com.example.mvpbase.eventbus.EventBusUtil;
import com.example.mvpbase.widget.MsgView;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.PromptBean;
import com.example.wanandroid.main.event.ChangeThemeEvent;
import com.example.wanandroid.utils.ThemeColorUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 11:18
 */
@BindLayoutRes(R.layout.activity_theme_color)
public class ThemeColorActivity extends BaseActivity {
    @BindView(R.id.themeColorRv)
    RecyclerView mRecyclerView;
    private CommonAdapter<PromptBean> mAdapter;
    private List<PromptBean> mLists = new ArrayList<>();
    private int index = 0;

    @Override
    public void initView() {
        setTitleBar("主题颜色", ThemeColorUtil.getThemeColor(mContext));
        index = ThemeColorUtil.getThemeColorIndex();
        initRv();
    }

    private void initRv() {
        mLists.add(new PromptBean("胖次蓝", R.color.theme_color));
        mLists.add(new PromptBean("少女粉", R.color.theme_FB7299));
        mLists.add(new PromptBean("基佬紫", R.color.theme_673AB7));
        mLists.add(new PromptBean("姨妈红", R.color.theme_F44336));
        mLists.add(new PromptBean("咸蛋黄", R.color.theme_FDD835));
        mLists.add(new PromptBean("早苗绿", R.color.theme_8BC34A));
        mAdapter = new CommonAdapter<PromptBean>(mContext, R.layout.item_theme_color_rv, mLists) {
            @Override
            protected void convert(ViewHolder holder, PromptBean item, int position) {
                MsgView msgView = holder.getView(R.id.themeColorBlock);
                msgView.setBackgroundColor(ContextCompat.getColor(mContext, item.getThemeColor()));
                holder.setText(R.id.themeColorTitle, item.getContent())
                        .setVisible(R.id.themeColorImage, index == position);
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                index = i;
                ThemeColorUtil.setThemeColorIndex(i);
                ThemeColorUtil.setThemeColor(ContextCompat.getColor(mContext, mLists.get(i).getThemeColor()));
                setTitleBar("主题颜色",ThemeColorUtil.getThemeColor(mContext));
                EventBusUtil.post(new ChangeThemeEvent());
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }
}
