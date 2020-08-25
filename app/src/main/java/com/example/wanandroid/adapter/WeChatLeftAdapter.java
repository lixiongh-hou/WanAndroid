package com.example.wanandroid.adapter;

import android.content.Context;
import android.graphics.Typeface;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.utils.ThemeColorUtil;
import com.example.wanandroid.wechat.bean.WeChatNameBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/14
 * Time: 10:50
 */
public class WeChatLeftAdapter extends CommonAdapter<WeChatNameBean> {
    private int checked = 0;

    public WeChatLeftAdapter(Context context, List<WeChatNameBean> data) {
        super(context, R.layout.item_we_chat_left_rv, data);
    }

    @Override
    protected void convert(ViewHolder holder, WeChatNameBean item, int position) {
        holder.setText(R.id.weChatLeftName, item.getName())
                .setTag(R.id.weChatLeftMain, item.getName());
        if (holder.getAdapterPosition() == checked){
            holder.setBackgroundColor(R.id.weChatLeftMain, ContextCompat.getColor(mContext, R.color.white))
                    .setTextColor(R.id.weChatLeftName, ThemeColorUtil.getThemeColor(mContext))
                    .setTypeface(Typeface.DEFAULT_BOLD, R.id.weChatLeftName);
        }else {
            holder.setBackgroundColor(R.id.weChatLeftMain, ContextCompat.getColor(mContext, R.color.color_F8))
                    .setTextColorRes(R.id.weChatLeftName, R.color.color_66)
                    .setTypeface(Typeface.DEFAULT, R.id.weChatLeftName);
        }
    }

    public void setChecked(int checked){
        this.checked = checked;
        notifyDataSetChanged();
    }
    public int getChecked(){
        return checked;
    }
}
