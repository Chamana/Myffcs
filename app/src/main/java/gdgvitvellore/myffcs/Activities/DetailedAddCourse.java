package gdgvitvellore.myffcs.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gdgvitvellore.myffcs.API.ConnectAPI;
import gdgvitvellore.myffcs.GSON.CourseResponse;
import gdgvitvellore.myffcs.GSON.InsertResponse;
import gdgvitvellore.myffcs.R;

/**
 * Created by chaman on 19/6/17.
 */

public class DetailedAddCourse extends Activity implements ConnectAPI.ServerAuthenticateListener {
    TextInputEditText c_name;
    AutoCompleteTextView c_slot,c_fac,c_loc,c_lslot,c_lfac;
    TextInputLayout til_name,til_slot,til_fac,til_emblabslot,til_loc;
    FloatingActionButton submit_fab;
    TextView c_type,c_mode,c_cred,c_count;
    ArrayList<String> id,fac,crscd,crsnm,slot,cred,venue,UniqueSlotList,UniqueFacultyList,type,mode,uniqueEThSlotList,uniqueELASlotList;
    ArrayList<Integer> count;
    Bundle b;
    ArrayAdapter<String> slotAdapter,facultyAdapter,ethSlotAdapter,eloSlotAdapter;
    String selectedCourseId,selectedCourseId1,selectedCourseId2;
    ConnectAPI connectAPI;
    List<CourseResponse> courses;
    SharedPreferences sharedPreferences;
    LinearLayout cred_ll;
    GoogleProgressBar googleProgressBar;
    ProgressDialog progressDialog;
    LinearLayout addCourse_ll;
    String couType,couMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcourses_activity);
        connectAPI=new ConnectAPI(getApplicationContext());
        connectAPI.setServerAuthenticateListener(this);
        connectAPI.offeredCourse();
        init();
        setInit();
    }

    private void init() {
        c_name=(TextInputEditText)findViewById(R.id.codename_et);
        c_slot=(AutoCompleteTextView)findViewById(R.id.courseslot_at);
        c_fac=(AutoCompleteTextView)findViewById(R.id.courseFac_at);
        c_loc=(AutoCompleteTextView)findViewById(R.id.courseLoc_at);
        c_lslot=(AutoCompleteTextView)findViewById(R.id.emblabslot_at);
        submit_fab=(FloatingActionButton)findViewById(R.id.submit_fab);
        c_type=(TextView)findViewById(R.id.type_tv);
        c_mode=(TextView)findViewById(R.id.tv_mode);
        c_cred=(TextView)findViewById(R.id.tv_credit);
        c_count=(TextView)findViewById(R.id.count_tv);
        type=new ArrayList<String>();
        til_name=(TextInputLayout)findViewById(R.id.codename_til);
        til_slot=(TextInputLayout)findViewById(R.id.courseslot_til);
        til_fac=(TextInputLayout)findViewById(R.id.courseFac_til);
        til_emblabslot=(TextInputLayout)findViewById(R.id.emblabslot_til);
        til_loc=(TextInputLayout)findViewById(R.id.courseLoc_til);
        id=new ArrayList<String>();
        fac=new ArrayList<String>();
        crscd=new ArrayList<String>();
        crsnm=new ArrayList<String>();
        slot=new ArrayList<String>();
        venue=new ArrayList<String>();
        cred=new ArrayList<String>();
        mode=new ArrayList<String>();
        uniqueEThSlotList=new ArrayList<String>();
        uniqueELASlotList=new ArrayList<String>();
        count=new ArrayList<>();
        addCourse_ll=(LinearLayout)findViewById(R.id.addCourse_ll);
        cred_ll=(LinearLayout)findViewById(R.id.cred_ll);
        //googleProgressBar=(GoogleProgressBar)findViewById(R.id.progressBar);
        UniqueFacultyList=new ArrayList<String>();
        sharedPreferences=getApplicationContext().getSharedPreferences("Prefs",MODE_PRIVATE);
        b=getIntent().getExtras();
    }

    private void setInit() {
        til_name.setHint(b.getString("code"));
        c_name.setText(b.getString("name"));
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching Course Details...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    @Override
    public void onRequestInitiated(int code) {

    }

    @Override
    public void onRequestCompleted(final int code, final Object result) {

        if(code==ConnectAPI.offerdcourses_code) {
            courses = (List<CourseResponse>) result;
            int size = courses.size();
            //Fetching the details of the course selected
            for (int i = 0; i < size; i++) {
                if (courses.get(i).getCrscd().equals(b.getString("code"))) {
                    id.add(courses.get(i).get_id());
                    fac.add(courses.get(i).getFaculty());
                    slot.add(courses.get(i).getSlot());
                    cred.add(courses.get(i).getCredits());
                    venue.add(courses.get(i).getVenue());
                    type.add(courses.get(i).getType());
                    mode.add(courses.get(i).getMode());
                }
            }

            //fetching the course types available for the selected course code
            final Set<String> courseType=new HashSet<String>(type);
            ArrayList<String> ctype=new ArrayList<String>(courseType);
            couType="";

            //Setting the course type
            for(int i=0;i<ctype.size();i++){
                couType=couType+ctype.get(i);
            }
            if(couType.toUpperCase().equals("TH")){
                c_type.setText("Theory Only");
            }
            else if(couType.toUpperCase().equals("LO")){
                c_type.setText("Lab Only");
            }
            else if(couType.toUpperCase().equals("ELAETH") || couType.toUpperCase().equals("ETHELA")){
                c_type.setText("Embedded Theory & Lab");
                addCourse_ll.setWeightSum(7);
                til_emblabslot.setVisibility(View.VISIBLE);
            }
            else if(couType.toUpperCase().equals("EPJETH") || couType.toUpperCase().equals("ETHEPJ")){
                c_type.setText("Embedded Theory & Project");
            }else if(couType.toUpperCase().equals("EPJELA") || couType.toUpperCase().equals("ELAEPJ")){
                c_type.setText("Embedded Lab & Project");
            }
            else if(couType.toUpperCase().equals("EPJELAETH") || couType.toUpperCase().equals("EPJETHELA") || couType.toUpperCase().equals("ETHELAEPJ") ||couType.toUpperCase().equals("ETHEPJELA") || couType.toUpperCase().equals("ELAETHEPJ") || couType.equals("ELAEPJETH")){
                c_type.setText("Embedded Theory Lab & Project");
                addCourse_ll.setWeightSum(7);
                til_emblabslot.setVisibility(View.VISIBLE);
            }
            else{
                //nothing
            }


            //Getting the different course mode available for the selected course code
            final Set<String> courseMode=new HashSet<String>(mode);
            ArrayList<String> cmode=new ArrayList<String>(courseMode);
            couMode="";

            //setting the course mode for the selected course code
            for(int i=0;i<cmode.size();i++){
                couMode=couMode+" "+cmode.get(i);
            }
            c_mode.setText(couMode);

            //getting the credits for the selected course code
            final Set<String> courseCred=new HashSet<String>(cred);
            ArrayList<String> credits=new ArrayList<String>(courseCred);
            int credit=0;

            //setting the credit for the selected course
            for(int i=0;i<credits.size();i++){
                credit+=Integer.parseInt(credits.get(i));
            }
            //the following condition is applicable for courses of CSE that contains all 3 type theory,lab and project so we add 1 for the project which is default one.
            if(couType.toUpperCase().equals("EPJELAETH") || couType.toUpperCase().equals("EPJETHELA") || couType.toUpperCase().equals("ETHELAEPJ") ||couType.toUpperCase().equals("ETHEPJELA") || couType.toUpperCase().equals("ELAETHEPJ") || couType.equals("ELAEPJETH")){
                credit+=1;
            }
            c_cred.setText(String.valueOf(credit));


            c_type.setVisibility(View.VISIBLE);
            c_mode.setVisibility(View.VISIBLE);
            cred_ll.setVisibility(View.VISIBLE);
            progressDialog.dismiss();

            //displaying all the slots available for the code selected
            c_slot.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) {
                        setSlotAdapter();
                    }
                }
            });

            //after the slot is selected what has to be done is defined here
            c_slot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    InputMethodManager imm = (InputMethodManager)getApplicationContext(). getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    c_fac.requestFocus();
                }
            });

            //displaying the list of faculties for the selected slot of the selected course code
            c_fac.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        InputMethodManager imm = (InputMethodManager)getApplicationContext(). getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        setFacultyAdapter();
                    }
                }
            });

            //setting the location and count of the registered students for theory only,lab only, theory and project,lab and project courses and rest display the lab slots
            c_fac.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if(couType.toUpperCase().equals("TH") || couType.toUpperCase().equals("LO")){
                        int s;
                        for(int i=0;i<courses.size();i++){
                            if(courses.get(i).getCrscd().equals(til_name.getHint().toString()) && courses.get(i).getSlot().equals(c_slot.getText().toString()) && courses.get(i).getFaculty().equals(c_fac.getText().toString())){
                                c_loc.setText(courses.get(i).getVenue());
                                selectedCourseId=courses.get(i).get_id();
                                int count=courses.get(i).getCount().length;
                                c_count.setText(String.valueOf(count));
                            }
                        }
                    }
                    else if(couType.toUpperCase().equals("ELAETH") || couType.toUpperCase().equals("ETHELA")){
                        c_lslot.requestFocus();
                    }
                    else if(couType.toUpperCase().equals("EPJETH") || couType.toUpperCase().equals("ETHEPJ")){
                        for(int i=0;i<courses.size();i++){
                            if(courses.get(i).getCrscd().equals(til_name.getHint().toString()) && courses.get(i).getSlot().equals(c_slot.getText().toString()) && courses.get(i).getFaculty().equals(c_fac.getText().toString())){
                                c_loc.setText(courses.get(i).getVenue());
                                selectedCourseId=courses.get(i).get_id();
                                int count=courses.get(i).getCount().length;
                                c_count.setText(String.valueOf(count));
                            }
                        }
                        for(int i=0;i<courses.size();i++){
                            if(courses.get(i).getCrscd().equals(til_name.getHint()) && courses.get(i).getSlot().toUpperCase().equals("NIL") && courses.get(i).getFaculty().toUpperCase().contains("ACAD") && courses.get(i).getType().equals("EPJ")){
                                selectedCourseId1=courses.get(i).get_id();
                            }
                        }
                    }else if(couType.toUpperCase().equals("EPJELA") || couType.toUpperCase().equals("ELAEPJ")){
                        for(int i=0;i<courses.size();i++){
                            if(courses.get(i).getCrscd().equals(til_name.getHint().toString()) && courses.get(i).getSlot().equals(c_slot.getText().toString()) && courses.get(i).getFaculty().equals(c_fac.getText().toString())){
                                c_loc.setText(courses.get(i).getVenue());
                                selectedCourseId=courses.get(i).get_id();
                                int count=courses.get(i).getCount().length;
                                c_count.setText(String.valueOf(count));
                            }
                        }
                        for(int i=0;i<courses.size();i++){
                            if(courses.get(i).getCrscd().equals(til_name.getHint()) && courses.get(i).getSlot().toUpperCase().equals("NIL") && courses.get(i).getFaculty().toUpperCase().contains("ACAD") && courses.get(i).getType().equals("EPJ")){
                                selectedCourseId1=courses.get(i).get_id();
                            }
                        }
                    }
                    else if(couType.toUpperCase().equals("EPJELAETH") || couType.toUpperCase().equals("EPJETHELA") || couType.toUpperCase().equals("ETHELAEPJ") ||couType.toUpperCase().equals("ETHEPJELA") || couType.toUpperCase().equals("ELAETHEPJ") || couType.equals("ELAEPJETH")){
                       c_lslot.requestFocus();
                    }
                    else{
                        //nothing
                    }
                }
            });

            //this is to display the lab slots of the faculty selected and course as embedded courses the theory and lab faculty must be same
            c_lslot.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        setEmbLabSlot();
                    }
                }
            });

            //Now setting the location,count of the embedded courses
            c_lslot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String venue="";
                    String coun="";
                    if(couType.toUpperCase().equals("ELAETH") || couType.toUpperCase().equals("ETHELA")){
                        for(int i=0;i<courses.size();i++){
                            if(courses.get(i).getCrscd().equals(til_name.getHint().toString()) && courses.get(i).getSlot().equals(c_slot.getText().toString()) && courses.get(i).getFaculty().equals(c_fac.getText().toString())){
                                venue=courses.get(i).getVenue();
                                selectedCourseId=courses.get(i).get_id();
                                int count=courses.get(i).getCount().length;
                                coun+= "TH: "+String.valueOf(count);
                            }
                        }
                        for(int i=0;i<courses.size();i++){
                            if(courses.get(i).getCrscd().equals(til_name.getHint()) && courses.get(i).getSlot().toUpperCase().equals(c_lslot.getText().toString().toUpperCase()) && courses.get(i).getFaculty().equals(c_fac.getText().toString()) && courses.get(i).getType().equals("ELA")){
                                selectedCourseId1=courses.get(i).get_id();
                                venue=venue+","+courses.get(i).getVenue();
                                int count=courses.get(i).getCount().length;
                                coun=coun+" , "+"LO: "+String.valueOf(count);
                            }
                        }
                        c_loc.setText(venue);
                        c_count.setText(coun);
                    }
                    if(couType.toUpperCase().equals("EPJELAETH") || couType.toUpperCase().equals("EPJETHELA") || couType.toUpperCase().equals("ETHELAEPJ") ||couType.toUpperCase().equals("ETHEPJELA") || couType.toUpperCase().equals("ELAETHEPJ") || couType.equals("ELAEPJETH")){
                        String venue1="";
                        String coun1="";
                            for(int i=0;i<courses.size();i++){
                                if(courses.get(i).getCrscd().equals(til_name.getHint().toString()) && courses.get(i).getSlot().equals(c_slot.getText().toString()) && courses.get(i).getFaculty().equals(c_fac.getText().toString())){
                                    venue1=courses.get(i).getVenue();
                                    selectedCourseId=courses.get(i).get_id();
                                    int count=courses.get(i).getCount().length;
                                    coun1+= "TH: "+String.valueOf(count);
                                }
                            }
                            for(int i=0;i<courses.size();i++){
                                if(courses.get(i).getCrscd().equals(til_name.getHint()) && courses.get(i).getSlot().toUpperCase().equals(c_lslot.getText().toString().toUpperCase()) && courses.get(i).getFaculty().equals(c_fac.getText().toString()) && courses.get(i).getType().equals("ELA")){
                                    selectedCourseId1=courses.get(i).get_id();
                                    venue1=venue1+","+courses.get(i).getVenue();
                                    int count=courses.get(i).getCount().length;
                                    coun1=coun1+" , "+"LO: "+String.valueOf(count);
                                }
                            }
                            for(int i=0;i<courses.size();i++){
                                 if(courses.get(i).getCrscd().equals(til_name.getHint()) && courses.get(i).getSlot().toUpperCase().equals("NIL") && courses.get(i).getFaculty().toUpperCase().contains("ACAD") && courses.get(i).getType().equals("EPJ")){
                                     selectedCourseId2=courses.get(i).get_id();
                                 }
                            }
                        c_loc.setText(venue1);
                        c_count.setText(coun1);
                    }
                }
            });

            /**
             * Inserting the course when the fab icon is clicked
             */

            submit_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(c_slot.getText().toString())){
                        Snackbar.make(getCurrentFocus(),"select the slot",Snackbar.LENGTH_SHORT).show();
                    }
                    if(TextUtils.isEmpty(c_fac.getText().toString())){
                        Snackbar.make(getCurrentFocus(),"select the faculty",Snackbar.LENGTH_SHORT).show();
                    }
                    if(!TextUtils.isEmpty(c_slot.getText().toString()) && !TextUtils.isEmpty(c_fac.getText().toString())) {
                        progressDialog.setMessage("Adding Course...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        if (couType.toUpperCase().equals("TH")) {
                            connectAPI.insert(sharedPreferences.getString("uid", ""), selectedCourseId);
                        } else if (couType.toUpperCase().equals("LO")) {
                            connectAPI.insert(sharedPreferences.getString("uid", ""), selectedCourseId);
                        } else if (couType.toUpperCase().equals("ELAETH") || couType.toUpperCase().equals("ETHELA")) {
                            connectAPI.insertEmbedded(sharedPreferences.getString("uid", ""), selectedCourseId, selectedCourseId1);
                        } else if (couType.toUpperCase().equals("EPJETH") || couType.toUpperCase().equals("ETHEPJ")) {
                            connectAPI.insertEmbedded(sharedPreferences.getString("uid", ""), selectedCourseId, selectedCourseId1);
                        } else if (couType.toUpperCase().equals("EPJELA") || couType.toUpperCase().equals("ELAEPJ")) {
                            connectAPI.insertEmbedded(sharedPreferences.getString("uid", ""), selectedCourseId, selectedCourseId1);
                        } else if (couType.toUpperCase().equals("EPJELAETH") || couType.toUpperCase().equals("EPJETHELA") || couType.toUpperCase().equals("ETHELAEPJ") || couType.toUpperCase().equals("ETHEPJELA") || couType.toUpperCase().equals("ELAETHEPJ") || couType.equals("ELAEPJETH")) {
                            connectAPI.insertEmbThLoPjCourse(sharedPreferences.getString("uid", ""), selectedCourseId, selectedCourseId1, selectedCourseId2);
                        } else {
                            //nothing
                        }
                    }else{
                        Snackbar.make(getCurrentFocus(),"Check the details",Snackbar.LENGTH_SHORT).show();
                    }

                }
            });
        }
        if (code==ConnectAPI.insertCourse_code){
            InsertResponse insertResponse=(InsertResponse)result;
            progressDialog.dismiss();
            if(insertResponse.getStatus().equals("false")){
                Snackbar.make(getCurrentFocus(),insertResponse.getMessage(),Snackbar.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        if(code==ConnectAPI.insertEmbeddedCourse_code){
            InsertResponse insertEmbRes=(InsertResponse)result;
            progressDialog.dismiss();
            if(insertEmbRes.getStatus().equals("false")){
                Snackbar.make(getCurrentFocus(),insertEmbRes.getMessage(),Snackbar.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        if(code==ConnectAPI.insertEmbTLPCourse_code){
            InsertResponse insertResponse=(InsertResponse)result;
            progressDialog.dismiss();
            if(insertResponse.getStatus().equals("false")){
                Snackbar.make(getCurrentFocus(),insertResponse.getMessage(),Snackbar.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }


    @Override
    public void onRequestError(int code, Object result) {

    }


    /**
     * This function is used to display the slots available for the course code selected based on the type of the course
     */
    private void setSlotAdapter() {
        if(couType.toUpperCase().equals("TH")){
            Set<String> uniqueSlots=new HashSet<String>(slot);
            UniqueSlotList=new ArrayList<String>(uniqueSlots);
            Collections.sort(UniqueSlotList);
            slotAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,UniqueSlotList);
            c_slot.setThreshold(0);
            c_slot.setAdapter(slotAdapter);
            c_slot.setDropDownBackgroundResource(R.color.colorPrimary);
            c_slot.showDropDown();
        }else if(couType.toUpperCase().equals("LO")){
            Set<String> uniqueSlots = new HashSet<String>(slot);
            UniqueSlotList=new ArrayList<String>(uniqueSlots);
            Collections.sort(UniqueSlotList);
            slotAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,UniqueSlotList);
            c_slot.setThreshold(0);
            c_slot.setAdapter(slotAdapter);
            c_slot.setDropDownBackgroundResource(R.color.colorPrimary);
            c_slot.showDropDown();
        } else if(couType.toUpperCase().equals("ELAETH") || couType.toUpperCase().equals("ETHELA")){
            ArrayList<String> ethslots=new ArrayList<>();
            for(int i=0;i<slot.size();i++){
                if(slot.get(i).contains("A") || slot.get(i).contains("B") || slot.get(i).contains("C") || slot.get(i).contains("D") || slot.get(i).contains("E") || slot.get(i).contains("F") || slot.get(i).contains("G")){
                    ethslots.add(slot.get(i).toString());
                }
            }
            Set<String> uniqueETHSlots=new HashSet<String>(ethslots);
            uniqueEThSlotList=new ArrayList<String>(uniqueETHSlots);
            Collections.sort(uniqueEThSlotList);
            ethSlotAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,uniqueEThSlotList);
            c_slot.setThreshold(0);
            c_slot.setAdapter(ethSlotAdapter);
            c_slot.setDropDownBackgroundResource(R.color.colorPrimary);
            c_slot.showDropDown();
        }
        else if(couType.toUpperCase().equals("EPJETH") || couType.toUpperCase().equals("ETHEPJ")){
            ArrayList<String> ethslots=new ArrayList<>();
            for(int i=0;i<slot.size();i++){
                if(slot.get(i).contains("A") || slot.get(i).contains("B") || slot.get(i).contains("C") || slot.get(i).contains("D") || slot.get(i).contains("E") || slot.get(i).contains("F") || slot.get(i).contains("G")){
                    ethslots.add(slot.get(i).toString());
                }
            }
            Set<String> uniqueETHSlots=new HashSet<String>(ethslots);
            uniqueEThSlotList=new ArrayList<String>(uniqueETHSlots);
            Collections.sort(uniqueEThSlotList);
            ethSlotAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,uniqueEThSlotList);
            c_slot.setThreshold(0);
            c_slot.setAdapter(ethSlotAdapter);
            c_slot.setDropDownBackgroundResource(R.color.colorPrimary);
            c_slot.showDropDown();
            c_lslot.setText("NIL");
        }else if(couType.toUpperCase().equals("EPJELA") || couType.toUpperCase().equals("ELAEPJ")){
            ArrayList<String> ethslots=new ArrayList<>();
            for(int i=0;i<slot.size();i++){
                if(slot.get(i).contains("L")){
                    ethslots.add(slot.get(i).toString());
                }
            }
            Set<String> uniqueETHSlots=new HashSet<String>(ethslots);
            uniqueEThSlotList=new ArrayList<String>(uniqueETHSlots);
            Collections.sort(uniqueEThSlotList);
            ethSlotAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,uniqueEThSlotList);
            c_slot.setThreshold(0);
            c_slot.setAdapter(ethSlotAdapter);
            c_slot.setDropDownBackgroundResource(R.color.colorPrimary);
            c_slot.showDropDown();
            c_lslot.setText("NIL");
        }
        else if(couType.toUpperCase().equals("EPJELAETH") || couType.toUpperCase().equals("EPJETHELA") || couType.toUpperCase().equals("ETHELAEPJ") ||couType.toUpperCase().equals("ETHEPJELA") || couType.toUpperCase().equals("ELAETHEPJ") || couType.equals("ELAEPJETH")){
            ArrayList<String> ethslots=new ArrayList<>();
            for(int i=0;i<slot.size();i++){
                if(slot.get(i).contains("A") || slot.get(i).contains("B") || slot.get(i).contains("C") || slot.get(i).contains("D") || slot.get(i).contains("E") || slot.get(i).contains("F") || slot.get(i).contains("G")){
                    ethslots.add(slot.get(i).toString());
                }
            }
            Set<String> uniqueETHSlots=new HashSet<String>(ethslots);
            uniqueEThSlotList=new ArrayList<String>(uniqueETHSlots);
            Collections.sort(uniqueEThSlotList);
            ethSlotAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,uniqueEThSlotList);
            c_slot.setThreshold(0);
            c_slot.setAdapter(ethSlotAdapter);
            c_slot.setDropDownBackgroundResource(R.color.colorPrimary);
            c_slot.showDropDown();
        }
        else{
            //do nothing
        }
    }
    /**
     * This function is used to display the list of faculty for the selected slot and code
     */
    private void setFacultyAdapter(){
        UniqueFacultyList.clear();
        for(int i=0;i<courses.size();i++){
            if(courses.get(i).getCrscd().equals(til_name.getHint().toString()) && courses.get(i).getSlot().equals(c_slot.getText().toString())){
                UniqueFacultyList.add(courses.get(i).getFaculty().toString());
            }
        }
        facultyAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,UniqueFacultyList);
        c_fac.setThreshold(0);
        c_fac.setAdapter(facultyAdapter);
        c_fac.setDropDownBackgroundResource(R.color.colorPrimary);
        c_fac.showDropDown();
    }

    /**
     * This function is to display the list of lab slot available for the selected faculty
     */
    private void setEmbLabSlot() {
        ArrayList<String> labSlots=new ArrayList<String>();
        for(int i=0;i<courses.size();i++){
            if(courses.get(i).getCrscd().equals(til_name.getHint().toString())  && courses.get(i).getFaculty().equals(c_fac.getText().toString()) && courses.get(i).getSlot().contains("L")){
                labSlots.add(courses.get(i).getSlot());
            }
        }
        eloSlotAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,labSlots);
        c_lslot.setThreshold(0);
        c_lslot.setAdapter(eloSlotAdapter);
        c_lslot.setDropDownBackgroundResource(R.color.colorPrimary);
        c_lslot.showDropDown();
    }

}
