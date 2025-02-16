package com.example.hungerhub.homeTabs.db;
import androidx.room.TypeConverter;
import com.example.hungerhub.homeTabs.model.MealModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromMealModel(MealModel mealModel) {
        return mealModel == null ? null : gson.toJson(mealModel);
    }

    @TypeConverter
    public static MealModel toMealModel(String mealModelString) {
        if (mealModelString == null) return null;
        Type type = new TypeToken<MealModel>() {}.getType();
        return gson.fromJson(mealModelString, type);
    }
}

