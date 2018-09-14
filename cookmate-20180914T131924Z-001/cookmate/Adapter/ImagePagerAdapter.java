package codes.saurabh.cookmate.Adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import codes.saurabh.cookmate.R;

/**
 * Created by Mayank on 3/7/2018.
 */

public class ImagePagerAdapter extends PagerAdapter
{
    private Context mContext;
    private ArrayList<Uri> imageList;

    private float scale;
    private float sizeInDp = 5;

    public ImagePagerAdapter(Context context, ArrayList<Uri> imageResourceList) {
        this.mContext = context;
        this.imageList = imageResourceList;
        scale = mContext.getResources().getDisplayMetrics().density;
    }


    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == ((ImageView) object));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER);


        int dpAsPixels = (int) (sizeInDp * scale + 0.5f);
        imageView.setPadding(dpAsPixels, dpAsPixels, dpAsPixels,  dpAsPixels);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            imageView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_screen_border));
        }
        else
        {
            imageView.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.add_screen_border));
        }

        Glide.with(mContext).load(imageList.get(position)).into(imageView);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return imageList.indexOf(object);
    }
}
