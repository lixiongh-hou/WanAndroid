package com.example.wanandroid.wechat.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpbase.annotation.BindEventBus;
import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseApp;
import com.example.mvpbase.base.BaseInterfaceFragment;
import com.example.mvpbase.divider.ListDivider;
import com.example.mvpbase.loading.NetworkAnomalyCallBack;
import com.example.mvpbase.networks.NetWorkUtils;
import com.example.mvpbase.utils.constant.ConstantUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.wanandroid.R;
import com.example.wanandroid.adapter.WeChatLeftAdapter;
import com.example.wanandroid.adapter.WeChatRightAdapter;
import com.example.wanandroid.main.activity.WebViewActivity;
import com.example.wanandroid.main.event.ChangeThemeEvent;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.example.mvpbase.utils.ThemeColorUtil;
import com.example.wanandroid.wechat.bean.WeChatListBean;
import com.example.wanandroid.wechat.bean.WeChatNameBean;
import com.example.wanandroid.wechat.mvp.WeChatPresenter;
import com.example.wanandroid.wechat.mvp.WeChatView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 17:30
 */
@BindEventBus
@BindLayoutRes(R.layout.fragment_we_chat)
public class WeChatFragment extends BaseInterfaceFragment<WeChatPresenter> implements WeChatView {

    public static WeChatFragment getInstance(){
        return new WeChatFragment();
    }

    @BindView(R.id.WeChatLeftRv)
    RecyclerView mRecyclerViewLeft;
    private WeChatLeftAdapter mLeftAdapter;
    private List<WeChatNameBean> mLeftLists = new ArrayList<>();

    @BindView(R.id.WeChatRightRv)
    RecyclerView mRecyclerViewRight;
    private WeChatRightAdapter mRightAdapter;
    private List<DatasBean> mRightLists = new ArrayList<>();

    @BindView(R.id.tv_header)
    TextView tvStickyHeaderView;
    private boolean move = true;
    private int index = 0;

    @Override
    public WeChatPresenter initPresenter() {
        return new WeChatPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        initRefresh();
        tvStickyHeaderView.setTextColor(ThemeColorUtil.getThemeColor(mContext));
        initLeftRv();
        initRightRv();


    }


    private void initLeftRv() {
        mLeftAdapter = new WeChatLeftAdapter(mContext, mLeftLists);
        mRecyclerViewLeft.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerViewLeft.setAdapter(mLeftAdapter);
        ((DefaultItemAnimator) mRecyclerViewLeft.getItemAnimator()).setSupportsChangeAnimations(false);
        mLeftAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                mLeftAdapter.setChecked(i);
                if (index != i){
                    startRefresh();
                    index = i;
                }

            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }
    private void initRightRv() {
        mRightAdapter = new WeChatRightAdapter(mContext, mRightLists);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerViewRight.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewRight.addItemDecoration(new ListDivider(5, R.color.trans, false));
        ((DefaultItemAnimator) mRecyclerViewRight.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerViewRight.setAdapter(mRightAdapter);
        mRightAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.URL, mRightAdapter.getDatas().get(i).getLink());
                bundle.putString(WebViewActivity.TITLE, Html.fromHtml(mRightAdapter.getDatas().get(i).getTitle(),
                        Html.FROM_HTML_MODE_COMPACT).toString());
                gotoActivity(WebViewActivity.class, bundle);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }

    @Override
    public void requestData() {
        if (move) {
            getPresenter().getWeChatName();
            getPresenter().setShowDialog(false);
            move = false;
        }else {
            getPresenter().getWeChatList(
                    String.valueOf(mLeftLists.get(mLeftAdapter.getChecked()).getId()), getPage());
        }
    }



    @Override
    public void getWeChatNameSuc(List<WeChatNameBean> bean) {
        if (isRefresh()){
            mLeftLists.clear();
        }
        mLeftLists.addAll(bean);
        mLeftAdapter.notifyDataSetChanged();
        getPresenter().getWeChatList(String.valueOf(bean.get(0).getId()), getPage());


    }

    @Override
    public void getWeChatListSuc(WeChatListBean bean) {
        if (isRefresh()){
            mRightLists.clear();
        }
        tvStickyHeaderView.setText(bean.getDatas().get(0).getAuthor());
        mRightLists.addAll(bean.getDatas());
        mRightAdapter.notifyDataSetChanged();
        successAfter(mRightAdapter.getItemCount());


    }
    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showShort(msg);
        if (ConstantUtil.NETWORK.equals(msg)){
            loadService.showCallback(NetworkAnomalyCallBack.class);
        }
        failureAfter(mRightAdapter.getItemCount());
    }
    @Override
    public void reLoad(View view) {
        super.reLoad(view);
        view.setOnClickListener(v -> {
            if (NetWorkUtils.isNetworkConnected(BaseApp.getAppContext())) {
                showLoadingDialog();
            }
            move = true;
            requestData();
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeThemeEvent(ChangeThemeEvent event) {
        tvStickyHeaderView.setTextColor(ThemeColorUtil.getThemeColor(mContext));
        mLeftAdapter.notifyDataSetChanged();

    }
}
