package com.gallery.mobile.uniquegallery;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.mobile.android.unique.gallery.activities.AlbumActivity;
import com.mobile.android.unique.gallery.extras.Data;
import com.mobile.android.unique.gallery.extras.Media;
import com.mobile.android.unique.gallery.models.PhotoEntry;
import com.mobile.android.unique.gallery.models.VideoEntry;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final int REQUEST_CODE = 102;
    private ImageView selectedIV;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedIV = (ImageView) findViewById(R.id.selectedIV);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.nophotos)
                .showImageForEmptyUri(R.drawable.nophotos)
                .showImageOnFail(R.drawable.nophotos).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true).build();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        videoView = null;
        switch (id) {
            case R.id.photosBtn:
                intent = new Intent(this, AlbumActivity.class);
                intent.putExtra(Data.EXTRA_TYPE, Media.PHOTO);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.videosBtn:
                intent = new Intent(this, AlbumActivity.class);
                intent.putExtra(Data.EXTRA_TYPE, Media.VIDEO);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.galleryBtn:
                intent = new Intent(this, AlbumActivity.class);
                intent.putExtra(Data.EXTRA_TYPE, Media.BOTH);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null && !videoView.isPlaying()) {
            videoView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null && videoView.isPlaying()) {
            videoView.pause();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                videoView = (VideoView) findViewById(R.id.videoView);
                Object object = data.getSerializableExtra(getString(R.string.bundle_path));
                if (object instanceof PhotoEntry) {
                    selectedIV.setVisibility(View.VISIBLE);
                    videoView.setVisibility(View.GONE);
                    ImageLoader.getInstance().displayImage("file://" + ((PhotoEntry) object).path, selectedIV, options);
                } else if (object instanceof VideoEntry) {
                    selectedIV.setVisibility(View.GONE);
                    videoView.setVisibility(View.VISIBLE);
                    MediaController mediaController = new MediaController(this);
                    mediaController.setAnchorView(videoView);
                    mediaController.setMediaPlayer(videoView);
                    Uri uri = Uri.parse(((VideoEntry) object).path);
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(uri);
                    videoView.requestFocus();
                    videoView.start();
                }
            }
        }
    }
}