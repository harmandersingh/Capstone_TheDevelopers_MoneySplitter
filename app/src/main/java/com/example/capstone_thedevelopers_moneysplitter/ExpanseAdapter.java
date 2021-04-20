package com.example.capstone_thedevelopers_moneysplitter;


import android.content.Context;
        import android.net.Uri;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.bumptech.glide.Glide;

        import java.util.ArrayList;

public class ExpanseAdapter extends RecyclerView.Adapter<ExpanseAdapter.ViewHolder> {

    private ArrayList<ExpanseData> mData;
    private LayoutInflater mInflater;
    OnItemClickListener onItemClickListener;
    Context context;

    // data is passed into the constructor
    ExpanseAdapter(Context context, ArrayList<ExpanseData> data, OnItemClickListener onItemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public void onBindViewHolder(@NonNull ExpanseAdapter.ViewHolder holder, int position) {

        holder.myTextView.setText(mData.get(position).getUserName() + " payed $ " + mData.get(position).getExpanse());
        holder.description.setText(mData.get(position).getDescription());

        Glide
                .with(context)
                .load(Uri.parse(mData.get(position).image))
                .centerCrop()
                .into(holder.imgBill);
//        holder.imgBill.setImageURI(Uri.parse(mData.get(position).image));
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(position));
    }


    @NonNull
    @Override
    public ExpanseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_expanse, parent, false);
        return new ExpanseAdapter.ViewHolder(view);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        TextView description;
        ImageView imgBill;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.txtName);
            description = itemView.findViewById(R.id.txtDetail);
            imgBill = itemView.findViewById(R.id.imgBill);
        }
    }

    interface OnItemClickListener {
        void onItemClick(int index);
    }


}

