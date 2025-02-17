package com.example.hungerhub.homeTabs.plan.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hungerhub.R;
import com.example.hungerhub.homeTabs.model.MealModel;
import com.example.hungerhub.homeTabs.plan.models.PlanMealModel;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyViewHolder> {
    Context context;
    List<PlanMealModel> meals;
    Planiview planiview;

    public void setMeals(List<PlanMealModel> meals) {
        this.meals = meals;

    }

    public PlanAdapter(Context context, List<PlanMealModel> ingredients, Planiview planiview) {
        this.context = context;
        this.meals = ingredients;
        this.planiview = planiview;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fav_item,parent,false);
        MyViewHolder myviewHolder = new MyViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PlanMealModel current = meals.get(position);
        //holder.imageView.setImageBitmap(current.getImage());
        Glide.with(context).load(current.getMealModel().getImgUrl()).apply(new RequestOptions().override(200,200)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(holder.imageView);
        holder.tileTxt.setText(current.getMealModel().getTitle());
        holder.area.setText(current.getMealModel().getStrArea());
        holder.remBtn.setOnClickListener(v->planiview.deleteMeal(current));
       holder.cardView.setOnClickListener(v->planiview.onMealClicked(meals.get(position).getMealModel()));


    }

    @Override
    public int getItemCount() {
        return meals.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tileTxt;
        Button remBtn;
        TextView area;

    CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mealPhotoFav);
            tileTxt = itemView.findViewById(R.id.favTitle);
            remBtn= itemView.findViewById(R.id.deleteBtn);
            area= itemView.findViewById(R.id.area);
            cardView=itemView.findViewById(R.id.card);

        }
    }
}
