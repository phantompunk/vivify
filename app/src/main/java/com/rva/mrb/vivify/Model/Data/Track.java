package com.rva.mrb.vivify.Model.Data;

import io.realm.RealmObject;

/**
 * Created by Bao on 8/21/16.
 */
public class Track extends RealmObject {
    private String trackName;
    private String artist;
    private String trackId;

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
}
