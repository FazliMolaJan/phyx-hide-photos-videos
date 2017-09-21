package com.aganticsoft.phyxhidephotosandvideos.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aganticsoft.phyxhidephotosandvideos.R;
import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LockActivity extends AppCompatActivity {

    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.pin_lock_view)
    PinLockView pinLockView;
    @BindView(R.id.indicator_dots)
    IndicatorDots indicatorDots;

    public static final int TYPE_CREATE_PASSWORD = 1;
    public static final int TYPE_REQUIRE_PASSWORD = 2;


    /**
     * @param type one of these type: TYPE_REQUIRE_PASSWORD, TYPE_CREATE_PASSWORD
     */
    public static Intent getIntent(Context context, int type) {
        Intent intent = new Intent(context, LockActivity.class);
        intent.putExtra("type", type);

        return intent;
    }

    int type = 0;
    boolean retypePassword = false;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        ButterKnife.bind(this);

        type = getIntent().getIntExtra("type", TYPE_REQUIRE_PASSWORD);

        if (type == TYPE_REQUIRE_PASSWORD) {
            tvDescription.setVisibility(View.GONE);
        } else {
            tvDescription.setVisibility(View.VISIBLE);
        }


        pinLockView.attachIndicatorDots(indicatorDots);
        pinLockView.setPinLockListener(new PinLockListener() {

            @Override
            public void onComplete(String pin) {
                if (type == TYPE_CREATE_PASSWORD) {
                    if (retypePassword) {
                        Intent intent = new Intent(LockActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        retypePassword = true;
                        pinLockView.resetPinLockView();
                    }
                } else { // require password

                }
            }

            @Override
            public void onEmpty() {

            }

            @Override
            public void onPinChange(int pinLength, String intermediatePin) {

            }
        });
    }
}
