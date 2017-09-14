package com.example.android.lagosjavadevelopers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by OLANUBI J. A on 9/1/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.viewHolder> {

    public interface OnItemClickListener {
        void onItemClick(SourceItem item);
    }


    private List<SourceItem> sourceItemList;
    private final OnItemClickListener listener;

   //private Context context;

    public RecyclerAdapter(List<SourceItem> srcItemList, OnItemClickListener listener) {

        sourceItemList = srcItemList;
        this.listener = listener;

    }
    @Override
    public RecyclerAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new viewHolder(inflatedView);
    }
    //Edit Text Here--
   // @Override
   // public void onBindViewHolder(viewHolder holder, int position) {
      //  holder.text.setText(sourceItemList.get(position).getName());
       // Glide.with(holder.image.getContext()).load(sourceItemList.get(position).getAvatar()).into(holder.image);
   // }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {

        holder.bind(sourceItemList.get(position), listener);
    }


    @Override
    public int getItemCount() {
        return sourceItemList.size();
    }


    public static class viewHolder extends RecyclerView.ViewHolder  { //implements View.OnClickListener
        private ImageView image;
        private TextView text;

        public viewHolder(View itemView){
        super(itemView);
            image = (ImageView) itemView.findViewById(R.id.profileImage);
            text = (TextView) itemView.findViewById(R.id.userName);
            //Edit Text Here-- itemView.setOnClickListener(this);
        }
        //
        public void bind(final SourceItem item, final OnItemClickListener listener){
            text.setText(item.getName());
            Glide.with(itemView.getContext()).load(item.getAvatar()).into(image);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
        //

        /**Edit Text HEre 1
        //@Override
        public void onClick(View v) {
           Context context = itemView.getContext();
            Intent showDetails= new Intent(context, ProfileDetails.class);
            context.startActivity(showDetails);

        }
        */
    }


}
