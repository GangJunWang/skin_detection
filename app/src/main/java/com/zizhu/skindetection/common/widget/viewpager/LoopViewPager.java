package com.zizhu.skindetection.common.widget.viewpager;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 可以循环滚动的viewpager banner广告栏
 * A ViewPager subclass enabling infinte scrolling of the viewPager elements
 *
 * realPosition=(position-1)%count [0->3, 1->0, 2->1, 3->2, 4->3, 5->0]
 */
public class LoopViewPager extends ViewPager {

	private static final boolean DEFAULT_BOUNDARY_CASHING = false;

	OnPageChangeListener mOuterPageChangeListener;
	private LoopViewPagerAdapterWrapper mAdapter;
	private boolean mBoundaryCaching = DEFAULT_BOUNDARY_CASHING;

	/**
	 * helper function which may be used when implementing FragmentPagerAdapter
	 * 
	 * @param position
	 * @param count
	 * @return (position-1)%count
	 */
	public static int toRealPosition(int position, int count) {
		position = position - 1;
		if (position < 0) {
			position += count;
		} else {
			position = position % count;
		}
		return position;
	}

	/**
	 * If set to true, the boundary views (i.e. first and last) will never be
	 * destroyed This may help to prevent "blinking" of some views
	 * 
	 * @param flag
	 */
	public void setBoundaryCaching(boolean flag) {
		mBoundaryCaching = flag;
		if (mAdapter != null) {
			mAdapter.setBoundaryCaching(flag);
		}
	}

	@Override
	public void setAdapter(PagerAdapter adapter) {
		mAdapter = new LoopViewPagerAdapterWrapper(adapter);
		mAdapter.setBoundaryCaching(mBoundaryCaching);
		super.setAdapter(mAdapter);
		setCurrentItem(0, false);
		startImageTimerTask();
	}

	@Override
	public PagerAdapter getAdapter() {
		return mAdapter != null ? mAdapter.getRealAdapter() : mAdapter;
	}

	@Override
	public int getCurrentItem() {
		return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
	}

	public void setCurrentItem(int item, boolean smoothScroll) {
		int realItem = mAdapter.toInnerPosition(item);
		super.setCurrentItem(realItem, smoothScroll);
	}

	@Override
	public void setCurrentItem(int item) {
		if (getCurrentItem() != item) {
			setCurrentItem(item, true);
		}
	}

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		mOuterPageChangeListener = listener;
	};

	public LoopViewPager(Context context) {
		super(context);
		init();
	}

	public LoopViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		super.setOnPageChangeListener(onPageChangeListener);
	}

	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
		private float mPreviousOffset = -1;
		private float mPreviousPosition = -1;

		@Override
		public void onPageSelected(int position) {

			int realPosition = mAdapter.toRealPosition(position);
			if (mPreviousPosition != realPosition) {
				mPreviousPosition = realPosition;
				if (mOuterPageChangeListener != null) {
					mOuterPageChangeListener.onPageSelected(realPosition);
				}
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			int realPosition = position;
			if (mAdapter != null) {
				realPosition = mAdapter.toRealPosition(position);

				if (positionOffset == 0 && mPreviousOffset == 0 && (position == 0 || position == mAdapter.getCount() - 1)) {
					setCurrentItem(realPosition, false);
				}
			}

			mPreviousOffset = positionOffset;
			if (mOuterPageChangeListener != null) {
				if (realPosition != mAdapter.getRealCount() - 1) {
					mOuterPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
				} else {
					if (positionOffset > .5) {
						mOuterPageChangeListener.onPageScrolled(0, 0, 0);
					} else {
						mOuterPageChangeListener.onPageScrolled(realPosition, 0, 0);
					}
				}
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if (mAdapter != null) {
				int position = LoopViewPager.super.getCurrentItem();
				int realPosition = mAdapter.toRealPosition(position);
				if (state == ViewPager.SCROLL_STATE_IDLE && (position == 0 || position == mAdapter.getCount() - 1)) {
					setCurrentItem(realPosition, false);
				}
			}
			if (mOuterPageChangeListener != null) {
				mOuterPageChangeListener.onPageScrollStateChanged(state);
			}
		}
	};

	/**
	 * 触摸停止计时器，抬起启动计时器
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			// 开始图片滚动
			startImageTimerTask();
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// 停止图片滚动
			stopImageTimerTask();
		}
		return super.dispatchTouchEvent(event);
	}

	/**
	 * 图片滚动任务
	 */
	private void startImageTimerTask() {
		isStop = false;
		// 图片滚动
		mHandler.postDelayed(mImageTimerTask, 5000);
	}

	private boolean isStop;

	/**
	 * 停止图片滚动任务
	 */
	private void stopImageTimerTask() {
		isStop = true;
		mHandler.removeCallbacks(mImageTimerTask);
	}

	private Handler mHandler = new Handler();

	/**
	 * 图片自动轮播Task
	 */
	private Runnable mImageTimerTask = new Runnable() {
		@Override
		public void run() {
			if (mAdapter != null) {
				int currentItem = getCurrentItem();
				setCurrentItem(currentItem + 1);
				if (!isStop) { // if isStop=true //当你退出后 要把这个给停下来 不然 这个一直存在
								// 就一直在后台循环
					mHandler.postDelayed(mImageTimerTask, 5000);
				}

			}
		}
	};
}
