//package com.example.casino.fragment;
//
//import android.net.Uri;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.LifecycleObserver;
//import androidx.lifecycle.LifecycleOwner;
//import androidx.lifecycle.Observer;
//import androidx.navigation.NavController;
//import androidx.navigation.NavOptions;
//import androidx.navigation.fragment.NavHostFragment;
//
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageSwitcher;
//import android.widget.ImageView;
//import android.widget.Toast;
//import android.widget.ViewSwitcher;
//
//import com.example.casino.MainActivity;
//import com.example.casino.R;
//import com.example.casino.viewmodel.CasinoViewModel;
//
//import java.util.List;
//import java.util.Vector;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ThreeCardsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class ThreeCardsFragment extends Fragment {
//
//    // TODO: Rename and change types of parameters
//    private CasinoViewModel userViewModel;
//
//    public ThreeCardsFragment() {
//        // Required empty public constructor
//    }
//
//    public static ThreeCardsFragment newInstance() {
//        ThreeCardsFragment fragment = new ThreeCardsFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        MainActivity mainActivity = (MainActivity)getActivity();
//        userViewModel = mainActivity.getUserViewModel();
//        View view = inflater.inflate(R.layout.fragment_three_cards,
//                container, false);
//
//        Process_View_ListLinkCardsBot_LiveData(view);
//        Process_View_ListLinkCardsPlayer_LiveData(view);
//        Process_View_Button(view);
//
//        return  view;
//    }
//
//    private void Process_View_ListLinkCardsPlayer_LiveData(View view) {
//        userViewModel.getListLinkCardsPlayerLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
//
//            @Override
//            public void onChanged(List<String> listLink) {
//                ImageButton imageButtonFirstCard = (ImageButton)view.findViewById(R.id.image_first_card_player);
//                ImageButton imageButtonSecondCard = (ImageButton)view.findViewById(R.id.image_second_card_player);
//                ImageButton imageButtonThirdCard = (ImageButton)view.findViewById(R.id.image_third_card_player);
//                Vector<ImageButton> listImageButton = new Vector<>();
//                listImageButton.add(imageButtonFirstCard);
//                listImageButton.add(imageButtonSecondCard);
//                listImageButton.add(imageButtonThirdCard);
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
//        userViewModel.getListLinkCardsBotLiveData().observe(this, new Observer<List<String>>() {
//            @Override
//            public void onChanged(List<String> listLink) {
//                ImageView imageFirstCard = (ImageView)view.findViewById(R.id.image_first_card_bot);
//                ImageView imageSecondCard = (ImageView)view.findViewById(R.id.image_second_card_bot);
//                ImageView imageThirdCard = (ImageView)view.findViewById(R.id.image_third_card_bot);
//                Vector<ImageView> listImageView = new Vector<>();
//                listImageView.add(imageFirstCard);
//                listImageView.add(imageSecondCard);
//                listImageView.add(imageThirdCard);
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
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        navigateToEndGameFragment();
//                    }
//                }, 20000);
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
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        navigateToEndGameFragment();
//                    }
//                }, 20000);
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
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        navigateToEndGameFragment();
//                    }
//                }, 20000);
//            }
//        });
//    }
//
//    private void navigateToEndGameFragment(){
//        NavController navController = NavHostFragment.findNavController(this);
//        navController.navigate(
//                R.id.action_threeCardsFragment_to_endGameFragment,
//                null,
//                new NavOptions.Builder()
//                        .setEnterAnim(android.R.animator.fade_in)
//                        .setExitAnim(android.R.animator.fade_out)
//                        .build()
//        );
//    }
//}