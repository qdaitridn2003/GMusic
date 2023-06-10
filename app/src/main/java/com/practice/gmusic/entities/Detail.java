package com.practice.gmusic.entities;

public class Detail {

    private int id;
    private int playlistId;
    private int trackId;
    private String trackName;
    private String trackAuthor;
    private String trackUrl;
    private String trackImg;

    public Detail() {
    }

    public Detail(int playlistId, int trackId) {
        this.playlistId = playlistId;
        this.trackId = trackId;
    }

    public Detail(int id, int playlistId, int trackId, String trackName, String trackAuthor,
                  String trackUrl, String trackImg) {
        this.id = id;
        this.playlistId = playlistId;
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

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
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
