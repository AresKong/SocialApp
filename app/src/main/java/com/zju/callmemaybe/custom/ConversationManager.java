package com.zju.callmemaybe.custom;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.zju.callmemaybe.model.ConversationType;
import com.zju.callmemaybe.model.MyConversation;
import com.zju.callmemaybe.utils.ConversationCacheUtils;
import com.zju.callmemaybe.model.MyClientManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConversationManager {
    private static ConversationManager conversationManager;

    public ConversationManager() {}

    public static synchronized ConversationManager getInstance() {
        if (conversationManager == null) {
            conversationManager = new ConversationManager();
        }
        return conversationManager;
    }


    public void findRecentConversations(final MyConversation.MultiConversationsCallback callback) {
        AVIMConversationQuery query = MyClientManager.getInstance().getClient().getQuery();
        query.containsMembers(Arrays.asList(MyClientManager.getInstance().getClientId()));
        query.whereEqualTo(ConversationType.ATTR_TYPE_KEY, ConversationType.Single.getValue());
        query.orderByDescending(MyClientManager.KEY_UPDATED_AT);
        query.limit(100);
        query.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                List<String> conversationIds = new ArrayList<>();
                final List<MyConversation> conversations = new ArrayList<>();

                for (AVIMConversation c : list) {
                    MyConversation mConversation = new MyConversation();
                    conversationIds.add(c.getConversationId());

                    mConversation.setConversationId(c.getConversationId());
                    mConversation.setUnreadCount(0);
                    conversations.add(mConversation);
                }

                if (conversationIds.size() > 0) {
                    ConversationCacheUtils.cacheConversations(conversationIds, new ConversationCacheUtils.CacheConversationCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e != null) {
                                callback.done(conversations, e);
                            } else {
                                callback.done(conversations, null);
                            }
                        }
                    });
                } else {
                    callback.done(conversations, null);
                }
            }
        });
    }


}
