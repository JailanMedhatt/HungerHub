package com.example.hungerhub;

import android.util.Log;

import com.example.hungerhub.homeTabs.MealModel;
import com.example.hungerhub.homeTabs.fav.presenter.PresenterMethodsCaller;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FirebaseHelper {
    private FirebaseFirestore db;
   public FirebaseHelper(){
        db=FirebaseFirestore.getInstance();
    }
    public void addMealToFireStore(MealModel mealModel){
        db.collection("meals").document().set(mealModel).addOnSuccessListener(
                v->{
                    Log.i("TAG", "addMealToFireStore:added ");
                }
        ).addOnFailureListener(e->{
            Log.i("TAG", "addMealToFireStore:added ");
        });
    }
    public Observable<List<MealModel>> getAllMeals(String uid) {
        return Observable.<List<MealModel>>create(emitter -> {
            db.collection("meals")
                    .whereEqualTo("uId", uid)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<MealModel> meals = new ArrayList<>();
                        for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                            MealModel meal = d.toObject(MealModel.class);
                            if (meal != null) {
                                meals.add(meal);
                            }
                        }
                        emitter.onNext(meals); // Emit the result
                    })
                    .addOnFailureListener(emitter::onError); // Emit an error if query fails
        }).subscribeOn(Schedulers.io()); // Run on a background thread
    }
    public void deleteMeal(MealModel meal) {
        db.collection("meals")
                .whereEqualTo("idMeal", meal.getIdMeal())
                .whereEqualTo("uId", meal.getuId())
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            document.getReference().delete();
                        }
                    }
                });
    }
}
