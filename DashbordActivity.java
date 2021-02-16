package com.wavymusic.DashBord.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.AppUpdate.Constants;
import com.AppUpdate.InAppUpdateManager;
import com.AppUpdate.InAppUpdateStatus;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.root.UnitySendValue.AppGeneral;
import com.wavymusic.App.MyApplication;
import com.wavymusic.AppUtils.Utils;
import com.wavymusic.DashBord.Fragment.ThemeHomeFragmentUv;
import com.wavymusic.DashBord.Fragment.ThemeHomeFragmentWavy;
import com.wavymusic.DashBord.Model.CategoryModel;
import com.wavymusic.DashBord.Model.ThemeHomeModel;
import com.wavymusic.DashBord.Model.ThemeHomeModelUv;
import com.wavymusic.DashBord.View.ViewPager.CustomViewPager;
import com.wavymusic.ExitApplication.activity.ExitAppActivity;
import com.wavymusic.Favourite.Activity.FavouriteActivity;
import com.wavymusic.MoreApp.MoreAppActivity;
import com.wavymusic.MyCreationVideo.activity.YourVideoActivity;
import com.wavymusic.Partical.Model.ParticalCategoryModel;
import com.wavymusic.Partical.Model.ParticalItemModel;
import com.wavymusic.R;
import com.wavymusic.RetrofitApiCall.APIClient;
import com.wavymusic.RetrofitApiCall.APIInterface;
import com.wavymusic.RetrofitApiCall.AppConstant;
import com.wavymusic.Setting.SettingActivity;
import com.wavymusic.UnityPlayerActivity;
import com.wavymusic.notification.activity.NotificationActivity;
import com.wavymusic.notification.utils.NotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashbordActivity extends AppCompatActivity implements View.OnClickListener, InAppUpdateManager.InAppUpdateHandler {

    public Activity activity = DashbordActivity.this;
    RelativeLayout rlLoading;
    LinearLayout layoutHomeMain;
    LinearLayout llRetry;
    Button btnRetry;
    TextView tv_prg_msg;
    private TabLayout tabLayoutWavy;
    private CustomViewPager viewPagerWavy;
    TextView tvViewAllWavy, tvViewAllUv;
    private TabLayout tabLayoutUv;
    private CustomViewPager viewPagerUv;
    private ImageView ivSetting, ivMyCreation, ivFavourite;
    //    private ImageView ivMoreApp;
    private ViewPagerAdapterWavy wavyAdapter;
    private ViewPagerAdapterUv uvAdapter;
    ArrayList<CategoryModel> tabcategorylistWavy = new ArrayList<>();
    ArrayList<CategoryModel> tabcategorylistUv = new ArrayList<>();
    private ArrayList<ThemeHomeModel> WhatsNewListWavy = new ArrayList<>();
    private ArrayList<ThemeHomeModelUv> WhatsNewListUv = new ArrayList<>();
    APIInterface apiInterface;
    APIInterface apiInterfaceUv;
    public boolean IsFromLanguage;
    public boolean IsFromMain;

    boolean isSetupReady = false;
    public boolean isApiRunning = false;
    public boolean isParticlesApiRunning = false;

    public SharedPreferences pref;

    private AdView adView;

    //    SliderView sliderHome;
//    ArrayList<SliderModel> sliderList = new ArrayList<>();
//    SliderAdpater sliderAdpater;
    public boolean IsNotification;

    //App Update
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private InAppUpdateManager inAppUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
        MyApplication.DashbordAct = activity;
        pref = PreferenceManager.getDefaultSharedPreferences(activity);
        IsFromLanguage = getIntent().getBooleanExtra("IsFromLanguage", false);
        IsFromMain = getIntent().getBooleanExtra("IsFromMain", false);
        IsNotification = getIntent().getBooleanExtra("IsFromNotification", false);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        apiInterfaceUv = APIClient.getClientUv().create(APIInterface.class);
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryId("111");
        categoryModel.setName("New");
        tabcategorylistWavy.add(categoryModel);

        CategoryModel categoryModelUv = new CategoryModel();
        categoryModelUv.setCategoryId("110");
        categoryModelUv.setName("Whatsnew");
        tabcategorylistUv.add(categoryModelUv);
        PutAnalyticsEvent();
        Utils.INSTANCE.CopyAssets(activity);
        BindView();
        SetListener();
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(activity, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                FirebaseMessaging.getInstance().subscribeToTopic(NotificationConfig.TOPIC_GLOBAL);
            }
        });
        MyApplication.isEditCallWavy = false;
        SetDashbordThemJsonData();
        VerifyToSetOfflineThemeDataDashbord();
        BannerAds();
        AppGeneral.loadFFMpeg(this);
//        SetSliderAdapter();
        inAppUpdateManager = InAppUpdateManager.Builder(this, REQ_CODE_VERSION_UPDATE).resumeUpdates(true).mode(Constants.UpdateMode.FLEXIBLE).handler(this);
        if (IsNotification) {
            Log.e("TAG", "DahsbordIsNotification" + IsNotification);
            Intent intent = new Intent(activity, NotificationActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void SetDashbordThemJsonData() {
        if (Utils.checkConnectivity(activity, false)) {
            if (pref.getString("DashbordofflineResponse", "").equalsIgnoreCase("")) {
                if (!isApiRunning) {
                    isApiRunning = true;
                    GetDashbordTheme();
                } else if (IsFromLanguage && MyApplication.TempcallWavy != null) {
                    MyApplication.TempcallWavy.cancel();
                    isApiRunning = true;
                    GetDashbordTheme();
                }
            } else if (((new Date().getTime() - pref.getLong("DashbordofflineResponseTime", 1588598205L)) >= AppConstant.ApiUpdateTime)) {
                if (!isApiRunning) {
                    isApiRunning = true;
                    GetDashbordTheme();
                }
            } else if (IsFromLanguage) {
                if (!isApiRunning) {
                    isApiRunning = true;
                    GetDashbordTheme();
                } else if (MyApplication.TempcallWavy != null) {
                    MyApplication.TempcallWavy.cancel();
                    isApiRunning = true;
                    GetDashbordTheme();
                }
            }
        } else if (pref.getString("DashbordofflineResponse", "").equalsIgnoreCase("")) {
            layoutHomeMain.setVisibility(View.GONE);
            llRetry.setVisibility(View.VISIBLE);
            Log.e("TAG", "No Internet");
        }
    }

    private void VerifyToSetOfflineThemeDataDashbord() {
        if (pref.getString("DashbordofflineResponse", "").equalsIgnoreCase("")) {
            isSetupReady = true;
        } else if (!IsFromLanguage) {
            SetupDataInLayoutDashbord(pref.getString("DashbordofflineResponse", ""));
        } else {
            isSetupReady = true;
        }
    }

    private void GetDashbordTheme() {
        final Handler handler = new Handler();
        if (pref.getString("DashbordofflineResponse", "").equalsIgnoreCase("") || IsFromLanguage) {
            handler.postDelayed(new Runnable() {
                public void run() {
                    tv_prg_msg.setText("Slow Internet Connection");
                }
            }, 10000);
        }
        llRetry.setVisibility(View.GONE);
        rlLoading.setVisibility(View.VISIBLE);
        Call<JsonObject> call = apiInterfaceUv.GetDashbordTheme(AppConstant.Token, AppConstant.ApplicationId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        if (handler != null) {
                            handler.removeCallbacksAndMessages(null);
                        }
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response.body()));
                        SetOfflineCategory(activity, jsonObj.toString(), "DashbordofflineResponse");
                        //Set Response Time
                        SetOfflineResponseTime(activity, new Date(), "DashbordofflineResponseTime");
                        isApiRunning = false;
                        if (isSetupReady) {
                            isSetupReady = false;
                            SetupDataInLayoutDashbord(jsonObj.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                isApiRunning = false;
                rlLoading.setVisibility(View.GONE);
                if (pref.getString("DashbordofflineResponse", "").equalsIgnoreCase("")) {
                    llRetry.setVisibility(View.VISIBLE);
                    Toast.makeText(activity, "Data/wifi Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void SetupDataInLayoutDashbord(String response) {
        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONObject jsonObjectUvurl = jsonObj.getJSONObject("aws_url_uv");
            /*UvUrl*/
            AppConstant.ThemeThumbUv = jsonObjectUvurl.getString("theme_thumb_url");
            AppConstant.AnimUrlUv = jsonObjectUvurl.getString("animation_url");
            AppConstant.SoundUrlUv = jsonObjectUvurl.getString("souns_url");

            /*WavyUrl*/
            JSONObject jsonObjectWavyurl = jsonObj.getJSONObject("aws_url_beats");
            AppConstant.ThemeThumburlWavy = jsonObjectWavyurl.getString("theme_thumb_url");
            AppConstant.SmallThemeThumbrlWavy = jsonObjectWavyurl.getString("smll_thumb_url");
            AppConstant.SoundUrlWavy = jsonObjectWavyurl.getString("souns_url");


            /*Wavy Music Cateogry*/
            JSONArray tabcategorywavy = jsonObj.getJSONArray("beats");
            for (int i = 0; i < tabcategorywavy.length(); i++) {
                JSONObject categoryJSONObjectWavy = tabcategorywavy.getJSONObject(i);
                CategoryModel categoryModel = new CategoryModel();
                String CategoryId = categoryJSONObjectWavy.getString("id");
                SetOfflineCategory(activity, categoryJSONObjectWavy.toString(), CategoryId);
                categoryModel.setCategoryId(categoryJSONObjectWavy.getString("id"));
                categoryModel.setName(categoryJSONObjectWavy.getString("name"));
                tabcategorylistWavy.add(categoryModel);
                //What'sNewData
                JSONArray jSONArray4 = categoryJSONObjectWavy.getJSONArray("themes");
                for (int j = 0; j < jSONArray4.length(); j++) {
                    ThemeHomeModel themeModel = new ThemeHomeModel();
                    JSONObject jsonobjecttheme = jSONArray4.getJSONObject(j);
                    if (jsonobjecttheme.getString("is_release").equalsIgnoreCase("1")) {
                        themeModel.setThemeid(jsonobjecttheme.getString("id"));
                        themeModel.setCategoryid(jsonobjecttheme.getString("category"));
                        themeModel.setThemeName(jsonobjecttheme.getString("theme_name"));
                        themeModel.setImage(jsonobjecttheme.getString("theme_thumbnail"));
                        themeModel.setSmall_Thumbnail(jsonobjecttheme.getString("small_thumbnail"));
                        themeModel.setAnimsoundurl(jsonobjecttheme.getString("sound_file"));
                        themeModel.setAnimSoundname(jsonobjecttheme.getString("sound_filename") + ".mp3");
                        themeModel.setAnimSoundPath(new File(Utils.PathOfThemeFolder).getAbsolutePath() + File.separator + themeModel.getAnimSoundname());
                        themeModel.isAvailableOffline = CheckFileSize(new File(Utils.PathOfThemeFolder).getAbsolutePath() + File.separator + themeModel.getAnimSoundname());
                        themeModel.setAnimSoundfilesize(Integer.parseInt(jsonobjecttheme.getString("sound_size")));
                        themeModel.setGameobjectName(jsonobjecttheme.getString("game_object"));
                        themeModel.setThemeCounter(jsonobjecttheme.getString("downloads"));
                        themeModel.setNewRealise(jsonobjecttheme.getString("is_release"));
                        if (themeModel.isNewRealise().equals("1")) {
                            WhatsNewListWavy.add(themeModel);
                        }
                    }
                }
            }
            /*United Video Category*/
            JSONArray tabcategoryuv = jsonObj.getJSONArray("uv");
            for (int i = 0; i < tabcategoryuv.length(); i++) {
                JSONObject categoryJSONObjectuv = tabcategoryuv.getJSONObject(i);
                CategoryModel categoryModel = new CategoryModel();
                String CategoryId = categoryJSONObjectuv.getString("id");
                SetOfflineCategory(activity, categoryJSONObjectuv.toString(), CategoryId);
                categoryModel.setCategoryId(categoryJSONObjectuv.getString("id"));
                categoryModel.setName(categoryJSONObjectuv.getString("name"));
                tabcategorylistUv.add(categoryModel);

                //What'sNewData
                JSONArray jSONArrayUv = categoryJSONObjectuv.getJSONArray("themes");
                for (int j = 0; j < jSONArrayUv.length(); j++) {
                    ThemeHomeModelUv themeModel = new ThemeHomeModelUv();
                    JSONObject jsonobjecttheme = jSONArrayUv.getJSONObject(j);
                    if (jsonobjecttheme.getString("is_release").equalsIgnoreCase("1")) {
                        themeModel.setThemeid(jsonobjecttheme.getString("theme_id"));
                        themeModel.setCategoryid(jsonobjecttheme.getString("category"));
                        themeModel.setVideoType(jsonobjecttheme.getString("video_type"));
                        themeModel.setThemeName(jsonobjecttheme.getString("theme_name"));
                        themeModel.setImage(jsonobjecttheme.getString("theme_thumbnail"));
                        themeModel.setBundelUrl(jsonobjecttheme.getString("animation_file"));
                        themeModel.setBundelName(jsonobjecttheme.getString("animation_file_name") + ".unity3d");
                        themeModel.setPrefebName(jsonobjecttheme.getString("animation_file_name"));
                        themeModel.setBundelPath(new File(Utils.PathOfThemeFolder).getAbsolutePath() + File.separator + themeModel.getBundelName());
                        themeModel.setBundelSize(jsonobjecttheme.getString("animation_file_size"));
                        themeModel.setAnimsoundurl(jsonobjecttheme.getString("sound_file"));
                        themeModel.setAnimSoundname(jsonobjecttheme.getString("sound_file_name") + ".mp3");
                        themeModel.setAnimSoundPath(new File(Utils.PathOfThemeFolder).getAbsolutePath() + File.separator + themeModel.getAnimSoundname());
                        themeModel.setAnimSoundfilesize(Integer.parseInt(jsonobjecttheme.getString("sound_file_size")));
                        themeModel.setImageWidht(Integer.parseInt(jsonobjecttheme.getString("width")));
                        themeModel.setImageHeight(Integer.parseInt(jsonobjecttheme.getString("height")));
                        themeModel.isAvailableOffline = CheckFileExistWithSize(new File(Utils.PathOfThemeFolder).getAbsolutePath() + File.separator + themeModel.getBundelName(), Integer.parseInt(themeModel.getBundelSize()));
                        themeModel.setImg_no_of(Integer.parseInt(jsonobjecttheme.getString("number_of_images")));
                        themeModel.setGameobjectName(jsonobjecttheme.getString("game_object"));
                        themeModel.setThemeCounter(jsonobjecttheme.getString("total_download"));
                        themeModel.setIsNewRealise(jsonobjecttheme.getString("is_release"));
                        if (themeModel.getIsNewRealise().equals("1")) {
                            WhatsNewListUv.add(themeModel);
                        }
                    }
                }
            }
            SetOfflineCategory(activity, Utils.WhatsNewJsonWavy(WhatsNewListWavy), "NewThemeWavy");
            setUpPagerWavy();
            SetTabLayoutWavy();

            SetOfflineCategory(activity, Utils.WhatsNewJsonUv(WhatsNewListUv), "NewThemeUv");
            setUpPagerUv();
            SetTabLayoutUv();
            rlLoading.setVisibility(View.GONE);
            SetAssetTheme();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void PutAnalyticsEvent() {
        FirebaseAnalytics mFirebaseAnalytics;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "DashbordActivity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }


    private void BindView() {
//        sliderHome = findViewById(R.id.slider_home);
        rlLoading = findViewById(R.id.rl_loading);
        llRetry = findViewById(R.id.llRetry);
        layoutHomeMain = findViewById(R.id.ll_home_main);
        btnRetry = findViewById(R.id.btnRetry);
        tv_prg_msg = findViewById(R.id.tv_prg_msg);
        tv_prg_msg.setText("Please wait…");
        tabLayoutWavy = findViewById(R.id.tab_wavy);
        viewPagerWavy = findViewById(R.id.vp_wavy);
        tvViewAllWavy = findViewById(R.id.tv_viewAll_Wavy);
        tvViewAllUv = findViewById(R.id.tv_viewAll_Uv);
        tabLayoutUv = findViewById(R.id.tab_uv);
        viewPagerUv = findViewById(R.id.vp_uv);
        ivSetting = findViewById(R.id.iv_setting);
        ivMyCreation = findViewById(R.id.iv_my_creation);
        ivFavourite = findViewById(R.id.iv_favourite);
//        ivMoreApp = findViewById(R.id.iv_home);
        tvViewAllWavy.setOnClickListener(this);
        tvViewAllUv.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        ivMyCreation.setOnClickListener(this);
        ivFavourite.setOnClickListener(this);
//        ivMoreApp.setOnClickListener(this);
    }

  /*  private void SetSliderAdapter() {
        sliderList.add(0, new SliderModel("https://beatsadmin.s3.ap-south-1.amazonaws.com/thumb/1598020791SunMeriSehzadi.png"));
        sliderList.add(1, new SliderModel("https://beatsadmin.s3.ap-south-1.amazonaws.com/thumb/15969710234925a22b52f81d5cdab6505cce3c12a3fc2.png"));
        sliderList.add(2, new SliderModel("https://beatsadmin.s3.ap-south-1.amazonaws.com/thumb/1598020943YeMosamKiBaris.png"));
        sliderList.add(3, new SliderModel("https://beatsadmin.s3.ap-south-1.amazonaws.com/thumb/1598020892YeHasiWadiyaYehKhulaAasmaan.png"));
        sliderList.add(4, new SliderModel("https://beatsadmin.s3.ap-south-1.amazonaws.com/thumb/1596971183c5bbdf83249e97db3890bf51bfcf748d.png"));
        sliderList.add(5, new SliderModel("https://beatsadmin.s3.ap-south-1.amazonaws.com/thumb/1598020560DilMeraBlast.png"));
        sliderAdpater = new SliderAdpater(activity, sliderList);
        sliderHome.setSliderAdapter(sliderAdpater);
        sliderHome.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderHome.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderHome.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderHome.setIndicatorSelectedColor(getResources().getColor(R.color.white));
        sliderHome.setIndicatorUnselectedColor(getResources().getColor(R.color.slide_unselected));
        sliderHome.setScrollTimeInSec(3);
        sliderHome.setAutoCycle(true);
        sliderHome.startAutoCycle();
    }*/

    private void SetListener() {
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkConnectivity(activity, false)) {
                    layoutHomeMain.setVisibility(View.VISIBLE);
                    llRetry.setVisibility(View.GONE);
                    GetDashbordTheme();
                } else {
                    Toast.makeText(activity, "No Internet Connecation!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void BannerAds() {
        adView = new AdView(this, getResources().getString(R.string.fb_banner), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();
    }

    public void ShowBannerAds() {
        try {
            UnityPlayerActivity.layoutAdView.setVisibility(View.VISIBLE);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_viewAll_Wavy:
                GoToViewAllThemeWavy();
                break;
            case R.id.tv_viewAll_Uv:
                GoToViewAllThemeUv();
                break;
            case R.id.iv_setting:
                GoToSetting();
                break;
            case R.id.iv_my_creation:
                if (MyApplication.fbinterstitialAd != null && MyApplication.fbinterstitialAd.isAdLoaded()) {
                    MyApplication.AdsId = 10;
                    MyApplication.AdsShowContext = activity;
                    MyApplication.fbinterstitialAd.show();
                } else {
                    GoToMyCreation();
                }
                break;
            case R.id.iv_favourite:
                if (MyApplication.fbinterstitialAd != null && MyApplication.fbinterstitialAd.isAdLoaded()) {
                    MyApplication.AdsId = 12;
                    MyApplication.AdsShowContext = activity;
                    MyApplication.fbinterstitialAd.show();
                } else {
                    GoToFavourite();
                }
                break;
          /*  case R.id.iv_home:
                GoToMoreApp();
                break;*/
        }
    }

    private void GoToViewAllThemeWavy() {
        Intent intent = new Intent(activity, ThemeAllActivityWavy.class);
        intent.putExtra("IsFromLanguage", IsFromLanguage);
        startActivity(intent);
        finish();
    }

    private void GoToViewAllThemeUv() {
        Intent intent = new Intent(activity, ThemeAllActivityUv.class);
        intent.putExtra("IsFromLanguage", IsFromLanguage);
        startActivity(intent);
        finish();
    }

    private void GoToSetting() {
        startActivity(new Intent(activity, SettingActivity.class));
        finish();
    }

    private void GoToMyCreation() {
        startActivity(new Intent(activity, YourVideoActivity.class));
        finish();
    }

    private void GoToMoreApp() {
        startActivity(new Intent(activity, MoreAppActivity.class));
        finish();
    }

    private void GoToFavourite() {
        Intent intent = new Intent(activity, FavouriteActivity.class);
        intent.putExtra("IsFrom", "Uv");
        intent.putExtra("IsMain", "Home");
        startActivity(intent);
        finish();
    }

   /* private void RateApp() {
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
             shareIntent.putExtra(Intent.EXTRA_SUBJECT, "United Videos");
             String shareMessage = "\nGet free United Videos at here:";
             shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + activity.getPackageName() + "\n\n";
             shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
             startActivity(Intent.createChooser(shareIntent, "choose one"));
         } catch (Exception e) {
             e.printStackTrace();
         }
     }*/

    private void SetOfflineCategory(Context c, String userObject, String key) {
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

    public class ViewPagerAdapterWavy extends FragmentPagerAdapter {

        public ViewPagerAdapterWavy(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return ThemeHomeFragmentWavy.newInstance(Integer.parseInt(tabcategorylistWavy.get(position).getCategoryId()));
        }

        @Override
        public int getCount() {
            return tabcategorylistWavy.size();
        }


        public View getTabView(int position) {
            View tabCatView = LayoutInflater.from(activity).inflate(R.layout.row_category_item, null);
            TextView tv = tabCatView.findViewById(R.id.tvCategoryName);
            ImageView ivCat = tabCatView.findViewById(R.id.iv_cat);
            tv.setText(tabcategorylistWavy.get(position).getName());
            ivCat.setImageResource(Utils.INSTANCE.ThemHomeCatThum(tabcategorylistWavy.get(position).getName()));
            return tabCatView;
        }
    }

    public class ViewPagerAdapterUv extends FragmentPagerAdapter {

        public ViewPagerAdapterUv(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return ThemeHomeFragmentUv.newInstance(Integer.parseInt(tabcategorylistUv.get(position).getCategoryId()));
        }


        @Override
        public int getCount() {
            return tabcategorylistUv.size();
        }


        public View getTabView(int position) {
            View tabCatView = LayoutInflater.from(activity).inflate(R.layout.row_category_item, null);
            TextView tv = tabCatView.findViewById(R.id.tvCategoryName);
            ImageView ivCat = tabCatView.findViewById(R.id.iv_cat);
            tv.setText(tabcategorylistUv.get(position).getName());
            ivCat.setImageResource(Utils.INSTANCE.ThemHomeCatThum(tabcategorylistUv.get(position).getName()));
            return tabCatView;
        }
    }

    private void setUpPagerWavy() {
        wavyAdapter = new ViewPagerAdapterWavy(getSupportFragmentManager());
        int i;
        viewPagerWavy.setAdapter(wavyAdapter);
        if (MyApplication.CatSelectedPositionWavy != -1) {
            i = MyApplication.CatSelectedPositionWavy;
        } else {
            i = 0;
        }
        viewPagerWavy.setCurrentItem(i);
        tabLayoutWavy.setupWithViewPager(viewPagerWavy);
    }

    private void setUpPagerUv() {
        uvAdapter = new ViewPagerAdapterUv(getSupportFragmentManager());
        int i;
        viewPagerUv.setAdapter(uvAdapter);
        if (MyApplication.CatSelectedPositionUv != -1) {
            i = MyApplication.CatSelectedPositionUv;
        } else {
            i = 0;
        }
        viewPagerUv.setCurrentItem(i);
        tabLayoutUv.setupWithViewPager(viewPagerUv);
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void SetTabLayoutWavy() {
        for (int i = 0; i < tabLayoutWavy.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayoutWavy.getTabAt(i);
            tab.setCustomView(wavyAdapter.getTabView(i));
        }
        ImageView ivCat = tabLayoutWavy.getTabAt(tabLayoutWavy.getSelectedTabPosition()).getCustomView().findViewById(R.id.iv_cat);
        ivCat.setImageResource(Utils.INSTANCE.ThemHomeCatThumSelected(tabcategorylistWavy.get(tabLayoutWavy.getSelectedTabPosition()).getName()));
        tabLayoutWavy.getTabAt(0).getCustomView().setSelected(true);
        tabLayoutWavy.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                LinearLayout llIndictor = customView.findViewById(R.id.ll_indictor);
                llIndictor.setVisibility(View.VISIBLE);
                ImageView ivCat = customView.findViewById(R.id.iv_cat);
                ivCat.setImageResource(Utils.INSTANCE.ThemHomeCatThumSelected(tabcategorylistWavy.get(tab.getPosition()).getName()));
                MyApplication.ThemePositionWavy = 0;
                MyApplication.CatSelectedPositionWavy = tab.getPosition();

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                LinearLayout llIndictor = customView.findViewById(R.id.ll_indictor);
                llIndictor.setVisibility(View.GONE);
                ImageView ivCat = customView.findViewById(R.id.iv_cat);
                ivCat.setImageResource(Utils.INSTANCE.ThemHomeCatThum(tabcategorylistWavy.get(tab.getPosition()).getName()));
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    View customView = tab.getCustomView();
                    ImageView ivCat = customView.findViewById(R.id.iv_cat);
                    ivCat.setImageResource(Utils.INSTANCE.ThemHomeCatThumSelected(tabcategorylistWavy.get(tab.getPosition()).getName()));
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void SetTabLayoutUv() {
        for (int i = 0; i < tabLayoutUv.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayoutUv.getTabAt(i);
            tab.setCustomView(uvAdapter.getTabView(i));
        }
        ImageView ivCat = tabLayoutUv.getTabAt(tabLayoutUv.getSelectedTabPosition()).getCustomView().findViewById(R.id.iv_cat);
        ivCat.setImageResource(Utils.INSTANCE.ThemHomeCatThumSelected(tabcategorylistUv.get(tabLayoutUv.getSelectedTabPosition()).getName()));
        tabLayoutUv.getTabAt(0).getCustomView().setSelected(true);
        tabLayoutUv.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                LinearLayout llIndictor = customView.findViewById(R.id.ll_indictor);
                llIndictor.setVisibility(View.VISIBLE);
                ImageView ivCat = customView.findViewById(R.id.iv_cat);
                ivCat.setImageResource(Utils.INSTANCE.ThemHomeCatThumSelected(tabcategorylistUv.get(tab.getPosition()).getName()));
                MyApplication.ThemePositionUv = 0;
                MyApplication.CatSelectedPositionUv = tab.getPosition();
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                LinearLayout llIndictor = customView.findViewById(R.id.ll_indictor);
                llIndictor.setVisibility(View.GONE);
                ImageView ivCat = customView.findViewById(R.id.iv_cat);
                ivCat.setImageResource(Utils.INSTANCE.ThemHomeCatThum(tabcategorylistUv.get(tab.getPosition()).getName()));
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    View customView = tab.getCustomView();
                    ImageView ivCat = customView.findViewById(R.id.iv_cat);
                    ivCat.setImageResource(Utils.INSTANCE.ThemHomeCatThumSelected(tabcategorylistUv.get(tab.getPosition()).getName()));
                }
            }
        });
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

    private void SetAssetTheme() {
        if (Utils.checkConnectivity(activity, false)) {
            if (pref.getString("offlineParticles", "").equalsIgnoreCase("")) {
                SetParticlesData(getFileFormAsset());
                if (!isParticlesApiRunning) {
                    ParticalWavy();
                }
            } else if (((new Date().getTime() - pref.getLong("offlineParticlesTime_v1.7", 1588598205L)) >= AppConstant.ApiUpdateTime)) {
                SetParticlesData(pref.getString("offlineParticles", ""));
                if (!isParticlesApiRunning) {
                    ParticalWavy();
                }
            } else {
                SetParticlesData(pref.getString("offlineParticles", ""));
            }
        } else {
            if (pref.getString("offlineParticles", "").equalsIgnoreCase("")) {
                SetParticlesData(getFileFormAsset());
            } else {
                SetParticlesData(pref.getString("offlineParticles", ""));
            }
        }
    }

    private void ParticalWavy() {
        isParticlesApiRunning = true;
        Call<JsonObject> call = apiInterfaceUv.ParticalWavy(AppConstant.Token, AppConstant.ParticleApplicationId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response.body()));
                        //Set Response In Offline
                        SetOfflineParticle(activity, jsonObj.toString(), "offlineParticles");
                        //Set Response Time
                        SetOfflineParticleTime(activity, new Date(), "offlineParticlesTime");
                        isParticlesApiRunning = false;
                        if (!MyApplication.isEditCallWavy) {
                            SetParticlesData(jsonObj.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public void SetParticlesData(String result) {
        if (result != null) {
            Utils.INSTANCE.particalAssetModels.clear();
            UnityPlayerActivity.unityPlayeractivity.particalCategoryModels.clear();
            final String bundelpath = Utils.INSTANCE.getAssetUnityPath();
            try {
                JSONObject jsonObj = new JSONObject(result);
                String AssetUrl = jsonObj.getString("theme_bundle_url");
                String ParticalThumbUrl = jsonObj.getString("theme_thumb_url");
                JSONArray jsonArray = jsonObj.getJSONArray("category");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    final JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ParticalCategoryModel particalCategoryModel = new ParticalCategoryModel();
                    particalCategoryModel.setCatId(jsonObject.getString("id"));
                    particalCategoryModel.setCatName(jsonObject.getString("name"));
                    UnityPlayerActivity.unityPlayeractivity.particalCategoryModels.add(particalCategoryModel);
                    JSONArray jSONArray4 = jsonObject.getJSONArray("themes");
                    for (int j = 0; j < jSONArray4.length(); j++) {
                        JSONObject themeJSONObject = jSONArray4.getJSONObject(j);
                        final ParticalItemModel particalModel = new ParticalItemModel();
                        particalModel.setThemeId(Integer.parseInt(themeJSONObject.getString("theme_id")));
                        particalModel.setParticalCatName(particalCategoryModel.getCatName());
                        particalModel.setCatId(Integer.parseInt(themeJSONObject.getString("cat_name")));
                        particalModel.setThemeName(themeJSONObject.getString("theme_name"));
                        particalModel.setBundelName(themeJSONObject.getString("theme_name") + ".zip");
                        particalModel.setBundelSize(Integer.parseInt(themeJSONObject.getString("bundle_size")));
                        particalModel.isAvailableOffline = CheckFileExistWithSize(new File(Utils.PathOfAssetFolder).getAbsolutePath() + File.separator + particalModel.getBundelName(), particalModel.getBundelSize());
                        particalModel.setGmaeObjName(themeJSONObject.getString("game_object_name"));
                        particalModel.setIsPreimum(themeJSONObject.getString("is_premuim"));
                        if (themeJSONObject.getString("from_asset").equals("true")) {
                            particalModel.setFromAsset(true);
                            particalModel.setThemeBundel(AssetUrl + themeJSONObject.getString("theme_bundle"));
                            particalModel.setThumbImage(ParticalThumbUrl + themeJSONObject.getString("theme_thumbnail"));
                        } else {
                            particalModel.setFromAsset(false);
                            particalModel.setThemeBundel(AssetUrl + themeJSONObject.getString("theme_bundle"));
                            particalModel.setThumbImage(ParticalThumbUrl + themeJSONObject.getString("theme_thumbnail"));
                        }
                        final StringBuilder sb2 = new StringBuilder();
                        sb2.append(bundelpath);
                        sb2.append(particalModel.getThemeName());
                        sb2.append(File.separator);
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
        }
    }

    @Override
    public void onBackPressed() {
        if (MyApplication.fbinterstitialAd != null && MyApplication.fbinterstitialAd.isAdLoaded()) {
            MyApplication.AdsId = 3;
            MyApplication.AdsShowContext = activity;
            MyApplication.fbinterstitialAd.show();
        } else {
            GoToExit();
        }
    }

    private void GoToExit() {
        startActivity(new Intent(activity, ExitAppActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQ_CODE_VERSION_UPDATE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                // If the update is cancelled by the user,
                // you can request to start the update again.
                inAppUpdateManager.checkForAppUpdate();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onInAppUpdateError(int code, Throwable error) {
        Log.e("TAG", "code: " + code, error);
    }


    @Override
    public void onInAppUpdateStatus(InAppUpdateStatus status) {
        Log.e("TAG", "OnInAppUpdateStatus" + status.toString());
    }
}