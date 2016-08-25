package com.mobile.android.unique.gallery.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.mobile.android.unique.gallery.R;
import com.mobile.android.unique.gallery.activities.GalleryPhotosActivity;
import com.mobile.android.unique.gallery.adapters.AlbumAdapter;
import com.mobile.android.unique.gallery.application.ApplicationLevel;
import com.mobile.android.unique.gallery.component.PhoneMediaControl;
import com.mobile.android.unique.gallery.component.PhoneMediaVideoController;
import com.mobile.android.unique.gallery.extras.Media;
import com.mobile.android.unique.gallery.models.PhotoAlbumEntry;
import com.mobile.android.unique.gallery.models.VideoAlbumEntry;
import com.mobile.android.unique.gallery.utils.Logger;
import com.mobile.android.unique.gallery.utils.StorageManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    private final int REQUEST_CODE_PHOTOS = 100;

    private GridView mView;
    // private PhotosAlbumAdapter photosAlbumAdapter = null;
    private int itemWidth = 100;
    private Media mediaType = Media.PHOTO;

    //video
    // private ArrayList<PhoneMediaVideoController.AlbumEntry> arrayVideoDetails = new ArrayList<>();
    private ArrayList<Object> objects = new ArrayList<>();
    private AlbumAdapter albumAdapter = null;
    private Activity activity = null;

    public AlbumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param media Parameter 2.
     * @return A new instance of fragment AlbumFragment.
     */
    public static AlbumFragment newInstance(Media media) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, media);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        if (getArguments() != null) {
            mediaType = (Media) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        mView = (GridView) view.findViewById(R.id.grid_view);
        Logger.getInfo("mediaTYpe::" + mediaType);
        mView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        switch (mediaType) {
            case PHOTO:
                loadMedia();
                loadPhotosAlbum();
                break;
            case VIDEO:
                loadMedia();
                loadVideoAlbum();
                break;
            default:
                break;
        }
    }

    /**
     * Used to load the media items in a gridview
     */
    private void loadMedia() {
        int position = mView.getFirstVisiblePosition();
        int columnsCount = 2;
        mView.setNumColumns(columnsCount);
        itemWidth = (ApplicationLevel.displaySize.x - ((columnsCount + 1) * ApplicationLevel.dp(4))) / columnsCount;
        mView.setColumnWidth(itemWidth);
        mView.setAdapter(albumAdapter = new AlbumAdapter(activity, objects, itemWidth));

        albumAdapter.notifyDataSetChanged();
        mView.setSelection(position);
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
        mPhoneMediaVideoController.loadAllVideoMedia(activity);
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
                if (mView != null && mView.getEmptyView() == null) {
                    mView.setEmptyView(null);
                }
                if (albumAdapter != null) {
                    albumAdapter.notifyDataSetChanged();
                }
            }
        });
        mediaControl.loadGalleryPhotosAlbums(activity);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView imageView = (ImageView) view.findViewById(R.id.media_photo_image);
        Object object = imageView.getTag();
        if (object instanceof PhotoAlbumEntry) {
            PhotoAlbumEntry albumEntry = (PhotoAlbumEntry) object;
            StorageManager.getInstance().setSelectedAlbumObject(albumEntry);
            Intent mIntent = new Intent(activity, GalleryPhotosActivity.class);
            startActivityForResult(mIntent, REQUEST_CODE_PHOTOS);
        } else if (object instanceof VideoAlbumEntry) {
            VideoAlbumEntry albumEntry = (VideoAlbumEntry) object;
            StorageManager.getInstance().setSelectedAlbumObject(albumEntry);
            Intent mIntent = new Intent(activity, GalleryPhotosActivity.class);
            startActivityForResult(mIntent, REQUEST_CODE_PHOTOS);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PHOTOS && resultCode == Activity.RESULT_OK) {
            activity.setResult(Activity.RESULT_OK, data);
            activity.onBackPressed();
        }
    }
}
