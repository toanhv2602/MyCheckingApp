package vn.skymapglobal.checkingapp.Login_Regis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;
import io.realm.RealmResults;
import vn.skymapglobal.checkingapp.R;
import vn.skymapglobal.checkingapp.Object.User;

public class RegisterActivity extends AppCompatActivity {
    private EditText UsernameEditText, FullnameEditText, PasswordEditText, PasswordConfirmEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UsernameEditText = findViewById(R.id.Username_box_register);
        FullnameEditText = findViewById(R.id.Fullname_box_resgister);
        PasswordEditText = findViewById(R.id.Password_box_resgister);
        PasswordConfirmEditText = findViewById(R.id.Password_box_resgister_confirm);
        registerButton = findViewById(R.id.registerButton);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Newusername = UsernameEditText.getText().toString();
                String Newpassword = PasswordEditText.getText().toString();
                String PasswordConfirm = PasswordConfirmEditText.getText().toString();
                String Fullname = FullnameEditText.getText().toString();
                if (Newusername.isEmpty() || Newpassword.isEmpty() || PasswordConfirm.isEmpty()) {
                    // Thiếu thông tin đăng ký
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin đăng ký.", Toast.LENGTH_SHORT).show();
                }
                else if (isUsernameTaken(Newusername)) {
                   Toast.makeText(RegisterActivity.this,"Tên đăng nhập đã tồn tại",Toast.LENGTH_SHORT).show();
                }
                else if (Newpassword.equals(PasswordConfirm)){
                    //Thêm người dùng vào cơ sở dữ liệu
                    Realm realm = Realm.getDefaultInstance();
                    long nextId = getNextUserId();
                    User newuser = new User();
                    newuser.setId(nextId);
                    newuser.setUsername(Newusername);
                    newuser.setPassword(Newpassword);
                    newuser.setFullname(Fullname);

                    realm.beginTransaction();
                    realm.copyToRealm(newuser);
                    realm.commitTransaction();
                    realm.close();


                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
    private long getNextUserId() {
        Realm realm = Realm.getDefaultInstance();
        Number currentMaxId = realm.where(User.class).max("id");
        return currentMaxId == null ? 1 : currentMaxId.longValue() + 1;
    }
    private boolean isUsernameTaken(String username) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> users = realm.where(User.class)
                .equalTo("username", username)
                .findAll();
        return !users.isEmpty();
    }
}