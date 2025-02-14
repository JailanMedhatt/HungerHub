package com.example.hungerhub.homeTabs.search.chosenMeals.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hungerhub.R;
import com.example.hungerhub.homeTabs.MealModel;

import com.example.hungerhub.homeTabs.search.chosenMeals.FilterMealsiview;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MyViewHolder> {
    Context context;
    List<MealModel> meals;
    FilterMealsiview filterMealsiview;

    public void setMeals(List<MealModel> meals) {
        this.meals = meals;

    }

    public MealsAdapter(Context context, List<MealModel> ingredients, FilterMealsiview filterMealsiview) {
        this.context = context;
        this.meals = ingredients;
        this.filterMealsiview =filterMealsiview;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.meal_item,parent,false);
        MyViewHolder myviewHolder = new MyViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MealModel current = meals.get(position);
        //holder.imageView.setImageBitmap(current.getImage());
        Glide.with(context).load(current.getImgUrl()).apply(new RequestOptions().override(200,200)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(holder.imageView);
        holder.tileTxt.setText(current.getTitle());

        holder.card.setOnClickListener(v->filterMealsiview.onMealClicked(current));



    }

    @Override
    public int getItemCount() {
        return meals.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tileTxt;


        CardView card;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mealPhotoFav);
            tileTxt = itemView.findViewById(R.id.favTitle);

            card= itemView.findViewById(R.id.card);

        }
    }
}
