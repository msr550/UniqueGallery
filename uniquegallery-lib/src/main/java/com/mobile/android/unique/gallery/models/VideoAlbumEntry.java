package com.mobile.android.unique.gallery.models;

import java.util.ArrayList;

/**
 * Created by user on 8/11/2016.
 */
public class VideoAlbumEntry {
    public int bucketId;
    public String bucketName;
    public VideoEntry coverPhoto;
    public ArrayList<VideoEntry> photos = new ArrayList<VideoEntry>();

    public VideoAlbumEntry(int bucketId, String bucketName, VideoEntry coverPhoto) {
        this.bucketId = bucketId;
        this.bucketName = bucketName;
        this.coverPhoto = coverPhoto;
    }

    public void addPhoto(VideoEntry photoEntry) {
        photos.add(photoEntry);
    }
}
