package com.mobile.android.unique.gallery.models;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by user on 8/11/2016.
 */
public class VideoEntry implements Serializable {
    public int imageId;
    public int bucketId;
    public String bucketName;
    public String path;
    public long dateTaken;
    public String resolution;
    public String size;
    public String displayname;
    public String duration;
    public Bitmap curThumb;

    public VideoEntry(int imageId, int bucketId, String bucketName,
                      String path, long dateTaken, String resolution, String size,
                      String displayname, String duration, Bitmap curThumb) {
        this.imageId = imageId;
        this.bucketId = bucketId;
        this.bucketName = bucketName;
        this.path = path;
        this.dateTaken = dateTaken;
        this.resolution = resolution;
        this.size = size;
        this.displayname = displayname;
        this.duration = duration;
        this.curThumb = curThumb;
    }
}
