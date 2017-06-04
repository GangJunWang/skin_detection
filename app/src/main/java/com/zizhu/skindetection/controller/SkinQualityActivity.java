package com.zizhu.skindetection.controller;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 质肤测试
 */
public class SkinQualityActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    View ll_skin_quality_topic, rl_skin_quality_board, v_center_line;

    private RoundProgressBar rpb_skin_quality_head;
    private TextView tv_skin_quality_head;
    private RoundProgressBar rpb_skin_quality_right_face;
    private TextView tv_skin_quality_right_face;
    private RoundProgressBar rpb_skin_quality_left_face;
    private TextView tv_skin_quality_left_face;
    private RoundProgressBar rpb_skin_quality_left_mandible;
    private TextView tv_skin_quality_left_mandible;
    private RoundProgressBar rpb_skin_quality_left_nose;
    private TextView tv_skin_quality_left_nose;
    private Map<String, Float> sumMap = new HashMap<String, Float>();
    private Map<String, Float> allergyMap = new HashMap<String, Float>();
    private TextView tv_skin_quality_belong, tv_skin_quality_advice;
    private TextView tv_skin_quality_head_title, tv_skin_quality_right_face_title, tv_skin_quality_left_face_title,
            tv_skin_quality_mandible_title, tv_skin_quality_nose_title;
    private int checkPosition = -1;
    private List<Integer> checkPositions = new ArrayList<>();
    List<String> roundValues = new ArrayList<>();
    List<RoundProgressBar> roundProgressBars = new ArrayList<>();

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_skin_quality;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        titleBarView.initCenterTitle("肤质检测");
        ll_skin_quality_topic = findViewById(R.id.ll_skin_quality_topic);
        rl_skin_quality_board = findViewById(R.id.rl_skin_quality_board);
        v_center_line = findViewById(R.id.v_center_line);
        rpb_skin_quality_head = (RoundProgressBar) findViewById(R.id.rpb_skin_quality_head);
        tv_skin_quality_head = (TextView) findViewById(R.id.tv_skin_quality_head);
        rpb_skin_quality_right_face = (RoundProgressBar) findViewById(R.id.rpb_skin_quality_right_face);
        tv_skin_quality_right_face = (TextView) findViewById(R.id.tv_skin_quality_right_face);
        rpb_skin_quality_left_face = (RoundProgressBar) findViewById(R.id.rpb_skin_quality_left_face);
        tv_skin_quality_left_face = (TextView) findViewById(R.id.tv_skin_quality_left_face);
        rpb_skin_quality_left_mandible = (RoundProgressBar) findViewById(R.id.rpb_skin_quality_left_mandible);
        tv_skin_quality_left_mandible = (TextView) findViewById(R.id.tv_skin_quality_left_mandible);
        rpb_skin_quality_left_nose = (RoundProgressBar) findViewById(R.id.rpb_skin_quality_left_nose);
        tv_skin_quality_left_nose = (TextView) findViewById(R.id.tv_skin_quality_left_nose);
        tv_skin_quality_belong = (TextView) findViewById(R.id.tv_skin_quality_belong);
        tv_skin_quality_advice = (TextView) findViewById(R.id.tv_skin_quality_advice);
    }

    private void findTextView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                findTextView((ViewGroup) viewGroup.getChildAt(i));
            }
            if ((viewGroup.getChildAt(i) instanceof TextView)) {
                TextView textView = (TextView) viewGroup.getChildAt(i);
                if (textView.getTag() == null) {
                    textView.setTypeface(BaseApplication.getInstance().getLantingheiFace());
                } else {
                    textView.setTypeface(BaseApplication.getInstance().getLantingheiVeryFace());
                }
            }
            if ((viewGroup.getChildAt(i) instanceof RadioButton)) {
                RadioButton radioButton = (RadioButton) viewGroup.getChildAt(i);
                radioButton.setTypeface(BaseApplication.getInstance().getLantingheiFace());
                radioButton.setTextColor(getResources().getColor(R.color.gray_18));
            }
            if ((viewGroup.getChildAt(i) instanceof RoundProgressBar)) {
                RoundProgressBar roundProgressBar = (RoundProgressBar) viewGroup.getChildAt(i);
                if (!"value".equals(roundProgressBar.getTag() + "")) {
                    roundProgressBar.setProgress(100);
                }
                if (roundProgressBar.getTag() != null && roundValues.contains(roundProgressBar.getTag() + "")) {
                    roundProgressBars.add(roundProgressBar);
                }
            }
        }
    }

    @Override
    protected void bindEven() {
        rpb_skin_quality_head.setOnClickListener(this);
        rpb_skin_quality_right_face.setOnClickListener(this);
        rpb_skin_quality_left_face.setOnClickListener(this);
        rpb_skin_quality_left_mandible.setOnClickListener(this);
        rpb_skin_quality_left_nose.setOnClickListener(this);
        ((RadioGroup) findViewById(R.id.rg_skin_quality_choose_q1)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.rg_skin_quality_choose_q2)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.rg_skin_quality_choose_q3)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.rg_skin_quality_choose_q4)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.rg_skin_quality_choose_q5)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.rg_skin_quality_choose_q6)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.rg_skin_quality_choose_q7)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.rg_skin_quality_choose_q8)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.rg_skin_quality_choose_q9)).setOnCheckedChangeListener(this);

        tv_skin_quality_head_title = (TextView) findViewById(R.id.tv_skin_quality_head_title);
        tv_skin_quality_right_face_title = (TextView) findViewById(R.id.tv_skin_quality_right_face_title);
        tv_skin_quality_left_face_title = (TextView) findViewById(R.id.tv_skin_quality_left_face_title);
        tv_skin_quality_mandible_title = (TextView) findViewById(R.id.tv_skin_quality_mandible_title);
        tv_skin_quality_nose_title = (TextView) findViewById(R.id.tv_skin_quality_nose_title);
    }

    @Override
    protected void setView() {
        ScreenUtil.initStatusBar(this, R.color.project_color);
        rl_skin_quality_board.post(new Runnable() {
            @Override
            public void run() {
                int height = ll_skin_quality_topic.getHeight();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rl_skin_quality_board.getLayoutParams();
                params.topMargin = height + ScreenUtil.dip2px(getApplicationContext(), 20);
                rl_skin_quality_board.setLayoutParams(params);
                RelativeLayout.LayoutParams lineParams = (RelativeLayout.LayoutParams) v_center_line.getLayoutParams();
                lineParams.height = height + ScreenUtil.dip2px(getApplicationContext(), 25);
                v_center_line.setLayoutParams(lineParams);
            }
        });
        EventBus.getDefault().register(this);
        for (int i = 1; i < 12; i++) {
            roundValues.add(i + "");
        }
        findTextView((ViewGroup) getWindow().getDecorView());
    }

    @Override
    protected void onClickView(View view) {
        switch (view.getId()) {
            case R.id.rpb_skin_quality_head:
            case R.id.rpb_skin_quality_right_face:
            case R.id.rpb_skin_quality_left_face:
            case R.id.rpb_skin_quality_left_mandible:
            case R.id.rpb_skin_quality_left_nose:
                changeColor(view);
                break;
        }
    }

    private void findView(List<String> values, int value) {
        for (RoundProgressBar roundProgressBar : roundProgressBars) {
            changeColor(roundProgressBar, getResources().getColor(android.R.color.white), null, getResources().getColor(R.color.project_color));
            if (values.contains(roundProgressBar.getTag() + "")) {
                changeColor(roundProgressBar, getResources().getColor(R.color.health_good), null, getResources().getColor(android.R.color.white));
            }
//            for(Integer checkId : checkPositions){
//                switch (checkId){
//                    case 1:{
//                        if ("1".equals(roundProgressBar.getTag() + "")) {
//                            changeColor(roundProgressBar, getResources().getColor(R.color.health_good), null, getResources().getColor(android.R.color.white));
//                        } else if ("2".equals(roundProgressBar.getTag() + "")) {
//                            changeColor(roundProgressBar, getResources().getColor(R.color.health_good), null, getResources().getColor(android.R.color.white));
//                        }
//                    }
//                    break;
//                    case 2:{
//                        if ("3".equals(roundProgressBar.getTag() + "")) {
//                            changeColor(roundProgressBar, getResources().getColor(R.color.health_good), null, getResources().getColor(android.R.color.white));
//                        } else if ("4".equals(roundProgressBar.getTag() + "")) {
//                            changeColor(roundProgressBar, getResources().getColor(R.color.health_good), null, getResources().getColor(android.R.color.white));
//                        }
//                    }
//                    break;
//                    case 3:{
//                        if ("11".equals(roundProgressBar.getTag() + "")) {
//                            changeColor(roundProgressBar, getResources().getColor(R.color.health_good), null, getResources().getColor(android.R.color.white));
//                        }else if ("10".equals(roundProgressBar.getTag() + "")) {
//                            changeColor(roundProgressBar, getResources().getColor(R.color.health_good), null, getResources().getColor(android.R.color.white));
//                        }
//                    }
//                    break;
//                    case 4:{
//                        if ("5".equals(roundProgressBar.getTag() + "")) {
//                            changeColor(roundProgressBar, getResources().getColor(R.color.health_good), null, getResources().getColor(android.R.color.white));
//                        } else if ("6".equals(roundProgressBar.getTag() + "")) {
//                            changeColor(roundProgressBar, getResources().getColor(R.color.health_good), null, getResources().getColor(android.R.color.white));
//                        } else if ("7".equals(roundProgressBar.getTag() + "")) {
//                            changeColor(roundProgressBar, getResources().getColor(R.color.health_good), null, getResources().getColor(android.R.color.white));
//                        }
//                    }
//                    break;
//                    case 5:{
//                        if ("8".equals(roundProgressBar.getTag() + "")) {
//                            changeColor(roundProgressBar, getResources().getColor(R.color.health_good), null, getResources().getColor(android.R.color.white));
//                        } else if ("9".equals(roundProgressBar.getTag() + "")) {
//                            changeColor(roundProgressBar, getResources().getColor(R.color.health_good), null, getResources().getColor(android.R.color.white));
//                        }
//                    }
//                    break;
//                }
//            }
        }
    }

    private void findView(List<String> values) {
        for (RoundProgressBar roundProgressBar : roundProgressBars) {
            changeColor(roundProgressBar, getResources().getColor(android.R.color.white), null, getResources().getColor(R.color.project_color));
            if (values.contains(roundProgressBar.getTag() + "")) {
                changeColor(roundProgressBar, getResources().getColor(R.color.health_good), null, getResources().getColor(android.R.color.white));
            }
        }
    }

    public void changeColor(View view) {
        List<String> values = new ArrayList<>();
        if (!checkPositions.contains(1)) {
            changeColor(rpb_skin_quality_head, getResources().getColor(android.R.color.white), tv_skin_quality_head, getResources().getColor(R.color.project_color));
        }
        if (!checkPositions.contains(3)) {
            changeColor(rpb_skin_quality_left_face, getResources().getColor(android.R.color.white), tv_skin_quality_left_face, getResources().getColor(R.color.project_color));
        }
        if (!checkPositions.contains(4)) {
            changeColor(rpb_skin_quality_left_mandible, getResources().getColor(android.R.color.white), tv_skin_quality_left_mandible, getResources().getColor(R.color.project_color));
        }
        if (!checkPositions.contains(2)) {
            changeColor(rpb_skin_quality_right_face, getResources().getColor(android.R.color.white), tv_skin_quality_right_face, getResources().getColor(R.color.project_color));
        }
        if (!checkPositions.contains(5)) {
            changeColor(rpb_skin_quality_left_nose, getResources().getColor(android.R.color.white), tv_skin_quality_left_nose, getResources().getColor(R.color.project_color));
        }
        switch (view.getId()) {
            case R.id.rpb_skin_quality_head: {
                checkPosition = 1;
                values.add(1 + "");
                values.add(2 + "");
                findView(values, 1);
                changeColor(rpb_skin_quality_head, getResources().getColor(R.color.health_good), tv_skin_quality_head, getResources().getColor(android.R.color.white));
                ToastUtils.show("您检测的是额头");
            }
            break;
            case R.id.rpb_skin_quality_right_face: {
                checkPosition = 2;
                values.add(3 + "");
                values.add(4 + "");
                findView(values, 2);
                changeColor(rpb_skin_quality_right_face, getResources().getColor(R.color.health_good), tv_skin_quality_right_face, getResources().getColor(android.R.color.white));
                ToastUtils.show("您检测的是右脸");
            }
            break;
            case R.id.rpb_skin_quality_left_face: {
                checkPosition = 3;
                values.add(10 + "");
                values.add(11 + "");
                findView(values, 5);
                changeColor(rpb_skin_quality_left_face, getResources().getColor(R.color.health_good), tv_skin_quality_left_face, getResources().getColor(android.R.color.white));
                ToastUtils.show("您检测的是左脸");
            }
            break;
            case R.id.rpb_skin_quality_left_mandible: {
                checkPosition = 4;
                values.add(5 + "");
                values.add(6 + "");
                values.add(7 + "");
                findView(values, 3);
                changeColor(rpb_skin_quality_left_mandible, getResources().getColor(R.color.health_good), tv_skin_quality_left_mandible, getResources().getColor(android.R.color.white));
                ToastUtils.show("您检测的是下颚");
            }
            break;
            case R.id.rpb_skin_quality_left_nose: {
                checkPosition = 5;
                values.add(8 + "");
                values.add(9 + "");
                findView(values, 4);
                changeColor(rpb_skin_quality_left_nose, getResources().getColor(R.color.health_good), tv_skin_quality_left_nose, getResources().getColor(android.R.color.white));
                ToastUtils.show("您检测的是鼻子");
            }
            break;
        }
    }

    public void changeColor(RoundProgressBar roundProgressBar, int cricleColor, TextView textView, int textColor) {
        roundProgressBar.setCricleColor(cricleColor);
        if (textView != null)
            textView.setTextColor(textColor);
        if (textColor == getResources().getColor(android.R.color.white)) {
            roundProgressBar.setProgress(0);
        } else {
            roundProgressBar.setProgress(100);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (checkPositions.size() < 5 && i != -1) {
            ToastUtils.show("请检测部位/目标效果");
            radioGroup.check(-1);
            return;
        }
        int value = -1;
        switch (radioGroup.getId()) {
            case R.id.rg_skin_quality_choose_q1:
                switch (i) {
                    case R.id.rb_skin_quality_q1_a:
                        value = 1;
                        break;
                    case R.id.rb_skin_quality_q1_b:
                        value = 2;
                        break;
                    case R.id.rb_skin_quality_q1_c:
                        value = 3;
                        break;
                    case R.id.rb_skin_quality_q1_d:
                        value = 4;
                        break;
                    case R.id.rb_skin_quality_q1_e:
                        value = 5;
                        break;
                }
                break;
            case R.id.rg_skin_quality_choose_q2:
                switch (i) {
                    case R.id.rb_skin_quality_q2_a:
                        value = 1;
                        break;
                    case R.id.rb_skin_quality_q2_b:
                        value = 2;
                        break;
                    case R.id.rb_skin_quality_q2_c:
                        value = 3;
                        break;
                    case R.id.rb_skin_quality_q2_d:
                        value = 4;
                        break;
                }
                break;
            case R.id.rg_skin_quality_choose_q3:
                switch (i) {
                    case R.id.rb_skin_quality_q3_a:
                        value = 1;
                        break;
                    case R.id.rb_skin_quality_q3_b:
                        value = 2;
                        break;
                    case R.id.rb_skin_quality_q3_c:
                        value = 3;
                        break;
                    case R.id.rb_skin_quality_q3_d:
                        value = 4;
                        break;
                    case R.id.rb_skin_quality_q3_e:
                        value = 5;
                        break;
                }
                break;
            case R.id.rg_skin_quality_choose_q4:
                switch (i) {
                    case R.id.rb_skin_quality_q4_a:
                        value = 1;
                        break;
                    case R.id.rb_skin_quality_q4_b:
                        value = 2;
                        break;
                    case R.id.rb_skin_quality_q4_c:
                        value = 3;
                        break;
                    case R.id.rb_skin_quality_q4_d:
                        value = 4;
                        break;
                }
                break;
            case R.id.rg_skin_quality_choose_q5:
                switch (i) {
                    case R.id.rb_skin_quality_q5_a:
                        value = 1;
                        break;
                    case R.id.rb_skin_quality_q5_b:
                        value = 2;
                        break;
                    case R.id.rb_skin_quality_q5_c:
                        value = 3;
                        break;
                    case R.id.rb_skin_quality_q5_d:
                        value = 4;
                        break;
                }
                break;
            case R.id.rg_skin_quality_choose_q6:
                switch (i) {
                    case R.id.rb_skin_quality_q6_a:
                        value = 1;
                        break;
                    case R.id.rb_skin_quality_q6_b:
                        value = 2;
                        break;
                    case R.id.rb_skin_quality_q6_c:
                        value = 3;
                        break;
                    case R.id.rb_skin_quality_q6_d:
                        value = 4;
                        break;
                    case R.id.rb_skin_quality_q6_e:
                        value = 5;
                        break;
                }
                break;
            case R.id.rg_skin_quality_choose_q7:
                switch (i) {
                    case R.id.rb_skin_quality_q7_a:
                        value = 1;
                        break;
                    case R.id.rb_skin_quality_q7_b:
                        value = 2;
                        break;
                    case R.id.rb_skin_quality_q7_c:
                        value = 3;
                        break;
                    case R.id.rb_skin_quality_q7_d:
                        value = 4;
                        break;
                    case R.id.rb_skin_quality_q7_e:
                        value = 5;
                        break;
                }
                break;
            case R.id.rg_skin_quality_choose_q8:
                switch (i) {
                    case R.id.rb_skin_quality_q8_a:
                        value = 1;
                        break;
                    case R.id.rb_skin_quality_q8_b:
                        value = 2;
                        break;
                    case R.id.rb_skin_quality_q8_c:
                        value = 3;
                        break;
                    case R.id.rb_skin_quality_q8_d:
                        value = 4;
                        break;
                    case R.id.rb_skin_quality_q8_e:
                        value = 5;
                        break;
                }
                break;
            case R.id.rg_skin_quality_choose_q9: {
                switch (i) {
                    case R.id.rb_skin_quality_q9_a:
                        value = 1;
                        break;
                    case R.id.rb_skin_quality_q9_b:
                        value = 2;
                        break;
                    case R.id.rb_skin_quality_q9_c:
                        value = 3;
                        break;
                    case R.id.rb_skin_quality_q9_d:
                        value = 4;
                        break;
                    case R.id.rb_skin_quality_q9_e:
                        value = 5;
                        break;
                }
            }
            break;
        }
        calculateWater(radioGroup, value);
    }

    int q2 = -1;

    private void calculateWater(RadioGroup radioGroup, int position) {
        float result = -1;
        switch (position) {
            case 1:
            case 2:
            case 3:
            case 4: {
                result = position;
            }
            break;
            case 5: {
                result = 2.5f;
            }
            break;
        }
        switch (radioGroup.getId()) {
            case R.id.rg_skin_quality_choose_q1:
                sumMap.put("q1", result);
                break;
            case R.id.rg_skin_quality_choose_q2:
                sumMap.put("q2", result);
                q2 = position;
                break;
            case R.id.rg_skin_quality_choose_q3:
                sumMap.put("q3", result);
                break;
            case R.id.rg_skin_quality_choose_q4:
                sumMap.put("q4", result);
                break;
            case R.id.rg_skin_quality_choose_q5:
                sumMap.put("q5", result);
                break;
            case R.id.rg_skin_quality_choose_q6:
                allergyMap.put("q6", result);
                break;
            case R.id.rg_skin_quality_choose_q7:
                allergyMap.put("q7", result);
                break;
            case R.id.rg_skin_quality_choose_q8:
                allergyMap.put("q8", result);
                break;
            case R.id.rg_skin_quality_choose_q9:
                allergyMap.put("q9", result);
                break;
        }
        if (!sumMap.containsKey("q1")) {
            return;
        }
        if (!sumMap.containsKey("q2")) {
            return;
        }
        if (!sumMap.containsKey("q3")) {
            return;
        }
        if (!sumMap.containsKey("q4")) {
            return;
        }
        if (!sumMap.containsKey("q5")) {
            return;
        }
        if (!allergyMap.containsKey("q6")) {
            return;
        }
        if (!allergyMap.containsKey("q7")) {
            return;
        }
        if (!allergyMap.containsKey("q8")) {
            return;
        }
        if (!allergyMap.containsKey("q9")) {
            return;
        }
        String skinResult = "";
        if (q2 == 2) {
            skinResult = "中性皮肤";
        } else if (q2 == 3) {
            skinResult = "混合性皮肤";
        } else {
            float skinSum = 0;
            for (Map.Entry<String, Float> skin : sumMap.entrySet()) {
                skinSum += skin.getValue();
            }
            if (skinSum > 13) {
                skinResult = "油性皮肤";
            } else {
                skinResult = "干性皮肤";
            }
        }
        String allergyResult = "";
        float allergySum = 0;
        for (Map.Entry<String, Float> allergy : allergyMap.entrySet()) {
            allergySum += allergy.getValue();
        }
        if (allergySum > 8) {
            allergyResult = "敏感性皮肤";
        } else {
            allergyResult = "耐受性皮肤";
        }
        String belong = "";
        String advice = "";
        switch (allergyResult) {
            case "敏感性皮肤": {
                switch (skinResult) {
                    case "油性皮肤": {
                        belong = "敏感油性皮肤";
                        advice = "敏感油性皮肤需要加强清洁，建议使用去污力较强、适合痤疮皮肤的洗面奶，每日清洁2次，不要过度清洁，" +
                                "否则反而会造成皮肤屏障的伤害。使用控油保湿的护肤品。注意饮食调解，避免甜食、奶制品、" +
                                "油脂食品、肥腻食品、辛辣食品、刺激性食品(生葱、生蒜、洋葱、韭菜、浓茶、咖啡)。";
                    }
                    break;
                    case "干性皮肤": {
                        belong = "敏感干性皮肤";
                        advice = "敏感干性皮肤日常里需要补充充足的水分，洗脸时使用补水的洁面乳。敏感干性皮肤应该多吃富含维生素A、维生素C。饮食尽量多选择一些维生素含量高的和脂肪含量高的食物，" +
                                "比如鸡蛋、鱼、牛奶等都可以经常使用，多吃脂肪类的食物，新鲜的水果。对于敏感肤质，找到造成过敏的产品或成份，并停止使用。" +
                                "若无找出过敏物质类固醇药膏是最好的疗法。类固醇药膏主要是减少发炎症状，但必需小心使用，皮肤刺激发生时，" +
                                "在患处使用类固醇药膏，只要几天后，皮肤就回复正常状态。但长时间使用，类固醇药膏会伤害皮肤。";
                    }
                    break;
                    case "中性皮肤": {
                        belong = "敏感性皮肤";
                        advice = "时刻注意保持皮肤清洁，可用温和的洗面奶及柔肤水，帮助杀菌、清洁、柔软肌肤。不建议更改往日用惯的化妆品品牌，随意更改品牌的话，" +
                                "很容易出现过敏。注意皮肤的保湿，可选用清爽型、亲水性护肤品，含油多的护肤品应尽量少用。注意风沙对皮肤的影响，" +
                                "平时皮肤较敏感的人外出时尤其要注意用纱巾、口罩等遮挡，避免风吹。饮食上多吃新鲜水果、蔬菜，少食刺激性强、" +
                                "易引起过敏反应的食物如海鲜、笋类等。注意阳光、气候、水、植物(花粉)、化妆品、香水、蚊虫叮咬及高蛋白食物都有可能导致过敏。";
                    }
                    break;
                    case "混合性皮肤": {
                        belong = "敏感混合性皮肤";
                        advice = "在平日里须注意皮肤要补水保湿。日常出门建议随身携带保湿喷雾，用化妆棉进行清洁。不建议更改往日用惯的化妆品品牌，" +
                                "随意更改品牌的话，很容易出现过敏。可选用清爽型、亲水性护肤品，增强皮肤的抵抗力，含油多的护肤品应尽量少用。" +
                                "注意风沙对皮肤的影响，平时皮肤较敏感的人外出时尤其要注意用纱巾、口罩等遮挡，避免风吹。饮食上注意补充维生素C，" +
                                "多吃新鲜水果、蔬菜，少食刺激性强、易引起过敏反应的食物如海鲜、笋类等。当接触到刺激性物质就会引发肌肤的问题。" +
                                "对阳光、气候、水、植物(花粉)、化妆品、香水、蚊虫叮咬及高蛋白食物都有可能导致过敏。";
                    }
                    break;
                }
            }
            break;
            case "耐受性皮肤": {
                switch (skinResult) {
                    case "油性皮肤": {
                        belong = "油性皮肤";
                        advice = "每天早晚彻底清洁皮肤，选用减少油脂分泌，缩小毛孔的护肤品。洗完脸后可用一些孕妇专用收敛水。晚上洁面后，入睡前最好不用护肤品，以保持皮肤的正常排泄通畅，" +
                                "当然可适当地按摩，以改善皮肤的血液循环。饮食上要注意少食含脂肪、糖类高的食物，忌过食烟酒及辛辣食物、应多食水果蔬菜，以改善皮肤的油腻粗糙感。";
                    }
                    break;
                    case "干性皮肤": {
                        belong = "干性皮肤";
                        advice = "在平日里要保证皮肤得到充足的水分。同时需要选用亲水性高、含保湿因子洁面乳与化妆水。早晚各一次，温和清洁肌肤，" +
                                "注意手法轻柔。建议使用具有保湿滋养功效的眼霜，每天早晚都均匀涂在眼睛周围肌肤上。在饮食中要注意选择一些脂肪、维生素含量高的食物，" +
                                "例如牛奶、南瓜、新鲜水果等。在户外记得做好防晒保湿工作。";
                    }
                    break;
                    case "中性皮肤": {
                        belong = "中性皮肤";
                        advice = "在平日里注意好补水保湿，使油分和水分达到平衡即可。可使用对皮肤有滋润作用的护肤品。注意日常的皮肤保养工作。" +
                                "建议晚上使用眼霜或眼部凝胶，保养脸部肌肤。在饮食上要注意补充皮肤所需的维生素和蛋白质,如水果、蔬菜、牛奶、豆制品等,避免烟、" +
                                "酒及辛辣食物的刺激。在户外要做好保护工作，注意防晒防燥。";
                    }
                    break;
                    case "混合性皮肤": {
                        belong = "混合性皮肤";
                        advice = "在平日里须注意皮肤要补水保湿。在选择护肤品方面，最好选择两款补水系列的护肤品，一方面针对T区部位，一方面针对脸颊两侧的肌肤。" +
                                "在洗脸后可擦点清爽型的乳液。对于喜爱化妆女生来说，一定要及时的将脸部的妆容清洁干净，清洁不彻底就会出现长痘、粉刺的现象。" +
                                "日常出门建议随身携带保湿喷雾，用化妆棉进行清洁，同时注意防晒。在饮食上注意补充维生素C，多吃橙子，能帮助减少皮肤的黑色素。";
                    }
                    break;
                }
            }
            break;
        }
        tv_skin_quality_belong.setText(belong + "");
        tv_skin_quality_advice.setText(advice + "");
    }

    ExecutorService pool = Executors. newSingleThreadExecutor();
    int location = 0;
    boolean canStart = true;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(EventModel eventModel) {
        if (eventModel != null) {
            if ("read".equals(eventModel.type)) {
                canStart = false;
                tv_skin_quality_belong.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        canStart = true;
                    }
                }, 1500);
                if (checkPosition == -1) {
                    return;
                }
                ToastUtils.show("检测完成");
                byte[] array = (byte[]) eventModel.eventBus;
                byte[] value = {array[2], array[3], array[4], array[5]};
                int v2 = value[1] & 0xFF;
                int v3 = value[2] & 0xFF;
                if (v2 == 0) {
                    CustomDialogHelper.OneButtonDialog(this, "提示", "您测试的数据不对哦，请重新检测", "关闭", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    return;
                }
                StorageUtil.addRecordMode(new RecordModel(DateUtils.getStringDateAndTimeFromDate(new Date()), v2, v3));
                if (!checkPositions.contains(checkPosition)) {
                    checkPositions.add(checkPosition);
                }
                switch (checkPosition) {
                    case 1: {
                        tv_skin_quality_head_title.setText(Html.fromHtml("<b>" + changeWaterText(v2) + "<br/>" + changeOilText(v3) + "</b>"));
                    }
                    break;
                    case 2: {
                        tv_skin_quality_right_face_title.setText(Html.fromHtml("<b>" + changeWaterText(v2) + "<br/>" + changeOilText(v3) + "</b>"));
                    }
                    break;
                    case 3: {
                        tv_skin_quality_left_face_title.setText(Html.fromHtml("<b>" + changeWaterText(v2) + "<br/>" + changeOilText(v3) + "</b>"));
                    }
                    break;
                    case 4: {
                        tv_skin_quality_mandible_title.setText(Html.fromHtml("<b>" + changeWaterText(v2) + "<br/>" + changeOilText(v3) + "</b>"));
                    }
                    break;
                    case 5: {
                        tv_skin_quality_nose_title.setText(Html.fromHtml("<b>" + changeWaterText(v2) + "<br/>" + changeOilText(v3) + "</b>"));
                    }
                    break;
                }
                if (checkPositions.size() == 5) {
                    CustomDialogHelper.OneButtonDialog(this, "提示", "您已经完成脸部测试，可以进一步测试皮肤问题", "关闭", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
//                ToastUtils.show("测试完成，请查看测试结果");
            } else if ("start".equals(eventModel.type)) {
                if (checkPosition == -1) {
                    ToastUtils.show("请选择要检测的部位");
                    return;
                }
                ToastUtils.show("请用触头轻按皮肤部位5秒");
                location = checkPosition;
                if(location == 3){
                    location = 0;
                }else if(location == 4){
                    location = 3;
                }
                canStart = false;
                pool.execute(new Thread(runnable));
                pool.execute(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(8000);
                            canStart = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }));
            } else if ("error".equals(eventModel.type)) {
                ToastUtils.show("检测失败，请重新检测");
                canStart = false;
                tv_skin_quality_belong.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        canStart = true;
                    }
                }, 1500);
            }
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            canStart = true;
            while (canStart) {
                try {
                    Thread.sleep(1000);
                    location++;
                    if (!canStart) {
                        location = checkPosition;
                        if(location == 3){
                            location = 5;
                        }else if(location == 4){
                            location = 3;
                        }else if(location == 5){
                            location = 4;
                        }
                    }
                    List<String> values = new ArrayList<String>();
                    switch (location) {
                        case 1: {
                            values.add(1 + "");
                            values.add(2 + "");
                        }
                        break;
                        case 2: {
                            values.add(3 + "");
                            values.add(4 + "");
                        }
                        break;
                        case 3: {
                            values.add(5 + "");
                            values.add(6 + "");
                            values.add(7 + "");
                        }
                        break;
                        case 4: {
                            values.add(8 + "");
                            values.add(9 + "");
                        }
                        break;
                        case 6:
                        case 5: {
                            values.add(10 + "");
                            values.add(11 + "");
                        }
                        break;
                    }
                    findView(values);
                    if (location >= 5) {
                        location = 0;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private String changeWaterText(int value) {
        String result = "";
        if (value <= 35) {
            result = "严重缺水";
        } else if (value <= 40) {
            result = "稍微缺水";
        } else if (value <= 60) {
            result = "水分正常";
        } else if (value > 60) {
            result = "十分湿润";
        }
        return result;
    }

    private String changeOilText(int value) {
        String result = "";
        if (value <= 16) {
            result = "稍微缺油";
        } else if (value <= 30) {
            result = "油分正常";
        } else if (value > 30) {
            result = "稍微偏油";
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
