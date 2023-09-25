package vn.skymapglobal.checkingapp.Object;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Check_log extends RealmObject {
    @PrimaryKey
    private long id;
    private long userid;
    private String time;
    private long checkTypeId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getCheckTypeId() {
        return checkTypeId;
    }

    public void setCheckTypeId(long checkTypeId) {
        this.checkTypeId = checkTypeId;
    }
}