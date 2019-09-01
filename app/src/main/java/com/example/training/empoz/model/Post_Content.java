package com.example.training.empoz.model;

public class Post_Content {
    public String post_id,post_value,post_time;
    public Post_Content()
    {

    }
    public  Post_Content(String post_id,String post_value,String post_time)
    {
        this.post_id=post_id;
        this.post_value=post_value;
        this.post_time=post_time;

    }

    public String getPost_id() {
        return post_id;
    }

    public String getPost_time() {
        return post_time;
    }

    public String getPost_value() {
        return post_value;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public void setPost_value(String post_value) {
        this.post_value = post_value;
    }
}
