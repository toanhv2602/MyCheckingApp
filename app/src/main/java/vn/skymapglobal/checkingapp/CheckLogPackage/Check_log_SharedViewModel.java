package vn.skymapglobal.checkingapp.CheckLogPackage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.realm.RealmResults;
import vn.skymapglobal.checkingapp.Object.Check_log;

public class Check_log_SharedViewModel extends ViewModel {
    private final MutableLiveData<RealmResults<Check_log>> dataLiveData = new MutableLiveData<>();

    // Phương thức để cập nhật LiveData
    public void setData(RealmResults<Check_log> newData) {
        dataLiveData.setValue(newData);
    }

    // Phương thức để lấy LiveData
    public LiveData<RealmResults<Check_log>> getData() {
        return dataLiveData;
    }
}
