package com.mobile.android.unique.gallery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.mobile.android.unique.gallery.R;
import com.mobile.android.unique.gallery.adapters.AlbumAdapter;
import com.mobile.android.unique.gallery.application.ApplicationLevel;
import com.mobile.android.unique.gallery.component.PhoneMediaControl;
import com.mobile.android.unique.gallery.component.PhoneMediaVideoController;
import com.mobile.android.unique.gallery.extras.Data;
import com.mobile.android.unique.gallery.extras.Media;
import com.mobile.android.unique.gallery.models.PhotoAlbumEntry;
import com.mobile.android.unique.gallery.models.VideoAlbumEntry;
import com.mobile.android.unique.gallery.utils.StorageManager;
import com.mobile.android.unique.gallery.views.Header;

import java.util.ArrayList;

public class AlbumActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private final int REQUEST_CODE_PHOTOS = 100;
    // protected ArrayList<PhoneMediaControl.AlbumEntry> albumsSorted = new ArrayList<>();
    private GridView mView;
    // private PhotosAlbumAdapter photosAlbumAdapter = null;
    private int itemWidth = 100;
    private Media mediaType = Media.PHOTO;

    //video
    // private ArrayList<PhoneMediaVideoController.AlbumEntry> arrayVideoDetails = new ArrayList<>();
    private ArrayList<Object> objects = new ArrayList<>();
    private AlbumAdapter albumAdapter = null;

    //private VideoAlbumAdapter videoAlbumAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        mView = (GridView) findViewById(R.id.grid_view);
        mView.setOnItemClickListener(this);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        // setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        header = (Header) findViewById(R.id.header);
        header.initHeader();
        header.backIB.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mediaType = (Media) bundle.getSerializable(Data.EXTRA_TYPE);
        }
        String title = "";
        switch (mediaType) {
            case PHOTO:
                //  loadPhotos();
                title = getString(R.string.photos);
                loadMedia();
                loadPhotosAlbum();
                break;
            case VIDEO:
                //loadVideos();
                title = getString(R.string.videos);
                loadMedia();
                loadVideoAlbum();
                break;
            case BOTH:
                title = getString(R.string.gallery);
                loadMedia();
                loadPhotosAlbum();
                loadVideoAlbum();
                break;
            default:
                break;
        }
        //   getSupportActionBar().setTitle(title);
        header.titleTV.setText(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                StorageManager.getInstance().clearData();
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /* private void loadPhotos() {
         photosAlbumAdapter = new PhotosAlbumAdapter(this, albumsSorted);
         mView.setAdapter(photosAlbumAdapter);

         int position = mView.getFirstVisiblePosition();
         int columnsCount = 2;
         mView.setNumColumns(columnsCount);
         itemWidth = (ApplicationOwnGallery.displaySize.x - ((columnsCount + 1) * ApplicationOwnGallery.dp(4))) / columnsCount;
         mView.setColumnWidth(itemWidth);

         photosAlbumAdapter.notifyDataSetChanged();
         mView.setSelection(position);
         mView.setOnItemClickListener(this);
         loadPhotosAlbum();
     }

     private void loadVideos() {
         int position = mView.getFirstVisiblePosition();
         int columnsCount = 3;
         mView.setNumColumns(columnsCount);
         itemWidth = (ApplicationOwnGallery.displaySize.x - ((columnsCount + 1) * ApplicationOwnGallery.dp(4))) / columnsCount;
         mView.setColumnWidth(itemWidth);
         mView.setAdapter(videoAlbumAdapter = new VideoAlbumAdapter(this, arrayVideoDetails, itemWidth));

         videoAlbumAdapter.notifyDataSetChanged();
         mView.setSelection(position);
         loadVideoAlbum();
     }*/

    /**
     * Used to load the media items in a gridview
     */
    private void loadMedia() {
        int position = mView.getFirstVisiblePosition();
        int columnsCount = 2;
        mView.setNumColumns(columnsCount);
        itemWidth = (ApplicationLevel.displaySize.x - ((columnsCount + 1) * ApplicationLevel.dp(4))) / columnsCount;
        mView.setColumnWidth(itemWidth);
        mView.setAdapter(albumAdapter = new AlbumAdapter(this, objects, itemWidth));

        albumAdapter.notifyDataSetChanged();
        mView.setSelection(position);
        //loadVideoAlbum();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PHOTOS && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            super.onBackPressed();
        }
    }

    /**
     * Used to load the videos from the local paths
     */
    private void loadVideoAlbum() {

        PhoneMediaVideoController mPhoneMediaVideoController = new PhoneMediaVideoController();
        mPhoneMediaVideoController.setLoadallvideomediainterface(new PhoneMediaVideoController.LoadAllVideoMediaInterface() {
            @Override
            public void loadVideo(ArrayList<VideoAlbumEntry> videoAlbumList) {
                objects.addAll(videoAlbumList);
                if (albumAdapter != null) {
                    albumAdapter.notifyDataSetChanged();
                }
            }
        });
        mPhoneMediaVideoController.loadAllVideoMedia(this);
    }

    /**
     * Used to load the phots from the local paths
     */
    private void loadPhotosAlbum() {
        PhoneMediaControl mediaControl = new PhoneMediaControl();
        mediaControl.setLoadalbumphoto(new PhoneMediaControl.LoadAlbumPhoto() {
            @Override
            public void loadPhoto(ArrayList<PhotoAlbumEntry> albumsSorted_) {
                objects.addAll(albumsSorted_);
                //StorageManager.getInstance().setAlbumsSorted(albumsSorted);
                if (mView != null && mView.getEmptyView() == null) {
                    mView.setEmptyView(null);
                }
                if (albumAdapter != null) {
                    albumAdapter.notifyDataSetChanged();
                }
            }
        });
        mediaControl.loadGalleryPhotosAlbums(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView imageView = (ImageView) view.findViewById(R.id.media_photo_image);
        Object object = imageView.getTag();
        if (object instanceof PhotoAlbumEntry) {
            PhotoAlbumEntry albumEntry = (PhotoAlbumEntry) object;
            StorageManager.getInstance().setSelectedAlbumObject(albumEntry);
            Intent mIntent = new Intent(AlbumActivity.this, GalleryPhotosActivity.class);
            startActivityForResult(mIntent, REQUEST_CODE_PHOTOS);
        } else if (object instanceof VideoAlbumEntry) {
            VideoAlbumEntry albumEntry = (VideoAlbumEntry) object;
            StorageManager.getInstance().setSelectedAlbumObject(albumEntry);
            Intent mIntent = new Intent(AlbumActivity.this, GalleryPhotosActivity.class);
            startActivityForResult(mIntent, REQUEST_CODE_PHOTOS);
        }
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
