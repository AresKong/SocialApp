package com.zju.callmemaybe.viewholder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.zju.callmemaybe.Constants;
import com.zju.callmemaybe.R;
import com.zju.callmemaybe.activities.SingleChat;
import com.zju.callmemaybe.model.MyContactList;

import java.util.List;

import butterknife.Bind;

public class ContactsItemHolder extends AVCommonViewHolder {

    @Bind(R.id.contactName)
    protected TextView nameView;

    private String userId;
    public ContactsItemHolder(ViewGroup root) {
    	super(root.getContext(), root, R.layout.user_item);
    }

    @Override
    public void bindData(Object o) {
        String name = (String) o;
        nameView.setText(name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                new AlertDialog.Builder(getContext()).setTitle("提示")
                        .setMessage("请选择操作")
                        .setPositiveButton("发送消息", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String userId = nameView.getText().toString().trim();
                                Intent intent = new Intent(getContext(), SingleChat.class);
                                intent.putExtra(Constants.MEMBER_ID, userId);
                                getContext().startActivity(intent);
                            }

                        }).setNegativeButton("删除好友", new DialogInterface.OnClickListener() {//添加返回按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userId = nameView.getText().toString().trim();
                                MyContactList.getContactList().remove(userId);
                                new Thread(removeContact).start();
                                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                            }

                        }).show();
            }

        });
    }

    Runnable removeContact = new Runnable() {
        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
            AVUser currentUser = AVUser.getCurrentUser();
            String userName = currentUser.getUsername();
            final AVQuery<AVObject> query = new AVQuery<>(userName);
            query.whereEqualTo("contacts", userId);
            query.findInBackground(new FindCallback<AVObject>() {
                public void done(List<AVObject> avObjects, AVException e) {
                    if (e == null) {
                        try {
                        AVObject.deleteAll(avObjects);
//                            query.deleteAll();
                        } catch (AVException ee) {
                            Log.d("失败", "删除错误: " + ee.getMessage());
                        }
                    } else {
                        Log.d("失败", "查询错误: " + e.getMessage());
                    }
                }
            });
        }
    };
}
