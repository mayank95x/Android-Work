package codes.saurabh.cookmate;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import codes.saurabh.cookmate.Adapter.CommentAdapter;
import codes.saurabh.cookmate.Credentials.UserCredentials;
import codes.saurabh.cookmate.Util.CommentUtil;
import codes.saurabh.cookmate.Util.LikeUtil;

public class CommentActivity extends AppCompatActivity {

    private CommentAdapter adapter;
    private ArrayList<CommentUtil> commentList;
    private String username;
    private String postId;
    private EditText commentEditText;
    private DocumentReference commentRef;
    private UserCredentials credentials ;


    private FirebaseFirestore firebase;
    private DocumentReference postCountRef;

    private String TAG = CommentActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        credentials = new UserCredentials();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.commentToolbar);
        myToolbar.setTitle("Comments");

        setSupportActionBar(myToolbar);

        username = getIntent().getStringExtra("username");
        postId = getIntent().getStringExtra("postId");

        firebase = FirebaseFirestore.getInstance();
        commentRef = firebase.document("users/" + username + "/posts/" + postId + "/comments/" + postId );
        postCountRef = firebase.document("users/" + username + "/posts/" + postId);



        commentList = new ArrayList<>();

        ListView listView = (ListView) findViewById(R.id.commentListView);
        adapter = new CommentAdapter(this, commentList);
        listView.setAdapter(adapter);

        getComment();

        commentEditText = (EditText) findViewById(R.id.commentEditText);
        ImageView sendButton = (ImageView) findViewById(R.id.commentPost);
        Log.i(TAG, "username =   ===== " + username + " postID =  -------- " + postId);

        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Fuckkk3333333333333333333");
                String comment = commentEditText.getText().toString();
                commentEditText.setText("");
                Log.i(TAG, credentials.getUsername() +"    fnvjf     ---------- " + credentials.getName());
                commentRef.update(credentials.getUsername(), credentials.getName() + "/" + comment)
                        .addOnSuccessListener(new OnSuccessListener< Void >() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CommentActivity.this, "Comment added successfully", Toast.LENGTH_SHORT).show();
                                commentList.clear();
                                getComment();
                                updateCommentCount();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i(TAG, e + "");
                            }
                        });
            }
        });
    }

    private void getComment()
    {

        commentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        for(String str: document.getData().keySet())
                        {
                            String username = str;
                            String name = (String) document.get(username);
                            if(!name.equals("null")) {
                                String[] temp = name.split("/");
                                name = temp[0];
                                String comment = temp[1];

                                CommentUtil postComment = new CommentUtil(username, name, comment);
                                commentList.add(postComment);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void updateCommentCount()
    {

            postCountRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            postCountRef.update("commentCount", (long) document.get("commentCount") + 1);
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });

    }
}
