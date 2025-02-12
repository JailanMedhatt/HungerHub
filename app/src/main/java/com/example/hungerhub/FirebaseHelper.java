package com.example.hungerhub;

import android.util.Log;

import com.example.hungerhub.homeTabs.MealModel;
import com.example.hungerhub.homeTabs.fav.presenter.PresenterMethodsCaller;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

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
    public void getAllMeals(String uid, PresenterMethodsCaller methodsCaller){
        db.collection("meals").whereEqualTo("uId",uid).get().
                addOnSuccessListener(
                        queryDocumentSnapshots -> {
                          if(!queryDocumentSnapshots.isEmpty()){
                              List<MealModel> meals= new ArrayList<>();
                              for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
                                  meals.add(d.toObject(MealModel.class));
                              }
                              methodsCaller.onSuccess(meals);
                          }
                        }
                );
    }
}
