package com.example.wanandroid.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseInterfaceActivity;
import com.example.wanandroid.dilaog.LoadingDialog;
import com.example.mvpbase.eventbus.EventBusUtil;
import com.example.mvpbase.utils.check.CheckUtil;
import com.example.mvpbase.utils.log.LogUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.wanandroid.R;
import com.example.wanandroid.event.Type;
import com.example.wanandroid.main.event.MyCollectionEvent;
import com.example.wanandroid.main.mvp.AddCollectionPresenter;
import com.example.wanandroid.main.mvp.AddCollectionView;
import com.example.mvpbase.utils.ThemeColorUtil;
import com.example.wanandroid.utils.UserBiz;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/21
 * Time: 10:09
 */
@BindLayoutRes(R.layout.activity_add_collection)
public class AddCollectionActivity extends BaseInterfaceActivity<AddCollectionPresenter> implements AddCollectionView {
    public static final String TITLE = "title";
    private String title;
    private final String TYPE1 = "添加收藏";
    private final String TYPE2 = "分享文章";


    @BindView(R.id.submit)
    Button mSubmit;
    @BindView(R.id.title)
    EditText mTitle;
    @BindView(R.id.link)
    EditText mLink;
    @BindView(R.id.sharerText)
    TextView mSharerText;
    @BindView(R.id.sharer)
    EditText mSharer;

    private LoadingDialog loadingDialog;
    @Override
    public AddCollectionPresenter initPresenter() {
        return new AddCollectionPresenter(this);
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle){
            title = bundle.getString(TITLE);
        }
        super.initView();
        setTitleBar(title, ThemeColorUtil.getThemeColor(mContext));
        mSubmit.setBackgroundColor(ThemeColorUtil.getThemeColor(mContext));
        switch (title){
            case TYPE1:
                mSharerText.setVisibility(View.VISIBLE);
                mSharer.setVisibility(View.VISIBLE);
                break;
            case TYPE2:
                mSharerText.setVisibility(View.GONE);
                mSharer.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        mSharer.setText(CheckUtil.isNotEmptyOb(UserBiz.getUser()) ? UserBiz.getUser().getNickname() : "请登录");
        mSubmit.setOnClickListener(v -> {
            if (CheckUtil.isEmpty(getStringByUI(mTitle))){
                return;
            }
            if (CheckUtil.isEmpty(getStringByUI(mLink))){
                return;
            }
            if (!getStringByUI(mLink).startsWith("http://") && !getStringByUI(mLink).startsWith("https://")){
                LogUtil.e(getStringByUI(mLink));
                ToastUtil.showShort("链接地址必须以http://或https://开头");
                return;
            }
            if (CheckUtil.isEmpty(getStringByUI(mSharer))){
                return;
            }
            hideSoftKeyBoard();
            loadingDialog = LoadingDialog.newInstance();
            loadingDialog.show(getSupportFragmentManager());
            Map<String, Object> map = new HashMap<>(3);
            if (TYPE1.equals(title)) {
                map.put("title", getStringByUI(mTitle));
                map.put("author", getStringByUI(mSharer));
                map.put("link", getStringByUI(mLink));
                getPresenter().addCollection(map);
            }else if (TYPE2.equals(title)){
                map.put("title", getStringByUI(mTitle));
                map.put("link", getStringByUI(mLink));
                getPresenter().shareArticles(map);
            }
        });
    }

    @Override
    public void requestData() {

    }

    @Override
    public void addCollectionSuc() {
        if (loadingDialog != null){
            loadingDialog.dismiss();
        }
        EventBusUtil.post(new MyCollectionEvent(Type.REFRESH_LIST));
        finish();
    }

    @Override
    public void shareArticlesSuc() {
        if (loadingDialog != null){
            loadingDialog.dismiss();
        }
        finish();
    }

    @Override
    public void showErrorMsg(String msg) {
        if (loadingDialog != null){
            loadingDialog.dismiss();
        }
        ToastUtil.showShort(msg);
    }
}
