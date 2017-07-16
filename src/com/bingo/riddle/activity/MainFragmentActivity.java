package com.bingo.riddle.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.bingo.lantern.R;

public class MainFragmentActivity extends FragmentActivity {
	private RadioGroup rg;
	private RadioButton firstBtn;
	private RadioButton secondBtn;
	private RadioButton thirdBtn;
	private RadioButton forthBtn;
	private FragmentTabHost mFragmentTabhost;
	public static final String SHOW_OF_FIRST_TAG = "first";
	public static final String SHOW_OF_SECOND_TAG = "second";
	public static final String SHOW_OF_THIRD_TAG = "third";
	// public static final String SHOW_OF_forth_TAG = "forth";

	private int SCREEN_WIDTH;

	private float currentX;
	private float preX;

	private List<Fragment> list = new ArrayList<Fragment>();
	private ViewPager mViewPager;

	private boolean isScrolling = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		SCREEN_WIDTH = metrics.widthPixels;
		setContentView(R.layout.activity_main);

		mFragmentTabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		rg = (RadioGroup) findViewById(R.id.tab_rg_menu);
		firstBtn = (RadioButton) findViewById(R.id.tab_rb_1);
		secondBtn = (RadioButton) findViewById(R.id.tab_rb_2);
		thirdBtn = (RadioButton) findViewById(R.id.tab_rb_3);
		// forthBtn = (RadioButton) findViewById(R.id.tab_rb_4);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				SCREEN_WIDTH / 4, LinearLayout.LayoutParams.WRAP_CONTENT);
		mFragmentTabhost.setup(this, getSupportFragmentManager(), R.id.pager);

		TabSpec tabSpec0 = mFragmentTabhost.newTabSpec(SHOW_OF_FIRST_TAG)
				.setIndicator("0");
		TabSpec tabSpec1 = mFragmentTabhost.newTabSpec(SHOW_OF_SECOND_TAG)
				.setIndicator("1");
		TabSpec tabSpec2 = mFragmentTabhost.newTabSpec(SHOW_OF_THIRD_TAG)
				.setIndicator("2");
		// TabSpec tabSpec3 = mFragmentTabhost.newTabSpec(SHOW_OF_forth_TAG)
		// .setIndicator("3");

		mFragmentTabhost.addTab(tabSpec0, GuessFragment.class, null);
		mFragmentTabhost.addTab(tabSpec1, RiddleFragment.class, null);
		mFragmentTabhost.addTab(tabSpec2, SetInfoFragment.class, null);
		// mFragmentTabhost.addTab(tabSpec3, SetInfoFragment.class, null);

		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.tab_rb_1:
					preX = currentX;
					currentX = 0;
					mFragmentTabhost.setCurrentTabByTag(SHOW_OF_FIRST_TAG);
					break;
				case R.id.tab_rb_2:
					preX = currentX;
					currentX = SCREEN_WIDTH * 1 / 3;
					mFragmentTabhost.setCurrentTabByTag(SHOW_OF_SECOND_TAG);
					break;
				case R.id.tab_rb_3:
					preX = currentX;
					currentX = SCREEN_WIDTH * 2 / 3;
					mFragmentTabhost.setCurrentTabByTag(SHOW_OF_THIRD_TAG);
					break;
				// case R.id.tab_rb_4:
				// preX = currentX;
				// currentX = SCREEN_WIDTH * 3 / 4;
				// mFragmentTabhost.setCurrentTabByTag(SHOW_OF_forth_TAG);
				// break;

				default:
					break;
				}
				Animation translateAnimation = new TranslateAnimation(preX,
						currentX, 0, 0);
				translateAnimation.setFillAfter(true);
				translateAnimation.setDuration(1000);
			}
		});

		mFragmentTabhost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				int position = mFragmentTabhost.getCurrentTab();
				mViewPager.setCurrentItem(position);
			}
		});

		mFragmentTabhost.setCurrentTab(0);

		GuessFragment p1 = new GuessFragment();
		RiddleFragment p2 = new RiddleFragment();
		// ProverbFragment p3 = new ProverbFragment();
		SetInfoFragment p3 = new SetInfoFragment();

		list.add(p1);
		list.add(p2);
		list.add(p3);
		// list.add(p4);
		mViewPager.setAdapter(new MenuAdapter(getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(new ViewPagerListener());
	}

	class MenuAdapter extends FragmentPagerAdapter {

		public MenuAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			return list.size();
		}

	}

	class ViewPagerListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
			if (arg0 == 1) {
				isScrolling = true;
			} else {
				isScrolling = false;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int index) {
			if (index == 0) {
				firstBtn.setChecked(true);
			} else if (index == 1) {
				secondBtn.setChecked(true);
			} else if (index == 2) {
				thirdBtn.setChecked(true);
			}
			// else if (index == 3) {
			// forthBtn.setChecked(true);
			// }
			mFragmentTabhost.setCurrentTab(index);
		}
	}

	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		} // 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}

	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			// 获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
}
