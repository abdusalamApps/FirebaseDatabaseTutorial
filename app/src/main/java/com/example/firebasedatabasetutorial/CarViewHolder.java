package com.example.firebasedatabasetutorial;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarViewHolder extends RecyclerView.ViewHolder {

    private TextView mModelTV, mColorTV, mTopSpeedTV, mViewsTV;

    public CarViewHolder(@NonNull View itemView) {
        super(itemView);

        mModelTV = itemView.findViewById(R.id.listItemModel_tv);
        mColorTV = itemView.findViewById(R.id.listItemColor_tv);
        mTopSpeedTV = itemView.findViewById(R.id.listItemSpeed_tv);
        mViewsTV = itemView.findViewById(R.id.listItemViews_tv);

    }

    public void setModel(String model) {
        mModelTV.setText(model);
    }

    public void setColor(String color) {
        mColorTV.setText(color);
    }

    public void setSpeed(int speed) {
        mTopSpeedTV.setText(String.format("%s kmh",String.valueOf(speed)));
    }

    public void setViews(long views) {
        mViewsTV.setText(String.format("%s Views", String.valueOf(views)));
    }
}
