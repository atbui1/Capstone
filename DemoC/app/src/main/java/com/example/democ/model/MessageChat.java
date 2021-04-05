package com.example.democ.model;

public class MessageChat {
    private String imageUserMessage;
    private String usernameMessage;
    private String timeMessage;

    public MessageChat(String imageUserMessage, String usernameMessage, String timeMessage) {
        this.imageUserMessage = imageUserMessage;
        this.usernameMessage = usernameMessage;
        this.timeMessage = timeMessage;
    }

    public String getImageUserMessage() {
        return imageUserMessage;
    }

    public void setImageUserMessage(String imageUserMessage) {
        this.imageUserMessage = imageUserMessage;
    }

    public String getUsernameMessage() {
        return usernameMessage;
    }

    public void setUsernameMessage(String usernameMessage) {
        this.usernameMessage = usernameMessage;
    }

    public String getTimeMessage() {
        return timeMessage;
    }

    public void setTimeMessage(String timeMessage) {
        this.timeMessage = timeMessage;
    }
}
