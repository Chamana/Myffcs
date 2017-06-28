package gdgvitvellore.myffcs.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gdgvitvellore.myffcs.API.ConnectAPI;
import gdgvitvellore.myffcs.Utils.AcCardAdapter;
import gdgvitvellore.myffcs.Actors.CourseResponse;
import gdgvitvellore.myffcs.R;

/**
 * Created by chaman on 11/6/17.
 */

public class AddCoursesFragmet extends Fragment implements ConnectAPI.ServerAuthenticateListener, SearchView.OnQueryTextListener {
    ConnectAPI connectAPI;
    ArrayList<String> crscd,crsnm,unique_crscd,unique_crsnm;
    RecyclerView recyclerView;
    ArrayList<String> sample,UniqueCodeList;
    SearchView searchView;
    Toolbar toolbar;
    int j=0;
    RelativeLayout progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.offeredcourses_addfrag,container,false);
        connectAPI=new ConnectAPI(getContext());
        connectAPI.setServerAuthenticateListener(this);
        connectAPI.offeredCourse();
        toolbar=(Toolbar)v.findViewById(R.id.toolbar);
        toolbar.setTitle("Add Course");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        searchView=(SearchView)v.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by code/name");
        searchView.setOnQueryTextListener(this);
        EditText searchEditText = (EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.WHITE);
        searchEditText.setHintTextColor(Color.WHITE);
        crscd=new ArrayList<String>();
        crsnm=new ArrayList<String>();
        unique_crscd=new ArrayList<String>();
        unique_crsnm=new ArrayList<String>();
        sample=new ArrayList<String>();
        UniqueCodeList=new ArrayList<String>();
        progressDialog=(RelativeLayout)v.findViewById(R.id.loading);
        recyclerView=(RecyclerView)v.findViewById(R.id.recview_courses);
        return v;
    }

    @Override
    public void onRequestInitiated(int code) {
        if(code==ConnectAPI.offerdcourses_code){

        }
    }

    @Override
    public void onRequestCompleted(int code, Object result) {
        if(code==ConnectAPI.offerdcourses_code){
            List<CourseResponse> course=(List<CourseResponse>)result;
            int size=course.size();
            for(int i=0;i<size;i++){
                sample.add(course.get(i).getCrscd()+"-"+course.get(i).getCrsnm());
            }
            Set<String> uniqueCodes=new HashSet<String>(sample);
            UniqueCodeList=new ArrayList<String>(uniqueCodes);
            Collections.sort(UniqueCodeList);
            for (int i=0;i<UniqueCodeList.size();i++){
                String s[]=UniqueCodeList.get(i).split("-");
                unique_crscd.add(s[0]);
                unique_crsnm.add(s[1]);
            }
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new AcCardAdapter(unique_crscd,unique_crsnm));
            progressDialog.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestError(int code, Object result) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
         if(newText == null|| newText.trim().isEmpty()){
            resetSearch();
             return false;
         }
        List<String> filteredValues=new ArrayList<String>(UniqueCodeList);
        for(String value:UniqueCodeList){
            if(!value.toLowerCase().contains(newText.toLowerCase())){
                filteredValues.remove(value);
            }
        }
        ArrayList<String> filteredCode=new ArrayList<String>();
        ArrayList<String> filteredName=new ArrayList<String>();
        for (int i=0;i<filteredValues.size();i++){
            String s[]=filteredValues.get(i).split("-");
            filteredCode.add(s[0]);
            filteredName.add(s[1]);
        }
        recyclerView.setAdapter(new AcCardAdapter(filteredCode,filteredName));
        return false;
    }

    private void resetSearch() {
        recyclerView.setAdapter(new AcCardAdapter(unique_crscd,unique_crsnm));
    }
}
