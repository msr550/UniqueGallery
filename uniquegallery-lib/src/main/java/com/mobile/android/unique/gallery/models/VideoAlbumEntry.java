package com.mobile.android.unique.gallery.models;

import java.util.ArrayList;

/**
 * Created by MSR on 8/11/2016.
 */
public class VideoAlbumEntry {
    public int bucketId;
    public String bucketName;
    public VideoEntry coverVideo;
    public ArrayList<VideoEntry> photos = new ArrayList<VideoEntry>();

    public VideoAlbumEntry(int bucketId, String bucketName, VideoEntry coverVideo) {
        this.bucketId = bucketId;
        this.bucketName = bucketName;
        this.coverVideo = coverVideo;
    }

    public void addPhoto(VideoEntry photoEntry) {
        photos.add(photoEntry);
    }
}
