package codes.saurabh.cookmate.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import codes.saurabh.cookmate.AddFragment;
import codes.saurabh.cookmate.MainActivity;
import codes.saurabh.cookmate.R;
import codes.saurabh.cookmate.ViewPostActivity;


public class IngredientFragment1 extends Fragment {

    public IngredientFragment1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredient1, container, false);


        AddFragment.mIngredientEditText = (EditText) view.findViewById(R.id.add_screen_text_ingredients1);


        return view;
    }

}
