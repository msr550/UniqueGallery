package com.mobile.android.unique.gallery.utils;

/**
 * Created by MSR on 8/10/2016.
 */
public class StorageManager {
    private static StorageManager storageManager = null;
    // private ArrayList<PhoneMediaControl.AlbumEntry> albumsSorted = new ArrayList<>();

    private Object selectedAlbumObject = null;


    private StorageManager() {
    }

    public static StorageManager getInstance() {
        if (storageManager == null) {
            storageManager = new StorageManager();
        }
        return storageManager;
    }

   /* public ArrayList<PhoneMediaControl.AlbumEntry> getAlbumsSorted() {
        return albumsSorted;
    }

    public void setAlbumsSorted(ArrayList<PhoneMediaControl.AlbumEntry> albumsSorted) {
        this.albumsSorted = albumsSorted;
    }*/

    public Object getSelectedAlbumObject() {
        return selectedAlbumObject;
    }

    public void setSelectedAlbumObject(Object object) {
        this.selectedAlbumObject = object;
    }
}
