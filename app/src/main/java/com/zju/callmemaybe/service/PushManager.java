package com.zju.callmemaybe.service;

import android.content.Context;

import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVPush;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.PushService;
import com.zju.callmemaybe.activities.Launch;

import java.util.HashMap;
import java.util.Map;

public class PushManager {
    public final static String ALERT = "alert";

    private final static String ACTION = "action";
    public static final String INSTALLATION_CHANNELS = "channels";
    private static PushManager pushManager;
    private Context context;

    public synchronized static PushManager getInstance() {
        if (pushManager == null) {
            pushManager = new PushManager();
        }
        return pushManager;
    }

    public void init(Context context) {
        this.context = context;
        PushService.setDefaultPushCallback(context, Launch.class);
        subscribeCurrentUserChannel();
    }

    private void subscribeCurrentUserChannel() {
        if (AVUser.getCurrentUser() != null) {
            PushService.subscribe(context, AVUser.getCurrentUser().getObjectId(),
                    Launch.class);
        }
    }

    public void unsubscribeCurrentUserChannel() {
        if (AVUser.getCurrentUser() != null) {
            PushService.unsubscribe(context, AVUser.getCurrentUser().getObjectId());
        }
    }

    public void pushMessage(String userId, String message, String action) {
        AVQuery query = AVInstallation.getQuery();
        query.whereContains(INSTALLATION_CHANNELS, userId);
        AVPush push = new AVPush();
        push.setQuery(query);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put(ALERT, message);
        dataMap.put(ACTION, action);
        push.setData(dataMap);
        push.sendInBackground();
    }
}
