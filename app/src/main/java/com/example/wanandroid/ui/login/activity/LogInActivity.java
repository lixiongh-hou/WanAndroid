package com.example.wanandroid.ui.login.activity;

import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseInterfaceActivity;
import com.example.wanandroid.dilaog.LoadingDialog;
import com.example.mvpbase.eventbus.EventBusUtil;
import com.example.mvpbase.utils.check.CheckUtil;
import com.example.mvpbase.utils.constant.ConstantUtil;
import com.example.mvpbase.utils.system.SPUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.mvpbase.widget.MsgView;
import com.example.wanandroid.R;
import com.example.wanandroid.event.Type;
import com.example.wanandroid.ui.home.event.HomeEvent;
import com.example.wanandroid.ui.login.bean.LogInBean;
import com.example.wanandroid.ui.login.mvp.LogInPresenter;
import com.example.wanandroid.ui.login.mvp.LogInView;
import com.example.wanandroid.main.event.MainEvent;
import com.example.mvpbase.utils.ThemeColorUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/10
 * Time: 13:31
 */
@BindLayoutRes(R.layout.activity_log_in)
public class LogInActivity extends BaseInterfaceActivity<LogInPresenter> implements LogInView {

    @BindView(R.id.accountText)
    AppCompatEditText mAccountText;
    @BindView(R.id.passwordText)
    AppCompatEditText mPasswordText;
    @BindView(R.id.buttonLogin)
    MsgView mButtonLogin;
    @BindView(R.id.registerText)
    TextView mRegisterText;
    LoadingDialog loadingDialog;
    @Override
    public LogInPresenter initPresenter() {
        return new LogInPresenter(this);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void initView() {
        super.initView();
        softKeyboardAdaptive();
        setTitleBar(getResources().getString(R.string.login), ThemeColorUtil.getThemeColor(mContext));
        mButtonLogin.setBackgroundColor(ThemeColorUtil.getThemeColor(mContext));
        mRegisterText.setTextColor(ThemeColorUtil.getThemeColor(mContext));
        mButtonLogin.setOnClickListener(v -> {

            if (CheckUtil.isEmpty(getStringByUI(mAccountText))){
                ToastUtil.showShort("请输入注册账号");
            }else if (CheckUtil.isEmpty(getStringByUI(mPasswordText))){
                ToastUtil.showShort("请输入注册密码");
            }else {
                hideSoftKeyBoard();
                loadingDialog = LoadingDialog.newInstance();
                loadingDialog.show(getSupportFragmentManager());
                Map<String, Object> map = new HashMap<>(2);
                map.put("username", getStringByUI(mAccountText));
                map.put("password", getStringByUI(mPasswordText));
                getPresenter().logIn(map);
            }
        });
        mRegisterText.setOnClickListener(v -> {

        });
    }

    @Override
    public void logInSuc(LogInBean bean) {
        SPUtil.putByClass(ConstantUtil.USER, bean);
        EventBusUtil.post(new HomeEvent(Type.REFRESH_LIST));
        EventBusUtil.post(new MainEvent(Type.REFRESH));
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        finish();
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showShort(msg);
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
