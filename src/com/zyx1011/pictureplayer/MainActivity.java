package com.zyx1011.pictureplayer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ViewPager mPager;
	private List<ImageView> mImgRes;
	private BannerAdapter mAdapter;
	private LinearLayout mPage;
	private TextView mDesc;
	private Handler handler = new Handler();
	private Runnable task = new Runnable() {
		public void run() {
			mPager.setCurrentItem(mPager.getCurrentItem() + 1);
			handler.postDelayed(this, 3000);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		initData();
		initEvent();
	}

	private void initEvent() {
		mAdapter = new BannerAdapter();
		mPager.setAdapter(mAdapter);

		// 设置页面监听器
		mPager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				mDesc.setText(Constans.IMAGES_DES[position % Constans.PIC_RES.length]);

				for (int i = 0; i < mPage.getChildCount(); i++) {
					mPage.getChildAt(i).setSelected(false); // 先让全部为未选中
				}
				mPage.getChildAt(position % Constans.PIC_RES.length).setSelected(true); // 使当前位置为选中
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		mPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					stopAction();
					break;

				case MotionEvent.ACTION_UP:
					startAction();
					break;

				default:
					break;
				}
				return false;
			}
		});

		// 处理左滑
		int index = (Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % Constans.PIC_RES.length);
		mPager.setCurrentItem(index);
		startAction();
	}

	/**
	 * 开始自动轮播
	 */
	private void startAction() {
		handler.postDelayed(task, 3000);
	}

	/**
	 * 移除自动轮播
	 */
	private void stopAction() {
		handler.removeCallbacks(task);
	}

	private void initData() {
		mImgRes = new ArrayList<ImageView>();

		for (int i = 0; i < Constans.PIC_RES.length; i++) {
			// 轮播图片
			ImageView picView = new ImageView(getApplicationContext());
			picView.setImageResource(Constans.PIC_RES[i]);
			picView.setScaleType(ScaleType.FIT_XY);
			mImgRes.add(picView);

			// 点阵
			ImageView dotView = new ImageView(getApplicationContext());
			dotView.setBackgroundResource(R.drawable.dot_selector);
			LayoutParams params = new LayoutParams(DisplayUtils.dp2px(getApplicationContext(), 8),
					DisplayUtils.dp2px(getApplicationContext(), 8));
			params.leftMargin = DisplayUtils.dp2px(getApplicationContext(), 10);
			mPage.addView(dotView, params);

			if (i == 0) { // 默认选中第一个
				dotView.setSelected(true);
			}
		}
	}

	private void initView() {
		mPager = (ViewPager) findViewById(R.id.pager);
		mDesc = (TextView) findViewById(R.id.desc);
		mPage = (LinearLayout) findViewById(R.id.page);

		mDesc.setText(Constans.IMAGES_DES[0]); // 默认显示第一张图片
	}

	private class BannerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE; // 最大值
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView view = mImgRes.get(position % Constans.PIC_RES.length);
			container.addView(view);

			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

}
