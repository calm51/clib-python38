package ca.calm.openssldemo;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.content.Context;
import android.view.WindowManager;
import android.os.Build;
import android.content.Intent;
import android.app.PendingIntent;
import android.util.Log;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;

import android.app.Activity;
import android.graphics.Rect;
import android.view.Window;
import android.os.Vibrator;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import android.speech.tts.TextToSpeech;

import org.qtproject.qt5.android.bindings.QtActivity;
import org.qtproject.qt5.android.bindings.QtApplication;
import android.util.Log;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import java.util.Locale;
import java.lang.String;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import android.view.WindowInsets;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import android.graphics.Color;
import android.graphics.BitmapFactory;
import android.app.NotificationChannel;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;


public class openssldemo extends org.qtproject.qt5.android.bindings.QtActivity implements TextToSpeech.OnInitListener{
    static final private String TAG = "openssldemo";
    private Context m_context = null;
    private static Handler m_handler = null;
    private static openssldemo m_instance;
    private static NotificationManager m_notificationManager = null;
    private static Notification.Builder m_builder = null;
    static public openssldemo instance() { return m_instance; }
    public void setContext(Context context) {context.toString();}

    public native void statusbarHeightChanged(int i);
    public int statusbarHeight = 88;

    private static TextToSpeech m_tts = null;

    public openssldemo(){
        //System.out.println("");
        Log.d(TAG, "I'm coming!");
        m_instance = this;
    }

    public static String joinArgs(String... args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {sb.append(" ").append(arg);}
        return sb.toString().trim();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, joinArgs("onCreate()", savedInstanceState!=null?savedInstanceState.toString():""));
        super.onCreate(savedInstanceState);
        m_context = getApplicationContext();
        m_handler = new Handler() {
               @Override
               public void handleMessage(Message msg) {
                   switch (msg.what) {
                   case 1:
                       Toast toast = Toast.makeText(m_instance, (String)msg.obj, Toast.LENGTH_SHORT);
                       toast.show();
                       break;
                   };
               }
           };
        m_tts = new TextToSpeech(m_context,this);
        int result = m_tts.setLanguage(Locale.CHINA);
        Log.d(TAG, joinArgs("m_tts.setLanguage()", Integer.toString(result)));

    //   // 获取系统服务窗口管理器对象
    //       WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    //       // 创建一个Rect对象，并获取可见矩形的边界
    //       Rect rect = new Rect();
    //       getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
    //       // 计算状态栏高度
    //       int statusbarHeight = rect.top;

    //   getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    //   getWindow().getDecorView().setFitsSystemWindows(true);

        this.setStatusBarFullTransparent();

     }

    @Override
    public void onInit(int status) {
        Log.d(TAG, "onInit()");
        /*
        Locale loc = new Locale("es", "","");
        if(m_tts.isLanguageAvailable(loc) >= TextToSpeech.LANG_AVAILABLE){
                m_tts.setLanguage(loc);
        }
        m_tts.speak("hola mundos", TextToSpeech.QUEUE_FLUSH, null);
        */
    }

    public boolean move_task_to_back(boolean b){
        Log.d(TAG, joinArgs("move_task_to_back()",Boolean.toString(b)));
        return this.moveTaskToBack(b); // isTaskRoot()
    }

    @Override
    protected void onPause() { // 在 Activity 失去焦点时调用，但在 Activity 完全不可见之前，对应着 Activity 从可见状态到不可见状态的过渡
        Log.d(TAG, "onPause()");
        super.onPause();
    }
    @Override
    protected void onStop() { // 在 Activity 即将变为不可见时调用，但在 Activity 完全不可见之前，对应着 Activity 从前台转到后台的事件
        Log.d(TAG, "onStop()");
        super.onStop();

        this.upStatusbarHeight();
    }
    @Override
    protected void onResume() { // 在 Activity 即将开始交互并变为用户可见时调用，对应着 Activity 从不可见状态到可见状态的过渡
        Log.d(TAG, "onResume()");
        super.onResume();
    }
    @Override
    protected void onStart() { // 在 Activity 即将变为用户可见时调用，但在 Activity 变为前台并且开始交互之前，对应着 Activity 从后台转到前台的事件
        Log.d(TAG, "onStart()");
        super.onStart();
    }

    @Override
    protected void onRestart() { // 当Activity由停止状态重新启动时调用
        Log.d(TAG, "onRestart()");
        super.onRestart();
    }
    @Override
    protected void onDestroy() { // 当Activity即将被销毁时调用
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
        m_tts.shutdown();
    }


    public void toast(String s){
        Log.d(TAG, joinArgs("toast()", s));
        m_handler.sendMessage(m_handler.obtainMessage(1, s));
    }

    public void speak(String msg){
        Log.d(TAG, joinArgs("speak()", msg));
        m_tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null,null);
    }

    public void toStatusbarTextWhite() {
        if (Build.VERSION.SDK_INT >= 6 ) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    public void toStatusbarTextBlack() {
        if (Build.VERSION.SDK_INT >= 6 ) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    private void setStatusBarFullTransparent(){
        if (Build.VERSION.SDK_INT >= 6 ) { //Build.VERSION_CODES.M
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
            // 状态栏字体设置为深色，SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 为SDK23增加 //
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // 部分机型的statusbar会有半透明的黑色背景
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);// SDK21
        }
    }

    public static boolean isNativeMethodImplemented(Class<?> clazz, String methodName) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
          if (method.getName().equals(methodName) && Modifier.isNative(method.getModifiers())) {
              return true;
          }
        }
        return false;
    }
    public void upStatusbarHeight() {
//        isNativeMethodImplemented(this.getClass(), "statusbarHeightChanged");

//        getWindow().getDecorView().setOnApplyWindowInsetsListener((v,insets) ->{
//            int statusbarHeight = insets.getStableInsetTop();
//            this.statusbarHeight = statusbarHeight;
//            statusbarHeightChanged(this.statusbarHeight);
//            return insets;
//            });
//        int statusbarHeight = 0;
//        int resourceId = this.getResources().getIdentifier("status_bar_height","dimen","android");
//        if (resourceId>0){
//            statusbarHeight = this.getResources().getDimensionPixelSize(resourceId);
//            this.statusbarHeight = statusbarHeight;}
//        statusbarHeightChanged(this.statusbarHeight);
        WindowInsets insets = getWindow().getDecorView().getRootWindowInsets();
        int statusbarHeight = insets.getStableInsetTop();
        this.statusbarHeight = statusbarHeight;
        statusbarHeightChanged(this.statusbarHeight);
    }

//    public void notify(String s){
//        Log.d(TAG, joinArgs("notify()", s));
//        if (m_notificationManager == null) {
//            m_notificationManager = (NotificationManager)m_instance.getSystemService(Context.NOTIFICATION_SERVICE);
//            m_builder = new Notification.Builder(m_instance);
//            m_builder.setSmallIcon(R.drawable.icon);
//            m_builder.setContentTitle("A message from Qt!");
//        }
//        m_builder.setContentText(s);
//        m_notificationManager.notify(1, m_builder.build());
//    }
    public void notify(String title, String message) {
        try {
            m_notificationManager = (NotificationManager) m_context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel notificationChannel = new NotificationChannel("Qt", "Qt Notifier", importance);
                m_notificationManager.createNotificationChannel(notificationChannel);
                m_builder = new Notification.Builder(m_context, notificationChannel.getId());
            } else {
                m_builder = new Notification.Builder(m_context);
            }
            m_builder.setSmallIcon(R.drawable.icon)
                    .setLargeIcon(BitmapFactory.decodeResource(m_context.getResources(), R.drawable.icon))
                    .setContentTitle(title)
                    .setContentText(message)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setColor(Color.TRANSPARENT)
                    .setAutoCancel(true);

            m_notificationManager.notify(0, m_builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void vibrate(int milliseconds) {
        Log.d(TAG, joinArgs("vibrate()", Integer.toString(milliseconds)));
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            if(milliseconds==-1){ // 长震动
                long[] p = {800,1000,800,1000};
                vibrator.vibrate(p,0);
            }else if(milliseconds==0){ // 取消震动
                vibrator.cancel();
            }else{ // 短震动
                vibrator.vibrate(milliseconds);
            }
        }
    }

    public void sendSound() {
    //        String soundName = ":/data/src/waw.waw";
    //        int resId = context.getResources().getIdentifier(soundName.substring(1), "raw", context.getPackageName());
    //        Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + resId);
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone mRingtone = RingtoneManager.getRingtone(m_context, soundUri);
        mRingtone.play();
    }





// ==========================================================


public int addTest(int value0, int value1) {
    Log.d(TAG, "I'm a test.");
    return value0 + value1;
}


// ==========================================================





}
