package com.example.wanandroid.ui.system.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpbase.annotation.BindEventBus;
import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseApp;
import com.example.mvpbase.base.BaseInterfaceFragment;
import com.example.mvpbase.loading.LoadingCallBack;
import com.example.mvpbase.loading.NetworkAnomalyCallBack;
import com.example.mvpbase.networks.NetWorkUtils;
import com.example.mvpbase.utils.constant.ConstantUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.mvpbase.widget.MsgView;
import com.example.wanandroid.R;
import com.example.wanandroid.main.event.ChangeThemeEvent;
import com.example.wanandroid.ui.system.activity.SystemDetailedActivity;
import com.example.wanandroid.ui.system.bean.SystemBean;
import com.example.wanandroid.ui.system.mvp.SystemPresenter;
import com.example.wanandroid.ui.system.mvp.SystemView;
import com.example.wanandroid.utils.ThemeColorUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.example.mvpbase.utils.RvUtil.getLayoutManager;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 17:33
 */
@BindEventBus
@BindLayoutRes(R.layout.fragment_system)
public class SystemFragment extends BaseInterfaceFragment<SystemPresenter> implements SystemView {

    public static SystemFragment getInstance(){
        return new SystemFragment();
    }

    @BindView(R.id.systemRv)
    RecyclerView mRecyclerView;
    private CommonAdapter<SystemBean> mAdapter;
    private List<SystemBean> mLists = new ArrayList<>();
    private int index = -1;

    @Override
    public SystemPresenter initPresenter() {
        return new SystemPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        initRefresh();
        setEnableRefresh(false);
        setEnableLoadMore(false);
        initRv();
    }

    private void initRv() {
        mAdapter = new CommonAdapter<SystemBean>(mContext, R.layout.item_system_rv, mLists) {
            @Override
            protected void convert(ViewHolder holder, SystemBean item, int position) {
                holder.setText(R.id.itemSystemName, item.getName())
                        .setText(R.id.itemSystemNum, String.format("%s类", item.getChildren().size()))
                        .setVisible(R.id.itemSystemLay, item.isShow)
                        .setTextColor(R.id.itemSystemNum, ThemeColorUtil.getThemeColor(mContext));
                ImageView imageView = holder.getView(R.id.itemSystemImage);
                imageView.setColorFilter(ThemeColorUtil.getThemeColor(mContext));
                //展开二级列表
                holder.setOnClickListener(R.id.itemSystemBtn, v -> {
                    for (int i = 0; i < mAdapter.getDatas().size(); i++) {
                        if (i == position){
                            mAdapter.getDatas().get(i).isShow = !mAdapter.getDatas().get(i).isShow;
                        }else {
                            mAdapter.getDatas().get(i).isShow = false;
                        }
                    }
                    if (index == position){
                        Log.e("SystemFragment", "onClick1: "+ index);
                        index = -1;
                    }else {
                        int oldOpened = index;
                        Log.e("SystemFragment", "onClick2: "+ oldOpened);
                        index = position;
                        Log.e("SystemFragment", "onClick3: "+ index);
                        mAdapter.notifyItemChanged(oldOpened);
                    }
                    mAdapter.notifyItemChanged(position);

                });
                //二级列表Rv
                RecyclerView recyclerView = holder.getView(R.id.itemSystemRv);
                CommonAdapter<SystemBean.ChildrenBean> adapter = new
                        CommonAdapter<SystemBean.ChildrenBean>(mContext, R.layout.item_system_children_rv, item.getChildren()) {
                    @Override
                    protected void convert(ViewHolder holder, SystemBean.ChildrenBean childrenBean, int position) {
                        MsgView msgView = holder.getView(R.id.itemSystemChildren);
                        msgView.setText(childrenBean.getName());
                        msgView.setBackgroundColor(ThemeColorUtil.getThemeColor(mContext));
                    }
                };
                recyclerView.setLayoutManager(getLayoutManager(mContext));
                recyclerView.setClipToPadding(false);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        Bundle bundle = new Bundle();
                        bundle.putString(SystemDetailedActivity.TITLE, mAdapter.getDatas().get(position).getName());
                        bundle.putParcelableArrayList(SystemDetailedActivity.SYSTEM_CHILDREN,
                                (ArrayList<? extends Parcelable>) mAdapter.getDatas().get(position).getChildren());
                        bundle.putInt(SystemDetailedActivity.INDEX, i);
                        gotoActivity(SystemDetailedActivity.class, bundle);
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        return false;
                    }
                });
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.getItemAnimator().setChangeDuration(300);
        mRecyclerView.getItemAnimator().setMoveDuration(300);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                Bundle bundle = new Bundle();
                bundle.putString(SystemDetailedActivity.TITLE, mAdapter.getDatas().get(i).getName());
                bundle.putParcelableArrayList(SystemDetailedActivity.SYSTEM_CHILDREN, (ArrayList<? extends Parcelable>) mAdapter.getDatas().get(i).getChildren());
                bundle.putInt(SystemDetailedActivity.INDEX, 0);
                gotoActivity(SystemDetailedActivity.class, bundle);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }

    @Override
    public void requestData() {
        getPresenter().getSystem();
        getPresenter().setShowDialog(false);
    }

    @Override
    public void getSystemSuc(List<SystemBean> beans) {
        if (isRefresh()){
            mLists.clear();
        }
        mLists.addAll(beans);
        mAdapter.notifyDataSetChanged();
        successAfter(mAdapter.getItemCount());
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeThemeEvent(ChangeThemeEvent event) {
        mAdapter.notifyDataSetChanged();
    }
}
