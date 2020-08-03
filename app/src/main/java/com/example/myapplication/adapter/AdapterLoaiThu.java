package com.example.myapplication.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.dao.KhoanThuChiDAO;
import com.example.myapplication.dbhelper.DBHelper;
import com.example.myapplication.model.KhoanThuChi;
import java.util.ArrayList;

public class AdapterLoaiThu extends RecyclerView.Adapter<AdapterLoaiThu.ViewHolder> {
    Context context;
    ArrayList<KhoanThuChi> ds;

    public AdapterLoaiThu(Context context, ArrayList<KhoanThuChi> ds) {
        this.context = context;
        this.ds = ds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_thu_chi,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        DBHelper dbHelper = new DBHelper(context);
        final KhoanThuChiDAO khoanThuChiDAO = new KhoanThuChiDAO(dbHelper.getWritableDatabase());
        holder.tvSTT.setText(ds.get(position).getMaKhoan()+"");
        holder.tvName.setText(ds.get(position).getTenKhoan());
        //bat su kien va xu ly item edit trong cardView
        holder.imEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View nho = LayoutInflater.from(context).inflate(R.layout.update_khoan_thu_chi,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Sửa cái này !!!")
                        .setView(nho)
                        .setPositiveButton("Sửa đi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText name = nho.findViewById(R.id.edUpdateTenKhoan);
                                ds.get(position).setTenKhoan(name.getText().toString());
                                khoanThuChiDAO.update(ds.get(position));
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Thôi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        //bat su kien va xu ly item delete trong cardView
        holder.imDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Xóa cái này nhé !!!")
                        .setPositiveButton("Xóa Luôn", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                khoanThuChiDAO.delete(ds.get(position).getMaKhoan());
                                ds.remove(ds.get(position));
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Không xóa đâu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvSTT;
        public TextView tvName;
        public ImageView imEdit;
        public ImageView imDelete;

        public ViewHolder(View view) {
            super(view);
            tvSTT = view.findViewById(R.id.tvStt);
            tvName = view.findViewById(R.id.tvName);
            imEdit = view.findViewById(R.id.imEdit);
            imDelete = view.findViewById(R.id.imDelete);
        }

    }
}

