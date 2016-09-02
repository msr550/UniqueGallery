package com.mobile.android.unique.gallery.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobile.android.unique.gallery.R;


/**
 * Created by Sandeep Maram on 7/5/15.
 */
public class Header extends RelativeLayout {

    public ImageView backIB;
    public TextView titleTV;

    public Header(Context context) {
        super(context);
    }

    public Header(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Header(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Used to initialise the header
     */
    public void initHeader() {
        headerLayout();
    }

    /**
     * By Using this method we are inflating the Header layout
     */
    public void headerLayout() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.header_layout, this);
        backIB = (ImageView) findViewById(R.id.backIB);
        titleTV = (TextView) findViewById(R.id.title_tv);
    }
}
