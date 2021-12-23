package com.example.idleman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;

public class Fragment_SignupTab extends Fragment {
    EditText account,username,password,confirm;
    Button signup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_signup, container, false);
        account = root.findViewById(R.id.accountnum);
        username = root.findViewById(R.id.username);
        password = root.findViewById(R.id.pass);
        confirm = root.findViewById(R.id.confirm);

        account.setTranslationY(800);
        username.setTranslationY(800);
        password.setTranslationY(800);
        confirm.setTranslationY(800);

        account.setAlpha(0);
        username.setAlpha(0);
        password.setAlpha(0);
        confirm.setAlpha(0);

        account.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        username.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        password.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        confirm.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(700).start();

        return root;
    }
}
