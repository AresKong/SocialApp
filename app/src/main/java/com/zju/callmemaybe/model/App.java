package com.zju.callmemaybe.model;

import android.app.Application;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.zju.callmemaybe.MessageHandler;
import com.zju.callmemaybe.service.PushManager;


/**
 * 初始化工作
 */
public class App extends Application{
    public static boolean debug = true;
    public static App myApp;

    private final String TAG = "App";


    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
//        AVOSCloud.initialize(this, "zFeMVYB4tMuBVvjcAWt8uBOh", "IInViyO81sNlBNj4TUSoXyQH");
        AVOSCloud.initialize(this, "DJKcQJUGePzrSCGwtLqJ7V7e", "DRdcMKQIWq7UQ2EDDY1H61UB");
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            public void done(AVException e) {
                if (e == null) {
                    // 保存成功
                    // 关联  installationId 到用户表等操作……
                } else {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
        PushManager.getInstance().init(this);

    }
}
