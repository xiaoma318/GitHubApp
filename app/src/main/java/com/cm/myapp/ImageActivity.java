package com.cm.myapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageActivity extends AppCompatActivity {
    String[] urls = {"https://s7d1.scene7.com/is/image/PETCO/dog-category-090617-369w-269h-hero-cutout-d?fmt=png-alpha",
            "https://ichef.bbci.co.uk/news/660/cpsprodpb/475B/production/_98776281_gettyimages-521697453.jpg",
            "http://cdn3-www.dogtime.com/assets/uploads/2011/01/file_23262_entlebucher-mountain-dog-300x189.jpg",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        GridView gv = findViewById(R.id.gvImage);

        ImageAdapter adapter = new ImageAdapter(this, urls);
        gv.setAdapter(adapter);
    }

    private class ImageAdapter extends BaseAdapter {
        String[] mItems;
        Context mContext;
        ImageLoader mImageLoader;

        public ImageAdapter(Context context, String[] items) {
            mItems = items;
            mContext = context;
            mImageLoader = ImageLoader.getInstance();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext).build();
            mImageLoader.init(config);
        }

        @Override
        public int getCount() {
            return mItems.length;
        }

        @Override
        public String getItem(int i) {
            return mItems[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(R.layout.grid_item, viewGroup, false);
            }
            ImageView imageView = view.findViewById(R.id.image);
            mImageLoader.displayImage(mItems[i], imageView);
            // Picasso.with(mContext).load(mItems[i]).into(imageView);
            return view;
        }
    }
}
