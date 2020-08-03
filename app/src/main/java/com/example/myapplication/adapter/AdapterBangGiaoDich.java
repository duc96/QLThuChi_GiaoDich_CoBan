package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.BangGiaoDich;

import java.util.ArrayList;

public class AdapterBangGiaoDich extends ArrayAdapter {

    Context context;
    ArrayList<BangGiaoDich> listGD;

    public AdapterBangGiaoDich(@NonNull Context context, int resource, ArrayList<BangGiaoDich> listGD) {
        super(context, resource, listGD);
        this.context = context;
        this.listGD = listGD;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.row_bang_giao_dich,null);
        }
        //map cac item trong listView Bang Giao Dich
        TextView tvMaGD = convertView.findViewById(R.id.tvMaGD);
        TextView tvNgayGD = convertView.findViewById(R.id.tvNgayGD);
        TextView tvSoTien = convertView.findViewById(R.id.tvSoTien);
        TextView tvMaKhoan = convertView.findViewById(R.id.tvMaKhoan);
        TextView tvMoTa = convertView.findViewById(R.id.tvMoTa);

        //set du lieu cho listView Bang Giao Dich
        tvMaGD.setText(listGD.get(position).getMaGiaoDich()+"");
        tvNgayGD.setText(listGD.get(position).getNgayGiaoDich()+"");
        tvSoTien.setText(listGD.get(position).getSoTienGiaoDich()+"");
        tvMaKhoan.setText(listGD.get(position).getMaKhoan()+"");
        tvMoTa.setText(listGD.get(position).getMoTa()+"");
        return convertView;
    }

}
