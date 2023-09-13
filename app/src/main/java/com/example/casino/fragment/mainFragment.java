package com.example.casino.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.casino.MainActivity;
import com.example.casino.R;
import com.example.casino.viewmodel.CasinoViewModel;

public class mainFragment extends Fragment {

    private String mUsername;
    private String mPassword;

    private CasinoViewModel userViewModel;

    public static mainFragment newInstance() {
        mainFragment fragment = new mainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public mainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,
                container, false);

        MainActivity mainActivity = (MainActivity)getActivity();
        userViewModel = mainActivity.getUserViewModel();
        Process_View_Login_Register_LiveData();
        Process_View_Button(view);


        return view;
    }

    private void Process_View_Login_Register_LiveData(){
        userViewModel.getMessageLoginRegisterLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String str) {
                if (str == "LOGIN SUCCESS") {
                    navigateToClientMenuFragment();
                } else if (str == "LOGIN FALSE") {
                    Toast toast = Toast.makeText(requireActivity(), "Login Fail", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (str == "REGISTER SUCCESS") {
                    navigateToClientMenuFragment();
                } else if (str == "REGISTER FALSE") {
                    Toast toast = Toast.makeText(requireActivity(), "Register Fail", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                }
            }
        });
    }

    private void Process_View_Button(View view) {
        // Button login
        Button btnLogin = (Button) view.findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userViewModel.Process_EI_Button_Login(mUsername, mPassword);
            }
        });

        // Button register
        Button btnRegister = (Button) view.findViewById(R.id.button_register);
        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userViewModel.Process_EI_Button_Register(mUsername, mPassword);
            }
        });
    }

    private void navigateToClientMenuFragment(){
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(
                R.id.action_mainFragment_to_clientMenuFragment,
                null,
                new NavOptions.Builder()
                        .setEnterAnim(android.R.animator.fade_in)
                        .setExitAnim(android.R.animator.fade_out)
                        .build()
        );
    }
}