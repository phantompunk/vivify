package com.rva.mrb.vivify.Model.Data;


import java.nio.channels.AlreadyConnectedException;

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

    public MediaType(Track track, int type) {
        this.track = track;
        this.type = type;
    }

    public MediaType(Album album, int type) {
        this.album = album;
        this.type = type;
    }

    public MediaType(Playlist playlist, int type) {
        this.playlist = playlist;
        this.type = type;
    }

    public MediaType(Artist artist, int type) {
        this.artist = artist;
        this.type = type;
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
