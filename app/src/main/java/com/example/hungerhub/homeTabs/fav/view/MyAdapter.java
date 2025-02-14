package com.example.hungerhub.homeTabs.fav.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hungerhub.R;
import com.example.hungerhub.homeTabs.model.MealModel;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    List<MealModel> meals;
    FavIview favIview;

    public void setMeals(List<MealModel> meals) {
        this.meals = meals;

    }

    public MyAdapter(Context context, List<MealModel> ingredients,FavIview favIview) {
        this.context = context;
        this.meals = ingredients;
        this.favIview=favIview;

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
        MealModel current = meals.get(position);
        //holder.imageView.setImageBitmap(current.getImage());
        Glide.with(context).load(current.getImgUrl()).apply(new RequestOptions().override(200,200)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(holder.imageView);
        holder.tileTxt.setText(current.getTitle());
        holder.area.setText(current.getStrArea());
        holder.remBtn.setOnClickListener(v->{
            favIview.deleteFromFav(current);
        });



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


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mealPhotoFav);
            tileTxt = itemView.findViewById(R.id.favTitle);
            remBtn= itemView.findViewById(R.id.deleteBtn);
            area= itemView.findViewById(R.id.area);

        }
    }
}
