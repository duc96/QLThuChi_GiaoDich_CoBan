package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myapplication.adapter.AdapterLoaiThuLoaiChi;
import com.example.myapplication.R;
import com.example.myapplication.dao.KhoanThuChiDAO;
import com.example.myapplication.dbhelper.DBHelper;
import com.example.myapplication.model.KhoanThuChi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FragmentBangThuChi extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    FloatingActionButton button;
    public static ArrayList<KhoanThuChi> listThu = new ArrayList<>();
    public static ArrayList<KhoanThuChi> listChi = new ArrayList<>();

    public FragmentBangThuChi() {
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_bang_thu_chi, container, false);

        DBHelper helper = new DBHelper(getContext());
        KhoanThuChiDAO tcDAO = new KhoanThuChiDAO(helper.getWritableDatabase());
        listThu.clear();
        listChi.clear();
        listThu.addAll(tcDAO.readLoai("thu"));
        listChi.addAll(tcDAO.readLoai("chi"));

        //map cac item trong fragment
        viewPager = (ViewPager) view.findViewById(R.id.viewpagerBangThuChi);
        tabLayout = (TabLayout) view.findViewById(R.id.tabbangThuChi);
        button = view.findViewById(R.id.fbtnBangThuChi);

        //su kien cho nut floating button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //layout cho AlertDialog
                View mView = getLayoutInflater().inflate(R.layout.them_bang_thu_chi,null);

                //map item trong layout them bang thu chi
                final Spinner spinner = mView.findViewById(R.id.spnLoai);
                final EditText edtTenKhoan = mView.findViewById(R.id.edtTenKhoan);

                // tao adapter cho spinner trong layout them bang thu chi
                List list = new ArrayList();
                list.add("thu");
                list.add("chi");
                ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                // tao AlertDialog khi an vao floating button ben duoi
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Thêm Khoản Thu Chi")
                        .setView(mView)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String a = spinner.getSelectedItem().toString();
                                if(a.equals("chi")){
                                    //lay du lieu khi nguoi dung nhap vao voi loai la chi

                                    KhoanThuChi sub1 = new KhoanThuChi(edtTenKhoan.getText().toString(),a);
                                    DBHelper helper = new DBHelper(getContext());
                                    KhoanThuChiDAO tcDAO = new KhoanThuChiDAO(helper.getWritableDatabase());
                                    long num = tcDAO.createKhoanThuChi(sub1);

                                    if(num > 0){
                                    //add du lieu vao recyclerview loai chi
                                        listChi.clear();
                                        listChi.addAll(tcDAO.readLoai("chi"));
                                    }
                                }if(a.equals("thu")) {
                                    //lay du lieu khi nguoi dung nhap vao voi loai la thu

                                    KhoanThuChi sub1 = new KhoanThuChi(edtTenKhoan.getText().toString(), a);
                                    DBHelper helper = new DBHelper(getContext());
                                    KhoanThuChiDAO tcDAO = new KhoanThuChiDAO(helper.getWritableDatabase());
                                    long num = tcDAO.createKhoanThuChi(sub1);

                                    if (num > 0) {
                                    //add du lieu vao recyclerview loai thu
                                        listThu.clear();
                                        listThu.addAll(tcDAO.readLoai("thu"));
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //set cac thuoc tinh va ten cua cac tabLayout + viewPager
        tabLayout.addTab(tabLayout.newTab().setText("Loại thu"));
        tabLayout.addTab(tabLayout.newTab().setText("Loại chi"));

        AdapterLoaiThuLoaiChi adapterLoaiThuLoaiChi = new AdapterLoaiThuLoaiChi(getFragmentManager());

        viewPager.setAdapter(adapterLoaiThuLoaiChi);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return view;
    }
}
