package com.example.serna_agudo_isabel_pmdm03;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.serna_agudo_isabel_pmdm03.bikes.BikesContent;
import com.example.serna_agudo_isabel_pmdm03.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.serna_agudo_isabel_pmdm03.databinding.FragmentItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBikeRecyclerViewAdapter extends RecyclerView.Adapter<MyBikeRecyclerViewAdapter.ViewHolder> {

    private final List<BikesContent.Bike> mValues;
    private BikeOnClickListener callback;

    public MyBikeRecyclerViewAdapter(List<BikesContent.Bike> items) {
        mValues = items;
    }

    public MyBikeRecyclerViewAdapter(List<BikesContent.Bike> items, BikeOnClickListener bikeClickListener) {
        mValues = items;
        callback = bikeClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentItemBinding binding = FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bindView(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private FragmentItemBinding binding;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(BikesContent.Bike bike) {
            binding.txtCity.setText(bike.getCity());
            binding.imgPhoto.setImageBitmap(bike.getPhoto());
            binding.txtDescription.setText(bike.getDescription());
            binding.txtLocation.setText(bike.getLocation());
            binding.txtOwner.setText(bike.getOwner());
            binding.btnMail.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    callback.onClick(bike);
                }
            });
        }
    }

    public interface BikeOnClickListener {
        void onClick(BikesContent.Bike bike);
    }
}