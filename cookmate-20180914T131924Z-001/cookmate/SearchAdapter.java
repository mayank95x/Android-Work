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

import java.util.List;

/**
 * Created by Saurabh Kumar on 09-03-2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context mContext;
    private List<s_card> searchList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView contentImage;
        public TextView caption;

        public MyViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.name_text);
            contentImage = itemView.findViewById(R.id.profile_image);
            caption = itemView.findViewById(R.id.status_text);

        }
    }

    public SearchAdapter(Context mContext, List<s_card> searchList) {
        this.mContext = mContext;
        this.searchList = searchList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        s_card card = searchList.get(position);
        holder.username.setText(card.getmUsernameString());
        holder.caption.setText(card.getmuser_handle());

        //Glide.with(mContext).load(card.getmBookmarkId()).into(holder.bookmark);
        Glide.with(mContext).load(card.getMcontentImageId()).into(holder.contentImage);
        //holder.caption.setText(card.getmCaptionString());
        //Glide.with(mContext).load(card.getmLikeId()).into(holder.like);
        //Glide.with(mContext).load(card.getmCommentId()).into(holder.comment);
        //Glide.with(mContext).load(card.getmShareId()).into(holder.share);

        holder.contentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Comming Soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }
}
