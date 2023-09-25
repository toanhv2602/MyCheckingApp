package vn.skymapglobal.checkingapp.HomePackage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import vn.skymapglobal.checkingapp.R;
import vn.skymapglobal.checkingapp.Object.User;


public class Edit_user_Fragment extends Fragment {
    private void updateDataInRealm() {
        // Thực hiện cập nhật dữ liệu trong Realm
        RealmResults<User> Userlist = realm.where(User.class).findAll();
        userSharedViewModel.setData(Userlist);
    }

    private EditText usernameEditText, passwordEditText, fullnameEditText;
    private TextView id;
    private Button updatebtn;
    private User_SharedViewModel userSharedViewModel;
    private Realm realm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSharedViewModel = new ViewModelProvider(requireActivity()).get(User_SharedViewModel.class);
        realm = Realm.getDefaultInstance();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user_, container, false);
        Bundle args = getArguments();
        usernameEditText =view.findViewById(R.id.username_edit);
        passwordEditText =view.findViewById(R.id.password_edit);
        fullnameEditText =view.findViewById(R.id.fullname_edit);
        id=view.findViewById(R.id.ID_label);
        updatebtn = view.findViewById(R.id.updatebtn);
        String username = args.getString("userName");
        realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).equalTo("username",username).findFirst();
        usernameEditText.setText(user.getUsername());
        passwordEditText.setText(user.getPassword());
        fullnameEditText.setText(user.getFullname());
        id.setText(Long.toString(user.getId()));
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                user.setUsername(usernameEditText.getText().toString());
                user.setFullname(fullnameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                realm.commitTransaction();
                updateDataInRealm();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }
}