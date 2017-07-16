package com.bingo.riddle.activity;

import java.util.ArrayList;

import net.youmi.android.AdManager;
import net.youmi.android.listener.Interface_ActivityListener;
import net.youmi.android.offers.EarnPointsOrderInfo;
import net.youmi.android.offers.EarnPointsOrderList;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.offers.PointsChangeNotify;
import net.youmi.android.offers.PointsEarnNotify;
import net.youmi.android.offers.PointsManager;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bingo.lantern.R;
import com.bingo.util.CopyImgToSDCard;
import com.tencent.connect.UserInfo;
import com.tencent.connect.avatar.QQAvatar;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.tencent.tool.BaseUIListener;
import com.tencent.tool.TecentConstants;
import com.tencent.tool.ThreadManager;
import com.tencent.tool.Util;
import com.wechat.tool.WeChatConstants;

public class SetInfoFragment extends Fragment implements PointsChangeNotify,
		PointsEarnNotify {
	private View rootView;
	private TextView qq;
	private TextView qqzone;
	private TextView wechat;
	private TextView wechatcircle;
	private TextView love;
	private TextView advise;
	private TextView share;
	private TextView headtip;
	private TextView login;
	private UserInfo mInfo;
	private TextView mUserInfo;
	private ImageView mUserLogo;

	private String userName;

	private static boolean isServerSideLogin = false;

	private static final int REQUEST_SET_AVATAR = 2;

	private static final String TAG = SetInfoFragment.class.getName();

	public static String mAppid;

	// QZone分享， SHARE_TO_QQ_TYPE_DEFAULT 图文，SHARE_TO_QQ_TYPE_IMAGE 纯图
	// private int shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
	private String SCOPE = "all";

	// IWXAPI 是第三方app和微信通信的openapi接口
	private IWXAPI api;
	private static final int THUMB_SIZE = 150;
	private static final String SDCARD_ROOT = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	/**
	 * 显示积分余额的控件
	 */
	TextView mTextViewPoints;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_set_info, container,
				false);

		qq = (TextView) rootView
				.findViewById(R.id.set_personal_info_activity_tv_qq);
		qqzone = (TextView) rootView
				.findViewById(R.id.set_personal_info_activity_tv_qqzone);
		wechat = (TextView) rootView
				.findViewById(R.id.set_personal_info_activity_tv_wechat);
		wechatcircle = (TextView) rootView
				.findViewById(R.id.set_personal_info_activity_tv_wechatcircle);
		love = (TextView) rootView
				.findViewById(R.id.set_personal_info_activity_tv_love);
		login = (TextView) rootView
				.findViewById(R.id.set_personal_info_activity_tv_login);
		mUserInfo = (TextView) rootView
				.findViewById(R.id.set_personal_info_activity_tv_name);
		mUserLogo = (ImageView) rootView
				.findViewById(R.id.set_personal_info_activity_iv_head_pic);
		headtip = (TextView) rootView
				.findViewById(R.id.set_personal_info_activity_ll_head_tip);
		advise = (TextView) rootView
				.findViewById(R.id.set_personal_info_activity_tv_advise);
		share = (TextView) rootView
				.findViewById(R.id.set_personal_info_activity_tv_share);
		
		mTextViewPoints = (TextView) rootView
				.findViewById(R.id.set_personal_info_activity_tv_score);

		// 从5.3.0版本起，客户端积分托管将由 int 转换为 float
		float pointsBalance = PointsManager.getInstance(getActivity())
				.queryPoints();
		mTextViewPoints.setText(" " + pointsBalance);

		updateLoginButton();

		mAppid = TecentConstants.APP_ID;
		TecentConstants.mTencent = Tencent
				.createInstance(mAppid, getActivity());

		// 通过WXAPIFactory工厂，获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(getActivity(), WeChatConstants.APP_ID,
				false);

		// 有米android 积分墙sdk 5.0.0之后支持定制浏览器顶部标题栏的部分UI
		// deployOfferBrowserConfig();

		// (可选)关闭有米log输出，建议开发者在嵌入有米过程中不要关闭，以方便随时捕捉输出信息，出问题时可以快速定位问题
		// AdManager.getInstance(Context context).setEnableDebugLog(false);

		// 初始化接口，应用启动的时候调用，参数：appId, appSecret
		AdManager.getInstance(getActivity()).init("69d82a8f65a5975f",
				"035255d0087c1397");

		// 如果开发者使用积分墙的服务器回调,
		// 1.需要告诉sdk，现在采用服务器回调
		// 2.建议开发者传入自己系统中用户id（如：邮箱账号之类的）（请限制在50个字符串以内）
		// 3.务必在下面的OffersManager.getInstance(this).onAppLaunch();代码之前声明使用服务器回调

		// OffersManager.setUsingServerCallBack(true);
		// OffersManager.getInstance(this).setCustomUserId("user_id");

		// 如果使用积分广告，请务必调用积分广告的初始化接口:
		OffersManager.getInstance(getActivity()).onAppLaunch();

		// (可选)注册积分监听-随时随地获得积分的变动情况
		PointsManager.getInstance(getActivity()).registerNotify(this);

		// (可选)注册积分订单赚取监听（sdk v4.10版本新增功能）
		PointsManager.getInstance(getActivity()).registerPointsEarnNotify(this);

		// (可选)设置是否在通知栏显示下载相关提示。默认为true，标识开启；设置为false则关闭。（sdk v4.10版本新增功能）
		// AdManager.setIsDownloadTipsDisplayOnNotification(false);

		// (可选)设置安装完成后是否在通知栏显示已安装成功的通知。默认为true，标识开启；设置为false则关闭。（sdk
		// v4.10版本新增功能）
		// AdManager.setIsInstallationSuccessTipsDisplayOnNotification(false);

		// (可选)设置是否在通知栏显示积分赚取提示。默认为true，标识开启；设置为false则关闭。
		// 如果开发者采用了服务器回调积分的方式，那么本方法将不会生效
		// PointsManager.setEnableEarnPointsNotification(false);

		// (可选)设置是否开启积分赚取的Toast提示。默认为true，标识开启；设置为false这关闭。
		// 如果开发者采用了服务器回调积分的方式，那么本方法将不会生效
		// PointsManager.setEnableEarnPointsToastTips(false);

		// -------------------------------------------------------------------------------------------
		// 积分墙SDK 5.3.0 新增分享任务，下面为新增接口

		// (可选) 获取当前应用的签名md5字符串，可用于申请微信appid时使用
		// 注意：获取是确保应用采用的是发布版本的签名而不是debug签名

		// Log.d("youmi", String.format("包名：%s\n签名md5值：%s",
		// this.getPackageName(),
		// OffersManager.getInstance(this).getSignatureMd5String()));

		// (重要)
		// 如果开发者需要开启分享任务墙，需要调用下面代码以注册微信appid（这里的appid为贵应用在微信平台上注册获取得到的appid）
		// 1. 微信的appid，请开发者在微信官网上自行注册
		// 2. 如果注册失败(返回false)，请参考/doc/有米AndroidSDK常见问题.html
		boolean isRegisterSuccess = OffersManager.getInstance(getActivity())
				.registerToWx("wx735b468e43f5d797");
//		Toast.makeText(getActivity(),
//				String.format("注册微信appid %s", isRegisterSuccess ? "成功" : "失败"),
//				Toast.LENGTH_SHORT).show();

		return rootView;
	}

	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		// 设置头像
		mUserLogo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Context context = v.getContext();
				Animation shake = AnimationUtils.loadAnimation(context,
						R.anim.shake);
				v.startAnimation(shake);
				onClickSetAvatar();
			}
		});

		// 发送到QQ
		qq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Context context = v.getContext();
				Animation shake = AnimationUtils.loadAnimation(context,
						R.anim.shake);
				v.startAnimation(shake);
				// onClickShareApp();
				onClickShareOnlyImg();
			}
		});

		// 发送到QQ空间
		qqzone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				Animation shake = AnimationUtils.loadAnimation(context,
						R.anim.shake);
				v.startAnimation(shake);
				shareToQzone();
			}
		});

		// 分享到微信
		wechat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				Animation shake = AnimationUtils.loadAnimation(context,
						R.anim.shake);
				v.startAnimation(shake);
				sendImgToWeChat();
				// Toast.makeText(getActivity(), "正在开发中，敬请期待。",
				// Toast.LENGTH_SHORT)
				// .show();
			}
		});

		// 分享到微信朋友圈
		wechatcircle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				Animation shake = AnimationUtils.loadAnimation(context,
						R.anim.shake);
				v.startAnimation(shake);
				sendImgToWeChatZone();
				// Toast.makeText(getActivity(), "正在开发中，敬请期待。",
				// Toast.LENGTH_SHORT)
				// .show();
			}
		});

		// 登录
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				Animation shake = AnimationUtils.loadAnimation(context,
						R.anim.shake);
				v.startAnimation(shake);
				// doLoginToQQ();
				onClickLogin();
			}
		});

		// 我的收藏
		love.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				Animation shake = AnimationUtils.loadAnimation(context,
						R.anim.shake);
				v.startAnimation(shake);
				Toast.makeText(getActivity(), "请到QQ收藏查看", Toast.LENGTH_SHORT)
						.show();
			}
		});

		// 体验应用
		advise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				Animation shake = AnimationUtils.loadAnimation(context,
						R.anim.shake);
				v.startAnimation(shake);
				// 调用方式一：直接打开全屏积分墙
				// OffersManager.getInstance(this).showOffersWall();

				// 调用方式二：直接打开全屏积分墙，并且监听积分墙退出的事件onDestory
				OffersManager.getInstance(getActivity()).showOffersWall(
						new Interface_ActivityListener() {

							/**
							 * 当积分墙销毁的时候，即积分墙的Activity调用了onDestory的时候回调
							 */
							@Override
							public void onActivityDestroy(Context context) {
								Toast.makeText(context, "全屏积分墙退出了",
										Toast.LENGTH_SHORT).show();
							}
						});
			}
		});
		
		//分享得钻石
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				Animation shake = AnimationUtils.loadAnimation(context,
						R.anim.shake);
				v.startAnimation(shake);
				// 调用方式一：直接打开全屏分享任务积分墙
				// OffersManager.getInstance(this).showShareWall();

				// 调用方式二：直接打开全屏分享任务积分墙，并且监听分享任务积分墙退出的事件onDestory
				OffersManager.getInstance(getActivity()).showShareWall(new Interface_ActivityListener() {

					/**
					 * 当分享任务积分墙销毁的时候，即分享任务积分墙的Activity调用了onDestory的时候回调
					 */
					@Override
					public void onActivityDestroy(Context context) {
						Toast.makeText(context, "全屏分享任务积分墙退出了", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});

	}

	// 改变登录按钮样式
	private void updateLoginButton() {
		if (TecentConstants.mTencent != null
				&& TecentConstants.mTencent.isSessionValid()) {
			if (isServerSideLogin) {
				updateUserInfo();
				// login.setTextColor(Color.BLUE);
				login.setText("QQ号登录");
			} else {
				updateUserInfo();
				login.setTextColor(Color.RED);
				login.setText("退出帐号");
			}
		} else {
			updateUserInfo();
			// login.setTextColor(Color.BLUE);
			login.setText("QQ号登录");
		}
	}

	// 修改用户信息
	private void updateUserInfo() {
		if (TecentConstants.mTencent != null
				&& TecentConstants.mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onError(UiError e) {

				}

				@Override
				public void onComplete(final Object response) {
					Message msg = new Message();
					msg.obj = response;
					msg.what = 0;
					mHandler.sendMessage(msg);
					new Thread() {

						@Override
						public void run() {
							JSONObject json = (JSONObject) response;
							if (json.has("figureurl")) {
								Bitmap bitmap = null;
								try {
									bitmap = Util.getbitmap(json
											.getString("figureurl_qq_2"));
								} catch (JSONException e) {

								}
								Message msg = new Message();
								msg.obj = bitmap;
								msg.what = 1;
								mHandler.sendMessage(msg);
							}
						}

					}.start();
				}

				@Override
				public void onCancel() {

				}
			};
			mInfo = new UserInfo(getActivity(),
					TecentConstants.mTencent.getQQToken());
			mInfo.getUserInfo(listener);

		} else {
			mUserInfo.setText("未登录");
			login.setTextColor(Color.BLACK);
			mUserLogo.setBackgroundResource(R.drawable.default_avatar_shadow);
			mUserLogo.setVisibility(android.view.View.VISIBLE);
		}
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				JSONObject response = (JSONObject) msg.obj;
				if (response.has("nickname")) {
					try {
						mUserInfo.setVisibility(android.view.View.VISIBLE);
						userName = response.getString("nickname");
						mUserInfo.setText(userName);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} else if (msg.what == 1) {
				Bitmap bitmap = (Bitmap) msg.obj;
				mUserLogo.setImageBitmap(bitmap);
				mUserLogo.setVisibility(android.view.View.VISIBLE);
			}
		}

	};

	// 登录到QQ
	private void onClickLogin() {
		if (!TecentConstants.mTencent.isSessionValid()) {
			TecentConstants.mTencent.login(this, "all", loginListener);
			isServerSideLogin = false;
			Log.d("SDKQQAgentPref",
					"FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
		} else {
			if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
				TecentConstants.mTencent.logout(getActivity());
				TecentConstants.mTencent.login(this, "all", loginListener);
				isServerSideLogin = false;
				Log.d("SDKQQAgentPref",
						"FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
				return;
			}
			TecentConstants.mTencent.logout(getActivity());
			mUserInfo.setText("未登录");
			mUserLogo.setBackgroundResource(R.drawable.default_avatar_shadow);
			updateLoginButton();
		}
	}

	public static void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
			String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
					&& !TextUtils.isEmpty(openId)) {
				TecentConstants.mTencent.setAccessToken(token, expires);
				TecentConstants.mTencent.setOpenId(openId);
			}
		} catch (Exception e) {
		}
	}

	IUiListener loginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject values) {
			Log.d("SDKQQAgentPref",
					"AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
			initOpenidAndToken(values);
			updateUserInfo();
			updateLoginButton();
		}
	};

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			if (null == response) {
				Util.showResultDialog(getActivity(), "返回为空", "登录失败");
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				Util.showResultDialog(getActivity(), "返回为空", "登录失败");
				return;
			}
			// Util.showResultDialog(getActivity(), response.toString(),
			// "登录成功");

			doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			Util.toastMessage(getActivity(), "onError: " + e.errorDetail);
			Util.dismissDialog();
		}

		@Override
		public void onCancel() {
			Util.toastMessage(getActivity(), "onCancel: ");
			Util.dismissDialog();
			if (isServerSideLogin) {
				isServerSideLogin = false;
			}
		}
	}

	// 分享纯图片到QQ
	private void onClickShareOnlyImg() {
		ThreadManager.getMainHandler().post(new Runnable() {
			@Override
			public void run() {
				Bundle params = new Bundle();
				CopyImgToSDCard.CopyImg(getActivity(), "app.png");
				params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,
						TecentConstants.localImgUrl);
				params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "灯谜库");
				params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
						QQShare.SHARE_TO_QQ_TYPE_IMAGE);
				// params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,
				// QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
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
				params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "应用（灯谜库）分享");// 必填
				params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "点击进入下载页面");
				params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,
						TecentConstants.shareTargetUrl);// 必填
				// 支持传多个imageUrl
				ArrayList<String> imageUrls = new ArrayList<String>();
				for (int i = 0; i < 1; i++) {
					imageUrls.add(TecentConstants.shareImgUrl);
				}
				params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,
						imageUrls);
				TecentConstants.mTencent.shareToQzone(getActivity(), params,
						qZoneShareListener);
			}
		});
	}

	// 启动微信
	private void startWeChat() {
		ThreadManager.getMainHandler().post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getActivity(), "result=" + api.openWXApp(),
						Toast.LENGTH_LONG).show();
			}
		});
	}

	// 发送图片到微信
	private void sendImgToWeChat() {
		CopyImgToSDCard.CopyImg(getActivity(), "app.png");
		String path = SDCARD_ROOT + "/app.png";

		WXImageObject imgObj = new WXImageObject();
		imgObj.setImagePath(path);

		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;

		Bitmap bmp = BitmapFactory.decodeFile(path);
		Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
				THUMB_SIZE, true);
		bmp.recycle();
		msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);

	}

	// 发送图片到微信朋友圈
	private void sendImgToWeChatZone() {
		CopyImgToSDCard.CopyImg(getActivity(), "app.png");
		String path = SDCARD_ROOT + "/app.png";

		WXImageObject imgObj = new WXImageObject();
		imgObj.setImagePath(path);

		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;

		Bitmap bmp = BitmapFactory.decodeFile(path);
		Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
				THUMB_SIZE, true);
		bmp.recycle();
		msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		api.sendReq(req);
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

	IUiListener qZoneShareListener = new IUiListener() {

		@Override
		public void onCancel() {
			Util.toastMessage(getActivity(), "onCancel: ");
		}

		@Override
		public void onError(UiError e) {
			// TODO Auto-generated method stub
			Util.toastMessage(getActivity(), "onError: " + e.errorMessage, "e");
		}

		@Override
		public void onComplete(Object response) {
			// TODO Auto-generated method stub
			Util.toastMessage(getActivity(),
					"onComplete: " + response.toString());
		}

	};

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	private void onClickSetAvatar() {
		if (this.ready(getActivity())) {
			Intent intent = new Intent();
			// 开启Pictures画面Type设定为image
			intent.setType("image/*");
			// 使用Intent.ACTION_GET_CONTENT这个Action
			intent.setAction(Intent.ACTION_GET_CONTENT);
			// 取得相片后返回本画面
			startActivityForResult(intent, REQUEST_SET_AVATAR);
			// 在 onActivityResult 中调用 doSetAvatar
		}
	}

	private void doSetAvatar(Uri uri) {
		QQAvatar qqAvatar = new QQAvatar(TecentConstants.mTencent.getQQToken());
		qqAvatar.setAvatar(getActivity(), uri,
				new BaseUIListener(getActivity()), R.anim.zoomout);
	}

	public boolean ready(Activity activity) {
		if (TecentConstants.mTencent == null) {
			return false;
		}
		boolean ready = TecentConstants.mTencent.isSessionValid()
				&& TecentConstants.mTencent.getQQToken().getOpenId() != null;
		if (!ready) {
			Toast.makeText(getActivity(), "登录之后才能修改头像哦!", Toast.LENGTH_SHORT)
					.show();
		}
		return ready;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "-->onActivityResult " + requestCode + " resultCode="
				+ resultCode);
		if (requestCode == REQUEST_SET_AVATAR
				&& resultCode == Activity.RESULT_OK) {
			doSetAvatar(data.getData());
		} else {
			TecentConstants.mTencent.onActivityResultData(requestCode,
					resultCode, data, loginListener);
			if (requestCode == Constants.REQUEST_API) {
				if (resultCode == Constants.RESULT_LOGIN) {
					Tencent.handleResultData(data, loginListener);
					Log.d(TAG, "-->onActivityResult handle logindata");
				}
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 退出时回收资源
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();

		// （可选）注销积分监听
		// 如果在onCreate调用了PointsManager.getInstance(this).registerNotify(this)进行积分余额监听器注册，那这里必须得注销
		PointsManager.getInstance(getActivity()).unRegisterNotify(this);

		// （可选）注销积分订单赚取监听
		// 如果在onCreate调用了PointsManager.getInstance(this).registerPointsEarnNotify(this)
		// 进行积分订单赚取监听器注册，那这里必须得注销
		PointsManager.getInstance(getActivity()).unRegisterPointsEarnNotify(
				this);

		// 回收积分广告占用的资源
		OffersManager.getInstance(getActivity()).onAppExit();
	}

	/**
	 * 积分余额发生变动时，就会回调本方法（本回调方法执行在UI线程中）
	 *
	 * 从5.3.0版本起，客户端积分托管将由 int 转换为 float
	 */
	@Override
	public void onPointBalanceChange(float pointsBalance) {
		mTextViewPoints.setText(" " + pointsBalance);
	}

	/**
	 * 积分订单赚取时会回调本方法（本回调方法执行在UI线程中）
	 */
	@Override
	public void onPointEarn(Context arg0, EarnPointsOrderList list) {
		// 遍历订单
		for (int i = 0; i < list.size(); ++i) {
			EarnPointsOrderInfo info = list.get(i);
			Toast.makeText(getActivity(), info.getMessage(), Toast.LENGTH_LONG)
					.show();
		}
	}

}