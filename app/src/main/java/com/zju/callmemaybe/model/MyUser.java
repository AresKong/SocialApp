package com.zju.callmemaybe.model;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;

/**
 * 用户的抽象
 */
public class MyUser extends AVUser{
    public static final String TAG = "MyUser";
    public static final String USERNAME = "username";
    public static final String LOCATION = "location";
    public static String phoneNumber = "";
    public static String objectId = "";
    public static MyUser getCurrentUser() {
        return getCurrentUser(MyUser.class);
    }

    public static boolean isLogin() {
        return (AVUser.getCurrentUser() != null);
    }


    public static void signUpByNameAndPwd(String name, String password, SignUpCallback callback) {
        AVUser user = new AVUser();
        user.setUsername(name);
        user.setPassword(password);
        user.put("num", 0);
        user.signUpInBackground(callback);
    }

    public static void uploadPhoneNumber(String phone) {
        phoneNumber = phone;
        AVUser currentUser = AVUser.getCurrentUser();
        objectId = currentUser.getObjectId();
        AVObject post = AVObject.createWithoutData("_User", objectId);
        post.put("mobilePhoneNumber", phone);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.i("LeanCloud", "Save successfully.");
                } else {
                    Log.e("LeanCloud", e.getMessage());
                }
            }
        });
    }

    public static void uploadLocation() {

    }
}
