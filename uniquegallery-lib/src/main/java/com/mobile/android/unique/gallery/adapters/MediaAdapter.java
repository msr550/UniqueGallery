package com.mobile.android.unique.gallery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mobile.android.unique.gallery.R;
import com.mobile.android.unique.gallery.component.VideoThumbleLoader;
import com.mobile.android.unique.gallery.models.PhotoEntry;
import com.mobile.android.unique.gallery.models.VideoEntry;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by MSR on 8/10/2016.
 */
public class MediaAdapter extends BaseFragmentAdapter {
    private ArrayList<Object> photos = new ArrayList<Object>();
    private int itemWidth = 100;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private VideoThumbleLoader thumbleLoader;

    /**
     * Parameterized constructor
     *
     * @param context   Activity Context
     * @param photos    List of photo paths
     * @param itemWidth item width
     */
    public MediaAdapter(Context context, ArrayList<Object> photos, int itemWidth) {
        this.mContext = context;
        this.layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.thumbleLoader = new VideoThumbleLoader(mContext);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.nophotos)
                .showImageForEmptyUri(R.drawable.nophotos)
                .showImageOnFail(R.drawable.nophotos).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true).build();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        this.photos = photos;
        this.itemWidth = itemWidth;
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
        return photos != null ? photos.size() : 0;
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
        viewHolder mHolder;
        if (view == null) {
            mHolder = new viewHolder();
            view = layoutInflater.inflate(R.layout.album_image, viewGroup, false);
            mHolder.imageView = (ImageView) view.findViewById(R.id.album_image);
            mHolder.playIV = (ImageView) view.findViewById(R.id.playIV);
            mHolder.imageView.setTag(i);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = itemWidth;
            params.height = itemWidth;
            view.setLayoutParams(params);
            view.setTag(mHolder);
        } else {
            mHolder = (viewHolder) view.getTag();
        }
        Object object = photos.get(i);
        mHolder.playIV.setVisibility(View.GONE);
        if (object instanceof PhotoEntry) {
            PhotoEntry mPhotoEntry = (PhotoEntry) object;
            String path = mPhotoEntry.path;
            mHolder.imageView.setTag(mPhotoEntry);
            if (path != null && !path.equals("")) {
                ImageLoader.getInstance().displayImage("file://" + path, mHolder.imageView, options);
            }
        } else if (object instanceof VideoEntry) {
            mHolder.playIV.setVisibility(View.VISIBLE);
            VideoEntry albumEnrty = (VideoEntry) object;
            thumbleLoader.DisplayImage("" + albumEnrty.videoId, mContext, mHolder.imageView, null);
            mHolder.imageView.setTag(albumEnrty);
        }
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    class viewHolder {
        public ImageView imageView, playIV;
    }

}
