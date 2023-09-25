package vn.skymapglobal.checkingapp.Login_Regis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import vn.skymapglobal.checkingapp.MainActivity;
import vn.skymapglobal.checkingapp.Object.Check_type;
import vn.skymapglobal.checkingapp.R;
import vn.skymapglobal.checkingapp.Object.User;

public class LoginActivity extends AppCompatActivity {
    private EditText UsernameEditText ;
    private EditText PasswordEditText;
    private Button LoginButton ;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Ánh xạ các đối tượng view
        UsernameEditText=findViewById(R.id.Username_box);
        PasswordEditText=findViewById(R.id.Password_box);
        LoginButton=findViewById(R.id.Login_button);
        register=findViewById(R.id.dangky_btn);

        //Khởi tạo event click login
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = UsernameEditText.getText().toString();
                String password = PasswordEditText.getText().toString();

                Realm realm = Realm.getDefaultInstance();

                User user = realm.where(User.class)
                        .equalTo("username", username)
                        .equalTo("password",password)
                        .findFirst();
                if (user != null) {
                    // Đăng nhập thành công
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username",username);
                    startActivity(intent);



                } else {
                    // Đăng nhập thất bại
                    Toast.makeText(LoginActivity.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //Khởi tạo event click đăng ký
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Check_type> checkTypes = realm.where(Check_type.class).findAll();

        if (checkTypes.isEmpty()) {
            Check_type checkin = new Check_type();
            long nextId = getNextId();
            checkin.setId(nextId);
            checkin.setName("Checkin");
            realm.beginTransaction();
            realm.copyToRealm(checkin);
            realm.commitTransaction();


            Check_type checkout = new Check_type();
            long nextId1 = getNextId();
            checkout.setId(nextId1);
            checkout.setName("Checkout");
            realm.beginTransaction();
            realm.copyToRealm(checkout);
            realm.commitTransaction();
            realm.close();
        }



    }
    private long getNextId() {
        Realm realm = Realm.getDefaultInstance();
        Number currentMaxId = realm.where(Check_type.class).max("id");
        return currentMaxId == null ? 1 : currentMaxId.longValue() + 1;
    }
}