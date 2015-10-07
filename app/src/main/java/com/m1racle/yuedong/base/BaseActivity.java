package com.m1racle.yuedong.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.AppManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.ui.DialogUtil;

import org.kymjs.kjframe.utils.StringUtils;

import butterknife.ButterKnife;

/**
 * The Base Activity of the Yuedong App
 * all activities should extend this Activity
 * @author sczyh30
 * @since 0.1
 */
public abstract class BaseActivity extends AppCompatActivity
    implements BaseDialogControl,View.OnClickListener {

    private ProgressDialog waitDialog;
    private boolean isVisible;

    protected LayoutInflater mInflater;
    protected ActionBar mActionBar;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the present theme
        if (AppContext.getNightModeSwitch()) {
            setTheme(R.style.AppBaseTheme_Night);
        } else {
            setTheme(R.style.AppBaseTheme_Light);
        }
        // add this Activity to the AppManager
        AppManager.getAppManager().addActivity(this);
        // set the content layout
        onBeforeSetContentLayout();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        // get the components
        mActionBar = getSupportActionBar();
        mInflater = getLayoutInflater();
        // initialize the ActionBar
        if (hasActionBar()) {
            initActionBar(mActionBar);
        }
        // inject the widgets by annotation
        ButterKnife.bind(this);
        // initialize the activity
        init(savedInstanceState);

    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        return super.onMenuOpened(featureId, menu);
    }
    /**
     * Get the ActionBar title
     * @return the resource id of the app name by default
     */
    protected int getActionBarTitle() {
        return R.string.app_name;
    }

    protected boolean hasBackButton() {
        return false;
    }

    protected int getLayoutId() {
        return 0;
    }

    /**
     * The method to init the ActionBar
     * @param actionBar ActionBar instance
     */
    protected void initActionBar(ActionBar actionBar) {
        if (actionBar == null)
            return;
        if (hasBackButton()) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        } else {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
            actionBar.setDisplayUseLogoEnabled(false);
            int titleRes = getActionBarTitle();
            if (titleRes != 0) {
                actionBar.setTitle(titleRes);
            }
        }
    }

    /**
     * The method to init the entire activity
     * needs to be implemented by sub-activities
     * @param savedInstanceState the bundle
     */
    protected void init(Bundle savedInstanceState) {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * Set the content layout on before
     */
    protected void onBeforeSetContentLayout() {}

    /**
     * default true
     * @return if the activity has the ActionBar
     */
    protected boolean hasActionBar() {
        return true;
    }

    /**
     * Set the ActionBar title by resource id
     * @param resId resource id of the title
     */
    public void setActionBarTitle(int resId) {
        if (resId != 0) {
            setActionBarTitle(getString(resId));
        }
    }

    /**
     * Set the ActionBar title by string
     * @param title title string
     */
    public void setActionBarTitle(String title) {
        if (StringUtils.isEmpty(title)) {
            title = getString(R.string.app_name);
        }
        if (hasActionBar() && mActionBar != null) {
                mActionBar.setTitle(title);
        }
    }

    @Override
    public ProgressDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    @Override
    public ProgressDialog showWaitDialog(int resId) {
        return showWaitDialog(getString(resId));
    }

    @Override
    public ProgressDialog showWaitDialog(String message) {
        if (isVisible) {
            if (dialog == null) {
                dialog = DialogUtil.getWaitDialog(this, message);
            }
            if (dialog != null) {
                dialog.setMessage(message);
                dialog.show();
            }
            return dialog;
        }
        return null;
    }

    @Override
    public void hideWaitDialog() {
        if (isVisible && dialog != null) {
            try {
                dialog.dismiss();
                dialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
