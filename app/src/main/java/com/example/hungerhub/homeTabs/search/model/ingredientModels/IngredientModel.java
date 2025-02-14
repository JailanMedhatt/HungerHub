package com.example.hungerhub.homeTabs.search.model.ingredientModels;

public class IngredientModel {
  private   String idIngredient;
   private String strIngredient;

    public IngredientModel(String idIngredient, String strIngredient) {
        this.idIngredient = idIngredient;
        this.strIngredient = strIngredient;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }
}
