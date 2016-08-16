package com.mobile.android.unique.gallery.utils;

/**
 * Created by MSR on 8/10/2016.
 */
public class StorageManager {
    private static StorageManager storageManager = null;

    private Object selectedAlbumObject = null;

    /**
     * Private constructor which is used to prevent multiple object creations
     */
    private StorageManager() {
    }

    /**
     * Used to create StorageManager instance.
     * If StorageManager instance is null then only it creates object other wise retuns existing instance
     *
     * @return returns StorageManager instance
     */
    public static StorageManager getInstance() {
        if (storageManager == null) {
            storageManager = new StorageManager();
        }
        return storageManager;
    }

    /**
     * getting the selected media list
     *
     * @return returns object
     */
    public Object getSelectedAlbumObject() {
        return selectedAlbumObject;
    }

    /**
     * Stores the object
     *
     * @param object Object data
     */
    public void setSelectedAlbumObject(Object object) {
        this.selectedAlbumObject = object;
    }
}
