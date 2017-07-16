package com.bingo.riddle.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import net.youmi.android.spot.SpotManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ext.SatelliteMenu;
import android.view.ext.SatelliteMenu.SateliteClickedListener;
import android.view.ext.SatelliteMenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bingo.lantern.R;
import com.bingo.riddle.db.DBRiddleManager;
import com.bingo.riddle.model.Riddle;
import com.bingo.sortlist.CharacterParser;
import com.bingo.sortlist.ClearEditText;
import com.bingo.sortlist.PinyinComparator;
import com.bingo.sortlist.SideBar;
import com.bingo.sortlist.SideBar.OnTouchingLetterChangedListener;
import com.bingo.sortlist.SortAdapter;
import com.bingo.sortlist.SortModel;
import com.bingo.util.MyDialog;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXAppExtendObject;
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
import com.wechat.tool.CameraUtil;
import com.wechat.tool.WeChatConstants;

public class RiddleFragment extends Fragment {
	private View rootView;
	@SuppressWarnings("unused")
	private boolean isFirstCreate = true;
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	private String shareContent;

	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	private ProgressDialog progressDialog;

	private static final int START = 0;

	private static final int OVER = 1;

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

		if (rootView != null) {
			isFirstCreate = false;
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}

			return rootView;
		}
		isFirstCreate = true;
		rootView = inflater
				.inflate(R.layout.activity_riddle, container, false);

		SatelliteMenu menu = (SatelliteMenu) rootView.findViewById(R.id.menu);
		List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
		items.add(new SatelliteMenuItem(1, R.drawable.other));
		items.add(new SatelliteMenuItem(2, R.drawable.word));
		items.add(new SatelliteMenuItem(3, R.drawable.plant));
		items.add(new SatelliteMenuItem(4, R.drawable.animal));

		api = WXAPIFactory.createWXAPI(getActivity(), WeChatConstants.APP_ID,
				true);
		api.registerApp(WeChatConstants.APP_ID);
		// isTimelineCb = false;

		mAppid = TecentConstants.APP_ID;
		TecentConstants.mTencent = Tencent
				.createInstance(mAppid, getActivity());
		
		showBanner();

		menu.addItems(items);

		menu.setOnItemClickedListener(new SateliteClickedListener() {

			public void eventOccured(int id) {
				// Log.i("sat", "Clicked on " + id);
				switch (id) {
				case 1:
					/* 开启一个新线程，在新线程里执行耗时的方法 */
					new Thread(new Runnable() {
						public void run() {
							handler.sendEmptyMessage(0);
							updateData("");
							// 耗时的方法
							handler.sendEmptyMessage(1);
							// 执行耗时的方法之后发送消给handler
						}
					}).start();
					break;
				case 2:
					/* 开启一个新线程，在新线程里执行耗时的方法 */
					new Thread(new Runnable() {
						public void run() {
							handler.sendEmptyMessage(0);
							updateData("字谜");
							// 耗时的方法
							handler.sendEmptyMessage(1);
							// 执行耗时的方法之后发送消给handler
						}
					}).start();

					break;
				case 3:
					/* 开启一个新线程，在新线程里执行耗时的方法 */
					new Thread(new Runnable() {
						public void run() {
							handler.sendEmptyMessage(0);
							updateData("经典");
							// 耗时的方法
							handler.sendEmptyMessage(1);
							// 执行耗时的方法之后发送消给handler
						}
					}).start();

					break;
				case 4:
					/* 开启一个新线程，在新线程里执行耗时的方法 */
					new Thread(new Runnable() {
						public void run() {
							handler.sendEmptyMessage(0);
							// updateData(R.array.animal);
							updateData("动物");
							// 耗时的方法
							handler.sendEmptyMessage(1);
							// 执行耗时的方法之后发送消给handler
						}
					}).start();

					break;
				default:
					/* 开启一个新线程，在新线程里执行耗时的方法 */
					new Thread(new Runnable() {
						public void run() {
							handler.sendEmptyMessage(0);
							// updateData(R.array.animal);
							updateData("");
							// 耗时的方法
							handler.sendEmptyMessage(1);
							// 执行耗时的方法之后发送消给handler
						}
					}).start();

					break;
				}

			}

		});

		init();

		/* 开启一个新线程，在新线程里执行耗时的方法 */
		new Thread(new Runnable() {
			public void run() {
				handler.sendEmptyMessage(0);
				// updateData(R.array.animal);
				updateData("");
				// 耗时的方法
				handler.sendEmptyMessage(1);
				// 执行耗时的方法之后发送消给handler
			}
		}).start();

		return rootView;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// handler接收到消息后就会执行此方法
			switch (msg.what) {
			case START:
				/* 显示ProgressDialog */
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				progressDialog = ProgressDialog.show(getActivity(), "正在努力为您加载",
						"请稍后……");
				break;
			case OVER:
				if (adapter != null) {
					sortListView.setAdapter(adapter);
				}
				progressDialog.dismiss();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void init() {
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) rootView.findViewById(R.id.sidrbar);
		dialog = (TextView) rootView.findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});

		sortListView = (ListView) rootView.findViewById(R.id.riddleList);

		sortListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Toast.makeText(getActivity(), "想要更多操作请长按，谢谢合作。",
				// Toast.LENGTH_SHORT).show();
				String message = "长按可邀请好友猜谜哦。";
				MyDialog.myDialog(getActivity(), message);
			}
		});

		sortListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				final SortModel sm = SourceDateList.get(position);

				// String share = sm.getName().substring(0,
				// sm.getName().indexOf(")"))
				// + ")";
				String share = sm.getName().toString().trim();
				if (!"".equals(share)) {
					shareContent = share;
				} else {
					shareContent = "";
				}

				showListDialog(newtan, sm);

				return true;
			}

			private String[] newtan = new String[] { "收藏到QQ", "复制", "邀请微信好友来猜",
					"邀请QQ好友来猜", "分享到微信朋友圈", "分享到QQ空间", "发送至QQ（我的电脑）" };

			private void showListDialog(final String[] arg, final SortModel sm) {
				new AlertDialog.Builder(getActivity()).setTitle("")
						.setItems(arg, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {

								case 0:// 收藏到QQ
									new Thread(new Runnable() {
										public void run() {
											handler.sendEmptyMessage(0);
											onClickLoveText();
											// 耗时的方法
											handler.sendEmptyMessage(1);
											// 执行耗时的方法之后发送消给handler
										}
									}).start();

									break;

								case 1:// 复制
									ClipboardManager cmb = (ClipboardManager) getActivity()
											.getSystemService(
													getActivity().CLIPBOARD_SERVICE);
									cmb.setText(shareContent);
									// Toast.makeText(getActivity(), "复制成功！",
									// Toast.LENGTH_SHORT).show();
									String message = "复制成功！";
									MyDialog.myDialog(getActivity(), message);
									break;
								case 2:// 邀请微信好友来猜
									sendTextToWeChat();
									break;
								case 3:// 邀请QQ好友来猜
									new Thread(new Runnable() {
										public void run() {
											handler.sendEmptyMessage(0);
											onClickShareToQQ();
											// 耗时的方法
											handler.sendEmptyMessage(1);
											// 执行耗时的方法之后发送消给handler
										}
									}).start();
									break;
								case 4:// 分享到微信朋友圈
									new Thread(new Runnable() {
										public void run() {
											handler.sendEmptyMessage(0);
											sendTextToWeChatZone();
											// 耗时的方法
											handler.sendEmptyMessage(1);
											// 执行耗时的方法之后发送消给handler
										}
									}).start();
									break;
								case 5:// 分享到QQ空间
									new Thread(new Runnable() {
										public void run() {
											handler.sendEmptyMessage(0);
											shareToQzone();
											// 耗时的方法
											handler.sendEmptyMessage(1);
											// 执行耗时的方法之后发送消给handler
										}
									}).start();
									break;
								case 6:// 发送至QQ（我的电脑）
									new Thread(new Runnable() {
										public void run() {
											handler.sendEmptyMessage(0);
											onClickShareTextToComputer();
											// 耗时的方法
											handler.sendEmptyMessage(1);
											// 执行耗时的方法之后发送消给handler
										}
									}).start();
									break;
								}
								;
							}
						}).show();
			}
		});

		mClearEditText = (ClearEditText) rootView
				.findViewById(R.id.filter_edit);

		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	// private void updateData(int id) {
	// // 实例化汉字转拼音类
	// SourceDateList = filledData(getResources().getStringArray(id));
	// // 根据a-z进行排序源数据
	// Collections.sort(SourceDateList, pinyinComparator);
	// adapter = new SortAdapter(getActivity(), SourceDateList);
	// }

	private String[] getRiddlesByKind(String kind) {
		dbRManager = new DBRiddleManager(getActivity());
		List<Riddle> list = new ArrayList<Riddle>();
		list = dbRManager.findRiddlesByKind(kind);

		if (list.size() > 0) {
			System.out.println("===========list.size()" + list.size());
			int size = list.size();
			String[] str = new String[size];
			for (int i = 0; i < size; i++) {
				str[i] = list.get(i).getRiddleDes().trim() + "—— 谜底："
						+ list.get(i).getRiddleKey().trim();
			}
			return (str);
		} else {
			// Toast.makeText(getActivity(), "没有获取到谜语。", Toast.LENGTH_SHORT)
			// .show();
			String message = "很遗憾，没有获取到谜语。";
			MyDialog.myDialog(getActivity(), message);
		}
		return null;
	}

	private String[] getRiddleDesByKind(String kind) {
		dbRManager = new DBRiddleManager(getActivity());
		List<Riddle> list = new ArrayList<Riddle>();
		list = dbRManager.findRiddlesByKind(kind);

		if (list.size() > 0) {
			System.out.println("===========list.size()" + list.size());
			int size = list.size();
			String[] str = new String[size];
			for (int i = 0; i < size; i++) {
				str[i] = list.get(i).getRiddleDes().trim();
			}
			return (str);
		} else {
			// Toast.makeText(getActivity(), "没有获取到谜语。", Toast.LENGTH_SHORT)
			// .show();
			String message = "很遗憾，没有获取到谜语。";
			MyDialog.myDialog(getActivity(), message);
		}
		return null;
	}

	private String[] getRiddleKeyByKind(String kind) {
		dbRManager = new DBRiddleManager(getActivity());
		List<Riddle> list = new ArrayList<Riddle>();
		list = dbRManager.findRiddlesByKind(kind);

		if (list.size() > 0) {
			System.out.println("===========list.size()" + list.size());
			int size = list.size();
			String[] str = new String[size];
			for (int i = 0; i < size; i++) {
				str[i] = "—— 谜底：" + list.get(i).getRiddleKey().trim();
			}
			return (str);
		} else {
			// Toast.makeText(getActivity(), "没有获取到谜语。", Toast.LENGTH_SHORT)
			// .show();
			String message = "很遗憾，没有获取到谜语。";
			MyDialog.myDialog(getActivity(), message);
		}
		return null;
	}

	private void updateData(String kind) {
		// 实例化汉字转拼音类
		SourceDateList = filledData(getRiddlesByKind(kind),
				getRiddleDesByKind(kind), getRiddleKeyByKind(kind));
		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(getActivity(), SourceDateList);
	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String[] data, String[] riddleDes,
			String[] riddleKey) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < riddleDes.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setData(data[i]);
			sortModel.setName(riddleDes[i]);
			sortModel.setKey(riddleKey[i]);
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(riddleDes[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}
			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Toast.makeText(getActivity(), "requestCode:" + requestCode,
				Toast.LENGTH_SHORT).show();
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("===========requestCode:" + requestCode);
		switch (requestCode) {

		case 0x101: {
			final WXAppExtendObject appdata = new WXAppExtendObject();
			final String path = CameraUtil.getResultPhotoPath(getActivity(),
					data, SDCARD_ROOT + "/tencent/");
			appdata.filePath = path;
			appdata.extInfo = "this is ext info";

			final WXMediaMessage msg = new WXMediaMessage();
			msg.setThumbImage(Util.extractThumbNail(path, 150, 150, true));
			msg.title = "this is title";
			msg.description = "this is description";
			msg.mediaObject = appdata;

			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = buildTransaction("appdata");
			req.message = msg;
			// req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline :
			// SendMessageToWX.Req.WXSceneSession;
			api.sendReq(req);

			break;
		}
		default:
			break;
		}
	}
	
	private void showBanner() {

		final ViewGroup container = (ViewGroup) rootView
				.findViewById(R.id.riddle_banner_container);
		
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
