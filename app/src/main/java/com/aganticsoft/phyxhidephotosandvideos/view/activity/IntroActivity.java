package com.aganticsoft.phyxhidephotosandvideos.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;

import com.aganticsoft.phyxhidephotosandvideos.PhyxApp;
import com.aganticsoft.phyxhidephotosandvideos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroActivity extends BaseActivity{

    private static final int REQUEST_WRITE_EXTERNAL = 0x88;

    @BindView(R.id.btnGrant)
    Button btnGrant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int writePermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (writePermissionCheck == PackageManager.PERMISSION_GRANTED) {
            // go to email activity
            PhyxApp.getInstance().initDirectory();

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_intro);
            ButterKnife.bind(this);

            btnGrant.setOnClickListener( v -> {
                ActivityCompat.requestPermissions(this
                        , new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL);
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL:
                PhyxApp.getInstance().initDirectory();

                Intent intent = new Intent(this, InputEmailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
