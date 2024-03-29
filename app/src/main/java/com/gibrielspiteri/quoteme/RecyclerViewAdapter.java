package com.gibrielspiteri.quoteme;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    public static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mQuotes = new ArrayList<>();
    private ArrayList<String> mAuthors = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> quotes, ArrayList<String> authors, ArrayList<String> images){
        mContext = context;
        mQuotes = quotes;
        mAuthors = authors;
        mImages = images;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

//        Glide.with(mContext)
//                .asBitmap()
//                .load(mImages.get(position))
//                .into(holder.image);

        holder.tvBody.setText(mQuotes.get(position));
        holder.tvAuthor.setText((mAuthors.get(position)));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mQuotes.get(position));
                Toast.makeText(mContext, mQuotes.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuotes.size()-1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView tvBody, tvAuthor;
        ConstraintLayout parentLayout;
        public ViewHolder(View itemView){
            super(itemView);
            //image = itemView.findViewById(R.id.ivImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }
}
