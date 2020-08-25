package com.example.wanandroid.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;

import androidx.annotation.RequiresApi;

import com.example.mvpbase.utils.check.CheckUtil;
import com.example.wanandroid.R;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/14
 * Time: 11:01
 */
public class WeChatRightAdapter extends CommonAdapter<DatasBean> {

    public WeChatRightAdapter(Context context, List<DatasBean> data) {
        super(context, R.layout.item_article_rv, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void convert(ViewHolder holder, DatasBean item, int position) {
        holder.setText(R.id.articleAuthor, handleAuthor(item))
                .setText(R.id.articleTime, item.getNiceDate())
                .setText(R.id.articleTitle, handleTitle(item))
                .setText(R.id.articleType, handleCategory(item))
                .setVisible(R.id.articleNew, item.isFresh())
                .setVisible(R.id.articleStatus, item.isTop())
                .setImageResource(R.id.articleCollect, isCollect(item));
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

    private int isCollect(DatasBean item) {
        return item.isCollect() ? R.drawable.collect_selector_icon : R.drawable.uncollect_selector_icon;
    }

}
