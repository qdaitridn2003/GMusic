package com.practice.gmusic.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.gmusic.R;
import com.practice.gmusic.adapters.CategoryAdapter;
import com.practice.gmusic.adapters.StatAdapter;
import com.practice.gmusic.daos.CategoryDAO;
import com.practice.gmusic.entities.Category;
import com.practice.gmusic.entities.Stat;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView rvTrending,rvInstrumental,rvLofi,rvAsia,rvUsUk,rvTop5;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvTrending = view.findViewById(R.id.rvTrending);
        rvInstrumental = view.findViewById(R.id.rvInstrumental);
        rvLofi = view.findViewById(R.id.rvLofi);
        rvAsia = view.findViewById(R.id.rvAsia);
        rvUsUk = view.findViewById(R.id.rvUsUk);
        rvTop5 = view.findViewById(R.id.rvTop5);

        loadData();

        return view;
    }

    private void loadData(){
        CategoryDAO DAO = new CategoryDAO(getContext());

        ArrayList<Category> listInstrumental = DAO.getAll("Instrumental");
        ArrayList<Category> listLofiChill = DAO.getAll("Lofi Chill");
        ArrayList<Category> listAsia = DAO.getAll("Asia");
        ArrayList<Category> listUsUk = DAO.getAll("US UK");
        ArrayList<Category> listTrending = DAO.getAll("Trending");
        ArrayList<Stat> listTop5 = DAO.getTop5Track();

        CategoryAdapter trendingAdapter = new CategoryAdapter(listTrending,getContext());
        CategoryAdapter instrumentalAdapter = new CategoryAdapter(listInstrumental,getContext());
        CategoryAdapter lofiChillAdapter = new CategoryAdapter(listLofiChill,getContext());
        CategoryAdapter asiaAdapter = new CategoryAdapter(listAsia,getContext());
        CategoryAdapter usUkAdapter = new CategoryAdapter(listUsUk,getContext());
        StatAdapter statAdapter = new StatAdapter(listTop5,getContext());

        LinearLayoutManager trendingManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager instrumentalManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager lofiChillManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager asiaManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager usUkManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        rvTrending.setLayoutManager(trendingManager);
        rvTrending.setAdapter(trendingAdapter);

        rvInstrumental.setLayoutManager(instrumentalManager);
        rvInstrumental.setAdapter(instrumentalAdapter);

        rvLofi.setLayoutManager(lofiChillManager);
        rvLofi.setAdapter(lofiChillAdapter);

        rvAsia.setLayoutManager(asiaManager);
        rvAsia.setAdapter(asiaAdapter);

        rvUsUk.setLayoutManager(usUkManager);
        rvUsUk.setAdapter(usUkAdapter);

        rvTop5.setLayoutManager(layoutManager);
        rvTop5.setAdapter(statAdapter);
    }
}