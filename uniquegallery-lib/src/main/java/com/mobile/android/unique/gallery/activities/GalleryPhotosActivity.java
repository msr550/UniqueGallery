package com.mobile.android.unique.gallery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.mobile.android.unique.gallery.R;
import com.mobile.android.unique.gallery.adapters.MediaAdapter;
import com.mobile.android.unique.gallery.application.ApplicationLevel;
import com.mobile.android.unique.gallery.models.PhotoAlbumEntry;
import com.mobile.android.unique.gallery.models.PhotoEntry;
import com.mobile.android.unique.gallery.models.VideoAlbumEntry;
import com.mobile.android.unique.gallery.models.VideoEntry;
import com.mobile.android.unique.gallery.utils.StorageManager;

import java.util.ArrayList;

public class GalleryPhotosActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private Toolbar toolbar;
    private GridView mView;
    private boolean imageSelected = false;
    private ArrayList<Object> mediaList = new ArrayList<>();


    private int itemWidth = 100;
    private MediaAdapter photosAdapter;
    private PhotoAlbumEntry photoAlbumEntry = null;
    private VideoAlbumEntry videoAlbum = null;
    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_photos);
        initializeActionBar();
        initializeView();
    }

    /**
     * Initiate the actionBar
     */
    private void initializeActionBar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Used to intialte gridview and loading initializing objects
     */
    private void initializeView() {
        mView = (GridView) findViewById(R.id.grid_view);
        int position = mView.getFirstVisiblePosition();
        int columnsCount = 3;
        mView.setNumColumns(columnsCount);
        itemWidth = (ApplicationLevel.displaySize.x - ((columnsCount + 1) * ApplicationLevel.dp(4))) / columnsCount;
        mView.setColumnWidth(itemWidth);
        Object object = StorageManager.getInstance().getSelectedAlbumObject();
        if (object instanceof PhotoAlbumEntry) {
            photoAlbumEntry = (PhotoAlbumEntry) object;
            mediaList.addAll(photoAlbumEntry.photos);
            toolbar.setTitle(photoAlbumEntry.bucketName + " (" + photoAlbumEntry.photos.size() + ")");
        } else if (object instanceof VideoAlbumEntry) {
            videoAlbum = (VideoAlbumEntry) object;
            toolbar.setTitle(videoAlbum.bucketName + " (" + videoAlbum.photos.size() + ")");
            mediaList.addAll(videoAlbum.photos);
        }
        photosAdapter = new MediaAdapter(this, mediaList, itemWidth);
        mView.setAdapter(photosAdapter);
        photosAdapter.notifyDataSetChanged();
        mView.setSelection(position);
        mView.setOnItemClickListener(this);
        LoadAllAlbum();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Used to load photosAdapter object
     */
    private void LoadAllAlbum() {
        if (mView != null && mView.getEmptyView() == null) {
            mView.setEmptyView(null);
        }
        if (photosAdapter != null) {
            photosAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        int resultCode = RESULT_CANCELED;
        if (imageSelected) {
            resultCode = RESULT_OK;
        }
        setResult(resultCode, intent);
        super.onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView imageView = (ImageView) view.findViewById(R.id.album_image);
        Object object = imageView.getTag();
        if (object instanceof PhotoEntry) {
            PhotoEntry photoEntry = (PhotoEntry) object;
            intent = new Intent();
            intent.putExtra(getString(R.string.bundle_path), photoEntry);
            imageSelected = true;
            onBackPressed();
        } else if (object instanceof VideoEntry) {
            VideoEntry videoEntry = (VideoEntry) object;
            intent = new Intent();
            intent.putExtra(getString(R.string.bundle_path), videoEntry);
            // path = photoEntry.path;
            imageSelected = true;
            onBackPressed();
        }
    }
}
