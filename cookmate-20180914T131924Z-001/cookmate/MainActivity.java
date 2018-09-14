package codes.saurabh.cookmate;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
                                                               CategoryFragment.OnFragmentInteractionListener,
                                                               AddFragment.OnFragmentInteractionListener,
                                                               SearchFragment.OnFragmentInteractionListener,
                                                               ProfileFragment.OnFragmentInteractionListener,
                                                               Tab1.OnFragmentInteractionListener,
                                                               Tab2.OnFragmentInteractionListener,
                                                               Tab3.OnFragmentInteractionListener{

    private Fragment fragment;

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(HomeFragment.newInstance());
                    return loadFragment(fragment);
                case R.id.navigation_category:
                    setFragment(CategoryFragment.newInstance());
                    return loadFragment(fragment);
                case R.id.navigation_add:
                    setFragment(AddFragment.newInstance());
                    return loadFragment(fragment);
                case R.id.navigation_search:
                    setFragment(SearchFragment.newInstance());
                    return loadFragment(fragment);
                case R.id.navigation_profile:
                    setFragment(ProfileFragment.newInstance());
                    return loadFragment(fragment);
            }
            return false;
        }
    };

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
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new HomeFragment());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
