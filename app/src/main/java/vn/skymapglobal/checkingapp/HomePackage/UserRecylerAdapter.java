package vn.skymapglobal.checkingapp.HomePackage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;
import vn.skymapglobal.checkingapp.Object.Check_log;
import vn.skymapglobal.checkingapp.R;
import vn.skymapglobal.checkingapp.Object.User;

public class UserRecylerAdapter extends RecyclerView.Adapter<UserRecylerAdapter.ViewHolder> {
    private RealmResults<User> data;
    public UserRecylerAdapter(RealmResults<User> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void setData(RealmResults<User> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User item = data.get(position);
        if (item == null){
            return;
        }
        holder.username.setText(item.getUsername());
        holder.fullname.setText(item.getFullname());
        holder.password.setText(item.getPassword());
        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                User user = realm.where(User.class)
                        .equalTo("username",item.getUsername())
                        .findFirst();
                RealmResults<Check_log> checkLogs = realm.where(Check_log.class)
                        .equalTo("userid",item.getId())
                        .findAll();
                realm.beginTransaction();
                user.deleteFromRealm();
                checkLogs.deleteAllFromRealm();
                realm.commitTransaction();
                notifyDataSetChanged();
                realm.close();
            }
        });
        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                Edit_user_Fragment fragment = new Edit_user_Fragment();

                //truyền dữ liệu qua bundle
                Bundle bundle = new Bundle();
                bundle.putString("userName", item.getUsername());
                fragment.setArguments(bundle);
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.homelist, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username,fullname,password;
        public Button editbtn,delbtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            fullname = itemView.findViewById(R.id.fullname);
            password = itemView.findViewById(R.id.password);
            editbtn = itemView.findViewById(R.id.editbtn);
            delbtn = itemView.findViewById(R.id.delbtn);

        }
    }
}
