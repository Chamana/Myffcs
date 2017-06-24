package gdgvitvellore.myffcs.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import gdgvitvellore.myffcs.R;

/**
 * Created by chaman on 20/6/17.
 */

public class AboutUs extends AppCompatActivity {
    ImageView fb,gh;
    public static String fb_url="https://facebook.com/gdgvitvellore";
    public static String gh_url="https://github.com/GDGVIT";
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
        gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ghIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(gh_url));
                startActivity(ghIntent);
            }
        });
    }

    private void init() {
        fb=(ImageView)findViewById(R.id.fb);
        gh=(ImageView)findViewById(R.id.git);
    }

}
