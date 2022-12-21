package com.example.serna_agudo_isabel_pmdm03;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.serna_agudo_isabel_pmdm03.bikes.BikesContent;
import com.example.serna_agudo_isabel_pmdm03.databinding.FragmentItemListBinding;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment implements MyBikeRecyclerViewAdapter.BikeOnClickListener {

    FragmentItemListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemListBinding.inflate(LayoutInflater.from(requireContext()), container, false);
        BikesContent.loadBikesFromJSON(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set the adapter
        MyBikeRecyclerViewAdapter recyclerView = new MyBikeRecyclerViewAdapter(
                BikesContent.ITEMS,
                this
        );
        binding.recycler.setAdapter(recyclerView);
    }

    @Override
    public void onClick(BikesContent.Bike bike) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL,new String[]{bike.getEmail()});
        i.putExtra(Intent.EXTRA_SUBJECT,"Couch App: I'd like to book your bike");
        i.putExtra(Intent.EXTRA_TEXT,"Dear Mr/Mrs " + bike.getOwner() + ": \n" +
                "I'd like to use your bike at " + bike.getLocation() + " (" +
                bike.getCity() + ") " + "for the following date: " + BikesContent.selectedDate + ".\n" +
                "Can you confirm its availability?\n" +
                "Kindest regards.");
        i.setType("message/rfc822");
        startActivity(i);
        Toast.makeText(requireContext(),
                "Envíando email...",Toast.LENGTH_LONG).show();
    }

}