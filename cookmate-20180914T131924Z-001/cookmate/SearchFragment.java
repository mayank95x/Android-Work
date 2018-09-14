package codes.saurabh.cookmate;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.*;
import com.google.firebase.firestore.*;
import com.google.android.gms.tasks.Task;
import android.util.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.*;

import java.util.*;
import java.util.Map;



import com.google.firebase.firestore.CollectionReference;

import org.w3c.dom.Document;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";
    private static String food_search="Chole Bhature";

    private RecyclerView mSearchRecyclerView;
    private SearchAdapter mAdapter;
    private List<s_card> mSearchList;
    private ImageView search_button;
    //public final String s

    //(ImageView) findViewById(R.id.search_btn);

    public ArrayList<String> output_display=new ArrayList<String>();

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
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
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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

        /*

        */
        //Log.d("final_items","final"+output_display);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);
        final View searchFragView = inflater.inflate(R.layout.fragment_search, null);
        mSearchRecyclerView = (RecyclerView) searchFragView.findViewById(R.id.result_list);
        mSearchList = new ArrayList<>();
        mAdapter = new SearchAdapter(getActivity(),mSearchList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mSearchRecyclerView.setLayoutManager(mLayoutManager);
        mSearchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSearchRecyclerView.setAdapter(mAdapter);

        final EditText searchContent = (EditText) searchFragView.findViewById(R.id.search_field);

        search_button = (ImageView) searchFragView.findViewById(R.id.search_btn);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                final String searchText = searchContent.getText().toString();
                //Toast.makeText(ProfileFragment.this.getContext(), "Followers list", Toast.LENGTH_SHORT).show();
                Toast.makeText(searchFragView.getContext(),"Searching",Toast.LENGTH_SHORT).show();
                Log.d("here",searchText+"");
                mSearchList.clear();
                prepareCards(searchText);

            }
        });

        return searchFragView;
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

    private void prepareCards(final String searchText){
        Log.d("text_to_search",""+searchText);

        final int[] contentImages = new int[]{
                R.drawable.food_image_1,
                R.drawable.food_image_2,
                R.drawable.food_image_3,
                R.drawable.food_image_4,
                R.drawable.food_image_5,
                R.drawable.food_image_6,
                R.drawable.food_image_7,
                R.drawable.food_image_8,
                R.drawable.food_image_9,
                R.drawable.food_image_10,
                //here is lts of food items to be displayed in search
                R.drawable.food_image_11,   //chole bhature
                R.drawable.food_image_12,   //chicken noodles
                R.drawable.food_image_13,   //chicken kebab
                R.drawable.food_image_14,   //chicken tikka
                R.drawable.food_image_15,   //italian pasta
                R.drawable.food_image_16,   //mushroom pasta
                R.drawable.food_image_17    //veg noodles
        };

        //s_card c;
        /*
        */
        final String[] food_items=new String[]{"chole bhature","chicken noodles","chicken kebab","chicken tikka","italiano pasta"
        ,"mushroom pasta","veg noodles"
        };

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference doc_ref=db.collection("search").document("searchDoc");
        Task<DocumentSnapshot> documentSnapshotTask = doc_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("fine", "working");
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        Log.d("output", "hello" + document.getData());
                        Iterator it = document.getData().entrySet().iterator();
                        s_card c;
                        while (it.hasNext()) {
                            int done = 0;
                            Map.Entry pair = (Map.Entry) it.next();

                            String user_handle[] = pair.getValue().toString().split("/");
                            Log.d("user_handle", "" + user_handle[1]);
                            Log.d("bhadwa", searchText + "");
                            String tokens_search_text[] = searchText.split(" ");

                            if (pair.getKey().equals(searchText)) {
                                int index_found=Arrays.asList(food_items).indexOf(searchText);
                                Log.d("index",index_found+"");
                                c = new s_card(searchText, user_handle[1], contentImages[index_found+10]);
                                mSearchList.add(c);
                                output_display.add((String) pair.getKey());
                                done = 1;
                            }
                            if (pair.getKey().toString().toLowerCase().equals(searchText) && done == 0) {
                                int index_found=Arrays.asList(food_items).indexOf(searchText.toLowerCase());

                                c = new s_card(searchText, user_handle[1], contentImages[index_found+10]);
                                mSearchList.add(c);
                                output_display.add((String) pair.getKey());
                                done = 2;
                            }
                            if (done == 0) {
                                String items_database[] = ((String) pair.getKey()).split(" ");
                                Log.d("items_databse", "items_database" + items_database);
                                for (String tr : items_database) {
                                    if (searchText.toLowerCase().contains(tr.toLowerCase())) {
                                        int index_found=Arrays.asList(food_items).indexOf(((String) pair.getKey()).toLowerCase());
                                        c = new s_card(pair.getKey().toString(), user_handle[1], contentImages[10+index_found]);
                                        mSearchList.add(c);

                                    }
                                }
                            }


                        }
                        mAdapter.notifyDataSetChanged();

                    } else {
                        Log.d("o", "no such doc");
                    }
                } else {
                    Log.d("fail", "failed due" + task.getException());
                }
            }
        });

        /*
        */

        mAdapter.notifyDataSetChanged();

    }
}
