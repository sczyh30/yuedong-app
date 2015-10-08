package com.m1racle.yuedong.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.TimeZoneUtil;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Sensor Data View Fragment
 * provides the temperature data
 * @author sczyh30
 */
public class TemperatureViewFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;
    private SensorManager mSensorManager;
    private SensorEventListener sensorEventListener;

    private final static int HANDLE_TIME = 0;
    private final static int HANDLE_DATA = 1;

    @Bind(R.id.fn_temp_data_text)
    TextView mEtTempData;
    @Bind(R.id.fn_temp_time_text)
    TextView mEtTime;
    @Bind(R.id.fn_temp_status_text)
    TextView mEtTempStatus;

    public TemperatureViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature_view, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                int sensorType = event.sensor.getType();
                if(sensorType == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                    int data = Math.round(values[0]);
                    getTempData(data);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(sensorEventListener);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View v) {

    }

    private final class MyHandler extends Handler {
        private final WeakReference<TemperatureViewFragment> mFragment;

        private MyHandler(TemperatureViewFragment mFragment) {
            this.mFragment = new WeakReference<>(mFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {
                case HANDLE_TIME: //Time
                    mFragment.get().mEtTime.setText((String)object);
                    break;
                case HANDLE_DATA: //Data
                    mFragment.get().mEtTempData.setText((String)object);
                    mFragment.get().mEtTempStatus.setText(inspectTempData(msg.arg1));
                    getTempTimestamp();
                    break;
                default:
                    break;
            }
        }
    }

    private final MyHandler mHandler = new MyHandler(this);

    /**
     * A method to inspect the temperature level
     * uses a very simple standard
     * only for study
     * @param temp temperature
     * @return the level
     */
    private String inspectTempData(int temp) {
        if(temp >= 19 && temp <= 23)
            return "良好";
        else if (temp > 23 && temp <= 28) {
            return "优良";
        }
        else if (temp > 28 && temp <= 32) {
            return "偏热";
        }
        else if (temp > 32) {
            return "炎热";
        }
        else
            return "寒冷";
    }

    /**
     * This method get the data of the temperature
     * handle the data by handler
     */
    private void getTempData(int data) {
        String temperature = "" + data + " ℃";
        Message message = Message.obtain();
        message.what = HANDLE_DATA;
        message.obj = temperature;
        message.arg1 = data;
        mHandler.sendMessage(message);
        //LogUtil.log("GET => Temperature Data : fucking get :" + data);
    }

    /**
     * This method get the time for the temperature
     * handle the time by handler
     */
    private void getTempTimestamp() {
        Message message = Message.obtain();
        message.what = HANDLE_TIME;
        message.obj = TimeZoneUtil.getCurrentTime();
        mHandler.sendMessage(message);
    }
}
