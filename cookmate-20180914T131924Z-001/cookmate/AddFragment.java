package codes.saurabh.cookmate;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import codes.saurabh.cookmate.Adapter.ImagePagerAdapter;
import codes.saurabh.cookmate.Adapter.ViewPagerAdapter;
import codes.saurabh.cookmate.Fragments.IngredientFragment1;
import codes.saurabh.cookmate.Fragments.RecipeFragment1;
import codes.saurabh.cookmate.Util.LikeUtil;
import codes.saurabh.cookmate.Util.PostUtil;
import codes.saurabh.cookmate.data.FirestoreConstant;
import codes.saurabh.cookmate.Fragments.IngredientFragment;
import codes.saurabh.cookmate.Fragments.RecipeFragment;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import codes.saurabh.cookmate.Adapter.ImagePagerAdapter;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;

    private String LOG_TAG = AddFragment.class.getSimpleName();
    private Spinner add_screen_spinner;
    private TabLayout add_screen_tab;

    private int IMAGE_REQUEST_CODE = 1001;
    private String TAG = AddFragment.class.getSimpleName();

    private ArrayList<Uri> mArrayUri;
    private ImagePagerAdapter imageAdapter;
    private ViewPager imagePager;
    private Map<String, String > urlImage;

    private String actionBarColor;
    private SwitchCompat switchCompat;
    public static EditText mRecipeEditText;
    public static EditText mIngredientEditText;

    private String mUserName = "@pk";

    private FirebaseFirestore mFirestoreDatabase;
    private DocumentReference mUsers;

    private CollectionReference mPosts;
    private DocumentReference mPhotos;
    private DocumentReference mPostUserList;
    private DocumentReference mLikeList;
    private DocumentReference mCommentList;
    private DocumentReference mCounterList;

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;

    private OnFragmentInteractionListener mListener;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    //public static HomeFragment newInstance(String param1, String param2) {
    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);

        View addView = inflater.inflate(R.layout.fragment_add, null);

        actionBarColor = "#FFA000";

        mFirestoreDatabase = FirebaseFirestore.getInstance();
        mUsers = mFirestoreDatabase.document("users/" + mUserName);
        mPosts = mFirestoreDatabase.collection("users/" + mUserName + "/posts");
        mPostUserList = mFirestoreDatabase.document("users/" + mUserName + "/posts/postUserList");
        mCounterList = mFirestoreDatabase.document("Counter/postCount");

        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference();




        // Inflate your custom layout
        //final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_addscreen, container);

        // Set up your ActionBar
//        final ActionBar actionBar = ((AppCompatActivity)getContext()).getSupportActionBar();
//        actionBar.setCustomView(actionBarLayout);
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayShowCustomEnabled(true);
//
//        ColorDrawable colorDrawable = new ColorDrawable(
//                Color.parseColor(actionBarColor));
//        actionBar.setBackgroundDrawable(colorDrawable);
//
//        actionBar.setCustomView(actionBarLayout);


        switchCompat = (SwitchCompat) addView.findViewById(R.id.add_screen_switch_compat);


        TextView postButton = (TextView) addView.findViewById(R.id.add_screen_post_button);
        TextView cancelButton = (TextView) addView.findViewById(R.id.add_screen_cancel_button);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Post saved", Toast.LENGTH_SHORT).show();
                submitPost();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }
        });


        mArrayUri = new ArrayList<Uri>();

        add_screen_spinner = (Spinner) addView.findViewById(R.id.add_screen_category_spinner);
        ArrayList<String> categories = getSpinnerData();

        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, categories);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_screen_spinner.setAdapter(spinner_adapter);

        ViewPager add_screen_view_pager = (ViewPager) addView.findViewById(R.id.add_screen_viewpager);
        intializeViewpager(add_screen_view_pager);

        add_screen_tab = (TabLayout) addView.findViewById(R.id.add_screen_tabLayout);
        add_screen_tab.setupWithViewPager(add_screen_view_pager);

        imagePager = (ViewPager) addView.findViewById(R.id.add_screen_image_viewpager);
        imageAdapter = new ImagePagerAdapter(getContext(), mArrayUri);
        imagePager.setAdapter(imageAdapter);

        FloatingActionButton imagePickerFab = (FloatingActionButton) addView.findViewById(R.id.add_screen_image_fab);
        imagePickerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImage();
            }
        });


        mPosts
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        return addView;
        //return inflater.inflate(R.layout.fragment_add, null);
    }

    private void intializeViewpager(ViewPager viewpager)
    {
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(((AppCompatActivity)getActivity()).getSupportFragmentManager());

        pagerAdapter.addFragment(new RecipeFragment1(), "Recipe");
        pagerAdapter.addFragment(new IngredientFragment1(), "Ingredient");

        viewpager.setAdapter(pagerAdapter);

    }

    private ArrayList<String> getSpinnerData()
    {
        ArrayList<String> temp_spinner_list = new ArrayList<>();
        temp_spinner_list.add("Chinese");
        temp_spinner_list.add("Italian");
        temp_spinner_list.add("French");
        temp_spinner_list.add("Indian");
        temp_spinner_list.add("Mexican");
        temp_spinner_list.add("German");

        return temp_spinner_list;
    }

    private void fetchImage()
    {

        Intent intentImage = new Intent();
        intentImage.setType("image/*");
        intentImage.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intentImage.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intentImage,"Select Picture"), IMAGE_REQUEST_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            //data.getParcelableArrayExtra(name);
            //If Single image selected then it will fetch from Gallery
            if (data.getData() != null) {
                Uri mImageUri = data.getData();
                mArrayUri.add(mImageUri);
                Log.v(this + "", "Selected Images" + mImageUri);
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();

                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                        }

                        Log.v(this + "", "Selected Images" + mArrayUri.size());
                    }
                }

            }
        }
    }

    private EditText captionEditText;

    private void submitPost()
    {
        captionEditText = (EditText) ((AppCompatActivity)getActivity()).findViewById(R.id.captionEditText);
        int likeCount = 0;
        int commentCount = 0;


        Log.i(LOG_TAG, "-------------" + String.valueOf(mRecipeEditText == null) + " ");
        Log.i(LOG_TAG, "---------------" + String.valueOf(mIngredientEditText == null) + " ");


        String recipe = mRecipeEditText.getText().toString();
        String ingredient = mIngredientEditText.getText().toString();

        String category = add_screen_spinner.getSelectedItem().toString();
        int is_veg;

        if(switchCompat.isSelected())
        {
            is_veg = 1;
        }
        else
        {
            is_veg = 0;
        }

        //dump newPost
        String caption = captionEditText.getText().toString();

        Map<String, Object> dataToSave = new HashMap<>();

        dataToSave.put(FirestoreConstant.username, mUserName);
        dataToSave.put(FirestoreConstant.category, category);
        dataToSave.put(FirestoreConstant.recipe, recipe);
        dataToSave.put(FirestoreConstant.ingredient, ingredient);

        dataToSave.put(FirestoreConstant.isVeg, is_veg);
        //dataToSave.put(FirestoreConstant.imageList, mArrayUri);

        dataToSave.put(FirestoreConstant.likeCount, likeCount);
        dataToSave.put(FirestoreConstant.commentCount, commentCount);

        dataToSave.put(FirestoreConstant.postDate, "23 Aug, 2018");
        dataToSave.put(FirestoreConstant.caption, caption);


        mPosts
                .add(dataToSave)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        final String postDocRef = documentReference.getId();
                        Log.d(TAG, "DocumentSnapshot added with ID: " + postDocRef);

                        mLikeList = mFirestoreDatabase.document("users/"+mUserName+"/posts/"+postDocRef+"/likes/"+postDocRef);
                        mCommentList = mFirestoreDatabase.document("users/"+mUserName+"/posts/"+postDocRef+"/comments/"+postDocRef);
                        Map<String, String> mapw = new HashMap<>();
                        mapw.put("Cookmate", "null");
                        mLikeList.set(mapw);
                        mCommentList.set(mapw);


                        //upload images
                        urlImage = new HashMap<>();


                        for(int i = 0 ; i < mArrayUri.size(); i++) {
                            StorageReference imageRef = mStorageReference.child("images/" + mUserName + "/"+postDocRef + "/" +mArrayUri.get(i).getLastPathSegment());
                            UploadTask uploadTask = imageRef.putFile(mArrayUri.get(i));
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                                    urlImage.put(downloadUri.toString() , downloadUri.toString());
                                    Log.i(TAG, "999999999999  " +  downloadUri.toString());
                                    if(mArrayUri.size() == urlImage.size())
                                    {
                                        uploadPhotoUrls(postDocRef);
                                    }
                                }
                            });

                        }


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        mCounterList
            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        long numberCount = (long) document.get("count");
                        Log.i("+=+=+==+===++==+", numberCount + "");
                        HashMap<String, Long> hash = new HashMap<>();
                        hash.put("count", numberCount+1);

                        mCounterList.set(hash)
                                .addOnSuccessListener(new OnSuccessListener< Void >() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i("done man", "done");

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.i(TAG, e + "");
                                    }
                                });

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
    private DocumentReference postCount;
    private void uploadPhotoUrls(final String postDocRef)
    {
        mPhotos = mFirestoreDatabase.document("users/" + mUserName + "/posts/" + postDocRef + "/photos/" + postDocRef);
        mPhotos.set(urlImage)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "Images added successfully");
                        postCount = mFirestoreDatabase.document("Counter/postCount");
                        postCount.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document != null && document.exists()) {
                                        postCount.update("count", (Long)document.get("count"));
                                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                });
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
}
