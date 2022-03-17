package com.example.tugas2musicplayer;

import java.io.Serializable;

//Serializable digunakan untuk passing atau mengirim ModelAudio ke Activity lainnya
public class ModelAudio implements Serializable {
    String path, title, duration;

    //Generate Constructor
    public ModelAudio(String path, String title, String duration) {
        this.path = path;
        this.title = title;
        this.duration = duration;
    }

    //Generate Getter and Setter
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
