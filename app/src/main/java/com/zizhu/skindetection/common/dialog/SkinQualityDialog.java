package com.zizhu.skindetection.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zizhu.skindetection.R;
import com.zizhu.skindetection.base.BaseApplication;

/**
 * Created by 1 on 2016/11/27.
 */
public class SkinQualityDialog extends Dialog {

    private View rootView;
    private TextView tv_skin_quality_dialog_share, tv_skin_quality_dialog_oil, tv_skin_quality_dialog_water, tv_skin_quality_dialog_own, tv_skin_quality_dialog_own_result;
    private ImageView iv_skin_quality_result;

    public SkinQualityDialog(Context context) {
        super(context, R.style.CommonDialog);

        rootView = getLayoutInflater().inflate(R.layout.layout_skin_quality_dialog, null);
        setContentView(rootView);

        tv_skin_quality_dialog_share = (TextView) rootView.findViewById(R.id.tv_skin_quality_dialog_share);
        tv_skin_quality_dialog_oil = (TextView) rootView.findViewById(R.id.tv_skin_quality_dialog_oil);
        tv_skin_quality_dialog_water = (TextView) rootView.findViewById(R.id.tv_skin_quality_dialog_water);
        tv_skin_quality_dialog_own = (TextView) rootView.findViewById(R.id.tv_skin_quality_dialog_own);
        tv_skin_quality_dialog_own_result = (TextView) rootView.findViewById(R.id.tv_skin_quality_dialog_own_result);
        iv_skin_quality_result = (ImageView) findViewById(R.id.iv_skin_quality_result);
        tv_skin_quality_dialog_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onShareListener != null){
                    onShareListener.share();
                }
            }
        });
        tv_skin_quality_dialog_water.setTypeface(BaseApplication.getInstance().getLantingheiVeryFace());
        tv_skin_quality_dialog_oil.setTypeface(BaseApplication.getInstance().getLantingheiVeryFace());
        tv_skin_quality_dialog_own.setTypeface(BaseApplication.getInstance().getLantingheiVeryFace());
    }

    public SkinQualityDialog setContent(int water, int oil, String object, boolean win, OnShareListener onShareListener){
        tv_skin_quality_dialog_water.setText((water < 10 ? "0" + water + "" : water + ""));
        tv_skin_quality_dialog_oil.setText((oil < 10 ? "0" + oil + "" : oil + ""));
        if(win){
            iv_skin_quality_result.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ball_win));
            tv_skin_quality_dialog_own.setText(Html.fromHtml("<font color='#2B2D39'>恭喜你！</font>"));
            tv_skin_quality_dialog_own_result.setText(Html.fromHtml("你战胜了你<b><font color='#2B2D39'>"+object+"</font></b>的肤质"));
        }else{
            iv_skin_quality_result.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ball_lose));
            tv_skin_quality_dialog_own.setText(Html.fromHtml("<font color='#2B2D39'>加油哦！</font>"));
            tv_skin_quality_dialog_own_result.setText(Html.fromHtml("您的肤质输给了你的<b><font color='#2B2D39'>"+object+"</font></b>"));
        }
        this.onShareListener = onShareListener;
        return this;
    }

    private OnShareListener onShareListener;

    public interface OnShareListener{
        void share();
    }

}
