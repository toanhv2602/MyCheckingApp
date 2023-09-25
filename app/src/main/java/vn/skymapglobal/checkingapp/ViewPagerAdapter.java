package vn.skymapglobal.checkingapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.skymapglobal.checkingapp.CheckLogPackage.ChecklogFragment;
import vn.skymapglobal.checkingapp.HomePackage.HomeFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {

    private Bundle dataBundle;
    public ViewPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public int getItemCount() {
        return 3; // Số lượng trang
    }
    public void setDataBundle(Bundle bundle) {
        this.dataBundle = bundle;
    }

    @Override
    public Fragment createFragment(int position) {

        // Tạo và trả về fragment tương ứng với vị trí (position)
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                ChecklogFragment fragment1 = new ChecklogFragment();
                fragment1.setArguments(dataBundle);
                return fragment1;
            case 2:
                ProfileFragment fragment = new ProfileFragment();
                fragment.setArguments(dataBundle);
                return fragment;
            default:
                return new HomeFragment();
        }

    }




}