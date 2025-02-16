package com.example.hungerhub.homeTabs.plan.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hungerhub.R;

import java.util.ArrayList;
import java.util.List;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.MyViewHolder> {
    Context context;
    Planiview planiview;
   private  int selectedItem=-1;
   int previouSelected=-1;
   int primaryColor;
   int whiteColor;
  List<String> weekList=new ArrayList<>();
  //private  int selectedPosition=RecyclerView.NO_POSITION;


    public CalenderAdapter(Context context,List<String> weekList,Planiview planiview) {
        this.context = context;
        this.weekList=weekList;
        this.planiview=planiview;
        primaryColor = ContextCompat.getColor(context,R.color.primary);
        whiteColor =ContextCompat.getColor(context,R.color.white);


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.week_item,parent,false);
        MyViewHolder myviewHolder = new MyViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     String current= weekList.get(position);
     holder.day.setText(current);

     holder.card.setOnClickListener(v->{
         planiview.onDayClicked(current);
         previouSelected=selectedItem;
         selectedItem=holder.getBindingAdapterPosition();
         notifyItemChanged(selectedItem);
         notifyItemChanged(previouSelected);

     });


        if(selectedItem==holder.getBindingAdapterPosition()){
            holder.card.setCardBackgroundColor(whiteColor);
            holder.day.setTextColor(primaryColor);

        }
        else{
            holder.card.setCardBackgroundColor(primaryColor);
            holder.day.setTextColor(whiteColor);
        }


    }

    @Override
    public int getItemCount() {
        return weekList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{


        TextView day;
        CardView card;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            day=itemView.findViewById(R.id.dayName);
            card=itemView.findViewById(R.id.card);


        }
    }
}
