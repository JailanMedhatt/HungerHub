package com.example.hungerhub.Authentication.interfaces;

import android.view.View;

public interface OnResponseHandler {
    public void onSuccessResponse(View view,String uid);
    public void onFailureResponse(String message);
}
