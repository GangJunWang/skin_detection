package com.zizhu.skindetection.controller.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.shaojun.widget.superAdapter.SuperAdapter;
import com.shaojun.widget.superAdapter.internal.SuperViewHolder;
import com.zizhu.skindetection.R;
import com.zizhu.skindetection.base.BaseApplication;
import com.zizhu.skindetection.common.model.WaterOilResultModel;

import java.util.List;

/**
 * Created by 1 on 2016/12/21.
 */
public class WaterOilAdapter extends SuperAdapter<WaterOilResultModel> {

    public WaterOilAdapter(Context context, List<WaterOilResultModel> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, WaterOilResultModel item) {
        TextView tv_water_oil_type = holder.findViewById(R.id.tv_water_oil_type);
        TextView tv_water_oil_detail = holder.findViewById(R.id.tv_water_oil_detail);
        TextView tv_water_oil_solute = holder.findViewById(R.id.tv_water_oil_solute);
//            tv_water_oil_type.setTypeface(BaseApplication.getInstance().getLantingheiVeryFace());
        tv_water_oil_detail.setTypeface(BaseApplication.getInstance().getLantingheiFace());
        tv_water_oil_solute.setTypeface(BaseApplication.getInstance().getLantingheiFace());
        tv_water_oil_type.setText(Html.fromHtml("<b>"+item.question + "</b>"));
        tv_water_oil_detail.setText(item.detail + "");
        if(!TextUtils.isEmpty(item.solute)) {
            tv_water_oil_solute.setVisibility(View.VISIBLE);
            tv_water_oil_solute.setText(Html.fromHtml("<b><font color='#3a3a3a'>护肤建议：</font></b>" + item.solute));
        }else{
            tv_water_oil_solute.setVisibility(View.GONE);
        }
    }
}
