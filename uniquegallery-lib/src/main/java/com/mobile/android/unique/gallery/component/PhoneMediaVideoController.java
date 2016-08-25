package com.mobile.android.unique.gallery.component;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.mobile.android.unique.gallery.application.ApplicationLevel;
import com.mobile.android.unique.gallery.models.VideoAlbumEntry;
import com.mobile.android.unique.gallery.models.VideoEntry;

import java.util.ArrayList;
import java.util.HashMap;

public class PhoneMediaVideoController {
    private static final String[] projectionPhotos = {
            MediaStore.Video.Media._ID, MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DATA, MediaStore.Video.Media.DATE_TAKEN,
            MediaStore.Video.Media.RESOLUTION, MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,};
    public static LoadAllVideoMediaInterface loadallvideomediainterface;
    private static Context context;

    /**
     * Used to load all videos from local paths
     *
     * @param mContext
     */
    public static void loadAllVideoMedia(Context mContext) {
        context = mContext;

        new Thread(new Runnable() {

            @Override
            public void run() {
                Cursor cursor = null;
                HashMap<Integer, VideoAlbumEntry> albums = new HashMap<Integer, VideoAlbumEntry>();
                final ArrayList<VideoAlbumEntry> videoAlbumEntriesList = new ArrayList<VideoAlbumEntry>();
                try {
                    cursor = MediaStore.Video.query(ApplicationLevel.applicationContext.getContentResolver(), MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projectionPhotos);
                    if (cursor != null) {
                        int videoIdColumn = cursor.getColumnIndex(MediaStore.Video.Media._ID);
                        int bucketIdColumn = cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID);
                        int bucketNameColumn = cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
                        int dataColumn = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
                        int dateColumn = cursor.getColumnIndex(MediaStore.Video.Media.DATE_TAKEN);
                        int resolutionColumn = cursor.getColumnIndex(MediaStore.Video.Media.RESOLUTION);
                        int sizeColumn = cursor.getColumnIndex(MediaStore.Video.Media.SIZE);
                        int displaynameColumn = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME);
                        int durationColumn = cursor.getColumnIndex(MediaStore.Video.Media.DURATION);
                        if (cursor.getCount() >= 1) {
                            while (cursor.moveToNext()) {
                                int imageId = cursor.getInt(videoIdColumn);
                                int bucketId = cursor.getInt(bucketIdColumn);
                                String bucketName = cursor.getString(bucketNameColumn);
                                String path = cursor.getString(dataColumn);
                                long dateTaken = cursor.getLong(dateColumn);
                                String resolution = cursor.getString(resolutionColumn);
                                String size = cursor.getString(sizeColumn);
                                String displayname = cursor.getString(displaynameColumn);
                                String duration = cursor.getString(durationColumn);

                                VideoEntry mVideoEntry = new VideoEntry(imageId, bucketId, bucketName, path, dateTaken, resolution, size, displayname, duration, null);


                                VideoAlbumEntry albumEntry = albums.get(bucketId);
                                if (albumEntry == null) {
                                    albumEntry = new VideoAlbumEntry(bucketId, bucketName, mVideoEntry);
                                    albums.put(bucketId, albumEntry);
                                    videoAlbumEntriesList.add(albumEntry);
                                }
                                albumEntry.addPhoto(mVideoEntry);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
                    public void run() {
                        if (loadallvideomediainterface != null) {
                            loadallvideomediainterface.loadVideo(videoAlbumEntriesList);
                        }
                    }
                });
            }
        }).start();
        ;
    }

    /**
     * Used to run on Main thread
     *
     * @param runnable Runnable interface reference
     */
    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    /**
     * Used to run on Main thread after certain time delay in millis
     *
     * @param runnable Runnable interface reference
     * @param delay    delay in millis
     */
    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            ApplicationLevel.applicationHandler.post(runnable);
        } else {
            ApplicationLevel.applicationHandler.postDelayed(runnable, delay);
        }
    }

    /**
     * used to get the LoadAllVideoMediaInterface interface reference
     *
     * @return LoadAllVideoMediaInterface interface reference
     */
    public static LoadAllVideoMediaInterface getLoadallvideomediainterface() {
        return loadallvideomediainterface;
    }

    /**
     * used to set the LoadAllVideoMediaInterface interface reference
     *
     * @param loadallvideomediainterface interface reference
     */
    public static void setLoadallvideomediainterface(
            LoadAllVideoMediaInterface loadallvideomediainterface) {
        PhoneMediaVideoController.loadallvideomediainterface = loadallvideomediainterface;
    }

    public interface LoadAllVideoMediaInterface {
        /**
         * Used to load the videos from local paths
         *
         * @param videoAlbumList List of VideoAlbumEntry
         */
        void loadVideo(ArrayList<VideoAlbumEntry> videoAlbumList);
    }
}
