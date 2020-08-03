package com.example.myapplication.adapter;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.R;
import com.example.myapplication.fragment.FragmentLoaiChi;
import com.example.myapplication.fragment.FragmentLoaiThu;

public class AdapterLoaiThuLoaiChi extends FragmentStatePagerAdapter {


    public AdapterLoaiThuLoaiChi(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        //set Fragment cho cac tablayout
        switch (position){
            case 0:
                FragmentLoaiThu loaiThu = new FragmentLoaiThu();

                Bundle b1 = new Bundle();
                b1.putInt("loaithu", R.layout.fragment_fragment_loai_thu);
                loaiThu.setArguments(b1);

                return loaiThu;
            case 1:
                FragmentLoaiChi loaiChi = new FragmentLoaiChi();

                Bundle b2 = new Bundle();
                b2.putInt("loaichi",R.layout.fragment_fragment_loai_chi);
                loaiChi.setArguments(b2);

                return loaiChi;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}

