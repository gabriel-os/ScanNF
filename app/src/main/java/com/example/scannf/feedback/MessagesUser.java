package com.example.scannf.feedback;

import android.content.Context;
import android.widget.Toast;

public class MessagesUser {
    private Context context;


    public MessagesUser(Context context) {
        this.context = context;
    }

    public void showToast(String message, int duration) {
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
