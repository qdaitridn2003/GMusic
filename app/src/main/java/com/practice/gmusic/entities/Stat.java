package com.practice.gmusic.entities;

public class Stat {

    private int trackId;
    private String trackName;
    private String trackAuthor;
    private String trackUrl;
    private String trackImg;
    private int amount;

    public Stat() {
    }

    public Stat(int trackId, String trackName, String trackAuthor, String trackUrl, String trackImg, int amount) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.trackAuthor = trackAuthor;
        this.trackUrl = trackUrl;
        this.trackImg = trackImg;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}