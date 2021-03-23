package com.esc.tarotcardreading;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener;

import com.esc.tarotcardreading.NativeAds.NativeAdvanceAds;
import com.esc.tarotcardreading.kprogresshud.KProgressHUD;
import com.esc.tarotcardreading.preference.EPreferences;
import com.google.ads.consent.ConsentInformation;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class TaroQuestionsActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 111;
    public static final String MyPREFERENCES = "MyPrefs";
    static final String NOTIFICATION_COUNT = "notificationCount";
    static Activity activity = null;
    public static Runnable changeAdBool = new Runnable() {
        public void run() {
            StringBuilder sb = new StringBuilder();
            sb.append("initil value");
            sb.append(TaroQuestionsActivity.interstitialTimer);
            Log.e("interstitial", sb.toString());
            String str = "AAAAA";
            if (TaroQuestionsActivity.i == 0) {
                TaroQuestionsActivity.i++;
                TaroQuestionsActivity.startbool = true;
                int i = TaroQuestionsActivity.interstitialTimer;
                if (TaroQuestionsActivity.sharedpreferences.getInt("applaunched", 0) <= 3) {
                    i = TaroQuestionsActivity.interstitialTimer * 5;
                }
                TaroQuestionsActivity.mHandler.postDelayed(TaroQuestionsActivity.changeAdBool, i);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("");
                sb2.append(i);
                Log.e("interstitial timer", sb2.toString());
                StringBuilder sb3 = new StringBuilder();
                sb3.append("*****  ");
                sb3.append(TaroQuestionsActivity.i);
                Log.e(str, sb3.toString());
                return;
            }
            TaroQuestionsActivity.showbool = true;
            Log.e(str, "true");
            TaroQuestionsActivity.i = 0;
            TaroQuestionsActivity.stopbool = true;
            TaroQuestionsActivity.stopRunnable();
        }
    };
    static ConsentInformation consentInformation = null;
    public static Editor editor;
    public static boolean exitbool = false;
    public static Typeface face1;
    public static Typeface face2;
    public static Typeface face3;
    public static Typeface faceg;
    static int i = 0;
    public static int interstitialTimer;
    public static String locale = "";
    public static Handler mHandler = new Handler();
    public static int mNotificationCount;
    public static Boolean s_popupads = Boolean.valueOf(false);
    public static SharedPreferences sharedpreferences;
    public static boolean showPopupAds = false;
    public static boolean showbool = false;
    public static boolean showchange = false;
    public static boolean showdecisioninlang = false;
    public static boolean showdestiny = false;
    public static boolean showdreams = false;
    public static boolean showkeyoflifeinlang = false;
    public static boolean showloveinlang = false;
    public static boolean showopportunities = false;
    public static boolean showrelationshippotential = false;
    public static boolean showrelationshippurpose = false;
    public static boolean showwork = false;
    public static boolean startbool = false;
    public static boolean stopbool = false;
    static ArrayList<String> tarosubcat = new ArrayList<>();
    static ArrayList<String> tarosubcat2 = new ArrayList<>();
    static ArrayList<String> tarosubcat3 = new ArrayList<>();
    ActionBarDrawerToggle actionBarDrawerToggle;
    CustomList3 adp;
    CustomList_Subcat adp2;
    Intent alarmIntent;
    public AlarmManager alarmManager;
    public ArrayList<String> app_link_2;
    private ArrayList<String> app_name_2;
    ImageView back;
    private ArrayList<String> bigbanner1_name_2;
    private ArrayList<String> btn_text_2;
    RelativeLayout btnlay;
    //    Button btnn;
//    Button btny;
    String c_subcat;
    public ArrayList<String> campaign_name_2;
    public Runnable changeAdBoolMoreapps;
    Button changeNtrans;
    public int cnt;
    Button dailybtn;
    Button destinylfypurpose;
    List<String> dhead1 = new ArrayList();
    List<String> dhead2 = new ArrayList();
    List<String> dhead3 = new ArrayList();
    List<String> dhead4 = new ArrayList();
    List<String> dhor = new ArrayList();
    DisplayMetrics displayMetrics = new DisplayMetrics();
    DrawerLayout drawer;
    Button dreamsvietnamese;
    Button drmsnambition;
    //    RelativeLayout exitlay;
    ExpandableListAdapter expandableListAdapter;
    Button fNf;
    Button getrdngbtn3;
    List<String> head1 = new ArrayList();
    List<String> head2 = new ArrayList();
    List<String> head3 = new ArrayList();
    List<String> head4 = new ArrayList();
    TextView header;
    TextView header0;
    TextView headernote;
    Button health;
    Button health_tr;
    int height;
    List<String> hor = new ArrayList();
    private ArrayList<String> install_btncolor_2;
    private ArrayList<String> install_textcolor_2;
    boolean isDialogOpened;
    Button lNr;
    ImageView ladyimg;
    int lastExpandedPosition = -1;
    private ArrayList<String> ldesc_2;
    Button life;
    LinearLayout linearLayout;
    LinearLayout linspanish;
    LinearLayout linvietnamese;
    ExpandableListView listview;
    public Handler mHandlerMoreapps;
    FrameLayout main;
    LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
    LinkedHashMap<String, List<String>> map2 = new LinkedHashMap<>();
    public String mediumpopupbanner;
    String menu;
    String menu2;
    Button money;
    Button monthlybtn2;
    NewListAdapter newListAdapter;
    ArrayList<String> newlistitem;
    PendingIntent pendingIntent;
    ArrayList<Integer> r;
    int ran;
    String rateuslink;
    int showcnt;
    public boolean showrateus;
    public String source;
    private boolean startboolMoreapps;
    ListView subcatlist;
    String subtype;
    ImageView tableimg;
    TextView tv;
    String type;
    int width;
    Button workvietnamese;
    Button wrkncarrier;

    EPreferences ePreferences;
    private UnifiedNativeAd nativeAd;


    private int id;
    public InterstitialAd mInterstitialAd;
    private KProgressHUD hud;

    private class CustomList3 extends ArrayAdapter<String> {
        private final Activity context;
        private final ArrayList<String> dlist;

        public CustomList3(Activity activity, ArrayList<String> arrayList) {
            super(activity, R.layout.list_item_drawer, arrayList);
            this.context = activity;
            this.dlist = arrayList;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View inflate = this.context.getLayoutInflater().inflate(R.layout.list_item_drawer, null, true);
            TextView textView = inflate.findViewById(R.id.txtcatviewdr);
            textView.setTextColor(-1);
            String str = this.dlist.get(i);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("");
            textView.setText(sb.toString());
            return inflate;
        }
    }

    private class CustomList_Subcat extends ArrayAdapter<String> {
        private final Activity context;
        private final ArrayList<String> dlist;

        public CustomList_Subcat(Activity activity, ArrayList<String> arrayList) {
            super(activity, R.layout.list_item1, arrayList);
            this.context = activity;
            this.dlist = arrayList;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View inflate = this.context.getLayoutInflater().inflate(R.layout.list_item1, null, true);
            TextView textView = inflate.findViewById(R.id.txtcatview);
            if (!TaroQuestionsActivity.locale.contains("vi")) {
                textView.setTypeface(TaroQuestionsActivity.faceg);
            }
            textView.setText(this.dlist.get(i));
            textView.setTextColor(-1);
            return inflate;
        }
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context _context;
        private LinkedHashMap<String, List<String>> _listDataChild;
        private List<String> _listDataHeader;

        public long getChildId(int i, int i2) {
            return i2;
        }

        public long getGroupId(int i) {
            return i;
        }

        public boolean hasStableIds() {
            return false;
        }

        public boolean isChildSelectable(int i, int i2) {
            return true;
        }

        public ExpandableListAdapter(Context context, List<String> list, LinkedHashMap<String, List<String>> linkedHashMap) {
            this._context = context;
            this._listDataHeader = list;
            this._listDataChild = linkedHashMap;
        }

        public Object getChild(int i, int i2) {
            return ((List) this._listDataChild.get(this._listDataHeader.get(i))).get(i2);
        }

        @SuppressLint("WrongConstant")
        public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
            String str = (String) getChild(i, i2);
            View inflate = view == null ? ((LayoutInflater) this._context.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.child_list, null) : view;
            TextView textView = inflate.findViewById(R.id.txt);
            textView.setTypeface(TaroQuestionsActivity.faceg);
            textView.setText(str);
            String str2 = "pt";
            String str3 = "2020";
            String str4 = "KKKKKKK";

            if (TaroQuestionsActivity.locale.contains(str2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("unlockedDM_");
                sb.append(TaroQuestionsActivity.locale);
                String sb2 = sb.toString();
                String str5 = "     ";
                if (str.contains(TaroQuestionsActivity.this.getResources().getString(R.string.decisionmaking))) {
                    if (!TaroQuestionsActivity.sharedpreferences.getBoolean(sb2, false)) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("########################   DD");
                        sb3.append(str);
                        sb3.append(str5);
                        Log.e(str4, sb3.toString());
                    } else {

                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("**** DD ");
                        sb4.append(str);
                        sb4.append(str5);
                        Log.e(str4, sb4.toString());
                    }
                }
                StringBuilder sb5 = new StringBuilder();
                sb5.append("unlockedlove_");
                sb5.append(TaroQuestionsActivity.locale);
                String sb6 = sb5.toString();
                if (str.contains(TaroQuestionsActivity.this.getResources().getString(R.string.lovespread))) {
                    if (!TaroQuestionsActivity.sharedpreferences.getBoolean(sb6, false)) {
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("########################   LL");
                        sb7.append(str);
                        sb7.append(str5);
                        Log.e(str4, sb7.toString());
                    } else {
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("**** LL ");
                        sb8.append(str);
                        sb8.append(str5);
                        Log.e(str4, sb8.toString());
                    }
                }
                StringBuilder sb9 = new StringBuilder();
                sb9.append("unlockedlife_");
                sb9.append(TaroQuestionsActivity.locale);
                String sb10 = sb9.toString();
                String str6 = "########################   KK";
                String str7 = "**** KK ";
                if (str.contains(TaroQuestionsActivity.this.getResources().getString(R.string.keyoflife))) {
                    if (!TaroQuestionsActivity.sharedpreferences.getBoolean(sb10, false)) {
                        StringBuilder sb11 = new StringBuilder();
                        sb11.append(str6);
                        sb11.append(str);
                        sb11.append(str5);
                        Log.e(str4, sb11.toString());
                    } else {
                        StringBuilder sb12 = new StringBuilder();
                        sb12.append(str7);
                        sb12.append(str);
                        sb12.append(str5);
                        Log.e(str4, sb12.toString());
                    }
                }
                StringBuilder sb13 = new StringBuilder();
                sb13.append("unlockeddestiny_");
                sb13.append(TaroQuestionsActivity.locale);
                String sb14 = sb13.toString();
                if (str.contains(TaroQuestionsActivity.this.getResources().getString(R.string.destNlyfPurpose))) {
                    if (!TaroQuestionsActivity.sharedpreferences.getBoolean(sb14, false)) {
                        StringBuilder sb15 = new StringBuilder();
                        sb15.append(str6);
                        sb15.append(str);
                        sb15.append(str5);
                        Log.e(str4, sb15.toString());
                    } else {
                        StringBuilder sb16 = new StringBuilder();
                        sb16.append(str7);
                        sb16.append(str);
                        sb16.append(str5);
                        Log.e(str4, sb16.toString());
                    }
                }
                StringBuilder sb17 = new StringBuilder();
                sb17.append("unlockedchange_");
                sb17.append(TaroQuestionsActivity.locale);
                String sb18 = sb17.toString();
                if (str.contains(TaroQuestionsActivity.this.getResources().getString(R.string.changeNtrans))) {
                    if (!TaroQuestionsActivity.sharedpreferences.getBoolean(sb18, false)) {
                        StringBuilder sb19 = new StringBuilder();
                        sb19.append(str6);
                        sb19.append(str);
                        sb19.append(str5);
                        Log.e(str4, sb19.toString());
                    } else {
                        StringBuilder sb20 = new StringBuilder();
                        sb20.append(str7);
                        sb20.append(str);
                        sb20.append(str5);
                        Log.e(str4, sb20.toString());
                    }
                }
            } else {
                if (str.contains("Change & Transformation")) {
                    if (!TaroQuestionsActivity.sharedpreferences.getBoolean("unlockedchange", false)) {
                        Log.e(str4, "ch    V @@@@@@@@@@@@*****");
                    } else {
                        Log.e(str4, "ch    IN @@@@@@@@@@@@*****");
                    }
                }
                if (str.contains("Destiny & Life Purpose")) {
                    if (!TaroQuestionsActivity.sharedpreferences.getBoolean("unlockeddestiny", false)) {
                        Log.e(str4, "des    V @@@@@@@@@@@@*****");
                    } else {
                        Log.e(str4, "des    IN @@@@@@@@@@@@*****");
                    }
                }
                if (str.contains(TaroQuestionsActivity.this.getResources().getString(R.string.oppNobstacles))) {
                    if (!TaroQuestionsActivity.sharedpreferences.getBoolean("unlockedopportunities", false)) {
                        Log.e(str4, "opp    V @@@@@@@@@@@@*****");
                    } else {
                        Log.e(str4, "opp    IN @@@@@@@@@@@@*****");
                    }
                }
                if (str.contains(TaroQuestionsActivity.this.getResources().getString(R.string.relationship))) {
                    if (!TaroQuestionsActivity.sharedpreferences.getBoolean("unlockedrelationship", false)) {
                        Log.e(str4, "re    V @@@@@@@@@@@@*****");
                    } else {
                        Log.e(str4, "re    IN @@@@@@@@@@@@*****");
                    }
                }
                if (str.contains(TaroQuestionsActivity.this.getResources().getString(R.string.relationshippurpose))) {
                    if (!TaroQuestionsActivity.sharedpreferences.getBoolean("unlockedrelpurpose", false)) {
                        Log.e(str4, "re p    V @@@@@@@@@@@@*****");
                    } else {
                        Log.e(str4, "re p    IN @@@@@@@@@@@@*****");
                    }
                }
            }
            return inflate;
        }

        public int getChildrenCount(int i) {
            return this._listDataChild.get(this._listDataHeader.get(i)).size();
        }

        public Object getGroup(int i) {
            return this._listDataHeader.get(i);
        }

        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @SuppressLint("WrongConstant")
        public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
            String str = (String) getGroup(i);
            if (view == null) {
                view = ((LayoutInflater) this._context.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_v, null);
            }
            TextView textView = view.findViewById(R.id.title);
            if (!TaroQuestionsActivity.locale.contains("vi")) {
                textView.setTypeface(TaroQuestionsActivity.faceg, Typeface.BOLD);
            }
            textView.setText(str);
            return view;
        }
    }

    public TaroQuestionsActivity() {
        String str = "";
        this.subtype = str;
        this.c_subcat = str;
        this.showrateus = false;
        this.rateuslink = "http://play.google.com/store/apps/details?id=com.internetdesignzone.tarocards";
        this.mHandlerMoreapps = new Handler();
        this.startboolMoreapps = false;
        this.ldesc_2 = new ArrayList<>();
        this.app_name_2 = new ArrayList<>();
        this.btn_text_2 = new ArrayList<>();
        this.bigbanner1_name_2 = new ArrayList<>();
        this.app_link_2 = new ArrayList<>();
        this.campaign_name_2 = new ArrayList<>();
        this.install_btncolor_2 = new ArrayList<>();
        this.install_textcolor_2 = new ArrayList<>();
        this.source = "Tarot_Card_Reading";
        this.mediumpopupbanner = "Moreapps_PopupBanner";
        this.cnt = 0;
        this.r = new ArrayList<>();
        this.ran = 0;
        this.isDialogOpened = false;
        this.showcnt = 0;
    }

    public void onCreate(Bundle bundle) {
        String str;
        int i2;
        Drawable drawable;
        Bundle bundle2 = bundle;
        super.onCreate(bundle);
        this.ePreferences = EPreferences.getInstance(this);
        if (bundle2 != null) {
            mNotificationCount = bundle2.getInt(NOTIFICATION_COUNT);
        }
        activity = this;
        showPopupAds = false;
        getWindowManager().getDefaultDisplay().getMetrics(this.displayMetrics);
        this.height = this.displayMetrics.heightPixels;
        this.width = this.displayMetrics.widthPixels;
        locale = getResources().getConfiguration().locale.getLanguage();
        StringBuilder sb = new StringBuilder();
        sb.append("onCreate...>");
        sb.append(locale);
        Log.e("Locale", sb.toString());
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, 0);
        sharedpreferences = sharedPreferences;
        editor = sharedPreferences.edit();
        String str2 = "count";
        editor.putInt(str2, sharedpreferences.getInt(str2, 0) + 1);
        editor.commit();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(";;;");
        sb2.append(sharedpreferences.getInt(str2, 0));
        Log.e("value is", sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("taroquestions");
        sb3.append(AlarmReceiver.isAlarmNotified);
        Log.e("isalarmnotified", sb3.toString());
        editor.putBoolean("removeads", true);
        editor.commit();
        String str3 = "tr";
        String str4 = "ja";
        String str5 = "tl";
        String str6 = "it";
        String str7 = "es";
        String str8 = "hi";
        String str9 = "vi";
        interstitialAd();
        if (locale.contains(str3)) {
            setContentView(R.layout.mainlayout_turkish);
            ImageView imageView = findViewById(R.id.ladyimg);
            this.ladyimg = imageView;
            imageView.setImageResource(R.drawable.newladyimg);
            this.tableimg = findViewById(R.id.tableimg);
            this.btnlay = findViewById(R.id.btnlay);
            this.drmsnambition = findViewById(R.id.drmsnambition);
            this.wrkncarrier = findViewById(R.id.wrkncarrier);
            this.health_tr = findViewById(R.id.health);


            this.drmsnambition.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TaroQuestionsActivity.this.FlurryLog();
                    String str = "Dreams & Ambitions";
                    TaroQuestionsActivity.this.googleanalytic(str);
                    Intent intent = new Intent(TaroQuestionsActivity.this, TaroCardsQuestionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "one");
                    bundle.putString("head", TaroQuestionsActivity.this.getResources().getString(R.string.dreamsNami));
                    bundle.putString("ccc", str);
                    bundle.putString("ccc2", TaroQuestionsActivity.this.getResources().getString(R.string.dreamsNami));
                    intent.putExtras(bundle);
                    TaroQuestionsActivity.this.startActivity(intent);
                }
            });
            this.wrkncarrier.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TaroQuestionsActivity.this.FlurryLog();
                    String str = "Work & Career";
                    TaroQuestionsActivity.this.googleanalytic(str);
                    Intent intent = new Intent(TaroQuestionsActivity.this, TaroCardsQuestionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "one");
                    bundle.putString("head", TaroQuestionsActivity.this.getResources().getString(R.string.workNcareer));
                    bundle.putString("ccc", str);
                    bundle.putString("ccc2", TaroQuestionsActivity.this.getResources().getString(R.string.workNcareer));
                    intent.putExtras(bundle);
                    TaroQuestionsActivity.this.startActivity(intent);
                }
            });
            this.health_tr.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TaroQuestionsActivity.this.FlurryLog();
                    String str = "Health";
                    TaroQuestionsActivity.this.googleanalytic(str);
                    Intent intent = new Intent(TaroQuestionsActivity.this, TaroCardsQuestionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "one");
                    bundle.putString("head", TaroQuestionsActivity.this.getResources().getString(R.string.health));
                    bundle.putString("ccc", str);
                    bundle.putString("ccc2", TaroQuestionsActivity.this.getResources().getString(R.string.health));
                    intent.putExtras(bundle);
                    TaroQuestionsActivity.this.startActivity(intent);
                }
            });

            if ((getResources().getConfiguration().screenLayout & 15) == 4) {
                LayoutParams layoutParams = this.ladyimg.getLayoutParams();
                double d = this.height;
                Double.isNaN(d);
                layoutParams.height = (int) (d * 0.45d);
                LayoutParams layoutParams2 = this.tableimg.getLayoutParams();
                double d2 = this.height;
                Double.isNaN(d2);
                layoutParams2.height = (int) (d2 * 0.55d);
            } else if ((getResources().getConfiguration().screenLayout & 15) == 3) {
                LayoutParams layoutParams3 = this.ladyimg.getLayoutParams();
                double d3 = this.height;
                Double.isNaN(d3);
                layoutParams3.height = (int) (d3 * 0.45d);
                LayoutParams layoutParams4 = this.tableimg.getLayoutParams();
                double d4 = this.height;
                Double.isNaN(d4);
                layoutParams4.height = (int) (d4 * 0.55d);
            } else {
                LayoutParams layoutParams5 = this.ladyimg.getLayoutParams();
                double d5 = this.height;
                Double.isNaN(d5);
                layoutParams5.height = (int) (d5 * 0.4d);
                LayoutParams layoutParams6 = this.tableimg.getLayoutParams();
                double d6 = this.height;
                Double.isNaN(d6);
                layoutParams6.height = (int) (d6 * 0.6d);
            }
            this.wrkncarrier.setTypeface(faceg);
            this.drmsnambition.setTypeface(faceg);
            this.health_tr.setTypeface(faceg);
            str = str9;
        } else if (locale.contains(str4) || locale.contains("th") || locale.contains("da") || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains(str7) || locale.contains("fr") || locale.contains(str8) || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains(str6) || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains(str9) || locale.contains(str5)) {
            str = str9;
            setContentView(R.layout.mainlayout2);
            ImageView imageView2 = findViewById(R.id.ladyimg);
            this.ladyimg = imageView2;
            imageView2.setImageResource(R.drawable.newladyimg);
            this.tableimg = findViewById(R.id.tableimg);
            this.btnlay = findViewById(R.id.btnlay);
            this.lNr = findViewById(R.id.lovenrel);
            this.money = findViewById(R.id.money);
            this.life = findViewById(R.id.life);
            this.fNf = findViewById(R.id.fnf);
            this.health = findViewById(R.id.health);
            this.changeNtrans = findViewById(R.id.chngentransformation);
            this.destinylfypurpose = findViewById(R.id.destiny);
            this.linspanish = findViewById(R.id.linspanish);
            this.linvietnamese = findViewById(R.id.linvietnamese);
            this.workvietnamese = findViewById(R.id.workvietnamese);
            this.dreamsvietnamese = findViewById(R.id.dreamsvietnamese);
            if (locale.contains(str7)) {
                this.changeNtrans.setVisibility(View.VISIBLE);
                this.destinylfypurpose.setVisibility(View.VISIBLE);

                SharedPreferences sharedPreferences2 = sharedpreferences;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("unlockedchange_");
                sb4.append(locale);

                SharedPreferences sharedPreferences3 = sharedpreferences;
                StringBuilder sb5 = new StringBuilder();
                sb5.append("unlockeddestiny_");
                sb5.append(locale);

            } else if (locale.contains(str) || locale.contains(str6) || locale.contains(str5)) {
                this.linvietnamese.setVisibility(View.VISIBLE);
                this.linspanish.setVisibility(View.GONE);

                SharedPreferences sharedPreferences4 = sharedpreferences;
                StringBuilder sb6 = new StringBuilder();
                sb6.append("unlockedwork_");
                sb6.append(locale);

                SharedPreferences sharedPreferences5 = sharedpreferences;
                StringBuilder sb7 = new StringBuilder();
                sb7.append("unlockeddreams_");
                sb7.append(locale);
            }
            this.lNr.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TaroQuestionsActivity.this.FlurryLog();
                    String str = "Love & Relationships";
                    TaroQuestionsActivity.this.googleanalytic(str);
                    Intent intent = new Intent(TaroQuestionsActivity.this, TaroCardsQuestionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "one");
                    bundle.putString("head", TaroQuestionsActivity.this.getResources().getString(R.string.loveNrel));
                    bundle.putString("ccc", str);
                    bundle.putString("ccc2", TaroQuestionsActivity.this.getResources().getString(R.string.loveNrel));
                    intent.putExtras(bundle);
                    TaroQuestionsActivity.this.startActivity(intent);
                }
            });
            this.money.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TaroQuestionsActivity.this.FlurryLog();
                    String str = "Money";
                    TaroQuestionsActivity.this.googleanalytic(str);
                    Intent intent = new Intent(TaroQuestionsActivity.this, TaroCardsQuestionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "one");
                    bundle.putString("head", TaroQuestionsActivity.this.getResources().getString(R.string.money));
                    bundle.putString("ccc", str);
                    bundle.putString("ccc2", TaroQuestionsActivity.this.getResources().getString(R.string.money));
                    intent.putExtras(bundle);
                    TaroQuestionsActivity.this.startActivity(intent);
                }
            });
            this.life.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TaroQuestionsActivity.this.FlurryLog();
                    String str = "Life";
                    TaroQuestionsActivity.this.googleanalytic(str);
                    Intent intent = new Intent(TaroQuestionsActivity.this, TaroCardsQuestionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "one");
                    bundle.putString("head", TaroQuestionsActivity.this.getResources().getString(R.string.life));
                    bundle.putString("ccc", str);
                    bundle.putString("ccc2", TaroQuestionsActivity.this.getResources().getString(R.string.life));
                    intent.putExtras(bundle);
                    TaroQuestionsActivity.this.startActivity(intent);
                }
            });
            this.fNf.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TaroQuestionsActivity.this.FlurryLog();
                    String str = "Family & Friends";
                    TaroQuestionsActivity.this.googleanalytic(str);
                    Intent intent = new Intent(TaroQuestionsActivity.this, TaroCardsQuestionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "one");
                    bundle.putString("head", TaroQuestionsActivity.this.getResources().getString(R.string.familyNfriends));
                    bundle.putString("ccc", str);
                    bundle.putString("ccc2", TaroQuestionsActivity.this.getResources().getString(R.string.familyNfriends));
                    intent.putExtras(bundle);
                    TaroQuestionsActivity.this.startActivity(intent);
                }
            });
            this.health.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TaroQuestionsActivity.this.FlurryLog();
                    String str = "Health";
                    TaroQuestionsActivity.this.googleanalytic(str);
                    Intent intent = new Intent(TaroQuestionsActivity.this, TaroCardsQuestionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "one");
                    bundle.putString("head", TaroQuestionsActivity.this.getResources().getString(R.string.health));
                    bundle.putString("ccc", str);
                    bundle.putString("ccc2", TaroQuestionsActivity.this.getResources().getString(R.string.health));
                    intent.putExtras(bundle);
                    TaroQuestionsActivity.this.startActivity(intent);
                }
            });
            this.workvietnamese.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    String str = "Work & Career";
                    String str2 = "dd/M/yyyy hh:mm:ss";
                    String str3 = "kkkk";
                    try {
                        String format = new SimpleDateFormat(str2, Locale.ENGLISH).format(Calendar.getInstance().getTime());
                        SharedPreferences sharedPreferences = TaroQuestionsActivity.sharedpreferences;
                        StringBuilder sb = new StringBuilder();
                        sb.append("dateunlockedwork_");
                        sb.append(TaroQuestionsActivity.locale);
                        String string = sharedPreferences.getString(sb.toString(), format);
                        Log.e(str3, "control");
                        Date time = Calendar.getInstance().getTime();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2, Locale.ENGLISH);
                        String format2 = simpleDateFormat.format(time);
                        Log.e(str3, string);
                        Date parse = simpleDateFormat.parse(string);
                        Log.e(str3, "control2");
                        int difference = TaroQuestionsActivity.getDifference(parse, simpleDateFormat.parse(format2));
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("unlockedwork_");
                        sb2.append(TaroQuestionsActivity.locale);
                        if (!TaroQuestionsActivity.sharedpreferences.getBoolean(sb2.toString(), false)) {
                            if (!TaroQuestionsActivity.this.isNetworkAvailable()) {
                                Toast.makeText(TaroQuestionsActivity.this.getApplicationContext(), TaroQuestionsActivity.this.getResources().getString(R.string.interneterror), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(TaroQuestionsActivity.this, TaroQuestionsActivity.this.getResources().getString(R.string.tryagain), Toast.LENGTH_LONG).show();
                            }
                        } else if (difference < 5) {
                            TaroQuestionsActivity.this.FlurryLog();
                            TaroQuestionsActivity.this.googleanalytic(str);
                            Intent intent = new Intent(TaroQuestionsActivity.this, TaroCardsQuestionActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("type", "one");
                            bundle.putString("head", TaroQuestionsActivity.this.getResources().getString(R.string.workNcareer));
                            bundle.putString("ccc", str);
                            bundle.putString("ccc2", TaroQuestionsActivity.this.getResources().getString(R.string.workNcareer));
                            intent.putExtras(bundle);
                            TaroQuestionsActivity.this.startActivity(intent);
                        }
                    } catch (ParseException e) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("");
                        sb3.append(e);
                        Log.e(str3, sb3.toString());
                    }
                }
            });
            this.dreamsvietnamese.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    String str = "Dreams & Ambitions";
                    String str2 = "dd/M/yyyy hh:mm:ss";
                    String str3 = "kkkk";
                    try {
                        String format = new SimpleDateFormat(str2, Locale.ENGLISH).format(Calendar.getInstance().getTime());
                        SharedPreferences sharedPreferences = TaroQuestionsActivity.sharedpreferences;
                        StringBuilder sb = new StringBuilder();
                        sb.append("dateunlockeddreams_");
                        sb.append(TaroQuestionsActivity.locale);
                        String string = sharedPreferences.getString(sb.toString(), format);
                        Log.e(str3, "control");
                        Date time = Calendar.getInstance().getTime();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2, Locale.ENGLISH);
                        String format2 = simpleDateFormat.format(time);
                        Log.e(str3, string);
                        Date parse = simpleDateFormat.parse(string);
                        Log.e(str3, "control2");
                        int difference = TaroQuestionsActivity.getDifference(parse, simpleDateFormat.parse(format2));
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("unlockeddreams_");
                        sb2.append(TaroQuestionsActivity.locale);
                        if (!TaroQuestionsActivity.sharedpreferences.getBoolean(sb2.toString(), false)) {
                            if (!TaroQuestionsActivity.this.isNetworkAvailable()) {
                                Toast.makeText(TaroQuestionsActivity.this.getApplicationContext(), TaroQuestionsActivity.this.getResources().getString(R.string.interneterror), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(TaroQuestionsActivity.this, TaroQuestionsActivity.this.getResources().getString(R.string.tryagain), Toast.LENGTH_LONG).show();
                            }
                        } else if (difference < 5) {
                            TaroQuestionsActivity.this.FlurryLog();
                            TaroQuestionsActivity.this.googleanalytic(str);
                            Intent intent = new Intent(TaroQuestionsActivity.this, TaroCardsQuestionActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("type", "one");
                            bundle.putString("head", TaroQuestionsActivity.this.getResources().getString(R.string.dreamsNami));
                            bundle.putString("ccc", str);
                            bundle.putString("ccc2", TaroQuestionsActivity.this.getResources().getString(R.string.dreamsNami));
                            intent.putExtras(bundle);
                            TaroQuestionsActivity.this.startActivity(intent);
                        }
                    } catch (ParseException e) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("");
                        sb3.append(e);
                        Log.e(str3, sb3.toString());
                    }
                }
            });
            this.changeNtrans.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    String str = "Change & Transformation";
                    String str2 = "dd/M/yyyy hh:mm:ss";
                    String str3 = "kkkk";
                    try {
                        String format = new SimpleDateFormat(str2, Locale.ENGLISH).format(Calendar.getInstance().getTime());
                        SharedPreferences sharedPreferences = TaroQuestionsActivity.sharedpreferences;
                        StringBuilder sb = new StringBuilder();
                        sb.append("dateunlockedchange_");
                        sb.append(TaroQuestionsActivity.locale);
                        String string = sharedPreferences.getString(sb.toString(), format);
                        Log.e(str3, "control");
                        Date time = Calendar.getInstance().getTime();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2, Locale.ENGLISH);
                        String format2 = simpleDateFormat.format(time);
                        Log.e(str3, string);
                        Date parse = simpleDateFormat.parse(string);
                        Log.e(str3, "control2");
                        int difference = TaroQuestionsActivity.getDifference(parse, simpleDateFormat.parse(format2));
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("unlockedchange_");
                        sb2.append(TaroQuestionsActivity.locale);
                        if (!TaroQuestionsActivity.sharedpreferences.getBoolean(sb2.toString(), false)) {
                            if (!TaroQuestionsActivity.this.isNetworkAvailable()) {
                                Toast.makeText(TaroQuestionsActivity.this.getApplicationContext(), TaroQuestionsActivity.this.getResources().getString(R.string.interneterror), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(TaroQuestionsActivity.this, TaroQuestionsActivity.this.getResources().getString(R.string.tryagain), Toast.LENGTH_LONG).show();
                            }
                        } else if (difference < 5) {
                            TaroQuestionsActivity.this.FlurryLog();
                            TaroQuestionsActivity.this.googleanalytic(str);
                            Intent intent = new Intent(TaroQuestionsActivity.this, TaroCardsQuestionActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("type", "one");
                            bundle.putString("head", TaroQuestionsActivity.this.getResources().getString(R.string.changeNtrans));
                            bundle.putString("ccc", str);
                            bundle.putString("ccc2", TaroQuestionsActivity.this.getResources().getString(R.string.changeNtrans));
                            intent.putExtras(bundle);
                            TaroQuestionsActivity.this.startActivity(intent);
                        }
                    } catch (ParseException e) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("");
                        sb3.append(e);
                        Log.e(str3, sb3.toString());
                    }
                }
            });
            this.destinylfypurpose.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    String str = "Destiny & Life Purpose";
                    String str2 = "dd/M/yyyy hh:mm:ss";
                    String str3 = "kkkk";
                    try {
                        String format = new SimpleDateFormat(str2, Locale.ENGLISH).format(Calendar.getInstance().getTime());
                        SharedPreferences sharedPreferences = TaroQuestionsActivity.sharedpreferences;
                        StringBuilder sb = new StringBuilder();
                        sb.append("dateunlockeddestiny_");
                        sb.append(TaroQuestionsActivity.locale);
                        String string = sharedPreferences.getString(sb.toString(), format);
                        Log.e(str3, "control");
                        Date time = Calendar.getInstance().getTime();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2, Locale.ENGLISH);
                        String format2 = simpleDateFormat.format(time);
                        Log.e(str3, string);
                        Date parse = simpleDateFormat.parse(string);
                        Log.e(str3, "control2");
                        int difference = TaroQuestionsActivity.getDifference(parse, simpleDateFormat.parse(format2));
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("unlockeddestiny_");
                        sb2.append(TaroQuestionsActivity.locale);
                        if (!TaroQuestionsActivity.sharedpreferences.getBoolean(sb2.toString(), false)) {
                            if (!TaroQuestionsActivity.this.isNetworkAvailable()) {
                                Toast.makeText(TaroQuestionsActivity.this.getApplicationContext(), TaroQuestionsActivity.this.getResources().getString(R.string.interneterror), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(TaroQuestionsActivity.this, TaroQuestionsActivity.this.getResources().getString(R.string.tryagain), Toast.LENGTH_LONG).show();
                            }
                        } else if (difference < 5) {
                            TaroQuestionsActivity.this.FlurryLog();
                            TaroQuestionsActivity.this.googleanalytic(str);
                            Intent intent = new Intent(TaroQuestionsActivity.this, TaroCardsQuestionActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("type", "one");
                            bundle.putString("head", TaroQuestionsActivity.this.getResources().getString(R.string.destNlyfPurpose));
                            bundle.putString("ccc", str);
                            bundle.putString("ccc2", TaroQuestionsActivity.this.getResources().getString(R.string.destNlyfPurpose));
                            intent.putExtras(bundle);
                            TaroQuestionsActivity.this.startActivity(intent);
                        }
                    } catch (ParseException e) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("");
                        sb3.append(e);
                        Log.e(str3, sb3.toString());
                    }
                }
            });

            if ((getResources().getConfiguration().screenLayout & 15) == 4) {
                LayoutParams layoutParams7 = this.ladyimg.getLayoutParams();
                double d7 = this.height;
                Double.isNaN(d7);
                layoutParams7.height = (int) (d7 * 0.45d);
                LayoutParams layoutParams8 = this.tableimg.getLayoutParams();
                double d8 = this.height;
                Double.isNaN(d8);
                layoutParams8.height = (int) (d8 * 0.55d);
            } else if ((getResources().getConfiguration().screenLayout & 15) == 3) {
                LayoutParams layoutParams9 = this.ladyimg.getLayoutParams();
                double d9 = this.height;
                Double.isNaN(d9);
                layoutParams9.height = (int) (d9 * 0.45d);
                LayoutParams layoutParams10 = this.tableimg.getLayoutParams();
                double d10 = this.height;
                Double.isNaN(d10);
                layoutParams10.height = (int) (d10 * 0.55d);
            } else {
                LayoutParams layoutParams11 = this.ladyimg.getLayoutParams();
                double d11 = this.height;
                Double.isNaN(d11);
                layoutParams11.height = (int) (d11 * 0.4d);
                LayoutParams layoutParams12 = this.tableimg.getLayoutParams();
                double d12 = this.height;
                Double.isNaN(d12);
                layoutParams12.height = (int) (d12 * 0.6d);
            }
            if (locale.contains(str8)) {
                faceg = Typeface.createFromAsset(getAssets(), "fonts/mangal.ttf");
            } else {
                faceg = Typeface.createFromAsset(getAssets(), "fonts/georgiar.ttf");
            }
            this.lNr.setTypeface(faceg);
            this.money.setTypeface(faceg);
            this.health.setTypeface(faceg);
            this.fNf.setTypeface(faceg);
            this.life.setTypeface(faceg);
            this.changeNtrans.setTypeface(faceg);
            this.destinylfypurpose.setTypeface(faceg);
            this.workvietnamese.setTypeface(faceg);
            this.dreamsvietnamese.setTypeface(faceg);
        } else {
            setContentView(R.layout.questions);
            this.drawer = findViewById(R.id.drawer_layout);
            this.main = findViewById(R.id.main);
            ladyimg = findViewById(R.id.ladyimg);
            ladyimg.setImageResource(R.drawable.newladyimg);
            this.tableimg = findViewById(R.id.tableimg);
            this.btnlay = findViewById(R.id.btnlay);

            String str10 = str9;
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawer, R.string.drawer_open, R.string.drawer_close) {
                public void onDrawerClosed(View view) {
                    TaroQuestionsActivity.this.getSupportActionBar().setTitle("");
                    TaroQuestionsActivity.this.invalidateOptionsMenu();
                }

                public void onDrawerOpened(View view) {
                    TaroQuestionsActivity.this.getSupportActionBar().setTitle("");
                    TaroQuestionsActivity.this.invalidateOptionsMenu();
                }
            };
            this.actionBarDrawerToggle = actionBarDrawerToggle;
            this.drawer.setDrawerListener(actionBarDrawerToggle);
            this.drawer.setDrawerListener(new SimpleDrawerListener() {
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                }

                public void onDrawerSlide(View view, float f) {
                    super.onDrawerSlide(view, f);
                    if (TaroQuestionsActivity.exitbool) {
                        TaroQuestionsActivity.this.drawer.closeDrawers();
                    }
                }

                public void onDrawerOpened(View view) {
                    super.onDrawerOpened(view);
                    if (TaroQuestionsActivity.exitbool) {
                        TaroQuestionsActivity.this.drawer.closeDrawers();
                    }
                }
            });
            this.drawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
            this.linearLayout = findViewById(R.id.navlayout);
            RelativeLayout relativeLayout = findViewById(R.id.btnlay);
            this.btnlay = relativeLayout;
            relativeLayout.setVisibility(View.VISIBLE);
            Button button = findViewById(R.id.startnav);
            this.dailybtn = button;
            button.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                        try {
                            hud = KProgressHUD.create(activity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("Showing Ads").setDetailsLabel("Please Wait...");
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
                        }, 2000);

                    } else {
                        TaroQuestionsActivity.this.drawer.openDrawer(TaroQuestionsActivity.this.linearLayout);
                        TaroQuestionsActivity.this.childClickMethod(0, 0);
                    }
                }
            });
            monthlybtn2 = findViewById(R.id.startnav2);
            monthlybtn2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                        try {
                            hud = KProgressHUD.create(activity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("Showing Ads").setDetailsLabel("Please Wait...");
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
                                    id = 102;
                                    mInterstitialAd.show();
                                }
                            }
                        }, 2000);
                    } else {
                        TaroQuestionsActivity.this.drawer.openDrawer(TaroQuestionsActivity.this.linearLayout);
                        TaroQuestionsActivity.this.childClickMethod(0, 1);
                    }
                }
            });
            Button button3 = findViewById(R.id.startnav3);
            this.getrdngbtn3 = button3;
            button3.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                        try {
                            hud = KProgressHUD.create(activity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("Showing Ads").setDetailsLabel("Please Wait...");
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
                                    id = 103;
                                    mInterstitialAd.show();
                                }
                            }
                        }, 2000);
                    } else {
                        TaroQuestionsActivity.this.drawer.openDrawer(TaroQuestionsActivity.this.linearLayout);
                        TaroQuestionsActivity.this.listview.expandGroup(1);
                    }
                }
            });

            if (locale.contains("pt")) {

            } else {

            }
            LayoutParams layoutParams15 = this.dailybtn.getLayoutParams();
            double d15 = this.height;
            Double.isNaN(d15);
            layoutParams15.height = (int) (d15 * 0.08d);
            LayoutParams layoutParams16 = this.monthlybtn2.getLayoutParams();
            double d16 = this.height;
            Double.isNaN(d16);
            layoutParams16.height = (int) (d16 * 0.08d);
            LayoutParams layoutParams17 = this.getrdngbtn3.getLayoutParams();
            double d17 = this.height;
            Double.isNaN(d17);
            layoutParams17.height = (int) (d17 * 0.08d);

            double d18 = this.height;
            Double.isNaN(d18);

            if ((getResources().getConfiguration().screenLayout & 15) != 4) {
                if ((getResources().getConfiguration().screenLayout & 15) == 3) {
                    this.dailybtn.setTextSize(22.0f);
                    this.monthlybtn2.setTextSize(22.0f);
                    this.getrdngbtn3.setTextSize(22.0f);
                } else {
                    this.dailybtn.setTextSize(15.0f);
                    this.monthlybtn2.setTextSize(15.0f);
                    this.getrdngbtn3.setTextSize(15.0f);
                }
            }
            this.dailybtn.setTypeface(faceg);
            this.monthlybtn2.setTypeface(faceg);
            this.getrdngbtn3.setTypeface(faceg);
            this.dailybtn.setText(getResources().getString(R.string.dailytaro));
            this.monthlybtn2.setText(getResources().getString(R.string.monthlytaro));
            this.getrdngbtn3.setText(getResources().getString(R.string.gettr));
            this.header0 = findViewById(R.id.headernote0);
            this.listview = findViewById(R.id.drawer);
            this.header = findViewById(R.id.headernote);
            str = str10;
            if (!locale.contains(str)) {
                this.header.setTypeface(faceg);
                this.header0.setTypeface(faceg);
            }
            LinkedHashMap data = getData();
            ExpandableListAdapter expandableListAdapter2 = new ExpandableListAdapter(this, new ArrayList(data.keySet()), data);
            this.expandableListAdapter = expandableListAdapter2;
            this.listview.setAdapter(expandableListAdapter2);
            this.subcatlist = findViewById(R.id.listView1);
            this.main.setBackgroundResource(R.drawable.bg_main);
            this.listview.setOnChildClickListener(new OnChildClickListener() {
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j) {
                    Log.e("TAG", "List item selected");
                    TaroQuestionsActivity.this.childClickMethod(i, i2);
                    return false;
                }
            });
            this.listview.setOnGroupExpandListener(new OnGroupExpandListener() {
                public void onGroupExpand(int i) {
                    if (!(TaroQuestionsActivity.this.lastExpandedPosition == -1 || i == TaroQuestionsActivity.this.lastExpandedPosition)) {
                        TaroQuestionsActivity.this.listview.collapseGroup(TaroQuestionsActivity.this.lastExpandedPosition);
                    }
                    TaroQuestionsActivity.this.lastExpandedPosition = i;
                }
            });
            this.listview.setOnGroupClickListener(new OnGroupClickListener() {
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                    String str = Intent.ACTION_VIEW;
                    if (i == 5) {
                        TaroQuestionsActivity.this.startActivity(new Intent(str, Uri.parse(TaroQuestionsActivity.this.rateuslink)));
                    }
                    return false;
                }
            });
            this.subcatlist.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    TaroQuestionsActivity.this.menu2 = TaroQuestionsActivity.tarosubcat.get(i);
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(TaroQuestionsActivity.this.menu2);
                    Log.e("menu2", sb.toString());
                    boolean equals = TaroQuestionsActivity.this.menu2.equals("Get Your Question Answered");
                    String str = "ccc2";
                    String str2 = "head";
                    String str3 = "one";
                    String str4 = "Get Tarot Readings";
                    String str5 = "ccc";
                    String str6 = "type";
                    if (equals) {
                        TaroQuestionsActivity taroQuestionsActivity = TaroQuestionsActivity.this;
                        taroQuestionsActivity.googleanalytic2(str4, taroQuestionsActivity.menu);
                        TaroQuestionsActivity.this.type = str3;
                        Bundle bundle = new Bundle();
                        bundle.putString(str2, TaroQuestionsActivity.this.c_subcat);
                        bundle.putString(str5, TaroQuestionsActivity.this.menu);
                        bundle.putString(str6, TaroQuestionsActivity.this.type);
                        bundle.putString(str, TaroQuestionsActivity.this.menu2);
                        Intent intent = new Intent(view.getContext(), TaroCardsQuestionActivity.class);
                        intent.putExtras(bundle);
                        TaroQuestionsActivity.this.startActivityForResult(intent, 0);
                    } else if (TaroQuestionsActivity.this.menu2.equals("Know about the Past, Present, Future")) {
                        TaroQuestionsActivity taroQuestionsActivity2 = TaroQuestionsActivity.this;
                        taroQuestionsActivity2.googleanalytic2(str4, taroQuestionsActivity2.menu);
                        TaroQuestionsActivity.this.type = "three";
                        Bundle bundle2 = new Bundle();
                        bundle2.putString(str2, TaroQuestionsActivity.this.c_subcat);
                        bundle2.putString(str5, TaroQuestionsActivity.this.menu);
                        bundle2.putString(str6, TaroQuestionsActivity.this.type);
                        bundle2.putString(str, TaroQuestionsActivity.this.menu2);
                        Intent intent2 = new Intent(view.getContext(), TaroCardsQuestionActivity.class);
                        intent2.putExtras(bundle2);
                        TaroQuestionsActivity.this.startActivityForResult(intent2, 0);
                    } else if (TaroQuestionsActivity.this.menu2.equals("Complete Relationship Analysis")) {
                        TaroQuestionsActivity taroQuestionsActivity3 = TaroQuestionsActivity.this;
                        taroQuestionsActivity3.googleanalytic2(str4, taroQuestionsActivity3.menu);
                        TaroQuestionsActivity.this.type = "four";
                        Bundle bundle3 = new Bundle();
                        bundle3.putString(str2, TaroQuestionsActivity.this.c_subcat);
                        bundle3.putString(str5, TaroQuestionsActivity.this.menu);
                        bundle3.putString(str6, TaroQuestionsActivity.this.type);
                        bundle3.putString(str, TaroQuestionsActivity.this.menu2);
                        Intent intent3 = new Intent(view.getContext(), TaroCardsQuestionActivity.class);
                        intent3.putExtras(bundle3);
                        TaroQuestionsActivity.this.startActivityForResult(intent3, 0);
                    } else {
                        String str7 = "subtype_eng";
                        String str8 = "subtype";
                        String str9 = "name";
                        String str10 = "Meanings Of All Tarot Cards";
                        if (TaroQuestionsActivity.this.menu2.equals("Pentacles") || TaroQuestionsActivity.this.menu2.equals("Cups") || TaroQuestionsActivity.this.menu2.equals("Swords") || TaroQuestionsActivity.this.menu2.equals("Wands")) {
                            String str11 = TaroQuestionsActivity.tarosubcat.get(i);
                            String str12 = "Minor Arcana";
                            TaroQuestionsActivity.this.googleanalytic2(str10, str12);
                            TaroQuestionsActivity.this.type = str3;
                            Bundle bundle4 = new Bundle();
                            bundle4.putString("menu", TaroQuestionsActivity.this.getResources().getString(R.string.meaningoftc));
                            bundle4.putString(str9, str11);
                            bundle4.putString(str6, TaroQuestionsActivity.this.type);
                            bundle4.putString(str8, TaroQuestionsActivity.this.getResources().getString(R.string.minorA));
                            bundle4.putString(str7, str12);
                            Intent intent4 = new Intent(view.getContext(), SecondActivity.class);
                            intent4.putExtras(bundle4);
                            TaroQuestionsActivity.this.startActivityForResult(intent4, 0);
                            return;
                        }
                        TaroQuestionsActivity.this.type = str3;
                        String str13 = TaroQuestionsActivity.tarosubcat.get(i);
                        String str14 = "Major Arcana";
                        TaroQuestionsActivity.this.googleanalytic2(str10, str14);
                        Bundle bundle5 = new Bundle();
                        bundle5.putString("ques", TaroQuestionsActivity.this.getResources().getString(R.string.meaningoftc));
                        bundle5.putString(str9, str13);
                        bundle5.putString("name2", TaroQuestionsActivity.tarosubcat2.get(i));
                        bundle5.putString(str6, TaroQuestionsActivity.this.type);
                        bundle5.putString(str8, TaroQuestionsActivity.this.getResources().getString(R.string.majorA));
                        bundle5.putString(str7, str14);
                        bundle5.putString(str5, str10);
                        Intent intent5 = new Intent(view.getContext(), TaroResultActivity.class);
                        intent5.putExtras(bundle5);
                        TaroQuestionsActivity.this.startActivityForResult(intent5, 0);
                    }
                }
            });
        }

        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.status_1));
        }
        face1 = Typeface.createFromAsset(getAssets(), "fonts/roboto_thin.ttf");
        face2 = Typeface.createFromAsset(getAssets(), "fonts/roboto_light.ttf");
        face3 = Typeface.createFromAsset(getAssets(), "fonts/roboto_medium.ttf");
        if (locale.contains(str8)) {
            faceg = Typeface.createFromAsset(getAssets(), "fonts/MANGAL.TTF");
        } else {
            faceg = Typeface.createFromAsset(getAssets(), "fonts/georgiar.ttf");
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (!locale.contains(str4) && !locale.contains("th") && !locale.contains("da") && !locale.contains("pl") && !locale.contains("ko") && !locale.contains("zh") && !locale.contains(str3) && !locale.contains(str7) && !locale.contains("fr") && !locale.contains(str8) && !locale.contains("nb") && !locale.contains("sv") && !locale.contains("de") && !locale.contains(str6) && !locale.contains("ms") && !locale.contains("ru") && !locale.contains("nl") && !locale.contains("fi") && !locale.contains("el") && !locale.contains("in") && !locale.contains(str) && !locale.contains(str5)) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawernew_black);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        View inflate = LayoutInflater.from(this).inflate(R.layout.titleview, null);
        ((TextView) inflate.findViewById(R.id.title)).setTypeface(faceg);
        getSupportActionBar().setCustomView(inflate);
        if (!locale.contains(str)) {
            ((TextView) inflate.findViewById(R.id.title)).setTypeface(faceg);
        }
        ((TextView) inflate.findViewById(R.id.title)).setTextColor(getResources().getColor(R.color.white));
        setAlarm();
        if (AlarmReceiver.isAlarmNotified) {
            AlarmReceiver.isAlarmNotified = false;
            if (locale.contains(str4) || locale.contains("th") || locale.contains("da") || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains(str3) || locale.contains(str7) || locale.contains("fr") || locale.contains(str8) || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains(str6) || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains(str) || locale.contains(str5)) {
                Log.e("RRRRR", "HHHHHHHHHH");
                return;
            }
            Log.e("Else", "eeeeeeeeeeee");
            if (AlarmReceiver.random == 1 || AlarmReceiver.random == 16 || AlarmReceiver.random == 17 || AlarmReceiver.random == 18 || AlarmReceiver.random == 19) {
                childClickMethod(0, 0);
            }
            if (AlarmReceiver.random == 2) {
                i2 = 1;
                childClickMethod(1, 0);
            } else {
                i2 = 1;
            }
            if (AlarmReceiver.random == 3) {
                childClickMethod(i2, i2);
            }
            if (AlarmReceiver.random == 4) {
                childClickMethod(i2, 2);
            }
            if (AlarmReceiver.random == 5) {
                childClickMethod(i2, 3);
            }
            if (AlarmReceiver.random == 6) {
                childClickMethod(i2, 4);
            }
            if (AlarmReceiver.random == 7) {
                childClickMethod(i2, 5);
            }
            if (AlarmReceiver.random == 8) {
                childClickMethod(i2, 6);
            }
            if (AlarmReceiver.random == 9) {
                childClickMethod(i2, 7);
            }
            if (AlarmReceiver.random == 10) {
                childClickMethod(i2, 8);
            }
            if (AlarmReceiver.random == 11) {
                childClickMethod(i2, 9);
            }
            if (AlarmReceiver.random == 12) {
                childClickMethod(i2, 10);
            }
            if (AlarmReceiver.random == 13) {
                childClickMethod(i2, 11);
            }
            if (AlarmReceiver.random == 14) {
                childClickMethod(i2, 12);
            }
            if (AlarmReceiver.random == 15) {
                childClickMethod(i2, 13);
            }
        }
    }


    private void interstitialAd() {
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                RequestInterstitial();
                switch (id) {
                    case 101:
                        TaroQuestionsActivity.this.drawer.openDrawer(TaroQuestionsActivity.this.linearLayout);
                        TaroQuestionsActivity.this.childClickMethod(0, 0);
                        break;
                    case 102:
                        TaroQuestionsActivity.this.drawer.openDrawer(TaroQuestionsActivity.this.linearLayout);
                        TaroQuestionsActivity.this.childClickMethod(0, 1);
                        break;
                    case 103:
                        TaroQuestionsActivity.this.drawer.openDrawer(TaroQuestionsActivity.this.linearLayout);
                        TaroQuestionsActivity.this.listview.expandGroup(1);
                        break;

                }
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

            }
        });
    }

    public void RequestInterstitial() {
        try {
            mInterstitialAd = new InterstitialAd(activity);
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial));
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void childClickMethod(int i2, int i3) {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        String sb;
        int i20;
        int i21;
        int i22;
        int i23;
        int i24;
        int i25;
        int i32;
        String str14 = "da";
        if (locale.contains("ja") || locale.contains("th") || locale.contains(str14) || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("es") || locale.contains("fr") || locale.contains("hi") || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl")) {
            Log.e("KKKKKKK", "LLLLLLLLLL");
        } else {
            Log.e("Else", "Leeeeeeeeeeee");
            this.drawer.closeDrawer(this.linearLayout);
            this.dailybtn.setVisibility(View.INVISIBLE);
            this.monthlybtn2.setVisibility(View.INVISIBLE);
            this.getrdngbtn3.setVisibility(View.INVISIBLE);
            this.ladyimg.setVisibility(View.INVISIBLE);
            this.tableimg.setVisibility(View.INVISIBLE);
        }
        ArrayList<String> arrayList = tarosubcat;
        arrayList.removeAll(arrayList);
        ArrayList<String> arrayList2 = tarosubcat2;
        arrayList2.removeAll(arrayList2);
        ArrayList<String> arrayList3 = tarosubcat3;
        arrayList3.removeAll(arrayList3);
        String str15 = "control2";
        String str16 = "control";
        String str17 = "one";
        String str18 = "";
        String str19 = "dd/M/yyyy hh:mm:ss";
        String str20 = "rateus";
        String str21 = "kkkk";
        if (i2 == 0) {
            if (i3 == 0) {
                this.menu = "Daily Tarot Horoscope";
                FlurryLog();
                googleanalytic("Horoscopes");
                Log.e("TAG", "Horoscopes");
                Go(this.menu, str17, getResources().getString(R.string.dailytaro), str18);
            }
            if (i3 == 1) {
                this.menu = "Monthly Tarot Reading";
                FlurryLog();
                googleanalytic("Horoscopes");
                Go(this.menu, "five", getResources().getString(R.string.monthlytaro), str18);
            }
            if (i3 == 2) {
                this.menu = "Birthday Tarot Reading";
                FlurryLog();
                googleanalytic("Horoscopes");
                Go(this.menu, "three", getResources().getString(R.string.birthdaytaro), str18);
            }
            if (i3 == 3) {
                try {
                    String string = sharedpreferences.getString("dateunlockedyearly", new SimpleDateFormat(str19, Locale.ENGLISH).format(Calendar.getInstance().getTime()));
                    Log.e(str21, str16);
                    Date time2 = Calendar.getInstance().getTime();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str19, Locale.ENGLISH);
                    String format = simpleDateFormat.format(time2);
                    Log.e(str21, string);
                    Date parse = simpleDateFormat.parse(string);
                    Log.e(str21, str15);
                    getDifference(parse, simpleDateFormat.parse(format));
                    if (sharedpreferences.getBoolean(str20, false)) {
                        this.menu = "2020 Tarot Reading";
                        FlurryLog();
                        googleanalytic("Horoscopes");
                        Go(this.menu, "five", getResources().getString(R.string.taro2020), str18);
                    } else if (!isNetworkAvailable()) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.interneterror), Toast.LENGTH_LONG).show();
                        this.main.setBackgroundResource(R.drawable.bg_main);
                        this.dailybtn.setVisibility(View.VISIBLE);
                        this.getrdngbtn3.setVisibility(View.VISIBLE);
                        this.monthlybtn2.setVisibility(View.VISIBLE);
                        this.subcatlist.setVisibility(View.INVISIBLE);
                        this.header.setVisibility(View.INVISIBLE);
                        this.header0.setVisibility(View.INVISIBLE);
                        this.ladyimg.setVisibility(View.VISIBLE);
                        this.tableimg.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.tryagain), Toast.LENGTH_LONG).show();
                        this.main.setBackgroundResource(R.drawable.bg_main);
                        this.dailybtn.setVisibility(View.VISIBLE);
                        this.getrdngbtn3.setVisibility(View.VISIBLE);
                        this.monthlybtn2.setVisibility(View.VISIBLE);
                        this.subcatlist.setVisibility(View.INVISIBLE);
                        this.header.setVisibility(View.INVISIBLE);
                        this.header0.setVisibility(View.INVISIBLE);
                        this.ladyimg.setVisibility(View.VISIBLE);
                        this.tableimg.setVisibility(View.VISIBLE);
                    }
                } catch (ParseException unused) {
                    unused.printStackTrace();
                }
            }
        }
        String str22 = "pt";
        if (i2 == 1) {
            String str23 = "ccc2";
            String str24 = "ccc";
            String str25 = "head";
            String str26 = "type";
            String str27 = str15;
            String str28 = "Get Tarot Readings";
            if (i3 == 0) {
                this.menu = "Life";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.life);
                if (locale.contains(str14) || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("fr") || locale.contains("hi") || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl")) {
                    Intent intent = new Intent(this, TaroCardsQuestionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(str26, str17);
                    bundle.putString(str25, getResources().getString(R.string.life));
                    bundle.putString(str24, "Life");
                    bundle.putString(str23, getResources().getString(R.string.life));
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    getSubCat();
                }
            }
            if (i3 == 1) {
                this.menu = "Love & Relationships";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.loveNrel);
                if (locale.contains(str14) || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("fr") || locale.contains("hi") || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl")) {
                    Intent intent2 = new Intent(this, TaroCardsQuestionActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(str26, str17);
                    bundle2.putString(str25, getResources().getString(R.string.loveNrel));
                    bundle2.putString(str24, "Love & Relationships");
                    bundle2.putString(str23, getResources().getString(R.string.loveNrel));
                    intent2.putExtras(bundle2);
                    startActivity(intent2);
                } else {
                    this.subcatlist.setVisibility(View.VISIBLE);
                    this.header.setVisibility(View.VISIBLE);
                    this.header.setText(getResources().getString(R.string.which_tr_u_want));
                    this.header0.setVisibility(View.VISIBLE);
                    TextView textView = this.header0;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(getResources().getString(R.string.ychoose));
                    sb2.append(this.c_subcat);
                    textView.setText(sb2.toString());
                    this.main.setBackgroundResource(R.drawable.bg_main);
                    fill_subcat_LoveAndRelationship();
                }
            }
            if (i3 == 2) {
                this.menu = "Family & Friends";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.familyNfriends);
                if (locale.contains(str14) || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("fr") || locale.contains("hi") || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl")) {
                    Intent intent3 = new Intent(this, TaroCardsQuestionActivity.class);
                    Bundle bundle3 = new Bundle();
                    bundle3.putString(str26, str17);
                    bundle3.putString(str25, getResources().getString(R.string.familyNfriends));
                    bundle3.putString(str24, "Family & Friends");
                    bundle3.putString(str23, getResources().getString(R.string.familyNfriends));
                    intent3.putExtras(bundle3);
                    startActivity(intent3);
                } else {
                    getSubCat();
                }
            }
            if (i3 == 3) {
                this.menu = "Money";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.money);
                if (locale.contains(str14) || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("fr") || locale.contains("hi") || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl")) {
                    Intent intent4 = new Intent(this, TaroCardsQuestionActivity.class);
                    Bundle bundle4 = new Bundle();
                    bundle4.putString(str26, str17);
                    bundle4.putString(str25, getResources().getString(R.string.money));
                    bundle4.putString(str24, "Money");
                    bundle4.putString(str23, getResources().getString(R.string.money));
                    intent4.putExtras(bundle4);
                    startActivity(intent4);
                } else {
                    getSubCat();
                }
            }
            if (i3 == 4) {
                this.menu = "Health";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.health);
                if (locale.contains(str14) || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("fr") || locale.contains("hi") || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl")) {
                    Intent intent5 = new Intent(this, TaroCardsQuestionActivity.class);
                    Bundle bundle5 = new Bundle();
                    bundle5.putString(str26, str17);
                    bundle5.putString(str25, getResources().getString(R.string.health));
                    bundle5.putString(str24, "Health");
                    bundle5.putString(str23, getResources().getString(R.string.health));
                    intent5.putExtras(bundle5);
                    startActivity(intent5);
                } else {
                    getSubCat();
                }
            }
            if (i3 == 5) {
                this.menu = "Dreams & Ambitions";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.dreamsNami);
                getSubCat();
            }
            if (i3 == 6) {
                this.menu = "Travel";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.travel);
                getSubCat();
            }
            if (i3 == 7) {
                this.main.setBackgroundResource(R.drawable.bg_main);
                this.menu = "Work & Career";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.workNcareer);
                getSubCat();
            }
            if (i3 == 8) {
                this.menu = "Focus";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.focus);
                getSubCat();
            }
            if (i3 == 9) {
                this.menu = "Success";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.success);
                getSubCat();
            }
            if (i3 == 10) {
                this.menu = "Luck";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.luck);
                getSubCat();
            }
            if (i3 == 11) {
                this.menu = "Emotional And Mental State";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.emoNmental);
                getSubCat();
            }
            if (i3 == 12) {
                this.menu = "Marriage";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.marriage);
                getSubCat();
            }
            if (i3 == 13) {
                this.menu = "Past Life";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.pastlife);
                this.type = "three";
                this.dailybtn.setVisibility(View.VISIBLE);
                this.getrdngbtn3.setVisibility(View.VISIBLE);
                this.monthlybtn2.setVisibility(View.VISIBLE);
                this.ladyimg.setVisibility(View.VISIBLE);
                this.tableimg.setVisibility(View.VISIBLE);
                if (!sharedpreferences.getBoolean(str20, false)) {
                    i32 = 4;
                } else {
                    i32 = 4;
                }
                this.subcatlist.setVisibility(i32);
                this.header.setVisibility(i32);
                this.header0.setVisibility(i32);
                Bundle bundle6 = new Bundle();
                bundle6.putString(str25, this.c_subcat);
                bundle6.putString(str24, this.menu);
                bundle6.putString(str26, this.type);
                bundle6.putString(str23, str18);
                Intent intent6 = new Intent(getApplicationContext(), TaroCardsQuestionActivity.class);
                intent6.putExtras(bundle6);
                startActivityForResult(intent6, 0);
            }
            if (i3 == 14) {
                this.menu = "Change & Transformation";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.changeNtrans);
                getSubCat();
            }
            if (i3 == 15) {
                this.menu = "Destiny & Life Purpose";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.destNlyfPurpose);
                getSubCat();
            }
            if (i3 == 16) {
                this.menu = "Opportunities & Obstacles";
                FlurryLog();
                googleanalytic(str28);
                this.c_subcat = getResources().getString(R.string.oppNobstacles);
                this.type = str17;
                getSubCat();
            } else {
                getSubCat();
            }
        }

        if (i2 == 2) {
            if (i3 == 0) {
                Log.e("TaroQuestion", "tracker - Get Answer  -- Question on your mind");
                this.menu = "Immediate Question On your Mind";
                FlurryLog();
                googleanalytic("Get Answers");
                Go(this.menu, str17, getResources().getString(R.string.immQmind), str18);
            }
            if (i3 == 1) {
                Log.e("TaroQuestion", "tracker - Get Answer  -- Wish be fulfilled");
                this.menu = "Will Your Wish Be Fulfilled?";
                FlurryLog();
                googleanalytic("Get Answers");
                Go(this.menu, str17, getResources().getString(R.string.wishfulfilled), str18);
            }
            if (i3 == 2) {
                Log.e("TaroQuestion", "tracker - Get Answer  -- Yes or No");
                this.menu = "Yes or No";
                FlurryLog();
                googleanalytic("Get Answers");
                Go(this.menu, str17, getResources().getString(R.string.yesorno), str18);
            }
        }
        if (i2 == 3) {
            this.menu = "Meanings Of All Tarot Cards";
            FlurryLog();
            if (i3 == 0) {
                Log.e("TaroQuestion", "tracker - Meaning of all Tarot Cards  -- Major Arcana");
                this.subcatlist.setVisibility(View.VISIBLE);
                this.header.setVisibility(View.VISIBLE);
                TextView textView2 = this.header;
                StringBuilder sb9 = new StringBuilder();
                sb9.append(getResources().getString(R.string.ychoose));
                sb9.append(" ");
                sb9.append(this.dhead4.get(i3));
                textView2.setText(sb9.toString());
                this.header0.setVisibility(View.INVISIBLE);
                this.main.setBackgroundResource(R.drawable.bg_main);
                fill_subcat_MajorArcana();
            }
            if (i3 == 1) {
                Log.e("TaroQuestion", "tracker - Meaning of all Tarot Cards  -- Minor Arcana");
                this.subcatlist.setVisibility(View.VISIBLE);
                this.header.setVisibility(View.VISIBLE);
                TextView textView3 = this.header;
                StringBuilder sb10 = new StringBuilder();
                sb10.append(getResources().getString(R.string.ychoose));
                sb10.append(" ");
                sb10.append(this.dhead4.get(i3));
                textView3.setText(sb10.toString());
                this.header0.setVisibility(View.INVISIBLE);
                this.main.setBackgroundResource(R.drawable.bg_main);
                fill_subcat_MinorArcana();

            }
        }
        if (i2 == 4) {
            String str33 = "Most Popular Tarot Spreads";
            this.menu = str33;
            if (i3 == 0) {
                Log.e("TaroQuestion", "Most Popular Tarot Spreads  -- Celtic Cross");
                googleanalytic3(str33, "Celtic Cross");
                FlurryLog2("Celtic Cross");
                this.main.setBackgroundResource(R.drawable.bg_main);
                this.dailybtn.setVisibility(View.VISIBLE);
                this.getrdngbtn3.setVisibility(View.VISIBLE);
                this.monthlybtn2.setVisibility(View.VISIBLE);
                this.ladyimg.setVisibility(View.VISIBLE);
                this.tableimg.setVisibility(View.VISIBLE);
                if (!sharedpreferences.getBoolean(str20, false)) {
                    i25 = 4;
                } else {
                    i25 = 4;
                }
                this.subcatlist.setVisibility(i25);
                this.header.setVisibility(i25);
                this.header0.setVisibility(i25);
                startActivity(new Intent(getApplicationContext(), MostPopularCelticCrossActivity.class));
            }
            if (i3 == 1) {
                Log.e("TaroQuestion", "Most Popular Tarot Spreads  -- Tree Of Life");
                googleanalytic3(str33, "Tree Of Life");
                FlurryLog2("Tree Of Life");
                this.main.setBackgroundResource(R.drawable.bg_main);
                this.dailybtn.setVisibility(View.VISIBLE);
                this.getrdngbtn3.setVisibility(View.VISIBLE);
                this.monthlybtn2.setVisibility(View.VISIBLE);
                this.ladyimg.setVisibility(View.VISIBLE);
                this.tableimg.setVisibility(View.VISIBLE);
                if (!sharedpreferences.getBoolean(str20, false)) {
                    i24 = 4;
                } else {
                    i24 = 4;
                }
                this.subcatlist.setVisibility(i24);
                this.header.setVisibility(i24);
                this.header0.setVisibility(i24);
                startActivity(new Intent(getApplicationContext(), PopularTarotActivity.class));
            }
            if (i3 == 2) {
                if (!locale.contains("es")) {
                    if (!locale.contains(str14)) {
                        Log.e("TaroQuestion", "Most Popular Tarot Spreads  -- Decision Making");
                        googleanalytic3(str33, "Decision Making");
                        FlurryLog2("Decision Making");
                        this.main.setBackgroundResource(R.drawable.bg_main);
                        this.dailybtn.setVisibility(View.VISIBLE);
                        this.getrdngbtn3.setVisibility(View.VISIBLE);
                        this.monthlybtn2.setVisibility(View.VISIBLE);
                        this.ladyimg.setVisibility(View.VISIBLE);
                        this.tableimg.setVisibility(View.VISIBLE);
                        if (!sharedpreferences.getBoolean(str20, false)) {
                            i23 = 4;
                        } else {
                            i23 = 4;
                        }
                        this.subcatlist.setVisibility(i23);
                        this.header.setVisibility(i23);
                        this.header0.setVisibility(i23);
                        startActivity(new Intent(getApplicationContext(), TarodecisionmakingActivity.class));
                    }
                    StringBuilder sb11 = new StringBuilder();
                    sb11.append("unlockedDM_");
                    sb11.append(locale);
                    sb = sb11.toString();
                    String format7 = new SimpleDateFormat(str19, Locale.ENGLISH).format(Calendar.getInstance().getTime());
                    SharedPreferences sharedPreferences3 = sharedpreferences;
                    StringBuilder sb12 = new StringBuilder();
                    sb12.append("date");
                    sb12.append(sb);
                    String string3 = sharedPreferences3.getString(sb12.toString(), format7);

                    try {
                        Date time5 = Calendar.getInstance().getTime();
                        SimpleDateFormat simpleDateFormat5 = new SimpleDateFormat(str19, Locale.ENGLISH);
                        String format8 = simpleDateFormat5.format(time5);
                        Date parse5 = simpleDateFormat5.parse(string3);
                        int difference4 = getDifference(parse5, simpleDateFormat5.parse(format8));
                        if (sharedpreferences.getBoolean(sb, false)) {
                            if (!isNetworkAvailable()) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.interneterror), Toast.LENGTH_LONG).show();
                                this.main.setBackgroundResource(R.drawable.bg_main);
                                this.dailybtn.setVisibility(View.VISIBLE);
                                this.getrdngbtn3.setVisibility(View.VISIBLE);
                                this.monthlybtn2.setVisibility(View.VISIBLE);
                                this.ladyimg.setVisibility(View.VISIBLE);
                                this.tableimg.setVisibility(View.VISIBLE);
                                if (!sharedpreferences.getBoolean(str20, false)) {
                                    i21 = 4;
                                } else {
                                    i21 = 4;
                                }
                                this.subcatlist.setVisibility(i21);
                                this.header.setVisibility(i21);
                                this.header0.setVisibility(i21);
                            } else {
                                Toast.makeText(this, getResources().getString(R.string.tryagain), Toast.LENGTH_LONG).show();
                                this.main.setBackgroundResource(R.drawable.bg_main);
                                this.dailybtn.setVisibility(View.VISIBLE);
                                this.getrdngbtn3.setVisibility(View.VISIBLE);
                                this.monthlybtn2.setVisibility(View.VISIBLE);
                                this.ladyimg.setVisibility(View.VISIBLE);
                                this.tableimg.setVisibility(View.VISIBLE);
                                if (!sharedpreferences.getBoolean(str20, false)) {
                                    i22 = 4;
                                } else {
                                    i22 = 4;
                                }
                                this.subcatlist.setVisibility(i22);
                                this.header.setVisibility(i22);
                                this.header0.setVisibility(i22);
                            }
                        } else if (difference4 < 5) {
                            googleanalytic3(str33, "Decision Making");
                            FlurryLog2("Decision Making");
                            this.main.setBackgroundResource(R.drawable.bg_main);
                            this.dailybtn.setVisibility(View.VISIBLE);
                            this.getrdngbtn3.setVisibility(View.VISIBLE);
                            this.monthlybtn2.setVisibility(View.VISIBLE);
                            this.ladyimg.setVisibility(View.VISIBLE);
                            this.tableimg.setVisibility(View.VISIBLE);
                            if (!sharedpreferences.getBoolean(str20, false)) {
                                i20 = 4;
                            } else {
                                i20 = 4;
                            }
                            this.subcatlist.setVisibility(i20);
                            this.header.setVisibility(i20);
                            this.header0.setVisibility(i20);
                            startActivity(new Intent(getApplicationContext(), TarodecisionmakingActivity.class));
                        }
                    } catch (ParseException unused4) {
                    }
                } else {
                    StringBuilder sb112 = new StringBuilder();
                    sb112.append("unlockedDM_");
                    sb112.append(locale);
                    sb = sb112.toString();
                    String format72 = new SimpleDateFormat(str19, Locale.ENGLISH).format(Calendar.getInstance().getTime());
                    SharedPreferences sharedPreferences32 = sharedpreferences;
                    StringBuilder sb122 = new StringBuilder();
                    sb122.append("date");
                    sb122.append(sb);
                    String string32 = sharedPreferences32.getString(sb122.toString(), format72);
                    Date time52 = Calendar.getInstance().getTime();
                    SimpleDateFormat simpleDateFormat52 = new SimpleDateFormat(str19, Locale.ENGLISH);
                    String format82 = simpleDateFormat52.format(time52);
                    Date parse52 = null;
                    try {
                        parse52 = simpleDateFormat52.parse(string32);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (sharedpreferences.getBoolean(sb, false)) {
                    }
                }
            }
            if (i3 == 3) {
                if (locale.contains(str14)) {
                    StringBuilder sb13 = new StringBuilder();
                    sb13.append("unlockedlove_");
                    sb13.append(locale);
                    String sb14 = sb13.toString();
                    try {
                        String format9 = new SimpleDateFormat(str19, Locale.ENGLISH).format(Calendar.getInstance().getTime());
                        SharedPreferences sharedPreferences4 = sharedpreferences;
                        StringBuilder sb15 = new StringBuilder();
                        sb15.append("date");
                        sb15.append(sb14);
                        String string4 = sharedPreferences4.getString(sb15.toString(), format9);
                        Date time6 = Calendar.getInstance().getTime();
                        SimpleDateFormat simpleDateFormat6 = new SimpleDateFormat(str19, Locale.ENGLISH);
                        String format10 = simpleDateFormat6.format(time6);
                        Date parse6 = simpleDateFormat6.parse(string4);
                        getDifference(parse6, simpleDateFormat6.parse(format10));
                        if (sharedpreferences.getBoolean(sb14, false)) {
                            googleanalytic3(str33, "Find Love");
                            FlurryLog2("Find Love");
                            this.main.setBackgroundResource(R.drawable.bg_main);
                            this.dailybtn.setVisibility(View.VISIBLE);
                            this.getrdngbtn3.setVisibility(View.VISIBLE);
                            this.monthlybtn2.setVisibility(View.VISIBLE);
                            this.ladyimg.setVisibility(View.VISIBLE);
                            this.tableimg.setVisibility(View.VISIBLE);
                            if (!sharedpreferences.getBoolean(str20, false)) {
                                i17 = 4;
                            } else {
                                i17 = 4;
                            }
                            this.subcatlist.setVisibility(i17);
                            this.header.setVisibility(i17);
                            this.header0.setVisibility(i17);
                            startActivity(new Intent(getApplicationContext(), TarolovespreadActivity.class));
                        } else if (!isNetworkAvailable()) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.interneterror), Toast.LENGTH_LONG).show();
                            this.main.setBackgroundResource(R.drawable.bg_main);
                            this.dailybtn.setVisibility(View.VISIBLE);
                            this.getrdngbtn3.setVisibility(View.VISIBLE);
                            this.monthlybtn2.setVisibility(View.VISIBLE);
                            this.ladyimg.setVisibility(View.VISIBLE);
                            this.tableimg.setVisibility(View.VISIBLE);
                            if (!sharedpreferences.getBoolean(str20, false)) {
                                i18 = 4;
                            } else {
                                i18 = 4;
                            }
                            this.subcatlist.setVisibility(i18);
                            this.header.setVisibility(i18);
                            this.header0.setVisibility(i18);
                        } else {
                            Toast.makeText(this, getResources().getString(R.string.tryagain), Toast.LENGTH_LONG).show();
                            this.main.setBackgroundResource(R.drawable.bg_main);
                            this.dailybtn.setVisibility(View.VISIBLE);
                            this.getrdngbtn3.setVisibility(View.VISIBLE);
                            this.monthlybtn2.setVisibility(View.VISIBLE);
                            this.ladyimg.setVisibility(View.VISIBLE);
                            this.tableimg.setVisibility(View.VISIBLE);
                            if (!sharedpreferences.getBoolean(str20, false)) {
                                i19 = 4;
                            } else {
                                i19 = 4;
                            }
                            this.subcatlist.setVisibility(i19);
                            this.header.setVisibility(i19);
                            this.header0.setVisibility(i19);
                        }
                    } catch (ParseException unused6) {
                        unused6.printStackTrace();
                    }
                } else {
                    googleanalytic3(str33, "Find Love");
                    FlurryLog2("Find Love");
                    this.main.setBackgroundResource(R.drawable.bg_main);
                    this.dailybtn.setVisibility(View.VISIBLE);
                    this.getrdngbtn3.setVisibility(View.VISIBLE);
                    this.monthlybtn2.setVisibility(View.VISIBLE);
                    this.ladyimg.setVisibility(View.VISIBLE);
                    this.tableimg.setVisibility(View.VISIBLE);
                    if (!sharedpreferences.getBoolean(str20, false)) {
                        i6 = 4;
                    } else {
                        i6 = 4;
                    }
                    this.subcatlist.setVisibility(i6);
                    this.header.setVisibility(i6);
                    this.header0.setVisibility(i6);
                    startActivity(new Intent(getApplicationContext(), TarolovespreadActivity.class));
                }
            }
            if (i3 == 4) {
                if (locale.contains(str14)) {
                    StringBuilder sb16 = new StringBuilder();
                    sb16.append("unlockedlife_");
                    sb16.append(locale);
                    String sb17 = sb16.toString();
                    try {
                        String format11 = new SimpleDateFormat(str19, Locale.ENGLISH).format(Calendar.getInstance().getTime());
                        SharedPreferences sharedPreferences5 = sharedpreferences;
                        StringBuilder sb18 = new StringBuilder();
                        sb18.append("date");
                        sb18.append(sb17);
                        String string5 = sharedPreferences5.getString(sb18.toString(), format11);
                        Date time7 = Calendar.getInstance().getTime();
                        SimpleDateFormat simpleDateFormat7 = new SimpleDateFormat(str19, Locale.ENGLISH);
                        String format12 = simpleDateFormat7.format(time7);
                        Date parse7 = simpleDateFormat7.parse(string5);
                        getDifference(parse7, simpleDateFormat7.parse(format12));
                        if (sharedpreferences.getBoolean(sb17, false)) {
                            googleanalytic3(str33, "Tarot Life");
                            FlurryLog2("Tarot Life");
                            this.main.setBackgroundResource(R.drawable.bg_main);
                            this.dailybtn.setVisibility(View.VISIBLE);
                            this.getrdngbtn3.setVisibility(View.VISIBLE);
                            this.monthlybtn2.setVisibility(View.VISIBLE);
                            this.ladyimg.setVisibility(View.VISIBLE);
                            this.tableimg.setVisibility(View.VISIBLE);
                            if (!sharedpreferences.getBoolean(str20, false)) {
                                i14 = 4;
                            } else {
                                i14 = 4;
                            }
                            this.subcatlist.setVisibility(i14);
                            this.header.setVisibility(i14);
                            this.header0.setVisibility(i14);
                            startActivity(new Intent(getApplicationContext(), TarokeyoflifeActivity.class));
                        } else if (!isNetworkAvailable()) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.interneterror), Toast.LENGTH_LONG).show();
                            this.main.setBackgroundResource(R.drawable.bg_main);
                            this.dailybtn.setVisibility(View.VISIBLE);
                            this.getrdngbtn3.setVisibility(View.VISIBLE);
                            this.monthlybtn2.setVisibility(View.VISIBLE);
                            this.ladyimg.setVisibility(View.VISIBLE);
                            this.tableimg.setVisibility(View.VISIBLE);
                            if (!sharedpreferences.getBoolean(str20, false)) {
                                i15 = 4;
                            } else {
                                i15 = 4;
                            }
                            this.subcatlist.setVisibility(i15);
                            this.header.setVisibility(i15);
                            this.header0.setVisibility(i15);
                        } else {
                            Toast.makeText(this, getResources().getString(R.string.tryagain), Toast.LENGTH_LONG).show();
                            this.main.setBackgroundResource(R.drawable.bg_main);
                            this.dailybtn.setVisibility(View.VISIBLE);
                            this.getrdngbtn3.setVisibility(View.VISIBLE);
                            this.monthlybtn2.setVisibility(View.VISIBLE);
                            this.ladyimg.setVisibility(View.VISIBLE);
                            this.tableimg.setVisibility(View.VISIBLE);
                            if (!sharedpreferences.getBoolean(str20, false)) {
                                i16 = 4;
                            } else {
                                i16 = 4;
                            }
                            this.subcatlist.setVisibility(i16);
                            this.header.setVisibility(i16);
                            this.header0.setVisibility(i16);
                        }
                    } catch (ParseException unused7) {
                        unused7.printStackTrace();
                    }
                } else {
                    googleanalytic3(str33, "Tarot Life");
                    FlurryLog2("Tarot Life");
                    this.main.setBackgroundResource(R.drawable.bg_main);
                    this.dailybtn.setVisibility(View.VISIBLE);
                    this.getrdngbtn3.setVisibility(View.VISIBLE);
                    this.monthlybtn2.setVisibility(View.VISIBLE);
                    this.ladyimg.setVisibility(View.VISIBLE);
                    this.tableimg.setVisibility(View.VISIBLE);
                    if (!sharedpreferences.getBoolean(str20, false)) {
                        i13 = 4;
                    } else {
                        i13 = 4;
                    }
                    this.subcatlist.setVisibility(i13);
                    this.header.setVisibility(i13);
                    this.header0.setVisibility(i13);
                    startActivity(new Intent(getApplicationContext(), TarokeyoflifeActivity.class));
                }
            }
            if (i3 == 5) {
                if (locale.contains(str14)) {
                    String str34 = "unlockedrelationship";
                    String format13 = new SimpleDateFormat(str19, Locale.ENGLISH).format(Calendar.getInstance().getTime());
                    SharedPreferences sharedPreferences6 = sharedpreferences;
                    StringBuilder sb19 = new StringBuilder();
                    sb19.append("date");
                    sb19.append(str34);
                    String string6 = sharedPreferences6.getString(sb19.toString(), format13);
                    Date time8 = Calendar.getInstance().getTime();
                    SimpleDateFormat simpleDateFormat8 = new SimpleDateFormat(str19, Locale.ENGLISH);
                    String format14 = simpleDateFormat8.format(time8);
                    Date parse8 = null;
                    try {
                        parse8 = simpleDateFormat8.parse(string6);
                        getDifference(parse8, simpleDateFormat8.parse(format14));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (sharedpreferences.getBoolean(str34, false)) {
                        googleanalytic3(str33, "Relationship Potential");
                        FlurryLog2("Relationship Potential");
                        this.main.setBackgroundResource(R.drawable.bg_main);
                        this.dailybtn.setVisibility(View.VISIBLE);
                        this.getrdngbtn3.setVisibility(View.VISIBLE);
                        this.monthlybtn2.setVisibility(View.VISIBLE);
                        this.ladyimg.setVisibility(View.VISIBLE);
                        this.tableimg.setVisibility(View.VISIBLE);
                        if (!sharedpreferences.getBoolean(str20, false)) {
                            i10 = 4;
                        } else {
                            i10 = 4;
                        }
                        this.subcatlist.setVisibility(i10);
                        this.header.setVisibility(i10);
                        this.header0.setVisibility(i10);
                        startActivity(new Intent(getApplicationContext(), RelationshipActivity.class));
                    } else if (!isNetworkAvailable()) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.interneterror), Toast.LENGTH_LONG).show();
                        this.main.setBackgroundResource(R.drawable.bg_main);
                        this.dailybtn.setVisibility(View.VISIBLE);
                        this.getrdngbtn3.setVisibility(View.VISIBLE);
                        this.monthlybtn2.setVisibility(View.VISIBLE);
                        this.ladyimg.setVisibility(View.VISIBLE);
                        this.tableimg.setVisibility(View.VISIBLE);
                        if (!sharedpreferences.getBoolean(str20, false)) {
                            i11 = 4;
                        } else {
                            i11 = 4;
                        }
                        this.subcatlist.setVisibility(i11);
                        this.header.setVisibility(i11);
                        this.header0.setVisibility(i11);
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.tryagain), Toast.LENGTH_LONG).show();
                        this.main.setBackgroundResource(R.drawable.bg_main);
                        this.dailybtn.setVisibility(View.VISIBLE);
                        this.getrdngbtn3.setVisibility(View.VISIBLE);
                        this.monthlybtn2.setVisibility(View.VISIBLE);
                        this.ladyimg.setVisibility(View.VISIBLE);
                        this.tableimg.setVisibility(View.VISIBLE);
                        if (!sharedpreferences.getBoolean(str20, false)) {
                            i12 = 4;
                        } else {
                            i12 = 4;
                        }
                        this.subcatlist.setVisibility(i12);
                        this.header.setVisibility(i12);
                        this.header0.setVisibility(i12);
                    }
                } else {
                    googleanalytic3(str33, "Relationship Potential");
                    FlurryLog2("Relationship Potential");
                    this.main.setBackgroundResource(R.drawable.bg_main);
                    this.dailybtn.setVisibility(View.VISIBLE);
                    this.getrdngbtn3.setVisibility(View.VISIBLE);
                    this.monthlybtn2.setVisibility(View.VISIBLE);
                    this.ladyimg.setVisibility(View.VISIBLE);
                    this.tableimg.setVisibility(View.VISIBLE);
                    if (!sharedpreferences.getBoolean(str20, false)) {
                        i10 = 4;
                    } else {
                        i10 = 4;
                    }
                    this.subcatlist.setVisibility(i10);
                    this.header.setVisibility(i10);
                    this.header0.setVisibility(i10);
                    startActivity(new Intent(getApplicationContext(), RelationshipActivity.class));
                }
            }
            if (i3 == 6) {
                if (locale.contains(str14)) {

                    String str35 = "unlockedrelpurpose";
                    try {
                        String format15 = new SimpleDateFormat(str19, Locale.ENGLISH).format(Calendar.getInstance().getTime());
                        SharedPreferences sharedPreferences7 = sharedpreferences;
                        StringBuilder sb20 = new StringBuilder();
                        sb20.append("date");
                        sb20.append(str35);
                        String string7 = sharedPreferences7.getString(sb20.toString(), format15);
                        Date time9 = Calendar.getInstance().getTime();
                        SimpleDateFormat simpleDateFormat9 = new SimpleDateFormat(str19, Locale.ENGLISH);
                        String format16 = simpleDateFormat9.format(time9);
                        Date parse9 = simpleDateFormat9.parse(string7);
                        getDifference(parse9, simpleDateFormat9.parse(format16));
                        if (sharedpreferences.getBoolean(str35, false)) {
                            googleanalytic3(str33, "Relationship Purpose");
                            FlurryLog2("Relationship Purpose");
                            this.main.setBackgroundResource(R.drawable.bg_main);
                            this.dailybtn.setVisibility(View.VISIBLE);
                            this.getrdngbtn3.setVisibility(View.VISIBLE);
                            this.monthlybtn2.setVisibility(View.VISIBLE);
                            this.ladyimg.setVisibility(View.VISIBLE);
                            this.tableimg.setVisibility(View.VISIBLE);
                            if (!sharedpreferences.getBoolean(str20, false)) {
                                i7 = 4;
                            } else {
                                i7 = 4;
                            }
                            this.subcatlist.setVisibility(i7);
                            this.header.setVisibility(i7);
                            this.header0.setVisibility(i7);
                            startActivity(new Intent(getApplicationContext(), SevenCardSpreadActivity.class));
                        } else if (!isNetworkAvailable()) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.interneterror), Toast.LENGTH_LONG).show();
                            this.main.setBackgroundResource(R.drawable.bg_main);
                            this.dailybtn.setVisibility(View.VISIBLE);
                            this.getrdngbtn3.setVisibility(View.VISIBLE);
                            this.monthlybtn2.setVisibility(View.VISIBLE);
                            this.ladyimg.setVisibility(View.VISIBLE);
                            this.tableimg.setVisibility(View.VISIBLE);
                            if (!sharedpreferences.getBoolean(str20, false)) {
                                i8 = 4;
                            } else {
                                i8 = 4;
                            }
                            this.subcatlist.setVisibility(i8);
                            this.header.setVisibility(i8);
                            this.header0.setVisibility(i8);

                        } else {
                            Toast.makeText(this, getResources().getString(R.string.tryagain), Toast.LENGTH_LONG).show();
                            this.main.setBackgroundResource(R.drawable.bg_main);
                            this.dailybtn.setVisibility(View.VISIBLE);
                            this.getrdngbtn3.setVisibility(View.VISIBLE);
                            this.monthlybtn2.setVisibility(View.VISIBLE);
                            this.ladyimg.setVisibility(View.VISIBLE);
                            this.tableimg.setVisibility(View.VISIBLE);
                            if (!sharedpreferences.getBoolean(str20, false)) {
                                i9 = 4;
                            } else {
                                i9 = 4;
                            }
                            this.subcatlist.setVisibility(i9);
                            this.header.setVisibility(i9);
                            this.header0.setVisibility(i9);
                        }
                    } catch (ParseException unused9) {

                    }
                } else {
                    googleanalytic3(str33, "Relationship Purpose");
                    FlurryLog2("Relationship Purpose");
                    this.main.setBackgroundResource(R.drawable.bg_main);
                    this.dailybtn.setVisibility(View.VISIBLE);
                    this.getrdngbtn3.setVisibility(View.VISIBLE);
                    this.monthlybtn2.setVisibility(View.VISIBLE);
                    this.ladyimg.setVisibility(View.VISIBLE);
                    this.tableimg.setVisibility(View.VISIBLE);
                    if (!sharedpreferences.getBoolean(str20, false)) {
                        i7 = 4;
                    } else {
                        i7 = 4;
                    }
                    this.subcatlist.setVisibility(i7);
                    this.header.setVisibility(i7);
                    this.header0.setVisibility(i7);
                    startActivity(new Intent(getApplicationContext(), SevenCardSpreadActivity.class));
                }
            }
        }
    }

    public void FlurryLog() {
        String str = "";
        String str2 = "XXXXXXXXX";
        if (locale.contains("ja") || locale.contains("th") || locale.contains("da") || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("hi") || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl") || locale.contains("pt") || locale.contains("es") || locale.contains("fr")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(this.menu);
            Log.e(str2, sb.toString());
            HashMap hashMap = new HashMap();
            hashMap.put("category", this.menu);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(locale);
            sb2.append(" - List item selected - google -  ");
            return;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(this.menu);
        Log.e(str2, sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append("List item selected - google -  ");
        sb4.append(this.menu);
    }

    public void FlurryLog2(String str) {
        String str2 = "";
        String str3 = "XXXXXXXXX";
        if (locale.contains("ja") || locale.contains("th") || locale.contains("da") || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("hi") || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl") || locale.contains("pt") || locale.contains("es") || locale.contains("fr")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(str);
            Log.e(str3, sb.toString());
            HashMap hashMap = new HashMap();
            hashMap.put("category", str);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(locale);
            sb2.append(" - List item selected - google -  ");
            return;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append(str);
        Log.e(str3, sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append("List item selected - google -  ");
        sb4.append(str);
    }

    public void FlurryLogUnlock(String str) {
        String str2 = "category";
        String str3 = "";
        String str4 = "XXXXXXXXX";
        if (locale.contains("ja") || locale.contains("th") || locale.contains("da") || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("hi") || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl") || locale.contains("pt") || locale.contains("es") || locale.contains("fr")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append(str);
            Log.e(str4, sb.toString());
            HashMap hashMap = new HashMap();
            hashMap.put(str2, str);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(locale);
            sb2.append(" - Unlocked Categories - google -  ");
            return;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str3);
        sb3.append(str);
        Log.e(str4, sb3.toString());
        HashMap hashMap2 = new HashMap();
        hashMap2.put(str2, str);
    }

    public void googleanalytic(String str) {
        String str2 = " menu  = ";
        String str3 = "tracker - catname  ";
        String str4 = "XXXXXXXXX";
        if (locale.contains("ja") || locale.contains("th") || locale.contains("da") || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("hi") || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl") || locale.contains("pt") || locale.contains("es") || locale.contains("fr")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append(str);
            sb.append(str2);
            sb.append(this.menu);
            Log.e(str4, sb.toString());
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str3);
        sb2.append(str);
        sb2.append(str2);
        sb2.append(this.menu);
        Log.e(str4, sb2.toString());
    }

    public void googleanalytic2(String str, String str2) {
        String str3 = " menu2 - ";
        String str4 = "  menu = ";
        String str5 = "tracker2 - catname = ";
        String str6 = "XXXXXXXXX";
        if (locale.contains("ja") || locale.contains("th") || locale.contains("da") || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("hi") || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl") || locale.contains("pt") || locale.contains("es") || locale.contains("fr")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str5);
            sb.append(str);
            sb.append(str4);
            sb.append(str2);
            sb.append(str3);
            sb.append(this.menu2);
            Log.e(str6, sb.toString());
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str5);
        sb2.append(str);
        sb2.append(str4);
        sb2.append(str2);
        sb2.append(str3);
        sb2.append(this.menu2);
        Log.e(str6, sb2.toString());
    }

    public void googleanalytic3(String str, String str2) {
        String str3 = "     ";
        String str4 = "";
        String str5 = "XXXXXXXXX";
        if (locale.contains("ja") || locale.contains("th") || locale.contains("da") || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("hi") || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl") || locale.contains("pt") || locale.contains("es") || locale.contains("fr")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str4);
            sb.append(str);
            sb.append(str3);
            sb.append(str2);
            Log.e(str5, sb.toString());
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str4);
        sb2.append(str);
        sb2.append(str3);
        sb2.append(str2);
        Log.e(str5, sb2.toString());
    }

    public void googleanalyticunlock(String str) {
        String str2 = "";
        String str3 = "XXXXXXXXX";
        if (locale.contains("ja") || locale.contains("th") || locale.contains("da") || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("hi") || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl") || locale.contains("pt") || locale.contains("es") || locale.contains("fr")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(str);
            Log.e(str3, sb.toString());
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(str);
        Log.e(str3, sb2.toString());
    }

    public void Go(String str, String str2, String str3, String str4) {
        this.main.setBackgroundResource(R.drawable.bg_main);
        this.dailybtn.setVisibility(View.VISIBLE);
        this.getrdngbtn3.setVisibility(View.VISIBLE);
        this.monthlybtn2.setVisibility(View.VISIBLE);
        this.ladyimg.setVisibility(View.VISIBLE);
        this.tableimg.setVisibility(View.VISIBLE);
        this.subcatlist.setVisibility(View.INVISIBLE);
        this.header.setVisibility(View.INVISIBLE);
        this.header0.setVisibility(View.INVISIBLE);
        Bundle bundle = new Bundle();
        bundle.putString("head", str3);
        bundle.putString("type", str2);
        bundle.putString("ccc", str);
        bundle.putString("ccc2", str4);
        Intent intent = new Intent(getApplicationContext(), TaroCardsQuestionActivity.class);
        intent.putExtras(bundle);
        Log.e("TAG", "Go");
        startActivityForResult(intent, 0);
    }

    public void getSubCat() {
        subcatlist.setVisibility(View.VISIBLE);
        header.setVisibility(View.VISIBLE);
        header.setText(getResources().getString(R.string.which_tr_u_want));
        header0.setVisibility(View.VISIBLE);
        btnlay.setVisibility(View.INVISIBLE);
        ladyimg.setVisibility(View.INVISIBLE);
        TextView textView = this.header0;
        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getString(R.string.ychoose));
        sb.append(" ");
        sb.append(this.c_subcat);
        textView.setText(sb.toString());
        this.main.setBackgroundResource(R.drawable.bg_main);
        fill_subcat();
    }

    public void fill_subcat() {
        if (tarosubcat.size() == 0) {
            tarosubcat.add("Get Your Question Answered");
            tarosubcat.add("Know about the Past, Present, Future");
        }
        if (tarosubcat3.size() == 0) {
            tarosubcat3.add(getResources().getString(R.string.get_ur_ques_ansed));
            tarosubcat3.add(getResources().getString(R.string.ppf));
        }
        CustomList_Subcat customList_Subcat = new CustomList_Subcat(this, tarosubcat3);
        this.adp2 = customList_Subcat;
        this.subcatlist.setAdapter(customList_Subcat);
        this.adp2.notifyDataSetChanged();
    }

    public void fill_subcat_MajorArcana() {
        tarosubcat2.add(getResources().getString(R.string.thefool));
        tarosubcat2.add(getResources().getString(R.string.themagician));
        tarosubcat2.add(getResources().getString(R.string.thehighpriestress));
        tarosubcat2.add(getResources().getString(R.string.theempress));
        tarosubcat2.add(getResources().getString(R.string.theemperor));
        tarosubcat2.add(getResources().getString(R.string.thehierophant));
        tarosubcat2.add(getResources().getString(R.string.thelovers));
        tarosubcat2.add(getResources().getString(R.string.thechariot));
        tarosubcat2.add(getResources().getString(R.string.strength));
        tarosubcat2.add(getResources().getString(R.string.thehermit));
        tarosubcat2.add(getResources().getString(R.string.wheeloffortune));
        tarosubcat2.add(getResources().getString(R.string.justice));
        tarosubcat2.add(getResources().getString(R.string.thehangedman));
        tarosubcat2.add(getResources().getString(R.string.death));
        tarosubcat2.add(getResources().getString(R.string.temperance));
        tarosubcat2.add(getResources().getString(R.string.thedevil));
        tarosubcat2.add(getResources().getString(R.string.thetower));
        tarosubcat2.add(getResources().getString(R.string.thestar));
        tarosubcat2.add(getResources().getString(R.string.themoon));
        tarosubcat2.add(getResources().getString(R.string.thesun));
        tarosubcat2.add(getResources().getString(R.string.judgement));
        tarosubcat2.add(getResources().getString(R.string.theworld));
        if (tarosubcat.size() == 0) {
            tarosubcat.add("The Fool");
            tarosubcat.add("The Magician");
            tarosubcat.add("The High Priestess");
            tarosubcat.add("The Empress");
            tarosubcat.add("The Emperor");
            tarosubcat.add("The Hierophant");
            tarosubcat.add("The Lovers");
            tarosubcat.add("The Chariot");
            tarosubcat.add("Strength");
            tarosubcat.add("The Hermit");
            tarosubcat.add("Wheel Of Fortune");
            tarosubcat.add("Justice");
            tarosubcat.add("The Hanged Man");
            tarosubcat.add("Death");
            tarosubcat.add("Temperance");
            tarosubcat.add("The Devil");
            tarosubcat.add("The Tower");
            tarosubcat.add("The Star");
            tarosubcat.add("The Moon");
            tarosubcat.add("The Sun");
            tarosubcat.add("Judgement");
            tarosubcat.add("The World");
        }
        CustomList_Subcat customList_Subcat = new CustomList_Subcat(this, tarosubcat2);
        this.adp2 = customList_Subcat;
        this.subcatlist.setAdapter(customList_Subcat);
        this.adp2.notifyDataSetChanged();
    }

    public void fill_subcat_MinorArcana() {
        if (tarosubcat.size() == 0) {
            tarosubcat.add("Pentacles");
            tarosubcat.add("Cups");
            tarosubcat.add("Swords");
            tarosubcat.add("Wands");
        }
        if (tarosubcat3.size() == 0) {
            tarosubcat3.add(getResources().getString(R.string.pentacle));
            tarosubcat3.add(getResources().getString(R.string.cups));
            tarosubcat3.add(getResources().getString(R.string.swords));
            tarosubcat3.add(getResources().getString(R.string.wands));
        }
        CustomList_Subcat customList_Subcat = new CustomList_Subcat(this, tarosubcat3);
        this.adp2 = customList_Subcat;
        this.subcatlist.setAdapter(customList_Subcat);
        this.adp2.notifyDataSetChanged();
    }

    public void fill_subcat_LoveAndRelationship() {
        if (tarosubcat.size() == 0) {
            tarosubcat.add("Get Your Question Answered");
            tarosubcat.add("Know about the Past, Present, Future");
            tarosubcat.add("Complete Relationship Analysis");
        }
        if (tarosubcat3.size() == 0) {
            tarosubcat3.add(getResources().getString(R.string.get_ur_ques_ansed));
            tarosubcat3.add(getResources().getString(R.string.ppf));
            tarosubcat3.add(getResources().getString(R.string.cra));
        }
        CustomList_Subcat customList_Subcat = new CustomList_Subcat(this, tarosubcat3);
        this.adp2 = customList_Subcat;
        this.subcatlist.setAdapter(customList_Subcat);
        this.adp2.notifyDataSetChanged();
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @SuppressLint("WrongConstant")
    public void setAlarm() {
        this.alarmManager = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
        Intent intent = new Intent(this, AlarmReceiver.class);
        this.alarmIntent = intent;
        this.pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 134217728);
        Calendar instance = Calendar.getInstance();
        instance.set(11, 9);
        this.alarmManager.setRepeating(0, instance.getTimeInMillis() + 86400000, 86400000, this.pendingIntent);
    }

    public LinkedHashMap<String, List<String>> getData() {
        this.hor.add("Daily Tarot Horoscope");
        this.hor.add("Monthly Tarot Reading");
        this.hor.add("Birthday Tarot Reading");
        this.head3.add("Immediate Question On your Mind");
        this.head3.add("Will Your Wish Be Fulfilled?");
        this.head3.add("Yes or No");
        this.head2.add("Life");
        this.head2.add("Love & Relationships");
        this.head2.add("Family & Friends");
        this.head2.add("Money");
        this.head2.add("Health");
        this.head2.add("Dreams & Ambitions");
        this.head2.add("Travel");
        this.head2.add("Work & Career");
        this.head2.add("Focus");
        this.head2.add("Success");
        this.head2.add("Luck");
        this.head2.add("Emotional And Mental State");
        this.head2.add("Marriage");
        this.head2.add("Past Life");
        String str = "es";
        String str2 = "Destiny & Life Purpose";
        String str3 = "Change & Transformation";
        if (!locale.contains(str)) {
            this.head2.add(str3);
            this.head2.add(str2);
            this.head2.add("Opportunities & Obstacles");
        }
        String str4 = "pt";
        if (locale.contains(str4) || locale.contains(str)) {
            this.head2.add(str3);
            this.head2.add(str2);
        }
        this.head1.add("Celtic Cross");
        this.head1.add("Tree Of Life");
        this.head4.add("Major Arcana");
        this.head4.add("Minor Arcana");
        this.map.put("Horoscopes", this.hor);
        this.map.put("Get Tarot Readings", this.head2);
        this.map.put("Get Answers", this.head3);
        this.map.put("Meanings Of All Tarot Cards", this.head4);
        this.map.put("Most Popular Tarot Spreads", this.head1);

        this.dhor.add(getResources().getString(R.string.dailytaro));
        this.dhor.add(getResources().getString(R.string.monthlytaro));
        this.dhor.add(getResources().getString(R.string.birthdaytaro));
        this.dhor.add(getResources().getString(R.string.taro2020));
        this.dhead3.add(getResources().getString(R.string.immQmind));
        this.dhead3.add(getResources().getString(R.string.wishfulfilled));
        this.dhead3.add(getResources().getString(R.string.yesorno));
        this.dhead2.add(getResources().getString(R.string.life));
        this.dhead2.add(getResources().getString(R.string.loveNrel));
        this.dhead2.add(getResources().getString(R.string.familyNfriends));
        this.dhead2.add(getResources().getString(R.string.money));
        this.dhead2.add(getResources().getString(R.string.health));
        this.dhead2.add(getResources().getString(R.string.dreamsNami));
        this.dhead2.add(getResources().getString(R.string.travel));
        this.dhead2.add(getResources().getString(R.string.workNcareer));
        this.dhead2.add(getResources().getString(R.string.focus));
        this.dhead2.add(getResources().getString(R.string.success));
        this.dhead2.add(getResources().getString(R.string.luck));
        this.dhead2.add(getResources().getString(R.string.emoNmental));
        this.dhead2.add(getResources().getString(R.string.marriage));
        this.dhead2.add(getResources().getString(R.string.pastlife));
        if (!locale.contains(str4)) {
            this.dhead2.add(getResources().getString(R.string.changeNtrans));
            this.dhead2.add(getResources().getString(R.string.destNlyfPurpose));
            this.dhead2.add(getResources().getString(R.string.oppNobstacles));
        }
        if (locale.contains(str4)) {
            this.dhead2.add(getResources().getString(R.string.changeNtrans));
            this.dhead2.add(getResources().getString(R.string.destNlyfPurpose));
        }
        if (locale.contains(str4)) {
            this.dhead1.add(getResources().getString(R.string.celticcross));
            this.dhead1.add(getResources().getString(R.string.treeoflife));
            this.dhead1.add(getResources().getString(R.string.decisionmaking));
            if (locale.contains(str4)) {
                this.dhead1.add(getResources().getString(R.string.lovespread));
            }
            if (locale.contains(str4)) {
                this.dhead1.add(getResources().getString(R.string.keyoflife));
            }
        } else {
            this.dhead1.add("Celtic Cross\n(10 Cards Spreads)");
            this.dhead1.add("Tree Of Life\n(10 Cards Spreads)");
            this.dhead1.add(getResources().getString(R.string.decisionmaking));
            this.dhead1.add(getResources().getString(R.string.lovespread));
            this.dhead1.add(getResources().getString(R.string.keyoflife));
            this.dhead1.add(getResources().getString(R.string.relationship));
            this.dhead1.add(getResources().getString(R.string.relationshippurpose));
        }
        this.dhead4.add(getResources().getString(R.string.majorA));
        this.dhead4.add(getResources().getString(R.string.minorA));
        this.map2.put(getResources().getString(R.string.horoscope), this.dhor);
        this.map2.put(getResources().getString(R.string.gettr), this.dhead2);
        this.map2.put(getResources().getString(R.string.getans), this.dhead3);
        this.map2.put(getResources().getString(R.string.meaningoftc), this.dhead4);
        this.map2.put(getResources().getString(R.string.mostpopular), this.dhead1);

        this.map2.put(getResources().getString(R.string.rateustxt2), new ArrayList());
        return this.map2;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (!(!locale.contains("pt")) || !(!locale.contains("en"))) {
            this.actionBarDrawerToggle.onConfigurationChanged(configuration);
        }
    }

    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        if (!(!locale.contains("pt")) || !(!locale.contains("en"))) {
            this.actionBarDrawerToggle.syncState();
        }
    }

    @SuppressLint("WrongConstant")
    public void onBackPressed() {
        exitbool = true;
        if (ePreferences.getBoolean("pref_key_rate", false)) {
            ExitDialog();
        } else {
            RateDialog();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void RateDialog() {
        final boolean[] isRate = {false, false};
        final Dialog dialog = new Dialog(activity);
        final ImageView ivStar1, ivStar2, ivStar3, ivStar4, ivStar5;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.PauseDialogAnimation1);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setCanceledOnTouchOutside(false);
        AdLoader.Builder builder = new AdLoader.Builder(this, getResources().getString(R.string.admob_native));
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                FrameLayout frameLayout = dialog.findViewById(R.id.fl_adplaceholder);
                dialog.findViewById(R.id.tvLoadAds).setVisibility(View.GONE);
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.ad_unified_small, null);
                NativeAdvanceAds.populateUnifiedNativeAdViewDialog(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }
        });
        VideoOptions videoOptions = new VideoOptions.Builder().build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());

        ivStar1 = dialog.findViewById(R.id.ivStar1);
        ivStar2 = dialog.findViewById(R.id.ivStar2);
        ivStar3 = dialog.findViewById(R.id.ivStar3);
        ivStar4 = dialog.findViewById(R.id.ivStar4);
        ivStar5 = dialog.findViewById(R.id.ivStar5);

        ivStar1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ivStar1.setImageResource(R.drawable.star_fill);
                ivStar2.setImageResource(R.drawable.star_empty);
                ivStar3.setImageResource(R.drawable.star_empty);
                ivStar4.setImageResource(R.drawable.star_empty);
                ivStar5.setImageResource(R.drawable.star_empty);
                isRate[0] = false;
                isRate[1] = true;
                return false;
            }
        });
        ivStar2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ivStar1.setImageResource(R.drawable.star_fill);
                ivStar2.setImageResource(R.drawable.star_fill);
                ivStar3.setImageResource(R.drawable.star_empty);
                ivStar4.setImageResource(R.drawable.star_empty);
                ivStar5.setImageResource(R.drawable.star_empty);
                isRate[0] = false;
                isRate[1] = true;
                return false;
            }
        });
        ivStar3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ivStar1.setImageResource(R.drawable.star_fill);
                ivStar2.setImageResource(R.drawable.star_fill);
                ivStar3.setImageResource(R.drawable.star_fill);
                ivStar4.setImageResource(R.drawable.star_empty);
                ivStar5.setImageResource(R.drawable.star_empty);
                isRate[0] = false;
                isRate[1] = true;
                return false;
            }
        });
        ivStar4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ivStar1.setImageResource(R.drawable.star_fill);
                ivStar2.setImageResource(R.drawable.star_fill);
                ivStar3.setImageResource(R.drawable.star_fill);
                ivStar4.setImageResource(R.drawable.star_fill);
                ivStar5.setImageResource(R.drawable.star_empty);
                isRate[0] = true;
                isRate[1] = true;
                return false;
            }
        });
        ivStar5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ivStar1.setImageResource(R.drawable.star_fill);
                ivStar2.setImageResource(R.drawable.star_fill);
                ivStar3.setImageResource(R.drawable.star_fill);
                ivStar4.setImageResource(R.drawable.star_fill);
                ivStar5.setImageResource(R.drawable.star_fill);
                isRate[0] = true;
                isRate[1] = true;
                return false;
            }
        });
        dialog.findViewById(R.id.btnLater).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finishAffinity();
            }
        });
        dialog.findViewById(R.id.btnSubmit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRate[1]) {
                    dialog.dismiss();
                    if (isRate[0]) {
                        ePreferences.putBoolean("pref_key_rate", true);
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                        } catch (ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
                    }
                    finishAffinity();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select Your Review Star", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    public void ExitDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.PauseDialogAnimation1);
        dialog.setContentView(R.layout.dialog_layout_exit);
        dialog.setCanceledOnTouchOutside(false);
        AdLoader.Builder builder = new AdLoader.Builder(this, getResources().getString(R.string.admob_native));
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                FrameLayout frameLayout = dialog.findViewById(R.id.fl_adplaceholder);
                dialog.findViewById(R.id.tvLoadAds).setVisibility(View.GONE);
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.ad_unified_small, null);
                NativeAdvanceAds.populateUnifiedNativeAdViewDialog(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }
        });
        VideoOptions videoOptions = new VideoOptions.Builder().build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
        dialog.findViewById(R.id.btnLater).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.findViewById(R.id.btnSubmit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        dialog.show();
    }

    private void applyFontToMenuItem(MenuItem menuItem) {
        SpannableString spannableString = new SpannableString(menuItem.getTitle());
        menuItem.setTitle(spannableString);
    }

    public boolean onPrepareOptionsMenu(Menu menu3) {
        return super.onPrepareOptionsMenu(menu3);
    }

    public boolean onCreateOptionsMenu(Menu menu3) {
        String str = "hi";
        if (locale.contains("ja") || locale.contains("th") || locale.contains("da") || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("es") || locale.contains("fr") || locale.contains(str) || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl")) {
            getMenuInflater().inflate(R.menu.main, menu3);
            if (locale.contains(str)) {
                applyFontToMenuItem(menu3.findItem(R.id.language));
                applyFontToMenuItem(menu3.findItem(R.id.ratebtn));
            }
        } else {
            getMenuInflater().inflate(R.menu.second, menu3);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.language) {
            startActivity(new Intent(this, LanguageActivity.class));
        } else if (itemId == R.id.ratebtn) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.rateuslink)));
        }
        if (locale.contains("ja") || locale.contains("th") || locale.contains("da") || locale.contains("pl") || locale.contains("ko") || locale.contains("zh") || locale.contains("tr") || locale.contains("es") || locale.contains("fr") || locale.contains("hi") || locale.contains("nb") || locale.contains("sv") || locale.contains("de") || locale.contains("it") || locale.contains("ms") || locale.contains("ru") || locale.contains("nl") || locale.contains("fi") || locale.contains("el") || locale.contains("in") || locale.contains("vi") || locale.contains("tl")) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (this.actionBarDrawerToggle.onOptionsItemSelected(menuItem)) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(NOTIFICATION_COUNT, mNotificationCount);
        super.onSaveInstanceState(bundle);
    }

    public void updateUI() {
        mNotificationCount++;
        StringBuilder sb = new StringBuilder();
        sb.append("Notification count = ");
        sb.append(mNotificationCount);
        Log.e("NC", sb.toString());
    }

    public static int getDifference(Date date, Date date2) {
        return (int) ((date2.getTime() - date.getTime()) / 86400000);
    }

    public void onResume() {
        super.onResume();
        StringBuilder sb = new StringBuilder();
        sb.append("aa  ");
        sb.append(locale);
        Log.e("LOCALE", sb.toString());

        Log.e("AAAAA", "onResume   ");
        String str2 = "";
        if (this.showrateus) {
            String str3 = "2020 Tarot Reading";
            this.menu = str3;
            Go(str3, "five", getResources().getString(R.string.taro2020), str2);
            this.showrateus = false;
            FlurryLogUnlock(this.menu);
            googleanalyticunlock(this.menu);
        }
        boolean z = showwork;
        String str4 = "ccc2";
        String str5 = "type";
        String str6 = "ccc";
        String str7 = "head";
        String str8 = "one";
        if (z) {
            this.menu = "Work & Career";
            this.c_subcat = getResources().getString(R.string.workNcareer);
            this.type = str8;
            Bundle bundle = new Bundle();
            bundle.putString(str7, this.c_subcat);
            bundle.putString(str6, this.menu);
            bundle.putString(str5, this.type);
            bundle.putString(str4, str2);
            showwork = false;
            Intent intent = new Intent(getApplicationContext(), TaroCardsQuestionActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
            FlurryLogUnlock(this.menu);
            googleanalyticunlock(this.menu);
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.unlocked), Toast.LENGTH_LONG).show();
        }
        if (showdreams) {
            this.menu = "Dreams & Ambitions";
            this.c_subcat = getResources().getString(R.string.dreamsNami);
            this.type = str8;
            Bundle bundle2 = new Bundle();
            bundle2.putString(str7, this.c_subcat);
            bundle2.putString(str6, this.menu);
            bundle2.putString(str5, this.type);
            bundle2.putString(str4, str2);
            showdreams = false;
            Intent intent2 = new Intent(getApplicationContext(), TaroCardsQuestionActivity.class);
            intent2.putExtras(bundle2);
            startActivityForResult(intent2, 0);
            FlurryLogUnlock(this.menu);
            googleanalyticunlock(this.menu);
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.unlocked), Toast.LENGTH_LONG).show();
        }
        if (showchange) {
            this.menu = "Change & Transformation";
            this.c_subcat = getResources().getString(R.string.changeNtrans);
            this.type = str8;
            Bundle bundle3 = new Bundle();
            bundle3.putString(str7, this.c_subcat);
            bundle3.putString(str6, this.menu);
            bundle3.putString(str5, this.type);
            bundle3.putString(str4, str2);
            showchange = false;
            Intent intent3 = new Intent(getApplicationContext(), TaroCardsQuestionActivity.class);
            intent3.putExtras(bundle3);
            startActivityForResult(intent3, 0);
            FlurryLogUnlock(this.menu);
            googleanalyticunlock(this.menu);
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.unlocked), Toast.LENGTH_LONG).show();
        }
        if (showdestiny) {
            this.menu = "Destiny & Life Purpose";
            this.c_subcat = getResources().getString(R.string.destNlyfPurpose);
            this.type = str8;
            Bundle bundle4 = new Bundle();
            bundle4.putString(str7, this.c_subcat);
            bundle4.putString(str6, this.menu);
            bundle4.putString(str5, this.type);
            bundle4.putString(str4, str2);
            showdestiny = false;
            Intent intent4 = new Intent(getApplicationContext(), TaroCardsQuestionActivity.class);
            intent4.putExtras(bundle4);
            startActivityForResult(intent4, 0);
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.unlocked), Toast.LENGTH_LONG).show();
            FlurryLogUnlock(this.menu);
            googleanalyticunlock(this.menu);
        }
        if (showopportunities) {
            this.menu = "Opportunities & Obstacles";
            this.c_subcat = getResources().getString(R.string.oppNobstacles);
            this.type = str8;
            Bundle bundle5 = new Bundle();
            bundle5.putString(str7, this.c_subcat);
            bundle5.putString(str6, this.menu);
            bundle5.putString(str5, this.type);
            bundle5.putString(str4, str2);
            showopportunities = false;
            Intent intent5 = new Intent(getApplicationContext(), TaroCardsQuestionActivity.class);
            intent5.putExtras(bundle5);
            startActivityForResult(intent5, 0);
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.unlocked), Toast.LENGTH_LONG).show();
            FlurryLogUnlock(this.menu);
            googleanalyticunlock(this.menu);
        }
        String str9 = "rateus";
        if (showrelationshippotential) {
            String str10 = "Relationship Potential";
            FlurryLogUnlock(str10);
            googleanalyticunlock(str10);
            this.main.setBackgroundResource(R.drawable.bg_main);
            this.dailybtn.setVisibility(View.VISIBLE);
            this.getrdngbtn3.setVisibility(View.VISIBLE);
            this.monthlybtn2.setVisibility(View.VISIBLE);
            this.ladyimg.setVisibility(View.VISIBLE);
            this.tableimg.setVisibility(View.VISIBLE);
            this.subcatlist.setVisibility(View.INVISIBLE);
            this.header.setVisibility(View.INVISIBLE);
            this.header0.setVisibility(View.INVISIBLE);
            showrelationshippotential = false;
            startActivity(new Intent(getApplicationContext(), RelationshipActivity.class));
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.unlocked), Toast.LENGTH_LONG).show();
        }
        if (showrelationshippurpose) {
            String str11 = "Relationship Purpose";
            FlurryLogUnlock(str11);
            googleanalyticunlock(str11);
            this.main.setBackgroundResource(R.drawable.bg_main);
            this.dailybtn.setVisibility(View.VISIBLE);
            this.getrdngbtn3.setVisibility(View.VISIBLE);
            this.monthlybtn2.setVisibility(View.VISIBLE);
            this.ladyimg.setVisibility(View.VISIBLE);
            this.tableimg.setVisibility(View.VISIBLE);
            this.subcatlist.setVisibility(View.INVISIBLE);
            this.header.setVisibility(View.INVISIBLE);
            this.header0.setVisibility(View.INVISIBLE);
            showrelationshippurpose = false;
            startActivity(new Intent(getApplicationContext(), SevenCardSpreadActivity.class));
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.unlocked), Toast.LENGTH_LONG).show();
        }
        if (showdecisioninlang) {
            StringBuilder sb2 = new StringBuilder();
            String str12 = "Decision Making - ";
            sb2.append(str12);
            sb2.append(locale);
            FlurryLogUnlock(sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str12);
            sb3.append(locale);
            googleanalyticunlock(sb3.toString());
            this.main.setBackgroundResource(R.drawable.bg_main);
            this.dailybtn.setVisibility(View.VISIBLE);
            this.getrdngbtn3.setVisibility(View.VISIBLE);
            this.monthlybtn2.setVisibility(View.VISIBLE);
            this.ladyimg.setVisibility(View.VISIBLE);
            this.tableimg.setVisibility(View.VISIBLE);
            this.subcatlist.setVisibility(View.INVISIBLE);
            this.header.setVisibility(View.INVISIBLE);
            this.header0.setVisibility(View.INVISIBLE);
            showdecisioninlang = false;
            startActivity(new Intent(getApplicationContext(), TarodecisionmakingActivity.class));
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.unlocked), Toast.LENGTH_LONG).show();
        }
        if (showloveinlang) {
            StringBuilder sb4 = new StringBuilder();
            String str13 = "Find Love - ";
            sb4.append(str13);
            sb4.append(locale);
            FlurryLogUnlock(sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str13);
            sb5.append(locale);
            googleanalyticunlock(sb5.toString());
            this.main.setBackgroundResource(R.drawable.bg_main);
            this.dailybtn.setVisibility(View.VISIBLE);
            this.getrdngbtn3.setVisibility(View.VISIBLE);
            this.monthlybtn2.setVisibility(View.VISIBLE);
            this.ladyimg.setVisibility(View.VISIBLE);
            this.tableimg.setVisibility(View.VISIBLE);
            this.subcatlist.setVisibility(View.INVISIBLE);
            this.header.setVisibility(View.INVISIBLE);
            this.header0.setVisibility(View.INVISIBLE);
            showloveinlang = false;
            Intent intent6 = new Intent(getApplicationContext(), TarolovespreadActivity.class);
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.unlocked), Toast.LENGTH_LONG).show();
            startActivity(intent6);
        }
        if (showkeyoflifeinlang) {
            StringBuilder sb6 = new StringBuilder();
            String str14 = "Tarot Life - ";
            sb6.append(str14);
            sb6.append(locale);
            FlurryLogUnlock(sb6.toString());
            StringBuilder sb7 = new StringBuilder();
            sb7.append(str14);
            sb7.append(locale);
            googleanalyticunlock(sb7.toString());
            this.main.setBackgroundResource(R.drawable.bg_main);
            this.dailybtn.setVisibility(View.VISIBLE);
            this.getrdngbtn3.setVisibility(View.VISIBLE);
            this.monthlybtn2.setVisibility(View.VISIBLE);
            this.ladyimg.setVisibility(View.VISIBLE);
            this.tableimg.setVisibility(View.VISIBLE);
            this.subcatlist.setVisibility(View.INVISIBLE);
            this.header.setVisibility(View.INVISIBLE);
            this.header0.setVisibility(View.INVISIBLE);
            showkeyoflifeinlang = false;
            startActivity(new Intent(getApplicationContext(), TarokeyoflifeActivity.class));
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.unlocked), Toast.LENGTH_LONG).show();
        }
    }

    public boolean isNetworkAvailable() {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onStart() {
        super.onStart();
        interstitialTimer = 15000;
    }

    public void onStop() {
        if (this.adp2 == null && !locale.contains("ja") && !locale.contains("th") && !locale.contains("da") && !locale.contains("pl") && !locale.contains("ko") && !locale.contains("zh") && !locale.contains("tr") && !locale.contains("es") && !locale.contains("fr") && !locale.contains("hi") && !locale.contains("nb") && !locale.contains("sv") && !locale.contains("de") && !locale.contains("it") && !locale.contains("ms") && !locale.contains("ru") && !locale.contains("nl") && !locale.contains("fi") && !locale.contains("el") && !locale.contains("in") && !locale.contains("vi") && !locale.contains("tl")) {
            this.dailybtn.setVisibility(View.VISIBLE);
            this.getrdngbtn3.setVisibility(View.VISIBLE);
            this.monthlybtn2.setVisibility(View.VISIBLE);
            this.ladyimg.setVisibility(View.VISIBLE);
            this.tableimg.setVisibility(View.VISIBLE);
        }
        super.onStop();
    }

    public static void stopRunnable() {
        if (stopbool) {
            Log.e("AAAAA", "stoprunnable");
            mHandler.removeCallbacks(changeAdBool);
        }
    }

    public void attachBaseContext(Context context) {
        super.attachBaseContext(Utils.changeLang(context, context.getApplicationContext().getSharedPreferences("MyPref", 0).getString("languagetoload", "en")));
    }

    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        if (i2 == 111 && iArr.length > 0) {
            int i3 = iArr[0];
        }
    }
}
