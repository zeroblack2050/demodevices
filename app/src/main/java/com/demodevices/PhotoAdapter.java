package com.demodevices;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Superadmin1 on 28/10/2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.viewHolder>{

    private Context context;
    ArrayList<String> arrayFiles;
    private int widthLayaout;
    private int heighthLayaout;

    public PhotoAdapter(Context context) {
        this.context = context;
    }

    public void setFiles(ArrayList<String> arrayFiles){
        this.arrayFiles = arrayFiles;
    }

    @Override
    public PhotoAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemphoto,parent,false);
        return new viewHolder(view);
    }

    public void setSize(int width, int height){
        this.widthLayaout = width;
        this.heighthLayaout = height;
    }

    @Override
    public void onBindViewHolder(PhotoAdapter.viewHolder holder, int position) {
        String fileName = arrayFiles.get(position);

        Glide.with(context).load(fileName).override(widthLayaout, heighthLayaout).into(holder.itemPhoto);
    }

    @Override
    public int getItemCount() {
        return arrayFiles.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        final ImageView itemPhoto;
        final ImageView itemDelete;

        public viewHolder(View itemView){
            super(itemView);
            itemPhoto  =  itemView.findViewById(R.id.item_image);
            itemDelete = itemView.findViewById(R.id.item_delete);

            itemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    arrayFiles.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeRemoved(getAdapterPosition(),getItemCount());
                }
            });

        }



    }


}
