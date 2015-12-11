package com.zju.callmemaybe.model;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

public class MyContactList {
    private static List<String> contactList = new ArrayList<>();
    private static String contact;
    
    public static List getContactsName() {
        AVUser currentUser = AVUser.getCurrentUser();
        String userName = currentUser.getUsername();
        AVQuery<AVObject> query = new AVQuery<>(userName);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {
                    for (int k = 0; k < avObjects.size(); k++) {
                        if (!MyContactList.getContactList().contains(avObjects.get(k).getString("contacts"))) {
                            contact = avObjects.get(k).getString("contacts");
                            contactList.add(contact);
                        }
                    }
                } else {
                    Log.d("失败", "查询错误: " + e.getMessage());
                }
            }
        });
        return contactList;
    }
    public static List<String> getContactList() { return contactList;}

    public static void clearContactList() {
        contactList = new ArrayList<>();
    }
}
