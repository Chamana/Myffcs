package gdgvitvellore.myffcs.Activities;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import gdgvitvellore.myffcs.R;

/**
 * Created by chaman on 20/6/17.
 */

public class Aboutus extends AppCompatActivity {
    ImageView fb,gp,gh,tw;
    public static String fb_url="https://facebook.com/gdgvitvellore";
    public static String gp_url="https://plus.google.com/+gdgvitvellore";
    public static String gh_url="https://github.com/GDGVIT";
    public static String tw_url="https://twitter.com/gdgvit";
    @Override


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        init();
        setInit();
    }

    private void setInit() {
        fb.setOnClickListener(new View.OnClickListener() {
          @Override
           public void onClick(View v) {
              Intent fbIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(fb_url));
              startActivity(fbIntent);
           }
        });

        gp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gpIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(gp_url));
                startActivity(gpIntent);
            }
        });

        gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ghIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(gh_url));
                startActivity(ghIntent);
            }
        });

        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(tw_url));
                startActivity(twIntent);
            }
        });

    }

    private void init() {
        fb=(ImageView)findViewById(R.id.fb);
        gp=(ImageView)findViewById(R.id.gplus);
        gh=(ImageView)findViewById(R.id.github);
        tw=(ImageView)findViewById(R.id.twitter);
    }
}
