package com.example.hungerhub.Authentication.login.view;

public interface Loginiview {
    public void onEmailError(String msg);
    public void onPassError();
    public void onSuccessResponse();
    public  void onFailureResponse(String msg);
}
