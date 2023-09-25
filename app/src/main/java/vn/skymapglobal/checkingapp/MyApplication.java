package vn.skymapglobal.checkingapp;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import vn.skymapglobal.checkingapp.Object.Check_type;
import vn.skymapglobal.checkingapp.Object.User;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        // Khởi tạo Realm
        Realm.init(this);

        // Cấu hình Realm
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("myrealm.realm") // Tên tệp Realm
                .schemaVersion(1) // Phiên bản Realm
                .deleteRealmIfMigrationNeeded()
                .allowWritesOnUiThread(true)// Xóa dữ liệu nếu có sự cố trong quá trình cập nhật schema
                .build();
        Realm.setDefaultConfiguration(configuration);






    }
}