package com.example.hungerhub.homeTabs.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.example.hungerhub.homeTabs.model.MealModel;

@Database(entities = MealModel.class,version = 4)
public abstract class AppDb extends RoomDatabase {
    public static AppDb instance;
    public  abstract MealDao getMealDao();
    public  static AppDb getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), AppDb.class,"mealDb").addMigrations(MIGRATION_1_3,MIGRATION_3_4).build();
        }
        return  instance;
    }
    static final Migration MIGRATION_1_3 = new Migration(1, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Combine all schema changes from 1 to 3
            database.execSQL("ALTER TABLE meal_table ADD COLUMN uId TEXT");
        }
    };
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Add the new 'calendar' column
            database.execSQL("ALTER TABLE meal_table ADD COLUMN calendar INTEGER DEFAULT 0 NOT NULL");
        }
    };


}
