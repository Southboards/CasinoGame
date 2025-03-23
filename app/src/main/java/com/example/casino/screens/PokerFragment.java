//package com.example.casino.fragment;
//
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.navigation.NavController;
//import androidx.navigation.NavOptions;
//import androidx.navigation.fragment.NavHostFragment;
//
//import android.os.Handler;
//import android.transition.Fade;
//import android.transition.Transition;
//import android.transition.TransitionManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.TranslateAnimation;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.casino.MainActivity;
//import com.example.casino.R;
//import com.example.casino.viewmodel.CasinoViewModel;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Vector;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link PokerFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class PokerFragment extends Fragment {
//
//    private CasinoViewModel userViewModel;
//    public PokerFragment() {
//        // Required empty public constructor
//    }
//
//    public static PokerFragment newInstance() {
//        PokerFragment fragment = new PokerFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        MainActivity mainActivity = (MainActivity)getActivity();
//        userViewModel = mainActivity.getUserViewModel();
//        View view = inflater.inflate(R.layout.fragment_poker, container, false);
//
//        TextView resultBot = view.findViewById(R.id.textview_result_bot);
//        TextView resultPlayer = view.findViewById(R.id.textview_result_player);
//
//        resultBot.setVisibility(View.GONE);
//        resultPlayer.setVisibility(View.GONE);
//
//        Process_View_ListLinkCardsPlayer_LiveData(view);
//        Process_View_ListLinkCardsBot_LiveData(view);
//        Process_View_ListLinkCardsTable_LiveData(view);
//        Process_View_EndGame_LiveData(view);
//        Process_View_ResultPlayers_LiveData(view);
//
//        Process_View_Button(view);
//        return view;
//    }
//
//    private void Process_View_ResultPlayers_LiveData(View view) {
//        userViewModel.getListResultPlayersLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
//
//            @Override
//            public void onChanged(List<String> results) {
//                TextView resultBot = view.findViewById(R.id.textview_result_bot);
//                TextView resultPlayer = view.findViewById(R.id.textview_result_player);
//
//                resultBot.setText(results.get(0));
//                resultPlayer.setText(results.get(1));
//            }
//        });
//    }
//
//    private void Process_View_EndGame_LiveData(View view) {
//        userViewModel.getEndGameLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//
//            @Override
//            public void onChanged(Boolean endgame) {
//                if (endgame){
//                    showResultGame(view);
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            navigateToEndGameFragment();
//                        }
//                    }, 15000);
//                }
//            }
//        });
//    }
//
//    private void Process_View_ListLinkCardsPlayer_LiveData(View view) {
//        userViewModel.getListLinkCardsPlayerLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
//
//            @Override
//            public void onChanged(List<String> listLink) {
//                ImageButton imageButtonFirstCard = (ImageButton)view.findViewById(R.id.image_first_card_player);
//                ImageButton imageButtonSecondCard = (ImageButton)view.findViewById(R.id.image_second_card_player);
//                Vector<ImageButton> listImageButton = new Vector<>();
//                listImageButton.add(imageButtonFirstCard);
//                listImageButton.add(imageButtonSecondCard);
//
//                for (int i=0;i<listImageButton.size();i++) {
//                    int finalI = i;
//                    listImageButton.get(i).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v)
//                        {
//                            userViewModel.Process_EI_Button_Card(finalI);
//                        }
//                    });
//                    String link = "android.resource://" + getContext().getPackageName() + "/drawable/" + listLink.get(i);
//                    Uri imgUri = Uri.parse(link);
//                    listImageButton.get(i).setImageURI(imgUri);
//                }
//            }
//        });
//    }
//
//    private void Process_View_ListLinkCardsBot_LiveData(View view) {
//        userViewModel.getListLinkCardsBotLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
//            @Override
//            public void onChanged(List<String> listLink) {
//                ImageView imageFirstCard = (ImageView)view.findViewById(R.id.image_first_card_bot);
//                ImageView imageSecondCard = (ImageView)view.findViewById(R.id.image_second_card_bot);
//                Vector<ImageView> listImageView = new Vector<>();
//                listImageView.add(imageFirstCard);
//                listImageView.add(imageSecondCard);
//
//                for (int i=0;i<listImageView.size();i++) {
//                    String link = "android.resource://" + getContext().getPackageName() + "/drawable/" + listLink.get(i);
//                    Uri imgUri = Uri.parse(link);
//                    listImageView.get(i).setImageURI(imgUri);
//                }
//            }
//        });
//    }
//
//    private void Process_View_ListLinkCardsTable_LiveData(View view) {
//        userViewModel.getListLinkCardsTableLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
//            private ImageView imageFirstCard;
//            private ImageView imageSecondCard;
//            private ImageView imageThirdCard;
//            private ImageView imageFourthCard;
//            private ImageView imageFifthCard;
//
//            @Override
//            public void onChanged(List<String> listLink) {
//                imageFirstCard = (ImageView)view.findViewById(R.id.image_first_card_table);
//                imageSecondCard = (ImageView)view.findViewById(R.id.image_second_card_table);
//                imageThirdCard = (ImageView)view.findViewById(R.id.image_third_card_table);
//                imageFourthCard = (ImageView)view.findViewById(R.id.image_fourth_card_table);
//                imageFifthCard = (ImageView)view.findViewById(R.id.image_fifth_card_table);
//
//                Vector<ImageView> listImageView = new Vector<>();
//                listImageView.add(imageFirstCard);
//                listImageView.add(imageSecondCard);
//                listImageView.add(imageThirdCard);
//                listImageView.add(imageFourthCard);
//                listImageView.add(imageFifthCard);
//
//
//                for (int i=0;i<listImageView.size();i++) {
//                    String link = "android.resource://" + getContext().getPackageName() + "/drawable/" + listLink.get(i);
//                    Uri imgUri = Uri.parse(link);
//                    listImageView.get(i).setImageURI(imgUri);
//                }
//            }
//        });
//    }
//
//    private void Process_View_Button(View view){
//        // Set button bet 100
//        Button btnBet100 = (Button) view.findViewById(R.id.btn_bet_100);
//        btnBet100.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                userViewModel.Process_EI_Button_Bet(100);
//            }
//        });
//
//        // Set button bet 200
//        Button btnBet200 = (Button) view.findViewById(R.id.btn_bet_200);
//        btnBet200.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                userViewModel.Process_EI_Button_Bet(200);
//            }
//        });
//
//        // Set button bet 500
//        Button btnBet500 = (Button) view.findViewById(R.id.btn_bet_500);
//        btnBet500.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                userViewModel.Process_EI_Button_Bet(500);
//            }
//        });
//
//        // Set button fold
//        Button btnFold = (Button) view.findViewById(R.id.btn_fold);
//        btnFold.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                userViewModel.Process_EI_Button_Fold();
//            }
//        });
//    }
//
//    private void navigateToEndGameFragment(){
//        NavController navController = NavHostFragment.findNavController(this);
//        navController.navigate(
//                R.id.action_pokerFragment_to_endGameFragment,
//                null,
//                new NavOptions.Builder()
//                        .setEnterAnim(android.R.animator.fade_in)
//                        .setExitAnim(android.R.animator.fade_out)
//                        .build()
//        );
//    }
//
//    private void showResultGame(View view){
//        TextView resultBot = view.findViewById(R.id.textview_result_bot);
//        TextView resultPlayer = view.findViewById(R.id.textview_result_player);
//
//        resultBot.setTextColor(Color.rgb(0,0,200));
//        resultPlayer.setTextColor(Color.rgb(0,0,200));
//
//        resultBot.setVisibility(View.VISIBLE);
//        resultPlayer.setVisibility(View.VISIBLE);
//    }
//}