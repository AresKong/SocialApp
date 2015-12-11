package com.zju.callmemaybe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.zju.callmemaybe.R;
import com.zju.callmemaybe.model.MyRoom;

public class CreateRoom extends BaseActivity {
    private EditText name;
    private EditText address;
    private EditText max;
    private EditText time;
    private EditText note;
    private String topic;
    private int name_checked = 0;
    private int address_checked = 0;
    private int max_checked = 0;
    private int time_checked = 0;
    private int note_checked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topic = getIntent().getStringExtra("topics");
        setContentView(R.layout.activity_create_room);
        name = (EditText) findViewById(R.id.RegRoomName);
        address = (EditText) findViewById(R.id.RegAddress);
        max = (EditText) findViewById(R.id.RegMax);
        time = (EditText) findViewById(R.id.RegTime);
        note = (EditText) findViewById(R.id.RegNote);

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String str = name.getText().toString();
                    if (str.length() <= 0) {
                        Toast.makeText(CreateRoom.this, "房间名不得为空", Toast.LENGTH_SHORT).show();
                        name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_white_24dp, 0);
                        name_checked = 0;
                    } else {
                        name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_black_24dp, 0);
                        name_checked = 1;
                    }
                }
            }
        });

        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String str = address.getText().toString();
                    if (str.length() <= 0) {
                        Toast.makeText(CreateRoom.this, "餐厅地址不得为空", Toast.LENGTH_SHORT).show();
                        address.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_white_24dp, 0);
                        address_checked = 0;
                    } else {
                        address.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_black_24dp, 0);
                        address_checked = 1;
                    }
                }
            }
        });

        max.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String str = max.getText().toString();
                    if (str.length() <= 0) {
                        Toast.makeText(CreateRoom.this, "最大人数不得为空", Toast.LENGTH_SHORT).show();
                        max.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_white_24dp, 0);
                        max_checked = 0;
                    } else {
                        max.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_black_24dp, 0);
                        max_checked = 1;
                    }
                }
            }
        });

        time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String str = time.getText().toString();
                    if (str.length() <= 0) {
                        Toast.makeText(CreateRoom.this, "聚餐时间不得为空", Toast.LENGTH_SHORT).show();
                        time.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_white_24dp, 0);
                        time_checked = 0;
                    } else {
                        time.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_black_24dp, 0);
                        time_checked = 1;
                    }
                }
            }
        });

        note.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String str = note.getText().toString();
                    if (str.length() <= 0) {
                        Toast.makeText(CreateRoom.this, "提供备注信息才能吸引其他同学哦", Toast.LENGTH_SHORT).show();
                        note_checked = 1;
                    }
                    note.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_black_24dp, 0);
                }
            }
        });
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

    public void regCreate(View view) {
        if (name_checked == 1 && address_checked == 1 && max_checked == 1
                && time_checked == 1 && note_checked == 1) {
            MyRoom.createTable(topic, name.getText().toString(), address.getText().toString(),
                    max.getText().toString(), time.getText().toString(), note.getText().toString(), new SaveCallback() {
                        public void done(AVException e) {
                            if (filterException(e)) {
                                // successfully
                                Toast.makeText(CreateRoom.this, "创建成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreateRoom.this, Main.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        } else {
            toast("信息不完整");
        }
    }
}
