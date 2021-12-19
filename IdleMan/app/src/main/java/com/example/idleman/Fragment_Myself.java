package com.example.idleman;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//import com.example.myteamapplication.Activity.MainActivity;
//import com.example.myteamapplication.Activity.SendActivity;
//import com.example.myteamapplication.R;

public class Fragment_Myself extends Fragment implements View.OnClickListener {

    private FragmentTransaction transaction;
    private FragmentManager manager;
    private RadioButton my_tab_send,my_tab_receive;
    private Context MainActivity;
    private LayoutInflater inflater;

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        MainActivity = getActivity();
//        inflater = LayoutInflater.from(getActivity());
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = getChildFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container_my,new com.example.idleman.Fragment_Myself_Send());
        transaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_myself, container, false);
        my_tab_send = rootView.findViewById(R.id.my_tab_send);
        my_tab_receive = rootView.findViewById(R.id.my_tab_receive);
        my_tab_send.setOnClickListener(this);
        my_tab_receive.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        transaction = manager.beginTransaction();
        switch (v.getId()){
            case R.id.my_tab_send:
                transaction.replace(R.id.fragment_container_my,new com.example.idleman.Fragment_Myself_Send());
                break;
            case R.id.my_tab_receive:
                transaction.replace(R.id.fragment_container_my,new Fragment_Message());
                break;
        }
        transaction.commit();
    }
}
