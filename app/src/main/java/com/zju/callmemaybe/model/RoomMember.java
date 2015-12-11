package com.zju.callmemaybe.model;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomMember {
    private static JSONArray members = new JSONArray();
    private static List<String> objectIdList = new ArrayList<>();
    private static List<HashMap<String,String>> infoList = new ArrayList<>();
    private static HashMap<String,String> singleInfo = new HashMap<>();
    private static boolean addFlag = true;

    public static JSONArray getRoomMembers(String objectId) {
        AVQuery<AVObject> query = new AVQuery<>("rooms");
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.whereEqualTo("objectId", objectId);
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {
                    members = avObjects.get(0).getJSONArray("members");
                }
                else {
                    Log.d("失败", "查询错误: " + e.getMessage());
                }
            }
        });
        return members;
    }

    public static JSONArray getRoomMembers() { return  members; }

    public static List getMemberInfo() {
        members = getRoomMembers();
        for (int i = 0; i < members.length(); i++)
            try {
                objectIdList.add(members.getString(i));
            } catch (JSONException e) {

            }
        AVQuery<AVObject> query = new AVQuery<>("_User");
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        for (String objectId : objectIdList) {
            query.whereEqualTo("objectId", objectId);
            query.findInBackground(new FindCallback<AVObject>() {
                public void done(List<AVObject> avObjects, AVException e) {
                    if (e == null) {
                        singleInfo.put("name", avObjects.get(0).getString("username"));
                        singleInfo.put("phone", avObjects.get(0).getString("mobilePhoneNumber"));
//                        singleInfo.put("dist", avObjects.get(0).getString("dist"));
                        for (HashMap<String, String> info : infoList) {
                            if (info.get("name").equals(singleInfo.get("name")) && info.get("phone").equals(singleInfo.get("phone")))
                                addFlag = false;
                            else
                                addFlag = true;
                        }
                        if (addFlag) {
                            infoList.add(singleInfo);
                            singleInfo = new HashMap<>();
                        }
                    } else {
                        Log.d("失败", "查询错误: " + e.getMessage());
                    }
                }
            });
        }
        return infoList;
    }
}
