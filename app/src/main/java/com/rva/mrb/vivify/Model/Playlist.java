package com.rva.mrb.vivify.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rigo on 7/11/16.
 */
public class Playlist {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Playlists getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Playlists playlists) {
        this.playlists = playlists;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    private String message;
    private Playlists playlists;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public class Playlists {

        private String href;
        private List<Item> items = new ArrayList<Item>();
        private Integer limit;
        private String next;
        private Integer offset;
        private Object previous;
        private Integer total;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public Integer getOffset() {
            return offset;
        }

        public void setOffset(Integer offset) {
            this.offset = offset;
        }

        public Object getPrevious() {
            return previous;
        }

        public void setPrevious(Object previous) {
            this.previous = previous;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Map<String, Object> getAdditionalProperties() {
            return additionalProperties;
        }

        public void setAdditionalProperties(Map<String, Object> additionalProperties) {
            this.additionalProperties = additionalProperties;
        }
    }

    public class Item {

        private Boolean collaborative;
        private User.ExternalUrls externalUrls;
        private String href;
        private String id;
        private List<User.Image> images = new ArrayList<User.Image>();
        private String name;
        //        private Owner owner;
        private Object _public;
        private String snapshotId;
        private Tracks tracks;
        private String type;
        private String uri;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Boolean getCollaborative() {
            return collaborative;
        }

        public void setCollaborative(Boolean collaborative) {
            this.collaborative = collaborative;
        }

        public User.ExternalUrls getExternalUrls() {
            return externalUrls;
        }

        public void setExternalUrls(User.ExternalUrls externalUrls) {
            this.externalUrls = externalUrls;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<User.Image> getImages() {
            return images;
        }

        public void setImages(List<User.Image> images) {
            this.images = images;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object get_public() {
            return _public;
        }

        public void set_public(Object _public) {
            this._public = _public;
        }

        public String getSnapshotId() {
            return snapshotId;
        }

        public void setSnapshotId(String snapshotId) {
            this.snapshotId = snapshotId;
        }

        public Tracks getTracks() {
            return tracks;
        }

        public void setTracks(Tracks tracks) {
            this.tracks = tracks;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public Map<String, Object> getAdditionalProperties() {
            return additionalProperties;
        }

        public void setAdditionalProperties(Map<String, Object> additionalProperties) {
            this.additionalProperties = additionalProperties;
        }
    }
    public class Tracks {

    private String href;
    private Integer total;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Map<String, Object> getAdditionalProperties() {
            return additionalProperties;
        }

        public void setAdditionalProperties(Map<String, Object> additionalProperties) {
            this.additionalProperties = additionalProperties;
        }
    }
}
