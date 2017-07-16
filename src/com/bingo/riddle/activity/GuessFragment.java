package com.bingo.riddle.activity;

import java.util.ArrayList;
import java.util.List;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bingo.lantern.R;
import com.bingo.riddle.db.DBRiddleManager;
import com.bingo.riddle.model.Riddle;
import com.bingo.util.FastClickUtil;
import com.bingo.util.MyDialog;
import com.bingo.util.RandUtil;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.open.GameAppOperation;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.tencent.tool.TecentConstants;
import com.tencent.tool.ThreadManager;
import com.tencent.tool.Util;
import com.wechat.tool.WeChatConstants;

public class GuessFragment extends Fragment implements OnCheckedChangeListener {
	private View rootView;

	private TextView riddleKey;

	private RadioGroup mRadioGroup;
	private RadioButton rand;
	private RadioButton all;
	private RadioButton classic;
	private RadioButton animal;
	private RadioButton love;
	private RadioButton funny;
	private RadioButton child;
	private RadioButton word;
	private RadioButton english;
	private ImageView mImageView;

	private float mCurrentCheckedRadioLeft;// 当前被选中的RadioButton距离左侧的距离
	private HorizontalScrollView mHorizontalScrollView;// 上面的水平滚动控件
	// private ViewPager mViewPager; // 下方的可横向拖动的控件
	// private ArrayList mViews;// 用来存放下方滚动的layout(layout_1,layout_2,layout_3)

	private TextView previous;
	private TextView next;
	private TextView riddleDes;
	private EditText guessKey;
	private Button guess;

	private int i;// 随机谜语id
	private int j;// 猜的次数
	private int firstId;// 获取到的灯谜的条数
	private int lastId;// 获取到的灯谜的条数
	private String kind = "";

	private String shareContent;

	public static String mAppid;

	// IWXAPI 是第三方app和微信通信的openapi接口
	private IWXAPI api;
	// private Boolean isTimelineCb;
	private static final int THUMB_SIZE = 250;
	private static final String SDCARD_ROOT = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	// 关于数据操作
	private DBRiddleManager dbRManager = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.activity_guess, container, false);

		riddleDes = (TextView) rootView.findViewById(R.id.guess_riddleDes);
		riddleKey = (TextView) rootView.findViewById(R.id.riddleKey);
		previous = (TextView) rootView.findViewById(R.id.previous);
		next = (TextView) rootView.findViewById(R.id.next);

		guessKey = (EditText) rootView.findViewById(R.id.guessKey);
		guess = (Button) rootView.findViewById(R.id.guess);

		iniController();
		iniListener();

		rand.setChecked(true);
		mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();

		dbRManager = new DBRiddleManager(getActivity());
		kind = "";
		getRiddleFirstIdByKind(kind);
		i = RandUtil.getRandom(firstId, lastId); // 生成firstId-lastId以内的随机数
		// i = firstId;
		getRiddleDesById(i);

		api = WXAPIFactory.createWXAPI(getActivity(), WeChatConstants.APP_ID,
				true);
		api.registerApp(WeChatConstants.APP_ID);

		mAppid = TecentConstants.APP_ID;
		TecentConstants.mTencent = Tencent
				.createInstance(mAppid, getActivity());
		
		showBanner();

		return rootView;
	}

	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		riddleDes.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				shareContent = riddleDes.getText().toString().trim();
				showListDialog(newtan);
				return true;
			}
		});

		rand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Context context = v.getContext();
				Animation shake = AnimationUtils.loadAnimation(context,
						R.anim.shake);
				v.startAnimation(shake);
				guessKey.setText(null);
				riddleKey.setText(null);
				kind = "";
				getRiddleFirstIdByKind(kind);
				i = RandUtil.getRandom(firstId, lastId); // 生成firstId-lastId以内的随机数
				// System.out.println("===========i====="+i);
				// i=firstId;
				getRiddleDesById(i);

			}

		});

		// 猜谜
		guess.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Context context = v.getContext();
				Animation shake = AnimationUtils.loadAnimation(context,
						R.anim.shake);

				if (!FastClickUtil.isFastClick()) {
					v.startAnimation(shake);
					if (!"".equals(guessKey.getText().toString().trim())) {
						getRiddleKeyById(i);
					} else {
						String message = "谜底不能为空哦！";
						MyDialog.myDialog(getActivity(), message);
					}
				}
			}
		});

		// 获取上一条谜面
		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!FastClickUtil.isFastClick()) {
					guessKey.setText(null);
					riddleKey.setText(null);
					j = 0;
					if (i > firstId) {
						i--;
						getRiddleDesById(i);
					} else {
						String message = "已经是第一条数据了。";
						MyDialog.myDialog(getActivity(), message);
					}
				}
			}
		});

		// 获取下一条谜面
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!FastClickUtil.isFastClick()) {
					guessKey.setText(null);
					riddleKey.setText(null);
					j = 0;
					if (i < lastId) {
						i++;
						getRiddleDesById(i);
					} else {
						String message = "已经是最后一条数据了。";
						MyDialog.myDialog(getActivity(), message);
					}
				}
			}
		});

		// 获取谜底
		riddleKey.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Context context = v.getContext();
				Animation shake = AnimationUtils.loadAnimation(context,
						R.anim.shake);

				if (!FastClickUtil.isFastClick()) {
					v.startAnimation(shake);
					setRiddleKey(i);
				}
			}
		});
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		AnimationSet _AnimationSet = new AnimationSet(true);
		TranslateAnimation _TranslateAnimation;

		Log.i("zj", "checkedid=" + checkedId);
		if (checkedId == R.id.guess_rand) {
			_TranslateAnimation = new TranslateAnimation(
					mCurrentCheckedRadioLeft, getResources().getDimension(
							R.dimen.rdo1), 0f, 0f);
			_AnimationSet.addAnimation(_TranslateAnimation);
			_AnimationSet.setFillBefore(false);
			_AnimationSet.setFillAfter(true);
			_AnimationSet.setDuration(100);

			// mImageView.bringToFront();
			mImageView.startAnimation(_AnimationSet);// 开始上面蓝色横条图片的动画切换
			// mImageView.setLayoutParams(_LayoutParams1);
			// mViewPager.setCurrentItem(1);//
			// 让下方ViewPager跟随上面的HorizontalScrollView切换

			guessKey.setText(null);
			riddleKey.setText(null);
			kind = "";
			getRiddleFirstIdByKind(kind);
			i = RandUtil.getRandom(firstId, lastId); // 生成firstId-lastId以内的随机数
			System.out.println("=====Random======i=====" + i);
			// i=firstId;
			getRiddleDesById(i);
		} else if (checkedId == R.id.guess_all) {
			_TranslateAnimation = new TranslateAnimation(
					mCurrentCheckedRadioLeft, getResources().getDimension(
							R.dimen.rdo2), 0f, 0f);

			_AnimationSet.addAnimation(_TranslateAnimation);
			_AnimationSet.setFillBefore(false);
			_AnimationSet.setFillAfter(true);
			_AnimationSet.setDuration(100);

			// mImageView.bringToFront();
			mImageView.startAnimation(_AnimationSet);

			// mViewPager.setCurrentItem(2);

			guessKey.setText(null);
			riddleKey.setText(null);
			kind = "";
			getRiddleFirstIdByKind(kind);
			// i = RandUtil.getRandom(firstId, lastId); //
			// 生成firstId-lastId以内的随机数
			// System.out.println("===========i====="+i);
			i = firstId;
			getRiddleDesById(i);
		} else if (checkedId == R.id.guess_classic) {
			_TranslateAnimation = new TranslateAnimation(
					mCurrentCheckedRadioLeft, getResources().getDimension(
							R.dimen.rdo3), 0f, 0f);

			_AnimationSet.addAnimation(_TranslateAnimation);
			_AnimationSet.setFillBefore(false);
			_AnimationSet.setFillAfter(true);
			_AnimationSet.setDuration(100);

			// mImageView.bringToFront();
			mImageView.startAnimation(_AnimationSet);

			// mViewPager.setCurrentItem(3);

			guessKey.setText(null);
			riddleKey.setText(null);
			kind = "经典";
			getRiddleFirstIdByKind(kind);
			// i = RandUtil.getRandom(firstId, lastId); //
			// 生成firstId-lastId以内的随机数
			// System.out.println("===========i====="+i);
			i = firstId;
			getRiddleDesById(i);
		} else if (checkedId == R.id.guess_animal) {
			_TranslateAnimation = new TranslateAnimation(
					mCurrentCheckedRadioLeft, getResources().getDimension(
							R.dimen.rdo4), 0f, 0f);

			_AnimationSet.addAnimation(_TranslateAnimation);
			_AnimationSet.setFillBefore(false);
			_AnimationSet.setFillAfter(true);
			_AnimationSet.setDuration(100);

			// mImageView.bringToFront();
			mImageView.startAnimation(_AnimationSet);
			// mViewPager.setCurrentItem(4);

			guessKey.setText(null);
			riddleKey.setText(null);
			kind = "动物";
			getRiddleFirstIdByKind(kind);
			// i = RandUtil.getRandom(firstId, lastId); //
			// 生成firstId-lastId以内的随机数
			// System.out.println("===========i====="+i);
			i = firstId;
			getRiddleDesById(i);
		} else if (checkedId == R.id.guess_love) {
			_TranslateAnimation = new TranslateAnimation(
					mCurrentCheckedRadioLeft, getResources().getDimension(
							R.dimen.rdo5), 0f, 0f);

			_AnimationSet.addAnimation(_TranslateAnimation);
			_AnimationSet.setFillBefore(false);
			_AnimationSet.setFillAfter(true);
			_AnimationSet.setDuration(100);

			// mImageView.bringToFront();
			mImageView.startAnimation(_AnimationSet);

			// mViewPager.setCurrentItem(5);

			guessKey.setText(null);
			riddleKey.setText(null);
			kind = "爱情";
			getRiddleFirstIdByKind(kind);
			// i = RandUtil.getRandom(firstId, lastId); //
			// 生成firstId-lastId以内的随机数
			// System.out.println("===========i====="+i);
			i = firstId;
			getRiddleDesById(i);
		} else if (checkedId == R.id.guess_funny) {
			_TranslateAnimation = new TranslateAnimation(
					mCurrentCheckedRadioLeft, getResources().getDimension(
							R.dimen.rdo6), 0f, 0f);

			_AnimationSet.addAnimation(_TranslateAnimation);
			_AnimationSet.setFillBefore(false);
			_AnimationSet.setFillAfter(true);
			_AnimationSet.setDuration(100);

			// mImageView.bringToFront();
			mImageView.startAnimation(_AnimationSet);

			// mViewPager.setCurrentItem(6);

			guessKey.setText(null);
			riddleKey.setText(null);
			kind = "搞笑";
			getRiddleFirstIdByKind(kind);
			// i = RandUtil.getRandom(firstId, lastId); //
			// 生成firstId-lastId以内的随机数
			// System.out.println("===========i====="+i);
			i = firstId;
			getRiddleDesById(i);
		} else if (checkedId == R.id.guess_child) {
			_TranslateAnimation = new TranslateAnimation(
					mCurrentCheckedRadioLeft, getResources().getDimension(
							R.dimen.rdo7), 0f, 0f);

			_AnimationSet.addAnimation(_TranslateAnimation);
			_AnimationSet.setFillBefore(false);
			_AnimationSet.setFillAfter(true);
			_AnimationSet.setDuration(100);

			// mImageView.bringToFront();
			mImageView.startAnimation(_AnimationSet);

			// mViewPager.setCurrentItem(7);

			guessKey.setText(null);
			riddleKey.setText(null);
			kind = "儿童";
			getRiddleFirstIdByKind(kind);
			// i = RandUtil.getRandom(firstId, lastId); //
			// 生成firstId-lastId以内的随机数
			// System.out.println("===========i====="+i);
			i = firstId;
			getRiddleDesById(i);
		} else if (checkedId == R.id.guess_word) {
			_TranslateAnimation = new TranslateAnimation(
					mCurrentCheckedRadioLeft, getResources().getDimension(
							R.dimen.rdo8), 0f, 0f);

			_AnimationSet.addAnimation(_TranslateAnimation);
			_AnimationSet.setFillBefore(false);
			_AnimationSet.setFillAfter(true);
			_AnimationSet.setDuration(100);

			// mImageView.bringToFront();
			mImageView.startAnimation(_AnimationSet);

			// mViewPager.setCurrentItem(8);

			guessKey.setText(null);
			riddleKey.setText(null);
			kind = "字谜";
			getRiddleFirstIdByKind(kind);
			// i = RandUtil.getRandom(firstId, lastId); //
			// 生成firstId-lastId以内的随机数
			// System.out.println("===========i====="+i);
			i = firstId;
			getRiddleDesById(i);
		} else if (checkedId == R.id.guess_english) {
			_TranslateAnimation = new TranslateAnimation(
					mCurrentCheckedRadioLeft, getResources().getDimension(
							R.dimen.rdo9), 0f, 0f);

			_AnimationSet.addAnimation(_TranslateAnimation);
			_AnimationSet.setFillBefore(false);
			_AnimationSet.setFillAfter(true);
			_AnimationSet.setDuration(100);

			// mImageView.bringToFront();
			mImageView.startAnimation(_AnimationSet);

			// mViewPager.setCurrentItem(9);

			guessKey.setText(null);
			riddleKey.setText(null);
			kind = "英语";
			getRiddleFirstIdByKind(kind);
			// i = RandUtil.getRandom(firstId, lastId); //
			// 生成firstId-lastId以内的随机数
			// System.out.println("===========i====="+i);
			i = firstId;
			getRiddleDesById(i);
		}

		mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();

		Log.i("zj", "getCurrentCheckedRadioLeft="
				+ getCurrentCheckedRadioLeft());
		Log.i("zj", "getDimension=" + getResources().getDimension(R.dimen.rdo2));

		mHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft
				- (int) getResources().getDimension(R.dimen.rdo2), 0);
	}

	private float getCurrentCheckedRadioLeft() {
		// TODO Auto-generated method stub
		if (rand.isChecked()) {
			// Log.i("zj",
			return getResources().getDimension(R.dimen.rdo1);
		} else if (all.isChecked()) {
			// Log.i("zj",
			return getResources().getDimension(R.dimen.rdo2);
		} else if (classic.isChecked()) {
			// Log.i("zj",
			return getResources().getDimension(R.dimen.rdo3);
		} else if (animal.isChecked()) {
			// Log.i("zj",
			return getResources().getDimension(R.dimen.rdo4);
		} else if (love.isChecked()) {
			// Log.i("zj",
			return getResources().getDimension(R.dimen.rdo5);
		} else if (funny.isChecked()) {
			// Log.i("zj",
			return getResources().getDimension(R.dimen.rdo6);
		} else if (child.isChecked()) {
			// Log.i("zj",
			return getResources().getDimension(R.dimen.rdo7);
		} else if (word.isChecked()) {
			// Log.i("zj",
			return getResources().getDimension(R.dimen.rdo8);
		} else if (english.isChecked()) {
			// Log.i("zj",
			return getResources().getDimension(R.dimen.rdo9);
		}
		return 0f;
	}

	private void iniListener() {
		// TODO Auto-generated method stub

		mRadioGroup.setOnCheckedChangeListener(this);

		// mViewPager.setOnPageChangeListener(new
		// MyPagerOnPageChangeListener());
	}

	private void iniController() {
		// TODO Auto-generated method stub
		mRadioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);
		rand = (RadioButton) rootView.findViewById(R.id.guess_rand);
		all = (RadioButton) rootView.findViewById(R.id.guess_all);
		classic = (RadioButton) rootView.findViewById(R.id.guess_classic);
		animal = (RadioButton) rootView.findViewById(R.id.guess_animal);
		love = (RadioButton) rootView.findViewById(R.id.guess_love);
		funny = (RadioButton) rootView.findViewById(R.id.guess_funny);
		child = (RadioButton) rootView.findViewById(R.id.guess_child);
		word = (RadioButton) rootView.findViewById(R.id.guess_word);
		english = (RadioButton) rootView.findViewById(R.id.guess_english);

		mImageView = (ImageView) rootView.findViewById(R.id.img1);

		mHorizontalScrollView = (HorizontalScrollView) rootView
				.findViewById(R.id.horizontalScrollView);

		// mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
	}

	private void getRiddleDesById(int riddleId) {
		dbRManager = new DBRiddleManager(getActivity());
		Riddle riddle = new Riddle();
		riddle = dbRManager.findRiddleById(riddleId);
		if (riddle != null) {
			riddleDes.setText(riddle.getRiddleDes());
		} else {
			riddleDes.setText("很遗憾，没有获取到谜语。");
		}
	}

	private void getRiddleFirstIdByKind(String kind) {
		dbRManager = new DBRiddleManager(getActivity());
		List<Riddle> list = new ArrayList<Riddle>();
		list = dbRManager.findRiddlesByKind(kind);

		if (list.size() > 0) {
			System.out.println("===========list.size()" + list.size());
			firstId = list.get(0).getRiddleId();
			lastId = list.get(list.size() - 1).getRiddleId();
		} else {
			String message = "很遗憾，没有获取到谜语。";
			MyDialog.myDialog(getActivity(), message);
		}
	}

	private void getRiddleKeyById(int riddleId) {
		dbRManager = new DBRiddleManager(getActivity());
		Riddle riddle = new Riddle();
		riddle = dbRManager.findRiddleById(riddleId);

		if (riddle != null) {
			System.out.println("=====guessKey.getText()===="
					+ guessKey.getText().toString().trim());
			System.out.println("=====riddle.getRiddleKey()===="
					+ riddle.getRiddleKey());
			if (guessKey.getText().toString().trim()
					.equals(riddle.getRiddleKey().toString().trim())) {
				String message = "真厉害！恭喜你猜对了。";
				MyDialog.myDialog(getActivity(), message);

			} else {
				j++;
				String message = "很遗憾，你猜错了，再猜猜。";
				MyDialog.myDialog(getActivity(), message);
			}
		} else {
			String message = "很遗憾，没有获取到谜底。";
			MyDialog.myDialog(getActivity(), message);
		}
	}

	private boolean guessResult(int riddleId) {
		dbRManager = new DBRiddleManager(getActivity());
		Riddle riddle = new Riddle();
		riddle = dbRManager.findRiddleById(riddleId);

		if (riddle != null) {
			if (guessKey.getText().toString().trim()
					.equals(riddle.getRiddleKey().toString().trim())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private void setRiddleKey(int riddleId) {
		dbRManager = new DBRiddleManager(getActivity());
		Riddle riddle = new Riddle();
		riddle = dbRManager.findRiddleById(riddleId);
		if (riddle != null) {
			if (guessResult(riddleId)) {
				riddleKey.setText(riddle.getRiddleKey());
			} else if (j >= 3) {
				riddleKey.setText(riddle.getRiddleKey());
			} else {
				// riddleKey.setText("至少猜3次才能查看谜底哦！");
				// Toast.makeText(getActivity(), "至少猜3次才能查看谜底哦！",
				// Toast.LENGTH_SHORT).show();
				String message = "至少猜3次才能查看谜底哦！";
				MyDialog.myDialog(getActivity(), message);
			}
		} else {
			Toast.makeText(getActivity(), "没有获取到谜底。", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private String[] newtan = new String[] { "复制", "邀请微信好友来猜", "邀请QQ好友来猜",
			"分享到微信朋友圈", "分享到QQ空间" };

	private void showListDialog(final String[] arg) {
		new AlertDialog.Builder(getActivity()).setTitle("")
				.setItems(arg, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {

						case 0:// 复制
							ClipboardManager cmb = (ClipboardManager) getActivity()
									.getSystemService(
											getActivity().CLIPBOARD_SERVICE);
							cmb.setText(shareContent);
							Toast.makeText(getActivity(), "复制成功！",
									Toast.LENGTH_SHORT).show();
							break;
						case 1:// 邀请微信好友来猜
							sendTextToWeChat();
							break;
						case 2:// 邀请QQ好友来猜
							new Thread(new Runnable() {
								public void run() {
									onClickShareToQQ();
									// 耗时的方法
								}
							}).start();
							break;
						case 3:// 分享到微信朋友圈
							new Thread(new Runnable() {
								public void run() {
									sendTextToWeChatZone();
									// 耗时的方法
								}
							}).start();
							break;
						case 4:// 分享到QQ空间
							new Thread(new Runnable() {
								public void run() {
									shareToQzone();
									// 耗时的方法
								}
							}).start();
							break;
						}
						;
					}
				}).show();
	}

	// 发送文本到微信
	private void sendTextToWeChat() {
		// 初始化一个WXTextObject对象
		WXTextObject textObj = new WXTextObject();
		textObj.text = shareContent;

		// 用WXTextObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		// 发送文本类型的消息时，title字段不起作用
		msg.title = "Will be ignored";
		msg.description = shareContent;

		// 构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
		req.message = msg;
		// req.scene = isTimelineCb? SendMessageToWX.Req.WXSceneTimeline :
		// SendMessageToWX.Req.WXSceneSession;;

		// 调用api接口发送数据到微信
		api.sendReq(req);
		// getActivity().finish();
	}

	// 发送文本到微信朋友圈
	private void sendTextToWeChatZone() {
		// 初始化一个WXTextObject对象
		WXTextObject textObj = new WXTextObject();
		textObj.text = shareContent;

		// 用WXTextObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		// 发送文本类型的消息时，title字段不起作用
		msg.title = "Will be ignored";
		msg.description = shareContent;

		// 构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;

		// 调用api接口发送数据到微信
		api.sendReq(req);
		// getActivity().finish();
	}

	// 发送图片到微信
	private void sendImgToWeChat() {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.app);
		WXImageObject imgObj = new WXImageObject(bmp);

		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;

		Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
				THUMB_SIZE, true);
		bmp.recycle();
		msg.thumbData = Util.bmpToByteArray(thumbBmp, true); // 设置缩略图

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		api.sendReq(req);
	}

	// 分享到我的电脑文本信息
	private void onClickShareTextToComputer() {
		final Bundle params = new Bundle();
		params.putString(GameAppOperation.QQFAV_DATALINE_APPNAME, "灯谜库");
		// params.putString(GameAppOperation.QQFAV_DATALINE_TITLE,"分享");
		params.putInt(GameAppOperation.QQFAV_DATALINE_REQTYPE,
				GameAppOperation.QQFAV_DATALINE_TYPE_TEXT);
		params.putString(GameAppOperation.QQFAV_DATALINE_DESCRIPTION,
				shareContent);
		TecentConstants.mTencent.sendToMyComputer(getActivity(), params,
				qqtestShareListener);
	}

	// 收藏到我的QQ
	private void onClickLoveText() {
		final Bundle params = new Bundle();
		params.putString(GameAppOperation.QQFAV_DATALINE_APPNAME, "灯谜库");
		// params.putString(GameAppOperation.QQFAV_DATALINE_TITLE,
		// title.getText().toString());
		params.putInt(GameAppOperation.QQFAV_DATALINE_REQTYPE,
				GameAppOperation.QQFAV_DATALINE_TYPE_TEXT);
		params.putString(GameAppOperation.QQFAV_DATALINE_DESCRIPTION,
				shareContent);
		TecentConstants.mTencent.addToQQFavorites(getActivity(), params,
				qqtestShareListener);
	}

	// 分享到QQ
	private void onClickShareToQQ() {
		ThreadManager.getMainHandler().post(new Runnable() {
			@Override
			public void run() {
				final Bundle params = new Bundle();
				params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
						QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
				params.putString(QQShare.SHARE_TO_QQ_TITLE, "猜一猜");
				params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareContent);
				params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,
						TecentConstants.shareTargetUrl);
				params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
						"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
				params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "灯谜库");
				TecentConstants.mTencent.shareToQQ(getActivity(), params,
						qqtestShareListener);
			}
		});
	}

	// 分享到QQ空间
	private void shareToQzone() {
		ThreadManager.getMainHandler().post(new Runnable() {
			@Override
			public void run() {
				final Bundle params = new Bundle();
				params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "猜一猜");// 必填
				params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareContent);
				params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,
						TecentConstants.shareTargetUrl);// 必填
				// 支持传多个imageUrl
				ArrayList<String> imageUrls = new ArrayList<String>();
				for (int i = 0; i < 1; i++) {
					imageUrls
							.add("http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
				}
				params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,
						imageUrls);
				TecentConstants.mTencent.shareToQzone(getActivity(), params,
						qqtestShareListener);
			}
		});
	}

	IUiListener qqtestShareListener = new IUiListener() {
		@Override
		public void onCancel() {
			Log.d(getTag(), "-----onCancel()------");
		}

		@Override
		public void onComplete(Object response) {
			// TODO Auto-generated method stub
			Util.toastMessage(getActivity(),
					"onComplete: " + response.toString());
			Toast.makeText(getActivity(), "result=" + response.toString(),
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onError(UiError e) {
			// TODO Auto-generated method stub
			Util.toastMessage(getActivity(), "onError: " + e.errorMessage, "e");
		}
	};

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	private void showBanner() {

		final ViewGroup container = (ViewGroup) rootView
				.findViewById(R.id.guess_banner_container);
		
		// 实例化广告条
		AdView adView = new AdView(getActivity(), AdSize.FIT_SCREEN);
		// 调用Activity的addContentView函数
		
		container.addView(adView, new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		// 监听广告条接口
		adView.setAdListener(new AdViewListener() {

			@Override
			public void onSwitchedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "广告条切换");
			}

			@Override
			public void onReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告成功");
			}

			@Override
			public void onFailedToReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告失败");
			}
		});
	}

	@Override
	public void onStop() {
		// 如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
		SpotManager.getInstance(getActivity()).onStop();
		super.onStop();
	}

	@Override
	public void onDestroy() {
		SpotManager.getInstance(getActivity()).onDestroy();
		super.onDestroy();
	}
}
