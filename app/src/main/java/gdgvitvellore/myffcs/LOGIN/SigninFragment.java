package gdgvitvellore.myffcs.LOGIN;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import gdgvitvellore.myffcs.API.ConnectAPI;
import gdgvitvellore.myffcs.Activities.HomeActivity;
import gdgvitvellore.myffcs.GSON.SigninResponse;
import gdgvitvellore.myffcs.R;

/**
 * Created by chaman on 8/6/17.
 */

public class SigninFragment extends Fragment implements ConnectAPI.ServerAuthenticateListener {

    TextInputEditText regno,pass;
    String regno_st,pass_st;
    Button signin;
    ConnectAPI connectApi;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.signin_activity,parent,false);
        regno=(TextInputEditText)v.findViewById(R.id.regno_et);
        pass=(TextInputEditText)v.findViewById(R.id.pass_et);
        signin=(Button)v.findViewById(R.id.signin_btn);
        sharedPreferences=getActivity().getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        connectApi=new ConnectAPI(getContext());
        connectApi.setServerAuthenticateListener(this);
        progressDialog=new ProgressDialog(getContext());
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regno_st=regno.getText().toString();
                pass_st=pass.getText().toString();
                connectApi.login(regno_st,pass_st);
            }
        });
        return v;
    }

    @Override
    public void onRequestInitiated(int code) {
        if(code==ConnectAPI.login_code){
            progressDialog.setMessage("Logging In.Please Wait");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }
    }

    @Override
    public void onRequestCompleted(int code, Object result) {
        if(code==ConnectAPI.login_code){
           SigninResponse signinResponse=(SigninResponse)result;
            if(signinResponse.getStatus().equals("false")){
                editor.putBoolean("isLoggedIn",false);
                editor.commit();
                Snackbar.make(getView(),"Invalid login credentials",Snackbar.LENGTH_LONG).show();
            }
            if(signinResponse.getStatus().equals("true")){
                editor.putBoolean("isLoggedIn",true);
                editor.commit();
                editor.putString("uid",signinResponse.getUid());
                editor.commit();
                Snackbar.make(getView(),signinResponse.getMessage(),Snackbar.LENGTH_LONG).show();
                getActivity().startActivity(new Intent(getActivity(),HomeActivity.class));
            }
            progressDialog.dismiss();
        }
    }

    @Override
    public void onRequestError(int code, Object result) {

    }

}
