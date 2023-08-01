package com.example.androidlearning.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidlearning.PlaceAdapter;
import com.example.androidlearning.databinding.FragmentHomeBinding;
import com.example.androidlearning.models.Place;
import com.example.shoppingapp.databinding.ActivityBooksBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BooksActivity extends Fragment {

    private ActivityBooksBinding binding;
    private ArrayList<Books> placesList;
    private RecyclerView placesRecyclerList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = ActivityBooksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        placesRecyclerList = binding.placesListView;


        placesList = new ArrayList<>();
        fetchPlaces();


        return root;
    }

    private void fetchPlaces() {
        FirebaseFirestore fire = FirebaseFirestore.getInstance();

        fire.collection("books")
                .get()
                .addOnCompleteListener(
                        task -> {
                            for(DocumentSnapshot doc : task.getResult()){
                                Place place = doc.toObject(Place.class);
                                placesList.add(place);
                            }
                            if(!placesList.isEmpty()){
                                PlaceAdapter adapter = new PlaceAdapter(placesList, getActivity());
                                placesRecyclerList.setAdapter(adapter);
                            }

                        }
                )
                .addOnFailureListener(
                        e -> Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show()

                );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}