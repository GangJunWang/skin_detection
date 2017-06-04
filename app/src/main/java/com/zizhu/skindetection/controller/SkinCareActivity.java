package com.zizhu.skindetection.controller;

import android.animation.ValueAnimator;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zizhu.skindetection.R;
import com.zizhu.skindetection.base.BaseApplication;
import com.zizhu.skindetection.base.activity.BaseActivity;
import com.zizhu.skindetection.common.model.EventModel;
import com.zizhu.skindetection.common.model.RecordModel;
import com.zizhu.skindetection.common.utils.DateUtils;
import com.zizhu.skindetection.common.utils.ScreenUtil;
import com.zizhu.skindetection.common.utils.StorageUtil;
import com.zizhu.skindetection.common.utils.ToastUtils;
import com.zizhu.skindetection.common.widget.dialog.CustomDialogHelper;
import com.zizhu.skindetection.common.widget.image.RoundProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * 护肤测试
 */
public class SkinCareActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    RadioGroup rg_skin_care_portion, rg_skin_care_aim;
    int portion = -1; // 部位
    int aim = -1; // 目标
    private TextView tv_skin_care_start, tv_skin_care_water_before, tv_skin_care_water_after, tv_skin_care_result, tv_skin_care_one_progress, tv_skin_care_two_progress,
            tv_skin_care_effect, tv_skin_care_difference_value, tv_skin_care_after_value, tv_skin_care_after_result, tv_skin_care_before_value, tv_skin_care_before_result;
    private int start = -1;
    private int finish = -1;
    private boolean isStart = false;
    private int step = 0;
    private int oneStep = 0;
    private View v_skin_care_one, v_skin_care_two, v_skin_care_one_progress, v_skin_care_two_progress, ll_skin_care_one, ll_skin_care_two;
    private RoundProgressBar rpb_skin_care_two, rpb_skin_care_three, rpb_skin_care_first_left, rpb_skin_care_first_right, rpb_skin_care_two_left, rpb_skin_care_two_right;
    private TextView tv_skin_care_two, tv_skin_care_three;
    Typeface face;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_skin_care;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        face = Typeface.createFromAsset(getAssets(),"fonts/lantinghei.ttf");
        titleBarView.initCenterTitle("护肤品检测");
        titleBarView.rootLayout.setBackgroundColor(getResources().getColor(R.color.gray_20));
        titleBarView.leftIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.tab_btn_return));
        titleBarView.centerTitle.setTextColor(getResources().getColor(R.color.project_color));
        ll_skin_care_one = findViewById(R.id.ll_skin_care_one);
        ll_skin_care_two = findViewById(R.id.ll_skin_care_two);
        rg_skin_care_portion = (RadioGroup) findViewById(R.id.rg_skin_care_portion);
        rg_skin_care_aim = (RadioGroup) findViewById(R.id.rg_skin_care_aim);
        tv_skin_care_start = (TextView) findViewById(R.id.tv_skin_care_start);
        tv_skin_care_water_before = (TextView) findViewById(R.id.tv_skin_care_water_before);
        tv_skin_care_water_after = (TextView) findViewById(R.id.tv_skin_care_water_after);
        tv_skin_care_result = (TextView) findViewById(R.id.tv_skin_care_result);
        tv_skin_care_one_progress = (TextView) findViewById(R.id.tv_skin_care_one_progress);
        tv_skin_care_two_progress = (TextView) findViewById(R.id.tv_skin_care_two_progress);
        v_skin_care_one = findViewById(R.id.v_skin_care_one);
        v_skin_care_two = findViewById(R.id.v_skin_care_two);
        v_skin_care_one_progress = findViewById(R.id.v_skin_care_one_progress);
        v_skin_care_two_progress = findViewById(R.id.v_skin_care_two_progress);
        tv_skin_care_two = (TextView) findViewById(R.id.tv_skin_care_two);
        tv_skin_care_three = (TextView) findViewById(R.id.tv_skin_care_three);
        rpb_skin_care_two = (RoundProgressBar) findViewById(R.id.rpb_skin_care_two);
        rpb_skin_care_three = (RoundProgressBar) findViewById(R.id.rpb_skin_care_three);
        rpb_skin_care_first_left = (RoundProgressBar) findViewById(R.id.rpb_skin_care_first_left);
        rpb_skin_care_first_right = (RoundProgressBar) findViewById(R.id.rpb_skin_care_first_right);
        rpb_skin_care_two_left = (RoundProgressBar) findViewById(R.id.rpb_skin_care_two_left);
        rpb_skin_care_two_right = (RoundProgressBar) findViewById(R.id.rpb_skin_care_two_right);

        tv_skin_care_effect = (TextView) findViewById(R.id.tv_skin_care_effect);
        tv_skin_care_difference_value = (TextView) findViewById(R.id.tv_skin_care_difference_value);
        tv_skin_care_after_value = (TextView) findViewById(R.id.tv_skin_care_after_value);
        tv_skin_care_after_result = (TextView) findViewById(R.id.tv_skin_care_after_result);
        tv_skin_care_before_value = (TextView) findViewById(R.id.tv_skin_care_before_value);
        tv_skin_care_before_result = (TextView) findViewById(R.id.tv_skin_care_before_result);
        findTextView((ViewGroup) getWindow().getDecorView());
    }

    private void findTextView(ViewGroup viewGroup){
        for(int i = 0; i < viewGroup.getChildCount(); i++){
            if(viewGroup.getChildAt(i) instanceof ViewGroup){
                findTextView((ViewGroup) viewGroup.getChildAt(i));
            }
            if((viewGroup.getChildAt(i) instanceof TextView)){
                TextView textView = (TextView) viewGroup.getChildAt(i);
                textView.setTypeface(face);
            }
            if((viewGroup.getChildAt(i) instanceof RadioButton)){
                RadioButton radioButton = (RadioButton) viewGroup.getChildAt(i);
                radioButton.setTypeface(face);
            }
        }
    }

    private void setOneProgress(int progress){
        float width = ((ViewGroup)v_skin_care_one_progress.getParent()).getChildAt(0).getWidth();
        int value = (int) (width / 100f * progress);
        ViewGroup.LayoutParams params = v_skin_care_one_progress.getLayoutParams();
        params.width = value;
        v_skin_care_one_progress.setLayoutParams(params);
        tv_skin_care_one_progress.setText(progress < 10 ? "0" + progress + "" : progress + "");
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_skin_care_one.getLayoutParams();
        value = (int) ((float)value * 10.f / 11.f);
        layoutParams.leftMargin = value;
        ll_skin_care_one.setLayoutParams(layoutParams);
    }

    private void setTwoProgress(int progress){
        float width = ((ViewGroup)v_skin_care_two_progress.getParent()).getChildAt(0).getWidth();
        int value = (int) (width / 100f * progress);
        ViewGroup.LayoutParams params = v_skin_care_two_progress.getLayoutParams();
        params.width = value;
        v_skin_care_two_progress.setLayoutParams(params);
        tv_skin_care_two_progress.setText(progress < 10 ? "0" + progress + "" : progress + "");
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_skin_care_two.getLayoutParams();
        value = (int) ((float)value * 10.f / 11.f);
        layoutParams.leftMargin = value;
        ll_skin_care_two.setLayoutParams(layoutParams);
    }

    @Override
    protected void bindEven() {
        rg_skin_care_portion.setOnCheckedChangeListener(this);
        rg_skin_care_aim.setOnCheckedChangeListener(this);
        tv_skin_care_start.setOnClickListener(this);
    }

    @Override
    protected void setView() {
        tv_skin_care_one_progress.setTypeface(BaseApplication.getInstance().getLantingheiVeryFace());
        tv_skin_care_two_progress.setTypeface(BaseApplication.getInstance().getLantingheiVeryFace());
        EventBus.getDefault().register(this);
        ScreenUtil.initStatusBar(this, R.color.gray_20);
    }

    @Override
    protected void onClickView(View view) {
        switch (view.getId()){
            case R.id.tv_skin_care_start:{
                if(!tv_skin_care_start.getText().toString().equals("重新测试")) {
                    if (start == -1) {
                        start = 1;
                        step++;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            tv_skin_care_start.setBackground(getResources().getDrawable(R.drawable.healthy_detection_check));
                        }
                    }
                }else{
                    startActivity(SkinCareActivity.class);
                    finish();
                }
            }
            break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int position) {
        if(isStart && position != -1){
            ToastUtils.show("您正在护肤中...");
            switch (radioGroup.getId()){
                case R.id.rg_skin_care_portion: {
                    switch (portion) {
                        case 1:
                            radioGroup.check(R.id.rb_01);
                            break;
                        case 2:
                            radioGroup.check(R.id.rb_02);
                            break;
                        case 3:
                            radioGroup.check(R.id.rb_03);
                            break;
                    }
                }
                break;
                case R.id.rg_skin_care_aim:
                switch (aim) {
                    case 1:
                        radioGroup.check(R.id.rb_skin_care_water);
                        break;
                    case 2:
                        radioGroup.check(R.id.rb_skin_care_oil);
                        break;
                }
            }
            return;
        }
        switch (radioGroup.getId()){
            case R.id.rg_skin_care_portion:{
                switch (position){
                    case R.id.rb_01:
                        portion = 1;
                        break;
                    case R.id.rb_02:
                        portion = 2;
                        break;
                    case R.id.rb_03:
                        portion = 3;
                        break;
                }
            }
            break;
            case R.id.rg_skin_care_aim:{
                if(position == R.id.rb_skin_care_water){
                    tv_skin_care_water_before.setText("护肤前的水分");
                    tv_skin_care_water_after.setText("护肤后的水分");
                    tv_skin_care_effect.setText("补水效果");
                    aim = 1;
                }else if(position == R.id.rb_skin_care_oil){
                    tv_skin_care_water_before.setText("护肤前的油分");
                    tv_skin_care_water_after.setText("护肤后的油分");
                    tv_skin_care_effect.setText("控油效果");
                    aim = 2;
                }
            }
            break;
        }
    }

    int startProgress = 0;
    Semaphore semaphore = new Semaphore(1);

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvenMain(final EventModel eventModel) {
        if (eventModel != null) {
            if ("read".equals(eventModel.type)) {
                if (portion == -1) {
                    return;
                }
                if (aim == -1) {
                    return;
                }
                if (step == 0) {
                    return;
                }
                if(finish == 1){
                    return;
                }
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
                start = -1;
                isStart = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tv_skin_care_start.setBackground(getResources().getDrawable(R.drawable.healthy_detection));
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
                            if (aim == 1) {
                                if (progress >= v2) {
                                    if (step == 1) {
                                        setOneProgress(progress);
                                        tv_skin_care_before_value.setText("水分值:" + (progress < 10 ? "0" + progress + "%" : progress + "%"));
                                        if (progress > 90) {
                                            rpb_skin_care_first_right.setStyle(1);
                                            rpb_skin_care_first_right.setCricleColor(getResources().getColor(R.color.health_good));
                                        }else{
                                            rpb_skin_care_first_right.setStyle(0);
                                            rpb_skin_care_first_right.setCricleColor(getResources().getColor(R.color.project_color));
                                            if(progress == 0){
                                                rpb_skin_care_first_left.setStyle(0);
                                                rpb_skin_care_first_left.setCricleColor(getResources().getColor(R.color.project_color));
                                            }
                                        }
                                    } else {
                                        setTwoProgress(progress);
                                        tv_skin_care_after_value.setText("水分值:" + (progress < 10 ? "0" + progress + "%" : progress + "%"));
                                        if (progress > 90) {
                                            rpb_skin_care_two_right.setStyle(1);
                                            rpb_skin_care_two_right.setCricleColor(getResources().getColor(R.color.health_good));
                                        }else {
                                            rpb_skin_care_two_right.setStyle(0);
                                            rpb_skin_care_two_right.setCricleColor(getResources().getColor(R.color.project_color));
                                            if(progress == 0){
                                                rpb_skin_care_two_left.setStyle(0);
                                                rpb_skin_care_two_left.setCricleColor(getResources().getColor(R.color.project_color));
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (progress >= v3) {
                                    if (step == 1) {
                                        setOneProgress(progress);
                                        tv_skin_care_before_value.setText("油分值:" + (progress < 10 ? "0" + progress + "%" : progress + "%"));
                                        if (progress > 90) {
                                            rpb_skin_care_first_right.setStyle(1);
                                            rpb_skin_care_first_right.setCricleColor(getResources().getColor(R.color.health_good));
                                        }
                                    } else {
                                        setTwoProgress(progress);
                                        tv_skin_care_after_value.setText("油分值:" + (progress < 10 ? "0" + progress + "%" : progress + "%"));
                                        if (progress > 90) {
                                            rpb_skin_care_two_right.setStyle(1);
                                            rpb_skin_care_two_right.setCricleColor(getResources().getColor(R.color.health_good));
                                        }
                                    }
                                }
                            }
                        }
                    });
                    animator.setDuration(1500);
                    animator.start();
                    if (step == 1) {
                        changeColor(rpb_skin_care_two, v_skin_care_one, tv_skin_care_two);
                        if(v2 > 0) {
                            rpb_skin_care_first_left.setStyle(1);
                            rpb_skin_care_first_left.setCricleColor(getResources().getColor(R.color.health_good));
                        }
                        tv_skin_care_start.setText("护肤完成");
                        if (aim == 1) {
                            oneStep = v2;
                            switch (portion) {
                                case 1: {
                                    tv_skin_care_before_result.setText(changeWaterText(v2, 35, 40, 60));
                                }
                                break;
                                case 2: {
                                    tv_skin_care_before_result.setText(changeWaterText(v2, 40, 45, 65));
                                }
                                break;
                                case 3: {
                                    tv_skin_care_before_result.setText(changeWaterText(v2, 30, 35, 55));
                                }
                                break;
                            }
                        } else {
                            oneStep = v3;
                            switch (portion) {
                                case 1: {
                                    tv_skin_care_before_result.setText(changeOilText(v3, 16, 30));
                                }
                                break;
                                case 2: {
                                    tv_skin_care_before_result.setText(changeOilText(v3, 21, 35));
                                }
                                break;
                                case 3: {
                                    tv_skin_care_before_result.setText(changeOilText(v3, 11, 25));
                                }
                                break;
                            }
                        }
                    } else if (step == 2) {
                        changeColor(rpb_skin_care_three, v_skin_care_two, tv_skin_care_three);
                        rpb_skin_care_two_left.setStyle(1);
                        rpb_skin_care_two_left.setCricleColor(getResources().getColor(R.color.health_good));
                        tv_skin_care_start.setText("重新测试");
                        tv_skin_care_result.setTextColor(getResources().getColor(R.color.health_good));
                        if (aim == 1) {
                            tv_skin_care_difference_value.setText(v2 - oneStep > 0 ? "+" + ((v2 - oneStep) < 10 ? "0" + (v2 - oneStep) + "%" : (v2 - oneStep) + "%") : (v2 - oneStep) + "%");
                            if(v2 - oneStep <= 20){
                                tv_skin_care_result.setText("您使用的护肤品补水效果一般噢");
                            } else if(v2 - oneStep <= 35){
                                tv_skin_care_result.setText("您使用的护肤品补水效果还行噢");
                            } else if(v2 - oneStep > 35){
                                tv_skin_care_result.setText("您使用的护肤品补水效果超棒噢");
                            }
                            switch (portion) {
                                case 1: {
                                    tv_skin_care_after_result.setText(changeWaterText(v2, 35, 40, 60));
                                }
                                break;
                                case 2: {
                                    tv_skin_care_after_result.setText(changeWaterText(v2, 40, 45, 65));
                                }
                                break;
                                case 3: {
                                    tv_skin_care_after_result.setText(changeWaterText(v2, 30, 35, 55));
                                }
                                break;
                            }
                        } else {
                            tv_skin_care_difference_value.setText(v3 - oneStep > 0 ? "+" + ((v3 - oneStep) < 10 ? "0" + (v3 - oneStep) + "%" : (v3 - oneStep) + "%") : (v3 - oneStep) + "%");
                            if(oneStep - v3 <= 10){
                                tv_skin_care_result.setText("您使用的护肤品控油效果一般噢");
                            } else if(oneStep - v3 <= 15){
                                tv_skin_care_result.setText("您使用的护肤品控油效果不错噢");
                            } else if(oneStep - v3 > 15){
                                tv_skin_care_result.setText("您使用的护肤品控油效果超棒噢");
                            }
                            switch (portion) {
                                case 1: {
                                    tv_skin_care_after_result.setText(changeOilText(v3, 16, 30));
                                }
                                break;
                                case 2: {
                                    tv_skin_care_after_result.setText(changeOilText(v3, 21, 35));
                                }
                                break;
                                case 3: {
                                    tv_skin_care_after_result.setText(changeOilText(v3, 11, 25));
                                }
                                break;
                            }
                        }
                    }
                }
//                ToastUtils.show("测试完成，请查看测试结果");
            } else if ("start".equals(eventModel.type)) {
                if (portion == -1) {
                    ToastUtils.show("请选择检测部位");
                    return;
                }
                if (aim == -1) {
                    ToastUtils.show("请选择目标检测");
                    return;
                }
                if (step == 0) {
                    ToastUtils.show("您还没有点击" + tv_skin_care_start.getText().toString());
                    finish = 1;
                    return;
                }
                finish = -1;
                ToastUtils.show("请用触头轻按皮肤部位5秒");
                ValueAnimator animator = ValueAnimator.ofInt(1, 100);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        startProgress = (int) animation.getAnimatedValue();
                        if (step == 1) {
                            setOneProgress(startProgress);
                        } else{
                            setTwoProgress(startProgress);
                        }
                        if(startProgress == 100){
                            semaphore.release();
                        }
                    }
                });
                animator.setDuration(8000);
                animator.start();
            }else if("error".equals(eventModel.type)){
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
    }

    private String changeWaterText(int value, int a, int b, int c){
        String result = "";
        if (value <= a) {
            result = "严重缺水";
        } else if(value <= b){
            result = "稍微缺水";
        } else if(value <= c){
            result = "水分正常";
        } else if(value > c){
            result = "十分湿润";
        }
        return result;
    }

    private String changeOilText(int value, int a, int b){
        String result = "";
        if(value <= a) {
            result = "稍微缺油";
        } else if(value <= b){
            result = "油分正常";
        } else if(value > b){
            result = "稍微偏油";
        }
        return result;
    }
    private void changeColor(RoundProgressBar roundProgressBar, View view, TextView textView){
        roundProgressBar.setStyle(1);
        roundProgressBar.setCricleColor(getResources().getColor(R.color.health_good));
        view.setVisibility(View.VISIBLE);
        textView.setTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
