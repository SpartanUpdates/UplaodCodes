package com.wavymusic.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.AdSettings;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.root.unity.AndroidUnityCall;
import com.unity3d.player.UnityPlayer;
import com.wavymusic.Fragment.ThemeFragmentByCategory;
import com.wavymusic.Model.CategoryModel;
import com.wavymusic.Model.ParticalCategoryModel;
import com.wavymusic.Model.ParticalItemModel;
import com.wavymusic.Model.ThemeHorizontalModel;
import com.wavymusic.Preferences.LanguagePref;
import com.wavymusic.ProgressBar.kprogresshud.KProgressHUD;
import com.wavymusic.R;
import com.wavymusic.Retrofit.APIClient;
import com.wavymusic.Retrofit.APIInterface;
import com.wavymusic.Retrofit.AppConstant;
import com.wavymusic.UnityPlayerActivity;
import com.wavymusic.Utils.Utils;
import com.wavymusic.application.MyApplication;
import com.wavymusic.guillotine.animation.GuillotineAnimation;
import com.wavymusic.guillotine.interfaces.GuillotineListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private final int REQUEST_PERMISSION_SETTING = 101;
    private static final long RIPPLE_DURATION = 250;
    //    public String AllFilePath;
    //    public InterstitialAd mInterstitialAd;
    public KProgressHUD hud;
    public int id;
    public MediaPlayer mediaPlayer;
    //    public AdLoader adLoader;
//    public List<UnifiedNativeAd> mNativeAds = new ArrayList<>();
    Activity activity = HomeActivity.this;
    ArrayList<CategoryModel> tabcategorylist = new ArrayList<>();

    TextView tvMycreation;
    RelativeLayout rlLoading;
    LinearLayout llRetry;
    Button btnRetry;
    TextView tvtitle;
    Toolbar toolbar;
    ViewPagerAdapter adp;
    int SelectedPosition = 0;
    AdRequest adRequest;
    AdView adView;
    public SharedPreferences pref;
    String Version;
    PackageInfo info = null;
    private ArrayList<ThemeHorizontalModel> WhatsNewList = new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean B = false;
    private boolean C = false;
    private boolean D = false;
    APIInterface apiInterface;
    FrameLayout fldrawermain;
    View contentHamburger;
    TextView tvAppversion;
    LinearLayout llcreation, llringtone, llSongLanguage, llFeedback, llrate, llshare, llprivacy;
    private GuillotineAnimation mGuillotineAnimation;
    private boolean isOpened = false;

    public boolean IsFromLanguage;
    public boolean IsFromMain;

    boolean isSetupReady = false;
    public static boolean isApiRunning = false;
    public static boolean isParticlesApiRunning = false;

    TextView tv_prg_msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pref = PreferenceManager.getDefaultSharedPreferences(activity);
        IsFromLanguage = getIntent().getBooleanExtra("IsFromLanguage", false);
        IsFromMain = getIntent().getBooleanExtra("IsFromMain", false);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryId("111");
        categoryModel.setName("New");
        tabcategorylist.add(categoryModel);
        BindView();
//        Utils.CreateDirectory();
        MyApplication.isEditCall = false;
        if (Utils.checkConnectivity(activity, false)) {
            if (pref.getString("offlineResponse", "").equalsIgnoreCase("")) {
                if (!isApiRunning) {
                    isApiRunning = true;
                    GetCategory();
                } else if (IsFromLanguage && MyApplication.Tempcall != null) {
                    MyApplication.Tempcall.cancel();
                    isApiRunning = true;
                    GetCategory();
                }
            } else if (((new Date().getTime() - pref.getLong("offlineResponseTime", 1588598205L)) >= 300000L)) {
                Log.e("TAG", "5 Min Complate");
                if (!isApiRunning) {
                    isApiRunning = true;
                    GetCategory();
                }
            } else if (IsFromLanguage) {
                if (!isApiRunning) {
                    isApiRunning = true;
                    GetCategory();
                    Log.e("TAG", "Is From Language");
                } else if (MyApplication.Tempcall != null) {
                    MyApplication.Tempcall.cancel();
                    isApiRunning = true;
                    GetCategory();
                    Log.e("TAG", "Not Is From Language");
                }
            }
        } else if (pref.getString("offlineResponse", "").equalsIgnoreCase("")) {
            Log.e("HomeActivity", "No Internet");
        }
        BannerAds();
//        CallInterstitialAd();
        GetAppVersion();
        NavDrawer();
        SetListener();
        FirebaseAnalytics mFirebaseAnalytics;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "HomeActivity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        Utils.INSTANCE.CopyAssets(activity);
        SetThemeData();
//        if (Build.VERSION.SDK_INT < 23) {
//            SetThemeData();
//        } else if (!this.B || this.C) {
//            this.C = false;
//            this.D = false;
//            RequestPermission(false);
//        }
//        if (this.D) {
//            this.C = true;
//        }
    }

    private void GetAppVersion() {
        PackageManager manager = activity.getPackageManager();
        try {
            info = manager.getPackageInfo(activity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            Version = info.versionName;
        }
    }

    public void PremissionFromSetting(HomeActivity homeActivity) {
        homeActivity.D = true;
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", homeActivity.getPackageName(), null);
        intent.setData(uri);
        homeActivity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
    }

    @Override
    protected void onDestroy() {
        this.adView.removeView(this.adView);
        AdView adView = this.adView;
        if (adView != null) {
            adView.destroy();
            this.adView = null;
        }
//        if (MyApplication.fbinterstitialAd != null) {
//            MyApplication.fbinterstitialAd.destroy();
//        }
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        super.onDestroy();
    }

    public void onPause() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemVisibility();
    }

    public void RequestPermission(boolean z) {
        if ((ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            Utils.CreateDirectory();
            Utils.INSTANCE.CopyAssets(activity);
            SetThemeData();
        } else if (z) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Necessary permission");
            builder.setMessage("Allow Required Permission");
            builder.setCancelable(false);
            builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    PremissionFromSetting(HomeActivity.this);
                }
            });
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UnityPlayerActivity.mUnityPlayer.quit();
                    finish();
                }
            });
            builder.show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Utils.CreateDirectory();
                Utils.INSTANCE.CopyAssets(activity);
                SetThemeData();
            } else {
                RequestPermission(true);
            }
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (iArr.length > 0) {
                if (iArr[0] == 0 && iArr[1] == PackageManager.PERMISSION_GRANTED) {
                    Utils.CreateDirectory();
                    Utils.INSTANCE.CopyAssets(activity);
                    Log.e("TAG", "PremissionGranted");
                    SetThemeData();
                } else if ((iArr[0] == -1 && !ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) || (iArr[1] == -1 && !ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE))) {
                    this.B = true;
                    RequestPermission(true);
                }
            }
        }
    }

    private void SystemVisibility() {
        if (Build.VERSION.SDK_INT <= 11 || Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= 19) {
                getWindow().getDecorView().setSystemUiVisibility(12288);
            }
            return;
        }
        getWindow().getDecorView().setSystemUiVisibility(8);
    }

    private void SetThemeData() {
        if (pref.getString("offlineResponse", "").equalsIgnoreCase("")) {
            isSetupReady = true;
        } else if (!IsFromLanguage) {
            Log.e("TAG", "OfflineRes" + pref.getString("offlineResponse", ""));
            SetupDataInLayout(pref.getString("offlineResponse", ""));
        } else {
            isSetupReady = true;
        }
    }

    private void SetAssetTheme() {
        if (Utils.checkConnectivity(activity, false)) {
            if (pref.getString("offlineParticles_v1.6", "").equalsIgnoreCase("")) {
                SetParticlesData(getFileFormAsset());
                Log.e("HomeActivity", "Particles Offline Update Model");
                if (!isParticlesApiRunning) {
                    GetAssetTheme getAssetTheme = new GetAssetTheme();
                    if (Build.VERSION.SDK_INT >= 11) {
                        getAssetTheme.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, AppConstant.AssetPath);
                    } else {
                        getAssetTheme.execute(AppConstant.AssetPath);
                    }
                }
            } else if (((new Date().getTime() - pref.getLong("offlineParticlesTime_v1.6", 1588598205L)) >= 300000L)) {
                Log.e("HomeActivity", "5 Min Complete Particles");
                SetParticlesData(pref.getString("offlineParticles_v1.6", ""));
                if (!isParticlesApiRunning) {
                    GetAssetTheme getAssetTheme = new GetAssetTheme();
                    if (Build.VERSION.SDK_INT >= 11) {
                        getAssetTheme.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, AppConstant.AssetPath);
                    } else {
                        getAssetTheme.execute(AppConstant.AssetPath);
                    }
                }
            } else {
                SetParticlesData(pref.getString("offlineParticles_v1.6", ""));
            }
        } else {
            if (pref.getString("offlineParticles_v1.6", "").equalsIgnoreCase("")) {
                SetParticlesData(getFileFormAsset());
            } else {
                SetParticlesData(pref.getString("offlineParticles_v1.6", ""));
            }
        }
    }

    public String getFileFormAsset() {
        try {
            InputStream open = getAssets().open("asset_bundel.json");
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void BannerAds() {
        adView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

//    private void CallInterstitialAd() {
//        mInterstitialAd = new InterstitialAd(activity);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial));
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//        mInterstitialAd.setAdListener(new AdListener());
//        mInterstitialAd.setAdListener(new AdListener() {
//            public void onAdClosed() {
//                switch (id) {
//                    case 100:
//                        GoToPreview();
//                        break;
//                    case 101:
//                        GoToExit();
//                        break;
//                }
//            }
//
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int i) {
//                super.onAdFailedToLoad(i);
//
//            }
//        });
//    }

//    public void GoToPreview() {
//        UnityPlayer.UnitySendMessage("StaticThemeDataBase", "OnLoadUserData", AllFilePath);
//        HideShowUnityBannerAds();
//        finish();
//    }

  /*  private void loadNativeAds() {
        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.native_ad));
        adLoader = builder.forUnifiedNativeAd(
                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        mNativeAds.add(unifiedNativeAd);
                        MyApplication.getInstance().IsNativeAdsLoaded = true;

                    }
                }).withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        MyApplication.getInstance().IsNativeAdsLoaded = false;
                    }
                }).build();

        // Load the Native ads.
        adLoader.loadAds(new AdRequest.Builder().build(), 5);
    }*/

    private void HideShowUnityBannerAds() {
        try {
            UnityPlayerActivity.layoutAdView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void BindView() {
        toolbar = findViewById(R.id.toolbar);
        fldrawermain = findViewById(R.id.fl_drawer_main);
        contentHamburger = findViewById(R.id.ivNavDrawer);
        tvtitle = findViewById(R.id.tv_app_name);
        rlLoading = findViewById(R.id.rl_loading);
        tabLayout = findViewById(R.id.mTabLayout);
        viewPager = findViewById(R.id.mViewPager);
        llRetry = findViewById(R.id.llRetry);
        btnRetry = findViewById(R.id.btnRetry);
        tv_prg_msg = findViewById(R.id.tv_prg_msg);
        tv_prg_msg.setText("Please wait…");
        tvMycreation = findViewById(R.id.tv_mycreation);
        tvMycreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToMyCreation();
            }
        });
    }

    private void NavDrawer() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }
        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        fldrawermain.addView(guillotineMenu);
        mGuillotineAnimation = new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .setGuillotineListener(new GuillotineListener() {
                    @Override
                    public void onGuillotineOpened() {
                        isOpened = true;
                    }

                    @Override
                    public void onGuillotineClosed() {
                        isOpened = false;
                    }
                }).build();

        tvAppversion = findViewById(R.id.tv_app_version_code);
        llcreation = findViewById(R.id.creation_group);
        llringtone = findViewById(R.id.ringtone_group);
        llSongLanguage = findViewById(R.id.Select_songlanguage_group);
        llFeedback = findViewById(R.id.feddback_group);
        llrate = findViewById(R.id.rate_us_group);
        llshare = findViewById(R.id.invite_group);
        llprivacy = findViewById(R.id.privacy_group);
        tvAppversion.setText("v" + Version);
        llcreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, YourVideoActivity.class));
                finish();

            }
        });
        llringtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, RingtoneActivity.class));
                finish();
            }
        });
        llSongLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, LanguageSelectActivity.class));
                finish();
            }
        });
        llFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Feedback();
            }
        });
        llrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateApp();
            }
        });
        llshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareAPP();

            }
        });
        llprivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent("android.intent.action.VIEW");
                intent1.setData(Uri.parse(getResources().getString(R.string.privacy_link)));
                startActivity(intent1);
            }
        });
    }

    private void GoToMyCreation() {
        Intent intent = new Intent(activity, YourVideoActivity.class);
        startActivity(intent);
        finish();
    }

    private void SetListener() {
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkConnectivity(activity, false)) {
                    llRetry.setVisibility(View.GONE);
                    GetCategory();
                } else {
                    Toast.makeText(activity, "No Internet Connecation!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void SetOfflineCategory(Context c, String userObject, String key/*, final Date date*/) {
        pref = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, userObject);
        editor.apply();
    }

    private void SetOfflineResponseTime(Context c, final Date date, String key) {
        pref = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, date.getTime());
        editor.apply();
    }

    private void SetOfflineParticle(Context c, String userObject, String key) {
        pref = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, userObject);
        editor.apply();
    }

    private void SetOfflineParticleTime(Context c, final Date date, String key) {
        pref = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, date.getTime());
        editor.apply();
    }

    private void Feedback() {
        Intent intent2 = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", getResources().getString(R.string.feedback_email), null));
        intent2.putExtra("android.intent.extra.SUBJECT", "Feedback");
        intent2.putExtra("android.intent.extra.TEXT", "Write your feedback here");
        startActivity(Intent.createChooser(intent2, "Send email..."));
    }

    private void RateApp() {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

    private void ShareAPP() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Beats");
            String shareMessage = "\nGet free Beats at here:";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + activity.getPackageName() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        if (!isOpened) {
            if (MyApplication.fbinterstitialAd != null && MyApplication.fbinterstitialAd.isAdLoaded()) {
                MyApplication.AdsId = 3;
                MyApplication.AdsShowContext = activity;
                MyApplication.fbinterstitialAd.show();
                /*try {
                    hud = KProgressHUD.create(activity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setLabel("Showing Ads")
                            .setDetailsLabel("Please Wait...");
                    hud.show();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (NullPointerException e2) {
                    e2.printStackTrace();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            hud.dismiss();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();

                        } catch (NullPointerException e2) {
                            e2.printStackTrace();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                            id = 101;
                            mInterstitialAd.show();
                        }
                    }
                }, 2000);*/
            } else {
                GoToExit();
            }
        } else {
            mGuillotineAnimation.close();
        }

    }

    private void GoToExit() {
        startActivity(new Intent(activity, ExitAppActivity.class));
        finish();
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void SetTabLayout() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adp.getTabView(i));
            View customView = tab.getCustomView();
        }

        ((TextView) tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getCustomView().findViewById(R.id.tv_cat_Name)).setTextColor(getResources().getColor(R.color.app_gradiant_end));
        LinearLayout linearLayout = tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getCustomView().findViewById(R.id.ll_tab_home);
        linearLayout.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));

        tabLayout.getTabAt(0).getCustomView().setSelected(true);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                LinearLayout linearLayout = customView.findViewById(R.id.ll_tab_home);
                linearLayout.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                ((TextView) customView.findViewById(R.id.tv_cat_Name)).setTextColor(getResources().getColor(R.color.app_gradiant_end));
                SelectedPosition = tab.getPosition();
                MyApplication.ThemePosition = 0;
                MyApplication.CatSelectedPosition = SelectedPosition;
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                ((TextView) customView.findViewById(R.id.tv_cat_Name)).setTextColor(getResources().getColor(R.color.white));
                LinearLayout linearLayout = customView.findViewById(R.id.ll_tab_home);
                linearLayout.setBackground(getResources().getDrawable(R.drawable.round_background));
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    View customView = tab.getCustomView();
                    ((TextView) customView.findViewById(R.id.tv_cat_Name)).setTextColor(getResources().getColor(R.color.app_gradiant_end));
                    LinearLayout linearLayout = customView.findViewById(R.id.ll_tab_home);
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));
                }
            }
        });
    }

    private void setUpPagerNew() {
        adp = new ViewPagerAdapter(getSupportFragmentManager());
        int i;
//        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(adp);
        if (MyApplication.CatSelectedPosition != -1) {
            i = MyApplication.CatSelectedPosition;
        } else {
            i = 0;
        }
        viewPager.setCurrentItem(i);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void GetCategory() {
        final Handler handler = new Handler();
        if (pref.getString("offlineResponse", "").equalsIgnoreCase("") || IsFromLanguage) {
            Log.e("HomeActivity", "Handler Running");
            handler.postDelayed(new Runnable() {
                public void run() {
                    Log.e("HomeActivity", "Your Internet Connection Too Slow");
                    tv_prg_msg.setText("Slow Internet Connection");
//                    Toast.makeText(activity, "Your Internet Connection Too Slow", Toast.LENGTH_SHORT).show();
                }
            }, 10000);
        }
        Log.e("HomeActivity", "CatId" + AppConstant.DefaultCategoryId + LanguagePref.a(this).a("pref_key_language_list", "22"));
        llRetry.setVisibility(View.GONE);
        rlLoading.setVisibility(View.VISIBLE);
        Call<JsonObject> call = apiInterface.GetAllTheme(AppConstant.Token, AppConstant.ApplicationId, AppConstant.DefaultCategoryId + LanguagePref.a(this).a("pref_key_language_list", "22"));
        MyApplication.Tempcall = call;
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        if (handler != null) {
                            Log.e("HomeActivity", "removeCallbacksAndMessages");
                            handler.removeCallbacksAndMessages(null);
                        }

                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response.body()));
                        //Set Response In Offline
                        SetOfflineCategory(activity, jsonObj.toString(), "offlineResponse");
                        Log.e("HomeActivity", "" + response.body());
                        //Set Response Time
                        SetOfflineResponseTime(activity, new Date(), "offlineResponseTime");
                        isApiRunning = false;
                        if (isSetupReady) {
                            isSetupReady = false;
                            SetupDataInLayout(jsonObj.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("HomeActivity", "Response Failed");
                isApiRunning = false;
                rlLoading.setVisibility(View.GONE);
                if (pref.getString("offlineResponse", "").equalsIgnoreCase("")) {
                    llRetry.setVisibility(View.VISIBLE);
                    Toast.makeText(activity, "Data/wifi Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void SetupDataInLayout(String response) {
        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONArray tabcategory = jsonObj.getJSONArray("category");
            for (int i = 0; i < tabcategory.length(); i++) {
                JSONObject tabcategoryJSONObject = tabcategory.getJSONObject(i);
                String CategoryId = tabcategoryJSONObject.getString("id");
                SetOfflineCategory(activity, tabcategoryJSONObject.toString(), CategoryId);
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setName(tabcategoryJSONObject.getString("name"));
                categoryModel.setCategoryId(tabcategoryJSONObject.getString("id"));
                tabcategorylist.add(categoryModel);
                //What'sNewData
                JSONArray jSONArray4 = tabcategoryJSONObject.getJSONArray("themes");
                for (int j = 0; j < jSONArray4.length(); j++) {
                    ThemeHorizontalModel themeModel = new ThemeHorizontalModel();
                    JSONObject jsonobjecttheme = jSONArray4.getJSONObject(j);
                    if (jsonobjecttheme.getString("is_release").equalsIgnoreCase("1")) {
                        themeModel.setThemeid(jsonobjecttheme.getString("id"));
                        themeModel.setCategoryid(jsonobjecttheme.getString("category"));
                        themeModel.setThemeName(jsonobjecttheme.getString("theme_name"));
                        themeModel.setImage(jsonobjecttheme.getString("theme_thumbnail"));
                        themeModel.setSmall_Thumbnail(jsonobjecttheme.getString("small_thumbnail"));
                        themeModel.setAnimsoundurl(jsonobjecttheme.getString("sound_file"));
                        themeModel.setAnimSoundname(jsonobjecttheme.getString("sound_filename") + ".mp3");
                        themeModel.setAnimSoundPath(new File(Utils.PathOfThemeFolder).getAbsolutePath() + File.separator + File.separator + themeModel.getAnimSoundname());
                        themeModel.isAvailableOffline = CheckFileSize(new File(Utils.PathOfThemeFolder).getAbsolutePath() + File.separator + themeModel.getAnimSoundname());
                        themeModel.setAnimSoundfilesize(Integer.parseInt(jsonobjecttheme.getString("sound_size")));
                        themeModel.setGameobjectName(jsonobjecttheme.getString("game_object"));
                        themeModel.setNewRealise(jsonobjecttheme.getString("is_release"));
                        if (themeModel.isNewRealise().equals("1")) {
                            WhatsNewList.add(themeModel);
                        }
                    }
                }
            }
            SetOfflineCategory(activity, Utils.WhatsNewJson(WhatsNewList), "NewTheme_v3");
            setUpPagerNew();
            SetTabLayout();
            rlLoading.setVisibility(View.GONE);
            SetAssetTheme();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean CheckFileSize(String file) {
        return new File(file).exists();
    }

    public boolean CheckFileExistWithSize(String file, int size) {
        return new File(file).exists() && new File(file).length() >= size;
    }
    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return ThemeFragmentByCategory.newInstance(Integer.parseInt(tabcategorylist.get(position).getCategoryId()));
        }


        @Override
        public int getCount() {
            return tabcategorylist.size();
        }


        public View getTabView(int position) {
            View tabCatView = LayoutInflater.from(activity).inflate(R.layout.row_category_item, null);
            TextView tv = tabCatView.findViewById(R.id.tv_cat_Name);
            tv.setText(tabcategorylist.get(position).getName());
            return tabCatView;
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class GetAssetTheme extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            isParticlesApiRunning = true;
            Log.e("HomeActivity", "Particles Api Calling Start");
        }

        protected String doInBackground(String... arg0) {
            HttpsURLConnection con = null;
            try {
                URL u = new URL(arg0[0]);
                con = (HttpsURLConnection) u.openConnection();
                con.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                return sb.toString();


            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (con != null) {
                    try {
                        con.disconnect();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String result) {
            Log.e("HomeActivity", "Particles Api Response==" + result);
            isParticlesApiRunning = false;
            if (result != null) {
                SetOfflineParticle(activity, result, "offlineParticles_v1.6");
                SetOfflineParticleTime(activity, new Date(), "offlineParticlesTime_v1.6");
                Log.e("HomeActivity", "Particles Rsponse==" + result);
                if (!MyApplication.isEditCall) {
                    SetParticlesData(result);
                }
            }
        }
    }

    public void SetParticlesData(String result) {
        Log.e("TAG", "Result" + result);
        if (result != null) {
            Utils.INSTANCE.particalAssetModels.clear();
            UnityPlayerActivity.unityPlayeractivity.particalCategoryModels.clear();
            final String bundelpath = Utils.INSTANCE.getAssetUnityPath();
            try {
                JSONObject jsonObj = new JSONObject(result);
                String AssetUrl = jsonObj.getString("link");
                JSONArray jsonArray = jsonObj.getJSONArray("category");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    final JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ParticalCategoryModel particalCategoryModel = new ParticalCategoryModel();
                    particalCategoryModel.setCatId(String.valueOf(jsonObject.getInt("Id")));
                    particalCategoryModel.setCatName(jsonObject.getString("Cat_Name"));
                    UnityPlayerActivity.unityPlayeractivity.particalCategoryModels.add(particalCategoryModel);
                    JSONArray jSONArray4 = jsonObject.getJSONArray("themes");
                    for (int j = 0; j < jSONArray4.length(); j++) {
                        JSONObject themeJSONObject = jSONArray4.getJSONObject(j);
                        final ParticalItemModel particalModel = new ParticalItemModel();
                        particalModel.setThemeId(themeJSONObject.getInt("Id"));
                        particalModel.setCatId(themeJSONObject.getInt("Cat_Id"));
                        particalModel.setThemeName(themeJSONObject.getString("Theme_Name"));
                        particalModel.setThemeInfo(themeJSONObject.getString("Theme_Info"));
                        particalModel.setBundelName(themeJSONObject.getString("Theme_Name") + ".unity3d");
                        particalModel.setBundelSize(Integer.parseInt(themeJSONObject.getString("Bundle_Size")));
//                        particalModel.setBundelSize(63101);
                        particalModel.isAvailableOffline = CheckFileExistWithSize(new File(Utils.PathOfAssetFolder).getAbsolutePath() + File.separator + particalModel.getBundelName(),particalModel.getBundelSize());
                        particalModel.setGmaeObjName(themeJSONObject.getString("GameobjectName"));
                        particalModel.setIsPreimum(themeJSONObject.getString("Is_Preimum"));
                        if (themeJSONObject.getString("is_From_Asset").equals("true")) {
                            particalModel.setFromAsset(true);
                            particalModel.setThemeBundel(themeJSONObject.getString("Theme_Bundle"));
                            particalModel.setThumbImage(themeJSONObject.getString("Thumnail_Small"));
                        } else {
                            particalModel.setFromAsset(false);
                            particalModel.setThemeBundel(AssetUrl + themeJSONObject.getString("Theme_Bundle"));
                            particalModel.setThumbImage(AssetUrl + themeJSONObject.getString("Thumnail_Small"));
                        }
                        final StringBuilder sb2 = new StringBuilder();
                        sb2.append(bundelpath);
                        sb2.append(particalModel.getThemeName());
                        sb2.append(".unity3d");
                        particalModel.setThemeUnity3dPath(sb2.toString());
                        UnityPlayerActivity.unityPlayeractivity.particalListModels.add(particalModel);
                        Utils.INSTANCE.particalAssetModels.add(particalModel);
                    }
                }
            } catch (JSONException ex2) {
                ex2.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                }
            });
//            Utils.INSTANCE.getParticalCatWise(getFileFormAsset());
        }

    }
}
