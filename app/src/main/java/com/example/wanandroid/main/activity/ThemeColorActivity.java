package com.example.wanandroid.main.activity;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
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
import com.example.mvpbase.utils.ThemeColorUtil;
import com.example.wanandroid.main.event.NightModeEvent;
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
    private int pos;
    @Override
    public void initView() {
        setTitleBar("主题颜色", ThemeColorUtil.getThemeColor(mContext));
        index = ThemeColorUtil.getThemeColorIndex();
        pos = index;
        initRv();
    }

    private void initRv() {
        mLists.add(new PromptBean("胖次蓝", R.color.theme_color));
        mLists.add(new PromptBean("夜间模式", R.color.theme_181818));
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
                if (index != i) {
                    index = i;
                    ThemeColorUtil.setThemeColorIndex(i);
                    if (i == 1) {
                        ThemeColorUtil.setTitleColor(ContextCompat.getColor(mContext, R.color.title_bg));
                        ThemeColorUtil.setThemeColor(ContextCompat.getColor(mContext, R.color.theme_color));
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        EventBusUtil.post(new NightModeEvent());
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        ThemeColorUtil.setThemeColor(ContextCompat.getColor(mContext, mLists.get(i).getThemeColor()));
                        ThemeColorUtil.setTitleColor(ContextCompat.getColor(mContext, mLists.get(i).getThemeColor()));
                        EventBusUtil.post(new ChangeThemeEvent());
                        mAdapter.notifyDataSetChanged();
                    }
                    if (index == 1){
                        pos = index;
                        startActivity(new Intent(ThemeColorActivity.this,ThemeColorActivity.class));
                        overridePendingTransition(R.anim.animo_alph_open, R.anim.animo_alph_close);
                        finish();
                        overridePendingTransition(R.anim.animo_alph_open, R.anim.animo_alph_close);
                    }else {
                        if (pos == 1){
                            startActivity(new Intent(ThemeColorActivity.this,ThemeColorActivity.class));
                            overridePendingTransition(R.anim.animo_alph_open, R.anim.animo_alph_close);
                            finish();
                            overridePendingTransition(R.anim.animo_alph_open, R.anim.animo_alph_close);
                            pos = -1;
                            EventBusUtil.post(new NightModeEvent());
                        }else {
                             setTitleBar("主题颜色",ThemeColorUtil.getThemeColor(mContext));
                        }
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }
}
