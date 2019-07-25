package com.example.firebasedatabasetutorial;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarViewHolder extends RecyclerView.ViewHolder {

    private TextView mModelTV, mColorTV, mTopSpeedTV;

    public CarViewHolder(@NonNull View itemView) {
        super(itemView);

        mModelTV = itemView.findViewById(R.id.listItemModel_tv);
        mColorTV = itemView.findViewById(R.id.listItemColor_tv);
        mTopSpeedTV = itemView.findViewById(R.id.listItemSpeed_tv);

    }

    public void setModel(String model) {
        mModelTV.setText(model);
    }

    public void setColor(String color) {
        mColorTV.setText(color);
    }

    public void setSpeed(long speed) {
        mTopSpeedTV.setText(String.valueOf(speed));
    }
}
