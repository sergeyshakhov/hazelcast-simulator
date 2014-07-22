package com.hazelcast.stabilizer.common.messaging;

import com.hazelcast.stabilizer.Utils;
import com.hazelcast.stabilizer.common.KeyValuePair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Message implements Serializable {
    private MessageAddress messageAddress;

    public Message(MessageAddress messageAddress) {
        if (messageAddress == null) {
            throw new IllegalArgumentException("Message address cannot be null");
        }
        this.messageAddress = messageAddress;
    }

    public static String getMessageHelp() {
        Set<String> messageSpecs = MessagesFactory.getMessageSpecs();
        StringBuilder builder = new StringBuilder();
        for (String spec : messageSpecs) {
            builder.append(spec)
                    .append(" - ")
                    .append(MessagesFactory.getMessageDescription(spec))
                    .append(Utils.NEW_LINE);
        }
        return builder.toString();
    }

    public MessageAddress getMessageAddress() {
        return messageAddress;
    }

    public boolean disableMemberFailureDetection() {
        return false;
    }

    public static Message newBySpec(String messageTypeSpec, String messageAddressSpec) {
        return MessagesFactory.bySpec(messageTypeSpec, messageAddressSpec);
    }

    public static Message newBySpec(String messageTypeSpec, MessageAddress messageAddress) {
        return MessagesFactory.bySpec(messageTypeSpec, messageAddress);
    }

    public static Message newBySpecWithAttribute(String messageTypeSpec, String messageAddressSpec,
                                                 KeyValuePair<? extends Serializable, ? extends Serializable> attribute) {
        return MessagesFactory.bySpec(messageTypeSpec, messageAddressSpec, attribute);
    }

    public static Message newBySpecWithAttribute(String messageTypeSpec, MessageAddress messageAddress,
                                                 KeyValuePair<? extends Serializable, ? extends Serializable> attribute) {
        return MessagesFactory.bySpec(messageTypeSpec, messageAddress, attribute);
    }

}
