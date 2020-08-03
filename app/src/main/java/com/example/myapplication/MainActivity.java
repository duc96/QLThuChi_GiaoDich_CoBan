package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.dao.BangGiaoDichDAO;
import com.example.myapplication.dao.KhoanThuChiDAO;
import com.example.myapplication.dbhelper.DBHelper;
import com.example.myapplication.fragment.FragmentBangThuChi;
import com.example.myapplication.fragment.FragmentGioiThieu;

import com.example.myapplication.fragment.FragmentBangGiaoDich;
import com.example.myapplication.fragment.FragmentThongKe;
import com.example.myapplication.fragment.Fragment_Home;
import com.example.myapplication.model.BangGiaoDich;
import com.example.myapplication.model.KhoanThuChi;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawer;
    FragmentManager fragmentManager = getSupportFragmentManager();
    TextView tvSoDu;
    private float tongThu;
    private float tongChi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment_Home f0 = new Fragment_Home();
        fragmentManager.beginTransaction()
                .replace(R.id.framelayout, f0)
                .commit();

        mappingComponent();
        setToolbarListener();
        setNavigationViewListener();
        }

    protected void setDataSoDu(){
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        KhoanThuChiDAO thuChiDAO = new KhoanThuChiDAO(dbHelper.getWritableDatabase());
        BangGiaoDichDAO giaoDichDAO = new BangGiaoDichDAO(dbHelper.getWritableDatabase());

        final List listMaKhoanGiaoDich = giaoDichDAO.readAll();
        final List listMaKhoanThuChi = thuChiDAO.readAll();
        tongChi = 0;
        tongThu = 0;
        for(int i = 0; i < listMaKhoanGiaoDich.size(); i++) {
            //lấy mã khoản của bảng giao dịch
            BangGiaoDich conBGD = (BangGiaoDich) listMaKhoanGiaoDich.get(i);
            int ma1 = conBGD.getMaKhoan();
            for (int j = 0; j < listMaKhoanThuChi.size(); j++) {
                //lấy mã khoản của bảng thu chi
                KhoanThuChi conKTC = (KhoanThuChi) listMaKhoanThuChi.get(j);
                int ma2 = conKTC.getMaKhoan();
                //so sánh cùng mã khoản, cùng loại chi và có ngày giao dịch mà người dùng cần
                if (ma1 == ma2 && conKTC.getLoaiKhoan().equals("chi")) {
                    tongChi += conBGD.getSoTienGiaoDich();
                }
                if (ma1 == ma2 && conKTC.getLoaiKhoan().equals("thu")) {
                    tongThu += conBGD.getSoTienGiaoDich();
                }
            }
            if (tongChi != 0 || tongThu != 0) {

                if (tongThu - tongChi > 0) {
                    tvSoDu.setText((tongThu - tongChi) + " VND");
                } else {
                    tvSoDu.setText("-" + tongChi + " VND (cân nhắc khi chi tiêu !)");
                }
            }
        }
    }

    protected void mappingComponent(){
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawLayout);

        View header = navigationView.getHeaderView(0);
        tvSoDu = (TextView) header.findViewById(R.id.tvSoDu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                setDataSoDu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setToolbarListener(){
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setNavigationViewListener(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.bangGiaoDich:
                        toolbar.setTitle("Bảng giao dịch");
                        FragmentBangGiaoDich f1 = new FragmentBangGiaoDich();
                        fragmentManager.beginTransaction()
                                .replace(R.id.framelayout,f1)
                                .commit();
                        drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.bangThuChi:
                        toolbar.setTitle("Bảng thu chi");
                        FragmentBangThuChi f2 = new FragmentBangThuChi();
                        fragmentManager.beginTransaction()
                                .replace(R.id.framelayout,f2)
                                .commit();
                        drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.thongKe:
                        toolbar.setTitle("Thống kê");
                        FragmentThongKe f3 = new FragmentThongKe();
                        fragmentManager.beginTransaction()
                                .replace(R.id.framelayout,f3)
                                .commit();
                        drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.gioiThieu:
                        toolbar.setTitle("Giới thiệu");
                        FragmentGioiThieu f4 = new FragmentGioiThieu();
                        fragmentManager.beginTransaction()
                                .replace(R.id.framelayout,f4)
                                .commit();
                        drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.thoat:
                        System.exit(0);
                        break;
                }
                return false;
            }
        });
    }
}