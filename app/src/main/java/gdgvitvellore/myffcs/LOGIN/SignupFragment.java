package gdgvitvellore.myffcs.LOGIN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import gdgvitvellore.myffcs.API.ConnectAPI;
import gdgvitvellore.myffcs.Activities.HomeActivity;
import gdgvitvellore.myffcs.GSON.RegisterResponse;
import gdgvitvellore.myffcs.R;

/**
 * Created by chaman on 8/6/17.
 */

public class SignupFragment extends Fragment implements ConnectAPI.ServerAuthenticateListener{

    TextInputEditText name_et,regno_et,password_et;
    Button signup_btn;
    ConnectAPI connectApi;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.signup_activity,container,false);
        name_et=(TextInputEditText)v.findViewById(R.id.name_et);
        regno_et=(TextInputEditText)v.findViewById(R.id.regno_et);
        password_et=(TextInputEditText)v.findViewById(R.id.pass_et);
        signup_btn=(Button)v.findViewById(R.id.signup_btn);
        sharedPreferences=getActivity().getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        connectApi=new ConnectAPI(getContext());
        connectApi.setServerAuthenticateListener(this);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name_et.getText()) || TextUtils.isEmpty(regno_et.getText()) || TextUtils.isEmpty(password_et.getText())){
                    Snackbar.make(getView(),"Please enter all the details",Snackbar.LENGTH_LONG).show();
                }
                else{
                    String name=name_et.getText().toString();
                    String regno=regno_et.getText().toString();
                    String pass=password_et.getText().toString();
                    connectApi.signup(name,regno,pass);
                }
            }
        });
        return v;
    }

    @Override
    public void onRequestInitiated(int code) {

    }

    @Override
    public void onRequestCompleted(int code, Object result) {
    if(code==ConnectAPI.signup_code){
        RegisterResponse registerResponse=(RegisterResponse)result;
        if(registerResponse.getStatus().equals("done")){
            Snackbar.make(getView(),"Successfully signed up.",Snackbar.LENGTH_LONG).show();
            editor=sharedPreferences.edit();
            editor.putBoolean("isLoggedIn",true);
            editor.commit();
            getActivity().startActivity(new Intent(getActivity(), HomeActivity.class));
        }
    }
    }

    @Override
    public void onRequestError(int code, Object result) {
       /** if(code==ConnectAPI.signup_code){
            if(result.equals("failed")){
                Snackbar.make(getView(),"Signup Failed.",Snackbar.LENGTH_LONG).show();
                name_et.setText("");
                regno_et.setText("");
                password_et.setText("");
                name_et.requestFocus();
            }
        }**/
    }
}
