package com.example.myapplication.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.BangGiaoDichDAO;
import com.example.myapplication.dao.KhoanThuChiDAO;
import com.example.myapplication.dbhelper.DBHelper;
import com.example.myapplication.model.BangGiaoDich;
import com.example.myapplication.model.KhoanThuChi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class FragmentThongKe extends Fragment {
//    TextView tvNoiDung;
//    Button butTongNgay;
//    Button butTongThang;
//    Button butTongNam;
//    EditText edtNhapNgay;
//    EditText edtNhapThang;
//    EditText edtNhapNam;
//    float tongThu = 0;
//    float tongChi = 0;
    TextView tvTuNgay;
    TextView tvDenNgay;
    Button btnTim;
    TextView tvNoiDung;
    String tuNgay = "";
    String denNgay = "";
    private float tongThu = 0;
    private float tongChi = 0;


    public FragmentThongKe() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_thong_ke, container, false);

//        tvNoiDung = view.findViewById(R.id.tvNoiDung);
//        butTongNgay = view.findViewById(R.id.butTongNgay);
//        butTongThang = view.findViewById(R.id.butTongThang);
//        butTongNam = view.findViewById(R.id.butTongNam);
//        setOnClick();


        tvTuNgay = view.findViewById(R.id.tvTuNgay);
        tvDenNgay = view.findViewById(R.id.tvDenNgay);
        tvNoiDung = view.findViewById(R.id.tvNoiDung);

        btnTim = view.findViewById(R.id.btnTim);

        setOnClickListener();


        return view;
    }

    protected void setOnClickListener() {
        tvTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonTuNgay();
            }
        });

        tvDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonDenNgay();
            }
        });

        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tim();
                tongChi = 0;
                tongThu = 0;
            }
        });
    }

    private void chonTuNgay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                tuNgay = dateFormat.format(calendar.getTime());
                tvTuNgay.setText("Từ ngày \n" + tuNgay);
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void chonDenNgay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                denNgay = dateFormat.format(calendar.getTime());
                tvDenNgay.setText("Đến ngày \n" + denNgay);
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    protected void tim() {
        DBHelper dbHelper = new DBHelper(getContext());
        KhoanThuChiDAO thuChiDAO = new KhoanThuChiDAO(dbHelper.getWritableDatabase());
        BangGiaoDichDAO giaoDichDAO = new BangGiaoDichDAO(dbHelper.getWritableDatabase());

        final List listMaKhoanGiaoDich = giaoDichDAO.readAll();
        final List listMaKhoanThuChi = thuChiDAO.readAll();

        int namBatDau = Integer.parseInt(tuNgay.substring(6));
        int namKetThuc = Integer.parseInt(denNgay.substring(6));
        int thangBatDau = Integer.parseInt(tuNgay.substring(3, 5));
        int thangKetThuc = Integer.parseInt(denNgay.substring(3, 5));
        int ngayBatDau = Integer.parseInt(tuNgay.substring(0, 2));
        int ngayKetThuc = Integer.parseInt(denNgay.substring(0, 2));

        for (int i = 0; i < listMaKhoanGiaoDich.size(); i++) {
            //lấy mã khoản của bảng giao dịch
            BangGiaoDich conBGD = (BangGiaoDich) listMaKhoanGiaoDich.get(i);
            int ma1 = conBGD.getMaKhoan();
            for (int j = 0; j < listMaKhoanThuChi.size(); j++) {
                //lấy mã khoản của bảng thu chi
                KhoanThuChi conKTC = (KhoanThuChi) listMaKhoanThuChi.get(j);
                int ma2 = conKTC.getMaKhoan();
                //so sánh cùng mã khoản, cùng loại chi và có ngày giao dịch mà người dùng cần
                if (ma1 == ma2 && conKTC.getLoaiKhoan().equals("chi")) {
                    int nam = Integer.parseInt(conBGD.getNgayGiaoDich().substring(6));
                    int thang = Integer.parseInt(conBGD.getNgayGiaoDich().substring(3, 5));
                    int ngay = Integer.parseInt(conBGD.getNgayGiaoDich().substring(0, 2));

                    if(nam == namKetThuc && nam == namBatDau){
                        if(thang == thangBatDau && thang == thangKetThuc){
                            if(((ngay == ngayBatDau) || (ngay > ngayBatDau)) && ((ngay == ngayKetThuc) || (ngay < ngayKetThuc))){
                                tongChi += conBGD.getSoTienGiaoDich();
                            }
                        }else if(thang < thangKetThuc && thang > thangBatDau){
                            tongChi += conBGD.getSoTienGiaoDich();
                        }else if(thang == thangBatDau){
                            if(ngay > ngayBatDau){
                                tongChi += conBGD.getSoTienGiaoDich();
                            }
                        }else if(thang == thangKetThuc){
                            if(ngay < ngayKetThuc || ngay == ngayKetThuc){
                                tongChi += conBGD.getSoTienGiaoDich();
                            }
                        }
                    }else if(nam < namKetThuc && nam > namBatDau){
                        tongChi += conBGD.getSoTienGiaoDich();
                    }else if(nam == namKetThuc){
                        if(thang < thangKetThuc){
                            tongChi += conBGD.getSoTienGiaoDich();
                        }else if(thang == thangKetThuc){
                            if((ngay < ngayKetThuc) || (ngay == ngayKetThuc)){
                                tongChi += conBGD.getSoTienGiaoDich();
                            }
                        }
                    }else if(nam == namBatDau){
                        if(thang > thangBatDau){
                            tongChi += conBGD.getSoTienGiaoDich();
                        }else if(thang == thangBatDau){
                            if((ngay > ngayBatDau) || (ngay == ngayBatDau)){
                                tongChi += conBGD.getSoTienGiaoDich();
                            }
                        }
                    }
                }
                if(ma1 == ma2 && conKTC.getLoaiKhoan().equals("thu")){
                    int nam = Integer.parseInt(conBGD.getNgayGiaoDich().substring(6));
                    int thang = Integer.parseInt(conBGD.getNgayGiaoDich().substring(3, 5));
                    int ngay = Integer.parseInt(conBGD.getNgayGiaoDich().substring(0, 2));

                    if(nam == namKetThuc && nam == namBatDau){
                        if(thang == thangBatDau && thang == thangKetThuc){
                            if(((ngay == ngayBatDau) || (ngay > ngayBatDau)) && ((ngay == ngayKetThuc) || (ngay < ngayKetThuc))){
                                tongThu += conBGD.getSoTienGiaoDich();
                            }
                        }else if(thang < thangKetThuc && thang > thangBatDau){
                            tongThu += conBGD.getSoTienGiaoDich();
                        }else if(thang == thangBatDau){
                            if(ngay > ngayBatDau){
                                tongThu += conBGD.getSoTienGiaoDich();
                            }
                        }else if(thang == thangKetThuc){
                            if(ngay < ngayKetThuc || ngay == ngayKetThuc){
                                tongThu += conBGD.getSoTienGiaoDich();
                            }
                        }
                    }else if(nam < namKetThuc && nam > namBatDau){
                        tongThu += conBGD.getSoTienGiaoDich();
                    }else if(nam == namKetThuc){
                        if(thang < thangKetThuc){
                            tongThu += conBGD.getSoTienGiaoDich();
                        }else if(thang == thangKetThuc){
                            if((ngay < ngayKetThuc) || (ngay == ngayKetThuc)){
                                tongThu += conBGD.getSoTienGiaoDich();
                            }
                        }
                    }else if(nam == namBatDau){
                        if(thang > thangBatDau){
                            tongThu += conBGD.getSoTienGiaoDich();
                        }else if(thang == thangBatDau){
                            if((ngay > ngayBatDau) || (ngay == ngayBatDau)){
                                tongThu += conBGD.getSoTienGiaoDich();
                            }
                        }
                    }
                }
            }
        }

        tvNoiDung.setText("Tổng chi : " + tongChi +"\n" +
                "Tổng thu : " + tongThu + "\n" +
                "Chênh lệch : " + (tongThu - tongChi));

    }
//    public void setOnClick(){
////truy cập database
//        DBHelper dbHelper = new DBHelper(getContext());
//        KhoanThuChiDAO thuChiDAO = new KhoanThuChiDAO(dbHelper.getWritableDatabase());
//        BangGiaoDichDAO giaoDichDAO = new BangGiaoDichDAO(dbHelper.getWritableDatabase());
//
//        final List listMaKhoanGiaoDich = giaoDichDAO.readAll();
//        final List listMaKhoanThuChi = thuChiDAO.readAll();
//        //set sự kiện cho nút thống kê ngày
//        butTongNgay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                View mview = getActivity().getLayoutInflater().inflate(R.layout.thong_ke_ngay,null);
//                edtNhapNgay = mview.findViewById(R.id.edtThongKeNgay);
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setMessage("Nhập ngày tháng năm muốn thống kê")
//                        .setView(mview)
//                        .setPositiveButton("Thống kê ngày này đi", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //lấy thông tin người dùng nhập
//                                String a = edtNhapNgay.getText().toString();
//                                for(int i = 0; i < listMaKhoanGiaoDich.size(); i++){
//                                    //lấy mã khoản của bảng giao dịch
//                                    BangGiaoDich conBGD = (BangGiaoDich) listMaKhoanGiaoDich.get(i);
//                                    int ma1 = conBGD.getMaKhoan();
//                                    for(int j = 0; j < listMaKhoanThuChi.size(); j++){
//                                        //lấy mã khoản của bảng thu chi
//                                        KhoanThuChi conKTC = (KhoanThuChi) listMaKhoanThuChi.get(j);
//                                        int ma2 = conKTC.getMaKhoan();
//                                        //so sánh cùng mã khoản, cùng loại chi và có ngày giao dịch mà người dùng cần
//                                        if(ma1 == ma2 && conKTC.getLoaiKhoan().equals("chi") && conBGD.getNgayGiaoDich().equals(a)){
//                                            tongChi += conBGD.getSoTienGiaoDich();
//                                        }
//                                        if(ma1 == ma2 && conKTC.getLoaiKhoan().equals("thu") && conBGD.getNgayGiaoDich().equals(a)){
//                                            tongThu += conBGD.getSoTienGiaoDich();
//                                        }
//                                    }
//                                }
//                                //nếu có chi tiêu trong ngày đó thì tính tổng thu chi
//                                if(tongChi != 0 || tongThu != 0){
//                                    tvNoiDung.setText("Tổng thu trong ngày là :" + tongThu + "" +
//                                            "\n\nTổng chi trong ngày là :" + tongChi + "" +
//                                            "\n\nSố tiền còn lại là :" + (tongThu - tongChi));
//                                    if(tongThu - tongChi > 0){
//                                        Toast.makeText(getContext(), "Chi tiêu hợp lý đó !!!", Toast.LENGTH_LONG).show();
//                                    }else{
//                                        Toast.makeText(getContext(), "Cân nhắc lại khi chi tiêu nhé !!!", Toast.LENGTH_LONG).show();
//                                    }
//                                }else{
//                                    Toast.makeText(getContext(), "Không có ngày cần thống kê", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        })
//                        .setNegativeButton("Thôi", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
////                AlertDialog alertDialog = builder.create();
////                alertDialog.show();
//                builder.show();
//            }
//        });
//        //set sự kiện cho nút thống kê theo tháng
//        butTongThang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                View nview = getLayoutInflater().inflate(R.layout.thong_ke_thang,null);
//                edtNhapThang = nview.findViewById(R.id.edtThongKeThang);
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setMessage("Nhập tháng năm muốn thống kê")
//                        .setView(nview)
//                        .setPositiveButton("Thống kê tháng này đi", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //lấy dữ liệu của người dùng nhập vào
//                                String a = edtNhapThang.getText().toString();
//                                for(int i = 0; i < listMaKhoanGiaoDich.size(); i++){
//                                    //lấy mã khoản của bảng giao dịch
//                                    BangGiaoDich conBGD = (BangGiaoDich) listMaKhoanGiaoDich.get(i);
//                                    int ma1 = conBGD.getMaKhoan();
//                                    for(int j = 0; j < listMaKhoanThuChi.size(); j++){
//                                        //lấy mã khoản của bảng thu chi
//                                        KhoanThuChi conKTC = (KhoanThuChi) listMaKhoanThuChi.get(j);
//                                        int ma2 = conKTC.getMaKhoan();
//                                        //so sánh cùng mã khoản, cùng loại chi và có tháng giao dịch mà người dùng cần
//                                        if(ma1 == ma2 && conKTC.getLoaiKhoan().equals("chi") && conBGD.getNgayGiaoDich().substring(3).equals(a)){
//                                            tongChi += conBGD.getSoTienGiaoDich();
//                                        }
//                                        if(ma1 == ma2 && conKTC.getLoaiKhoan().equals("thu") && conBGD.getNgayGiaoDich().substring(3).equals(a)){
//                                            tongThu += conBGD.getSoTienGiaoDich();
//                                        }
//                                    }
//                                }
//                                //nếu có chi tiêu trong ngày đó thì tính tổng thu chi
//                                if(tongChi != 0 || tongThu != 0){
//                                    tvNoiDung.setText("Tổng thu trong tháng là :" + tongThu + "" +
//                                            "\n\nTổng chi trong tháng là :" + tongChi + "" +
//                                            "\n\nSố tiền còn lại là :" + (tongThu - tongChi));
//                                    if(tongThu - tongChi > 0){
//                                        Toast.makeText(getContext(), "Chi tiêu hợp lý đó !!!", Toast.LENGTH_LONG).show();
//                                    }else{
//                                        Toast.makeText(getContext(), "Cân nhắc lại khi chi tiêu nhé !!!", Toast.LENGTH_LONG).show();
//                                    }
//                                }else{
//                                    Toast.makeText(getContext(), "Không có ngày cần thống kê", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        })
//                        .setNegativeButton("Thôi", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//            }
//        });
//
//        butTongNam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                View oview = getLayoutInflater().inflate(R.layout.thong_ke_nam,null);
//                edtNhapNam = oview.findViewById(R.id.edtThongKeNam);
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setMessage("Nhập năm muốn thống kê")
//                        .setView(oview)
//                        .setPositiveButton("Thống kê năm này đi", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //lấy dữ liệu người dùng nhập vào
//                                String a = edtNhapNam.getText().toString();
//                                for(int i = 0; i < listMaKhoanGiaoDich.size(); i++){
//                                    //lấy mã khoản của bảng giao dịch
//                                    BangGiaoDich conBGD = (BangGiaoDich) listMaKhoanGiaoDich.get(i);
//                                    int ma1 = conBGD.getMaKhoan();
//                                    for(int j = 0; j < listMaKhoanThuChi.size(); j++){
//                                        //lấy mã khoản của khoản thu chi
//                                        KhoanThuChi conKTC = (KhoanThuChi) listMaKhoanThuChi.get(j);
//                                        int ma2 = conKTC.getMaKhoan();
//                                        //so sánh cùng mã khoản, cùng loại chi và có năm giao dịch mà người dùng cần
//                                        if(ma1 == ma2 && conKTC.getLoaiKhoan().equals("chi") && conBGD.getNgayGiaoDich().substring(6).equals(a)){
//                                            tongChi += conBGD.getSoTienGiaoDich();
//                                        }
//                                        if(ma1 == ma2 && conKTC.getLoaiKhoan().equals("thu") && conBGD.getNgayGiaoDich().substring(6).equals(a)){
//                                            tongThu += conBGD.getSoTienGiaoDich();
//                                        }
//                                    }
//                                }
//                                //nếu có chi tiêu trong ngày đó thì tính tổng thu chi
//                                if(tongChi != 0 || tongThu != 0){
//                                    tvNoiDung.setText("Tổng thu trong năm là :" + tongThu + "" +
//                                            "\n\nTổng chi trong năm là :" + tongChi + "" +
//                                            "\n\nSố tiền còn lại là :" + (tongThu - tongChi));
//                                    if(tongThu - tongChi > 0){
//                                        Toast.makeText(getContext(), "Chi tiêu hợp lý đó !!!", Toast.LENGTH_LONG).show();
//                                    }else{
//                                        Toast.makeText(getContext(), "Cân nhắc lại khi chi tiêu nhé !!!", Toast.LENGTH_LONG).show();
//                                    }
//                                }else{
//                                    Toast.makeText(getContext(), "Không có ngày cần thống kê", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        })
//                        .setNegativeButton("Thôi", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//            }
//        });
//
//
//    }

}
