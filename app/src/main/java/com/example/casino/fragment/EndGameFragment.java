package com.example.casino.fragment;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.casino.MainActivity;
import com.example.casino.R;
import com.example.casino.viewmodel.CasinoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EndGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EndGameFragment extends Fragment {
    private CasinoViewModel userViewModel;

    public EndGameFragment() {
        // Required empty public constructor
    }
    public static EndGameFragment newInstance() {
        EndGameFragment fragment = new EndGameFragment();
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
        View view = inflater.inflate(R.layout.fragment_end_game, container, false);

        Process_View_WinLose_LiveData(view);
        Process_View_MoneyBet_LiveData(view);
        Process_View_Button(view);

        return view;
    }

    private void Process_View_WinLose_LiveData(View view) {;
        userViewModel.getLinkWinLoseLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String str) {
                ImageView imageRank = (ImageView)view.findViewById(R.id.image_win_lose);
                String link = "android.resource://" + getContext().getPackageName() + "/drawable/" + str;
                Uri imgUri = Uri.parse(link);
                imageRank.setImageURI(imgUri);
            }
        });
    }

    private void Process_View_MoneyBet_LiveData(View view) {
        userViewModel.getMoneyBetLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer value) {
                TextView textView = view.findViewById(R.id.textview_moneybet);
                String moneyBetInfo;
                if (value >= 0) {
                    moneyBetInfo = "+" + value + " $";
                    textView.setTextColor(Color.rgb(0,200,0));
                }
                else {
                    moneyBetInfo = value + " $";
                    textView.setTextColor(Color.rgb(200,0,0));
                }
                textView.setText(moneyBetInfo);
            }
        });
    }

    private void Process_View_Button(View view) {
        // Set button play again
        Button btnPlayAgain = (Button) view.findViewById(R.id.btn_play_again);
        btnPlayAgain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userViewModel.Process_EI_Button_Play_Again();
                navigateToGameBackStack();
            }
        });

        // Set button out table
        Button btnOutTable = (Button) view.findViewById(R.id.btn_out_table);
        btnOutTable.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userViewModel.Process_EI_Button_Out_Table();
                navigateToClientMenuBackStack();
            }
        });
    }

    private void navigateToGameBackStack(){
        NavHostFragment.findNavController(this).popBackStack();
    }

    private void navigateToClientMenuBackStack(){
        NavHostFragment.findNavController(this).popBackStack(R.id.clientMenuFragment, false);
    }
}