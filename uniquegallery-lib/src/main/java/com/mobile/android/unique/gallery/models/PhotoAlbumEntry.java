package com.mobile.android.unique.gallery.models;

import java.util.ArrayList;

/**
 * Created by MSR on 8/11/2016.
 */
public class PhotoAlbumEntry {
    public int bucketId;
    public String bucketName;
    public PhotoEntry coverPhoto;
    public ArrayList<PhotoEntry> photos = new ArrayList<PhotoEntry>();

    public PhotoAlbumEntry(int bucketId, String bucketName, PhotoEntry coverPhoto) {
        this.bucketId = bucketId;
        this.bucketName = bucketName;
        this.coverPhoto = coverPhoto;
    }

    public void addPhoto(PhotoEntry photoEntry) {
        photos.add(photoEntry);
    }
}