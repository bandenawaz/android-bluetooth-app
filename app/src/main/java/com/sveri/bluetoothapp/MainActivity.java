package com.sveri.bluetoothapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnOn, btnOff, btnDisc;
    TextView tvRes;

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 0;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRes = findViewById(R.id.textViewRes);
        btnOn = findViewById(R.id.buttonDeviceOn);
        btnDisc = findViewById(R.id.buttonDiscoverable);
        btnOff = findViewById(R.id.buttonDeviceOff);

        btnOn.setOnClickListener(this);
        btnOff.setOnClickListener(this);
        btnDisc.setOnClickListener(this);

        //lets check whether device ssupports bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {

            tvRes.append("Device not supported");
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonDeviceOn) {

            if (!bluetoothAdapter.isEnabled()) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestBluetoothPermission(this,REQUEST_ENABLE_BT);
                    return;
                }else{
                    startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);

                }
            }
        }

        if (v.getId() == R.id.buttonDiscoverable){

            if (!bluetoothAdapter.isDiscovering()){

                final String permission = Manifest.permission.BLUETOOTH_SCAN;
                if(ContextCompat.checkSelfPermission(this,permission
                        ) != PackageManager.PERMISSION_GRANTED){

                    requestBluetoothPermission(this,REQUEST_DISCOVERABLE_BT);
                    }
                }else{
                    Toast.makeText(this, "Making your device discoverable",
                            Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE),
                            REQUEST_DISCOVERABLE_BT);
                }



            }




        if (v.getId() == R.id.buttonDeviceOff){

            bluetoothAdapter.disable();
            tvRes.setText("Turing off your bluetooth");
        }

    }

    public static void requestBluetoothPermission(
            @NonNull final Activity requestingActivity,
            final int requestCode) {
        final String[] permissions = {Manifest.permission.BLUETOOTH_CONNECT,Manifest.permission.BLUETOOTH_SCAN};
        if (ContextCompat.checkSelfPermission(requestingActivity,
                permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    requestingActivity, permissions[0])) {
                ActivityCompat.requestPermissions(requestingActivity,
                        new String[] { permissions[0] }, requestCode);
            }
        }else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    requestingActivity, permissions[1])) {
                ActivityCompat.requestPermissions(requestingActivity,
                        new String[] { permissions[1] }, requestCode);
            }
        }
    }
}