package com.zju.callmemaybe.model;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 房间的抽象
 */
public class MyRoom {
    public static final String TAG = "MyRoom";
    private static int roomNum = 0;
    private static List<HashMap<String,String>> roomList = new ArrayList<>();
    private static HashMap<String,String> singleRoom = new HashMap<>();
    private static boolean addFlag = true;

    public static void createTable(String topic, String name, String address, String max,
                                   String time, String note, SaveCallback callback) {
        AVUser currentUser = AVUser.getCurrentUser();
        String userName = currentUser.getUsername();
        roomNum ++;
        AVObject post = new AVObject("rooms");
        post.put("topic", topic);
        post.put("name", name);
        post.put("address", address);
        post.put("max", max);
        post.put("time", time);
        post.put("note", note);
        post.put("num", roomNum);
        post.put("host", userName);
        post.saveInBackground(callback);
    }

    public static List getAllRooms() {
        AVQuery<AVObject> query = new AVQuery<>("rooms");
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {
                    for (int k = 0; k < avObjects.size(); k++) {
                        singleRoom.put("host", avObjects.get(k).getString("host"));
                        singleRoom.put("time",avObjects.get(k).getString("time"));
                        singleRoom.put("note", avObjects.get(k).getString("note"));
                        singleRoom.put("topic",avObjects.get(k).getString("topic"));
                        for (HashMap<String, String> room : roomList) {
                            if (room.get("host").equals(singleRoom.get("host")) && room.get("time").equals(singleRoom.get("time")))
                                addFlag = false;
                            else
                                addFlag = true;
                        }
                        if (addFlag) {
                            roomList.add(singleRoom);
                            singleRoom = new HashMap<>();
                        }
                    }
                } else {
                    Log.d("失败", "查询错误: " + e.getMessage());
                }
            }
        });
        return roomList;
    }
}
