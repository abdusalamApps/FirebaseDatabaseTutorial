package com.example.firebasedatabasetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CarDetailsActivity extends AppCompatActivity {

    private TextView mModelTV, mColorTV, mDescriptionTV, mSpeedTV, mViewsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        findViews();

        if (getIntent() != null) {
            mModelTV.setText(getIntent().getStringExtra("model"));
            mColorTV.setText(getIntent().getStringExtra("color"));
            mDescriptionTV.setText(getIntent().getStringExtra("description"));
            mSpeedTV.setText(getIntent().getStringExtra("speed"));
            mViewsTV.setText(getIntent().getStringExtra("views"));

        }
    }

    private void findViews() {
        mModelTV = findViewById(R.id.carDetailsModel_tv);
        mColorTV = findViewById(R.id.carDetailsColor_tv);
        mDescriptionTV = findViewById(R.id.carDetailsDescription_tv);
        mSpeedTV = findViewById(R.id.carDetailsSpeed_tv);
        mViewsTV = findViewById(R.id.carDetailsViews_tv);
    }
}
