package com.example.DY_PDT;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.DY_PDT.model.shop;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivityCons extends AppCompatActivity {

    final String PRODACTION = Constants.PRODACTION;
    final String PRODACTIONWEB = Constants.PRODACTIONWEB;
    final String LINESTOP=Constants.LINESTOP;
    final String OFFLINE=Constants.IOFFLINE;
    final String VAGONGAYUKLASH=Constants.VAGONGAYUKLASH;

    String LOGIN_URL="";
    Intent intent;

    EditText etUserNameCon;
    EditText etPasswordCon;
    TextView tvPasswordMesage;
    Button buttonLogin;
    Button buttonExit;

    SharedPreferences prefs;
    String WorkStatus = "DY";
    ActionBar actionBar;
    Double NewversionName;
    Double OldversionName;
    Thread thread;


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater mi = getMenuInflater();
//        mi.inflate(R.menu.actionbar1, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        prefs = getSharedPreferences("MY_PREFS_NAME", Activity.MODE_PRIVATE);
//        String OmborStatus = prefs.getString("OmborStatus", "0");
//        SharedPreferences.Editor editor = prefs.edit();
//
//
//        //////
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Parolni kiriting");
//        LinearLayout layout =new  LinearLayout(this);
//        layout.setOrientation( LinearLayout.VERTICAL);
//
//        final EditText editText = new EditText(this);
//        final EditText etOmborStatus=new EditText(this);
//        final TextView tvStatus=new TextView(this);
//        tvStatus.setText("Status: 1-Trim, 2-ok, 3-print, 4-zavod(out), 5-Sklad(in), 6-Sklad(out) 7-Zavod(in), 8-700, 9-test");
//        tvStatus.setTextSize(25);
//        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        etOmborStatus.setInputType(InputType.TYPE_CLASS_NUMBER);
//        etOmborStatus.setText(OmborStatus);
//        layout.addView(editText);
//        layout.addView(tvStatus);
//        layout.addView(etOmborStatus);
//
//        builder.setView(layout);
//        builder.setPositiveButton("O'zgartrish", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String newValue = editText.getText().toString();
//
//                if(newValue.equals("Admin123")){
//
//                    switch (item.getItemId()) {
//                        case R.id.IdLineStop:
//                            editor.putString("WorkStatus", LINESTOP);
//                            editor.commit();
//                            buttonLogin.setText(LINESTOP);
//                            WorkStatus = LINESTOP;
//                            break;
//
//                        case R.id.IdProdagtion:
//                            editor.putString("WorkStatus", PRODACTION);
//                            editor.commit();
//                            buttonLogin.setText(PRODACTION);
//                            WorkStatus = PRODACTION;
//                            break;
//                        case R.id.IdProdagtionWeb:
//                            editor.putString("WorkStatus", PRODACTIONWEB);
//                            editor.commit();
//                            buttonLogin.setText(PRODACTIONWEB);
//                            WorkStatus = PRODACTIONWEB;
//                            break;
//                        case R.id.IdInventarOFFLINE:
//                            editor.putString("WorkStatus", OFFLINE);
//                            editor.commit();
//                            buttonLogin.setText(OFFLINE);
//                            WorkStatus = OFFLINE;
//                            break;
//                        case R.id.IdVagongaYuklash:
//                            editor.putString("WorkStatus", VAGONGAYUKLASH);
//                            editor.commit();
//                            buttonLogin.setText(VAGONGAYUKLASH);
//                            WorkStatus = VAGONGAYUKLASH;
//                            break;
//
//
//
////                        case R.id.IdTest:
////                            editor.putString("WorkStatus", TEST);
////                            editor.commit();
////                            buttonLogin.setText(TEST);
////                            WorkStatus = TEST;
////                            break;
//                        default:
//                            throw new IllegalStateException("Unexpected value: " + item.getItemId());
//                    }
//
//                    dialog.dismiss();
//                 //   Toast.makeText(this, "You need to type something in the editText", Toast.LENGTH_LONG).show();
//            }else{
//               // tv.setText(newValue);
//                dialog.dismiss();
//                return;
//            }
//
//            }
//        });
//        //////
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cons);
        initView();
        OldversionName = Double.valueOf(BuildConfig.VERSION_NAME);
        actionBar = getSupportActionBar();
        actionBar.setTitle("LINE STOP " + OldversionName);

        ////////////////////

        //installtion permission

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            if (!getPackageManager().canRequestPackageInstalls()) {
//                startActivityForResult(new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
//                        .setData(Uri.parse(String.format("package:%s", getPackageName()))), 1);
//            }
//        }//Storage Permission
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//        }
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }
//
//        /////////////////////
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);




//        NewversionName = getInfo();
//
//        try {
//            Thread.sleep(1000);
//
// //          if(NewversionName>OldversionName){
//
//                new AlertDialog.Builder(this)
//                        .setIcon(android.R.drawable.ic_dialog_info)
//                        .setTitle("Dasturni yangilash")
//                        .setMessage("Yangi versiyasini o'rnatasimi?")
//                        .setPositiveButton("ha", new DialogInterface.OnClickListener()
//                        {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                //new DownloadFileFromURL().execute(configAPI+"getConfig.php?get_apk=sapPPApp");
//
//                                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
//                                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                            == PackageManager.PERMISSION_DENIED){
//                                        ActivityCompat.requestPermissions(LoginActivityCons.this, new String[]{Manifest.permission. WRITE_EXTERNAL_STORAGE}, STORAGE_CODE);
//                                    }
//                                    else{
//
//
//
//            //                            DownloadOnSDcard();
//
//                                        //initDownload();
//
//////////                                  //      initDownload();
// ///////////////////////                                       thread.start();
// //                                       DownloadFiles();
//                                        //DownloadFiles();
//
//                                    }
//                                }
//                                else{
//   //                                 DownloadOnSDcard();
//                                   //initDownload();
//
/////////////////////////                                   thread.start();
////                                    DownloadFiles();
//
//                                }
//
//                            }
//
//                        })
//                        .setNegativeButton("Yo'q", null)
//                        .show();
////            }
//
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //downloadFileNew();
                    getApkFile(Constants.URL_GETAPK);
                    //     DownloadFiles();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        prefs = getSharedPreferences("MY_PREFS_NAME", Activity.MODE_PRIVATE);

        WorkStatus = prefs.getString("WorkStatus", PRODACTION);
        WorkStatus=LINESTOP;


            buttonLogin.setText(LINESTOP);
            LOGIN_URL = Constants.URLSEND_LINE_STOP_LOGIN;

        etUserNameCon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tvPasswordMesage.setText("");
                } else {
                    if (etUserNameCon.getText().toString().isEmpty()) {
                        tvPasswordMesage.setText("Userni kriting");
                        etUserNameCon.setFocusable(true);
                    }
                }
            }
        });

        etPasswordCon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tvPasswordMesage.setText("");

                } else {
                    if (etPasswordCon.getText().toString().isEmpty()) {
                        tvPasswordMesage.setText("Userni kriting");
                        etPasswordCon.setFocusable(true);
                    }
                }
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private Double getInfo() {

        postGetInfo(Constants.URLGET_INFO, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String responseStr = response.body().string();

                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);

                            NewversionName = Double.valueOf(jsonObject.getString("app_version"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("responseStr :" + responseStr);
                    }
                }
            }
        });
        return NewversionName;
    }

    private void initView() {
        etUserNameCon = findViewById(R.id.etUserNameCon);
        etPasswordCon = findViewById(R.id.etPasswordCon);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonExit = findViewById(R.id.buttonExit);
        tvPasswordMesage = findViewById(R.id.tvPasswordMesage);
    }

    String message;

    public void Login(View view) {

//                     prefs = getSharedPreferences("MY_PREFS_NAME", Activity.MODE_PRIVATE);
//                 SharedPreferences.Editor editor = prefs.edit();
//                 editor.putLong("shpIDMAX", 0L);
//                 editor.commit();
                 AddAreaArrayList();
            intent = new Intent(this, activityLineStop.class);


        /////////////
        if (!etUserNameCon.getText().toString().isEmpty()) {
            try {
                post(LOGIN_URL, "", etUserNameCon.getText().toString(), etPasswordCon.getText().toString(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Something went wrong
                        message = "Aloqa yo'q!!!";
                        if ( checkUser(etUserNameCon.getText().toString(), etPasswordCon.getText().toString()) == true) {
                            intent.putExtra("etUserNameCon", etUserNameCon.getText().toString());
                            intent.putExtra("etPasswordCon", etPasswordCon.getText().toString());
                            intent.putExtra("WorkStatus", WorkStatus);
                            startActivity(intent);
                        } else {
                            message = "Userni tekshiring";
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseStr = "";

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                responseStr = response.body().string();

                                System.out.println("responseStr :" + responseStr);

                                    if (responseStr.contains("success")) {

 //                                      if( WorkStatus.equals(VAGONGAYUKLASH)==false){
                                           checkAndAddUser(etUserNameCon.getText().toString(), etPasswordCon.getText().toString());
                                           intent.putExtra("etUserNameCon", etUserNameCon.getText().toString());
                                           intent.putExtra("etPasswordCon", etPasswordCon.getText().toString());
//                                       }

                                       //else {
                                       //    try {
                                        //       JSONObject jsonObject=new JSONObject(responseStr);
                                       //        intent.putExtra("vagonlar", jsonObject.optString("vagons"));
                                       //    } catch (JSONException e) {
                                       //        e.printStackTrace();
                                      //     }

                                     //  }
                                        intent.putExtra("WorkStatus", WorkStatus);
                                        startActivity(intent);

                                    }
                            }
                            // Do what you want to do with the response.
                            message = "OK!";
                        } else {
                            if (response.body() != null) {
                                responseStr = response.body().string();
                            }
                            switch (response.code()) {
                                case 401:
                                    message = responseStr;
                                    break;
                                case 500:
                                case 502:
                                case 503:
                                case 504:
                                    if (checkUser(etUserNameCon.getText().toString(), etPasswordCon.getText().toString()) == true) {
                                        intent.putExtra("etUserNameCon", etUserNameCon.getText().toString());
                                        intent.putExtra("etPasswordCon", etPasswordCon.getText().toString());

                                        startActivity(intent);
                                    } else {
                                        message = response.code() + "Userni tekshiring";
                                    }
                                    break;
                                default:
                                    message = response.code() + "Unexpected error:" + responseStr;
                            }
                            // Request not successful
                            message = "Request not successful" + response.code();
                        }
                    }
                });
            } catch (Exception e) {
                message = "Error Getting data : \n" + e.getMessage();
            }
            tvPasswordMesage.setText(message);

        } else {
            buttonLogin.setEnabled(true);
            tvPasswordMesage.setText("Userni, parol kriting");
        }
    }

    //  Intent intent2 = new Intent(this, activity_menu.class);
//
//    private void WithToken(String access_token, String token_type) {
//
//
//        getWerehouse(Constants.URL_AUTOINFO_WEREHOUSE, access_token, token_type, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                if(response.isSuccessful()){
//                    if(response.body()!=null){
//
//                        try {
//                            JSONArray jsonObject=new JSONArray(response.body().string());
//                            intent.putExtra("etUserNameCon", etUserNameCon.getText().toString());
//                            intent.putExtra("WorkStatus", WorkStatus);
//                            intent.putExtra("access_token", access_token);
//                            intent.putExtra("ombor", jsonObject.toString());
//                            checkAndAddOmbor(jsonObject.toString());
//                            startActivity(intent);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        //checkAndAddUser(etUserNameCon.getText().toString(), etPasswordCon.getText().toString());
//
//                    }
//                }
//            }
//        });
//    }

//    private String getOmborlar() {
//        HotOrNotOmbor hotOrNotOmborlar = new HotOrNotOmbor(this);
//        hotOrNotOmborlar.open();
//        String omborlar = hotOrNotOmborlar.getOmborlar();
//        hotOrNotOmborlar.close();
//        return omborlar;
//
//    }
//    private Boolean checkAndAddOmbor(String omborlar) {
//        HotOrNotOmbor hotOrNotOmbor = new HotOrNotOmbor(this);
//        hotOrNotOmbor.open();
//        hotOrNotOmbor.createEntryOmbor(omborlar);
//        hotOrNotOmbor.close();
//        return true;
//    }

//    private Boolean checkAndAddUserOmbor(String userNmae, String password,String access_token,String ombor ) {
//        HotOrNotOmbor hotOrNotOmbor = new HotOrNotOmbor(this);
//        hotOrNotOmbor.open();
//        boolean checkAndAddUser = hotOrNotOmbor.setUserInfo(userNmae, password,access_token,ombor);
//        hotOrNotOmbor.close();
//        return checkAndAddUser;
//    }

//    private String checkUserOmbor(String userName, String password) {
//        HotOrNotOmbor hotOrNotObor = new HotOrNotOmbor(this);
//        hotOrNotObor.open();
//        String checkToken = hotOrNotObor.getUserInfo(userName, password);
//        hotOrNotObor.close();
//        return checkToken;
//    }

    private Boolean checkAndAddUserWeb(String userNmae, String password,String access_token ) {
        HotOrNotLineStop hotOrNotMYOWeb = new HotOrNotLineStop(this);
        hotOrNotMYOWeb.open();
        boolean checkAndAddUser = hotOrNotMYOWeb.setUserInfo(userNmae, password,access_token);
        hotOrNotMYOWeb.close();
        return checkAndAddUser;
    }

//    private String checkUserWeb(String userName, String password) {
//        HotOrNotMYOWeb hotOrNotMYOWeb = new HotOrNotMYOWeb(this);
//        hotOrNotMYOWeb.open();
//        String checkToken = hotOrNotMYOWeb.getUserInfo(userName, password);
//        hotOrNotMYOWeb.close();
//        return checkToken;
//    }
    private Boolean checkAndAddUser(String userNmae, String password) {
        HotOrNotLineStop hotOrNotMYO = new HotOrNotLineStop(this);
        hotOrNotMYO.open();
        boolean checkAndAddUser = hotOrNotMYO.setUserInfo(userNmae, password);
        hotOrNotMYO.close();
        return checkAndAddUser;
    }

    private Boolean checkUser(String userName, String password) {
        HotOrNotLineStop hotOrNotMYO = new HotOrNotLineStop(this);
        hotOrNotMYO.open();
        boolean checkUser = hotOrNotMYO.getUserInfo(userName, password);
        hotOrNotMYO.close();
        return checkUser;
    }


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    Call getWerehouse(String url, String token, String type, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Authorization", "Bearer "+token)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    Call post(String url, String json, final String login, final String parol, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Authorization", Credentials.basic(login, parol))
            //    .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }



//    Call postLogin(String url, String json, String login, String parol, Callback callback) {
//
//        JSONObject jsonObject=new JSONObject();
//
//        try {
//            jsonObject.put("client_id",2);
//            jsonObject.put("client_secret","6i4Y0T5QFICTuxUo0052xMPDN6QEDbuGkyRdhcHl");
//            jsonObject.put("grant_type","password");
//            jsonObject.put("password",parol);
//            jsonObject.put("username",login);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
//        Request request = new Request.Builder()
//                .url(url)
////                .header("Authorization", Credentials.basic(login, parol))
//                .post(body)
//                .build();
//        Call call = client.newCall(request);
//        call.enqueue(callback);
//        return call;
//    }


    Call postGetInfo(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }


    @Override
    protected void onRestart() {
        buttonLogin.setEnabled(true);
        super.onRestart();
    }


    private static void downloadFileNew() {
        try {
            URL u = new URL(Constants.URL_GETAPK);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream("outputFile.apk"));
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            return; // swallow a 404
        } catch (IOException e) {
            return; // swallow a 404
        }
    }

    public static File getApkFile(String url) {
        int last = url.lastIndexOf("/");
        File file = new File(Environment.getExternalStorageDirectory() + "/" + "download/",
                url.substring(last + 1));
        try {/*www.j a  v a 2  s. c o  m*/
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                FileOutputStream os = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.flush();
                os.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    public void DownloadFiles() {

        try {
            URL u = new URL(Constants.URL_GETAPK);
            InputStream is = u.openStream();

            DataInputStream dis = new DataInputStream(is);

            byte[] buffer = new byte[1024];
            int length;

            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/" + "download/dynew.apk"));
            while ((length = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            //        installAPK();


        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate", "malformed url error", mue);
        } catch (IOException ioe) {
            Log.e("SYNC getUpdate", "io error", ioe);
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
        }
    }

    void installAPK() {

        String PATH = Environment.getExternalStorageDirectory() + "/" + "download/dynew.apk";
        File file = new File(PATH);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uriFromFile(getApplicationContext(), new File(PATH)), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                getApplicationContext().startActivity(intent);
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();

            }
        } else {
            Toast.makeText(getApplicationContext(), "installing", Toast.LENGTH_LONG).show();
        }
    }

    Uri uriFromFile(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
        } else {
            return Uri.fromFile(file);
        }
    }


    public void DownloadOnSDcard() {
        File outputFile = null;
        try {
            URL url = new URL(Constants.URL_GETAPK); // Your given URL.
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect(); // Connection Complete here.!

            //Toast.makeText(getApplicationContext(), "HttpURLConnection complete.", Toast.LENGTH_SHORT).show();

            String PATH = Environment.getExternalStorageDirectory() + "/download/";
            File file = new File(PATH); // PATH = /mnt/sdcard/download/
            if (!file.exists()) {
                file.mkdirs();
            }
            outputFile = new File(file, "sapdy1.apk");

            if (outputFile.exists()) {
                outputFile.delete();
            }

            FileOutputStream fos = new FileOutputStream(outputFile);

            //      Toast.makeText(getApplicationContext(), "SD Card Path: " + outputFile.toString(), Toast.LENGTH_SHORT).show();

            InputStream is = c.getInputStream(); // Get from Server and Catch In Input Stream Object.

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1); // Write In FileOutputStream.
            }
            fos.close();
            is.close();//till here, it works fine - .apk is download to my sdcard in download file.
            // So please Check in DDMS tab and Select your Emulator.

            //Toast.makeText(getApplicationContext(), "Download Complete on SD Card.!", Toast.LENGTH_SHORT).show();
            //download the APK to sdcard then fire the Intent.

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + "sapdy1.apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (IOException e) {
            System.out.println("KKKKKKKKKK " + e.toString());
        }
    }


    private void initDownload() {
        String uri = Constants.URL_GETAPK;
        download(getApplicationContext(), "CodeSpeedy_writer", ".apk", "Downloads", uri.trim());
    }

    private void download(Context context, String Filename, String FileExtension, String DesignationDirectory, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, DesignationDirectory, Filename + FileExtension);
        assert downloadManager != null;
        downloadManager.enqueue(request);
        Snackbar snackbar = (Snackbar) Snackbar
                .make(findViewById(android.R.id.content), "Downloading...", Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    ArrayList<shop> shopArrayList=new ArrayList<>();

    private void AddAreaArrayList() {

//       }
        HotOrNotLineStop areaMalumot=new HotOrNotLineStop(LoginActivityCons.this);
        shopArrayList.clear();

        shopArrayList.add(new shop("IT","1"));
        shopArrayList.add(new shop("Supply Chain","1"));
        shopArrayList.add(new shop("Maintenance","1"));
        shopArrayList.add(new shop("Logistics","1"));
        shopArrayList.add(new shop("Material Control","1"));
        shopArrayList.add(new shop("Paint Shop","1"));
        shopArrayList.add(new shop("Press shop 1","1"));
        shopArrayList.add(new shop("Production engineering","1"));
        shopArrayList.add(new shop("Purchasing","1"));
        shopArrayList.add(new shop("Utility","1"));
        shopArrayList.add(new shop("Tool Shop","1"));
        shopArrayList.add(new shop("Assembly Shop","1"));
        shopArrayList.add(new shop("Body shop 1","1"));
        shopArrayList.add(new shop("Body shop 2","1"));
        shopArrayList.add(new shop("Общий отдел","1"));
        shopArrayList.add(new shop("SQ KD","1"));
        shopArrayList.add(new shop("SQ Local","1"));
        shopArrayList.add(new shop("Warehouse","1"));
        shopArrayList.add(new shop("ME","1"));
        shopArrayList.add(new shop("Quality","1"));

        areaMalumot.open();

        for(int a=0;a<shopArrayList.size();a++){
            try {
                areaMalumot.createEntryLineStopShop(shopArrayList.get(a).getShop_title(),shopArrayList.get(a).getShop_filter());
            }
            catch (Exception e){
            }
        }
        areaMalumot.close();
    }
}
