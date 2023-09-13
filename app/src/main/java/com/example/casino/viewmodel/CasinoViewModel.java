package com.example.casino.viewmodel;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.casino.model.Card_Game;
import com.example.casino.model.Player;
import com.example.casino.model.Poker;
import com.example.casino.model.Three_Cards;

import java.util.ArrayList;
import java.util.List;

public class CasinoViewModel extends ViewModel {

    // QTimer* m_timer;
    private int m_jackpotNumber;
    private Player m_player;
    private String m_linkRank;
    private Card_Game game_controller;
    private List<String> m_listLinkCardsBot;
    private List<String> m_listLinkCardsPlayer;
    private List<String> m_listLinkCardsTable;
    private String m_linkWinLose;

    private List<String> m_listResultPlayers;

    enum Type_Game {
        THREE_CARDS,
        POKER,
        NONE_TYPE
    }

    private Type_Game currentGame;

    private int timesBet;

    private MutableLiveData<List<String>> mListLinkCardsBotLiveData;
    private MutableLiveData<List<String>> mListLinkCardsPlayerLiveData;
    private MutableLiveData<List<String>> mListLinkCardsTableLiveData;
    private MutableLiveData<String> mMessageLoginRegisterLiveData;
    private MutableLiveData<String> mLinkRankLiveData;
    private MutableLiveData<String> mLinkWinLoseLiveData;
    private MutableLiveData<Integer> mMoneyPlayerLiveData;
    private MutableLiveData<Boolean> mEndGameLiveData;
    private MutableLiveData<Integer> mMoneyBetLiveData;
    private MutableLiveData<List<String>> mListResultPlayersLiveData;

    public CasinoViewModel() {
        m_jackpotNumber = 1000; // Databases::Get_Instance().getJackpot();
        m_player = null;
        m_linkRank = "";
        game_controller = null;
        m_linkWinLose = "";
        currentGame = Type_Game.NONE_TYPE;
        timesBet = 0;
        initLiveData();
    }

    private void initLiveData() {
        m_listLinkCardsBot = new ArrayList<>();
        m_listLinkCardsPlayer = new ArrayList<>();
        m_listLinkCardsTable = new ArrayList<>();
        m_listResultPlayers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            m_listLinkCardsBot.add("");
            m_listLinkCardsPlayer.add("");
            m_listLinkCardsTable.add("");
            m_listResultPlayers.add("");
        }

        mListLinkCardsBotLiveData = new MutableLiveData<>();
        mListLinkCardsPlayerLiveData = new MutableLiveData<>();
        mListLinkCardsTableLiveData = new MutableLiveData<>();
        mMessageLoginRegisterLiveData = new MutableLiveData<>();
        mLinkRankLiveData = new MutableLiveData<>();
        mLinkWinLoseLiveData = new MutableLiveData<>();
        mMoneyPlayerLiveData = new MutableLiveData<>();
        mEndGameLiveData = new MutableLiveData<>();
        mMoneyBetLiveData = new MutableLiveData<>();
        mListResultPlayersLiveData = new MutableLiveData<>();

        mListLinkCardsBotLiveData.setValue(m_listLinkCardsBot);
        mListLinkCardsPlayerLiveData.setValue(m_listLinkCardsPlayer);
        mListLinkCardsTableLiveData.setValue(m_listLinkCardsTable);
        mMessageLoginRegisterLiveData.setValue("NONE");
        mLinkRankLiveData.setValue("");
        mLinkWinLoseLiveData.setValue("");
        mMoneyPlayerLiveData.setValue(0);
        mEndGameLiveData.setValue(false);
        mMoneyBetLiveData.setValue(0);
        mListResultPlayersLiveData.setValue(m_listResultPlayers);
    }

    public MutableLiveData<Boolean> getEndGameLiveData() {
        return mEndGameLiveData;
    }

    public MutableLiveData<List<String>> getListLinkCardsBotLiveData() {
        return mListLinkCardsBotLiveData;
    }

    public MutableLiveData<List<String>> getListLinkCardsPlayerLiveData() {
        return mListLinkCardsPlayerLiveData;
    }

    public MutableLiveData<List<String>> getListLinkCardsTableLiveData() {
        return mListLinkCardsTableLiveData;
    }

    public MutableLiveData<String> getMessageLoginRegisterLiveData() {
        return mMessageLoginRegisterLiveData;
    }

    public MutableLiveData<String> getLinkRankLiveData() {
        return mLinkRankLiveData;
    }

    public MutableLiveData<String> getLinkWinLoseLiveData() {
        return mLinkWinLoseLiveData;
    }

    public MutableLiveData<Integer> getMoneyPlayerLiveData() {
        return mMoneyPlayerLiveData;
    }

    public MutableLiveData<Integer> getMoneyBetLiveData() { return mMoneyBetLiveData; }

    public MutableLiveData<List<String>> getListResultPlayersLiveData() {
        return mListResultPlayersLiveData;
    }

    public void setJackpotNumber(int value) {
        m_jackpotNumber = value;
        // emit jackpotNumberChanged();
    }

    public int getJackpotNumber() {
        return m_jackpotNumber;
    }

    public void sendMessageLoginRegister(String value) {
        mMessageLoginRegisterLiveData.setValue(value);
    }

    public void setMoneyPlayer(int value) {
        if (m_player != null ) {
            m_player.setMoney(value);
            mMoneyPlayerLiveData.setValue(m_player.getMoney());
        }
    }

    public void setLinkRank(String value) {
        if (m_linkRank != value) {
            m_linkRank = value;
            mLinkRankLiveData.setValue(m_linkRank);
        }
    }

    public void setLinkFirstCardBot(String value) {
        if (m_listLinkCardsBot.get(0) != value) {
            m_listLinkCardsBot.set(0, value);
        }
    }

    public void setLinkSecondCardBot(String value) {
        if (m_listLinkCardsBot.get(1) != value) {
            m_listLinkCardsBot.set(1, value);
        }
    }

    public void setLinkThirdCardBot(String value) {
        if (m_listLinkCardsBot.get(2) != value) {
            m_listLinkCardsBot.set(2, value);
        }
    }

    public void setLinkCardsBotLiveData() {
        mListLinkCardsBotLiveData.setValue(m_listLinkCardsBot);
    }

    public void setLinkFirstCardPlayer(String value) {
        if (m_listLinkCardsPlayer.get(0) != value) {
            m_listLinkCardsPlayer.set(0, value);
            mListLinkCardsPlayerLiveData.setValue(m_listLinkCardsPlayer);
        }
    }

    public void setLinkSecondCardPlayer(String value) {
        if (m_listLinkCardsPlayer.get(1) != value) {
            m_listLinkCardsPlayer.set(1, value);
            mListLinkCardsPlayerLiveData.setValue(m_listLinkCardsPlayer);
        }
    }

    public void setLinkThirdCardPlayer(String value) {
        if (m_listLinkCardsPlayer.get(2) != value) {
            m_listLinkCardsPlayer.set(2, value);
            mListLinkCardsPlayerLiveData.setValue(m_listLinkCardsPlayer);
        }
    }

    public void setLinkWinLose(String value) {
        if (m_linkWinLose != value) {
            m_linkWinLose = value;
            mLinkWinLoseLiveData.setValue(m_linkWinLose);
        }
    }

    public void setLinkFirstCardTable(String value) {
        if (m_listLinkCardsTable.get(0) != value) {
            m_listLinkCardsTable.set(0, value);
            mListLinkCardsTableLiveData.setValue(m_listLinkCardsTable);
        }
    }

    public void setLinkSecondCardTable(String value) {
        if (m_listLinkCardsTable.get(1) != value) {
            m_listLinkCardsTable.set(1, value);
            mListLinkCardsTableLiveData.setValue(m_listLinkCardsTable);
        }
    }

    public void setLinkThirdCardTable(String value) {
        if (m_listLinkCardsTable.get(2) != value) {
            m_listLinkCardsTable.set(2, value);
            mListLinkCardsTableLiveData.setValue(m_listLinkCardsTable);
        }
    }

    public void setLinkFourthCardTable(String value) {
        if (m_listLinkCardsTable.get(3) != value) {
            m_listLinkCardsTable.set(3, value);
            mListLinkCardsTableLiveData.setValue(m_listLinkCardsTable);
        }
    }

    public void setLinkFifthCardTable(String value) {
        if (m_listLinkCardsTable.get(4) != value) {
            m_listLinkCardsTable.set(4, value);
            mListLinkCardsTableLiveData.setValue(m_listLinkCardsTable);
        }
    }

    public void setMoneyBetLiveData(int value) {
        mMoneyBetLiveData.setValue(value);
    }

    public void setResultPlayersLiveData() {
        mListResultPlayersLiveData.setValue(m_listResultPlayers);
    }

    public void Process_EI_Button_Login(String accountInput, String passWordInput) {
        String account, passWord;
        account = accountInput;
        passWord = passWordInput;
        // if (Databases::Get_Instance().Check_Login(account, passWord)
        if (true) {
            this.m_player = new Player(account);
            Update_Rank_Player();
            sendMessageLoginRegister("LOGIN SUCCESS");
        } else {
            sendMessageLoginRegister("LOGIN FALSE");
        }
    }

    public void Process_EI_Button_Register(String accountInput, String passWordInput) {
        String account, passWord;
        account = accountInput;
        passWord = passWordInput;

        // if (Check_Valid::Check_Valid_Account(account, passWord) != 0)
        if (true) {
            // if (Databases::Get_Instance().Check_Account_Exist(account) == 0)
            if (true) {
                // Databases::Get_Instance().Add_Account(account, passWord);
                m_player = new Player(account);
                Update_Rank_Player();
                sendMessageLoginRegister("REGISTER SUCCESS");
            } else {
                sendMessageLoginRegister("REGISTER FALSE");
            }
        } else {
            sendMessageLoginRegister("REGISTER FALSE");
        }
    }

    public void Process_EI_Button_Three_Cards_Game() {
        currentGame = Type_Game.THREE_CARDS;
        game_controller = new Three_Cards(this.m_player);
        game_controller.Run();
        Update_Link_Cards();
    }

    public void Process_EI_Button_Poker_Game() {
        currentGame = Type_Game.POKER;
        game_controller = new Poker(this.m_player);
        game_controller.Run();
        Update_Link_Cards();
        mEndGameLiveData.setValue(false);
    }

    public void Process_EI_Button_Bet(int moneyBet) {
        if (game_controller != null) {
            game_controller.moneyBet += moneyBet;
            if (currentGame == Type_Game.THREE_CARDS) {
                switch (game_controller.Check_Win()) {
                    case WIN:
                        setMoneyPlayer(m_player.getMoney() + game_controller.moneyBet);
                        setMoneyBetLiveData(game_controller.moneyBet);
                        setLinkWinLose("you_win");
                        break;
                    case DRAW:
                        setLinkWinLose("you_draw");
                        setMoneyBetLiveData(0);
                        break;
                    case LOSE:
                        setMoneyPlayer(m_player.getMoney() - game_controller.moneyBet);
                        setMoneyBetLiveData(-game_controller.moneyBet);
                        setLinkWinLose("you_lose");
                        break;
                    default:
                        break;
                }
                Show_All_Cards();
                Update_Link_Cards();
            } else if (currentGame == Type_Game.POKER) {
                timesBet++;
                Poker poker_game = (Poker) (game_controller);
                if (timesBet == 1) {
                    poker_game.tableOwner.cardOwned.get(0).setStatus(true);
                    poker_game.tableOwner.cardOwned.get(1).setStatus(true);
                    poker_game.tableOwner.cardOwned.get(2).setStatus(true);
                } else if (timesBet == 2) {
                    poker_game.tableOwner.cardOwned.get(3).setStatus(true);
                } else if (timesBet == 3) {
                    poker_game.tableOwner.cardOwned.get(4).setStatus(true);
                } else {
                    switch (game_controller.Check_Win()) {
                        case WIN:
                            setMoneyPlayer(m_player.getMoney() + game_controller.moneyBet);
                            setMoneyBetLiveData(game_controller.moneyBet);
                            setLinkWinLose("you_win");
                            break;
                        case DRAW:
                            setLinkWinLose("you_draw");
                            setMoneyBetLiveData(0);
                            break;
                        case LOSE:
                            setMoneyPlayer(m_player.getMoney() - game_controller.moneyBet);
                            setMoneyBetLiveData(-game_controller.moneyBet);
                            setLinkWinLose("you_lose");
                            break;
                        default:
                            break;
                    }
                    Show_All_Cards();
                    mEndGameLiveData.setValue(true);
                    m_listResultPlayers.clear();
                    m_listResultPlayers.add(0,poker_game.getResultFirstPlayer());
                    m_listResultPlayers.add(1, poker_game.getResultSecondPlayer());
                    setResultPlayersLiveData();
                }
                Update_Link_Cards();
            } else {

            }
        }
    }

    public void Process_EI_Button_Fold() {
        Poker poker_game = (Poker) (game_controller);
        m_listResultPlayers.clear();
        m_listResultPlayers.add(0,"Bluff - Run");
        m_listResultPlayers.add(1, "Fold");
        setResultPlayersLiveData();
        poker_game.tableOwner.cardOwned.get(0).setStatus(true);
        poker_game.tableOwner.cardOwned.get(1).setStatus(true);
        poker_game.tableOwner.cardOwned.get(2).setStatus(true);
        poker_game.tableOwner.cardOwned.get(3).setStatus(true);
        poker_game.tableOwner.cardOwned.get(4).setStatus(true);
        setMoneyPlayer(m_player.getMoney() - game_controller.moneyBet);
        setLinkWinLose("you_lose");
        Show_All_Cards();
        Update_Link_Cards();
        mEndGameLiveData.setValue(true);
    }

    public void Process_EI_Button_Play_Again() {
        game_controller = null;
        Reset_Preset_InGame_Screen();
        switch (currentGame) {
            case THREE_CARDS:
                Process_EI_Button_Three_Cards_Game();
                break;
            case POKER:
                Process_EI_Button_Poker_Game();
                break;
            case NONE_TYPE:
                break;
            default:
                break;
        }
    }

    public void Process_EI_Button_Out_Table() {
        game_controller = null;
        Reset_Preset_InGame_Screen();
        Update_Rank_Player();
    }

    public void Process_EI_Button_Card(int value) {
        if (game_controller != null) {
            game_controller.player_ingame.cardOwned.get(value).Upside_Card();
            Update_Link_Cards();
        }
    }

    @SuppressLint("RestrictedApi")
    private void Update_Rank_Player() {
        String linkRank;
        switch (m_player.Get_Rank()) {
            case BRONZE:
                linkRank = "bronze_rank";
                break;
            case SILVER:
                linkRank = "silver_rank";
                break;
            case GOLD:
                linkRank = "gold_rank";
                break;
            case PLATINUM:
                linkRank = "platinum_rank";
                break;
            case DIAMOND:
                linkRank = "diamond_rank";
                break;
            case MASTER:
                linkRank = "master_rank";
                break;
            case GRANDMASTER:
                linkRank = "grandmaster_rank";
                break;
            case CHALLENGER:
                linkRank = "challenger_rank";
                break;
            default:
                linkRank = "";
                break;
        }
        setLinkRank(linkRank);
        setMoneyPlayer(m_player.getMoney());
    }

    private void Update_Link_Cards() {
        if (game_controller != null) {
            if (currentGame == Type_Game.THREE_CARDS) {
                setLinkFirstCardBot(game_controller.bot.cardOwned.get(0).Get_Link());
                setLinkSecondCardBot(game_controller.bot.cardOwned.get(1).Get_Link());
                setLinkThirdCardBot(game_controller.bot.cardOwned.get(2).Get_Link());
                setLinkCardsBotLiveData();
                setLinkFirstCardPlayer(game_controller.player_ingame.cardOwned.get(0).Get_Link());
                setLinkSecondCardPlayer(game_controller.player_ingame.cardOwned.get(1).Get_Link());
                setLinkThirdCardPlayer(game_controller.player_ingame.cardOwned.get(2).Get_Link());
            } else if (currentGame == Type_Game.POKER) {
                Poker poker_game = (Poker) game_controller;
                setLinkFirstCardBot(poker_game.bot.cardOwned.get(0).Get_Link());
                setLinkSecondCardBot(poker_game.bot.cardOwned.get(1).Get_Link());
                setLinkCardsBotLiveData();

                setLinkFirstCardPlayer(poker_game.player_ingame.cardOwned.get(0).Get_Link());
                setLinkSecondCardPlayer(poker_game.player_ingame.cardOwned.get(1).Get_Link());

                setLinkFirstCardTable(poker_game.tableOwner.cardOwned.get(0).Get_Link());
                setLinkSecondCardTable(poker_game.tableOwner.cardOwned.get(1).Get_Link());
                setLinkThirdCardTable(poker_game.tableOwner.cardOwned.get(2).Get_Link());
                setLinkFourthCardTable(poker_game.tableOwner.cardOwned.get(3).Get_Link());
                setLinkFifthCardTable(poker_game.tableOwner.cardOwned.get(4).Get_Link());
            } else {

            }
        }
    }

    private void Show_All_Cards() {
        for (int i = 0; i < game_controller.bot.cardOwned.size(); i++) {
            game_controller.bot.cardOwned.get(i).setStatus(true);
            game_controller.player_ingame.cardOwned.get(i).setStatus(true);
        }
    }

    private void Reset_Preset_InGame_Screen() {
        if (game_controller != null) {
            for (int i = 0; i < game_controller.bot.cardOwned.size(); i++) {
                game_controller.bot.cardOwned.get(i).setStatus(false);
                game_controller.player_ingame.cardOwned.get(i).setStatus(false);
            }
            Poker poker_game = (Poker) game_controller;
            for (int i = 0; i < poker_game.tableOwner.cardOwned.size(); i++) {
                poker_game.tableOwner.cardOwned.get(i).setStatus(false);
            }
        }
        timesBet = 0;
    }

    // private slots:
    // void Update_Jackpot_Callback();

}
