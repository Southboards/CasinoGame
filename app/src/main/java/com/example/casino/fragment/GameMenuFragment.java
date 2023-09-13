package com.example.casino.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.casino.MainActivity;
import com.example.casino.R;
import com.example.casino.viewmodel.CasinoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameMenuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TYPE_GAME_KEY = "type game";

    // TODO: Rename and change types of parameters
    private String mTypeGame;

    private CasinoViewModel userViewModel;

    public GameMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment GameMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameMenuFragment newInstance(String param1) {
        GameMenuFragment fragment = new GameMenuFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_GAME_KEY, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTypeGame = getArguments().getString(TYPE_GAME_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity)getActivity();
        userViewModel = mainActivity.getUserViewModel();
        View view = inflater.inflate(R.layout.fragment_game_menu,
                container, false);

        Process_View_Button(view);

        return view;
    }

    private void Process_View_Button(View view) {
        // Set button three cards
        Button btnThreeCards = (Button) view.findViewById(R.id.button_three_cards);
        btnThreeCards.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userViewModel.Process_EI_Button_Three_Cards_Game();
                navigateToThreeCardsFragment();
            }
        });

        // Set button poker
        Button btnPoker = (Button) view.findViewById(R.id.button_poker);
        btnPoker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userViewModel.Process_EI_Button_Poker_Game();
                navigateToPokerFragment();
            }
        });
    }

    private void navigateToThreeCardsFragment(){
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(
                R.id.action_gameMenuFragment_to_threeCardsFragment,
                null,
                new NavOptions.Builder()
                        .setEnterAnim(android.R.animator.fade_in)
                        .setExitAnim(android.R.animator.fade_out)
                        .build()
        );
    }

    private void navigateToPokerFragment(){
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(
                R.id.action_gameMenuFragment_to_pokerFragment,
                null,
                new NavOptions.Builder()
                        .setEnterAnim(android.R.animator.fade_in)
                        .setExitAnim(android.R.animator.fade_out)
                        .build()
        );
    }
}