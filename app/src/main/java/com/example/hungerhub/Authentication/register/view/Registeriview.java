package com.example.hungerhub.Authentication.register.view;

public interface Registeriview {
    public void onNameError();
    public void onEmailError(String error);
    public void onPassError(String msg);
    public void onCinPassError();
    public void onMatchingPassError();
   public void onSuccessResponse();
    public void onFailureResponse(String message);

}
