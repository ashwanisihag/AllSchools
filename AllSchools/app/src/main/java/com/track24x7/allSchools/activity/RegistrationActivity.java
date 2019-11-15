package com.track24x7.allSchools.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.track24x7.allSchools.R;
import com.track24x7.allSchools.progress.ProgressHUD;
import com.track24x7.allSchools.util.Pref;
import com.track24x7.allSchools.util.StringUtils;
import com.track24x7.allSchools.util.TagUtils;
import com.track24x7.allSchools.util.ToastClass;
import com.track24x7.allSchools.webservice.ResponseListCallback;
import com.track24x7.allSchools.webservice.WebServiceBaseResponseList;
import com.track24x7.allSchools.webservice.WebServicesUrls;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends BaseMenuActivity implements DatePickerDialog.OnDateSetListener, com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {

    @BindView(R.id.registration_dob)
    EditText registration_dob;
    @BindView(R.id.registration_user)
    EditText registration_user;
    @BindView(R.id.registration_first_name)
    EditText registration_first_name;
    @BindView(R.id.registration_last_name)
    EditText registration_last_name;
    @BindView(R.id.registration_phone_number)
    EditText registration_phone_number;
    @BindView(R.id.registration_email)
    EditText registration_email;
    @BindView(R.id.registration_address)
    EditText registration_address;
    @BindView(R.id.registration_postal_code)
    EditText registration_postal_code;
    @BindView(R.id.registration_school)
    EditText registration_school;
    /*@BindView(R.id.registration_roll_no)
    EditText registration_roll_no;*/
    @BindView(R.id.registration_spinner_joining_year)
    Spinner registration_spinner_joining_year;
    @BindView(R.id.registration_spinner_leaving_year)
    Spinner registration_spinner_leaving_year;
    @BindView(R.id.registration_designation)
    EditText registration_designation;
    @BindView(R.id.registration_posting)
    EditText registration_posting;
    @BindView(R.id.registration_spinner_blood_group)
    Spinner registration_spinner_blood_group;
    @BindView(R.id.registration_password)
    EditText registration_password;
    @BindView(R.id.registration_confirm_password)
    EditText registration_confirm_password;
    @BindView(R.id.registration_spinner_Professions)
    Spinner registration_spinner_Professions;
    @BindView(R.id.registration_department)
    EditText registration_department;
    @BindView(R.id.registration_profileLink)
    EditText registration_profileLink;
    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.registration_spinner_country)
    Spinner registration_spinner_country;
    @BindView(R.id.registration_spinner_state)
    Spinner registration_spinner_state;
    @BindView(R.id.registration_city)
    EditText registration_city;
    @BindView(R.id.registration_checkbox_phoneVisible)
    CheckBox registration_checkbox_phoneVisible;
    @BindView(R.id.registration_checkbox_locationVisible)
    CheckBox registration_checkbox_locationVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        registration_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedEditText = registration_dob;
                openDatePicker();
            }
        });

        getSupportActionBar().setTitle("Registration");

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!registration_spinner_country.getSelectedItem().toString().equalsIgnoreCase("Select Country")) {
                    if (!registration_spinner_state.getSelectedItem().toString().equalsIgnoreCase("Select State")) {
                        if (!registration_spinner_joining_year.getSelectedItem().toString().equalsIgnoreCase(registration_spinner_leaving_year.getSelectedItem().toString())) {
                            if (checkValidEdit(registration_school, registration_first_name, registration_last_name, registration_phone_number, registration_user, registration_password, registration_confirm_password, registration_dob)) {
                                if (registration_password.getText().toString().equals(registration_confirm_password.getText().toString())) {
                                    if (registration_password.getText().toString().length() >= 6) {
                          /*  if (registration_password.getText().toString().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})"))
                            {*/
                                        if (registration_phone_number.getText().toString().length() >= 10) {
                                            if (isValid(registration_email.getText().toString().trim())) {
                                                registerUser();
                                            } else {
                                                alert("Please enter valid email");
                                                ToastClass.showShortToast(getApplicationContext(), "Please enter valid email");
                                            }
                                        } else {
                                            alert("Please enter a valid phone number(greater then 9 digits)");
                                            ToastClass.showShortToast(getApplicationContext(), "Please enter a valid phone number(greater then 9 digits)");
                                        }
                         /*   }
                            else
                            {
                                ToastClass.showShortToast(getApplicationContext(), "Password should contain at-least one Uppercase, Special and a  Numeric character");
                            }*/
                                    } else {
                                        alert("Minimum password length is 6");
                                        ToastClass.showShortToast(getApplicationContext(), "Minimum password length is 6");
                                    }
                                } else {
                                    alert("Password do not match");
                                    ToastClass.showShortToast(getApplicationContext(), "Password do not match");
                                }
                            } else {
                                alert("Please enter mandatory fields");
                                ToastClass.showShortToast(getApplicationContext(), "Please enter mandatory fields");
                            }
                        } else {
                            alert("Joining year and leaving year cannot be same");
                        }
                    } else {
                        alert("Please select state");
                    }
                } else {
                    alert("Please select country");
                }
            }

        });

        addSpinnerAdapter(registration_spinner_joining_year);
        addSpinnerAdapter(registration_spinner_leaving_year);
    }


    public void addSpinnerAdapter(Spinner spinner) {
        int startingYear = 1960;
        List<String> yearList = new ArrayList<>();
        while (startingYear < 2019) {
            startingYear = startingYear + 1;
            yearList.add(String.valueOf(startingYear));
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, yearList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }

    public boolean checkValidEdit(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().length() == 0) {
                return false;
            }
        }
        return true;
    }


    ProgressHUD mProgressHUD;

    public void registerUser() {
        mProgressHUD = ProgressHUD.show(RegistrationActivity.this, "Registering a Legend...", true, true, null);

        try {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("Country", registration_spinner_country.getSelectedItem().toString());
            jsonObject.put("State", registration_spinner_state.getSelectedItem().toString());
            jsonObject.put("City", registration_city.getText().toString());
            jsonObject.put("School", registration_school.getText().toString());
            jsonObject.put("UserName", registration_user.getText().toString());
            jsonObject.put("FirstName", registration_first_name.getText().toString());
            jsonObject.put("LastName", registration_last_name.getText().toString());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date d = simpleDateFormat.parse(registration_dob.getText().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            jsonObject.put("DateOfBirth", sdf.format(d));
            jsonObject.put("PhoneNumber", registration_phone_number.getText().toString());
            jsonObject.put("Email", registration_email.getText().toString());
            jsonObject.put("Address", registration_address.getText().toString());
            jsonObject.put("PostalCode", registration_postal_code.getText().toString());
            jsonObject.put("RollNo", "0000");
            jsonObject.put("JoiningYear", registration_spinner_joining_year.getSelectedItem().toString() + "-01-01");
            jsonObject.put("LeavingYear", registration_spinner_leaving_year.getSelectedItem().toString() + "-01-01");
            jsonObject.put("Designation", registration_designation.getText().toString());
            jsonObject.put("Posting", registration_posting.getText().toString());
            jsonObject.put("BloodGroup", registration_spinner_blood_group.getSelectedItem().toString());
//            jsonObject.put("Photo", registration_ph.getText().toString());
//            jsonObject.put("Latitude", registration_imei.getText().toString());
//            jsonObject.put("Longitude", registration_imei.getText().toString());
            jsonObject.put("House", "House");
            jsonObject.put("Password", registration_password.getText().toString());
            jsonObject.put("ConfirmPassword", registration_confirm_password.getText().toString());
            jsonObject.put("UserRole", "User");
            jsonObject.put("Department", registration_department.getText().toString());
            jsonObject.put("Profile Link", registration_profileLink.getText().toString());
            jsonObject.put("Profession", registration_spinner_Professions.getSelectedItem().toString());
            if (registration_checkbox_phoneVisible.isChecked()) {
                jsonObject.put("PhoneVisible", true);
            } else {
                jsonObject.put("PhoneVisible", false);
            }
            if (registration_checkbox_locationVisible.isChecked()) {
                jsonObject.put("ShowLocation", true);
            } else {
                jsonObject.put("ShowLocation", false);
            }

            Log.d(TagUtils.getTag(), "json Object:-" + jsonObject.toString());

            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest getRequest = new StringRequest(Request.Method.POST, WebServicesUrls.REGISTER_USER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            mProgressHUD.dismiss();
                            Log.d(TagUtils.getTag(), "response:-" + response.toString());
                            if (response.toString().contains("Success")) {
                                ToastClass.showShortToast(getApplicationContext(), "Registration OK, please login to continue");
                                alert("Registration OK, please login to continue");
                            } else {
                                alert("Registration FAILED");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mProgressHUD.dismiss();
                            Log.d(TagUtils.getTag(), "error:-" + error.toString());
//                            error.printStackTrace();
                            NetworkResponse response = error.networkResponse;
                            if (error instanceof ServerError && response != null) {
                                try {
                                    String res = new String(response.data,
                                            HttpHeaderParser.parseCharset(response.headers));
                                    // Now you can use any deserializer to make sense of data
                                    JSONObject obj = new JSONObject(res);
                                    Iterator<?> keys = obj.keys();
                                    while (keys.hasNext()) {
                                        String key = (String) keys.next();
                                        if (obj.get(key) instanceof JSONObject) {
                                            JSONObject xx = new JSONObject(obj.get(key).toString());
                                            //Log.d("Error",xx.getString("Errors"));
                                            if (xx.has("Errors")) {
                                                alert(xx.getString("Errors"));
                                                Log.d(TagUtils.getTag(), "obj:-" + xx.getString("Errors"));
                                            } else if (xx.has("ExceptionMessage")) {
                                                alert(xx.getString("ExceptionMessage"));
                                                Log.d(TagUtils.getTag(), "obj:-" + xx.getString("ExceptionMessage"));
                                            }
                                        }
                                    }
                                    //JSONArray jsonArray = obj.optJSONArray("Errors");
                                    //ToastClass.showShortToast(getApplicationContext(), jsonArray.optString(0));

                                } catch (Exception e1) {
                                    ToastClass.showShortToast(getApplicationContext(), e1.getMessage());
                                    // Couldn't properly decode data to string
                                    e1.printStackTrace();
                                }
                            } else {
                                ToastClass.showShortToast(getApplicationContext(), error.toString());
                                alert("Registration failed!!!, unknown error or timeout error. Please try again");
                            }
                        }
                    }
            ) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    return jsonObject.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
//                    headers.put("Authorization", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.TOKEN_TYPE, "") + " " + Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.ACCESS_TOKEN, ""));
                    headers.put("Content-Type", "application/json");
//                    UtilityFunction.printAllValues(headers);
                    return headers;
                }
            };
            queue.add(getRequest);
        } catch (Exception e) {
            mProgressHUD.dismiss();
            ToastClass.showShortToast(getApplicationContext(), e.getMessage());
            e.printStackTrace();
        }
    }

    public void alert(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Alert!").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


    public void openDatePicker() {
        new SpinnerDatePickerDialogBuilder()
                .context(this)
                .callback(this)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .defaultDate(2018, 07, 27)
                .maxDate(2020, 0, 1)
                .minDate(1900, 0, 1)
                .build()
                .show();
    }

    EditText selectedEditText;

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        String day = "";
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }

        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }

        String date = day + "-" + month + "-" + year;
        selectedEditText.setText(date);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        String day = "";
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }

        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }

        String date = day + "-" + month + "-" + year;
        Log.d(TagUtils.getTag(), "date :-" + date);
        selectedEditText.setText(date);
    }
}
