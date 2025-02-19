package com.example.hungerhub.Authentication;

import android.app.AlertDialog;
import android.content.Context;

public class AlertDialouge {
    Context context;
    String title;
    String message;
    String btnString;
    String negBtnString;
    Runnable action;
    public AlertDialouge(Context context, String title, String message, String btnString, Runnable action,String negBtnString) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.btnString = btnString;
        this.action = action;
        this.negBtnString=negBtnString;
    }
    public  void showAlert(){
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(btnString,((dialog, which) -> {
            if(action!=null){
                action.run();
            }
        })).setNegativeButton(negBtnString,(((dialog, which) -> {
            dialog.dismiss();
        })));

   AlertDialog alertDialog= builder.create();
   alertDialog.show();

    }


}
