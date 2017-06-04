package com.zizhu.skindetection.controller;

import android.animation.ValueAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zizhu.skindetection.R;
import com.zizhu.skindetection.base.BaseApplication;
import com.zizhu.skindetection.base.activity.BaseActivity;
import com.zizhu.skindetection.common.model.EventModel;
import com.zizhu.skindetection.common.model.RecordModel;
import com.zizhu.skindetection.common.model.WaterOilModel;
import com.zizhu.skindetection.common.model.WaterOilResultModel;
import com.zizhu.skindetection.common.utils.DateUtils;
import com.zizhu.skindetection.common.utils.ScreenUtil;
import com.zizhu.skindetection.common.utils.StorageUtil;
import com.zizhu.skindetection.common.utils.ToastUtils;
import com.zizhu.skindetection.common.widget.dialog.CustomDialogHelper;
import com.zizhu.skindetection.common.widget.image.RoundProgressBar;
import com.zizhu.skindetection.controller.adapter.WaterOilAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 水油测试
 */
public class WaterOilActivity extends BaseActivity {

    private RoundProgressBar rpb_water_oil_water, rpb_water_oil_water_header_round, rpb_water_oil_oil_header_round;
    private RoundProgressBar rpb_water_oil_oil;
    private RoundProgressBar rpb_water_oil_water_t;
    private RoundProgressBar rpb_water_oil_water_hand;
    private RoundProgressBar rpb_water_oil_water_eye;
    private RoundProgressBar rpb_water_oil_water_u;
    private RoundProgressBar rpb_water_oil_oil_t;
    private RoundProgressBar rpb_water_oil_oil_eye;
    private RoundProgressBar rpb_water_oil_oil_hand;
    private RoundProgressBar rpb_water_oil_oil_u;
    private TextView tv_water_oil_oil_value;
    private TextView tv_water_oil_water_value;
    private TextView tv_water_oil_water_t;
    private TextView tv_water_oil_water_hand;
    private TextView tv_water_oil_water_eye;
    private TextView tv_water_oil_water_u;
    private TextView tv_water_oil_oil_t;
    private TextView tv_water_oil_oil_eye;
    private TextView tv_water_oil_oil_hand;
    private TextView tv_water_oil_oil_u;

    private TextView tv_water_oil_t_water_result;
    private TextView tv_water_oil_t_oil_result;
    private TextView tv_water_oil_u_water_result;
    private TextView tv_water_oil_u_oil_result;
    private TextView tv_water_oil_eye_water_result;
    private TextView tv_water_oil_eye_oil_result;
    private TextView tv_water_oil_hand_water_result;
    private TextView tv_water_oil_hand_oil_result;

    private TextView tv_water_oil_water_result;
    private TextView tv_water_oil_oil_result;

    private View ll_water_oil_t;
    private RecyclerView rv_water_oil_t;
    private WaterOilAdapter waterOilAdapterT;

    private View ll_water_oil_u;
    private RecyclerView rv_water_oil_u;
    private WaterOilAdapter waterOilAdapterU;

    private View ll_water_oil_hand;
    private RecyclerView rv_water_oil_hand;
    private WaterOilAdapter waterOilAdapterHand;

    private View ll_water_oil_eye;
    private RecyclerView rv_water_oil_eye;
    private WaterOilAdapter waterOilAdapterEye;

    private ImageView iv_01, iv_02, iv_03, iv_04, iv_05, iv_06;

    int type = -1;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_water_oil;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        ScreenUtil.initStatusBar(this, R.color.project_color);
        titleBarView.initCenterTitle("水油检测");
        rpb_water_oil_water = (RoundProgressBar) findViewById(R.id.rpb_water_oil_water);
        rpb_water_oil_water_header_round = (RoundProgressBar) findViewById(R.id.rpb_water_oil_water_header_round);
        rpb_water_oil_oil_header_round = (RoundProgressBar) findViewById(R.id.rpb_water_oil_oil_header_round);
        rpb_water_oil_oil = (RoundProgressBar) findViewById(R.id.rpb_water_oil_oil);
        rpb_water_oil_oil = (RoundProgressBar) findViewById(R.id.rpb_water_oil_oil);
        rpb_water_oil_water_t = (RoundProgressBar) findViewById(R.id.rpb_water_oil_water_t);
        rpb_water_oil_water_hand = (RoundProgressBar) findViewById(R.id.rpb_water_oil_water_hand);
        rpb_water_oil_water_eye = (RoundProgressBar) findViewById(R.id.rpb_water_oil_water_eye);
        rpb_water_oil_water_u = (RoundProgressBar) findViewById(R.id.rpb_water_oil_water_u);
        rpb_water_oil_oil_t = (RoundProgressBar) findViewById(R.id.rpb_water_oil_oil_t);
        rpb_water_oil_oil_eye = (RoundProgressBar) findViewById(R.id.rpb_water_oil_oil_eye);
        rpb_water_oil_oil_hand = (RoundProgressBar) findViewById(R.id.rpb_water_oil_oil_hand);
        rpb_water_oil_oil_u = (RoundProgressBar) findViewById(R.id.rpb_water_oil_oil_u);

        tv_water_oil_oil_value = (TextView) findViewById(R.id.tv_water_oil_oil_value);
        tv_water_oil_water_value = (TextView) findViewById(R.id.tv_water_oil_water_value);
        tv_water_oil_water_t = (TextView) findViewById(R.id.tv_water_oil_water_t);
        tv_water_oil_water_hand = (TextView) findViewById(R.id.tv_water_oil_water_hand);
        tv_water_oil_water_eye = (TextView) findViewById(R.id.tv_water_oil_water_eye);
        tv_water_oil_water_u = (TextView) findViewById(R.id.tv_water_oil_water_u);
        tv_water_oil_oil_t = (TextView) findViewById(R.id.tv_water_oil_oil_t);
        tv_water_oil_oil_eye = (TextView) findViewById(R.id.tv_water_oil_oil_eye);
        tv_water_oil_oil_hand = (TextView) findViewById(R.id.tv_water_oil_oil_hand);
        tv_water_oil_oil_u = (TextView) findViewById(R.id.tv_water_oil_oil_u);

        tv_water_oil_t_water_result = (TextView) findViewById(R.id.tv_water_oil_t_water_result);
        tv_water_oil_t_oil_result = (TextView) findViewById(R.id.tv_water_oil_t_oil_result);
        tv_water_oil_u_water_result = (TextView) findViewById(R.id.tv_water_oil_u_water_result);
        tv_water_oil_u_oil_result = (TextView) findViewById(R.id.tv_water_oil_u_oil_result);
        tv_water_oil_eye_water_result = (TextView) findViewById(R.id.tv_water_oil_eye_water_result);
        tv_water_oil_eye_oil_result = (TextView) findViewById(R.id.tv_water_oil_eye_oil_result);
        tv_water_oil_hand_water_result = (TextView) findViewById(R.id.tv_water_oil_hand_water_result);
        tv_water_oil_hand_oil_result = (TextView) findViewById(R.id.tv_water_oil_hand_oil_result);

        tv_water_oil_water_result = (TextView) findViewById(R.id.tv_water_oil_water_result);
        tv_water_oil_oil_result = (TextView) findViewById(R.id.tv_water_oil_oil_result);

        ll_water_oil_t = findViewById(R.id.ll_water_oil_t);
        rv_water_oil_t = (RecyclerView) findViewById(R.id.rv_water_oil_t);
        rv_water_oil_t.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        waterOilAdapterT = new WaterOilAdapter(this, new ArrayList<WaterOilResultModel>(), R.layout.layout_water_oil_item);
        rv_water_oil_t.setAdapter(waterOilAdapterT);

        ll_water_oil_u = findViewById(R.id.ll_water_oil_u);
        rv_water_oil_u = (RecyclerView) findViewById(R.id.rv_water_oil_u);
        rv_water_oil_u.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        waterOilAdapterU = new WaterOilAdapter(this, new ArrayList<WaterOilResultModel>(), R.layout.layout_water_oil_item);
        rv_water_oil_u.setAdapter(waterOilAdapterU);

        ll_water_oil_hand = findViewById(R.id.ll_water_oil_hand);
        rv_water_oil_hand = (RecyclerView) findViewById(R.id.rv_water_oil_hand);
        rv_water_oil_hand.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        waterOilAdapterHand = new WaterOilAdapter(this, new ArrayList<WaterOilResultModel>(), R.layout.layout_water_oil_item);
        rv_water_oil_hand.setAdapter(waterOilAdapterHand);

        ll_water_oil_eye = findViewById(R.id.ll_water_oil_eye);
        rv_water_oil_eye = (RecyclerView) findViewById(R.id.rv_water_oil_eye);
        rv_water_oil_eye.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        waterOilAdapterEye = new WaterOilAdapter(this, new ArrayList<WaterOilResultModel>(), R.layout.layout_water_oil_item);
        rv_water_oil_eye.setAdapter(waterOilAdapterEye);

        iv_01 = (ImageView) findViewById(R.id.iv_01);
        iv_02 = (ImageView) findViewById(R.id.iv_02);
        iv_03 = (ImageView) findViewById(R.id.iv_03);
        iv_04 = (ImageView) findViewById(R.id.iv_04);
        iv_05 = (ImageView) findViewById(R.id.iv_05);
        iv_06 = (ImageView) findViewById(R.id.iv_06);
        EventBus.getDefault().register(this);
        findTextView((ViewGroup) getWindow().getDecorView());
    }

    private void findTextView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                findTextView((ViewGroup) viewGroup.getChildAt(i));
            }
            if ((viewGroup.getChildAt(i) instanceof TextView)) {
                TextView textView = (TextView) viewGroup.getChildAt(i);
                textView.setTypeface(BaseApplication.getInstance().getLantingheiFace());
            }
        }
    }

    @Override
    protected void bindEven() {
        rpb_water_oil_water_t.setOnClickListener(this);
        rpb_water_oil_water_hand.setOnClickListener(this);
        rpb_water_oil_water_eye.setOnClickListener(this);
        rpb_water_oil_water_u.setOnClickListener(this);
        rpb_water_oil_oil_t.setOnClickListener(this);
        rpb_water_oil_oil_eye.setOnClickListener(this);
        rpb_water_oil_oil_hand.setOnClickListener(this);
        rpb_water_oil_oil_u.setOnClickListener(this);

        tv_water_oil_t_oil_result.setOnClickListener(this);
        tv_water_oil_u_oil_result.setOnClickListener(this);
        tv_water_oil_hand_oil_result.setOnClickListener(this);
        tv_water_oil_eye_oil_result.setOnClickListener(this);

    }

    @Override
    protected void setView() {
        tv_water_oil_oil_value.setTypeface(BaseApplication.getInstance().getLantingheiVeryFace());
        tv_water_oil_water_value.setTypeface(BaseApplication.getInstance().getLantingheiVeryFace());
    }

    @Override
    protected void onClickView(View view) {
        switch (view.getId()) {
            case R.id.rpb_water_oil_water_t:
            case R.id.rpb_water_oil_water_hand:
            case R.id.rpb_water_oil_water_eye:
            case R.id.rpb_water_oil_water_u:
            case R.id.rpb_water_oil_oil_t:
            case R.id.rpb_water_oil_oil_eye:
            case R.id.rpb_water_oil_oil_hand:
            case R.id.rpb_water_oil_oil_u: {
                rpb_water_oil_water_header_round.setCricleColor(getResources().getColor(R.color.project_color));
                rpb_water_oil_oil_header_round.setCricleColor(getResources().getColor(R.color.project_color));
                rpb_water_oil_water.setProgress(0);
                rpb_water_oil_oil.setProgress(0);
                tv_water_oil_water_result.setText("未检测");
                tv_water_oil_water_result.setTextColor(getResources().getColor(R.color.gray_22));
                tv_water_oil_oil_result.setText("未检测");
                tv_water_oil_oil_result.setTextColor(getResources().getColor(R.color.gray_22));
                tv_water_oil_water_value.setText("00");
                tv_water_oil_oil_value.setText("00");
                changeColor(view);
                ToastUtils.show("请打开M兔头部开关按钮");
            }
            break;
            case R.id.tv_water_oil_t_oil_result: {
                if (!"查看".equals(tv_water_oil_t_oil_result.getText().toString())) {
                    commonDialog.setMessage("请水油检测后查看");
                    commonDialog.showDig();
                    return;
                }
                if (ll_water_oil_t.getVisibility() == View.VISIBLE) {
                    iv_01.setVisibility(View.GONE);
                    iv_02.setVisibility(View.VISIBLE);
                } else {
                    iv_01.setVisibility(View.VISIBLE);
                    iv_02.setVisibility(View.GONE);
                }
                ll_water_oil_t.setVisibility(ll_water_oil_t.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
            break;
            case R.id.tv_water_oil_u_oil_result: {
                if (!"查看".equals(tv_water_oil_u_oil_result.getText().toString())) {
                    commonDialog.setMessage("请水油检测后查看");
                    commonDialog.showDig();
                    return;
                }
                if (ll_water_oil_u.getVisibility() == View.VISIBLE) {
                    iv_03.setVisibility(View.GONE);
                    iv_04.setVisibility(View.VISIBLE);
                } else {
                    iv_03.setVisibility(View.VISIBLE);
                    iv_04.setVisibility(View.GONE);
                }
                ll_water_oil_u.setVisibility(ll_water_oil_u.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
            break;
            case R.id.tv_water_oil_hand_oil_result: {
                if (!"查看".equals(tv_water_oil_hand_oil_result.getText().toString())) {
                    commonDialog.setMessage("请水油检测后查看");
                    commonDialog.showDig();
                    return;
                }
                ll_water_oil_hand.setVisibility(ll_water_oil_hand.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
            break;
            case R.id.tv_water_oil_eye_oil_result: {
                if (!"查看".equals(tv_water_oil_eye_oil_result.getText().toString())) {
                    commonDialog.setMessage("请水油检测后查看");
                    commonDialog.showDig();
                    return;
                }
                if (ll_water_oil_eye.getVisibility() == View.VISIBLE) {
                    iv_05.setVisibility(View.GONE);
                    iv_06.setVisibility(View.VISIBLE);
                } else {
                    iv_05.setVisibility(View.VISIBLE);
                    iv_06.setVisibility(View.GONE);
                }
                ll_water_oil_eye.setVisibility(ll_water_oil_eye.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
            break;
        }
    }

    void changeColor(View view) {
        changeColor(rpb_water_oil_water_t, 0, getResources().getColor(R.color.gray_26), tv_water_oil_water_t, getResources().getColor(R.color.gray_26));
        changeColor(rpb_water_oil_water_hand, 0, getResources().getColor(R.color.gray_26), tv_water_oil_water_hand, getResources().getColor(R.color.gray_26));
        changeColor(rpb_water_oil_water_eye, 0, getResources().getColor(R.color.gray_26), tv_water_oil_water_eye, getResources().getColor(R.color.gray_26));
        changeColor(rpb_water_oil_water_u, 0, getResources().getColor(R.color.gray_26), tv_water_oil_water_u, getResources().getColor(R.color.gray_26));
        changeColor(rpb_water_oil_oil_t, 0, getResources().getColor(R.color.gray_26), tv_water_oil_oil_t, getResources().getColor(R.color.gray_26));
        changeColor(rpb_water_oil_oil_eye, 0, getResources().getColor(R.color.gray_26), tv_water_oil_oil_eye, getResources().getColor(R.color.gray_26));
        changeColor(rpb_water_oil_oil_hand, 0, getResources().getColor(R.color.gray_26), tv_water_oil_oil_hand, getResources().getColor(R.color.gray_26));
        changeColor(rpb_water_oil_oil_u, 0, getResources().getColor(R.color.gray_26), tv_water_oil_oil_u, getResources().getColor(R.color.gray_26));
        switch (view.getId()) {
            case R.id.rpb_water_oil_water_t:
            case R.id.rpb_water_oil_oil_t: {
                changeColor(rpb_water_oil_water_t, 1, getResources().getColor(R.color.project_color), tv_water_oil_water_t, getResources().getColor(android.R.color.white));
                changeColor(rpb_water_oil_oil_t, 1, getResources().getColor(R.color.project_color), tv_water_oil_oil_t, getResources().getColor(android.R.color.white));
                type = 1;
            }
            break;
            case R.id.rpb_water_oil_water_eye:
            case R.id.rpb_water_oil_oil_eye: {
                changeColor(rpb_water_oil_oil_eye, 1, getResources().getColor(R.color.project_color), tv_water_oil_water_eye, getResources().getColor(android.R.color.white));
                changeColor(rpb_water_oil_water_eye, 1, getResources().getColor(R.color.project_color), tv_water_oil_oil_eye, getResources().getColor(android.R.color.white));
                type = 3;
            }
            break;
            case R.id.rpb_water_oil_water_hand:
            case R.id.rpb_water_oil_oil_hand: {
                changeColor(rpb_water_oil_oil_hand, 1, getResources().getColor(R.color.project_color), tv_water_oil_water_hand, getResources().getColor(android.R.color.white));
                changeColor(rpb_water_oil_water_hand, 1, getResources().getColor(R.color.project_color), tv_water_oil_oil_hand, getResources().getColor(android.R.color.white));
                type = 2;
            }
            break;
            case R.id.rpb_water_oil_water_u:
            case R.id.rpb_water_oil_oil_u: {
                changeColor(rpb_water_oil_oil_u, 1, getResources().getColor(R.color.project_color), tv_water_oil_water_u, getResources().getColor(android.R.color.white));
                changeColor(rpb_water_oil_water_u, 1, getResources().getColor(R.color.project_color), tv_water_oil_oil_u, getResources().getColor(android.R.color.white));
                type = 4;
            }
            break;
        }
    }

    public void changeColor(RoundProgressBar roundProgressBar, int style, int color, TextView textView, int textColor) {
        roundProgressBar.setStyle(style);
        roundProgressBar.setCricleColor(color);
        textView.setTextColor(textColor);
    }

    public void changeText(TextView waterTextView, int waterTextColor, String waterText) {
        waterTextView.setText(waterText);
        waterTextView.setTextColor(waterTextColor);
    }

    int startProgress = 0;
    Semaphore semaphore = new Semaphore(1);
    ExecutorService service = Executors.newSingleThreadExecutor();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(final EventModel eventModel) {
        if ("read".equals(eventModel.type)) {
            if (type == -1) {
                return;
            }
            if(startProgress != 100){
                service.execute(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            semaphore.acquire();
                            EventBus.getDefault().post(eventModel);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }));
                return;
            }
            byte[] array = (byte[]) eventModel.eventBus;
            byte[] value = {array[2], array[3], array[4], array[5]};
            if (value.length == 4) {
                final int v2 = value[1] & 0xFF;
                final int v3 = value[2] & 0xFF;
                StorageUtil.addRecordMode(new RecordModel(DateUtils.getStringDateAndTimeFromDate(new Date()), v2, v3));
                ValueAnimator animator = ValueAnimator.ofInt(1, 100);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int progress = startProgress - (int) animation.getAnimatedValue();
                        if (progress >= ((float) v2)) {
                            rpb_water_oil_water.setProgress(progress);
                            tv_water_oil_water_value.setText(progress < 10 ? "0" + progress + "" : progress + "");
                            switch (type) {
                                case 4:
                                case 1: {
                                    if (progress <= 35) {
                                        rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                                        rpb_water_oil_water_header_round.setCricleColor(getResources().getColor(R.color.health_serious));
                                    } else if (progress <= 40) {
                                        rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                                        rpb_water_oil_water_header_round.setCricleColor(getResources().getColor(R.color.health_serious));
                                    } else if (progress <= 60) {
                                        rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_moderate));
                                        rpb_water_oil_water_header_round.setCricleColor(getResources().getColor(R.color.health_moderate));
                                    } else if (progress > 60) {
                                        rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_good));
                                        rpb_water_oil_water_header_round.setCricleColor(getResources().getColor(R.color.health_good));
                                    }
                                }
                                break;
                                case 2: {
                                    if (progress <= 30) {
                                        rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                                        rpb_water_oil_water_header_round.setCricleColor(getResources().getColor(R.color.health_serious));
                                    } else if (progress <= 35) {
                                        rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                                        rpb_water_oil_water_header_round.setCricleColor(getResources().getColor(R.color.health_serious));
                                    } else if (progress <= 55) {
                                        rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_moderate));
                                        rpb_water_oil_water_header_round.setCricleColor(getResources().getColor(R.color.health_moderate));
                                    } else if (progress >= 56) {
                                        rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_good));
                                        rpb_water_oil_water_header_round.setCricleColor(getResources().getColor(R.color.health_good));
                                    }
                                }
                                break;
                                case 3: {
                                    if (progress <= 40) {
                                        rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                                        rpb_water_oil_water_header_round.setCricleColor(getResources().getColor(R.color.health_serious));
                                    } else if (progress <= 45) {
                                        rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                                        rpb_water_oil_water_header_round.setCricleColor(getResources().getColor(R.color.health_serious));
                                    } else if (progress <= 65) {
                                        rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_moderate));
                                        rpb_water_oil_water_header_round.setCricleColor(getResources().getColor(R.color.health_moderate));
                                    } else if (progress > 65) {
                                        rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_good));
                                        rpb_water_oil_water_header_round.setCricleColor(getResources().getColor(R.color.health_good));
                                    }
                                }
                                break;
                            }
                        }
                        if (progress >= ((float) v3)) {
                            rpb_water_oil_oil.setProgress(progress);
                            tv_water_oil_oil_value.setText(progress < 10 ? "0" + progress + "" : progress + "");
                            switch (type) {
                                case 4:
                                case 1: {
                                    if (progress <= 16) {
                                        rpb_water_oil_oil.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                                        changeText(tv_water_oil_oil_result, getResources().getColor(R.color.gray_22), "缺油");
                                        rpb_water_oil_oil_header_round.setCricleColor(getResources().getColor(R.color.health_serious));
                                    } else if (progress <= 30) {
                                        rpb_water_oil_oil.setCricleProgressColor(getResources().getColor(R.color.health_good));
                                        changeText(tv_water_oil_oil_result, getResources().getColor(R.color.gray_22), "油份正常");
                                        rpb_water_oil_oil_header_round.setCricleColor(getResources().getColor(R.color.health_good));
                                    } else if (progress > 30) {
                                        rpb_water_oil_oil.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                                        changeText(tv_water_oil_oil_result, getResources().getColor(R.color.gray_22), "偏油");
                                        rpb_water_oil_oil_header_round.setCricleColor(getResources().getColor(R.color.health_serious));
                                    }
                                }
                                break;
                                case 2: {
                                    if (progress <= 11) {
                                        rpb_water_oil_oil.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                                        changeText(tv_water_oil_oil_result, getResources().getColor(R.color.gray_22), "缺油");
                                        rpb_water_oil_oil_header_round.setCricleColor(getResources().getColor(R.color.health_serious));
                                    } else if (progress <= 25) {
                                        rpb_water_oil_oil.setCricleProgressColor(getResources().getColor(R.color.health_good));
                                        changeText(tv_water_oil_oil_result, getResources().getColor(R.color.gray_22), "油份正常");
                                        rpb_water_oil_oil_header_round.setCricleColor(getResources().getColor(R.color.health_good));
                                    } else if (progress > 25) {
                                        rpb_water_oil_oil.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                                        changeText(tv_water_oil_oil_result, getResources().getColor(R.color.gray_22), "偏油");
                                        rpb_water_oil_oil_header_round.setCricleColor(getResources().getColor(R.color.health_serious));
                                    }
                                }
                                break;
                                case 3: {
                                    if (progress <= 21) {
                                        rpb_water_oil_oil.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                                        changeText(tv_water_oil_oil_result, getResources().getColor(R.color.gray_22), "缺油");
                                        rpb_water_oil_oil_header_round.setCricleColor(getResources().getColor(R.color.health_serious));
                                    } else if (progress <= 35) {
                                        rpb_water_oil_oil.setCricleProgressColor(getResources().getColor(R.color.health_good));
                                        changeText(tv_water_oil_oil_result, getResources().getColor(R.color.gray_22), "油份正常");
                                        rpb_water_oil_oil_header_round.setCricleColor(getResources().getColor(R.color.health_good));
                                    } else if (progress > 35) {
                                        rpb_water_oil_oil.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                                        changeText(tv_water_oil_oil_result, getResources().getColor(R.color.gray_22), "偏油");
                                        rpb_water_oil_oil_header_round.setCricleColor(getResources().getColor(R.color.health_serious));
                                    }
                                }
                                break;
                            }
                        }
                    }
                });
                animator.setDuration(1500);
                animator.start();
                float result1 = (float) v2;
                float result2 = (float) v3;
                switch (type) {
                    case 4:
                    case 1: {
                        if (type == 1) {
                            changeText(tv_water_oil_t_oil_result, getResources().getColor(R.color.gray_26), "查看");
                        } else {
                            changeText(tv_water_oil_u_oil_result, getResources().getColor(R.color.gray_26), "查看");
                        }
                        if (result1 <= 35) {
                            changeText(tv_water_oil_water_result, getResources().getColor(R.color.gray_22), "严重缺水");
                            if (result2 <= 16) {
                                if (type == 1) {
                                    tv_water_oil_t_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>严重缺水</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                    setDataAdapter("T", "水分缺失/缺油", waterOilAdapterT);
                                } else {
                                    tv_water_oil_u_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>严重缺水</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                    setDataAdapter("T", "水分缺失/缺油", waterOilAdapterU);
                                }
                            } else if (result2 <= 30) {
                                if (type == 1) {
                                    tv_water_oil_t_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>严重缺水</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                    setDataAdapter("T", "水分缺失/油分正常", waterOilAdapterT);
                                } else {
                                    tv_water_oil_u_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>严重缺水</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                    setDataAdapter("T", "水分缺失/油分正常", waterOilAdapterU);
                                }
                            } else if (result2 > 30) {
                                if (type == 1) {
                                    tv_water_oil_t_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>严重缺水</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                    setDataAdapter("T", "水分缺失/偏油", waterOilAdapterT);
                                } else {
                                    tv_water_oil_u_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>严重缺水</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                    setDataAdapter("T", "水分缺失/偏油", waterOilAdapterU);
                                }
                            }
                        } else if (result1 <= 40) {
                            changeText(tv_water_oil_water_result, getResources().getColor(R.color.gray_22), "稍微缺水");
                            if (result2 <= 16) {
                                if (type == 1) {
                                    tv_water_oil_t_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>稍微缺水</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                    setDataAdapter("T", "水分缺失/缺油", waterOilAdapterT);
                                } else {
                                    tv_water_oil_u_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>严重缺水</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                    setDataAdapter("T", "水分缺失/缺油", waterOilAdapterU);
                                }
                            } else if (result2 <= 30) {
                                if (type == 1) {
                                    tv_water_oil_t_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>稍微缺水</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                    setDataAdapter("T", "水分缺失/油分正常", waterOilAdapterT);
                                } else {
                                    tv_water_oil_u_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>严重缺水</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                    setDataAdapter("T", "水分缺失/油分正常", waterOilAdapterU);
                                }
                            } else if (result2 > 30) {
                                if (type == 1) {
                                    tv_water_oil_t_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>稍微缺水</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                    setDataAdapter("T", "水分缺失/偏油", waterOilAdapterT);
                                } else {
                                    tv_water_oil_u_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>稍微缺水</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                    setDataAdapter("T", "水分缺失/偏油", waterOilAdapterU);
                                }
                            }
                        } else if (result1 <= 60) {
                            changeText(tv_water_oil_water_result, getResources().getColor(R.color.gray_22), "水分正常");
                            if (result2 <= 16) {
                                if (type == 1) {
                                    tv_water_oil_t_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#F9C164'>正常</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                    setDataAdapter("T", "水分正常/缺油", waterOilAdapterT);
                                } else {
                                    tv_water_oil_u_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#F9C164'>正常</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                    setDataAdapter("T", "水分正常/缺油", waterOilAdapterU);
                                }
                            } else if (result2 <= 30) {
                                if (type == 1) {
                                    tv_water_oil_t_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#F9C164'>正常</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                    setDataAdapter("T", "水分正常/油分正常", waterOilAdapterT);
                                } else {
                                    tv_water_oil_u_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#F9C164'>水分正常</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                    setDataAdapter("T", "水分正常/油分正常", waterOilAdapterU);
                                }
                            } else if (result2 > 30) {
                                if (type == 1) {
                                    tv_water_oil_t_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#F9C164'正常</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                    setDataAdapter("T", "水分正常/偏油", waterOilAdapterT);
                                } else {
                                    tv_water_oil_u_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#F9C164'>正常</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                    setDataAdapter("T", "水分正常/偏油", waterOilAdapterU);
                                }
                            }
                        } else if (result1 > 60) {
                            changeText(tv_water_oil_water_result, getResources().getColor(R.color.gray_22), "水分水润");
                            rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_good));
                            if (result2 <= 16) {
                                if (type == 1) {
                                    tv_water_oil_t_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#60C1BD'>水润</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                    setDataAdapter("T", "水分正常/缺油", waterOilAdapterT);
                                } else {
                                    tv_water_oil_u_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#60C1BD'>水润</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                    setDataAdapter("T", "水分正常/缺油", waterOilAdapterU);
                                }
                            } else if (result2 <= 30) {
                                if (type == 1) {
                                    tv_water_oil_t_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#60C1BD'>水润</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                    setDataAdapter("T", "十分水润/油分正常", waterOilAdapterT);
                                } else {
                                    tv_water_oil_u_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#60C1BD'>水润</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                    setDataAdapter("T", "十分水润/油分正常", waterOilAdapterU);
                                }
                            } else if (result2 > 30) {
                                if (type == 1) {
                                    tv_water_oil_t_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#60C1BD'>水润</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                    setDataAdapter("T", "水分正常/偏油", waterOilAdapterT);
                                } else {
                                    tv_water_oil_u_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#60C1BD'>水润</font>" +
                                            "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                    setDataAdapter("T", "水分正常/偏油", waterOilAdapterU);
                                }
                            }
                        }
                    }
                    break;
                    case 2: {
                        changeText(tv_water_oil_hand_oil_result, getResources().getColor(R.color.gray_26), "查看");
                        if (result1 <= 30) {
                            rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                            changeText(tv_water_oil_water_result, getResources().getColor(R.color.gray_22), "严重缺水");
                            if (result2 <= 11) {
                                tv_water_oil_hand_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>严重缺水</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                setDataAdapter("手", "水分缺失/缺油", waterOilAdapterHand);
                            } else if (result2 <= 25) {
                                tv_water_oil_hand_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>严重缺水</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                setDataAdapter("手", "水分缺失/油分正常", waterOilAdapterHand);
                            } else if (result2 > 25) {
                                tv_water_oil_hand_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>严重缺水</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                setDataAdapter("手", "水分缺失/缺油", waterOilAdapterHand);
                            }
                        } else if (result1 <= 35) {
                            rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                            changeText(tv_water_oil_water_result, getResources().getColor(R.color.gray_22), "稍微缺水");
                            if (result2 <= 11) {
                                tv_water_oil_hand_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'稍微缺水</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                setDataAdapter("手", "水分缺失/缺油", waterOilAdapterHand);
                            } else if (result2 <= 25) {
                                tv_water_oil_hand_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>稍微缺水</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                setDataAdapter("手", "水分缺失/油分正常", waterOilAdapterHand);
                            } else if (result2 > 25) {
                                tv_water_oil_hand_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>稍微缺水</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                setDataAdapter("手", "水分缺失/偏油", waterOilAdapterHand);
                            }
                        } else if (result1 <= 55) {
                            rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_moderate));
                            changeText(tv_water_oil_water_result, getResources().getColor(R.color.gray_22), "水分正常");
                            if (result2 <= 11) {
                                tv_water_oil_hand_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#F9C164'>正常</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                setDataAdapter("手", "水分正常/缺油", waterOilAdapterHand);
                            } else if (result2 <= 25) {
                                tv_water_oil_hand_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#F9C164'>正常</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                setDataAdapter("手", "水分正常/油分正常", waterOilAdapterHand);
                            } else if (result2 > 25) {
                                tv_water_oil_hand_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#F9C164'>正常</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                setDataAdapter("手", "水分正常/偏油", waterOilAdapterHand);
                            }
                        } else if (result1 >= 56) {
                            changeText(tv_water_oil_water_result, getResources().getColor(R.color.gray_22), "水分水润");
                            if (result2 <= 11) {
                                tv_water_oil_hand_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#60C1BD'>水润</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                setDataAdapter("手", "水分正常/缺油", waterOilAdapterHand);
                            } else if (result2 <= 25) {
                                tv_water_oil_hand_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#60C1BD'>水润</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                setDataAdapter("手", "十分水润/油分正常", waterOilAdapterHand);
                            } else if (result2 > 25) {
                                tv_water_oil_hand_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#60C1BD'>水润</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                setDataAdapter("手", "水分正常/偏油", waterOilAdapterHand);
                            }
                        }
                    }
                    break;
                    case 3: {
                        changeText(tv_water_oil_eye_oil_result, getResources().getColor(R.color.gray_26), "查看");
                        if (result1 <= 40) {
                            rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                            changeText(tv_water_oil_water_result, getResources().getColor(R.color.gray_22), "严重缺水");
                            if (result2 <= 21) {
                                tv_water_oil_eye_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>严重缺水</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                setDataAdapter("眼", "水分缺失/缺油", waterOilAdapterEye);
                            } else if (result2 <= 35) {
                                tv_water_oil_eye_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>严重缺水</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                setDataAdapter("眼", "水分缺失/油分正常", waterOilAdapterEye);
                            } else if (result2 > 35) {
                                tv_water_oil_eye_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>严重缺水</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                setDataAdapter("眼", "水分缺失/偏油", waterOilAdapterEye);
                            }
                        } else if (result1 <= 45) {
                            rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_serious));
                            changeText(tv_water_oil_water_result, getResources().getColor(R.color.gray_22), "稍微缺水");
                            if (result2 <= 21) {
                                tv_water_oil_eye_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>稍微缺水</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                setDataAdapter("眼", "水分缺失/缺油", waterOilAdapterEye);
                            } else if (result2 <= 35) {
                                tv_water_oil_eye_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>稍微缺水</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                setDataAdapter("眼", "水分缺失/油分正常", waterOilAdapterEye);
                            } else if (result2 > 35) {
                                tv_water_oil_eye_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#EC6D6D'>稍微缺水</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                setDataAdapter("眼", "水分缺失/偏油", waterOilAdapterEye);
                            }
                        } else if (result1 <= 65) {
                            rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_moderate));
                            changeText(tv_water_oil_water_result, getResources().getColor(R.color.gray_22), "水分正常");
                            if (result2 <= 21) {
                                tv_water_oil_eye_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#F9C164'>正常</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                setDataAdapter("眼", "水分正常/缺油", waterOilAdapterEye);
                            } else if (result2 <= 35) {
                                tv_water_oil_eye_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#F9C164'>正常</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                setDataAdapter("眼", "水分正常/油分正常", waterOilAdapterEye);
                            } else if (result2 > 35) {
                                tv_water_oil_eye_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#F9C164'>正常</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                setDataAdapter("眼", "水分正常/偏油", waterOilAdapterEye);
                            }
                        } else if (result1 > 65) {
                            rpb_water_oil_water.setCricleProgressColor(getResources().getColor(R.color.health_good));
                            changeText(tv_water_oil_water_result, getResources().getColor(R.color.gray_22), "水分水润");
                            if (result2 <= 21) {
                                tv_water_oil_eye_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#60C1BD'>水润</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>缺油</font>"));
                                setDataAdapter("眼", "水分正常/缺油", waterOilAdapterEye);
                            } else if (result2 <= 35) {
                                tv_water_oil_eye_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#60C1BD'>水润</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#60C1BD'>正常</font>"));
                                setDataAdapter("眼", "十分水润/油分正常", waterOilAdapterEye);
                            } else if (result2 > 35) {
                                tv_water_oil_eye_water_result.setText(Html.fromHtml("<font color='#2B2D39'>水分</font><font color='#60C1BD'>水润</font>" +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;<font color='#2B2D39'>油分</font><font color='#EC6D6D'>偏油</font>"));
                                setDataAdapter("眼", "水分正常/偏油", waterOilAdapterEye);
                            }
                        }
                    }
                    break;
                }
            }
        } else if ("start".equals(eventModel.type)) {
            if (type == -1) {
                ToastUtils.show("请选择检测的部位");
                return;
            }
            ToastUtils.show("请用触头轻按皮肤部位5秒");
            ValueAnimator animator = ValueAnimator.ofInt(1, 100);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    startProgress = (int) animation.getAnimatedValue();
                    rpb_water_oil_water.setProgress(startProgress);
                    rpb_water_oil_oil.setProgress(startProgress);
                    rpb_water_oil_oil_header_round.setCricleColor(rpb_water_oil_oil.getCricleProgressColor());
                    rpb_water_oil_water_header_round.setCricleColor(rpb_water_oil_water.getCricleProgressColor());
                    if(startProgress == 100){
                        semaphore.release();
                    }
                }
            });
            animator.setDuration(8000);
            animator.start();
        } else if ("error".equals(eventModel.type)) {
            if(startProgress != 100){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            semaphore.acquire();
                            EventBus.getDefault().post(eventModel);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                return;
            }
            ToastUtils.show("检测失败，请重新检测");
        }
    }

    private void setDataAdapter(String order, String type, WaterOilAdapter waterOilAdapter) {
        waterOilAdapter.clear();
        waterOilAdapter.addAll(WaterOilModel.getWaterOilModel(order, type).waterOilResultModels);
        waterOilAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
