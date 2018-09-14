package codes.saurabh.cookmate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

//import codes.saurabh.followers.followersFrag.OnListFragmentInteractionListener;
import codes.saurabh.cookmate.followersFrag.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link follower} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FollowersRecyclerViewAdapter extends RecyclerView.Adapter<FollowersRecyclerViewAdapter.ViewHolder> {

    private  List<follower> followers_list = new ArrayList<>();
    private Context mContext;

    public FollowersRecyclerViewAdapter(List<follower> items, Context mContext) {
        followers_list = items;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.followers_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        follower follower = followers_list.get(position);
        holder.username.setText(follower.getUsername());
        holder.name.setText(follower.getName());
        Glide.with(mContext).load(follower.getPicID()).into(holder.profile_pic);
        Toast.makeText(mContext, "coming soon ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return followers_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        public ImageView profile_pic;
        public TextView username;
        public TextView name;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            username = (TextView) view.findViewById(R.id.username);
            name = (TextView) view.findViewById(R.id.name);
            profile_pic = (ImageView) view.findViewById(R.id.profile_pic);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + name.getText() + "'";
        }
    }
}
