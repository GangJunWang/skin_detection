package com.zizhu.skindetection.controller.fragment;


import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zizhu.skindetection.R;
import com.zizhu.skindetection.base.BaseApplication;
import com.zizhu.skindetection.base.fragment.BaseFragment;
import com.zizhu.skindetection.common.model.AdsInfo;
import com.zizhu.skindetection.common.utils.DisplayUtil;
import com.zizhu.skindetection.common.utils.ScreenUtil;
import com.zizhu.skindetection.common.widget.viewpager.CirclePageIndicator;
import com.zizhu.skindetection.common.widget.viewpager.LoopViewPager;
import com.zizhu.skindetection.controller.SkinCareActivity;
import com.zizhu.skindetection.controller.SkinQualityActivity;
import com.zizhu.skindetection.controller.SkinQualityPKActivity;
import com.zizhu.skindetection.controller.WaterOilActivity;
import com.zizhu.skindetection.controller.adapter.BannerViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

//  首页 测试肌肤问题
public class MainFragmen extends BaseFragment {

    //banner广告
    private RelativeLayout bannerViewPagerLayout;
    private LoopViewPager bannerViewPager;
    private CirclePageIndicator bannerCirclePageIndicator;
    private BannerViewPagerAdapter bannerViewPagerAdapter;
    private List<AdsInfo> dataList = new ArrayList<>();
    String model = "";

    // 功能
    private View ll_water_oil_layout;
    private View ll_skin_care_layout;
    private View ll_skin_layout;
    private View ll_skin_test_layout;

    public static MainFragmen newInstance() {
        MainFragmen fragment = new MainFragmen();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initWidget(View rootView) {
        model = android.os.Build.MODEL;
        bannerViewPagerLayout = (RelativeLayout) rootView.findViewById(R.id.tab_banner_viewpager_layout);
        bannerViewPager = (LoopViewPager) rootView.findViewById(R.id.tab_banner_viewpager);
        bannerCirclePageIndicator = (CirclePageIndicator) rootView.findViewById(R.id.tab_banner_viewpager_indicator);
        //设置banner的高度
        int width = ScreenUtil.getScreenWidth(context);
        if (BaseApplication.getInstance().hasNavigtionBar(getActivity())) {
            bannerViewPagerLayout.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (1280.f * (470.f / 1280.f))));
        }else{
            bannerViewPagerLayout.setLayoutParams(new LinearLayout.LayoutParams(width, (width * 8) / 12));
        }

        dataList.add(new AdsInfo(R.mipmap.bannar1));
        dataList.add(new AdsInfo(R.mipmap.bannar2));
        dataList.add(new AdsInfo(R.mipmap.bannar3));
        bannerViewPagerAdapter = new BannerViewPagerAdapter(
                context, dataList);
        bannerViewPager.setAdapter(bannerViewPagerAdapter);
        bannerViewPager.setBoundaryCaching(true);
        bannerCirclePageIndicator.setViewPager(bannerViewPager);

        ll_water_oil_layout = rootView.findViewById(R.id.ll_water_oil_layout);
        ll_skin_care_layout = rootView.findViewById(R.id.ll_skin_care_layout);
        ll_skin_layout = rootView.findViewById(R.id.ll_skin_layout);
        ll_skin_test_layout = rootView.findViewById(R.id.ll_skin_test_layout);
        findTextView((ViewGroup) rootView);
    }

    private void findTextView(ViewGroup viewGroup){
        for(int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                findTextView((ViewGroup) viewGroup.getChildAt(i));
            }
            if ((viewGroup.getChildAt(i) instanceof TextView)) {
                TextView textView = (TextView) viewGroup.getChildAt(i);
                Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/lantinghei.ttf");
                textView.setTypeface(face);
            }
            if ((viewGroup.getChildAt(i) instanceof ImageView)) {
                ImageView imageView = (ImageView) viewGroup.getChildAt(i);
                if (imageView.getTag() != null && "2".equals(imageView.getTag() + "")) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    if (BaseApplication.getInstance().hasNavigtionBar(getActivity())) {
                        layoutParams.width = DisplayUtil.dip2px(getActivity(), 27);
                        layoutParams.height = DisplayUtil.dip2px(getActivity(), 27);
                        layoutParams.leftMargin = DisplayUtil.dip2px(getActivity(), 10);
                        layoutParams.rightMargin = DisplayUtil.dip2px(getActivity(), 10);
                        layoutParams.topMargin = DisplayUtil.dip2px(getActivity(), 10);
                        layoutParams.bottomMargin = DisplayUtil.dip2px(getActivity(), 10);
                    } else {
                        layoutParams.leftMargin = DisplayUtil.dip2px(getActivity(), 15);
                        layoutParams.rightMargin = DisplayUtil.dip2px(getActivity(), 15);
                        layoutParams.topMargin = DisplayUtil.dip2px(getActivity(), 15);
                        layoutParams.bottomMargin = DisplayUtil.dip2px(getActivity(), 15);
                    }
                    imageView.setLayoutParams(layoutParams);
                }
            }
            if ((viewGroup.getChildAt(i) instanceof LinearLayout)) {
                LinearLayout linearLayout = (LinearLayout) viewGroup.getChildAt(i);
                if (linearLayout.getTag() != null) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                    if (BaseApplication.getInstance().hasNavigtionBar(getActivity())) {
                        if(Build.VERSION.SDK_INT >= 19) {
                            if("2".equals(linearLayout.getTag() + "")){
                                layoutParams.topMargin = DisplayUtil.dip2px(getActivity(), 22);
                            }else {
                                layoutParams.bottomMargin = DisplayUtil.dip2px(getActivity(), 22);
                            }
                        }
                    }
                    linearLayout.setLayoutParams(layoutParams);
                }
            }
        }
    }

    @Override
    protected void bindEven() {

        ll_water_oil_layout.setOnClickListener(this);
        ll_skin_care_layout.setOnClickListener(this);
        ll_skin_layout.setOnClickListener(this);
        ll_skin_test_layout.setOnClickListener(this);
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void onClickView(View view) {
        switch (view.getId()){
            case R.id.ll_water_oil_layout:{
                startActivity(WaterOilActivity.class);
            }
            break;
            case R.id.ll_skin_care_layout:{
                startActivity(SkinCareActivity.class);
            }
            break;
            case R.id.ll_skin_layout:{
                startActivity(SkinQualityActivity.class);
            }
            break;
            case R.id.ll_skin_test_layout:{
                startActivity(SkinQualityPKActivity.class);
            }
            break;
        }
    }

}
