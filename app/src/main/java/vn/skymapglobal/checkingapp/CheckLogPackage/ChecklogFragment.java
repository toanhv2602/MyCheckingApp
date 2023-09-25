package vn.skymapglobal.checkingapp.CheckLogPackage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import vn.skymapglobal.checkingapp.Object.Check_log;
import vn.skymapglobal.checkingapp.R;
import vn.skymapglobal.checkingapp.Object.User;


public class ChecklogFragment extends Fragment {
    private RecyclerView recyclerView;
    private Check_log_RecylerAdapter checkLogRecylerAdapter;
    private Check_log_SharedViewModel checklogSharedViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checklogSharedViewModel = new ViewModelProvider(requireActivity()).get(Check_log_SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_checklog, container, false);

        Bundle bundle = getArguments();
        String username = bundle.getString("username");
        recyclerView = view.findViewById(R.id.check_log_recylerview);
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class)
                .equalTo("username", username)
                .findFirst();
        RealmResults<Check_log> Checkloglist = realm.where(Check_log.class)
                                                .equalTo("userid",user.getId())
                                                .findAll().sort("time", Sort.DESCENDING);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        checkLogRecylerAdapter = new Check_log_RecylerAdapter(Checkloglist);
        recyclerView.setAdapter(checkLogRecylerAdapter);
        checklogSharedViewModel.getData().observe(getViewLifecycleOwner(), newData -> {
            checkLogRecylerAdapter.setData(newData);
            checkLogRecylerAdapter.notifyDataSetChanged();
        });

        realm.close();
        return view;
    }



}