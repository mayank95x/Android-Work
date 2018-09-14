package codes.saurabh.cookmate.Adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import codes.saurabh.cookmate.R;
import codes.saurabh.cookmate.Util.LikeUtil;

/**
 * Created by Mayank on 3/26/2018.
 */

public class LikeAdapter extends ArrayAdapter<LikeUtil>
{
    private Context mContext;
    public LikeAdapter(Activity context, ArrayList<LikeUtil> list)
    {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;
        if(currentView == null)
        {
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_like_list, null, false);
        }

        LikeUtil likeDetails = getItem(position);

        ImageView image = currentView.findViewById(R.id.like_profile_image);
        TextView username = (TextView) currentView.findViewById(R.id.like_username);
        TextView name = (TextView) currentView.findViewById(R.id.like_name);


        username.setText(likeDetails.getUsername());
        name.setText(likeDetails.getName());

        return currentView;

    }
}
