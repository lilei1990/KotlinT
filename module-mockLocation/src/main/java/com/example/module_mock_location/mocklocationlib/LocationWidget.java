package com.example.module_mock_location.mocklocationlib;

import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.module_mock_location.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * LocationWigdet:模拟位置信息提示控件
 */
public class LocationWidget extends DialogFragment {
    MockLocationManager mockLocationManager;
    private Context context;
    private TextView tvProvider = null;
    private TextView tvTime = null;
    private TextView tvLatitude = null;
    private TextView tvLongitude = null;
    private TextView tvSystemMockPositionStatus = null;
    private Button btnStartMock = null;
    private Button btnStopMock = null;
    private ImageView locationWigdetTipIv;
    private LinearLayout locationWigdetDataLl;
    private static LocationWidget sFragment;


    private LocationWidget(Context context) {
        this.context = context;
    }


    public static LocationWidget newInstance(Context context) {
        if (sFragment == null) {
            sFragment = new LocationWidget(context);
        }
        Bundle bundle = new Bundle();
        bundle.putString("tittle", "标题");
        sFragment.setArguments(bundle);
        return sFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.location_wiget_layout, container, true);
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View layout) {
        tvProvider = (TextView) layout.findViewById(R.id.tv_provider);
        tvTime = (TextView) layout.findViewById(R.id.tv_time);
        tvLatitude = (TextView) layout.findViewById(R.id.tv_latitude);
        tvLongitude = (TextView) layout.findViewById(R.id.tv_longitude);
        tvSystemMockPositionStatus = (TextView) layout.findViewById(R.id.tv_system_mock_position_status);
        locationWigdetTipIv = (ImageView) layout.findViewById(R.id.location_wigdet_tip_iv);
        locationWigdetDataLl = (LinearLayout) layout.findViewById(R.id.location_wigdet_data_ll);

        btnStartMock = (Button) layout.findViewById(R.id.btn_start_mock);
        btnStopMock = (Button) layout.findViewById(R.id.btn_stop_mock);

        mockLocationManager = new MockLocationManager();
        btnStartMock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMock();
            }
        });
        btnStopMock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopMock();
            }
        });
        mockLocationManager.initService(context);

        mockLocationManager.startThread();

    }

    /**
     * 开始模拟定位
     */
    public void stopMock() {
        stopMockLocation();
        btnStartMock.setEnabled(true);
        btnStopMock.setEnabled(false);
    }

    /**
     * 停止模拟定位
     */
    public void startMock() {
        if (mockLocationManager.getUseMockPosition(context)) {
            startMockLocation();
            btnStartMock.setEnabled(false);
            btnStopMock.setEnabled(true);
            refreshData();
        } else {
            ToastUtils.showLong("模拟位置不可用");
        }
    }

    public void refreshData() {
        // 判断系统是否允许模拟位置，并addTestProvider
        if (mockLocationManager.getUseMockPosition(context)) {
            if (mockLocationManager.bRun) {
                btnStartMock.setEnabled(false);
                btnStopMock.setEnabled(true);
            } else {
                btnStartMock.setEnabled(true);
                btnStopMock.setEnabled(false);
            }
            tvSystemMockPositionStatus.setText("已开启");
            locationWigdetTipIv.setVisibility(View.GONE);
            locationWigdetDataLl.setVisibility(View.VISIBLE);
        } else {
            mockLocationManager.bRun = false;
            btnStartMock.setEnabled(false);
            btnStopMock.setEnabled(false);
            tvSystemMockPositionStatus.setText("未开启");
            locationWigdetTipIv.setVisibility(View.VISIBLE);
            locationWigdetDataLl.setVisibility(View.GONE);
        }
        mockLocationManager.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    public void removeUpdates() {
        mockLocationManager.locationManager.removeUpdates(locationListener);
    }

    public void stopMockLocation() {
        mockLocationManager.bRun = false;
        mockLocationManager.stopMockLocation();
    }

    public void startMockLocation() {
        mockLocationManager.bRun = true;
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            setLocationData(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    /**
     * 获取到模拟定位信息，并显示
     *
     * @param location 定位信息
     */
    private void setLocationData(Location location) {
        tvProvider.setText(location.getProvider());
        tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(location.getTime())));
        tvLatitude.setText(location.getLatitude() + " °");
        tvLongitude.setText(location.getLongitude() + " °");
    }

    public void setMangerLocationData(double lat, double lon) {
        mockLocationManager.setLocationData(lat, lon);
    }
}