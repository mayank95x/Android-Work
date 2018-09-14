package codes.saurabh.cookmate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codes.saurabh.cookmate.Util.PostUtil;
import codes.saurabh.cookmate.data.FirestoreConstant;

/**
 * Created by Saurabh Kumar on 09-03-2018.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private Context mContext;
    private List<PostUtil> cardList;
    private String currentUsername;
    private String currentName;






    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        //Bookmark button implemented using Opensource code available at: https://github.com/jd-alexander/LikeButton
        public LikeButton bookmark;
        public ImageView contentImage;
        public TextView caption;
        //Like button implemented using Opensource code available at: https://github.com/jd-alexander/LikeButton
        public LikeButton like;
        public ImageButton comment;
        public ImageButton share;
        public TextView likeListButton;
        public RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.tab_1_username);
            bookmark = itemView.findViewById(R.id.tab1_bookmark);
            contentImage = itemView.findViewById(R.id.tab1_content);
            caption = itemView.findViewById(R.id.tab1_caption);
            like = itemView.findViewById(R.id.tab1_like);
            comment = itemView.findViewById(R.id.tab1_comment);
            share = itemView.findViewById(R.id.tab1_share);
            //likeListButton = itemView.findViewById(R.id.likeActivityList);
            relativeLayout = itemView.findViewById(R.id.relative_card);
        }
    }

    public CardAdapter(Context mContext, List<PostUtil> cardList, String userName, String name) {
        this.mContext = mContext;
        this.cardList = cardList;
        this.currentName = name;
        this.currentUsername = userName;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final PostUtil card = cardList.get(position);
        holder.username.setText("@avengers");
        holder.caption.setText(card.getCaption());
        //Glide.with(mContext).load(card.getmBookmarkId()).into(holder.bookmark);
    if(card.getPhotoUrlList().size() > 0) {
        Glide.with(mContext)
                .load("" + card.getPhotoUrlList().get(0))
                .into(holder.contentImage);
        holder.caption.setText(card.getCaption());
    }
        //Glide.with(mContext).load(card.getmLikeId()).into(holder.like);
        //Glide.with(mContext).load(card.getmCommentId()).into(holder.comment);
        //Glide.with(mContext).load(card.getmShareId()).into(holder.share);

        //Bookmark button implemented using Opensource code available at: https://github.com/jd-alexander/LikeButton
        holder.relativeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ViewPostActivity.class);
                intent.putExtra("recipe", card.getRecipe());
                intent.putExtra("ingredient", card.getIngredient());
                intent.putExtra("category", card.getCategory());
                intent.putExtra("isSelected", card.getIsVeg()+"");
                intent.putExtra("image", card.getPhotoUrlList().get(0)+"");
                intent.putExtra("caption", card.getCaption());

                mContext.startActivity(intent);

            }
        });


        holder.bookmark.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(mContext, "Bookmarked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(mContext, "Un-Bookmarked", Toast.LENGTH_SHORT).show();
            }
        });
        //Like button implemented using Opensource code available at: https://github.com/jd-alexander/LikeButton
/*
        holder.likeListButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LikeActivity.class);
                intent.putExtra("username", card.getUsername());
                intent.putExtra("postId", card.getPostId());
                mContext.startActivity(intent);
            }
        });
*/

        holder.like.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(mContext, "Liked", Toast.LENGTH_SHORT).show();
                FirebaseFirestore mFirebaseDatabase = FirebaseFirestore.getInstance();
                DocumentReference likeRef = mFirebaseDatabase.document("users/"+card.getUsername()+"/posts/" + card.getPostId()+
                                "/likes/" + card.getPostId());

                likeRef.update(currentUsername, currentName)
                        .addOnSuccessListener(new OnSuccessListener < Void > () {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext, "Updated Successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                DocumentReference postDoc = mFirebaseDatabase.document("users/"+card.getUsername()+"/posts/" + card.getPostId());
                postDoc.update(FirestoreConstant.likeCount, card.getLikeCount()+1)
                        .addOnSuccessListener(new OnSuccessListener < Void > () {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext, "Updated like Successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(mContext, "Unliked", Toast.LENGTH_SHORT).show();

                FirebaseFirestore mFirebaseDatabase = FirebaseFirestore.getInstance();
                DocumentReference likeRef = mFirebaseDatabase.document("users/"+card.getUsername()+"/posts/" + card.getPostId()+
                        "/likes/" + card.getPostId());

                likeRef.update(currentUsername, null)
                        .addOnSuccessListener(new OnSuccessListener < Void > () {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext, "Data deleted !",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                DocumentReference postDoc = mFirebaseDatabase.document("users/"+card.getUsername()+"/posts/" + card.getPostId());
                postDoc.update(FirestoreConstant.likeCount, card.getLikeCount()-1)
                        .addOnSuccessListener(new OnSuccessListener < Void > () {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext, "Updated like Successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "Comming Soon!", Toast.LENGTH_SHORT).show();
                Intent commentIntent = new Intent(mContext, CommentActivity.class);
                commentIntent.putExtra("username", card.getUsername());
                commentIntent.putExtra("postId", card.getPostId());
                mContext.startActivity(commentIntent);

            }
        });

        final String captionWhatsapp = card.getCaption();
        final String imageUrl = card.getPhotoUrlList().get(0) + "";

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "Comming Soon!", Toast.LENGTH_SHORT).show();
                //createPopUpBox(holder, card);
                View dialogView = LayoutInflater.from(mContext).inflate(R.layout.layout_share, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setView(dialogView)
                        .create()
                        .show();
                ImageView whatsappImage = (ImageView) dialogView.findViewById(R.id.whatsappIntent);
                ImageView gmailImage = (ImageView) dialogView.findViewById(R.id.gmailIntent);

                whatsappImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.i(mContext+"", "clicked -- whatsapp");
                        Intent shareIntent = new Intent();
                        shareIntent.setType("text/plain");
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, captionWhatsapp + "\n" +imageUrl);
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, captionWhatsapp);
                        // Target whatsapp:
                        shareIntent.setPackage("com.whatsapp");

                        try {
                            Log.i(mContext+"", "clicked -- whatsappdsvfdsv");
                            mContext.startActivity(shareIntent);
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(mContext, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                gmailImage.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setType("text/plain");
                            intent.setData(Uri.parse("mailto:"));
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Check this dish from cookmate");
                            intent.putExtra(Intent.EXTRA_TEXT, captionWhatsapp + "\n\n" + imageUrl);
                            if(intent.resolveActivity(mContext.getPackageManager()) != null) {
                                mContext.startActivity(intent);
                            }
                    }
                });

            }
        });

        holder.contentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ViewPostActivity.class);
                intent.putExtra("recipe", card.getRecipe());
                intent.putExtra("ingredient", card.getIngredient());
                intent.putExtra("category", card.getCategory());
                intent.putExtra("isSelected", card.getIsVeg()+"");
                intent.putExtra("image", card.getPhotoUrlList().get(0)+"");
                intent.putExtra("caption", card.getCaption());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    private void createPopUpBox(MyViewHolder holder, PostUtil card)
    {


    }
}
