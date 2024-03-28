package com.example.studentteacherappointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast extends AppCompatActivity
{
    private ImageView iv_icon;
    private TextView tv_message, tv_status;

   public void myToast(Context context, int imgRID, String message, String status)
   {
       View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_toast, null);

       iv_icon = view.findViewById(R.id.ivIconToast);
       tv_message = view.findViewById(R.id.tvMessageToast);
       tv_status = view.findViewById(R.id.tvStatusToast);

       iv_icon.setImageResource(imgRID);
       tv_message.setText(message);
       tv_status.setText(status);

       Toast toast = new Toast(context);
       toast.setView(view);
       toast.setDuration(Toast.LENGTH_LONG);
       toast.setGravity(Gravity.CENTER, 0, 0);
       toast.show();

   }


}