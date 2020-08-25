package com.example.wanandroid.main.activity;

import android.animation.IntEvaluator;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseInterfaceActivity;
import com.example.mvpbase.utils.RvUtil;
import com.example.mvpbase.utils.constant.ConstantUtil;
import com.example.mvpbase.utils.data.DateUtil;
import com.example.mvpbase.utils.glide.GlideUtil;
import com.example.mvpbase.utils.log.LogUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.mvpbase.widget.MsgView;
import com.example.mvpbase.widget.image.CircleImageView;
import com.example.wanandroid.R;
import com.example.wanandroid.main.bean.UserPointsBean;
import com.example.wanandroid.main.bean.UserPointsListBean;
import com.example.wanandroid.main.mvp.UserPointsPresenter;
import com.example.wanandroid.main.mvp.UserPointsView;
import com.example.wanandroid.utils.glide.GlideBlurformation;
import com.example.wanandroid.utils.ThemeColorUtil;
import com.example.wanandroid.widget.ArcImageView;
import com.kingja.loadsir.core.LoadSir;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author: 雄厚
 * Date: 2020/8/19
 * Time: 15:32
 */
@BindLayoutRes(R.layout.activity_user_points)
public class UserPointsActivity extends BaseInterfaceActivity<UserPointsPresenter> implements UserPointsView {
    public static final String USER_INFO = "userInfo";
    private UserPointsBean mBean;

    @BindView(R.id.userPointsBgTitle)
    LinearLayout mLinearLayout;
    @BindView(R.id.userPointsToolbar)
    Toolbar mToolbar;
    @BindView(R.id.nestedScrollView)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.content)
    LinearLayout content;

    @BindView(R.id.userPointsBg)
    ArcImageView mImageViewBg;
    @BindView(R.id.userPointsHead)
    CircleImageView mHead;
    @BindView(R.id.userPointsName)
    TextView mName;
    @BindView(R.id.userPointsLevel)
    MsgView mLevel;
    @BindView(R.id.userPointsCoinCount)
    TextView mCoinCount;
    @BindView(R.id.userPointsRank)
    TextView mRank;
    @BindView(R.id.userPointsRv)
    RecyclerView mRecyclerView;
    private CommonAdapter<UserPointsListBean.DatasBean> mAdapter;
    private List<UserPointsListBean.DatasBean> mLists = new ArrayList<>();

    @Override
    public UserPointsPresenter initPresenter() {
        return new UserPointsPresenter(this);
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle){
            mBean = bundle.getParcelable(USER_INFO);
        }
        super.initView();
        hideTitle();
        initRefresh();
        initUser();
        initSnv();
        initRv();
    }



    private void initRv() {
        mAdapter = new CommonAdapter<UserPointsListBean.DatasBean>(mContext, R.layout.item_user_points_rv, mLists) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void convert(ViewHolder holder, UserPointsListBean.DatasBean item, int position) {
                String descStr = item.getDesc().contains("积分") ? item.getDesc().substring(
                        item.getDesc().indexOf("积分")
                ) : "";
                holder.setText(R.id.itemUserPointsContent, String.format("%s%s", item.getReason(), descStr))
                        .setText(R.id.itemUserPointsTime, DateUtil.timeStamp2Date(item.getDate(), DateUtil.DF_YYYY_MM_DD_HH_MM_SS))
                        .setText(R.id.itemUserPointCoinCount, String.format("+(%s)", item.getCoinCount()))
                        .setTextColor(R.id.itemUserPointCoinCount, ThemeColorUtil.getThemeColor(mContext));
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        RvUtil.solveNestQuestion(mRecyclerView);
    }

    private void initSnv() {
        mLinearLayout.getBackground().mutate().setAlpha(0);
        mNestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int absY = Math.abs(scrollY);
            int scrollLength = mImageViewBg.getHeight() / 2;
            //如果在变化的范围内
            if ((scrollLength- absY) > 0) {
                // 算法：开始值 +（结束值 - 开始值）* 进度
                IntEvaluator evaluator = new IntEvaluator();
                // 总数-变动的值/总数 （ 变动的值范围：0~总数 ）
                // 结果比例：1 ~ 0
                float percent = (float) (scrollLength - absY) / scrollLength;

                //标题栏透明度
                int evaluate = evaluator.evaluate(percent, 255, 0);
                mLinearLayout.getBackground().mutate().setAlpha(evaluate);
            }else {
                mLinearLayout.getBackground().mutate().setAlpha(255);
            }
        });
    }
    private void initUser() {
        mToolbar.setTitle("我的积分");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
        mToolbar.setNavigationIcon(com.example.mvpbase.R.drawable.back_icon);
        mToolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        mLinearLayout.setBackgroundColor(ThemeColorUtil.getThemeColor(mContext));
        loadService = LoadSir.getDefault().register(content);
        showLoadingDialog();
        mName.setText(mBean.getUsername());
        mLevel.setBackgroundColor(ThemeColorUtil.getThemeColor(mContext));
        mLevel.setText(String.format("LV %s", mBean.getLevel()));
        mCoinCount.setText(String.format("积分：%s\u3000", mBean.getCoinCount()));
        mRank.setText(String.format("\u3000排行：%s", mBean.getRank()));
        Glide.with(this)
                .load(ConstantUtil.HEAD)
                .dontAnimate()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.error_img_square)
                .apply(RequestOptions.bitmapTransform(new GlideBlurformation(mContext)))
                .into(mImageViewBg);
        GlideUtil.loadImg(mContext, ConstantUtil.HEAD, mHead);
    }
    @Override
    public void requestData() {
        getPresenter().getUserPointsList(getPage());
    }

    @Override
    public void getUserPointsListSuc(UserPointsListBean bean) {
        if (isRefresh()){
            mLists.clear();
        }
        mLists.addAll(bean.getDatas());
        mAdapter.notifyDataSetChanged();
        successAfter(mAdapter.getItemCount());
    }

    @Override
    public void getUserPoints(UserPointsBean bean) {

    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showShort(msg);
        successAfter(mAdapter.getItemCount());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0x001, 0, "积分规则").setIcon(R.drawable.ic_help).setShowAsAction(1);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 0x001){
            Bundle bundle = new Bundle();
            bundle.putString(WebViewActivity.TITLE, "积分规则");
            bundle.putString(WebViewActivity.URL, "https://www.wanandroid.com/blog/show/2653");
            gotoActivity(WebViewActivity.class, bundle);
        }
        return super.onOptionsItemSelected(item);
    }
}
