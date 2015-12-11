package com.zju.callmemaybe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.zju.callmemaybe.R;
import com.zju.callmemaybe.model.MyContactList;

import java.util.List;

public class AddFriend extends BaseActivity{
    private EditText editText;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntent();
        setContentView(R.layout.activity_add_friend);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addToContacts(View view) {
        editText = (EditText) findViewById(R.id.edit_message);
        final String contactName = editText.getText().toString();
        AVUser currentUser = AVUser.getCurrentUser();
        userName = currentUser.getUsername();
        if(MyContactList.getContactList().size() == 0)
            addContact(contactName);
        else {
            AVQuery<AVObject> query = new AVQuery<>(userName);
            query.whereEqualTo("contacts", contactName);
            query.findInBackground(new FindCallback<AVObject>() {
                public void done(List<AVObject> avObjects, AVException e) {
                    if (e == null) {
                        if (avObjects.size() == 0) {
                            addContact(contactName);
                        } else
                            Toast.makeText(AddFriend.this, "该联系人已存在", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("失败", "查询错误: " + e.getMessage());
                    }
                }
            });
        }

        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    public void addContact(String contactName) {
        AVObject post = new AVObject(userName);
        post.put("contacts", contactName);
        post.saveInBackground(new SaveCallback() {
            public void done(AVException e) {
                if (null == e) {
                    Toast.makeText(AddFriend.this, "已添加", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddFriend.this, "添加失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
