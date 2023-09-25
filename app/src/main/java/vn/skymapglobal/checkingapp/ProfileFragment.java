package vn.skymapglobal.checkingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import vn.skymapglobal.checkingapp.CheckLogPackage.Check_log_SharedViewModel;
import vn.skymapglobal.checkingapp.Object.Check_log;
import vn.skymapglobal.checkingapp.Object.User;

public class ProfileFragment extends Fragment {
    private void updateDataInRealm() {
        // Thực hiện cập nhật dữ liệu trong Realm
        Bundle bundle = getArguments();
        String username = bundle.getString("username");
        User user = realm.where(User.class)
                .equalTo("username", username)
                .findFirst();
        RealmResults<Check_log> Checkloglist = realm.where(Check_log.class)
                .equalTo("userid",user.getId())
                .findAll().sort("time", Sort.DESCENDING);
        checklogSharedViewModel.setData(Checkloglist);
    }

    private boolean isCheckedIn = false;
    private TextView UserIdTextview, UsernameTextview, FullnameTextview;
    private Button checkinBtn, checkOutBtn;
    private Check_log_SharedViewModel checklogSharedViewModel;
    private Realm realm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checklogSharedViewModel = new ViewModelProvider(requireActivity()).get(Check_log_SharedViewModel.class);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_profile, container, false);
        UserIdTextview = view.findViewById(R.id.userid);
        UsernameTextview = view.findViewById(R.id.username);
        FullnameTextview = view.findViewById(R.id.fullname);
        checkinBtn = view.findViewById(R.id.check_btn);
        checkOutBtn = view.findViewById(R.id.checkout_btn);

        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        String username = bundle.getString("username");
        realm = Realm.getDefaultInstance();
        User user = realm.where(User.class)
                .equalTo("username", username)
                .findFirst();
        String IdString = String.format("ID: %d",user.getId());
        String UsernamString = String.format("Username: %s",user.getUsername());
        String FullnameString = String.format("Fullname: %s",user.getFullname());
        UserIdTextview.setText(IdString);
        UsernameTextview.setText(UsernamString);
        FullnameTextview.setText(FullnameString);




        checkinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheckedIn = true;
                updateButtonState();
                long nextId = getNextId();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Check_log newlog = realm.createObject(Check_log.class,nextId);
                        newlog.setTime(getCurrentTime());
                        newlog.setUserid(user.getId());
                        newlog.setCheckTypeId(1);

                    }
                });
                updateDataInRealm();
                realm.close();
                Toast.makeText(getActivity(), "Checkin thành công", Toast.LENGTH_SHORT).show();
                checkinBtn.setText("Đã checkin");
                checkOutBtn.setText("CHECKOUT");
            }
        });
        //End checkin action



        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheckedIn = false;
                updateButtonState();
                long nextId = getNextId();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Check_log newlog = realm.createObject(Check_log.class,nextId);
                        newlog.setTime(getCurrentTime());
                        newlog.setUserid(user.getId());
                        newlog.setCheckTypeId(2);

                    }
                });
                updateDataInRealm();
                realm.close();
                Toast.makeText(getActivity(), "CheckOut thành công", Toast.LENGTH_SHORT).show();
                checkOutBtn.setText("Đã checkOut");
                checkinBtn.setText("CHECKIN");



            }
        });
        //End checkOut action
        updateButtonState();



        return view;
    }


    //các function

    private void updateButtonState() {

        if (isCheckedIn) {
            // Nếu đã check-in, khóa nút "Check In" và mở nút "Check Out"
            checkinBtn.setEnabled(false);
            checkOutBtn.setEnabled(true);
        } else {
            // Nếu chưa check-in, mở nút "Check In" và khóa nút "Check Out"
            checkinBtn.setEnabled(true);
            checkOutBtn.setEnabled(false);
        }
    }
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
    private long getNextId() {
        Realm realm = Realm.getDefaultInstance();
        Number currentMaxId = realm.where(Check_log.class).max("id");
        return currentMaxId == null ? 1 : currentMaxId.longValue() + 1;
    }
}