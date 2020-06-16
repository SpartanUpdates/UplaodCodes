package com.wavymusic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.root.unity.AndroidUnityCall;
import com.wavymusic.Adapter.ParticalCatAdapter;
import com.wavymusic.Adapter.ParticalItemAdapter;
import com.wavymusic.Model.ParticalCategoryModel;
import com.wavymusic.Model.ParticalItemModel;
import com.wavymusic.Model.SongLanguageModel;
import com.wavymusic.NativeAds.NativeAdvanceAds;
import com.wavymusic.Preferences.LanguagePref;
import com.wavymusic.Retrofit.AppConstant;
import com.wavymusic.Utils.AppGeneral;
import com.wavymusic.Utils.Utils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.unity3d.player.UnityPlayer;
import com.wavymusic.activity.HomeActivity;
import com.wavymusic.activity.SongSelectActivity;
import com.wavymusic.application.MyApplication;

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
import java.util.HashSet;

import javax.net.ssl.HttpsURLConnection;


public class UnityPlayerActivity extends Activity implements View.OnClickListener {
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    public static UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
    public static FrameLayout layoutAdView;
    //    public static InterstitialAd mInterstitialAd;
    public static AlertDialog alertDialog;
    public static String m;
    public static String n;
    public static UnityPlayerActivity unityPlayeractivity;
    Activity activity = UnityPlayerActivity.this;
    int AdsCount = 0;
    AdRequest adRequest;
    AdView adView;
//    private boolean v = false;
//    private boolean w = false;
//    private boolean x = false;
//    private boolean B;

    public ArrayList<ParticalCategoryModel> particalCategoryModels;
    public RecyclerView rvParticalCat;
    public ParticalCatAdapter particalCatAdapter;

    public ArrayList<ParticalItemModel> particalListModels;
    public RecyclerView rvParticalItem;
    public ParticalItemAdapter particalItemAdapter;

    public LinearLayout llBottomView;
    public RelativeLayout rlParticalView;
    public LinearLayout llParticalCat;
    public LinearLayout llParticalItem;
    public LinearLayout llBack;

    public TextView tvFilter;
    public TextView tvLights;
    private int Catid = -1;
    SharedPreferences pref;
    private boolean u;
    public RewardedVideoAd mRewardedVideoAd;
    public String p;
    public String q;
    public int r;
    public int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view;
        int systemUiVisibility;
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            view = this.getWindow().getDecorView();
            systemUiVisibility = 8;
        } else {
            if (Build.VERSION.SDK_INT < 19) {
            }
            view = this.getWindow().getDecorView();
            systemUiVisibility = 4102;
        }
        view.setSystemUiVisibility(systemUiVisibility);
        this.getWindow().setFlags(1024, 1024);
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy
        unityPlayeractivity = this;

//        if (mInterstitialAd != null) {
//            mInterstitialAd = null;
//        }
//        mInterstitialAd = new InterstitialAd(activity);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial));
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                mInterstitialAd = null;
//            }
//
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//                AdsCount++;
//            }
//
//            @Override
//            public void onAdFailedToLoad(int i) {
//                super.onAdFailedToLoad(i);
//                if (mInterstitialAd != null) {
//                    mInterstitialAd = null;
//                }
//                if (AdsCount <= 2) {
//                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
//                }
//
//            }
//        });
        mUnityPlayer = new UnityPlayer(this);
        setContentView(R.layout.unity_ui);
        ((FrameLayout) findViewById(R.id.layout_unity_main)).addView(mUnityPlayer, 0);
        layoutAdView = findViewById(R.id.llBanner_unity);
        mUnityPlayer.requestFocus();
        if (Build.VERSION.SDK_INT < 23) {
            UnityPlayer.UnitySendMessage("StaticThemeDataBase", "IsCallHomeActivity", "ok");
            Utils.CreateDirectory();
            AppGeneral.loadFFMpeg(this);
            Log.e("TAG", "On Resume SDK23");
        } else {
            RequestPermission(false, true);
        }

        pref = PreferenceManager.getDefaultSharedPreferences(activity);
        BindView();
        BannerAds();
        loadRewardedVideo();
        SetParticalCategoryList();
        SetParticalListAdapter();
    }

    private void BindView() {
        rvParticalCat = findViewById(R.id.rvParticleCat);
        rvParticalItem = findViewById(R.id.rvParticleItem);
        llBottomView = findViewById(R.id.llBottomView);
        rlParticalView = findViewById(R.id.rl_partical_view);
        llParticalCat = findViewById(R.id.ll_partical_cat);
        llParticalItem = findViewById(R.id.llParticalItem);
        llBack = findViewById(R.id.llBack);

        tvFilter = findViewById(R.id.tvFilter);
        tvLights = findViewById(R.id.tvLights);

        llBack.setOnClickListener(this);
        findViewById(R.id.llImage).setOnClickListener(this);
        findViewById(R.id.llSong).setOnClickListener(this);
        findViewById(R.id.llText).setOnClickListener(this);
        findViewById(R.id.llFilter).setOnClickListener(this);
        findViewById(R.id.llLights).setOnClickListener(this);
        findViewById(R.id.llSave).setOnClickListener(this);
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

    public void loadRewardedVideo() {
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.loadAd(getResources().getString(R.string.RewardedVideo), new AdRequest.Builder().build());
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewarded(RewardItem rewardItem) {
                UnityPlayerActivity.this.u = true;
            }


            @Override
            public void onRewardedVideoAdLoaded() {
                Log.e("VideoAds", "onRewardedVideoAdLoaded");
            }

            @Override
            public void onRewardedVideoAdOpened() {
            }

            @Override
            public void onRewardedVideoStarted() {
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Log.e("VideoAds", "onRewardedVideoAdClosed" + q);
                MyApplication.ThemeAssetSelectedPosition = -1;
                particalItemAdapter.notifyDataSetChanged();
                if (UnityPlayerActivity.this.u) {
                    UnityPlayerActivity.this.u = false;
                    UnityPlayerActivity.this.RewardedVideoAdsWatchPref();
                } else {
                    UnityPlayer.UnitySendMessage("CategoryListGenerater", "LoadParticle", UnityPlayerActivity.this.q);
                }
                UnityPlayerActivity.this.loadRewardedAds();
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Log.e("VideoAds", "onRewardedVideoAdLeftApplication");
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
            }

            @Override
            public void onRewardedVideoCompleted() {
                Log.e("VideoAds", "onRewardedVideoCompleted");
            }
        });
        this.loadRewardedAds();
    }

    public final void loadRewardedAds() {
        mRewardedVideoAd.loadAd(getResources().getString(R.string.RewardedVideo), new AdRequest.Builder().build());
    }

    public final void RewardedVideoAdsWatchPref() {
        final int j = UnityPlayerActivity.unityPlayeractivity.j;
        if (j != -1) {
            this.particalItemAdapter.notifyItemInserted(j);
        }
        final UnityPlayerActivity c = UnityPlayerActivity.unityPlayeractivity;
        final int r = this.r;
        c.j = r;
        c.particalListModels.get(r).j = true;
        this.particalItemAdapter.notifyItemInserted(this.r);
        final String a = LanguagePref.a(UnityPlayerActivity.unityPlayeractivity).a("pref_key_watch_bundle_ads", "");
        String s;
        if (a.equals("")) {
            s = this.p;
        } else {
            final StringBuilder sb = new StringBuilder();
            sb.append(a);
//            sb.append("?");
//            sb.append(this.p);
            s = sb.toString();

        }
        Log.e("downString", s);
        LanguagePref.a(UnityPlayerActivity.unityPlayeractivity).b("pref_key_watch_bundle_ads", s);
    }

    public boolean CheckFileExistWithSize(String file, int size) {
        return new File(file).exists() && new File(file).length() >= size;
    }


    private void SetParticalCategoryList() {
        particalCategoryModels = new ArrayList<>();
        rvParticalCat.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        particalCatAdapter = new ParticalCatAdapter(this);
        rvParticalCat.setItemAnimator(new DefaultItemAnimator());
        rvParticalCat.setAdapter(particalCatAdapter);
    }


    private void SetParticalListAdapter() {
        this.particalListModels = new ArrayList<ParticalItemModel>();
        rvParticalItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        particalItemAdapter = new ParticalItemAdapter(this);
        rvParticalItem.setItemAnimator(new DefaultItemAnimator());
        rvParticalItem.setAdapter(particalItemAdapter);
    }

    public void SetCategoryData(int CategoryId) {
        Catid = CategoryId;
        if (pref.getString("offlineParticles_v1.6", "").equalsIgnoreCase("")) {
            SetParticleData(getFileFormAsset());
        } else {
            SetParticleData(pref.getString("offlineParticles_v1.6", ""));
        }
    }

    public void SetParticleData(String AssetJson) {
        if (AssetJson != null) {
            particalListModels.clear();
            final String bundelpath = Utils.INSTANCE.getAssetUnityPath();
            try {
                JSONObject jsonObj = new JSONObject(AssetJson);
                String AssetUrl = jsonObj.getString("link");
                JSONArray jsonArray = jsonObj.getJSONArray("category");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    final JSONObject jsonObject = jsonArray.getJSONObject(i);
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
                        if (particalModel.getCatId() == Catid) {
                            particalListModels.add(particalModel);
                        }
                        particalItemAdapter.notifyDataSetChanged();
                    }
                }
            } catch (JSONException ex2) {
                ex2.printStackTrace();
            }
        }
    }


    public void HidePartical() {
        llParticalItem.setVisibility(View.GONE);
        llParticalCat.setVisibility(View.VISIBLE);
    }

    public void CategoryShow() {
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        loadAnimation.setAnimationListener(new Animation.AnimationListener() {
            public final void onAnimationEnd(Animation animation) {
                llParticalItem.setVisibility(View.GONE);
                llParticalCat.setVisibility(View.VISIBLE);
                Animation loadAnimation = AnimationUtils.loadAnimation(UnityPlayerActivity.this, R.anim.slide_from_left);
                loadAnimation.setAnimationListener(new Animation.AnimationListener() {
                    public final void onAnimationEnd(Animation animation) {
                    }

                    public final void onAnimationRepeat(Animation animation) {

                    }

                    public final void onAnimationStart(Animation animation) {

                    }
                });
                llParticalCat.startAnimation(loadAnimation);
            }

            public final void onAnimationRepeat(Animation animation) {

            }

            public final void onAnimationStart(Animation animation) {

            }
        });
        this.llParticalItem.startAnimation(loadAnimation);

    }

    public void CategoryHide() {
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        loadAnimation.setAnimationListener(new Animation.AnimationListener() {
            public final void onAnimationEnd(Animation animation) {
                llParticalCat.setVisibility(View.GONE);
                llParticalItem.setVisibility(View.VISIBLE);
                Animation loadAnimation = AnimationUtils.loadAnimation(UnityPlayerActivity.this, R.anim.slide_from_left);
                loadAnimation.setAnimationListener(new Animation.AnimationListener() {
                    public final void onAnimationEnd(Animation animation) {
                    }

                    public final void onAnimationRepeat(Animation animation) {

                    }

                    public final void onAnimationStart(Animation animation) {

                    }
                });
                llParticalItem.startAnimation(loadAnimation);
            }

            public final void onAnimationRepeat(Animation animation) {

            }

            public final void onAnimationStart(Animation animation) {

            }
        });
        this.llParticalCat.startAnimation(loadAnimation);
    }

    public final void showBottom() {
//        if (llBottomView != null) {
//            llBottomView.setVisibility(View.VISIBLE);
//            rlParticalView.setVisibility(View.VISIBLE);
//        }
        if (llBottomView != null) {
            llBottomView.setVisibility(View.VISIBLE);
        }
        if (rlParticalView != null) {
            rlParticalView.setVisibility(View.VISIBLE);
        }
    }

    public final void hideBottom() {
//        if (llBottomView != null) {
//            llBottomView.setVisibility(View.GONE);
//            rlParticalView.setVisibility(View.GONE);
//        }
        if (llBottomView != null) {
            llBottomView.setVisibility(View.GONE);
        }
        if (rlParticalView != null) {
            rlParticalView.setVisibility(View.GONE);
        }
    }

    public final void StyleVisibleWithoutAnimation() {
        this.rlParticalView.setVisibility(View.VISIBLE);
    }

    public final void StyleGoneWithoutAnimation() {
        this.rlParticalView.setVisibility(View.GONE);
    }

    public void SetSelection(boolean Filter, boolean Lights) {
        tvFilter.setSelected(Filter);
        tvLights.setSelected(Lights);
    }

    public void HideFilterPanel() {
        Log.e("TAG", "HideFilterPanel");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StyleVisibleWithoutAnimation();
                SetSelection(false, false);
            }
        }, 200);
    }


    private void RequestPermission(boolean z, boolean isFromFirst) {
        if ((ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            if (isFromFirst) {
                UnityPlayer.UnitySendMessage("StaticThemeDataBase", "IsCallHomeActivity", "ok");
                Log.e("TAG", "Premission Allready Granted");
                Utils.CreateDirectory();
                AppGeneral.loadFFMpeg(activity);
            }
        } else if (z) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
            alertDialogBuilder.setTitle("Necessary permission");
            alertDialogBuilder.setMessage("Allow Required Permission");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Settings",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            PremissionFromSetting(UnityPlayerActivity.this);
                        }
                    });

            alertDialogBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                UnityPlayer.UnitySendMessage("StaticThemeDataBase", "IsCallHomeActivity", "ok");
                Log.e("TAG", "Premission From Setting Granted");
                Utils.CreateDirectory();
                AppGeneral.loadFFMpeg(activity);
            } else {
                RequestPermission(true, true);
            }
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (iArr.length > 0) {
                if (iArr[0] == 0 && iArr[1] == PackageManager.PERMISSION_GRANTED) {
                    UnityPlayer.UnitySendMessage("StaticThemeDataBase", "IsCallHomeActivity", "ok");
                    Log.e("TAG", "Premission Granted");
                    Utils.CreateDirectory();
                    AppGeneral.loadFFMpeg(activity);
                } else if ((iArr[0] == -1 && !ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) || (iArr[1] == -1 && !ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE))) {
                    RequestPermission(true, true);
                }
            }
        }
    }

    public void PremissionFromSetting(UnityPlayerActivity unityPlayerActivity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", unityPlayerActivity.getPackageName(), null);
        intent.setData(uri);
        unityPlayerActivity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
    }

    //
    private void BannerAds() {
        adView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }


    // Quit Unity
    @Override
    protected void onDestroy() {
        mUnityPlayer.quit();
        super.onDestroy();
    }


    // Pause Unity
    @Override
    protected void onPause() {
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override
    protected void onResume() {
        super.onResume();
        if (particalCatAdapter != null) {
            particalCatAdapter.notifyDataSetChanged();
        }
        if (particalItemAdapter != null) {
            particalItemAdapter.notifyDataSetChanged();
        }
        if (this.tvFilter != null) {
            if (MyApplication.getInstance().getCropImages().size() > 1) {
                this.tvFilter.setText("Transition");
            } else {
                MyApplication.getInstance().getCropImages().size();
                this.tvFilter.setText("Filter");
            }
        }
        if (Build.VERSION.SDK_INT < 23) {
//            UnityPlayer.UnitySendMessage("StaticThemeDataBase", "IsCallHomeActivity", "ok");
            Utils.CreateDirectory();
            AppGeneral.loadFFMpeg(this);
            Log.e("TAG", "On Resume SDK23");
        } else {
            Log.e("TAG", "On Resume Called...");
            RequestPermission(false, false);
        }
        mUnityPlayer.resume();
    }

    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    private void SaveDilaog() {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this, R.style.AdsDialog);
        final View inflate = LayoutInflater.from(this).inflate(R.layout.export_dialog, null);
        builder.setView(inflate);
        final androidx.appcompat.app.AlertDialog dialog = builder.create();
        AdLoader.Builder Adbuilder = new AdLoader.Builder(this, getResources().getString(R.string.native_ad));
        Adbuilder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                FrameLayout frameLayout = inflate.findViewById(R.id.fl_adplaceholder);
                inflate.findViewById(R.id.tvLoadingAds).setVisibility(View.GONE);
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.ad_unifieldnativeadview_small, null);
                NativeAdvanceAds.populateUnifiedNativeAdView(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }
        });
        VideoOptions videoOptions = new VideoOptions.Builder().build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();
        Adbuilder.withNativeAdOptions(adOptions);
        AdLoader adLoader = Adbuilder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());

        inflate.findViewById(R.id.btnNormal).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                UnityPlayer.UnitySendMessage("GameManager", "SaveVideo", "360");
                AndroidUnityCall.HideBannerAds(activity);
                AndroidUnityCall.HideLyricStyles(activity);
                LanguagePref.a(UnityPlayerActivity.this).b("pref_key_export_quality", "Low");
                dialog.dismiss();
            }
        });
        inflate.findViewById(R.id.btnHd).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                UnityPlayer.UnitySendMessage("GameManager", "SaveVideo", "480");
                AndroidUnityCall.HideBannerAds(activity);
                AndroidUnityCall.HideLyricStyles(activity);
                LanguagePref.a(UnityPlayerActivity.this).b("pref_key_export_quality", "Medium");
                dialog.dismiss();
            }
        });
        inflate.findViewById(R.id.btnFullHd).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                UnityPlayer.UnitySendMessage("GameManager", "SaveVideo", "720");
                AndroidUnityCall.HideBannerAds(activity);
                AndroidUnityCall.HideLyricStyles(activity);
                LanguagePref.a(UnityPlayerActivity.this).b("pref_key_export_quality", "High");
                dialog.dismiss();
            }
        });

        ((CheckBox) inflate.findViewById(R.id.cbDoNotAsk)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                LanguagePref languagePref;
                String str;
                String str2;
                if (z) {
                    languagePref = LanguagePref.a(UnityPlayerActivity.this);
                    str = "pref_last_load_time_ads";
                    str2 = "0";
                } else {
                    languagePref = LanguagePref.a(UnityPlayerActivity.this);
                    str = "pref_last_load_time_ads";
                    str2 = "1";
                }
                languagePref.b(str, str2);
            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llBack:
                CategoryShow();
                break;
            case R.id.llImage:
                UnityPlayer.UnitySendMessage("GameManager", "StopAgainMusic", "");
                AndroidUnityCall.OpenGallery(this);
                return;
            case R.id.llSong:
                startActivity(new Intent(this, SongSelectActivity.class));
                return;
            case R.id.llText:
                UnityPlayer.UnitySendMessage("GameManager", "EditText", "");
                break;
            case R.id.llFilter:
                if (this.rlParticalView.getVisibility() == View.VISIBLE) {
                    UnityPlayer.UnitySendMessage("GameManager", "ShowFilterOrTransition", "");
                    StyleGoneWithoutAnimation();
                    Log.e("TAG", "Filteropen - Filter Click - Style Close");
                    MyApplication.StatusUpdate("Close", "Open", "Close");
                    SetSelection(true, false);
                } else {
                    if (MyApplication.FilterStatus.equalsIgnoreCase("Close")) {
                        Log.e("TAG", "filterclose - Filter Click - Filter Open");
                        UnityPlayer.UnitySendMessage("GameManager", "ShowFilterOrTransition", "");
                        StyleGoneWithoutAnimation();
                        MyApplication.StatusUpdate("Close", "Open", "Close");
                        SetSelection(true, false);
                    }
                }
                break;
            case R.id.llLights:
                if (this.rlParticalView.getVisibility() == View.VISIBLE) {
                    UnityPlayer.UnitySendMessage("GameManager", "ShowLayerAndLights", "");
                    StyleGoneWithoutAnimation();
                    Log.e("TAG", "Lightsopen - Lights Click - Style Close");
                    MyApplication.StatusUpdate("Close", "Close", "Open");
                    SetSelection(false, true);
                } else {
                    if (MyApplication.LightsStatus.equalsIgnoreCase("Close")) {
                        Log.e("TAG", "Lightsclose - Lights Click - Lights Open");
                        UnityPlayer.UnitySendMessage("GameManager", "ShowLayerAndLights", "");
                        StyleGoneWithoutAnimation();
                        MyApplication.StatusUpdate("Close", "Close", "Open");
                        SetSelection(false, true);
                    }
                }
                break;
            case R.id.llSave:
                String str;
                if (LanguagePref.a(this).a("pref_last_load_time_ads", "1").equals("1")) {
                    SaveDilaog();
                } else {
                    str = LanguagePref.a(this).a("pref_key_export_quality", "Medium");
                    AndroidUnityCall.HideBannerAds(activity);
                    AndroidUnityCall.HideLyricStyles(activity);
                    if (str.equals("Low")) {
                        UnityPlayer.UnitySendMessage("GameManager", "SaveVideo", "360");
                        return;
                    } else if (str.equals("Medium")) {
                        UnityPlayer.UnitySendMessage("GameManager", "SaveVideo", "480");
                        return;
                    } else if (str.equals("High")) {
                        UnityPlayer.UnitySendMessage("GameManager", "SaveVideo", "720");
                        return;
                    } else {
                        UnityPlayer.UnitySendMessage("GameManager", "SaveVideo", "480");
                        return;
                    }
                }
                break;

        }
    }

}
