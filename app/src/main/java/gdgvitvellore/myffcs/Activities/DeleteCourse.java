package gdgvitvellore.myffcs.Activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import gdgvitvellore.myffcs.API.ConnectAPI;
import gdgvitvellore.myffcs.GSON.CourseResponse;
import gdgvitvellore.myffcs.GSON.DeleteResponse;
import gdgvitvellore.myffcs.GSON.RegisteredCoursesResponse;
import gdgvitvellore.myffcs.R;



/**
 * Created by chaman on 13/6/17.
 */
public class DeleteCourse extends AppCompatActivity implements ConnectAPI.ServerAuthenticateListener {
    TextInputLayout til_name;
    TextInputEditText c_name;
    ImageView del_btn,back_btn;
    AutoCompleteTextView c_slot,c_fac,c_loc;
    TextView c_type,c_mode,c_cred,c_count;
    Bundle b;
    SharedPreferences sharedPreferences;
    ConnectAPI connectApi;
    String id,code,name,fac,slot,venue,cred,count,type,mode,uid;
    LinearLayout ll;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletecourse_activity);
        progressDialog=new ProgressDialog(this);
        init();
        setInit();
        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectApi.delete(uid,id);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setInit() {
        uid=sharedPreferences.getString("uid","");
        connectApi.getRegisteredCourse(uid);
        id=b.getString("id");
        code=b.getString("code");
        name=b.getString("name");
        fac=b.getString("faculty");
        slot=b.getString("slot");
        venue=b.getString("venue");
        cred= String.valueOf(b.getInt("cred"));
        type=b.getString("type");
        mode=b.getString("mode");
        til_name.setHint(code);
        c_name.setText(name);
        switch(type){
            case "TH": c_type.setText("Theory Only");
                break;
            case "LO":c_type.setText("Lab Only");
                break;
            case "ETH" :c_type.setText("Embedded Theory & Lab");
                break;
            case "ELA":c_type.setText("Embedded Theory & Lab");
                break;
            case "EPJ":c_type.setText("Embedded Project");
                break;
        }
        c_mode.setText(mode);
        c_cred.setText(cred);
        ll.setVisibility(View.VISIBLE);
        c_slot.setText(slot);
        c_fac.setText(fac);
        c_loc.setText(venue);

    }

    private void init() {
        b=getIntent().getExtras();
        ll=(LinearLayout)findViewById(R.id.tv_ll);
        c_name=(TextInputEditText)findViewById(R.id.codename_et);
        c_slot=(AutoCompleteTextView)findViewById(R.id.courseslot_at);
        c_fac=(AutoCompleteTextView)findViewById(R.id.courseFac_at);
        c_loc=(AutoCompleteTextView)findViewById(R.id.courseLoc_at);
        c_type=(TextView)findViewById(R.id.type_tv);
        c_mode=(TextView)findViewById(R.id.tv_mode);
        c_cred=(TextView)findViewById(R.id.tv_credit);
        c_count=(TextView)findViewById(R.id.count_tv);
        til_name=(TextInputLayout)findViewById(R.id.codename_til);
        del_btn=(ImageView)findViewById(R.id.del_btn);
        back_btn=(ImageView)findViewById(R.id.back_btn);
        sharedPreferences=getApplicationContext().getSharedPreferences("Prefs",MODE_PRIVATE);
        connectApi=new ConnectAPI(getApplicationContext());
        connectApi.setServerAuthenticateListener(this);

    }

    @Override
    public void onRequestInitiated(int code) {
        if(code==ConnectAPI.registered_code){
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        }
        if(code==ConnectAPI.delete_code){
            progressDialog.setMessage("Deleting...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            progressDialog.setCancelable(false);
        }
    }

    @Override
    public void onRequestCompleted(int code, Object result) {
        if(code==ConnectAPI.registered_code){
            RegisteredCoursesResponse regis=(RegisteredCoursesResponse)result;
            int size=regis.getAllotedCourse().size();
            for(int i=0;i<size;i++){
                if(regis.getAllotedCourse().get(i).get_id().equals(id)){
                    int count=regis.getAllotedCourse().get(i).getCount();
                    c_count.setText(String.valueOf(count));
                }
            }
            c_count.setVisibility(View.VISIBLE);
            progressDialog.dismiss();
        }
        if(code==ConnectAPI.delete_code){
            DeleteResponse deleteResponse=(DeleteResponse)result;
            if(deleteResponse.getStatus().equals("true")){
                finish();
                Toast.makeText(this, "Course Deleted Successfully.", Toast.LENGTH_SHORT).show();
            }
            else{
                Snackbar.make(getCurrentFocus(),"Failed to delete the course.Try again.",Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestError(int code, Object result) {

    }
}
