package com.example.wanandroid.main.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseInterfaceActivity;
import com.example.wanandroid.dilaog.LoadingDialog;
import com.example.mvpbase.divider.ListDivider;
import com.example.mvpbase.utils.RvUtil;
import com.example.mvpbase.utils.check.CheckUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.wanandroid.R;
import com.example.wanandroid.dilaog.PromptDialog;
import com.example.wanandroid.main.bean.MyShareBean;
import com.example.wanandroid.main.mvp.MySharePresenter;
import com.example.wanandroid.main.mvp.MyShareView;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.example.mvpbase.utils.ThemeColorUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author: 雄厚
 * Date: 2020/8/21
 * Time: 17:00
 */
@BindLayoutRes(R.layout.activity_my_share)
public class MyShareActivity extends BaseInterfaceActivity<MySharePresenter> implements MyShareView {

    @BindView(R.id.myShare)
    RecyclerView mRecyclerView;
    private List<DatasBean> mLists = new ArrayList<>();
    private CommonAdapter<DatasBean> mAdapter;

    private int index = -1;
    private LoadingDialog loadingDialog;
    @Override
    public MySharePresenter initPresenter() {
        return new MySharePresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        showLoadingDialog();
        setTitleBar("我的分享", ThemeColorUtil.getThemeColor(mContext));
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
                        .setVisible(R.id.articleNew, item.isFresh())
                        .setVisible(R.id.articleStatus, item.isTop())
                        .setImageResource(R.id.articleCollect, R.drawable.ic_delete_share);
                holder.setOnClickListener(R.id.articleCollect, v -> PromptDialog.showDialog(mContext,
                        "删除分享", "确定要删除自己分享文章?", (dialog, which) -> {
                            getPresenter().delMyShare(String.valueOf(item.getId()));
                            index = position;
                            loadingDialog = LoadingDialog.newInstance();
                            loadingDialog.show(getSupportFragmentManager());
                        }));
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new ListDivider(5, R.color.trans, false));
        mRecyclerView.setAdapter(mAdapter);
        RvUtil.solveNestQuestion(mRecyclerView);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.URL, mAdapter.getDatas().get(i).getLink());
                bundle.putString(WebViewActivity.TITLE, handleTitle(mAdapter.getDatas().get(i)));
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
        getPresenter().myShare(getPage());
    }

    @Override
    public void myShareSuc(MyShareBean beans) {
        if (isRefresh()){
            mLists.clear();
        }
        mLists.addAll(beans.getShareArticles().getDatas());
        mAdapter.notifyDataSetChanged();
        successAfter(mAdapter.getItemCount());
    }

    @Override
    public void delMyShareSuc() {
        mLists.remove(index);
        //下面鬼方法不会调用onBindViewHolder所以 position 不会改变,当删除到最后一个会闪退下标越界
        mAdapter.notifyItemRemoved(index);
        // 如果移除的是最后一个，忽略
        if(index != mLists.size()){
            mAdapter.notifyItemRangeChanged(index, mLists.size() - index);
        }
        if (loadingDialog != null){
            loadingDialog.dismiss();
        }
        if (mAdapter.getDatas().size() == 0){
            successAfter(0);
        }

    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showShort(msg);
        failureAfter(mAdapter.getItemCount());
    }


}
