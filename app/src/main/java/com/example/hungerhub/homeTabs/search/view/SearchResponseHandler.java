package com.example.hungerhub.homeTabs.search.view;

import com.example.hungerhub.homeTabs.search.model.areaModels.AreaModel;
import com.example.hungerhub.homeTabs.search.model.categoryModels.CategoryModel;
import com.example.hungerhub.homeTabs.search.model.ingredientModels.IngredientModel;

import java.util.List;

public interface SearchResponseHandler {
    public  void setCategoriesList(List<CategoryModel> categories);
    public  void setIngredientsList(List<IngredientModel> ingredientsList);
    public  void setAreasList(List<AreaModel> areas);
    public void onCategoryClicked(String cat);
    public void onIngredientClicked(String ing);
    public void onAreaClicked(String ing);
}
