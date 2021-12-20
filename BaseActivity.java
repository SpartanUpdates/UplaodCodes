package com.kotlinz.puzzlecreator.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.kotlinz.puzzlecreator.Preferance.SessionManager;
import com.kotlinz.puzzlecreator.R;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import pl.droidsonroids.gif.GifImageView;


public class BaseActivity extends AppCompatActivity {

    public SessionManager sessionManager;
    public String  latestVersion;
    public Dialog dialog;
    public FirebaseAnalytics mFirebaseAnalytics;
    public FirebaseCrashlytics crashlytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        crashlytics = FirebaseCrashlytics.getInstance();
        crashlytics.setCustomKey("User_ID", sessionManager.getUserId());
        mFirebaseAnalytics.setUserId(sessionManager.getUserId());
        getCurrentVersionCode();
        new GetLatestVersion().execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showProgressDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog_layout);
        dialog.show();
    }

    public void showerrorDialog(String main, String Msg) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout);

        TextView txtmain = dialog.findViewById(R.id.txtmain);
        TextView txtmsg = dialog.findViewById(R.id.text_dialog);
        ImageView btnok = dialog.findViewById(R.id.btn_dialog);
        GifImageView gimg = dialog.findViewById(R.id.anim_tryagain);
        gimg.setImageResource(R.drawable.error_gif);
        txtmain.setVisibility(View.GONE);

        txtmain.setText(main);
        txtmsg.setText(Msg);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Msg.equalsIgnoreCase("Token Invalid.")) {
                    dialog.dismiss();
                    showtokenexpire();
                } else {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();

    }

    public void showtokenexpire() {

        Dialog dialogtkn = new Dialog(this);
        dialogtkn.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogtkn.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialogtkn.setCancelable(false);
        dialogtkn.setContentView(R.layout.session_dialog_layout);

        Button btnok = dialogtkn.findViewById(R.id.btn_dialog);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogtkn.dismiss();
                sessionManager.logoutUser();
            }
        });

        dialogtkn.show();

    }

    public void cancel_dialog() {
        dialog.dismiss();
    }

    public void setToolbar(String title) {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        ImageView back = (ImageView) toolbarTop.findViewById(R.id.arroeback);
        mTitle.setText(title);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

    }


    public void setarrawToolbar() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        ImageView back = (ImageView) toolbarTop.findViewById(R.id.arroeback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

    }

    private float getCurrentVersionCode() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Float.parseFloat(packageInfo.versionName);
    }

    private class GetLatestVersion extends AsyncTask<String, String, JSONObject> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getPackageName()).get();
                latestVersion = doc.getElementsByClass("htlgb").get(6).text();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (latestVersion != null) {
                if (Float.parseFloat(latestVersion) > getCurrentVersionCode()) {
                    if (!isFinishing()) {
                        showUpdateDialog();
                    }
                }
            }
            super.onPostExecute(jsonObject);
        }
    }

    private void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("A New Update is Available");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        dialog = builder.show();
    }
}
