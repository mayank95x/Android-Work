package codes.saurabh.cookmate;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import codes.saurabh.cookmate.Adapter.ViewPagerAdapter;
import codes.saurabh.cookmate.Fragments.IngredientFragment;
import codes.saurabh.cookmate.Fragments.RecipeFragment;


public class ViewPostActivity extends AppCompatActivity {

    public static TextView mIngredientEditText;
    public static TextView mRecipeEditText;



    private TabLayout add_screen_tab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        Intent intent = getIntent();
        String cat = intent.getStringExtra("category");
        String rec = intent.getStringExtra("recipe");
        String ing = intent.getStringExtra("ingredient");
        String isselec = intent.getStringExtra("isSelected");
        String url = intent.getStringExtra("image");
        String cap = intent.getStringExtra("caption");


        ImageView image = (ImageView) findViewById(R.id.add_screen_image_viewpager);
        TextView caption = (TextView) findViewById(R.id.captionEditText);
        android.support.v7.widget.SwitchCompat switchVeg = (android.support.v7.widget.SwitchCompat) findViewById(R.id.add_screen_switch_compat);
        TextView category = (TextView) findViewById(R.id.add_screen_category_spinner);

        ViewPager add_screen_view_pager = (ViewPager) findViewById(R.id.add_screen_viewpager);
        intializeViewpager(add_screen_view_pager);

        add_screen_tab = (TabLayout) findViewById(R.id.add_screen_tabLayout);
        add_screen_tab.setupWithViewPager(add_screen_view_pager);




        category.setText(cat);
        caption.setText(cap);
        if(isselec.equals("0"))
        {
            switchVeg.setChecked(false);
        }
        else
        {
            switchVeg.setChecked(true);
        }
        Glide.with(this)
                .load(url)
                .into(image);


        //mIngredientEditText.setText(ing);
        //mRecipeEditText.setText(rec);

    }




    private void intializeViewpager(ViewPager viewpager)
    {
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(new RecipeFragment(), "Recipe");
        pagerAdapter.addFragment(new IngredientFragment(), "Ingredient");

        viewpager.setAdapter(pagerAdapter);



    }
}
