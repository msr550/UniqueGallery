package com.mobile.android.unique.gallery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.android.unique.gallery.R;
import com.mobile.android.unique.gallery.component.VideoThumbleLoader;
import com.mobile.android.unique.gallery.models.PhotoAlbumEntry;
import com.mobile.android.unique.gallery.models.VideoAlbumEntry;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by MSR on 8/10/2016.
 */
public class AlbumAdapter extends BaseFragmentAdapter {
    private Context mContext;
    private VideoThumbleLoader thumbleLoader;
    private LayoutInflater inflater;
    private ArrayList<Object> arrayVideoDetails = null;
    private int itemWidth = 100;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();


    public AlbumAdapter(Context context, ArrayList<Object> arrayVideoDetails, int itemWidth) {
        this.mContext = context;
        this.thumbleLoader = new VideoThumbleLoader(mContext);
        this.arrayVideoDetails = arrayVideoDetails;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemWidth = itemWidth;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.nophotos)
                .showImageForEmptyUri(R.drawable.nophotos)
                .showImageOnFail(R.drawable.nophotos).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true).build();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public int getCount() {
        return arrayVideoDetails != null ? arrayVideoDetails.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mViewHolder;
        if (view == null) {
            mViewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.photo_picker_album_layout, viewGroup, false);
            mViewHolder.img = (ImageView) view.findViewById(R.id.media_photo_image);
            //mViewHolder.playIV = (ImageView) view.findViewById(R.id.playIV);
            mViewHolder.txtTitle = (TextView) view.findViewById(R.id.album_name);
            mViewHolder.txtCount = (TextView) view.findViewById(R.id.album_count);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = itemWidth;
            params.height = itemWidth;
            view.setLayoutParams(params);
            view.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) view.getTag();
        }
        Object object = arrayVideoDetails.get(i);
        // mViewHolder.playIV.setVisibility(View.GONE);
        if (object instanceof VideoAlbumEntry) {
            //mViewHolder.playIV.setVisibility(View.VISIBLE);
            VideoAlbumEntry videoAlbumEntry = (VideoAlbumEntry) object;
            thumbleLoader.DisplayImage("" + videoAlbumEntry.coverPhoto.imageId, mContext, mViewHolder.img, null);
            mViewHolder.txtTitle.setText(videoAlbumEntry.bucketName);
            mViewHolder.txtCount.setText(videoAlbumEntry.photos.size() + "");
            mViewHolder.img.setTag(videoAlbumEntry);
        } else if (object instanceof PhotoAlbumEntry) {
            PhotoAlbumEntry photoAlbumEntry = (PhotoAlbumEntry) object;
            // final ImageView imageView = (ImageView) view.findViewById(R.id.media_photo_image);
            if (photoAlbumEntry.coverPhoto != null && photoAlbumEntry.coverPhoto.path != null) {
                imageLoader.displayImage("file://" + photoAlbumEntry.coverPhoto.path, mViewHolder.img, options);
            } else {
                mViewHolder.img.setImageResource(R.drawable.nophotos);
            }
            mViewHolder.img.setTag(photoAlbumEntry);
            //  TextView textView = (TextView) view.findViewById(R.id.album_name);
            mViewHolder.txtTitle.setText(photoAlbumEntry.bucketName);
            //  textView = (TextView) view.findViewById(R.id.album_count);
            mViewHolder.txtCount.setText("" + photoAlbumEntry.photos.size());
        }

        return view;
    }

    private class ViewHolder {
        ImageView img;
        //  ImageView playIV;
        TextView txtTitle;
        TextView txtCount;
    }
}
