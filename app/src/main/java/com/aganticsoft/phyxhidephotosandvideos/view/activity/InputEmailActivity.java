package com.aganticsoft.phyxhidephotosandvideos.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.aganticsoft.phyxhidephotosandvideos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputEmailActivity extends BaseActivity {

    @BindView(R.id.btnSkip)
    Button btnSkip;

    @BindView(R.id.btnDone)
    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_email);
        ButterKnife.bind(this);

        btnSkip.setOnClickListener( v -> {
            Intent intent = LockActivity.getIntent(this, LockActivity.TYPE_CREATE_PASSWORD);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        btnDone.setOnClickListener( v -> {
            Intent intent = LockActivity.getIntent(this, LockActivity.TYPE_CREATE_PASSWORD);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}
