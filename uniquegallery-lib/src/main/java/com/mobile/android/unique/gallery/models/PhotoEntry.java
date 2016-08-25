package com.mobile.android.unique.gallery.models;

import java.io.Serializable;

/**
 * Created by MSR on 8/11/2016.
 */
public class PhotoEntry implements Serializable {
    public int bucketId;
    public int imageId;
    public long dateTaken;
    public String path;
    public int orientation;

    public PhotoEntry(int bucketId, int imageId, long dateTaken, String path, int orientation) {
        this.bucketId = bucketId;
        this.imageId = imageId;
        this.dateTaken = dateTaken;
        this.path = path;
        this.orientation = orientation;
    }
}
