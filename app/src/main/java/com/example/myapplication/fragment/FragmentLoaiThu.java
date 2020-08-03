package com.example.myapplication.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AdapterLoaiThu;

import static com.example.myapplication.fragment.FragmentBangThuChi.listThu;

public class FragmentLoaiThu extends Fragment {

    RecyclerView rcThu;

    public FragmentLoaiThu() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_loai_thu, container,false);
        rcThu = view.findViewById(R.id.recvLoaiThu);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getRootView().getContext());

        rcThu.setLayoutManager(layoutManager);
        AdapterLoaiThu thu = new AdapterLoaiThu(getContext(),listThu);
        rcThu.setAdapter(thu);

        return view;
    }

}
