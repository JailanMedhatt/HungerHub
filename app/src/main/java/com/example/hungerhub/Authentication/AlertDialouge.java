package com.example.hungerhub.Authentication;

import android.app.AlertDialog;
import android.content.Context;

public class AlertDialouge {
    Context context;
    String title;
    String message;
    String btnString;
    Runnable action;
    public AlertDialouge(Context context, String title, String message, String btnString, Runnable action) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.btnString = btnString;
        this.action = action;
    }
    public  void showAlert(){
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(btnString,((dialog, which) -> {
            if(action!=null){
                action.run();
            }
        }));

   AlertDialog alertDialog= builder.create();
   alertDialog.show();

    }


}
