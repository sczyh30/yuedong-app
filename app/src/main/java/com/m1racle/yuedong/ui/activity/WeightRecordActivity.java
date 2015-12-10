package com.m1racle.yuedong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseActivity;
import com.m1racle.yuedong.dao.WeightDaoImpl;
import com.m1racle.yuedong.entity.Weight;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;

import butterknife.Bind;

public class WeightRecordActivity extends BaseActivity {

    @Bind(R.id.wr_datePicker)
    DatePicker datePicker;
    @Bind(R.id.et_weight_rec_we)
    EditText mEtWeight;
    @Bind(R.id.et_weight_rec_he)
    EditText mEtHeight;
    @Bind(R.id.et_weight_rec_tip)
    EditText mEtTip;

    WeightDaoImpl weightDao = new WeightDaoImpl();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weight_record;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    /**
     * Validate the data of the EditText
     * @return true if it is valid
     */
    private boolean validate() {
        if(mEtWeight.length() == 0) {
            mEtWeight.setError("请输入您的体重");
            mEtWeight.requestFocus();
            return false;
        } else if(mEtHeight.length() == 0) {
            mEtHeight.setError("请输入您的身高");
            mEtHeight.requestFocus();
            return false;
        }
        return true;
    }

    private boolean saveData() {
        Weight weight = new Weight();
        float h = Float.parseFloat(mEtHeight.getText().toString());
        float w = Float.parseFloat(mEtWeight.getText().toString());
        weight.setHeight(h);
        weight.setWeight(w);
        LogUtil.log("BMI -> " + getBMI(h, w));
        weight.setIndex(getBMI(h, w));
        weight.setTip(mEtTip.getText().toString());
        //TODO:加入星期几的表述
        String time = "" + datePicker.getYear() + "年" + datePicker.getMonth() + "月" + datePicker.getDayOfMonth() + "日";
        weight.setwTime(time);
        return weightDao.save(weight);
    }

    /**
     * Get the BMI index
     * @param h height, cm
     * @param w weight, kg
     * @return the bmi index
     */
    private float getBMI(float h, float w) {
        return w / ((h / 100)*(h / 100));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_finish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.public_menu_send) {
            if(validate()) {
                if(saveData()) {
                    ToastUtil.toast(R.string.weight_insert_record_ok);
                    Intent intent = new Intent("com.m1racle.yuedong.action.ON_WEIGHT_PRESENT_CHANGE");
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    finish();
                } else
                    ToastUtil.toast(R.string.error_RDI006);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
