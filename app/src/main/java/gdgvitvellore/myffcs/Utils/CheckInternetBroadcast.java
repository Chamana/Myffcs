package gdgvitvellore.myffcs.Utils;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

/**
 * Created by chaman on 16/6/17.
 */

public class CheckInternetBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(isConnected(context)==true){
            
        }else{

        }
    }

    public static boolean isConnected(Context ctx){
        int[] typeNetwork={ConnectivityManager.TYPE_MOBILE,ConnectivityManager.TYPE_WIFI};
        try{
            ConnectivityManager cm=(ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            for(int type:typeNetwork){
                NetworkInfo networkInfo=cm.getActiveNetworkInfo();
                if(networkInfo!=null && networkInfo.getState()== NetworkInfo.State.CONNECTED){
                    return true;

                }
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }
}
