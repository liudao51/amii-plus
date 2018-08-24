package com.amii.plus.api.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class MessageEntity implements Serializable
{
    /**
     * TODO: 消息内容
     */
    private String content;
}