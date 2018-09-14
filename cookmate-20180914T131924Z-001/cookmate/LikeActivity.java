package codes.saurabh.cookmate;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import codes.saurabh.cookmate.Adapter.LikeAdapter;
import codes.saurabh.cookmate.Util.LikeUtil;

public class LikeActivity extends AppCompatActivity {

    private ArrayList<LikeUtil> likeList;
    private FirebaseFirestore mFirestoreDatabase;
    private DocumentReference mLikeCollection;

    private String TAG = LikeActivity.class.getSimpleName();

    private LikeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.commentToolbar);
        myToolbar.setTitle("Likes");

        setSupportActionBar(myToolbar);

        String username = getIntent().getStringExtra("username");
        String postId = getIntent().getStringExtra("postId");

        mFirestoreDatabase = FirebaseFirestore.getInstance();
        mLikeCollection = mFirestoreDatabase.document("users/" + username + "/posts/"+ postId +"/likes/" + postId);

        likeList = new ArrayList<>();

        ListView listView = (ListView) findViewById(R.id.likeListview);
        adapter = new LikeAdapter(this, likeList);
        listView.setAdapter(adapter);

        getLikeList();
    }

    private void getLikeList()
    {

        mLikeCollection.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
                            if(name != null) {
                                LikeUtil like = new LikeUtil(name, username);
                                likeList.add(like);
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
}
