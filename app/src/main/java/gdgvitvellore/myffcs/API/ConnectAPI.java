package gdgvitvellore.myffcs.API;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gdgvitvellore.myffcs.GSON.CourseResponse;
import gdgvitvellore.myffcs.GSON.DeleteResponse;
import gdgvitvellore.myffcs.GSON.InsertResponse;
import gdgvitvellore.myffcs.GSON.RegisterResponse;
import gdgvitvellore.myffcs.GSON.RegisteredCoursesResponse;
import gdgvitvellore.myffcs.GSON.SigninResponse;

/**
 * Created by chaman on 8/6/17.
 */

public class ConnectAPI {
    Context context;
    ServerAuthenticateListener serverAuthenticateListener;
    public static final int login_code=1;
    public static final int signup_code=2;
    public static final int offerdcourses_code=3;
    public static final int registered_code=4;
    public static final int insertCourse_code=5;
    public static final int insertEmbeddedCourse_code=6;
    public static final int insertEmbTLPCourse_code=7;
    public static final int delete_code=8;
    public static final String apiKey="Bp9MXU9kOVivgoiHsUiTpa0Z19nMDRVn";


    public ConnectAPI(Context ctx){
        this.context=ctx;
    }

    public void login(final String regno,final String password){
        if(serverAuthenticateListener!=null){
            serverAuthenticateListener.onRequestInitiated(login_code);
            StringRequest loginRequest=new StringRequest(Request.Method.POST,APIConstant.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson=new Gson();
                            SigninResponse signinResponse=gson.fromJson(response,SigninResponse.class);
                            serverAuthenticateListener.onRequestCompleted(login_code,signinResponse);
                        }
                    },null){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("username",regno);
                    params.put("password",password);
                    return params;
                }
            };
            RequestQueue queue= Volley.newRequestQueue(context);
            queue.add(loginRequest);
        }
    }

    public void signup(final String name,final String regno,final String password){
        if(serverAuthenticateListener!=null){
            serverAuthenticateListener.onRequestInitiated(signup_code);
            StringRequest registerRequest=new StringRequest(Request.Method.POST, APIConstant.REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson=new Gson();
                            RegisterResponse registerResponse=gson.fromJson(response,RegisterResponse.class);
                            serverAuthenticateListener.onRequestCompleted(signup_code,registerResponse);
                        }
                    }, null){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("name",name);
                    params.put("regno",regno);
                    params.put("password",password);
                    params.put("type","app");
                    return params;
                }
            };
            RequestQueue queue=Volley.newRequestQueue(context);
            queue.add(registerRequest);
        }
    }

    public void offeredCourse() {
        if(serverAuthenticateListener!=null){
            serverAuthenticateListener.onRequestInitiated(offerdcourses_code);
            StringRequest courseRequest=new StringRequest(Request.Method.GET, APIConstant.GETDETAILS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson=new Gson();
                        Type collectionType=new TypeToken<List<CourseResponse>>(){}.getType();
                        List<CourseResponse> courseResponse=gson.fromJson(response,collectionType);
                        serverAuthenticateListener.onRequestCompleted(offerdcourses_code,courseResponse);
                    }
                },null){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return super.getParams();
                }
            };
        RequestQueue queue=Volley.newRequestQueue(context);
        queue.add(courseRequest);
        }
    }

    public void setServerAuthenticateListener(ServerAuthenticateListener listener){
        serverAuthenticateListener=listener;
    }

    public void getRegisteredCourse(final String uid) {
        if(serverAuthenticateListener!=null){
            serverAuthenticateListener.onRequestInitiated(registered_code);
            StringRequest retrieveCourses=new StringRequest(Request.Method.POST, APIConstant.REGISTEREDCOURSES_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson=new Gson();
                            RegisteredCoursesResponse registeredCoursesResponse=gson.fromJson(response,RegisteredCoursesResponse.class);
                            serverAuthenticateListener.onRequestCompleted(registered_code,registeredCoursesResponse);

                        }
                    }, null){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("uid",uid);
                    params.put("apiKey",apiKey);
                    return params;
                }
            };
            RequestQueue queue=Volley.newRequestQueue(context);
            queue.add(retrieveCourses);
        }
    }

    public void insert(final String uid, final String selectedCourseId) {
        if(serverAuthenticateListener!=null){
            serverAuthenticateListener.onRequestInitiated(insertCourse_code);
            StringRequest insertRequest=new StringRequest(Request.Method.POST, APIConstant.INSERTCOURSE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson=new Gson();
                    InsertResponse insertResponse=gson.fromJson(response,InsertResponse.class);
                    serverAuthenticateListener.onRequestCompleted(insertCourse_code,insertResponse);
                }
            },null){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("uid",uid);
                    params.put("appKey",apiKey);
                    params.put("courseId",selectedCourseId);
                    return params;
                }
            };
            RequestQueue queue=Volley.newRequestQueue(context);
            queue.add(insertRequest);
        }
    }

    public void delete(final String uid, final String id) {
        if(serverAuthenticateListener!=null){
            serverAuthenticateListener.onRequestInitiated(delete_code);
            StringRequest deleteRequest=new StringRequest(Request.Method.POST, APIConstant.DELETECOURSE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson=new Gson();
                            DeleteResponse deleteResponse=gson.fromJson(response,DeleteResponse.class);
                            serverAuthenticateListener.onRequestCompleted(delete_code,deleteResponse);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("uid",uid);
                    params.put("appKey",apiKey);
                    params.put("courseId",id);
                    return params;
                }
            };
            RequestQueue queue=Volley.newRequestQueue(context);
            queue.add(deleteRequest);
        }
    }

    public void insertEmbedded(final String uid,final String id,final String id1){
        final String[] status = new String[1];
        if(serverAuthenticateListener!=null){
            serverAuthenticateListener.onRequestInitiated(insertEmbeddedCourse_code);
            StringRequest insertRequest=new StringRequest(Request.Method.POST, APIConstant.INSERTCOURSE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject json=new JSONObject(response);
                                status[0] =json.getString("status");
                                if(status[0]=="true"){
                                    StringRequest insertRequest1=new StringRequest(Request.Method.POST, APIConstant.INSERTCOURSE_URL,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response1) {
                                                    try {
                                                        JSONObject json1=new JSONObject(response1);
                                                        if(json1.getString("status").equals("true")){
                                                            Gson gson=new Gson();
                                                            InsertResponse insertResponse=gson.fromJson(response1,InsertResponse.class);
                                                            serverAuthenticateListener.onRequestCompleted(insertCourse_code,insertResponse);
                                                        }else{
                                                            StringRequest deleteRequest=new StringRequest(Request.Method.POST, APIConstant.DELETECOURSE_URL,
                                                                    new Response.Listener<String>() {
                                                                        @Override
                                                                        public void onResponse(String delResp) {
                                                                            try {
                                                                                JSONObject json2=new JSONObject(delResp);
                                                                                if(json2.equals("true")){
                                                                                    //do nothing
                                                                                }
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }

                                                                        }
                                                                    }, null){
                                                                @Override
                                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                                    Map<String,String> params=new HashMap<>();
                                                                    params.put("uid",uid);
                                                                    params.put("appKey",apiKey);
                                                                    params.put("courseId",id);
                                                                    return params;
                                                                }
                                                            };
                                                            RequestQueue queue=Volley.newRequestQueue(context);
                                                            queue.add(deleteRequest);
                                                            Gson gson=new Gson();
                                                            InsertResponse insertResponse1=gson.fromJson(response1,InsertResponse.class);
                                                            serverAuthenticateListener.onRequestCompleted(insertEmbeddedCourse_code,insertResponse1);
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            },null){
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String,String> params1=new HashMap<>();
                                            params1.put("uid",uid);
                                            params1.put("appKey",apiKey);
                                            params1.put("courseId",id1);
                                            return params1;
                                        }
                                    };
                                    RequestQueue queue=Volley.newRequestQueue(context);
                                    queue.add(insertRequest1);
                                }else{
                                    Gson gson=new Gson();
                                    InsertResponse insertRes=gson.fromJson(response,InsertResponse.class);
                                    serverAuthenticateListener.onRequestCompleted(insertEmbeddedCourse_code,insertRes);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },null){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("uid",uid);
                    params.put("appKey",apiKey);
                    params.put("courseId",id);
                    return params;
                }
            };
            RequestQueue queue=Volley.newRequestQueue(context);
            queue.add(insertRequest);

        }
    }

    public void insertEmbThLoPjCourse(final String uid, final String selectedCourseId, final String selectedCourseId1, final String selectedCourseId2) {
        final String[] status = new String[1];
        StringRequest insertRequest=new StringRequest(Request.Method.POST, APIConstant.INSERTCOURSE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    status[0] =jsonObject.getString("status");
                    if(status[0].equals("true")){
                        StringRequest insertRequest1=new StringRequest(Request.Method.POST, APIConstant.INSERTCOURSE_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response1) {
                                        try {
                                            JSONObject jsonObject=new JSONObject(response1);
                                            if(jsonObject.getString("status").equals("true")){
                                                StringRequest insertRequest2=new StringRequest(Request.Method.POST, APIConstant.INSERTCOURSE_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response2) {
                                                                try {
                                                                    JSONObject jsonObject1=new JSONObject(response2);
                                                                    if(jsonObject1.getString("status").equals("true")){
                                                                        Gson gson=new Gson();
                                                                        InsertResponse insertResponses=gson.fromJson(response2,InsertResponse.class);
                                                                        serverAuthenticateListener.onRequestCompleted(insertEmbTLPCourse_code,insertResponses);
                                                                    }
                                                                    else{
                                                                        StringRequest deleteRequest=new StringRequest(Request.Method.POST, APIConstant.DELETECOURSE_URL,
                                                                                new Response.Listener<String>() {
                                                                                    @Override
                                                                                    public void onResponse(String delResp) {
                                                                                        try {
                                                                                            JSONObject json2=new JSONObject(delResp);
                                                                                            if(json2.equals("true")){
                                                                                                StringRequest deleteRequest1=new StringRequest(Request.Method.POST, APIConstant.DELETECOURSE_URL,
                                                                                                        new Response.Listener<String>() {
                                                                                                            @Override
                                                                                                            public void onResponse(String delResp) {
                                                                                                                try {
                                                                                                                    JSONObject json2=new JSONObject(delResp);
                                                                                                                    if(json2.equals("true")){
                                                                                                                        //do nothing
                                                                                                                    }
                                                                                                                } catch (JSONException e) {
                                                                                                                    e.printStackTrace();
                                                                                                                }

                                                                                                            }
                                                                                                        }, null){
                                                                                                    @Override
                                                                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                                                                        Map<String,String> params2=new HashMap<>();
                                                                                                        params2.put("uid",uid);
                                                                                                        params2.put("appKey",apiKey);
                                                                                                        params2.put("courseId",selectedCourseId1);
                                                                                                        return params2;
                                                                                                    }
                                                                                                };
                                                                                                RequestQueue queue=Volley.newRequestQueue(context);
                                                                                                queue.add(deleteRequest1);
                                                                                            }
                                                                                        } catch (JSONException e) {
                                                                                            e.printStackTrace();
                                                                                        }

                                                                                    }
                                                                                }, null){
                                                                            @Override
                                                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                                                Map<String,String> params2=new HashMap<>();
                                                                                params2.put("uid",uid);
                                                                                params2.put("appKey",apiKey);
                                                                                params2.put("courseId",selectedCourseId);
                                                                                return params2;
                                                                            }
                                                                        };
                                                                        RequestQueue queue=Volley.newRequestQueue(context);
                                                                        queue.add(deleteRequest);
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        },null){
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String,String> params3=new HashMap<>();
                                                        params3.put("uid",uid);
                                                        params3.put("appKey",apiKey);
                                                        params3.put("courseId",selectedCourseId2);
                                                        return params3;
                                                    }
                                                };
                                                RequestQueue queue2=Volley.newRequestQueue(context);
                                                queue2.add(insertRequest2);
                                            }
                                            else{
                                                StringRequest deleteRequest=new StringRequest(Request.Method.POST, APIConstant.DELETECOURSE_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String delResp) {
                                                                try {
                                                                    JSONObject json2=new JSONObject(delResp);
                                                                    if(json2.equals("true")){
                                                                        //do nothing
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }

                                                            }
                                                        }, null){
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String,String> params2=new HashMap<>();
                                                        params2.put("uid",uid);
                                                        params2.put("appKey",apiKey);
                                                        params2.put("courseId",selectedCourseId);
                                                        return params2;
                                                    }
                                                };
                                                RequestQueue queue=Volley.newRequestQueue(context);
                                                queue.add(deleteRequest);
                                                Gson gson=new Gson();
                                                InsertResponse insertResponse1=gson.fromJson(response1,InsertResponse.class);
                                                serverAuthenticateListener.onRequestCompleted(insertEmbeddedCourse_code,insertResponse1);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },null){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params1=new HashMap<>();
                                params1.put("uid",uid);
                                params1.put("appKey",apiKey);
                                params1.put("courseId",selectedCourseId1);
                                return params1;
                            }
                        };
                        RequestQueue queue1=Volley.newRequestQueue(context);
                        queue1.add(insertRequest1);
                    }else{
                        Gson gson=new Gson();
                        InsertResponse insertRes=gson.fromJson(response,InsertResponse.class);
                        serverAuthenticateListener.onRequestCompleted(insertEmbTLPCourse_code,insertRes);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("uid",uid);
                params.put("appKey",apiKey);
                params.put("courseId",selectedCourseId);
                return params;
            }
        };
        RequestQueue queue=Volley.newRequestQueue(context);
        queue.add(insertRequest);
    }


    public interface ServerAuthenticateListener {

        void onRequestInitiated(int code);

        void onRequestCompleted(int code,Object result);

        void onRequestError(int code, Object result);
    }
}
