package com.rva.mrb.vivify.Model.Data;


import org.parceler.Parcel;

import java.nio.channels.AlreadyConnectedException;

@Parcel
public class MediaType {

    public static final int TRACK_TYPE = 0;
    public static final int ARTIST_TYPE = 1;
    public static final int PLAYLIST_TYPE = 2;
    public static final int ALBUM_TYPE = 3;

    private Track track;
    private Album album;
    private Playlist playlist;
    private Artist artist;
    private int type;

    public MediaType() { }

    public MediaType(Track track) {
        this.track = track;
        this.type = TRACK_TYPE;
    }

    public MediaType(Album album) {
        this.album = album;
        this.type = ALBUM_TYPE;
    }

    public MediaType(Playlist playlist) {
        this.playlist = playlist;
        this.type = PLAYLIST_TYPE;
    }

    public MediaType(Artist artist) {
        this.artist = artist;
        this.type = ARTIST_TYPE;
    }

    public int getMediaType() {
        return type;
    }

    public Track getTrack() {
        return track;
    }

    public Album getAlbum() {
        return this.album;
    }

    public Artist getArtist() {
        return artist;
    }

    public Playlist getPlaylist() {
        return playlist;
    }
}
