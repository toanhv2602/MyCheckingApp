package vn.skymapglobal.checkingapp.CheckLogPackage;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;
import vn.skymapglobal.checkingapp.Object.Check_log;
import vn.skymapglobal.checkingapp.Object.Check_type;
import vn.skymapglobal.checkingapp.R;
import vn.skymapglobal.checkingapp.Object.User;

public class Check_log_RecylerAdapter extends RecyclerView.Adapter<Check_log_RecylerAdapter.ViewHolder>{
    private RealmResults<Check_log> data;
    private Long userid;

    public void setData(RealmResults<Check_log> data) {
        this.data = data;
    }

    public Check_log_RecylerAdapter(RealmResults<Check_log> data){
        this.data = data;

    }
    @NonNull
    @Override
    public Check_log_RecylerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_log_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Check_log_RecylerAdapter.ViewHolder holder, int position) {
        Check_log item = data.get(position);
        if (item == null){
            return;
        }

        if ( item.getCheckTypeId() == 1 ){
            holder.itemView.setBackgroundColor(Color.parseColor("#00ff00"));
        }
        else if ( item.getCheckTypeId() == 2 ){
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFF00"));
        }
        Realm realm = Realm.getDefaultInstance();

            // Lấy userId từ bản ghi log
            long userId = item.getUserid();

            // Truy vấn thông tin người dùng (bao gồm username) từ bảng user
            User user = realm.where(User.class)
                    .equalTo("id", userId)
                    .findFirst();
            Check_type checkType = realm.where(Check_type.class)
                                    .equalTo("id",item.getCheckTypeId())
                                    .findFirst();
            String idString = String.format("ID: %s", user.getId());
            holder.userid.setText(idString);
            holder.username.setText(user.getUsername());
            if (checkType != null){
                holder.checktype.setText(checkType.getName());
            }

            holder.time.setText(item.getTime());


        realm.close();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username, checktype, time, userid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userid = itemView.findViewById(R.id.userid);
            username = itemView.findViewById(R.id.username);
            checktype = itemView.findViewById(R.id.check_type);
            time = itemView.findViewById(R.id.time_item);
        }
    }
}
