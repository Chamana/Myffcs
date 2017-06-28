package gdgvitvellore.myffcs.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import gdgvitvellore.myffcs.API.ConnectAPI;
import gdgvitvellore.myffcs.Actors.RegisteredCoursesResponse;
import gdgvitvellore.myffcs.R;

/**
 * Created by chaman on 14/6/17.
 */

public class TimetableFragment extends Fragment implements ConnectAPI.ServerAuthenticateListener {

    public static int numDownload;
    ConnectAPI connectApi;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> c_code,c_slot,c_venue;
    RelativeLayout tt_rl;
    GridLayout tt_gl;
    Bitmap bitmap;
    PhotoView img;
    int width,height;
    TextView A1L1,A1L14,B1L7,B1L20,C1L13,C1L26,D1L3,D1L19,E1L9,E1L25,F1,F1L2,G1L8,G1L21,A2L31,A2L44,B2L37,B2L50,C2L43,C2L56,D2L33,D2L49,E2L39,E2L55,F2L32,F2L45,G2L38,G2L51,TA1L27,TA2L57,TAA1L11,TAA2L41,TB1L4,TB2L34,TBB2L47,TC1L10,TC2L40,TCC2L53,TCC1L23,TD1L29,TD2L46,TDD2L59,TE1L22,TE2L52,TF1L28,TF2L58,TG1L5,TG2L35;
    TextView L6,L12,L24,L30,L36,L42,L48,L54,L60;
    FloatingActionButton show,download,share;
    Animation FabOpen,FabClose,RotateClock,RotateAntiClock;
    TextView tt_title;
    Boolean isOpen=false;
    ProgressDialog progressDialog;
    CardView googleProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.timetable_fragment,container,false);
        progressDialog=new ProgressDialog(getContext());
        googleProgressBar=(CardView) v.findViewById(R.id.google_progress);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init(v);
        setInit(v);
        return v;
    }

    private void setInit(View v) {
        connectApi.getRegisteredCourse(sharedPreferences.getString("uid",""));
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        tt_rl.setDrawingCacheEnabled(true);
        tt_rl.buildDrawingCache(true);
        tt_gl.setVisibility(View.VISIBLE);
        try{
        tt_gl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tt_gl.setVisibility(View.VISIBLE);
                width=tt_gl.getWidth();
                height=tt_gl.getHeight();
                tt_gl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                tt_gl.setVisibility(View.GONE);
            }
        });
        }catch (Exception e){

        }

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen){
                    download.startAnimation(FabClose);
                    share.startAnimation(FabClose);
                    show.startAnimation(RotateAntiClock);
                    download.setClickable(false);
                    share.setClickable(false);
                    isOpen=false;
                }
                else{
                    download.startAnimation(FabOpen);
                    share.startAnimation(FabOpen);
                    show.startAnimation(RotateClock);
                    download.setClickable(true);
                    share.setClickable(true);
                    isOpen=true;
                }
            }
        });
    }

    private void init(View v) {
        connectApi=new ConnectAPI(getContext());
        sharedPreferences=getActivity().getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        connectApi.setServerAuthenticateListener(this);
        c_code=new ArrayList<String>();
        c_slot=new ArrayList<String>();
        c_venue=new ArrayList<String>();
        tt_rl=(RelativeLayout)v.findViewById(R.id.tt_rl);
        tt_gl=(GridLayout)v.findViewById(R.id.timetable_gl);
        img=(PhotoView)v.findViewById(R.id.img);
        tt_title=(TextView)v.findViewById(R.id.tt_title);
        A1L1 = (TextView) v.findViewById(R.id.A1L1);
        A1L14 = (TextView) v.findViewById(R.id.A1L14);
        B1L7 = (TextView) v.findViewById(R.id.B1L7);
        B1L20 = (TextView) v.findViewById(R.id.B1L20);
        C1L13 = (TextView) v.findViewById(R.id.C1L13);
        C1L26 = (TextView) v.findViewById(R.id.C1L26);
        D1L3 = (TextView) v.findViewById(R.id.D1L3);
        D1L19 = (TextView) v.findViewById(R.id.D1L19);
        E1L9 = (TextView) v.findViewById(R.id.E1L9);
        E1L25 = (TextView) v.findViewById(R.id.E1L25);
        F1 = (TextView) v.findViewById(R.id.F1);
        F1L2 = (TextView) v.findViewById(R.id.F1L2);
        G1L8 = (TextView) v.findViewById(R.id.G1L8);
        G1L21 = (TextView) v.findViewById(R.id.G1L21);
        A2L31 = (TextView) v.findViewById(R.id.A2L31);
        A2L44 = (TextView) v.findViewById(R.id.A2L44);
        B2L37 = (TextView) v.findViewById(R.id.B2L37);
        B2L50 = (TextView) v.findViewById(R.id.B2L50);
        C2L43 = (TextView) v.findViewById(R.id.C2L43);
        C2L56 = (TextView) v.findViewById(R.id.C2L56);
        D2L33 = (TextView) v.findViewById(R.id.D2L33);
        D2L49 = (TextView) v.findViewById(R.id.D2L49);
        E2L39 = (TextView) v.findViewById(R.id.E2L39);
        E2L55 = (TextView) v.findViewById(R.id.E2L55);
        F2L32 = (TextView) v.findViewById(R.id.F2L32);
        F2L45 = (TextView) v.findViewById(R.id.F2L45);
        G2L38 = (TextView) v.findViewById(R.id.G2L38);
        G2L51 = (TextView) v.findViewById(R.id.G2L51);
        TA1L27 = (TextView) v.findViewById(R.id.TA1L27);
        TA2L57 = (TextView) v.findViewById(R.id.TA2L57);
        TAA1L11 = (TextView) v.findViewById(R.id.TAA1L11);
        TAA2L41=(TextView)v.findViewById(R.id.TAA2L41);
        TB1L4 = (TextView) v.findViewById(R.id.TB1L4);
        TB2L34 = (TextView) v.findViewById(R.id.TB2L34);
        TBB2L47 = (TextView) v.findViewById(R.id.TBB2L47);
        TC1L10 = (TextView) v.findViewById(R.id.TC1L10);
        TC2L40 = (TextView) v.findViewById(R.id.TC2L40);
        TCC2L53 = (TextView) v.findViewById(R.id.TCC2L53);
        TCC1L23 = (TextView) v.findViewById(R.id.TCC1L23);
        TD1L29 = (TextView) v.findViewById(R.id.TD1L29);
        TD2L46 = (TextView) v.findViewById(R.id.TD2L46);
        TDD2L59 = (TextView) v.findViewById(R.id.TDD2L59);
        TE1L22 = (TextView) v.findViewById(R.id.TE1L22);
        TE2L52 = (TextView) v.findViewById(R.id.TE2L52);
        TF1L28 = (TextView) v.findViewById(R.id.TF1L28);
        TF2L58 = (TextView) v.findViewById(R.id.TF2L58);
        TG1L5 = (TextView) v.findViewById(R.id.TG1L5);
        TG2L35 = (TextView) v.findViewById(R.id.TG2L35);
        L6=(TextView)v.findViewById(R.id.L6);
        L12=(TextView)v.findViewById(R.id.L12);
        L24=(TextView)v.findViewById(R.id.L24);
        L30=(TextView)v.findViewById(R.id.L30);
        L36=(TextView)v.findViewById(R.id.L36);
        L42=(TextView)v.findViewById(R.id.L42);
        L48=(TextView)v.findViewById(R.id.L48);
        L54=(TextView)v.findViewById(R.id.L54);
        L60=(TextView)v.findViewById(R.id.L60);
        show=(FloatingActionButton)v.findViewById(R.id.show_fab);
        download=(FloatingActionButton)v.findViewById(R.id.download_fab);
        share=(FloatingActionButton)v.findViewById(R.id.share_fab);
        FabOpen= AnimationUtils.loadAnimation(getContext(),R.anim.fab_open);
        FabClose= AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        RotateClock= AnimationUtils.loadAnimation(getContext(),R.anim.rotate_clockwise);
        RotateAntiClock= AnimationUtils.loadAnimation(getContext(),R.anim.rotate_anticlockwise);
    }

    @Override
    public void onRequestInitiated(int code) {
        googleProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestCompleted(int code, Object result) {
        if(code== ConnectAPI.registered_code){
            RegisteredCoursesResponse registeredCourseResponse=(RegisteredCoursesResponse)result;
            int size=registeredCourseResponse.getAllotedCourse().size();
            tt_title.setText(sharedPreferences.getString("name",""));
            c_code.clear();
            c_slot.clear();
            c_venue.clear();
            for(int i=0;i<size;i++){
                c_code.add(registeredCourseResponse.getAllotedCourse().get(i).getCrscd());
                c_slot.add(registeredCourseResponse.getAllotedCourse().get(i).getSlot());
                c_venue.add(registeredCourseResponse.getAllotedCourse().get(i).getVenue());
            }
            for(int i=0;i<c_code.size();i++){
                String[] tmpslot=c_slot.get(i).split("\\+");
                for(int j=0;j<tmpslot.length;j++) {
                    switch (tmpslot[j]) {
                        case "A1":
                            A1L1.setBackgroundResource(R.drawable.selected_bgk);
                            A1L14.setBackgroundResource(R.drawable.selected_bgk);
                            A1L1.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            A1L14.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "B1":
                            B1L7.setBackgroundResource(R.drawable.selected_bgk);
                            B1L7.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            B1L20.setBackgroundResource(R.drawable.selected_bgk);
                            B1L20.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;

                        case "C1":
                            C1L13.setBackgroundResource(R.drawable.selected_bgk);
                            C1L13.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            C1L26.setBackgroundResource(R.drawable.selected_bgk);
                            C1L26.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "D1":
                            D1L3.setBackgroundResource(R.drawable.selected_bgk);
                            D1L3.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            D1L19.setBackgroundResource(R.drawable.selected_bgk);
                            D1L19.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "E1":
                            E1L9.setBackgroundResource(R.drawable.selected_bgk);
                            E1L9.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            E1L25.setBackgroundResource(R.drawable.selected_bgk);
                            E1L25.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "F1":
                            F1L2.setBackgroundResource(R.drawable.selected_bgk);
                            F1L2.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            F1.setBackgroundResource(R.drawable.selected_bgk);
                            F1.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "G1":
                            G1L8.setBackgroundResource(R.drawable.selected_bgk);
                            G1L8.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            G1L21.setBackgroundResource(R.drawable.selected_bgk);
                            G1L21.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TA1":
                            TA1L27.setBackgroundResource(R.drawable.selected_bgk);
                            TA1L27.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TB1":
                            TB1L4.setBackgroundResource(R.drawable.selected_bgk);
                            TB1L4.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TC1":
                            TC1L10.setBackgroundResource(R.drawable.selected_bgk);
                            TC1L10.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TD1":
                            TD1L29.setBackgroundResource(R.drawable.selected_bgk);
                            TD1L29.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TE1":
                            TE1L22.setBackgroundResource(R.drawable.selected_bgk);
                            TE1L22.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TF1":
                            TF1L28.setBackgroundResource(R.drawable.selected_bgk);
                            TF1L28.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TG1":
                            TG1L5.setBackgroundResource(R.drawable.selected_bgk);
                            TG1L5.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "A2":
                            A2L31.setBackgroundResource(R.drawable.selected_bgk);
                            A2L31.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            A2L44.setBackgroundResource(R.drawable.selected_bgk);
                            A2L44.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "B2":
                            B2L37.setBackgroundResource(R.drawable.selected_bgk);
                            B2L37.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            B2L50.setBackgroundResource(R.drawable.selected_bgk);
                            B2L50.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "C2":
                            C2L43.setBackgroundResource(R.drawable.selected_bgk);
                            C2L43.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            C2L56.setBackgroundResource(R.drawable.selected_bgk);
                            C2L56.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "D2":
                            D2L33.setBackgroundResource(R.drawable.selected_bgk);
                            D2L33.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            D2L49.setBackgroundResource(R.drawable.selected_bgk);
                            D2L49.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "E2":
                            E2L39.setBackgroundResource(R.drawable.selected_bgk);
                            E2L39.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            E2L55.setBackgroundResource(R.drawable.selected_bgk);
                            E2L55.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "F2":
                            F2L32.setBackgroundResource(R.drawable.selected_bgk);
                            F2L32.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            F2L45.setBackgroundResource(R.drawable.selected_bgk);
                            F2L45.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "G2":
                            G2L38.setBackgroundResource(R.drawable.selected_bgk);
                            G2L38.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            G2L51.setBackgroundResource(R.drawable.selected_bgk);
                            G2L51.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TA2":
                            TA2L57.setBackgroundResource(R.drawable.selected_bgk);
                            TA2L57.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TB2":
                            TB2L34.setBackgroundResource(R.drawable.selected_bgk);
                            TB2L34.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TC2":
                            TC2L40.setBackgroundResource(R.drawable.selected_bgk);
                            TC2L40.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TD2":
                            TD2L46.setBackgroundResource(R.drawable.selected_bgk);
                            TD2L46.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TE2":
                            TE2L52.setBackgroundResource(R.drawable.selected_bgk);
                            TE2L52.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TF2":
                            TF2L58.setBackgroundResource(R.drawable.selected_bgk);
                            TF2L58.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TG2":
                            TG2L35.setBackgroundResource(R.drawable.selected_bgk);
                            TG2L35.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TAA1":
                            TAA1L11.setBackgroundResource(R.drawable.selected_bgk);
                            TAA1L11.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TCC1":
                            TCC1L23.setBackgroundResource(R.drawable.selected_bgk);
                            TCC1L23.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TBB2":
                            TBB2L47.setBackgroundResource(R.drawable.selected_bgk);
                            TBB2L47.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TCC2":
                            TCC2L53.setBackgroundResource(R.drawable.selected_bgk);
                            TCC2L53.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "TDD2":
                            TDD2L59.setBackgroundResource(R.drawable.selected_bgk);
                            TDD2L59.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L1":
                            A1L1.setBackgroundResource(R.drawable.selected_bgk);
                            A1L1.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L2":
                            F1L2.setBackgroundResource(R.drawable.selected_bgk);
                            F1L2.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L3":
                            D1L3.setBackgroundResource(R.drawable.selected_bgk);
                            D1L3.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L4":
                            TB1L4.setBackgroundResource(R.drawable.selected_bgk);
                            TB1L4.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L5":
                            TG1L5.setBackgroundResource(R.drawable.selected_bgk);
                            TG1L5.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L6":
                            L6.setBackgroundResource(R.drawable.selected_bgk);
                            L6.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L7":
                            B1L7.setBackgroundResource(R.drawable.selected_bgk);
                            B1L7.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L8":
                            G1L8.setBackgroundResource(R.drawable.selected_bgk);
                            G1L8.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L9":
                            E1L9.setBackgroundResource(R.drawable.selected_bgk);
                            E1L9.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L10":
                            TC1L10.setBackgroundResource(R.drawable.selected_bgk);
                            TC1L10.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L11":
                            TAA1L11.setBackgroundResource(R.drawable.selected_bgk);
                            TAA1L11.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L12":
                            L12.setBackgroundResource(R.drawable.selected_bgk);
                            L12.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L13":
                            C1L13.setBackgroundResource(R.drawable.selected_bgk);
                            C1L13.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L14":
                            A1L14.setBackgroundResource(R.drawable.selected_bgk);
                            A1L14.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L15":
                            F1.setBackgroundResource(R.drawable.selected_bgk);
                            F1.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L19":
                            D1L19.setBackgroundResource(R.drawable.selected_bgk);
                            D1L19.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L20":
                            B1L20.setBackgroundResource(R.drawable.selected_bgk);
                            B1L20.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L21":
                            G1L21.setBackgroundResource(R.drawable.selected_bgk);
                            G1L21.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L22":
                            TE1L22.setBackgroundResource(R.drawable.selected_bgk);
                            TE1L22.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L23":
                            TCC1L23.setBackgroundResource(R.drawable.selected_bgk);
                            TCC1L23.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L24":
                            L24.setBackgroundResource(R.drawable.selected_bgk);
                            L24.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L25":
                            E1L25.setBackgroundResource(R.drawable.selected_bgk);
                            E1L25.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L26":
                            C1L26.setBackgroundResource(R.drawable.selected_bgk);
                            C1L26.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L27":
                            TA1L27.setBackgroundResource(R.drawable.selected_bgk);
                            TA1L27.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L28":
                            TF1L28.setBackgroundResource(R.drawable.selected_bgk);
                            TF1L28.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L29":
                            TD1L29.setBackgroundResource(R.drawable.selected_bgk);
                            TD1L29.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L30":
                            L30.setBackgroundResource(R.drawable.selected_bgk);
                            L30.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L31":
                            A2L31.setBackgroundResource(R.drawable.selected_bgk);
                            A2L31.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L32":
                            F2L32.setBackgroundResource(R.drawable.selected_bgk);
                            F2L32.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L33":
                            D2L33.setBackgroundResource(R.drawable.selected_bgk);
                            D2L33.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L34":
                            TB2L34.setBackgroundResource(R.drawable.selected_bgk);
                            TB2L34.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L35":
                            TG2L35.setBackgroundResource(R.drawable.selected_bgk);
                            TG2L35.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L36":
                            L36.setBackgroundResource(R.drawable.selected_bgk);
                            L36.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L37":
                            B2L37.setBackgroundResource(R.drawable.selected_bgk);
                            B2L37.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L38":
                            G2L38.setBackgroundResource(R.drawable.selected_bgk);
                            G2L38.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L39":
                            E2L39.setBackgroundResource(R.drawable.selected_bgk);
                            E2L39.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L40":
                            TC2L40.setBackgroundResource(R.drawable.selected_bgk);
                            TC2L40.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L41":
                            TAA2L41.setBackgroundResource(R.drawable.selected_bgk);
                            TAA2L41.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L42":
                            L42.setBackgroundResource(R.drawable.selected_bgk);
                            L42.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L43":
                            C2L43.setBackgroundResource(R.drawable.selected_bgk);
                            C2L43.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L44":
                            A2L44.setBackgroundResource(R.drawable.selected_bgk);
                            A2L44.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L45":
                            F2L45.setBackgroundResource(R.drawable.selected_bgk);
                            F2L45.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L46":
                            TD2L46.setBackgroundResource(R.drawable.selected_bgk);
                            TD2L46.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L47":
                            TBB2L47.setBackgroundResource(R.drawable.selected_bgk);
                            TBB2L47.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L48":
                            L48.setBackgroundResource(R.drawable.selected_bgk);
                            L48.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L49":
                            D2L49.setBackgroundResource(R.drawable.selected_bgk);
                            D2L49.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L50":
                            B2L50.setBackgroundResource(R.drawable.selected_bgk);
                            B2L50.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L51":
                            G2L51.setBackgroundResource(R.drawable.selected_bgk);
                            G2L51.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L52":
                            TE2L52.setBackgroundResource(R.drawable.selected_bgk);
                            TE2L52.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L53":
                            TCC2L53.setBackgroundResource(R.drawable.selected_bgk);
                            TCC2L53.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L54":
                            L54.setBackgroundResource(R.drawable.selected_bgk);
                            L54.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L55":
                            E2L55.setBackgroundResource(R.drawable.selected_bgk);
                            E2L55.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L56":
                            C2L56.setBackgroundResource(R.drawable.selected_bgk);
                            C2L56.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L57":
                            TA2L57.setBackgroundResource(R.drawable.selected_bgk);
                            TA2L57.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L58":
                            TF2L58.setBackgroundResource(R.drawable.selected_bgk);
                            TF2L58.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L59":
                            TDD2L59.setBackgroundResource(R.drawable.selected_bgk);
                            TDD2L59.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                        case "L60":
                            L60.setBackgroundResource(R.drawable.selected_bgk);
                            L60.setText(c_code.get(i).toString() + "\n" + tmpslot[j] + "\n" + c_venue.get(i).toString());
                            break;
                    }
                }
            }
            tt_rl.post(new Runnable() {
                @Override
                public void run() {
                    try {
                    tt_gl.setVisibility(View.VISIBLE);
                    bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    tt_rl.layout(0,0,tt_rl.getLayoutParams().width,tt_rl.getLayoutParams().height);
                    Canvas c=new Canvas(bitmap);
                    tt_rl.draw(c);
                    tt_gl.setVisibility(View.GONE);
                    tt_rl.setDrawingCacheEnabled(false);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(bitmap!=null){
                        img.setVisibility(View.VISIBLE);
                        img.setImageBitmap(bitmap);
                        img.invalidate();
                    }
                }
            });
            show.setVisibility(View.VISIBLE);
            numDownload=sharedPreferences.getInt("numDownload",0);
            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String root=Environment.getExternalStorageDirectory().toString();
                    File MyDir=new File(root+"/Myffcs");
                    MyDir.mkdirs();
                    String filename=sharedPreferences.getString("regno","")+numDownload+".jpg";
                    File new_file=new File(MyDir,filename);
                    try{
                        FileOutputStream fos=new FileOutputStream(new_file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
                        download.startAnimation(FabClose);
                        share.startAnimation(FabClose);
                        show.startAnimation(RotateAntiClock);
                        download.setClickable(false);
                        share.setClickable(false);
                        isOpen=false;
                        show.setClickable(false);
                        Snackbar.make(getView(),"Image Saved Successfully",Snackbar.LENGTH_LONG).show();
                        numDownload+=1;
                        editor=sharedPreferences.edit();
                        editor.putInt("numDownload",numDownload);
                        editor.commit();
                        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new_file)));
                        fos.flush();
                        fos.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE));
                    show.setClickable(true);
                }
            });
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent=new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("image/jpg");
                    Uri imgUri=Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),bitmap,sharedPreferences.getString("regno",""),"TimeTable screenshot"));
                    shareIntent.putExtra(Intent.EXTRA_STREAM,imgUri);
                    startActivity(Intent.createChooser(shareIntent,"Share Via"));
                }
            });
            googleProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestError(int code, Object result) {

    }

    @Override
    public void onResume() {
        super.onResume();
        connectApi.getRegisteredCourse(sharedPreferences.getString("uid",""));
    }
}
