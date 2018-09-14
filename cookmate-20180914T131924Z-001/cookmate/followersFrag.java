package codes.saurabh.cookmate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import android.util.*;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class followersFrag extends Fragment {
    private OnListFragmentInteractionListener mListener;
    private List<follower> followers_list = new ArrayList<>();
    private FollowersRecyclerViewAdapter mFollowerAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public followersFrag() {
    }

    // COMPLETED: Customize parameter initialization
    @SuppressWarnings("unused")
    public static followersFrag newInstance() {
        followersFrag fragment = new followersFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.followers_item_list, container, false);

        // Set the adapter

            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            mFollowerAdapter = new FollowersRecyclerViewAdapter(followers_list, context);
            recyclerView.setAdapter(mFollowerAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                    recyclerView.getContext(),
                    layoutManager.getOrientation()
            );
            recyclerView.addItemDecoration(mDividerItemDecoration);
            prepareFollowers("@coolchef");// This method prepares dummy followers

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(follower item);
    }

    private void prepareFollowers(final String user_handle){
        Log.d("to_be_search",""+user_handle);
        final int[] picIDs = new int[]{
                R.drawable.p1,
                R.drawable.p2,
                R.drawable.p3,
                R.drawable.p4,
                R.drawable.p5,
                R.drawable.p6,
                R.drawable.p7,
                R.drawable.p8,
                R.drawable.p9,
                R.drawable.p10,
        };

        final String[] user_handles_buffer=new String[]{"@coolchef","@kitchenking","@taouji","@skyrocket","@neednogun","@johndoe","@imguy","@hotchef"};
        final String[] user_names=new String[]{"Mayank Bhoria","Rohit kumar","Shaym Agrawal","John Doe","Robert De neiro","Al Pacino","Sameul L Jackson","Leonardo De Caprio"};

        /*
        followers_list.add(new follower("@thecoolchef","Mayank Bhoriya",picIDs[0]));
        followers_list.add(new follower("@kitchenking","Akash Kumar Gautam",picIDs[1]));
        followers_list.add(new follower("@touji","Mayank Kumar",picIDs[2]));
        followers_list.add(new follower("@skytherocket","Rohit Kumar",picIDs[3]));
        followers_list.add(new follower("@neednogun","Saurabh Kumar",picIDs[4]));
        followers_list.add(new follower("@papakipari","Shyami Kumari",picIDs[6]));
        followers_list.add(new follower("@sauravkapati","Satyender",picIDs[5]));
        followers_list.add(new follower("@satenderkilugai","Saurav Kumar",picIDs[7]));
        followers_list.add(new follower("@imguy","Random Guy",picIDs[8]));
        followers_list.add(new follower("@theroadrunner","Patola",picIDs[9]));
        */


        int to_move=view_follower.no_of_followers;
        Log.d("no_of_followers",""+to_move);
        for(int i=0;i<to_move;i++){
            followers_list.add(new follower(user_handles_buffer[i],user_names[i],picIDs[i]));
        }


        mFollowerAdapter.notifyDataSetChanged();
    }
}
