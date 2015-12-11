package com.zju.callmemaybe.event;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * 推送消息事件
 */
public class ImTypeMessageEvent {
    public AVIMTypedMessage message;
    public AVIMConversation conversation;
}
