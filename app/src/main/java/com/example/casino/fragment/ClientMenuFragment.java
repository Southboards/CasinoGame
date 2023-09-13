package com.example.casino.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.casino.MainActivity;
import com.example.casino.R;
import com.example.casino.viewmodel.CasinoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientMenuFragment extends Fragment {

    private CasinoViewModel userViewModel;

    public ClientMenuFragment() {
        // Required empty public constructor
    }

    public static ClientMenuFragment newInstance() {
        ClientMenuFragment fragment = new ClientMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        MainActivity mainActivity = (MainActivity)getActivity();
        userViewModel = mainActivity.getUserViewModel();

        // Set image rank
        View view = inflater.inflate(R.layout.fragment_client_menu, container, false);
        Process_View_LinkRank_LiveData(view);
        Process_View_MoneyPlayer_LiveData(view);

        Process_View_Button(view);

        return view;
    }

    private void Process_View_LinkRank_LiveData(View view){
        userViewModel.getLinkRankLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String str) {
                ImageView imageRank = (ImageView)view.findViewById(R.id.image_rank);
                String link = "android.resource://" + getContext().getPackageName() + "/drawable/" + str;
                Uri imgUri = Uri.parse(link);
                imageRank.setImageURI(imgUri);
            }
        });
    }

    private void Process_View_MoneyPlayer_LiveData(View view){
        userViewModel.getMoneyPlayerLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer moneyValue) {
                TextView textView = (TextView)view.findViewById(R.id.textview_budget_number);
                textView.setText(moneyValue.toString());
            }
        });
    }

    private void Process_View_Button(View view) {
        // Set button play
        Button btnPlay = (Button) view.findViewById(R.id.button_play);
        btnPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                navigateToClientGameFragment();
            }
        });

        // Set button log out
        Button btnLogout = (Button) view.findViewById(R.id.button_logout);
        btnLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                userViewModel.sendMessageLoginRegister("NONE");
                navigateToMainFragmentBackStack();
            }
        });
    }

    private void navigateToClientGameFragment(){
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(
                R.id.action_clientMenuFragment_to_gameMenuFragment,
                null,
                new NavOptions.Builder()
                        .setEnterAnim(android.R.animator.fade_in)
                        .setExitAnim(android.R.animator.fade_out)
                        .build()
        );
    }

    private void navigateToMainFragmentBackStack(){
        NavHostFragment.findNavController(this).popBackStack(R.id.mainFragment, false);
    }
}