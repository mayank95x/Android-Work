package codes.saurabh.cookmate;

import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codes.saurabh.cookmate.Util.PostUtil;


public class HomeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private RecyclerView mHomeRecyclerView;
    private CardAdapter mCardAdapter;
    private List<a_card> mCardList;

    private String TAG = getActivity() + "";

    private String mUserName = "@pk";

    private FirebaseFirestore mFirestoreDatabase;
    private CollectionReference mUserCollection;

    private String currentUsername = "@chutiya";
    private String currentName = "Chutiya";
    private int postCounter= 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View homeFragView = inflater.inflate(R.layout.fragment_home, container, false);


        mFirestoreDatabase = FirebaseFirestore.getInstance();
        mUserCollection = mFirestoreDatabase.collection("users");
        getAllUsers();

        DocumentReference countPostref = mFirestoreDatabase.document("Counter/postCount");
        countPostref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        postCounter = Integer.parseInt((Long) document.get("count") + "");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });




        mHomeRecyclerView = (RecyclerView) homeFragView.findViewById(R.id.recycler_view_home_frag);
        mCardList = new ArrayList<>();
        postNameList = new ArrayList<>();
        mCardAdapter = new CardAdapter(getActivity(), postNameList, currentUsername, currentName);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mHomeRecyclerView.setLayoutManager(mLayoutManager);
        mHomeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHomeRecyclerView.setAdapter(mCardAdapter);
        prepareCards();
        return homeFragView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void prepareCards(){

        for(int i = 0; i< postNameList.size(); i++)
        {
            Log.i(TAG, "size     ============= " + postNameList.get(i).getPhotoUrlList().size());
        }

        mCardAdapter.notifyDataSetChanged();

/*
        int[] contentImages = new int[]{
            R.drawable.food_image_1,
            R.drawable.food_image_2,
            R.drawable.food_image_3,
            R.drawable.food_image_4,
            R.drawable.food_image_5,
            R.drawable.food_image_6,
            R.drawable.food_image_7,
            R.drawable.food_image_8,
            R.drawable.food_image_9,
            R.drawable.food_image_10
        };

        a_card c;
        c = new a_card(
                getResources().getString(R.string.username1),
                R.drawable.bookmark_1_icon,
                contentImages[0],
                getResources().getString(R.string.caption1),
                R.drawable.like_icon,
                R.drawable.comment_icon,
                R.drawable.share_icon);
        mCardList.add(c);

        c = new a_card(
                getResources().getString(R.string.username2),
                R.drawable.bookmark_1_icon,
                contentImages[1],
                getResources().getString(R.string.caption2),
                R.drawable.like_icon,
                R.drawable.comment_icon,
                R.drawable.share_icon);
        mCardList.add(c);

        c = new a_card(
                getResources().getString(R.string.username3),
                R.drawable.bookmark_1_icon,
                contentImages[2],
                getResources().getString(R.string.caption3),
                R.drawable.like_icon,
                R.drawable.comment_icon,
                R.drawable.share_icon);
        mCardList.add(c);

        c = new a_card(
                getResources().getString(R.string.username4),
                R.drawable.bookmark_1_icon,
                contentImages[3],
                getResources().getString(R.string.caption4),
                R.drawable.like_icon,
                R.drawable.comment_icon,
                R.drawable.share_icon);
        mCardList.add(c);

        c = new a_card(
                getResources().getString(R.string.username5),
                R.drawable.bookmark_1_icon,
                contentImages[4],
                getResources().getString(R.string.caption5),
                R.drawable.like_icon,
                R.drawable.comment_icon,
                R.drawable.share_icon);
        mCardList.add(c);

        c = new a_card(
                getResources().getString(R.string.username6),
                R.drawable.bookmark_1_icon,
                contentImages[5],
                getResources().getString(R.string.caption6),
                R.drawable.like_icon,
                R.drawable.comment_icon,
                R.drawable.share_icon);
        mCardList.add(c);

        c = new a_card(
                getResources().getString(R.string.username7),
                R.drawable.bookmark_1_icon,
                contentImages[6],
                getResources().getString(R.string.caption7),
                R.drawable.like_icon,
                R.drawable.comment_icon,
                R.drawable.share_icon);
        mCardList.add(c);

        c = new a_card(
                getResources().getString(R.string.username8),
                R.drawable.bookmark_1_icon,
                contentImages[7],
                getResources().getString(R.string.caption8),
                R.drawable.like_icon,
                R.drawable.comment_icon,
                R.drawable.share_icon);
        mCardList.add(c);

        c = new a_card(
                getResources().getString(R.string.username9),
                R.drawable.bookmark_1_icon,
                contentImages[8],
                getResources().getString(R.string.caption9),
                R.drawable.like_icon,
                R.drawable.comment_icon,
                R.drawable.share_icon);
        mCardList.add(c);

        c = new a_card(
                getResources().getString(R.string.username10),
                R.drawable.bookmark_1_icon,
                contentImages[9],
                getResources().getString(R.string.caption10),
                R.drawable.like_icon,
                R.drawable.comment_icon,
                R.drawable.share_icon);
        mCardList.add(c);
*/
        mCardAdapter.notifyDataSetChanged();
    }

    private ArrayList<String> userNameList;
    private void getAllUsers()
    {
        userNameList = new ArrayList<>();

        mUserCollection
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            int sizeOfTask = task.getResult().size();

                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                userNameList.add(document.getId());
                            }
                            if(userNameList.size() == sizeOfTask )
                            {
                                getPostUsers();
                            }
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private ArrayList<PostUtil> postNameList;
    private CollectionReference postCollection;
    private int flag = 0;

    private void getPostUsers()
    {

        for(int i = 0 ; i < userNameList.size(); i++)
        {
            String userName = userNameList.get(i);
            postCollection = mFirestoreDatabase.collection("users/" + userName + "/posts");
            postCollection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                PostUtil postOfUserFetch = new PostUtil( document.getId(),(String) document.get("caption"),(String) document.get("category"),(long) document.get("commentCount"),
                                        (String) document.get("ingredient"),(long) document.get("isVeg"),(long) document.get("likeCount"),(String) document.get("postDate"),
                                        (String) document.get("recipe"),(String) document.get("username"));

                                 postNameList.add(postOfUserFetch);
                                 Log.i(TAG, "postnamelist size = " + postNameList.size() +"  ---  flag == "+ flag);
                                 if(postNameList.size() > 3)
                                 {
                                     while(postCounter == 0)
                                     {

                                     }
                                 }
                                 if(postNameList.size() == postCounter && flag == 0)
                                 {
                                     flag = 1;
                                     getPostPhotos();
                                 }
                            }
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        }
    }

    private int counter = 0;
    private void getPostPhotos()
    {
        for(int i = 0 ; i < postNameList.size(); i++)
        {
            PostUtil currentPost = postNameList.get(i);
            Log.i("eeeeeeeeeeeeeeeee", currentPost.getPostId());
            DocumentReference mPhotosRef = mFirestoreDatabase.document("users/" + currentPost.getUsername() +
                    "/posts/" + currentPost.getPostId() + "/photos/" + currentPost.getPostId());
            Log.i(TAG, "username =  " + currentPost.getUsername() + "---  user id = " + currentPost.getPostId());
            mPhotosRef
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful())
                            {
                                DocumentSnapshot document = task.getResult();
                                if (document != null && document.exists())
                                {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    for(int j = 0 ; j < postNameList.size(); j++)
                                    {
                                        Log.i(TAG, " jrrrr ---- " + j);
                                        if(postNameList.get(j).getPostId().equals(document.getId()))
                                        {
                                            Log.i(TAG, " j tttttttt---- " + j);
                                            for(String str : document.getData().keySet())
                                            {
                                                try {
                                                    Log.i(TAG, " j ---- " + j);
                                                    postNameList.get(j).setPhotoList(new URL(str));
                                                } catch (MalformedURLException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                                else
                                {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                            counter++;
                            Log.i(TAG, "counter = " + counter + "         postNameList = " + postNameList.size());
                            if(counter == postNameList.size())
                            {
                                Log.i(TAG, "FUCK    " + counter);
                                prepareCards();
                            }
                        }
                    });


        }
        Log.i(TAG, "rrrrrrrrrr    " + counter);
    }


}
