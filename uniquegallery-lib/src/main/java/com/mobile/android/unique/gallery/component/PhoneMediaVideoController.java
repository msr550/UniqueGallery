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
    public static loadAllVideoMediaInterface loadallvideomediainterface;
    private static Context context;

    public static void loadAllVideoMedia(Context mContext) {
        context = mContext;

        new Thread(new Runnable() {

            @Override
            public void run() {
                Cursor cursor = null;
                HashMap<Integer, VideoAlbumEntry> albums = new HashMap<Integer, VideoAlbumEntry>();
                final ArrayList<VideoAlbumEntry> arrVideoDetails = new ArrayList<VideoAlbumEntry>();
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

//	 	                          String minikind cursor.getString( MediaStore.Video.Thumbnails.MINI_KIND);

//								BitmapFactory.Options options = new BitmapFactory.Options();
//								options.inSampleSize = 1;
//								Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(
//												ApplicationOwnGallery.applicationContext.getContentResolver(),imageId,
//												MediaStore.Video.Thumbnails.MINI_KIND,
//												options);

                                VideoEntry mVideoEntry = new VideoEntry(imageId, bucketId, bucketName, path, dateTaken, resolution, size, displayname, duration, null);


                                VideoAlbumEntry albumEntry = albums.get(bucketId);
                                if (albumEntry == null) {
                                    albumEntry = new VideoAlbumEntry(bucketId, bucketName, mVideoEntry);
                                    albums.put(bucketId, albumEntry);
                                    arrVideoDetails.add(albumEntry);
                                }
                                albumEntry.addPhoto(mVideoEntry);

//	 	                           VideoEntry mVideoEntry=new VideoEntry(imageId, bucketId, bucketName, path, dateTaken, resolution, size, displayname, duration,curThumb);
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
                            loadallvideomediainterface.loadVideo(arrVideoDetails);
                        }
                    }
                });
            }
        }).start();
        ;
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

    public static loadAllVideoMediaInterface getLoadallvideomediainterface() {
        return loadallvideomediainterface;
    }

    public static void setLoadallvideomediainterface(
            loadAllVideoMediaInterface loadallvideomediainterface) {
        PhoneMediaVideoController.loadallvideomediainterface = loadallvideomediainterface;
    }

    public interface loadAllVideoMediaInterface {
        void loadVideo(ArrayList<VideoAlbumEntry> videoAlbumList);
    }

  /*  /////////////added code
    public static class AlbumEntry {
        public int bucketId;
        public String bucketName;
        public VideoEntry coverPhoto;
        public ArrayList<VideoEntry> photos = new ArrayList<VideoEntry>();

        public AlbumEntry(int bucketId, String bucketName, VideoEntry coverPhoto) {
            this.bucketId = bucketId;
            this.bucketName = bucketName;
            this.coverPhoto = coverPhoto;
        }

        public void addPhoto(VideoEntry photoEntry) {
            photos.add(photoEntry);
        }
    }
    /////////////added code

    public static class VideoEntry {
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
    }*/
}
