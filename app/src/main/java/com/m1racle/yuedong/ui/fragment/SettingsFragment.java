package com.m1racle.yuedong.ui.fragment;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m1racle.yuedong.AppConfig;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.AppManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseApplication;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.ui.DialogUtil;
import com.m1racle.yuedong.ui.widget.togglebutton.ToggleButton;
import com.m1racle.yuedong.util.FileUtil;
import com.m1racle.yuedong.util.UIUtil;

import org.kymjs.kjframe.bitmap.BitmapConfig;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 系统设置界面
 */
public class SettingsFragment extends BaseFragment {

    //@Bind(R.id.tb_loading_img)
    ToggleButton mTbLoadImg;
    @Bind(R.id.tv_cache_size)
    TextView mTvCacheSize;
    @Bind(R.id.setting_logout)
    TextView mTvExit;

    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container,
                false);
        mTbLoadImg = (ToggleButton)view.findViewById(R.id.tb_loading_img);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
        mTbLoadImg.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                //AppContext.setLoadImage(on);
            }
        });


        view.findViewById(R.id.rl_loading_img).setOnClickListener(this);
        view.findViewById(R.id.rl_notification_settings).setOnClickListener(
                this);
        view.findViewById(R.id.rl_clean_cache).setOnClickListener(this);
        view.findViewById(R.id.rl_about).setOnClickListener(this);
        view.findViewById(R.id.rl_exit).setOnClickListener(this);

        //if (!AppContext.getContext().isLogin()) {
        //    mTvExit.setText("退出");
        //}
    }

    @Override
    public void initData() {
        if (AppContext.get(AppConfig.KEY_LOAD_IMAGE, true)) {
            mTbLoadImg.setToggleOn();
        } else {
            mTbLoadImg.setToggleOff();
        }

        calculateCacheSize();
    }

    /**
     * 计算缓存的大小
     */
    private void calculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = getActivity().getFilesDir();
        File cacheDir = getActivity().getCacheDir();

        fileSize += FileUtil.getDirSize(filesDir);
        fileSize += FileUtil.getDirSize(cacheDir);
        File externalCacheDir = getActivity().getExternalCacheDir();
        fileSize += FileUtil.getDirSize(externalCacheDir);
        fileSize += FileUtil.getDirSize(new File(
                    org.kymjs.kjframe.utils.FileUtils.getSDCardPath()
                            + File.separator + BitmapConfig.CACHEPATH));
        
        if (fileSize > 0)
            cacheSize = FileUtil.formatFileSize(fileSize);
        mTvCacheSize.setText(cacheSize);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
        case R.id.rl_loading_img:
            mTbLoadImg.toggle();
            break;
        case R.id.rl_notification_settings:
            UIUtil.showSettingNotification(getActivity());
            break;
        case R.id.rl_clean_cache:
            onClickCleanCache();
            break;
        case R.id.rl_about:
                UIUtil.showAboutYD(getActivity());
            break;
        case R.id.rl_exit:
            onClickExit();
            break;
        default:
            break;
        }

    }

    private void onClickCleanCache() {
        DialogUtil.getConfirmDialog(getActivity(), "是否清空缓存?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UIUtil.clearAppCache(getActivity());
                mTvCacheSize.setText("0 KB");
            }
        }).show();
    }

    private void onClickExit() {
        AppContext
                .set(AppConfig.KEY_NOTIFICATION_DISABLE_WHEN_EXIT,
                        false);
        AppManager.getAppManager().AppExit(getActivity());
        getActivity().finish();
    }


}
