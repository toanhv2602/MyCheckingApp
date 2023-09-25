package vn.skymapglobal.checkingapp.HomePackage;

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
import vn.skymapglobal.checkingapp.R;
import vn.skymapglobal.checkingapp.Object.User;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private User_SharedViewModel userSharedViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSharedViewModel = new ViewModelProvider(requireActivity()).get(User_SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.userRecylerview);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> Userlist = realm.where(User.class).findAll();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserRecylerAdapter userRecylerAdapter = new UserRecylerAdapter(Userlist);
        recyclerView.setAdapter(userRecylerAdapter);
        userSharedViewModel.getData().observe(getViewLifecycleOwner(), newData -> {
            userRecylerAdapter.setData(newData);
            userRecylerAdapter.notifyDataSetChanged();
        });
        realm.close();
        // Inflate the layout for this fragment
        return view;
    }
}