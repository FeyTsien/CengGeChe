package com.ygst.cenggeche.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.ui.fragment.message.MessageFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;


/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    public static final String OPEN_NOTIFICATION = "openNotification";

    //170976fa8ab712b87e5
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            LogUtils.i(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//				String regId2= JPushInterface.getRegistrationID(context); //也能获取到regId
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                AppData.saveRegistrationId(regId);
                LogUtils.d(TAG, "用户注册成功，Registration Id : " + regId);//100d85590970b3b830a
                //send the Registration Id to your server...
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                LogUtils.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                String pushType = getJSON(bundle,"pushType");
                if(pushType.equals("1")){
                    //为1是接收到好友申请
                    AppData.savaUnReadApplyCount(AppData.getUnReadApplyCount()+1);
                    MessageFragment.instance.showUnReadApplyCount();
                }
//				processCustomMessage(context, bundle);
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                receivingNotification(context, bundle);
                LogUtils.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                LogUtils.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
                String prio = bundle.getString(JPushInterface.EXTRA_NOTI_PRIORITY);
                LogUtils.d(TAG, "获取优先级： " + prio);
                String prio2 = bundle.getString(JPushInterface.EXTRA_NOTI_CATEGORY);
                LogUtils.d(TAG, "通知分类： " + prio2);
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                LogUtils.d(TAG, "[MyReceiver] 用户点击打开了通知");
                openNotification(context, bundle);
//                Intent intenta = new Intent(context,  WebViewActivity.class);
//                intenta.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intenta);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                LogUtils.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                LogUtils.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                LogUtils.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    //json解析
    private String getJSON(Bundle bundle,String key){
        String value = "";
        try {
            JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
            Iterator<String> it = json.keys();

            while (it.hasNext()) {
                String myKey = it.next().toString();
                if (key.equals(myKey)) {
                    value = json.optString(myKey);
                }
            }
        } catch (JSONException e) {
            LogUtils.e(TAG, "Get message extra JSON error!");
        }
        return value;
    }
    //接受到推送下来的通知
    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        LogUtils.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        LogUtils.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogUtils.d(TAG, "extras : " + extras);
    }

    //点击打开通知
    private void openNotification(Context context, Bundle bundle) {
        if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
            LogUtils.i(TAG, "This message has no Extra data");
            return;
        }
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";
        LogUtils.d(TAG, "字符串： " + extras);

        try {
            JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
            Iterator<String> it = json.keys();

            while (it.hasNext()) {
                String myKey = it.next().toString();
                if ("a".equals(myKey)) {
                    myValue = json.optString(myKey);
                }
            }
        } catch (JSONException e) {
            LogUtils.e(TAG, "Get message extra JSON error!");
        }
//		跳转到不同的Activity
//        if (!TextUtils.isEmpty(myValue)) {
//            LogUtils.d(TAG, "myValues  a:" + myValue);
//            Intent mIntent = new Intent(context, WebViewActivity.class);
//            mIntent.putExtra(OPEN_NOTIFICATION, myValue);
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            context.startActivity(mIntent);
//        } else {
//            Intent mIntent = new Intent(context, WebViewActivity.class);
//            mIntent.putExtra(OPEN_NOTIFICATION, "IS_NULL");
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            context.startActivity(mIntent);
//        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    LogUtils.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtils.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

//	//send msg to MainActivity
//	private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//		}
//	}


}
