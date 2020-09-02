package com.example.wanandroid.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpbase.utils.RvUtil;
import com.example.mvpbase.utils.ThemeColorUtil;
import com.example.wanandroid.R;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.example.wanandroid.ui.navigation.bean.NavigationBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/9/2
 * Time: 9:34
 */
public class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.BaseViewHolder> {
    private List<NavigationBean> mList;
    private Context context;
    public ViewPager2Adapter(Context context, List<NavigationBean> mList){
        this.context = context;
        this.mList = mList;
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_pager2, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        NavigationBean bean = mList.get(position);
        holder.tvPage.setText(bean.getName());
        CommonAdapter<DatasBean> mAdapter = new CommonAdapter<DatasBean>(context, R.layout.item_search_rv, bean.getArticles()) {
            @Override
            protected void convert(ViewHolder holder, DatasBean item, int position) {
                TextView msgView = holder.getView(R.id.itemSearchName);
                msgView.setBackground(getGradientDrawable());
                msgView.setText(item.getTitle());
            }
        };
        holder.recyclerView.setLayoutManager(RvUtil.getLayoutManager(context));
        holder.recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class  BaseViewHolder extends RecyclerView.ViewHolder{
        TextView tvPage;
        RecyclerView recyclerView;
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPage =itemView.findViewById(R.id.viewPager2Text);
            recyclerView = itemView.findViewById(R.id.viewPager2Rv);
        }
    }
    /**
     * 动态添加按下状态
     *
     * @return
     */
    private StateListDrawable getGradientDrawable() {
        GradientDrawable pressed = new GradientDrawable();
        pressed.setColor(ThemeColorUtil.getThemeColor(context));
        pressed.setShape(GradientDrawable.RECTANGLE);
        pressed.setCornerRadius(50);
        GradientDrawable normal = new GradientDrawable();
        normal.setColor(ContextCompat.getColor(context, R.color.activity_bg));
        normal.setShape(GradientDrawable.RECTANGLE);
        normal.setCornerRadius(50);
        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_pressed}, pressed);
        bg.addState(new int[]{}, normal);
        return bg;
    }
}
