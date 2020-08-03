package com.example.myapplication.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.myapplication.adapter.AdapterBangGiaoDich;
import com.example.myapplication.R;
import com.example.myapplication.dao.BangGiaoDichDAO;
import com.example.myapplication.dao.KhoanThuChiDAO;
import com.example.myapplication.dbhelper.DBHelper;
import com.example.myapplication.model.BangGiaoDich;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentBangGiaoDich extends Fragment {

    ListView lvBangGiaoDich;
    FloatingActionButton button;
    Spinner spnMaKhoan;
    EditText edtNgayGD;
    EditText edtSoTien;
    EditText edtMoTa;
    List<BangGiaoDich> listBGD = new ArrayList<>();

    public FragmentBangGiaoDich() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_bang_giao_dich, container, false);
        //truy cập database
        DBHelper dbHelper = new DBHelper(getContext());
        BangGiaoDichDAO giaoDichDAO = new BangGiaoDichDAO(dbHelper.getWritableDatabase());
        //map các đối tượng trong layout fragment bảng giao dịch
        lvBangGiaoDich = view.findViewById(R.id.lvBangGiaoDich);
        button = view.findViewById(R.id.fbtnBangGiaoDich);
        listBGD = giaoDichDAO.readAll();
        //set dữ liệu cho từng dòng của bảng giao dịch
        AdapterBangGiaoDich giaoDich = new AdapterBangGiaoDich(getContext(), R.layout.row_bang_giao_dich, (ArrayList<BangGiaoDich>) listBGD);
        lvBangGiaoDich.setAdapter(giaoDich);
        giaoDich.notifyDataSetChanged();
        registerForContextMenu(lvBangGiaoDich);

        //set sự kiện cho nút floating button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View mView = getLayoutInflater().inflate(R.layout.them_bang_giao_dich, null);

                DBHelper dbHelper = new DBHelper(getContext());
                KhoanThuChiDAO khoanThuChiDAO = new KhoanThuChiDAO(dbHelper.getWritableDatabase());
                //map các đối tượng trong layout của dialog
                spnMaKhoan = mView.findViewById(R.id.spnMaKhoan);
                edtNgayGD = mView.findViewById(R.id.edtNgayGiaoDich);
                edtSoTien = mView.findViewById(R.id.edtSoTien);
                edtMoTa = mView.findViewById(R.id.edtMoTa);
                //lấy dữ liệu cho spiner của layout dialog
                List listBDD = new ArrayList<>();
                listBDD.addAll(khoanThuChiDAO.readMaKhoan());
                //set dữ liệu vào spiner
                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listBDD);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnMaKhoan.setAdapter(adapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Thêm Bảng Giao Dịch Nhé")
                        .setView(mView)
                        .setPositiveButton("Thêm luôn", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBHelper dbHelper = new DBHelper(getContext());
                                BangGiaoDichDAO bangGiaoDichDAO = new BangGiaoDichDAO(dbHelper.getWritableDatabase());

                                BangGiaoDich a = new BangGiaoDich(edtNgayGD.getText().toString(),
                                        Float.parseFloat(edtSoTien.getText().toString()),
                                        Integer.parseInt(spnMaKhoan.getSelectedItem().toString()),
                                        edtMoTa.getText().toString());

                                long id = bangGiaoDichDAO.createBangGD(a);

                                if (id > 0) {
                                    listBGD.add(new BangGiaoDich((int) id, edtNgayGD.getText().toString(),
                                            Float.parseFloat(edtSoTien.getText().toString()),
                                            Integer.parseInt(spnMaKhoan.getSelectedItem().toString()),
                                            edtMoTa.getText().toString()));
                                    AdapterBangGiaoDich giaoDich = new AdapterBangGiaoDich(getContext(), R.layout.row_bang_giao_dich, (ArrayList<BangGiaoDich>) listBGD);

                                    lvBangGiaoDich.setAdapter(giaoDich);
//                                    giaoDich.notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton("Không đâu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.contextmenu, menu);
    }

    //set sự kiện khi chọn các item trong context menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.update:
                View mView = getLayoutInflater().inflate(R.layout.them_bang_giao_dich, null);
                DBHelper dbHelper1 = new DBHelper(getContext());
                KhoanThuChiDAO khoanThuChiDAO = new KhoanThuChiDAO(dbHelper1.getWritableDatabase());
                //map các đối tượng trong layout thêm bảng giao dich
                spnMaKhoan = mView.findViewById(R.id.spnMaKhoan);
                edtNgayGD = mView.findViewById(R.id.edtNgayGiaoDich);
                edtSoTien = mView.findViewById(R.id.edtSoTien);
                edtMoTa = mView.findViewById(R.id.edtMoTa);
                //lấy dữ liệu cho spiner trong layout thêm bảng giao dịch
                List listBDD = new ArrayList<>();
                listBDD.addAll(khoanThuChiDAO.readMaKhoan());
                //đổ dữ liệu vào spiner
                final ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listBDD);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnMaKhoan.setAdapter(adapter);
                //gán lại dữ liệu lên layout khi update
                final BangGiaoDich con = listBGD.get(info.position);
                edtNgayGD.setText(con.getNgayGiaoDich());
                edtSoTien.setText(con.getSoTienGiaoDich() + "");
                edtMoTa.setText(con.getMoTa());

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Update Bảng Giao Dịch Nhé !!!")
                        .setView(mView)
                        .setPositiveButton("Update luôn", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBHelper dbHelper = new DBHelper(getContext());
                                BangGiaoDichDAO bangGiaoDichDAO = new BangGiaoDichDAO(dbHelper.getWritableDatabase());

                                BangGiaoDich sub = new BangGiaoDich(con.getMaGiaoDich(), edtNgayGD.getText() + "", Float.parseFloat(edtSoTien.getText().toString()), Integer.parseInt(spnMaKhoan.getSelectedItem().toString()), edtMoTa.getText().toString());

                                int id = bangGiaoDichDAO.update(sub);
                                if (id > 0) {
                                    listBGD.clear();
                                    listBGD.addAll(bangGiaoDichDAO.readAll());
                                    AdapterBangGiaoDich giaoDich = new AdapterBangGiaoDich(getContext(), R.layout.row_bang_giao_dich, (ArrayList<BangGiaoDich>) listBGD);
                                    lvBangGiaoDich.setAdapter(giaoDich);
                                }
                            }
                        })
                        .setNegativeButton("Thôi bỏ đi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            case R.id.delete:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Delete Bảng Giao Dịch Nhé !!!")
                        .setPositiveButton("Xóa đi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBHelper dbHelper = new DBHelper(getContext());
                                BangGiaoDichDAO giaoDichDAO = new BangGiaoDichDAO(dbHelper.getWritableDatabase());
                                final BangGiaoDich con1 = listBGD.get(info.position);
                                int a = con1.getMaGiaoDich();
                                giaoDichDAO.delete(a);
                                if (a > 0) {
                                    listBGD.remove(info.position);
                                    AdapterBangGiaoDich adapterBangGiaoDich = new AdapterBangGiaoDich(getContext(), R.layout.row_bang_giao_dich, (ArrayList<BangGiaoDich>) listBGD);
                                    lvBangGiaoDich.setAdapter(adapterBangGiaoDich);
                                }
                            }
                        })
                        .setNegativeButton("Không xóa đâu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
