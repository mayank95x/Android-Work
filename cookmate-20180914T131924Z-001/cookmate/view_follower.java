package codes.saurabh.cookmate;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.*;

public class view_follower extends AppCompatActivity implements followersFrag.OnListFragmentInteractionListener{
    public static int no_of_followers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_follower);
        Intent intent = getIntent();
        //no_of_followers=intent.getIntExtra()Extra("followers");
        no_of_followers=intent.getIntExtra("followers",3);
        loadFragment(followersFrag.newInstance());
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onListFragmentInteraction(follower item) {
        //you can leave it empty
    }
}
