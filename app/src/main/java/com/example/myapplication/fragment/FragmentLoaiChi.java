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
import com.example.myapplication.dao.KhoanThuChiDAO;
import com.example.myapplication.dbhelper.DBHelper;


import static com.example.myapplication.fragment.FragmentBangThuChi.listChi;

public class FragmentLoaiChi extends Fragment {

    RecyclerView rcChi;
    public FragmentLoaiChi() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_loai_chi, container,false);
        rcChi = view.findViewById(R.id.recvLoaiChi);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getRootView().getContext());

        rcChi.setLayoutManager(layoutManager);
        AdapterLoaiThu chi = new AdapterLoaiThu(getContext(),listChi);
        rcChi.setAdapter(chi);

        return view;
    }


}
