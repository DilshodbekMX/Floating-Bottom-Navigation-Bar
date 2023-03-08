package com.example.bottomnavigationbar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// before
import com.example.bottomnavigationbar.Adaptor.CategoryAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

// after
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bottomnavigationbar.Domain.CategoryDomain;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private View categoryView;
    private ArrayList<CategoryDomain> categoryDomain;
    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerview;
    FirebaseFirestore db;
    CollectionReference categories;
    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        categoryView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerview = (RecyclerView) categoryView.findViewById(R.id.recyclerVeiw);
        // creating our new array list
        categoryDomain = new ArrayList<>();
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        categories = db.collection("Category");
        categoryAdapter = new CategoryAdapter(categoryDomain, getContext());
        recyclerview.setAdapter(categoryAdapter);
        loadrecyclerviewData();
        return categoryView;
    }
    private void loadrecyclerviewData() {

        categories.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                CategoryDomain dataModal = d.toObject(CategoryDomain.class);

                                categoryDomain.add(dataModal);
                            }
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}