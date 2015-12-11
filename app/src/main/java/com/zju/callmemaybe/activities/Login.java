package com.zju.callmemaybe.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.zju.callmemaybe.Constants;
import com.zju.callmemaybe.R;
import com.zju.callmemaybe.model.MyUser;
import com.zju.callmemaybe.model.MyClientManager;

import butterknife.Bind;

public class Login extends BaseActivity {
    private static final String TAG = "Login";

    @Bind(R.id.userName)
    protected TextView txtUserName;

    @Bind(R.id.pwd)
    protected TextView txtPwd;

    public static void goLoginActivityFromActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, Login.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVAnalytics.trackAppOpened(getIntent());
        setContentView(R.layout.activity_login);
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
        return super.onOptionsItemSelected(item);
    }

    public void login(View view) {
        final String username = txtUserName.getText().toString().trim();
        final String pwd = txtPwd.getText().toString().trim();
        MyUser.logInInBackground(username, pwd, new LogInCallback<MyUser>() {
            public void done(MyUser user, AVException e) {
                if (user != null) {
                    if (user.getInt("num") == 0) {
                        // 登录成功
                        updateUserInfo(user);
                    } else {
                        toast("该账号已在其他设备登录");
                    }
                } else {
                    // 登录失败
                    toast("登录失败");
                }
            }
        }, MyUser.class);

    }

    public void updateUserInfo(final MyUser user) {
        AVInstallation installation = AVInstallation.getCurrentInstallation();
        if (installation != null) {
            user.put(Constants.INSTALLATION, installation);
            user.put("num", 1);
            user.saveInBackground(new SaveCallback() {
                public void done(AVException e) {
                    if (filterException(e)) {
                        //根据用户名生成一个Client
                        MyClientManager.getInstance().open(user.getUsername(), new AVIMClientCallback() {
                            @Override
                            public void done(AVIMClient avimClient, AVIMException e) {
                                if (filterException(e)) {
                                    toast("登录成功");
                                    Main.goMainActivityFromActivity(Login.this);
                                    finish();
                                }
                            }
                        });

                    }
                }
            });
        }

    }


    public void toSignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

}
