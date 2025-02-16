package com.example.hungerhub.homeTabs.db;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.hungerhub.homeTabs.model.MealModel;
import com.example.hungerhub.homeTabs.plan.models.PlanMealModel;

@Database(entities = {MealModel.class, PlanMealModel.class},version = 10)
@TypeConverters(Converters.class)
public abstract class AppDb extends RoomDatabase {
    public static AppDb instance;
    public  abstract FavMealDao getMealDao();
    public abstract PlanMealDao getPlanMealDao();
    public  static AppDb getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), AppDb.class,"mealDb")
                   .fallbackToDestructiveMigration()
                    .build();
        }
        return  instance;
    }

}
