package com.zizhu.skindetection.controller;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.zizhu.skindetection.R;
import com.zizhu.skindetection.base.activity.BaseActivity;
import com.zizhu.skindetection.common.utils.CommonUtil;
import com.zizhu.skindetection.common.utils.ScreenUtil;
import com.zizhu.skindetection.common.widget.viewpager.CirclePageIndicator;

public class AppGuideActivity extends BaseActivity {

    private ViewPager mGuideViewPager;
    private CirclePageIndicator circlePageIndicator;
    private int[] images = new int[]{R.mipmap.guide_01, R.mipmap.guide_02, R.mipmap.guide_03};

    @Override
    protected int getActivityLayout() {
        return R.layout.app_guide_layout;
    }

    @Override
    protected void bindEven() {

    }

    @Override
    protected void setView() {
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        } else {
//            ScreenUtil.initStatusBar(this, R.color.gruid_color);
//        }
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        titleBarView.setVisibility(View.GONE);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.rg_guide);
        mGuideViewPager = (ViewPager) findViewById(R.id.viewpager_guide);
        mGuideViewPager.setAdapter(new GuideAdapter());
        circlePageIndicator.setViewPager(mGuideViewPager);
        circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            boolean isScroll = false;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (isScroll && (position == images.length - 1) && !CommonUtil.isClickSoFast(5000)) {
                    startActivity(MainActivity.class);
                    finish();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                boolean flag = false;
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        flag = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        flag = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (mGuideViewPager.getCurrentItem() == mGuideViewPager.getAdapter().getCount() - 1 && !flag) {
                            isScroll = true;
                        } else {
                            isScroll = false;
                        }
                        flag = true;
                        break;
                }
            }
        });
    }

    @Override
    protected void onClickView(View view) {

    }

    private class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(AppGuideActivity.this).inflate(R.layout.item_viewpager_app_guide, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.guide_image_view);
            imageView.setImageResource(images[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            ImageView imageView = (ImageView) view.findViewById(R.id.guide_image_view);
            imageView.setImageBitmap(null);
            container.removeView(view);
        }
    }
}
