package com.example.democ.iclick;

import com.example.democ.model.MessageChat;

public interface IClickMessage {
    void clickMessage(MessageChat messageChat);
    void clickDelete(int position, int count);
}
