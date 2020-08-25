package com.example.wanandroid.main.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseInterfaceActivity;
import com.example.mvpbase.utils.log.LogUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.mvpbase.widget.MsgView;
import com.example.wanandroid.R;
import com.example.wanandroid.main.bean.PointsRankingBean;
import com.example.wanandroid.main.bean.UserPointsBean;
import com.example.wanandroid.main.mvp.PointsRankingPresenter;
import com.example.wanandroid.main.mvp.PointsRankingView;
import com.example.wanandroid.utils.ThemeColorUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/19
 * Time: 11:38
 */
@BindLayoutRes(R.layout.activity_points_ranking)
public class PointsRankingActivity extends BaseInterfaceActivity<PointsRankingPresenter> implements PointsRankingView {
    @BindView(R.id.pointsRankingRv)
    RecyclerView mRecyclerView;
    private CommonAdapter<PointsRankingBean.DatasBean> mAdapter;
    private List<PointsRankingBean.DatasBean> mLists = new ArrayList<>();

    private UserPointsBean mBean;
    private boolean isBtn = false;
    @Override
    public PointsRankingPresenter initPresenter() {
        return new PointsRankingPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        initRefresh();
        setTitleBar("积分排行", ThemeColorUtil.getThemeColor(mContext));
        showLoadingDialog();
        initRv();
    }

    private void initRv() {
        mAdapter = new CommonAdapter<PointsRankingBean.DatasBean>(mContext, R.layout.item_points_ranking_rv, mLists) {
            @Override
            protected void convert(ViewHolder holder, PointsRankingBean.DatasBean item, int position) {
                switch (position){
                    case 0:
                        holder.setImageResource(R.id.itemPointsRankingCrown, R.drawable.icon_champion);
                        holder.setVisible(R.id.itemPointsRankingCrown, true);
                        break;
                    case 1:
                        holder.setImageResource(R.id.itemPointsRankingCrown, R.drawable.iconrunner_up);
                        holder.setVisible(R.id.itemPointsRankingCrown, true);
                        break;
                    case 2:
                        holder.setImageResource(R.id.itemPointsRankingCrown, R.drawable.icon_third_place);
                        holder.setVisible(R.id.itemPointsRankingCrown, true);
                        break;
                    default:
                        holder.setVisible(R.id.itemPointsRankingCrown, false);
                        break;
                }
                holder.setText(R.id.itemPointsRankingName, item.getUsername())
                        .setText(R.id.itemPointsRankingCoinCount, String.format("积分：%s", item.getCoinCount()));
                MsgView msgView = holder.getView(R.id.itemPointsRankingLevel);
                msgView.setText(String.format("LV %s", item.getLevel()));
                msgView.setBackgroundColor(ThemeColorUtil.getThemeColor(mContext));
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void requestData() {
        getPresenter().getPointsRanking(getPage());
        if (isRefresh()) {
            getPresenter().getUserPoints();
        }
    }
    @Override
    public void getPointsRankingSuc(PointsRankingBean bean) {
        if (isRefresh()){
            mLists.clear();
        }
        mLists.addAll(bean.getDatas());
        mAdapter.notifyDataSetChanged();
        successAfter(mAdapter.getItemCount());
    }


    @Override
    public void getUserPointsSuc(UserPointsBean bean) {
        isBtn = true;
        mBean = bean;
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showShort(msg);
        failureAfter(mAdapter.getItemCount());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0x001, 0, "我的排行").setIcon(R.drawable.ic_history).setShowAsAction(1);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 0x001){
            if (isBtn) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(UserPointsActivity.USER_INFO, mBean);
                gotoActivity(UserPointsActivity.class, bundle);

            }
        }
        return super.onOptionsItemSelected(item);
    }
}
