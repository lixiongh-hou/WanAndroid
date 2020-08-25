package com.example.wanandroid.ui.project.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseApp;
import com.example.mvpbase.base.BaseInterfaceFragment;
import com.example.mvpbase.divider.ListDivider;
import com.example.mvpbase.eventbus.EventBusUtil;
import com.example.mvpbase.loading.NetworkAnomalyCallBack;
import com.example.mvpbase.networks.NetWorkUtils;
import com.example.mvpbase.utils.check.CheckUtil;
import com.example.mvpbase.utils.constant.ConstantUtil;
import com.example.mvpbase.utils.glide.GlideUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.wanandroid.R;
import com.example.wanandroid.dao.footprint.FootprintUtil;
import com.example.wanandroid.event.Type;
import com.example.wanandroid.main.activity.WebViewActivity;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.example.wanandroid.ui.home.event.HomeEvent;
import com.example.wanandroid.ui.project.bean.ProjectListBean;
import com.example.wanandroid.ui.project.mvp.ProjectPresenter;
import com.example.wanandroid.ui.project.mvp.ProjectView;
import com.example.wanandroid.utils.UserBiz;
import com.example.wanandroid.wechat.bean.WeChatNameBean;
import com.wanglu.photoviewerlibrary.PhotoViewer;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/14
 * Time: 14:06
 */
@BindLayoutRes(R.layout.fragment_project_children)
public class ProjectChildrenFragment extends BaseInterfaceFragment<ProjectPresenter> implements ProjectView {
    private static final String ID = "id";
    private String id;
    public static ProjectChildrenFragment getInstance(String id){
        Bundle bundle = new Bundle();
        bundle.putString(ID, id);
        ProjectChildrenFragment fragment = new ProjectChildrenFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @BindView(R.id.projectRv)
    RecyclerView mRecyclerView;
    private CommonAdapter<DatasBean> mAdapter;
    private List<DatasBean> mList = new ArrayList<>();
    @Override
    public ProjectPresenter initPresenter() {
        return new ProjectPresenter(this);
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        if (null != bundle){
            id = bundle.getString(ID);
        }
        super.initView();
        initRefresh();
        initRv();
    }

    private void initRv() {
        mAdapter = new CommonAdapter<DatasBean>(mContext, R.layout.item_project_rv, mList) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void convert(ViewHolder holder, DatasBean item, int position) {
                ImageView imageView = holder.getView(R.id.projectImage);
                GlideUtil.loadImg(mContext, item.getEnvelopePic(), imageView);
                holder.setText(R.id.projectTitle, handleTitle(item))
                        .setText(R.id.projectContent, item.getDesc())
                        .setText(R.id.projectTime, String.format("%s / %s", item.getNiceDate(), handleAuthor(item)))
                        .setImageResource(R.id.projectCollect, isCollect(item));
                holder.setOnClickListener(R.id.projectCollect, v -> {
                    if (UserBiz.hasLogin(mContext)) {
                        if (item.isCollect()) {
                            item.setCollect(false);
                            getPresenter().unCollect(String.valueOf(item.getId()));
                            holder.setImageResource(R.id.projectCollect, R.drawable.uncollect_selector_icon);
                        } else {
                            item.setCollect(true);
                            getPresenter().collect(String.valueOf(item.getId()));
                            holder.setImageResource(R.id.projectCollect, R.drawable.collect_selector_icon);
                        }
                        FootprintUtil.updateFootPrint(mContext, item);
                    }
                });
                holder.setOnClickListener(R.id.projectImage, v -> PhotoViewer.INSTANCE.setClickSingleImg(item.getEnvelopePic(), imageView)
                        .setShowImageViewInterface((imageView1, s) -> {
                            GlideUtil.loadImg(mContext, s, imageView1);
                        }).start(ProjectChildrenFragment.this));
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new ListDivider(5, R.color.trans, false));
        mRecyclerView.setAdapter(mAdapter);
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

    private int isCollect(DatasBean item) {
        return item.isCollect() ? R.drawable.collect_selector_icon : R.drawable.uncollect_selector_icon;
    }
    /**
     * 判断用户名
     */
    private String handleAuthor(DatasBean item) {
        if (CheckUtil.isEmpty(item.getAuthor())) {
            return item.getShareUser();
        } else if (CheckUtil.isEmpty(item.getShareUser())) {
            return item.getAuthor();
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
    @Override
    public void requestData() {
        getPresenter().getProjectList(getPage(), id);
    }

    @Override
    public void getProjectTreeSuc(List<WeChatNameBean> bean) {

    }

    @Override
    public void getProjectListSuc(ProjectListBean bean) {
        if (isRefresh()){
            mList.clear();
        }
        mList.addAll(bean.getDatas());
        mAdapter.notifyDataSetChanged();
        successAfter(mAdapter.getItemCount());
    }

    @Override
    public void collectSuc() {
        EventBusUtil.post(new HomeEvent(Type.REFRESH_LIST));
    }

    @Override
    public void unCollectSuc() {
        EventBusUtil.post(new HomeEvent(Type.REFRESH_LIST));
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showShort(msg);
        if (ConstantUtil.NETWORK.equals(msg)){
            loadService.showCallback(NetworkAnomalyCallBack.class);
        }
        failureAfter(mAdapter.getItemCount());
    }

    @Override
    public void reLoad(View view) {
        super.reLoad(view);
        view.setOnClickListener(v -> {
            if (NetWorkUtils.isNetworkConnected(BaseApp.getAppContext())){
                showLoadingDialog();
            }
            requestData();
        });
    }
}
