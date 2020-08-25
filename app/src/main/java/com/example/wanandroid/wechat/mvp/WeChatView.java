package com.example.wanandroid.wechat.mvp;

import com.example.mvpbase.mvp.BaseView;
import com.example.wanandroid.wechat.bean.WeChatListBean;
import com.example.wanandroid.wechat.bean.WeChatNameBean;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/14
 * Time: 10:00
 */
public interface WeChatView extends BaseView {


    /**
     * 公众号名称
     * @param bean
     */
    void getWeChatNameSuc(List<WeChatNameBean> bean);

    /**
     * 公众号名称对应的数据
     * @param bean
     */
    void getWeChatListSuc(WeChatListBean bean);
}
