package com.example.part4_10;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DriveHolder {
    public ImageView typeImageview;
    public TextView titleView;
    public TextView dataView;
    public ImageView menuImageView;

    public DriveHolder(View root)
    {
        typeImageview = root.findViewById(R.id.custom_item_type_image);
        titleView = root.findViewById(R.id.custom_item_title);
        dataView = root.findViewById(R.id.custom_item_date);
        menuImageView = root.findViewById(R.id.custom_item_menu);
    }
}
