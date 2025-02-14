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
import com.example.hungerhub.homeTabs.search.CountryFlag;
import com.example.hungerhub.homeTabs.search.model.areaModels.AreaModel;
import com.example.hungerhub.homeTabs.search.model.categoryModels.CategoryModel;
import com.example.hungerhub.homeTabs.search.view.SearchResponseHandler;

import java.util.List;

public class AreaRecyclerAdapter extends RecyclerView.Adapter<AreaRecyclerAdapter.MyViewHolder> {
    Context context;
    List<AreaModel> list;
    SearchResponseHandler itemLisener;

    public AreaRecyclerAdapter(Context context, List<AreaModel> list , SearchResponseHandler iview) {
        this.context = context;
        this.list = list;
        this.itemLisener =iview;

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
        AreaModel current = list.get(position);
        //holder.imageView.setImageBitmap(current.getImage());
        Glide.with(context).load(CountryFlag.getFlagUrl(current.getStrArea())).apply(new RequestOptions().override(200,200)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(holder.imageView);
        holder.tileTxt.setText(current.getStrArea());
        holder.card.setOnClickListener(v-> itemLisener.onAreaClicked(current.getStrArea()));



    }

    @Override
    public int getItemCount() {
        return list.size();
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
