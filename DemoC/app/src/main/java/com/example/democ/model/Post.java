package com.example.democ.model;

public class Post {
    private String imageUserPost;
    private String userNamePost;
    private String timePost;
    private String contentPost;
    private String imagePost;
    private String numberLikePost;

    public Post(String imageUserPost, String userNamePost, String timePost, String contentPost, String imagePost, String numberLikePost) {
        this.imageUserPost = imageUserPost;
        this.userNamePost = userNamePost;
        this.timePost = timePost;
        this.contentPost = contentPost;
        this.imagePost = imagePost;
        this.numberLikePost = numberLikePost;
    }

    public String getImageUserPost() {
        return imageUserPost;
    }

    public void setImageUserPost(String imageUserPost) {
        this.imageUserPost = imageUserPost;
    }

    public String getUserNamePost() {
        return userNamePost;
    }

    public void setUserNamePost(String userNamePost) {
        this.userNamePost = userNamePost;
    }

    public String getTimePost() {
        return timePost;
    }

    public void setTimePost(String timePost) {
        this.timePost = timePost;
    }

    public String getContentPost() {
        return contentPost;
    }

    public void setContentPost(String contentPost) {
        this.contentPost = contentPost;
    }

    public String getImagePost() {
        return imagePost;
    }

    public void setImagePost(String imagePost) {
        this.imagePost = imagePost;
    }

    public String getNumberLikePost() {
        return numberLikePost;
    }

    public void setNumberLikePost(String numberLikePost) {
        this.numberLikePost = numberLikePost;
    }
}
