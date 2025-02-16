package com.example.hungerhub.homeTabs.home.view;

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
import com.example.hungerhub.homeTabs.model.MealModel;

import java.util.List;

public class MyCarouselAdapter extends RecyclerView.Adapter<MyCarouselAdapter.MyCarouselViewHolder> {
    Context context;
    List<MealModel> meals;
    IhomeView iview;


    public MyCarouselAdapter(Context context, List<MealModel> list,IhomeView iview) {
        this.context = context;
        this.meals = list;
        this.iview=iview;

    }



    @NonNull
    @Override
    public MyCarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.meal_item,parent,false);
        MyCarouselViewHolder myviewHolder = new MyCarouselViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCarouselViewHolder holder, int position) {
        MealModel current = meals.get(position);
        //holder.imageView.setImageBitmap(current.getImage());
        Glide.with(context).load(current.getImgUrl())
                .apply(new RequestOptions().override(200,200)).placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground).into(holder.imageView);
        holder.tileTxt.setText(current.getTitle());
        holder.cardView.setOnClickListener(v->iview.onMealClicked(current));


    }

    @Override
    public int getItemCount() {
        return meals.size();
    }


    class MyCarouselViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tileTxt;
        CardView cardView;


        public MyCarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mealPhotoFav);
            tileTxt = itemView.findViewById(R.id.favTitle);
            cardView= itemView.findViewById(R.id.card);

        }
    }
}

