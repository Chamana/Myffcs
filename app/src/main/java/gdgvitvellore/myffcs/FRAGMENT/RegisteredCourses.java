package gdgvitvellore.myffcs.FRAGMENT;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import gdgvitvellore.myffcs.API.ConnectAPI;
import gdgvitvellore.myffcs.CARDVIEWS.CoursesCardView;
import gdgvitvellore.myffcs.LOGIN.MainActivity;
import gdgvitvellore.myffcs.GSON.RegisteredCoursesResponse;
import gdgvitvellore.myffcs.R;

/**
 * Created by chaman on 19/6/17.
 */

public class RegisteredCourses extends Fragment implements ConnectAPI.ServerAuthenticateListener {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LinearLayout rl_tv;
    TextView tot_cred,name,tv_regno;
    Toolbar toolbar;
    ArrayList<String> c_id,c_code,c_type,c_slot,c_faculty,c_name,c_venue,c_mode;
    ArrayList<Integer> c_cred;
    ConnectAPI connectAPI;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView more,empty;
    ProgressDialog progressbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.registered_course,container,false);
        sharedPreferences=getActivity().getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        connectAPI=new ConnectAPI(getContext());
        connectAPI.setServerAuthenticateListener(this);
        connectAPI.getRegisteredCourse(sharedPreferences.getString("uid",""));
        init(v);
        return v;
    }

    private void init(View v) {
        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.refreshLayout);
        recyclerView=(RecyclerView)v.findViewById(R.id.courses_recview);
        name=(TextView)v.findViewById(R.id.name);
        progressbar=new ProgressDialog(getContext());
        c_code=new ArrayList<String>();
        c_type=new ArrayList<String>();
        c_slot=new ArrayList<String>();
        c_faculty=new ArrayList<String>();
        c_name=new ArrayList<String>();
        c_venue=new ArrayList<String>();
        c_id=new ArrayList<String>();
        c_mode=new ArrayList<String>();
        c_cred=new ArrayList<Integer>();
        rl_tv=(LinearLayout)v.findViewById(R.id.rl_tv);
        tot_cred=(TextView)v.findViewById(R.id.tv_totCred);
        recyclerView.setHasFixedSize(true);
        toolbar=(Toolbar)v.findViewById(R.id.toolbar);
        more=(ImageView)v.findViewById(R.id.popupMenu);
        tv_regno=(TextView)v.findViewById(R.id.regno);
        empty=(ImageView)v.findViewById(R.id.empty);
        editor=sharedPreferences.edit();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.YELLOW,Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContents();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(getContext(),more);
                popupMenu.getMenuInflater().inflate(R.menu.menu_items,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.aboutUs:
                                Toast.makeText(getContext(), "About Us", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.logout:
                                editor.putBoolean("isLoggedIn",false);
                                editor.commit();
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_items,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.aboutUs:
                Toast.makeText(getContext(), "About Us", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshContents() {
        /**progressbar.setMessage("Refreshing..");
         progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         progressbar.show();**/
        connectAPI.getRegisteredCourse(sharedPreferences.getString("uid",""));
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshContents();
    }

    @Override
    public void onRequestInitiated(int code) {

    }

    @Override
    public void onRequestCompleted(int code, Object result) {
        if(code==ConnectAPI.registered_code){
            c_id.clear();
            c_slot.clear();
            c_code.clear();
            c_name.clear();
            c_venue.clear();
            c_type.clear();
            c_faculty.clear();
            c_mode.clear();
            c_cred.clear();
            RegisteredCoursesResponse registeredCoursesResponse=(RegisteredCoursesResponse)result;
            editor.putString("name",registeredCoursesResponse.getName());
            editor.putString("regno",registeredCoursesResponse.getRegno());
            editor.commit();
            tv_regno.setText(sharedPreferences.getString("regno",""));
            name.setText("Welcome "+sharedPreferences.getString("name",""));
            int totCred=registeredCoursesResponse.getTotal_cred();
            tot_cred.setText(String.valueOf(totCred));
            String sample=registeredCoursesResponse.getMessage().toLowerCase();
            int size = registeredCoursesResponse.getAllotedCourse().size();
            if(registeredCoursesResponse.getMessage().contains("no")){
                empty.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);
            }
            else{
                empty.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                for (int i = 0; i < size; i++) {
                    c_id.add(registeredCoursesResponse.getAllotedCourse().get(i).get_id());
                    c_code.add(registeredCoursesResponse.getAllotedCourse().get(i).getCrscd());
                    c_name.add(registeredCoursesResponse.getAllotedCourse().get(i).getCrsnm());
                    c_faculty.add(registeredCoursesResponse.getAllotedCourse().get(i).getFaculty());
                    c_slot.add(registeredCoursesResponse.getAllotedCourse().get(i).getSlot());
                    c_type.add(registeredCoursesResponse.getAllotedCourse().get(i).getType());
                    c_venue.add(registeredCoursesResponse.getAllotedCourse().get(i).getVenue());
                    c_mode.add(registeredCoursesResponse.getAllotedCourse().get(i).getMode());
                    c_cred.add(registeredCoursesResponse.getAllotedCourse().get(i).getCredits());
                }
                recyclerView.setAdapter(new CoursesCardView(c_code, c_type, c_slot, c_name, c_faculty, c_venue, c_id, c_mode, c_cred));
            }
            swipeRefreshLayout.setRefreshing(false);
            //progressbar.dismiss();
        }
    }

    @Override
    public void onRequestError(int code, Object result) {

    }

}
