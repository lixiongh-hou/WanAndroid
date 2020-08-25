package com.example.wanandroid.main.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpbase.annotation.BindEventBus;
import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseInterfaceActivity;
import com.example.mvpbase.divider.ListDivider;
import com.example.mvpbase.eventbus.EventBusUtil;
import com.example.mvpbase.utils.check.CheckUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.wanandroid.R;
import com.example.wanandroid.event.Type;
import com.example.wanandroid.main.event.MainEvent;
import com.example.wanandroid.main.event.MyCollectionEvent;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.example.wanandroid.ui.home.event.HomeEvent;
import com.example.wanandroid.main.bean.MyCollectionBean;
import com.example.wanandroid.main.mvp.MyCollectionPresenter;
import com.example.wanandroid.main.mvp.MyCollectionView;
import com.example.wanandroid.utils.ThemeColorUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 15:45
 */
@BindEventBus
@BindLayoutRes(R.layout.activity_my_collection)
public class MyCollectionActivity extends BaseInterfaceActivity<MyCollectionPresenter> implements MyCollectionView {
    @BindView(R.id.collectionRv)
    RecyclerView mRecyclerView;
    private CommonAdapter<DatasBean> mAdapter;
    private List<DatasBean> mLists = new ArrayList<>();

    @Override
    public MyCollectionPresenter initPresenter() {
        return new MyCollectionPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        initRefresh();
        setTitleBar("我的收藏", ThemeColorUtil.getThemeColor(mContext));
        initRv();
    }
    private void initRv() {
        mAdapter = new CommonAdapter<DatasBean>(mContext, R.layout.item_article_rv, mLists) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void convert(ViewHolder holder, DatasBean item, int position) {
                holder.setText(R.id.articleAuthor, handleAuthor(item))
                        .setText(R.id.articleTime, item.getNiceDate())
                        .setText(R.id.articleTitle, handleTitle(item))
                        .setText(R.id.articleType, handleCategory(item))
                        .setImageResource(R.id.articleCollect, R.drawable.collect_selector_icon);
                holder.setOnClickListener(R.id.articleCollect, v -> {
                    getPresenter().unMyCollect(String.valueOf(item.getId()),
                            String.valueOf(item.getOriginId()));
                    mLists.remove(position);
                    //下面鬼方法不会调用onBindViewHolder所以 position 不会改变
                    mAdapter.notifyItemRemoved(position);
                    // 如果移除的是最后一个，忽略
                    if(position != mLists.size()){
                        mAdapter.notifyItemRangeChanged(position, mLists.size() - position);
                    }
                });
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new ListDivider(5, R.color.white, false));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.TITLE, handleTitle(mAdapter.getDatas().get(i)));
                bundle.putString(WebViewActivity.URL, mAdapter.getDatas().get(i).getLink());
                gotoActivity(WebViewActivity.class, bundle);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });

    }
    /**
     * 判断用户名
     */
    private String handleAuthor(DatasBean item) {
        if (CheckUtil.isEmpty(item.getAuthor())) {
            return String.format("作者：%s", item.getShareUser());
        } else if (CheckUtil.isEmpty(item.getShareUser())) {
            return String.format("作者：%s", item.getAuthor());
        } else {
            return "匿名用户";
        }
    }

    /**
     * 判断显示内容
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String handleTitle(DatasBean item) {
        return Html.fromHtml(item.getTitle(), Html.FROM_HTML_MODE_COMPACT).toString();
    }

    /**
     * 判断文章分类
     */
    private String handleCategory(DatasBean item) {
        if (CheckUtil.isEmpty(item.getSuperChapterName()) && CheckUtil.isEmpty(item.getChapterName())) {
            return "";
        } else if (CheckUtil.isEmpty(item.getSuperChapterName())) {
            return item.getChapterName();
        } else if (CheckUtil.isEmpty(item.getChapterName())) {
            return item.getSuperChapterName();
        } else {
            return String.format("%s / %s", item.getSuperChapterName(), item.getChapterName());
        }
    }
    @Override
    public void requestData() {
        getPresenter().getMyCollection(getPage());
        getPresenter().isShowDialog(false);
    }

    @Override
    public void getMyCollectionSuc(MyCollectionBean bean) {
        if (isRefresh()){
            mLists.clear();
        }
        mLists.addAll(bean.getDatas());
        mAdapter.notifyDataSetChanged();
        successAfter(mAdapter.getItemCount());
    }

    @Override
    public void unMyCollectSuc() {
        if (mAdapter.getDatas().size() == 0){
            successAfter(0);
        }
        EventBusUtil.post(new HomeEvent(Type.REFRESH_LIST));
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showShort(msg);
        failureAfter(mAdapter.getItemCount());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0x001, 0, "我的收藏").setIcon(R.drawable.ic_add).setShowAsAction(1);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 0x001){
            Bundle bundle = new Bundle();
            bundle.putString(AddCollectionActivity.TITLE, "添加收藏");
            gotoActivity(AddCollectionActivity.class, bundle);
        }
        return super.onOptionsItemSelected(item);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myCollectionEvent(MyCollectionEvent event) {
        if (event.type == Type.REFRESH_LIST){
            startRefresh();
        }
    }
}
