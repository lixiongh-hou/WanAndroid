package com.example.mvpbase.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mvpbase.R;
import com.example.mvpbase.annotation.BindEventBus;
import com.example.mvpbase.annotation.BindLayoutResUtil;
import com.example.mvpbase.base.delegate.DelegateImpl;
import com.example.mvpbase.base.delegate.IDelegatePublic;
import com.example.mvpbase.eventbus.EventBusUtil;
import com.example.mvpbase.loading.NetworkAnomalyCallBack;
import com.example.mvpbase.networks.NetWorkUtils;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 10:59
 * Fragment简单基类
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 初始化控件
     */
    public abstract void initView();

    IDelegatePublic iDelegate = new DelegateImpl();
    /**
     * 上下文对象
     */
    protected Context mContext;
    public Activity mActivity;
    /**
     * 根视图
     */
    protected View rootView;
    /**
     * 注解View
     */
    private Unbinder mUnbinder;

    protected LoadService<?> loadService;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 上下文对象
        mContext = context;
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            // 获取布局ID
            rootView = inflater.inflate(BindLayoutResUtil.getLayoutId(this), container, false);
            // 注解View
            mUnbinder = ButterKnife.bind(this, rootView);
            loadService = LoadSir.getDefault().register(rootView,
                    (Callback.OnReloadListener) v -> {
                        if (loadService.getCurrentCallback() == NetworkAnomalyCallBack.class) {
                            reLoad(v.findViewById(R.id.reload));
                        }
                    });
            loadService.showSuccess();
        } else {
            ViewGroup group = (ViewGroup) rootView.getParent();
            if (null != group) {
                group.removeView(rootView);
            }
        }
        return loadService.getLoadLayout();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化控件
        this.initView();
        // EventBus
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtil.register(this);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void reLoad(View view) {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 上下文对象
        mContext = null;
        // 注解View
        if (null != mUnbinder) {
            mUnbinder.unbind();
        }
        // 清除Handler
        iDelegate.clearHandler();
        // EventBus
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtil.unregister(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    // ---------------------------------------------------------------------------------------------
    // 提供给子类调用的方法

    public Handler getHandler(Handler.Callback callback) {
        return iDelegate.getHandler(callback);
    }

    /**
     * 跳转Activity
     */
    public void gotoActivity(Class<? extends Activity> cls, @Nullable Bundle mBundle, int requestCode) {
        iDelegate.gotoActivity(getActivity(), cls, mBundle, requestCode);
    }

    public void gotoActivity(Class<? extends Activity> cls, @Nullable Bundle mBundle) {
        iDelegate.gotoActivity(getActivity(), cls, mBundle);
    }

    public void gotoActivity(Class<? extends Activity> cls, int requestCode) {
        iDelegate.gotoActivity(getActivity(), cls, requestCode);
    }

    public void gotoActivity(Class<? extends Activity> cls) {
        iDelegate.gotoActivity(getActivity(), cls);
    }

    /**
     * 找控件
     */
    protected View findViewById(@IdRes int viewId) {
        if (null != rootView) {
            return rootView.findViewById(viewId);
        }
        return null;
    }

    public void forward(Fragment fragment, boolean hide) {
        forward(getId(), fragment, null, hide);
    }

    public void forward(int viewId, Fragment fragment, String name, boolean hide) {
        FragmentTransaction trans = getChildFragmentManager().beginTransaction();
        trans.setCustomAnimations(R.anim.right_push_in, R.anim.left_push_out,
                R.anim.left_push_in, R.anim.right_push_out);
        if (hide) {
            trans.hide(this);
            trans.add(viewId, fragment);
        } else {
            trans.replace(viewId, fragment);
        }

        trans.addToBackStack(name);
        trans.commitAllowingStateLoss();
    }

    public void backward() {
        getChildFragmentManager().popBackStack();
    }


}
