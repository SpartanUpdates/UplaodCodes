package com.videozoneinc.musicvideoeditor;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.videozoneinc.musicvideoeditor.modelclass.ImageData;
import com.videozoneinc.musicvideoeditor.modelclass.MusicData;
import com.videozoneinc.musicvideoeditor.util.EPreferences;
import com.videozoneinc.musicvideoeditor.util.PermissionModelUtil;
import com.videozoneinc.musicvideoeditor.util.Utils;
import com.videozoneinc.musicvideoeditor.utility.mask.THEMES;
import com.videozoneinc.musicvideoeditor.utility.mask.Themes1;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.videozoneinc.musicvideoeditor.video.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

public class MyApplication extends Application {
    public static int TEMP_POSITION = -1;
    public static int VIDEO_HEIGHT = 480;
    public static int VIDEO_WIDTH = 720;
    private static MyApplication instance;
    public static boolean isBreak = false;
    public static boolean isEndSave = false;
    public static boolean isLastRemoved = false;
    public static boolean isStartRemoved = false;
    public static boolean isStartSave = false;
    public static boolean isStoryAdded = false;
    public static boolean islistchanged;
    public float EXTRA_FRAME_TIME;
    public HashMap<String, ArrayList<ImageData>> allAlbum;
    private ArrayList<String> allFolder;
    Bitmap bitmapSecond;
    public int endFrame;
    int frame;
    public boolean isEditModeEnable;
    public boolean isFristTimeTheme;
    public boolean isFromSdCardAudio;
    public boolean isSelectSYS;
    String mAudioDirPath;
    public int min_pos;
    private MusicData musicData;
    private OnProgressReceiver onProgressReceiver;
    public int posForAddMusicDialog;
    private float second;
    private String selectedFolderId;
    public final ArrayList<ImageData> selectedImages;
    public THEMES selectedTheme;
    public Themes1 selected_theme;
    public int startFrame;
    public ArrayList<String> videoImages;
    public ArrayList<String> welcomeImages;
    public static int check = 1;
    private RequestQueue mRequestQueue;
    private static Context context;

    public static boolean IsVideo = false;

    public MyApplication() {
        this.posForAddMusicDialog = 0;
        this.videoImages = new ArrayList<String>();
        this.welcomeImages = new ArrayList<String>();
        this.selectedImages = new ArrayList<ImageData>();
        this.selectedFolderId = "";
        this.second = 2.0f;
        this.EXTRA_FRAME_TIME = 0.9f;
        this.selectedTheme = THEMES.Shine;
        this.selected_theme = Themes1.Shine;
        this.isEditModeEnable = false;
        this.isFromSdCardAudio = false;
        this.min_pos = Integer.MAX_VALUE;
        this.frame = 0;
        this.isFristTimeTheme = true;
        this.isSelectSYS = false;
        this.mAudioDirPath = "";
    }

    public static final String TAG = MyApplication.class.getSimpleName();

    public static MyApplication getInstance() {
        return MyApplication.instance;
    }

    private void init() {
        if (!new PermissionModelUtil((Context) this).needPermissionCheck()) {
            this.getFolderList();
            if (!FileUtils.APP_DIRECTORY.exists()) {
                FileUtils.APP_DIRECTORY.mkdirs();
            }
            this.copyAssets();
        }
        this.setVideoHeightWidth();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    private boolean isAudioFile(final String s) {
        return !TextUtils.isEmpty((CharSequence) s) && s.endsWith(".mp3");
    }

    public static boolean isMyServiceRunning(final Context context, final Class<?> clazz) {
        final Iterator<ActivityManager.RunningServiceInfo> iterator = ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE).iterator();
        while (iterator.hasNext()) {
            if (clazz.getName().equals(iterator.next().service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void setVideoHeightWidth() {
        final String s = this.getResources().getStringArray(R.array.video_height_width)[EPreferences.getInstance(this.getApplicationContext()).getInt("pref_key_video_quality", 2)];
        final StringBuilder sb = new StringBuilder();
        sb.append("MyApplication VideoQuality value is:- ");
        sb.append(s);
        MyApplication.VIDEO_WIDTH = Integer.parseInt(s.split(Pattern.quote("*"))[0]);
        MyApplication.VIDEO_HEIGHT = Integer.parseInt(s.split(Pattern.quote("*"))[1]);
    }

    public void addSelectedImage(final ImageData imageData) {
        if (MyApplication.isStoryAdded) {
            if (this.selectedImages.size() > 0) {

                this.selectedImages.add(this.selectedImages.size() - 1, imageData);
            } else {
                this.selectedImages.add(imageData);
            }

        } else {
            this.selectedImages.add(imageData);
        }
        ++imageData.imageCount;
    }

    public void removeSelectedImage(final int n) {
        if (n <= this.selectedImages.size()) {
            final ImageData imageData = this.selectedImages.remove(n);
            --imageData.imageCount;
        }
    }


    public void clearAllSelection() {
        this.videoImages.clear();
        this.allAlbum = null;
        selectedImages.clear();
        this.getSelectedImages().clear();
        System.gc();
        this.getFolderList();
    }

    public void copyAssets() {
        final File file = new File(FileUtils.APP_DIRECTORY, "watermark.png");
        if (!file.exists()) {
            final AssetManager assets = this.getAssets();
            try {
                final InputStream open = assets.open("watermark.png");
                final byte[] array = new byte[1024];
                final FileOutputStream fileOutputStream = new FileOutputStream(file);
                while (true) {
                    final int read = open.read(array, 0, 1024);
                    if (read < 0) {
                        break;
                    }
                    fileOutputStream.write(array, 0, read);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                open.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public HashMap<String, ArrayList<ImageData>> getAllAlbum() {
        return this.allAlbum;
    }

    public ArrayList<String> getAllFolder() {
        Collections.sort(this.allFolder);
        return this.allFolder;
    }

    public String getCurrentTheme() {
        return this.getSharedPreferences("theme", 0).getString("current_theme", THEMES.Shine.toString());
    }

    public void getFolderList() {
        this.allFolder = new ArrayList<String>();
        this.allAlbum = new HashMap<String, ArrayList<ImageData>>();
        String string = null;
        String string2;
        final Cursor query = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "_id", "bucket_display_name", "bucket_id", "datetaken", "_data"}, (String) null, (String[]) null, "_data DESC");
        if (query.moveToFirst()) {
            final int columnIndex = query.getColumnIndex("bucket_display_name");
            final int columnIndex2 = query.getColumnIndex("bucket_id");
            do {
                final ImageData imageData = new ImageData();
                imageData.imagePath = query.getString(query.getColumnIndex("_data"));
                imageData.imageThumbnail = query.getString(query.getColumnIndex("_data"));
                imageData.id = query.getInt(query.getColumnIndex("_id"));
                if (!imageData.imagePath.endsWith(".gif")) {
                    string = query.getString(columnIndex);
                    string2 = query.getString(columnIndex2);
                    if (!this.allFolder.contains(string)) {
                        this.allFolder.add(string);

                    }
                    ArrayList<ImageData> list;
                    if ((list = this.allAlbum.get(string)) == null) {
                        list = new ArrayList<ImageData>();
                    }
                    imageData.folderName = string;
                    list.add(imageData);
                    this.allAlbum.put(string, list);
                }
            } while (query.moveToNext());
        }

    }

    public int getFrame() {
        return this.frame;
    }

    public ArrayList<ImageData> getImageByAlbum(final String s) {
        ArrayList<ImageData> list;
        if ((list = this.getAllAlbum().get(s)) == null) {
            list = new ArrayList<ImageData>();
        }
        return list;
    }

    public MusicData getMusicData() {
        return this.musicData;
    }

    public ArrayList<MusicData> getMusicFiles() {
        final ArrayList<MusicData> list = new ArrayList<MusicData>();
        final Cursor query = this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "title", "_data", "_display_name", "duration"}, "is_music != 0", (String[]) null, "title ASC");
        final int columnIndex = query.getColumnIndex("_id");
        final int columnIndex2 = query.getColumnIndex("title");
        final int columnIndex3 = query.getColumnIndex("_display_name");
        final int columnIndex4 = query.getColumnIndex("_data");
        final int columnIndex5 = query.getColumnIndex("duration");
        while (query.moveToNext()) {
            final String string = query.getString(columnIndex4);
            if (this.isAudioFile(string)) {
                final MusicData musicData = new MusicData();
                musicData.track_Id = query.getLong(columnIndex);
                musicData.track_Title = query.getString(columnIndex2);
                musicData.track_data = string;
                musicData.track_duration = query.getLong(columnIndex5);
                musicData.track_displayName = query.getString(columnIndex3);
                list.add(musicData);
            }
        }
        return list;
    }

    public ArrayList<MusicData> getMusicFiles(final boolean b) {
        final ArrayList<MusicData> list = new ArrayList<MusicData>();
        final Cursor query = this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "title", "_data", "_display_name", "duration"}, "is_music != 0", (String[]) null, "title ASC");
        final int columnIndex = query.getColumnIndex("_id");
        final int columnIndex2 = query.getColumnIndex("title");
        final int columnIndex3 = query.getColumnIndex("_display_name");
        final int columnIndex4 = query.getColumnIndex("_data");
        final int columnIndex5 = query.getColumnIndex("duration");
        while (query.moveToNext()) {
            final String string = query.getString(columnIndex4);
            final StringBuilder sb = new StringBuilder();
            sb.append("GLOB Fatch PAth = ");
            sb.append(string);
            final boolean b2 = false;
            int n = 0;


            if (b) {
                if (this.isAudioFile(string)) {
                    if (this.isAudioFilterPath(string)) {
                        n = 1;
                    }
                }
            } else {
                if (this.isAudioFile(string)) {
                    n = 1;
                }
            }

            if (n == 1) {
                final MusicData musicData = new MusicData();
                musicData.track_Id = query.getLong(columnIndex);
                musicData.track_Title = query.getString(columnIndex2);
                musicData.track_data = string;
                musicData.track_duration = query.getLong(columnIndex5);
                musicData.track_displayName = query.getString(columnIndex3);
                list.add(musicData);
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("Fatch PAth = ");
                sb2.append(musicData.track_data);
            }
        }
        return list;
    }

    public OnProgressReceiver getOnProgressReceiver() {
        return this.onProgressReceiver;
    }

    public float getSecond() {
        return this.second;
    }

    public String getSelectedFolderId() {
        return this.selectedFolderId;
    }

    public ArrayList<ImageData> getSelectedImages() {
        return this.selectedImages;
    }

    public void initArray() {
        this.videoImages = new ArrayList<String>();
    }

    public boolean isAudioFilterPath(final String s) {
        if (this.mAudioDirPath.equals("")) {
            this.mAudioDirPath = Utils.INSTANCE.getAudioFolderPath();
        }
        return s.contains(this.mAudioDirPath);
    }

    public void onCreate() {
        super.onCreate();
        (MyApplication.instance = this).init();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public boolean runApp(final String s, final int n) {
        try {
            final Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            for (final ResolveInfo resolveInfo : this.getPackageManager().queryIntentActivities(intent, 0)) {
                if (resolveInfo.loadLabel(this.getPackageManager()).equals(s)) {
                    final Intent launchIntentForPackage = this.getPackageManager().getLaunchIntentForPackage(resolveInfo.activityInfo.applicationInfo.packageName);
                    if (n != 1) {
                        return launchIntentForPackage != null;
                    }
                    this.startActivity(launchIntentForPackage);
                }
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public void setAutostartAppName() {
        if (Build.MANUFACTURER.equals("asus")) {
            Utils.autostart_app_name = "Auto-start Manager";
            return;
        }
        if (Build.MANUFACTURER.equals("Xiaomi")) {
            Utils.autostart_app_name = "Security";
        }
    }

    public void setCurrentTheme(final String s) {
        final SharedPreferences.Editor edit = this.getSharedPreferences("theme", 0).edit();
        edit.putString("current_theme", s);
        edit.commit();
    }

    public void setFrame(final int frame) {
        this.frame = frame;
    }

    public void setMusicData(final MusicData musicData) {
        this.musicData = musicData;
    }

    public void setOnProgressReceiver(final OnProgressReceiver onProgressReceiver) {
        this.onProgressReceiver = onProgressReceiver;
    }

    public void setSecond(final float second) {
        this.second = second;
    }

    public void setSelectedFolderId(final String selectedFolderId) {
        this.selectedFolderId = selectedFolderId;
    }
}
