package com.example.whyko.labproject;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.whyko.labproject.Constants.REQUEST_IMEI;

public class MainActivity extends AppCompatActivity {
    private TextView ver;
    private TextView IMEI;
    private Button btn;
    private CharSequence imeiText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ver = (TextView) findViewById(R.id.version_textview);
        IMEI = (TextView) findViewById(R.id.imei_textview);
        btn = (Button) findViewById(R.id.get_permission_button);

        View.OnClickListener getPermission = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        };
        btn.setOnClickListener(getPermission);

        getVersionInfo();
        if (savedInstanceState != null){
            imeiText = savedInstanceState.getCharSequence("IMEI");
            if (imeiText != null) {
                IMEI.setText(imeiText);
            }
        }
        else {
            getIMEI();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outstate) {
        outstate.putCharSequence("IMEI", IMEI.getText());
        super.onSaveInstanceState(outstate);
    }

    private void getVersionInfo() {
        String versionName = BuildConfig.VERSION_NAME;
        ver.setText(versionName);
    }

    private void getIMEI() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.imei_dialog_title)
                        .setMessage(R.string.imei_dialog_message)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                requestPermission();
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .create()
                        .show();
            } else {
                requestPermission();
            }

        } else {
            String IMEINumber;
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            IMEINumber = telephonyManager.getDeviceId();
            IMEI.setText(IMEINumber);
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_PHONE_STATE},
                REQUEST_IMEI);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_IMEI: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getIMEI();
                }
            }
        }
    }

}
