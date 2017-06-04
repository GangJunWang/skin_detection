package com.zizhu.skindetection.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shaojun.widget.superAdapter.SuperAdapter;
import com.shaojun.widget.superAdapter.internal.SuperViewHolder;
import com.zizhu.skindetection.R;
import com.zizhu.skindetection.base.BaseApplication;
import com.zizhu.skindetection.common.model.RecordModel;
import com.zizhu.skindetection.common.utils.DateUtils;
import com.zizhu.skindetection.common.utils.DisplayUtil;

import java.util.List;

/**
 * 记录列表
 */
public class RecordAdapter extends SuperAdapter<RecordModel> {

    public RecordAdapter(Context context, List<RecordModel> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, RecordModel item) {
        View rl_record_list_title = holder.findViewById(R.id.rl_record_list_title);
        View v_record_left_progress = holder.findViewById(R.id.v_record_left_progress);
        View v_record_right_progress = holder.findViewById(R.id.v_record_right_progress);
        TextView tv_record_list_date = holder.findViewById(R.id.tv_record_list_date);
        TextView tv_record_item_left = holder.findViewById(R.id.tv_record_item_left);
        TextView tv_record_item_right = holder.findViewById(R.id.tv_record_item_right);
        TextView tv_record_list_time = holder.findViewById(R.id.tv_record_list_time);
        tv_record_list_date.setTypeface(BaseApplication.getInstance().getLantingheiFace());
        tv_record_list_time.setTypeface(BaseApplication.getInstance().getLantingheiFace());
        tv_record_item_left.setTypeface(BaseApplication.getInstance().getLantingheiFace());
        tv_record_item_right.setTypeface(BaseApplication.getInstance().getLantingheiFace());
        setProgress(item.water, v_record_left_progress, tv_record_item_left, 1);
        setProgress(item.oil, v_record_right_progress, tv_record_item_right, 2);
        boolean result = DateUtils.isToday(DateUtils.getDateFromString(item.time));
        if(!result){
            tv_record_list_date.setText(DateUtils.formatDisplayTime(item.time));
        }else{
            tv_record_list_date.setText("今天");
        }
        tv_record_list_time.setText(DateUtils.getHourTime(item.time));
        if(item.visible){
            rl_record_list_title.setVisibility(View.VISIBLE);
        }else{
            rl_record_list_title.setVisibility(View.GONE);
        }
    }

    private void setProgress(final int progress, final View view, final TextView textView, final int type){
        view.post(new Runnable() {
            @Override
            public void run() {
                float width = ((ViewGroup)view.getParent()).getChildAt(0).getWidth();
                int value = (int) (width / 100f * progress);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.width = value;
                view.setLayoutParams(params);
                textView.setText(progress < 10 ? "0" + progress + "%" : progress + "%");
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
                int v = 0;
                if(progress >= 30){
                    v = value - DisplayUtil.dip2px(getContext(), 25);
                }
                if(type == 2) {
                    layoutParams.leftMargin = v;
                }else{
                    layoutParams.rightMargin = v;
                }
                textView.setLayoutParams(layoutParams);
            }
        });
    }

}
