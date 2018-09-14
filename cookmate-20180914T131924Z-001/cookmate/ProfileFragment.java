package codes.saurabh.cookmate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.*;
import android.util.*;
import android.content.*;

import java.util.Iterator;
import java.util.Map;

/*
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
*/

import com.google.firebase.firestore.*;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.FirebaseOptions.Builder;



import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import static codes.saurabh.cookmate.GoogleSignInActivity.profileUserName;

public class ProfileFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private TextView user_name_obj;
    private TextView mfollower_obj;
    private TextView mfollowing_obj;
    private ImageView profile_view;

    private static String profile_url;
    public String muser_handle;
    public static int mcount_followers;
    public static int mcount_following;
    private int flag=0;

    final Map<String,String> mp=new HashMap<String,String>();

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tablayout);
        /*--Creating a service account
        */
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();
        DocumentReference doc_ref = db.collection("users").document(profileUserName);
        doc_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            Log.d("ok", "document data" + document.getData());

                            Iterator it = document.getData().entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry) it.next();
                                if (pair.getKey().equals("no_of_followers")) {
                                    Log.d("ok", "yes its fine");
                                    //mcount_followers=(Integer)pair.getValue();
                                    mcount_followers=((Long)pair.getValue()).intValue();
                                    Log.d("val",mcount_followers+"");

                                }
                                if(pair.getKey().equals("profile_pic_url")){
                                    profile_url=(String)pair.getValue();

                                }
                                if(pair.getKey().equals("no_of_following")){
                                    Log.d("ok","this is fine too");
                                    mcount_following=((Long)pair.getValue()).intValue();
                                    Log.d("val2",mcount_following+"");
                                }
                            }


                        } else {
                            Log.d("no such doc", "no doc");
                        }
                    } else {
                        Log.d("error", "get failed with", task.getException());
                    }
                }
            });


        Log.d("val3",mcount_followers+"");
        System.out.println(mcount_followers);

        TextView user_name_obj=(TextView)view.findViewById(R.id.user_name);
        user_name_obj.setText(profileUserName);
        TextView mfollower_obj=(TextView)view.findViewById(R.id.followers_info);
        mfollower_obj.setText("Followers:"+String.valueOf(mcount_followers));
        TextView mfollowing_obj=(TextView)view.findViewById(R.id.following_info);
        mfollowing_obj.setText("Following:"+mcount_following+"");

        profile_view=(ImageView)view.findViewById(R.id.profile_pic);
        //profile_view.setImageResource(R.drawable.);

        //Glide.with(ProfileFragment.this).load(profile_url).into(profile_view);
        Glide.with(ProfileFragment.this)
                .load(profile_url)
                .apply(new RequestOptions()
                    .fitCenter()
                )
                .into(profile_view)
        ;

        tabLayout.addTab(tabLayout.newTab().setText("Posts"));
        tabLayout.addTab(tabLayout.newTab().setText("Saved"));
        tabLayout.addTab(tabLayout.newTab().setText("Activity"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mfollower_obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileFragment.this.getContext(), "Followers list", Toast.LENGTH_SHORT).show();
                //Intent my_intent=new Intent(ProfileFragment.this.getContext(),view_follower.class);
                //ProfileFragment.this.startActivity(my_intent);
                Intent my_intent=new Intent(ProfileFragment.this.getActivity(),view_follower.class);
                my_intent.putExtra("followers",mcount_followers);
                startActivity(my_intent);

            }
        });

        mfollowing_obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileFragment.this.getContext(),"Following List",Toast.LENGTH_SHORT).show();
                Intent my_intent=new Intent(getActivity(),view_follower.class);
                startActivity(my_intent);

            }
        });


        final ViewPager viewPager = (ViewPager)view.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(((AppCompatActivity)getActivity()).getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
