package com.practice.gmusic.entities;

public class Category {

    private int id;
    private String name;
    private int trackId;
    private String trackName;
    private String trackAuthor;
    private String trackUrl;
    private String trackImg;

    public Category() {
    }

    public Category(int id, String name, int trackId, String trackName, String trackAuthor, String trackUrl,
                    String trackImg) {
        this.id = id;
        this.name = name;
        this.trackId = trackId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
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
