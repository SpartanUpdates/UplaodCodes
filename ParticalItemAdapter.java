package com.wavymusic.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.unity3d.player.UnityPlayer;
import com.wavymusic.Download.DownloadTask;
import com.wavymusic.Model.ParticalItemModel;
import com.wavymusic.Preferences.LanguagePref;
import com.wavymusic.R;
import com.wavymusic.UnityPlayerActivity;
import com.wavymusic.Utils.Utils;
import com.wavymusic.application.MyApplication;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class ParticalItemAdapter extends RecyclerView.Adapter<ParticalItemAdapter.ParticalIteViewHolder> {

    Context context;
    private String AssetPath;
    public int selectedPosition = -1;

    public ParticalItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ParticalIteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_partical_item, parent, false);
        return new ParticalIteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ParticalIteViewHolder holder, final int position) {
        final ParticalItemModel particalListModel = ((UnityPlayerActivity) this.context).particalListModels.get(position);
//        holder.tvThmeName.setText(particalListModel.getThemeName());
        if (particalListModel.isFromAsset()) {
            holder.ivDownload.setVisibility(View.GONE);
            Glide.with(context).load(Utils.INSTANCE.getAssetUnityPath() + particalListModel.getThumbImage()).placeholder(R.drawable.bg_partical_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ivThumb);
        } else {
            holder.ivDownload.setVisibility(View.VISIBLE);
            if (new File(Utils.INSTANCE.getAssetUnityPath() + particalListModel.getThemeInfo() + ".png").exists()) {
                Glide.with(context).load(Utils.INSTANCE.getAssetUnityPath() + particalListModel.getThemeInfo() + ".png").placeholder(R.drawable.bg_partical_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ivThumb);
            } else {
                Glide.with(context).load(particalListModel.getThumbImage()).placeholder(R.drawable.bg_partical_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ivThumb);
            }
        }
        if (!particalListModel.isAvailableOffline) {
            if (particalListModel.isDownloading) {
                holder.ivDownload.setVisibility(View.GONE);
                holder.tvCounter.setVisibility(View.VISIBLE);
            } else {
                holder.tvCounter.setVisibility(View.GONE);
                holder.ivDownload.setVisibility(View.VISIBLE);
            }
        } else {
            holder.tvCounter.setVisibility(View.GONE);
            holder.ivDownload.setVisibility(View.GONE);
        }

//        if (selectedPosition == position) {
//            holder.llParticalItemMain.setSelected(true);
//        } else {
//            holder.llParticalItemMain.setSelected(false);
//            if (MyApplication.ParticalCatid == particalListModel.getCatId() && MyApplication.ThemeAssetSelectedPosition == particalListModel.getThemeId()) {
//                holder.llParticalItemMain.setSelected(true);
//            }
//        }

        if (MyApplication.ParticalSelectedCatid == particalListModel.getCatId() && selectedPosition == position) {
            holder.llParticalItemMain.setSelected(true);
            Log.e("TAG", "Selected" + selectedPosition);
        } else {
            holder.llParticalItemMain.setSelected(false);
//            Log.e("TAG", "else Selected" + selectedPosition);
            if (MyApplication.ParticalCatid == particalListModel.getCatId() && MyApplication.ThemeAssetSelectedPosition == particalListModel.getThemeId()) {
                holder.llParticalItemMain.setSelected(true);
                Log.e("TAG", "IsAssetSelected" + particalListModel.getCatId());
            }
        }

        File BundelPath = new File(Utils.INSTANCE.getAssetUnityPath() + File.separator + particalListModel.getBundelName());
        int BundelFileSize = Integer.parseInt(String.valueOf(BundelPath.length()));
        Log.e("TAG", "BundelSize_1.6" + particalListModel.getBundelName() + "_" + BundelFileSize);

        holder.llParticalItemMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (particalListModel.isFromAsset()) {
                    DownloadThemeFiles(position, holder.ivDownload, holder.tvCounter, particalListModel);
                } else {
                    int UnityBundelSize = particalListModel.getBundelSize();
                    File BundelPath = new File(Utils.INSTANCE.getAssetUnityPath() + File.separator + particalListModel.getBundelName());
                    int BundelFileSize = Integer.parseInt(String.valueOf(BundelPath.length()));
                    Log.e("TAG", "BundelPath" + BundelPath);
                    Log.e("TAG", "SoundFie" + BundelFileSize);
                    if (new File(Utils.INSTANCE.getAssetUnityPath() + particalListModel.getBundelName()).exists()) {
                        if (BundelFileSize == UnityBundelSize) {
                            AssetPath = particalListModel.getThemeUnity3dPath() + MyApplication.SPLIT_PATTERN + particalListModel.getThemeInfo();
                            UnityPlayer.UnitySendMessage("CategoryListGenerater", "LoadParticle", AssetPath);
                            selectedPosition = position;
                            MyApplication.ThemeAssetSelectedPosition = -1;
                            MyApplication.ParticalSelectedCatid = particalListModel.getCatId();
                            Log.e("TAG", "BundelExists"+BundelFileSize);
                            notifyDataSetChanged();
                        } else {
                            Log.e("TAG", "Bundel Not Exists ReDownload");
                            if (particalListModel.getIsPreimum().equals("1")) {
                                ShowAdsDialog(context, position, particalListModel.getThemeName(), holder.ivDownload, holder.tvCounter, particalListModel);
                            } else {
                                DownloadThemeFiles(position, holder.ivDownload, holder.tvCounter, particalListModel);
                            }
                        }
                    } else {
                        Log.e("TAG", "Bundel Download");
                        if (particalListModel.getIsPreimum().equals("1")) {
                            ShowAdsDialog(context, position, particalListModel.getThemeName(), holder.ivDownload, holder.tvCounter, particalListModel);
                        } else {
                            DownloadThemeFiles(position, holder.ivDownload, holder.tvCounter, particalListModel);
                        }
                    }
                }
            }
        });
    }

    private void DownloadThemeFiles(int position, ImageView ivDownload, TextView tvThemeDownprogress, ParticalItemModel particalModel) {
        int UnityBundelSize = particalModel.getBundelSize();
        File BundelPath = new File(Utils.INSTANCE.getAssetUnityPath() + File.separator + particalModel.getBundelName());
        int BundelFileSize = Integer.parseInt(String.valueOf(BundelPath.length()));
        AssetPath = particalModel.getThemeUnity3dPath() + MyApplication.SPLIT_PATTERN + particalModel.getThemeInfo();
        if (new File(Utils.INSTANCE.getAssetUnityPath() + particalModel.getBundelName()).exists()) {
            if (BundelFileSize == UnityBundelSize) {
                Log.e("TAG", "ParticalPath" + AssetPath);
                UnityPlayer.UnitySendMessage("CategoryListGenerater", "LoadParticle", AssetPath);
                selectedPosition = position;
                MyApplication.ThemeAssetSelectedPosition = -1;
                MyApplication.ParticalSelectedCatid = particalModel.getCatId();
                notifyDataSetChanged();
            } else {
                if (!particalModel.isFromAsset()) {
                    if (Utils.checkConnectivity(context, true)) {
                        Log.e("TAG", "ReDownload");
                        ivDownload.setVisibility(View.GONE);
                        tvThemeDownprogress.setVisibility(View.VISIBLE);
                        new DownloadTask(context, ivDownload, tvThemeDownprogress, particalModel);
                    } else {
                        Toast.makeText(context, "No Internet Connecation!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e("TAG", "ReCopyAsset" );
                    ivDownload.setVisibility(View.GONE);
                    Utils.INSTANCE.CopyAssets(context);
                }
            }

        } else {
            if (!particalModel.isFromAsset()) {
                if (Utils.checkConnectivity(context, true)) {
                    ivDownload.setVisibility(View.GONE);
                    tvThemeDownprogress.setVisibility(View.VISIBLE);
                    new DownloadTask(context, ivDownload, tvThemeDownprogress, particalModel);
                } else {
                    Toast.makeText(context, "No Internet Connecation!", Toast.LENGTH_LONG).show();
                }
            } else {
                Log.e("TAG", "not connnec ReCopyAsset" );
                ivDownload.setVisibility(View.GONE);
                Utils.INSTANCE.CopyAssets(context);
            }
        }
    }

    private void ShowAdsDialog(final Context context, final int position, final String BundelName, final ImageView ivDownoad, final TextView tvCounter, final ParticalItemModel particalModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AdsDialog);
        builder.setCancelable(true);
        View inflate = LayoutInflater.from(context).inflate(R.layout.part_ad_dialog, null);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        Window window = create.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 80;
        attributes.flags &= -3;
        window.setAttributes(attributes);
        ((TextView) inflate.findViewById(R.id.themeName)).setText(particalModel.getThemeName());
        Button btnWatchVideo = inflate.findViewById(R.id.btnWatchVideo);
        Button btnCancel = inflate.findViewById(R.id.btnCancle);

        btnWatchVideo.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                if (((UnityPlayerActivity) context).j != -1) {
                    DownloadThemeFiles(position, ivDownoad, tvCounter, particalModel);
                }
                ((UnityPlayerActivity) context).j = position;
                ((UnityPlayerActivity) context).particalListModels.get(position).j = true;
                String bundelAdsWatch = LanguagePref.a(context).a("pref_key_watch_bundle_ads", "");
                if (bundelAdsWatch.equals("")) {
                    bundelAdsWatch = BundelName;
                } else {
//                    final StringBuilder sb = new StringBuilder();
//                    sb.append(a);
//                    sb.append("?");
//                    sb.append(BundelName);
//                    a = sb.toString();
                    bundelAdsWatch = AssetPath;
                }
                Log.e("AdpdownString", bundelAdsWatch);

                LanguagePref.a(context).b("pref_key_watch_bundle_ads", bundelAdsWatch);
                create.dismiss();
                final UnityPlayerActivity c = UnityPlayerActivity.unityPlayeractivity;
//                final String b = ss;
//                final int a2 = position;
//                final String d2 = AllFilePath;
                c.p = BundelName;
                c.r = position;
                c.q = AssetPath;
                try {
                    if (c.mRewardedVideoAd != null) {
                        if (c.mRewardedVideoAd.isLoaded()) {
                            selectedPosition = position;
                            c.mRewardedVideoAd.show();
                        } else {
                            if (Utils.checkConnectivity(context, false)) {
                                c.RewardedVideoAdsWatchPref();
                                c.loadRewardedAds();
                            } else {
                                Toast.makeText(context, "Data/Wifi Not Available", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        c.loadRewardedVideo();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                create.dismiss();
            }
        });
        create.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                create.dismiss();
            }
        });
        create.show();
    }

    @Override
    public int getItemCount() {
        return ((UnityPlayerActivity) this.context).particalListModels.size();
    }

    public class ParticalIteViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivThumb;
        private ImageView ivDownload;
        private TextView tvCounter;
        //        private TextView tvThmeName;
        LinearLayout llParticalItemMain;

        public ParticalIteViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.ivThumb);
            ivDownload = itemView.findViewById(R.id.ivDownload);
            tvCounter = itemView.findViewById(R.id.tvCounter);
//            tvThmeName = itemView.findViewById(R.id.tvName);
            llParticalItemMain = itemView.findViewById(R.id.ll_partical_item_main);
        }
    }
}
