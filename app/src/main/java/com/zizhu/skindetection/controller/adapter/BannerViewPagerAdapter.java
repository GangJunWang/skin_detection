package com.zizhu.skindetection.controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zizhu.skindetection.R;
import com.zizhu.skindetection.common.model.AdsInfo;
import com.zizhu.skindetection.common.utils.CommonUtil;

import java.util.List;

/**
 * 广告适配器
 *
 * @author JeremyAiYt
 */
public class BannerViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<AdsInfo> banners;

    public BannerViewPagerAdapter() {
    }

    public BannerViewPagerAdapter(Context context,
                                  List<AdsInfo> Banners) {
        this.context = context;
        this.banners = Banners;

    }

    public void changeData(List<AdsInfo> Banners) {
        this.banners = Banners;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return banners.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(View container, final int position) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_viewpager_banner, null);

        final ImageView photoView = (ImageView) view
                .findViewById(R.id.item_room_image);
        if (banners.size() > 0) {
            //广告比例1:3
//            photoView.setHeightRatio(MConstants.RATIO_POINT_BANNER);
            final AdsInfo adsInfo = banners.get(position);
            // ImageLoaderKit.getInstance().displayImage(adsInfo.resource, photoView);
            photoView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), adsInfo.resId));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        if(position == 0){
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!CommonUtil.isClickSoFast(1000)) {
                        CommonUtil.startCommonBrowserForPage((Activity) context, "https://shop18690443.koudaitong.com/v2/goods/2xho9icck5ocz?reft=1482143511089_1482143584909&spm=g321266073_f46103965&sf=wx_sm&form=singlemessage&oid=0&from=singlemessage&isappinstalled=0");
                    }
                }
            });
        }

        ((ViewGroup) container).addView(photoView);
        return photoView;
    }

}