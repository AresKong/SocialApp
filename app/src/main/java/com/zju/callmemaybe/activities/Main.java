package com.zju.callmemaybe.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.zju.callmemaybe.Constants;
import com.zju.callmemaybe.R;
import com.zju.callmemaybe.adapter.MyPagerAdapter;
import com.zju.callmemaybe.custom.PagerSlidingTabStrip;
import com.zju.callmemaybe.model.MyContactList;
import com.zju.callmemaybe.model.MyUser;
import com.zju.callmemaybe.service.PushManager;

public class Main extends BaseActivity {
    private static final String TAG = "Main";
    public static void goMainActivityFromActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, Main.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntent();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setActionBar(toolbar);

        //Set the sliding pages
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        MyPagerAdapter adapter = new MyPagerAdapter(getFragmentManager());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(adapter);
        tabs.setViewPager(pager);
        tabs.setSelectedTextColor(getResources().getColor(R.color.myPrimaryDark));
        tabs.setTextColor(getResources().getColor(R.color.TextColorDark));
        tabs.setBackgroundColor(getResources().getColor(R.color.white));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logOut) {
            logout();
            return true;
        }

        else if (id == R.id.scanQRcode) {
            openRoom();
            return true;
        }
        else if (id == R.id.addFriends) {
            addFriend();
            return true;
        }
        else if (id == R.id.startSessions) {
            addPhoneNumber();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) {
            user.put(Constants.INSTALLATION, null);
            user.put("num", 0);
            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (filterException(e)) {
                        PushManager.getInstance().unsubscribeCurrentUserChannel();
                        MyContactList.clearContactList();
                        AVUser.logOut();
                        finish();
                        Intent intent = new Intent(Main.this, Launch.class);
                        startActivity(intent);
                    }
                }
            });
        } else {
            toast("异常！用户为空");
            Intent intent = new Intent(Main.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void openRoom() {
        final String[] topics = new String[]{"学术", "娱乐", "其他"};
        new AlertDialog.Builder(this).setTitle("请选择房间类型").setSingleChoiceItems(
                topics, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Main.this, CreateRoom.class);
                        intent.putExtra("topics", topics[which]);
                        startActivity(intent);
                    }
                }).show();
    }

    public void addFriend() {
        Intent intent = new Intent(Main.this, AddFriend.class);
        startActivity(intent);
    }

    public void userInfo(View view) {

    }

    public void addPhoneNumber() {
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this).setTitle("添加手机号").setIcon(
                android.R.drawable.ic_menu_call).setView(
                editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phone = editText.getText().toString();
                MyUser.uploadPhoneNumber(phone);
                Toast.makeText(Main.this, "添加手机号成功", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public void setting(View view) {
        new AlertDialog.Builder(this).setTitle("手机号").setIcon(
                android.R.drawable.ic_menu_call).setMessage(MyUser.phoneNumber).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public void send(View view) {
        EditText testUser = (EditText) findViewById(R.id.testMessage);
        final String userId = testUser.getText().toString().trim();
        Intent intent = new Intent(Main.this, SingleChat.class);
        intent.putExtra(Constants.MEMBER_ID, userId);
        startActivity(intent);
    }

}
