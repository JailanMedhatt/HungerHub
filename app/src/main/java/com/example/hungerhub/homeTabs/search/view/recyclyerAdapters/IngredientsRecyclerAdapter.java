package com.example.hungerhub.homeTabs.search.view.recyclyerAdapters;


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
import com.example.hungerhub.homeTabs.search.model.ingredientModels.IngredientModel;
import com.example.hungerhub.homeTabs.search.view.SearchResponseHandler;

import java.util.List;

public class IngredientsRecyclerAdapter extends RecyclerView.Adapter<IngredientsRecyclerAdapter.MyViewHolder> {
    Context context;
    List<IngredientModel> ingredients;
    SearchResponseHandler viewListener;


    public IngredientsRecyclerAdapter(Context context, List<IngredientModel> ingredients,SearchResponseHandler viewListener) {
        this.context = context;
        this.ingredients = ingredients;
        this.viewListener=viewListener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ingredient_item,parent,false);
        MyViewHolder myviewHolder = new MyViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        IngredientModel current = ingredients.get(position);
        //holder.imageView.setImageBitmap(current.getImage());
        Glide.with(context).load("https://www.themealdb.com/images/ingredients/"+current.getStrIngredient()+".png").apply(new RequestOptions().override(200,200)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(holder.imageView);
        holder.tileTxt.setText(current.getStrIngredient());
        holder.card.setOnClickListener(v->viewListener.onIngredientClicked(current.getStrIngredient()));

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tileTxt;
      CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ingredientImg);
            tileTxt = itemView.findViewById(R.id.ingredient);
            card=itemView.findViewById(R.id.card);

        }
    }
}
