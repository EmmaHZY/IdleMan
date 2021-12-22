package com.example.idleman;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import org.jetbrains.annotations.NotNull;

public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;
    public LoginAdapter(FragmentManager fm, Context context, int totalTabs){
        super(fm);
        this.context=context;
        this.totalTabs=totalTabs;
    }
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                Fragment_LoginTab fragment_loginTab =new Fragment_LoginTab();
                return fragment_loginTab;
            case 1:
                Fragment_SignupTab fragment_signupTab =new Fragment_SignupTab();
                return fragment_signupTab;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
