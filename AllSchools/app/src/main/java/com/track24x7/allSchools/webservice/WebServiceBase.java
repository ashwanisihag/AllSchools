package com.track24x7.allSchools.webservice;

/**
 * Programmed by Sihag.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.track24x7.allSchools.util.TagUtils;
import com.track24x7.allSchools.util.ToastClass;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Ashwani Sihag on 28-12-2016.
 */

public class WebServiceBase extends AsyncTask<String, Void, String> {
    ArrayList<NameValuePair> nameValuePairs;
    Map<String,String> headers;
    String jResult;
    Context context;
    Object object;
    static HttpClient httpClient;
    static HttpPost httppost;
    static HttpResponse response;
    static BufferedReader bufferedReader;
    InputStream is;
    ProgressDialog progressDialog;
    boolean isdialog = true;
    String msg;
    private final String TAG = getClass().getName();

    public WebServiceBase(ArrayList<NameValuePair> nameValuePairs,Map<String,String> headers, Context context, Object object, String msg, boolean isdialog) {
        this.nameValuePairs = nameValuePairs;
        this.object = object;
        this.context = context;
        this.headers=headers;
        this.msg = msg;
        this.isdialog = isdialog;
        String nmv = "";
        for (NameValuePair nameValuePair : nameValuePairs) {
            nmv = nmv + nameValuePair.getName() + " : " + nameValuePair.getValue() + "\n";
        }
        Log.d(TagUtils.getTag(), "nmv:-" + nmv);
        Log.d(TAG, this.toString());
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isdialog) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            jResult = httpCall(params[0], nameValuePairs,headers);
            Log.d(TagUtils.getTag(), msg + ":-" + jResult);
        } catch (Exception e) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            e.printStackTrace();
        }
        return jResult;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (s != null && s.length() > 0) {
            if (object != null) {
                WebServicesCallBack mcallback = (WebServicesCallBack) object;
                mcallback.onGetMsg(msg, s);
            } else {
                ToastClass.showShortToast(context, "Something went wrong");
            }
        } else {
            ToastClass.showShortToast(context, "No Internet");
        }

    }


    public static String httpCall(String url, ArrayList<NameValuePair> postParameters, Map<String, String> headers) {
        String result = "";
        try {
            httpClient = new DefaultHttpClient();
            httppost = new HttpPost(url);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    // do stuff
                    httppost.addHeader(key, value);
                }
            }

            httppost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
            Log.d(TagUtils.getTag(), "httppost url:-" + httppost.getURI());
//            Log.d(TagUtils.getTag(),"httppost:-"+httppost.toString());
            // Execute HTTP Post Request
            response = httpClient.execute(httppost);

            //converting response into string
            result = convertToString(response);
            return result;
        } catch (IOException e) {
            Log.i("Io", e.toString());

            return "";
        }
    }

    private static String convertToString(HttpResponse response) {

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            String LineSeparator = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + LineSeparator);
            }
            bufferedReader.close();
            return stringBuffer.toString();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }

    @Override
    public String toString() {
        return "WebServiceBase{" +
                "nameValuePairs=" + nameValuePairs +
                ", jResult='" + jResult + '\'' +
                ", context=" + context +
                ", object=" + object +
                ", is=" + is +
                ", progressDialog=" + progressDialog +
                ", isdialog=" + isdialog +
                ", msg='" + msg + '\'' +
                ", TAG='" + TAG + '\'' +
                '}';
    }
}
