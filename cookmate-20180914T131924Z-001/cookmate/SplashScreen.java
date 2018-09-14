package codes.saurabh.cookmate;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

public class SplashScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initViews();



        new Handler().postDelayed(new Runnable() {

        /*
         * Showing splash screen with a timer. This will be useful when you
         * want to show case your app logo / company
         */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, GoogleSignInActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 4000);// times in millisends
    }


    private void initViews() {

        ImageView imageView = (ImageView) findViewById(R.id.image);
        //GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        //Glide.with(SplashScreen.this).load(R.raw.splash).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        Glide.with(this).asGif().load(R.raw.image).into(imageView);


//        Glide.with(this)
//                .load(R.raw.splash)
//                .into(new DrawableImageViewTarget(imageView) {
//                    @Override
//                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
//                        if (resource instanceof GifDrawable) {
//                            GifDrawable gifDrawable = (GifDrawable) resource;
//                            // Do things with GIF here.
//
//                        }
//                    }
//                });
    }

}
