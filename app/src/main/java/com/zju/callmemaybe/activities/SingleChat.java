package com.zju.callmemaybe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.zju.callmemaybe.Constants;
import com.zju.callmemaybe.R;
import com.zju.callmemaybe.custom.ChatFragment;
import com.zju.callmemaybe.model.MyClientManager;
import com.zju.callmemaybe.model.MyConversation;
import com.zju.callmemaybe.utils.ConversationCacheUtils;

import butterknife.Bind;

public class SingleChat extends BaseActivity {

    @Bind(R.id.id_toolbar)
    protected Toolbar toolbar;

    private ChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_chat);

        chatFragment = (ChatFragment) getFragmentManager().findFragmentById(R.id.fragment_chat);

        setActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String conversationId = getIntent().getStringExtra(Constants.CONVERSATION_ID);
        if (conversationId != null) {
            AVIMConversation con = ConversationCacheUtils.getCacheConversation(conversationId);
            chatFragment.setConversation(con);
            toolbar.setTitle(MyConversation.otherIdOfConversation(con));
        } else {
            String memberId = getIntent().getStringExtra(Constants.MEMBER_ID);
            toolbar.setTitle(memberId);
            getConversation(memberId);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if (null != extras && extras.containsKey(Constants.MEMBER_ID)) {
            String memberId = extras.getString(Constants.MEMBER_ID);
            toolbar.setTitle(memberId);
            getConversation(memberId);
        }
    }

    private void getConversation(final String memberId) {
        MyClientManager.getInstance().fetchConversationWithUserId(memberId, new AVIMConversationCreatedCallback() {
            @Override
            public void done(AVIMConversation avimConversation, AVIMException e) {
                if (filterException(e)) {
                    chatFragment.setConversation(avimConversation);
                }
            }
        });
    }
}
