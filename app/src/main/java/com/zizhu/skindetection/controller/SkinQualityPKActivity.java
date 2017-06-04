package com.zizhu.skindetection.controller;

import android.animation.ValueAnimator;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.shaojun.widget.image.CircleProgressBar;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.GetMessageFromWX;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.zizhu.skindetection.R;
import com.zizhu.skindetection.base.BaseApplication;
import com.zizhu.skindetection.base.activity.BaseActivity;
import com.zizhu.skindetection.common.dialog.SkinQualityDialog;
import com.zizhu.skindetection.common.model.EventModel;
import com.zizhu.skindetection.common.model.RecordModel;
import com.zizhu.skindetection.common.share.WechatShareManager;
import com.zizhu.skindetection.common.utils.DateUtils;
import com.zizhu.skindetection.common.utils.DisplayUtil;
import com.zizhu.skindetection.common.utils.ScreenUtil;
import com.zizhu.skindetection.common.utils.StorageUtil;
import com.zizhu.skindetection.common.utils.ToastUtils;
import com.zizhu.skindetection.common.widget.dialog.CustomDialogHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 肤质测试PK
 */
public class SkinQualityPKActivity extends BaseActivity implements IWXAPIEventHandler {

    CircleProgressBar cp_skin_quality_pk_own;
    CircleProgressBar cp_skin_quality_pk_other;
    private View rl_skin_quality_boy;
    private View rpb_skin_quality_boy_check;
    private View rl_skin_quality_girl;
    private View rpb_skin_quality_girl_check;
    private TextView tv_skin_quality_boy_water;
    private TextView tv_skin_quality_boy_oil;
    private TextView tv_skin_quality_girl_water;
    private TextView tv_skin_quality_girl_oil;
    private View tv_skin_quality_result;
    private TextView tv_skin_quality_own_water;
    private TextView tv_skin_quality_own_oil;
    private TextView tv_skin_quality_object;
    private TextView tv_skin_quality_object_water;
    private TextView tv_skin_quality_object_oil;

    private int check = -1;
    private int objectCheck = -1;
    private boolean loading = false;
    private List<Integer> positions = new ArrayList<Integer>();

    private TextView tv_01, tv_02;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_skin_quality_pk;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        titleBarView.initCenterTitle("肤质测试PK");
        titleBarView.rootLayout.setBackgroundColor(getResources().getColor(R.color.gray_20));
        titleBarView.leftIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.tab_btn_return));
        titleBarView.centerTitle.setTextColor(getResources().getColor(R.color.project_color));
        cp_skin_quality_pk_own = (CircleProgressBar) findViewById(R.id.cp_skin_quality_pk_own);
        cp_skin_quality_pk_other = (CircleProgressBar) findViewById(R.id.cp_skin_quality_pk_other);
        rl_skin_quality_boy = findViewById(R.id.rl_skin_quality_boy);
        rpb_skin_quality_boy_check = findViewById(R.id.rpb_skin_quality_boy_check);
        rl_skin_quality_girl = findViewById(R.id.rl_skin_quality_girl);
        rpb_skin_quality_girl_check = findViewById(R.id.rpb_skin_quality_girl_check);
        tv_skin_quality_boy_water = (TextView) findViewById(R.id.tv_skin_quality_boy_water);
        tv_skin_quality_boy_oil = (TextView) findViewById(R.id.tv_skin_quality_boy_oil);
        tv_skin_quality_girl_water = (TextView) findViewById(R.id.tv_skin_quality_girl_water);
        tv_skin_quality_girl_oil = (TextView) findViewById(R.id.tv_skin_quality_girl_oil);
        tv_skin_quality_result = findViewById(R.id.tv_skin_quality_result);

        tv_skin_quality_own_water = (TextView) findViewById(R.id.tv_skin_quality_own_water);
        tv_skin_quality_own_oil = (TextView) findViewById(R.id.tv_skin_quality_own_oil);
        tv_skin_quality_object = (TextView) findViewById(R.id.tv_skin_quality_object);
        tv_skin_quality_object_water = (TextView) findViewById(R.id.tv_skin_quality_object_water);
        tv_skin_quality_object_oil = (TextView) findViewById(R.id.tv_skin_quality_object_oil);
        findTextView((ViewGroup) getWindow().getDecorView());
        tv_skin_quality_boy_water.setTypeface(BaseApplication.getInstance().getLantingheiVeryFace());
        tv_skin_quality_boy_oil.setTypeface(BaseApplication.getInstance().getLantingheiVeryFace());
        tv_skin_quality_girl_water.setTypeface(BaseApplication.getInstance().getLantingheiVeryFace());
        tv_skin_quality_girl_oil.setTypeface(BaseApplication.getInstance().getLantingheiVeryFace());
        ((TextView)findViewById(R.id.tv_vs)).setTypeface(BaseApplication.getInstance().getLantingheiVeryFace());
        ScreenUtil.initStatusBar(this, R.color.gray_20);

        tv_01 = (TextView) findViewById(R.id.tv_01);
        tv_02 = (TextView) findViewById(R.id.tv_02);
    }

    private void findTextView(ViewGroup viewGroup){
        for(int i = 0; i < viewGroup.getChildCount(); i++){
            if(viewGroup.getChildAt(i) instanceof ViewGroup){
                findTextView((ViewGroup) viewGroup.getChildAt(i));
            }
            if((viewGroup.getChildAt(i) instanceof TextView)){
                TextView textView = (TextView) viewGroup.getChildAt(i);
                textView.setTypeface(BaseApplication.getInstance().getLantingheiFace());
            }
        }
    }

    @Override
    protected void bindEven() {
        rl_skin_quality_boy.setOnClickListener(this);
        rl_skin_quality_girl.setOnClickListener(this);
        tv_skin_quality_result.setOnClickListener(this);
        ((RadioGroup)findViewById(R.id.rg_skin_quality_choose)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int position) {
                switch (position){
                    case R.id.rbskin_quality_1:
                        objectCheck = 1;
                        tv_skin_quality_object.setText("闺蜜：");
                        break;
                    case R.id.rbskin_quality_2:
                        objectCheck = 2;
                        tv_skin_quality_object.setText("男朋友：");
                        break;
                    case R.id.rbskin_quality_3:
                        objectCheck = 3;
                        tv_skin_quality_object.setText("女朋友：");
                        break;
                    case R.id.rbskin_quality_4:
                        objectCheck = 4;
                        tv_skin_quality_object.setText("宝宝：");
                        break;
                }
            }
        });
        EventBus.getDefault().register(this);
    }

    private WechatShareManager mShareManager;

    @Override
    protected void setView() {
        mShareManager = WechatShareManager.getInstance(this);
    }

    ValueAnimator loadAnimator = ValueAnimator.ofInt(1, 101); // 加载
    ExecutorService service = Executors.newSingleThreadExecutor();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(EventModel eventModel){
        if(eventModel != null){
            if("read".equals(eventModel.type)){
                if(check == -1){
                    return;
                }
                if(check == 2){
                    if(objectCheck == -1){
                        return;
                    }
                }
                if(!positions.contains(check)){
                    positions.add(check);
                }
                byte[] array = (byte[]) eventModel.eventBus;
                byte[] value = {array[2], array[3], array[4], array[5]};
                if(value.length == 4) {
                    final int v2 = value[1] & 0xFF;
                    final int v3 = value[2] & 0xFF;
                    if(v2 == 0){
                        CustomDialogHelper.OneButtonDialog(this, "提示", "您测试的数据不对哦，请重新检测", "关闭", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        return;
                    }
                    loadAnimator.setRepeatCount(0);
                    StorageUtil.addRecordMode(new RecordModel(DateUtils.getStringDateAndTimeFromDate(new Date()), v2, v3));
                    ValueAnimator animator = ValueAnimator.ofInt(1, Math.max(v2, v3));
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int progress = (int) animation.getAnimatedValue();
                            if (progress <= v2 || progress == 1) {
                                if(check == 1) {
                                    tv_skin_quality_boy_water.setText(progress < 10 ? "0" + progress + "" : progress + "");
                                    tv_skin_quality_own_water.setText("水分" + (progress < 10 ? "0" + progress + "%" : progress + "%"));
                                    if(progress <= 35) {
                                        tv_skin_quality_own_water.setText(Html.fromHtml(tv_skin_quality_own_water.getText() + "&nbsp;&nbsp;严重缺水"));
                                    } else if(progress <= 40){
                                        tv_skin_quality_own_water.setText(Html.fromHtml(tv_skin_quality_own_water.getText() + "&nbsp;&nbsp;稍微缺水"));
                                    } else if(progress <= 60){
                                        tv_skin_quality_own_water.setText(Html.fromHtml(tv_skin_quality_own_water.getText() + "&nbsp;&nbsp;正常"));
                                    } else if(progress > 60){
                                        tv_skin_quality_own_water.setText(Html.fromHtml(tv_skin_quality_own_water.getText() + "&nbsp;&nbsp;水分水润"));
                                    }
                                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv_01.getLayoutParams();
                                    if(progress < 100) {
                                        layoutParams.leftMargin = DisplayUtil.dip2px(SkinQualityPKActivity.this, 1);
                                    } else{
                                        layoutParams.leftMargin = DisplayUtil.dip2px(SkinQualityPKActivity.this, 14);
                                    }
                                    tv_01.setLayoutParams(layoutParams);
                                }else{
                                    tv_skin_quality_girl_water.setText(progress < 10 ? "0" + progress + "" : progress + "");
                                    tv_skin_quality_object_water.setText("水分" + (progress < 10 ? "0" + progress + "%" : progress + "%"));
                                    if(progress <= 35) {
                                        tv_skin_quality_object_water.setText(Html.fromHtml(tv_skin_quality_object_water.getText() + "&nbsp;&nbsp;严重缺水"));
                                    } else if(progress <= 40){
                                        tv_skin_quality_object_water.setText(Html.fromHtml(tv_skin_quality_object_water.getText() + "&nbsp;&nbsp;稍微缺水"));
                                    } else if(progress <= 60){
                                        tv_skin_quality_object_water.setText(Html.fromHtml(tv_skin_quality_object_water.getText() + "&nbsp;&nbsp;正常"));
                                    } else if(progress > 60){
                                        tv_skin_quality_object_water.setText(Html.fromHtml(tv_skin_quality_object_water.getText() + "&nbsp;&nbsp;水分水润"));
                                    }
                                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv_02.getLayoutParams();
                                    if(progress < 100) {

                                        layoutParams.leftMargin = DisplayUtil.dip2px(SkinQualityPKActivity.this, 1);
                                    } else{
                                        layoutParams.leftMargin = DisplayUtil.dip2px(SkinQualityPKActivity.this, 14);
                                    }
                                    tv_02.setLayoutParams(layoutParams);
                                }
                            }
                            if (progress <= v3 || progress == 1) {
                                if(check == 1) {
                                    tv_skin_quality_boy_oil.setText(progress < 10 ? "0" + progress + "" : progress + "");
                                    tv_skin_quality_own_oil.setText("油分" + (progress < 10 ? "0" + progress + "%" : progress + "%"));
                                    if(progress <= 16) {
                                        tv_skin_quality_own_oil.setText(Html.fromHtml(tv_skin_quality_own_oil.getText() + "&nbsp;&nbsp;缺油"));
                                    } else if(progress <= 30){
                                        tv_skin_quality_own_oil.setText(Html.fromHtml(tv_skin_quality_own_oil.getText() + "&nbsp;&nbsp;正常"));
                                    } else if(progress > 30){
                                        tv_skin_quality_own_oil.setText(Html.fromHtml(tv_skin_quality_own_oil.getText() + "&nbsp;&nbsp;偏油"));
                                    }
                                }else{
                                    tv_skin_quality_girl_oil.setText(progress < 10 ? "0" + progress + "" : progress + "");
                                    tv_skin_quality_object_oil.setText("油分" + (progress < 10 ? "0" + progress + "%" : progress + "%"));
                                    if(progress <= 16) {
                                        tv_skin_quality_object_oil.setText(Html.fromHtml(tv_skin_quality_object_oil.getText() + "&nbsp;&nbsp;缺油"));
                                    } else if(progress <= 30){
                                        tv_skin_quality_object_oil.setText(Html.fromHtml(tv_skin_quality_object_oil.getText() + "&nbsp;&nbsp;正常"));
                                    } else if(progress > 30){
                                        tv_skin_quality_object_oil.setText(Html.fromHtml(tv_skin_quality_object_oil.getText() + "&nbsp;&nbsp;偏油"));
                                    }
                                }
                            }
                            if(progress == Math.max(v2, v3)) {
                                loading = false;
                            }
                        }
                    });
                    animator.setDuration(1500);
                    animator.start();
                }
//                ToastUtils.show("测试完成，请查看测试结果");
            }else if("start".equals(eventModel.type)){
                if(check == -1){
                    return;
                }
                if(check == 2){
                    if(objectCheck == -1){
                        ToastUtils.show("请选择VS对象");
                        return;
                    }
                }
                loading = true;
                loadAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int progress = (int) animation.getAnimatedValue();
                        if(check == 1) {
                            cp_skin_quality_pk_own.setProgress(progress);
                        }else{
                            cp_skin_quality_pk_other.setProgress(progress);
                        }
                    }
                });
                loadAnimator.setRepeatCount(-1);
                loadAnimator.setDuration(1500);
                loadAnimator.start();
                ToastUtils.show("请用触头轻按皮肤部位5秒");
                service.execute(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(8000);
                            loadAnimator.setRepeatCount(0);
                            loading = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }));
            }else if("error".equals(eventModel.type)){
                ToastUtils.show("检测失败，请重新检测");
                loadAnimator.setRepeatCount(0);
                loading = false;
            }
        }
    }

    @Override
    protected void onClickView(View view) {
        switch (view.getId()){
            case R.id.rl_skin_quality_boy:{
                if(!loading) {
                    if(objectCheck == -1){
                        ToastUtils.show("请选择VS对象");
                        return;
                    }
                    check = 2;
                    rpb_skin_quality_boy_check.setVisibility(View.VISIBLE);
                    rpb_skin_quality_girl_check.setVisibility(View.GONE);
                    ToastUtils.show("开始测试吧");
                }
            }
            break;
            case R.id.rl_skin_quality_girl:{
                if(!loading) {
                    check = 1;
                    rpb_skin_quality_boy_check.setVisibility(View.GONE);
                    rpb_skin_quality_girl_check.setVisibility(View.VISIBLE);
                    ToastUtils.show("开始测试吧");
                }
            }
            break;
            case R.id.tv_skin_quality_result:{
                if(positions.size() < 2){
                    return;
                }
                int water = Integer.valueOf(tv_skin_quality_boy_water.getText().toString());
                int oil = Integer.valueOf(tv_skin_quality_boy_oil.getText().toString());
                int girlWater = Integer.valueOf(tv_skin_quality_girl_water.getText().toString());
                new SkinQualityDialog(this).setContent(water, oil, tv_skin_quality_object.getText().toString().substring(0, tv_skin_quality_object.getText().toString().length() - 1),
                        water >= girlWater, new SkinQualityDialog.OnShareListener() {
                    @Override
                    public void share() {
                        WechatShareManager.ShareContentPicture mShareContentText = (WechatShareManager.ShareContentPicture) mShareManager.getShareContentPicture(
                                BitmapFactory.decodeResource(getResources(), R.mipmap.logo), ("M兔喊你来pk啦，快邀请闺蜜和亲友来M兔参加肤质检" +
                                        "测吧，看看谁的皮肤更水润哦！"));
                        mShareManager.shareByWebchat(mShareContentText, WechatShareManager.WECHAT_SHARE_WAY_PICTURE);
                    }
                }).show();
            }
            break;
        }
    }

    private String getTransaction() {
        final GetMessageFromWX.Req req = new GetMessageFromWX.Req();
        return req.transaction;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

    }
}
