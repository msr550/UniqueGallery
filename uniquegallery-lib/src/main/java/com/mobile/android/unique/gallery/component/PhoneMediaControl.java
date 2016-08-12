package com.mobile.android.unique.gallery.component;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.mobile.android.unique.gallery.application.ApplicationLevel;
import com.mobile.android.unique.gallery.models.PhotoAlbumEntry;
import com.mobile.android.unique.gallery.models.PhotoEntry;

import java.util.ArrayList;
import java.util.HashMap;

public class PhoneMediaControl {

    private static final String[] projectionPhotos = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.ORIENTATION
    };
    public static loadAlbumPhoto loadalbumphoto;
    private static Context context;

    public static void loadGalleryPhotosAlbums(Context mContext) {
        context = mContext;

        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<PhotoAlbumEntry> albumsSorted = new ArrayList<PhotoAlbumEntry>();
                HashMap<Integer, PhotoAlbumEntry> albums = new HashMap<Integer, PhotoAlbumEntry>();
                String cameraFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/" + "Camera/";
                Integer cameraAlbumId = null;

                Cursor cursor = null;
                try {
                    cursor = MediaStore.Images.Media.query(ApplicationLevel.applicationContext.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projectionPhotos, "", null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
                    if (cursor != null) {
                        int imageIdColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                        int bucketIdColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
                        int bucketNameColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                        int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                        int dateColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
                        int orientationColumn = cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION);

                        while (cursor.moveToNext()) {
                            int imageId = cursor.getInt(imageIdColumn);
                            int bucketId = cursor.getInt(bucketIdColumn);
                            String bucketName = cursor.getString(bucketNameColumn);
                            String path = cursor.getString(dataColumn);
                            long dateTaken = cursor.getLong(dateColumn);
                            int orientation = cursor.getInt(orientationColumn);

                            if (path == null || path.length() == 0) {
                                continue;
                            }
                            PhotoEntry photoEntry = new PhotoEntry(bucketId, imageId, dateTaken, path, orientation);
                            PhotoAlbumEntry albumEntry = albums.get(bucketId);
                            if (albumEntry == null) {
                                albumEntry = new PhotoAlbumEntry(bucketId, bucketName, photoEntry);
                                albums.put(bucketId, albumEntry);
                                if (cameraAlbumId == null && cameraFolder != null && path != null && path.startsWith(cameraFolder)) {
                                    albumsSorted.add(0, albumEntry);
                                    cameraAlbumId = bucketId;
                                } else {
                                    albumsSorted.add(albumEntry);
                                }
                            }
                            albumEntry.addPhoto(photoEntry);
                        }
                    }
                } catch (Exception e) {
                    Log.e("tmessages", e.toString());
                } finally {
                    if (cursor != null) {
                        try {
                            cursor.close();
                        } catch (Exception e) {
                            Log.e("tmessages", e.toString());
                        }
                    }
                }
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loadalbumphoto != null) {
                            loadalbumphoto.loadPhoto(albumsSorted);
                        }
                    }
                });
            }
        }).start();
    }

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            ApplicationLevel.applicationHandler.post(runnable);
        } else {
            ApplicationLevel.applicationHandler.postDelayed(runnable, delay);
        }
    }

    public loadAlbumPhoto getLoadalbumphoto() {
        return loadalbumphoto;
    }

    public void setLoadalbumphoto(loadAlbumPhoto loadalbumphoto) {
        this.loadalbumphoto = loadalbumphoto;
    }

    public interface loadAlbumPhoto {
        void loadPhoto(ArrayList<PhotoAlbumEntry> albumsSorted);
    }
}
