package com.example.hungerhub.Authentication.interfaces;

import android.view.View;

public interface OnResponseHandler {
    public void onSuccessResponse(View view);
    public void onFailureResponse(String message);
}
