package vn.skymapglobal.checkingapp.HomePackage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.realm.RealmResults;
import vn.skymapglobal.checkingapp.Object.User;

public class User_SharedViewModel extends ViewModel {
    private final MutableLiveData<RealmResults<User>> dataLiveData = new MutableLiveData<>();

    // Phương thức để cập nhật LiveData
    public void setData(RealmResults<User> newData) {
        dataLiveData.setValue(newData);
    }

    // Phương thức để lấy LiveData
    public LiveData<RealmResults<User>> getData() {
        return dataLiveData;
    }
}