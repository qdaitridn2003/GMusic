package com.practice.gmusic.entities;

import java.io.Serializable;

public class Track implements Serializable {

    private int id;
    private String trackName;
    private String trackAuthor;
    private String trackUrl;
    private String trackImg;

    public Track() {
    }

    public Track(int id, String trackName, String trackAuthor, String trackUrl, String trackImg) {
        this.id = id;
        this.trackName = trackName;
        this.trackAuthor = trackAuthor;
        this.trackUrl = trackUrl;
        this.trackImg = trackImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackAuthor() {
        return trackAuthor;
    }

    public void setTrackAuthor(String trackAuthor) {
        this.trackAuthor = trackAuthor;
    }

    public String getTrackUrl() {
        return trackUrl;
    }

    public void setTrackUrl(String trackUrl) {
        this.trackUrl = trackUrl;
    }

    public String getTrackImg() {
        return trackImg;
    }

    public void setTrackImg(String trackImg) {
        this.trackImg = trackImg;
    }
}
