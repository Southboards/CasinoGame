package com.example.casino.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casino.data.entities.Account
import com.example.casino.model.Card_Game
import com.example.casino.model.Player
import com.example.casino.model.Poker
import com.example.casino.model.Three_Cards
import com.example.casino.data.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class CasinoViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    // QTimer* m_timer;
    private var m_jackpotNumber: Int = 1000
    private var m_player: Player? = null
    private var m_linkRank: String = ""
    private var game_controller: Card_Game? = null
    private var m_listLinkCardsBot: MutableList<String> = mutableListOf()
    private var m_listLinkCardsPlayer: MutableList<String> = mutableListOf()
    private var m_listLinkCardsTable: MutableList<String> = mutableListOf()
    private var m_linkWinLose: String = ""
    private var m_listResultPlayers: MutableList<String> = mutableListOf()
    private var currentGame: Type_Game = Type_Game.NONE_TYPE
    private var timesBet: Int = 0
    private var mAccountRepository: AccountRepository = accountRepository

    private var mListLinkCardsBotLiveData: MutableLiveData<List<String>> = MutableLiveData()
    private var mListLinkCardsPlayerLiveData: MutableLiveData<List<String>> = MutableLiveData()
    private var mListLinkCardsTableLiveData: MutableLiveData<List<String>> = MutableLiveData()
    private var mMessageLoginRegisterLiveData: MutableLiveData<String> = MutableLiveData()
    private var mLinkRankLiveData: MutableLiveData<String> = MutableLiveData()
    private var mLinkWinLoseLiveData: MutableLiveData<String> = MutableLiveData()
    private var mMoneyPlayerLiveData: MutableLiveData<Int> = MutableLiveData()
    private var mEndGameLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private var mMoneyBetLiveData: MutableLiveData<Int> = MutableLiveData()
    private var mListResultPlayersLiveData: MutableLiveData<List<String>> = MutableLiveData()

    enum class Type_Game {
        THREE_CARDS,
        POKER,
        NONE_TYPE
    }

    init {
        initLiveData()
    }

    private fun initLiveData() {
        for (i in 0 until 5) {
            m_listLinkCardsBot.add("")
            m_listLinkCardsPlayer.add("")
            m_listLinkCardsTable.add("")
            m_listResultPlayers.add("")
        }

        mListLinkCardsBotLiveData.value = m_listLinkCardsBot
        mListLinkCardsPlayerLiveData.value = m_listLinkCardsPlayer
        mListLinkCardsTableLiveData.value = m_listLinkCardsTable
        mMessageLoginRegisterLiveData.value = "NONE"
        mLinkRankLiveData.value = ""
        mLinkWinLoseLiveData.value = ""
        mMoneyPlayerLiveData.value = 0
        mEndGameLiveData.value = false
        mMoneyBetLiveData.value = 0
        mListResultPlayersLiveData.value = m_listResultPlayers
    }

    fun getEndGameLiveData(): MutableLiveData<Boolean> = mEndGameLiveData
    fun getListLinkCardsBotLiveData(): MutableLiveData<List<String>> = mListLinkCardsBotLiveData
    fun getListLinkCardsPlayerLiveData(): MutableLiveData<List<String>> = mListLinkCardsPlayerLiveData
    fun getListLinkCardsTableLiveData(): MutableLiveData<List<String>> = mListLinkCardsTableLiveData
    fun getMessageLoginRegisterLiveData(): MutableLiveData<String> = mMessageLoginRegisterLiveData
    fun getLinkRankLiveData(): MutableLiveData<String> = mLinkRankLiveData
    fun getLinkWinLoseLiveData(): MutableLiveData<String> = mLinkWinLoseLiveData
    fun getMoneyPlayerLiveData(): MutableLiveData<Int> = mMoneyPlayerLiveData
    fun getMoneyBetLiveData(): MutableLiveData<Int> = mMoneyBetLiveData
    fun getListResultPlayersLiveData(): MutableLiveData<List<String>> = mListResultPlayersLiveData

    fun setJackpotNumber(value: Int) {
        m_jackpotNumber = value
        // emit jackpotNumberChanged();
    }

    fun getJackpotNumber(): Int = m_jackpotNumber

    fun sendMessageLoginRegister(value: String) {
        Log.d("check", "sendMessageLoginRegister: ")
        mMessageLoginRegisterLiveData.value = value
    }

    fun setMoneyPlayer(value: Int) {
        m_player?.let {
            it.setMoney(value)
            mMoneyPlayerLiveData.value = it.money
        }
    }

    fun setLinkRank(value: String) {
        if (m_linkRank != value) {
            m_linkRank = value
            mLinkRankLiveData.value = m_linkRank
        }
    }

    fun setLinkFirstCardBot(value: String) {
        if (m_listLinkCardsBot[0] != value) {
            m_listLinkCardsBot[0] = value
        }
    }

    fun setLinkSecondCardBot(value: String) {
        if (m_listLinkCardsBot[1] != value) {
            m_listLinkCardsBot[1] = value
        }
    }

    fun setLinkThirdCardBot(value: String) {
        if (m_listLinkCardsBot[2] != value) {
            m_listLinkCardsBot[2] = value
        }
    }

    fun setLinkCardsBotLiveData() {
        mListLinkCardsBotLiveData.value = ArrayList(m_listLinkCardsBot)
    }

    fun setLinkCardsPlayerLiveData() {
        mListLinkCardsPlayerLiveData.value = ArrayList(m_listLinkCardsPlayer)
    }

    fun setLinkCardsTableLiveData() {
        mListLinkCardsTableLiveData.value = ArrayList(m_listLinkCardsTable)
    }

    fun setLinkFirstCardPlayer(value: String) {
        if (m_listLinkCardsPlayer[0] != value) {
            m_listLinkCardsPlayer[0] = value
            setLinkCardsPlayerLiveData()
        }
    }

    fun setLinkSecondCardPlayer(value: String) {
        if (m_listLinkCardsPlayer[1] != value) {
            m_listLinkCardsPlayer[1] = value
            setLinkCardsPlayerLiveData()
        }
    }

    fun setLinkThirdCardPlayer(value: String) {
        if (m_listLinkCardsPlayer[2] != value) {
            m_listLinkCardsPlayer[2] = value
            setLinkCardsPlayerLiveData()
        }
    }

    fun setLinkWinLose(value: String) {
        if (m_linkWinLose != value) {
            m_linkWinLose = value
            mLinkWinLoseLiveData.value = m_linkWinLose
        }
    }

    fun setLinkFirstCardTable(value: String) {
        if (m_listLinkCardsTable[0] != value) {
            m_listLinkCardsTable[0] = value
            setLinkCardsTableLiveData()
        }
    }

    fun setLinkSecondCardTable(value: String) {
        if (m_listLinkCardsTable[1] != value) {
            m_listLinkCardsTable[1] = value
            setLinkCardsTableLiveData()
        }
    }

    fun setLinkThirdCardTable(value: String) {
        if (m_listLinkCardsTable[2] != value) {
            m_listLinkCardsTable[2] = value
            setLinkCardsTableLiveData()
        }
    }

    fun setLinkFourthCardTable(value: String) {
        if (m_listLinkCardsTable[3] != value) {
            m_listLinkCardsTable[3] = value
            setLinkCardsTableLiveData()
        }
    }

    fun setLinkFifthCardTable(value: String) {
        if (m_listLinkCardsTable[4] != value) {
            m_listLinkCardsTable[4] = value
            setLinkCardsTableLiveData()
        }
    }

    fun setMoneyBetLiveData(value: Int) {
        mMoneyBetLiveData.value = value
    }

    fun setResultPlayersLiveData() {
        mListResultPlayersLiveData.value = ArrayList(m_listResultPlayers)
    }

    fun Process_EI_Button_Login(accountInput: String, passWordInput: String) {
        val account = accountInput
        val password = passWordInput
        Log.d("check", "button Login: ")
        if (checkLogin(account, password) != null) {
            val money = getMoneyAccount(account)
            Log.d("check", "button Login ok: ")
            this.m_player = money?.let { Player(account, it) }
            Update_Rank_Player()
            sendMessageLoginRegister("LOGIN SUCCESS")
        } else {
            sendMessageLoginRegister("LOGIN FALSE")
        }
    }

    fun Process_EI_Button_Register(accountInput: String, passWordInput: String) {
        val account = accountInput
        val password = passWordInput
        Log.d("check", "button resgi ")
        if (isValidNewAccount(account, password)) {
            if (getAccountByUsername(account) == null) {
                insertAccount(account, password)
                Log.d("check", "button resgi ok")
                m_player = Player(account, 10000)
                Update_Rank_Player()
                sendMessageLoginRegister("REGISTER SUCCESS")
            } else {
                sendMessageLoginRegister("REGISTER FALSE")
            }
        } else {
            sendMessageLoginRegister("REGISTER FALSE")
        }
    }

    fun Process_EI_Button_Three_Cards_Game() {
        currentGame = Type_Game.THREE_CARDS
        game_controller = Three_Cards(this.m_player!!)
        game_controller?.Run()
        Update_Link_Cards()
        mEndGameLiveData.value = false
    }

    fun Process_EI_Button_Poker_Game() {
        currentGame = Type_Game.POKER
        game_controller = Poker(this.m_player!!)
        game_controller?.Run()
        Update_Link_Cards()
        mEndGameLiveData.value = false
    }

    fun Process_EI_Button_Bet(moneyBet: Int) {
        game_controller?.let {
            it.moneyBet += moneyBet
            if (currentGame == Type_Game.THREE_CARDS) {
                when (it.Check_Win()) {
                    Card_Game.EndGame.WIN -> {
                        setMoneyPlayer(m_player!!.money + it.moneyBet)
                        setMoneyBetLiveData(it.moneyBet)
                        setLinkWinLose("you_win")
                    }
                    Card_Game.EndGame.DRAW -> {
                        setLinkWinLose("you_draw")
                        setMoneyBetLiveData(0)
                    }
                    Card_Game.EndGame.LOSE -> {
                        setMoneyPlayer(m_player!!.money - it.moneyBet)
                        setMoneyBetLiveData(-it.moneyBet)
                        setLinkWinLose("you_lose")
                    }
                    else -> {
                    }
                }
                endGame()
            } else if (currentGame == Type_Game.POKER) {
                timesBet++
                val poker_game = game_controller as Poker
                when (timesBet) {
                    1 -> {
                        poker_game.tableOwner.cardOwned[0].setStatus(true)
                        poker_game.tableOwner.cardOwned[1].setStatus(true)
                        poker_game.tableOwner.cardOwned[2].setStatus(true)
                        Update_Link_Cards()
                    }
                    2 -> {
                        poker_game.tableOwner.cardOwned[3].setStatus(true)
                        Update_Link_Cards()
                    }
                    3 -> {
                        poker_game.tableOwner.cardOwned[4].setStatus(true)
                        Update_Link_Cards()
                    }
                    else -> {
                        when (it.Check_Win()) {
                            Card_Game.EndGame.WIN -> {
                                setMoneyPlayer(m_player!!.money + it.moneyBet)
                                setMoneyBetLiveData(it.moneyBet)
                                setLinkWinLose("you_win")
                            }
                            Card_Game.EndGame.DRAW -> {
                                setLinkWinLose("you_draw")
                                setMoneyBetLiveData(0)
                            }
                            Card_Game.EndGame.LOSE -> {
                                setMoneyPlayer(m_player!!.money - it.moneyBet)
                                setMoneyBetLiveData(-it.moneyBet)
                                setLinkWinLose("you_lose")
                            }
                            else -> {
                            }
                        }
                        m_listResultPlayers.clear()
                        m_listResultPlayers.add(0, poker_game.getResultFirstPlayer())
                        m_listResultPlayers.add(1, poker_game.getResultSecondPlayer())
                        setResultPlayersLiveData()
                        endGame()
                    }
                }
            } else {
            }
        }
    }

    fun Process_EI_Button_Fold() {
        val poker_game = game_controller as Poker
        m_listResultPlayers.clear()
        m_listResultPlayers.add(0, "Bluff - Run")
        m_listResultPlayers.add(1, poker_game.getResultSecondPlayer())
        setMoneyBetLiveData(-poker_game.moneyBet);
        setResultPlayersLiveData()
        endGame()
    }

    fun endGame() {
        Show_All_Cards()
        Update_Link_Cards()
        mEndGameLiveData.value = true
    }

    private fun Show_All_Cards() {
        for (i in game_controller?.bot?.cardOwned?.indices!!) {
            game_controller?.bot?.cardOwned!![i].setStatus(true)
            game_controller?.player_ingame?.cardOwned?.get(i)?.setStatus(true)
        }
    }

    private fun Update_Link_Cards() {
        game_controller?.let { controller ->
            when (currentGame) {
                Type_Game.THREE_CARDS -> {
                    // Set bot's cards
                    setLinkFirstCardBot(controller.bot.cardOwned[0].Get_Link())
                    setLinkSecondCardBot(controller.bot.cardOwned[1].Get_Link())
                    setLinkThirdCardBot(controller.bot.cardOwned[2].Get_Link())
                    setLinkCardsBotLiveData()

                    // Set player's cards
                    setLinkFirstCardPlayer(controller.player_ingame.cardOwned[0].Get_Link())
                    setLinkSecondCardPlayer(controller.player_ingame.cardOwned[1].Get_Link())
                    setLinkThirdCardPlayer(controller.player_ingame.cardOwned[2].Get_Link())
                }
                Type_Game.POKER -> {
                    val pokerGame = controller as Poker

                    // Set bot's cards
                    setLinkFirstCardBot(pokerGame.bot.cardOwned[0].Get_Link())
                    setLinkSecondCardBot(pokerGame.bot.cardOwned[1].Get_Link())
                    setLinkCardsBotLiveData()

                    // Set player's cards
                    setLinkFirstCardPlayer(pokerGame.player_ingame.cardOwned[0].Get_Link())
                    setLinkSecondCardPlayer(pokerGame.player_ingame.cardOwned[1].Get_Link())

                    // Set table's cards
                    setLinkFirstCardTable(pokerGame.tableOwner.cardOwned[0].Get_Link())
                    setLinkSecondCardTable(pokerGame.tableOwner.cardOwned[1].Get_Link())
                    setLinkThirdCardTable(pokerGame.tableOwner.cardOwned[2].Get_Link())
                    setLinkFourthCardTable(pokerGame.tableOwner.cardOwned[3].Get_Link())
                    setLinkFifthCardTable(pokerGame.tableOwner.cardOwned[4].Get_Link())
                }
                else -> {
                    // Handle other cases if needed
                }
            }
        }
    }

    fun Process_EI_Button_Play_Again() {
        game_controller = null
        Reset_Preset_InGame_Screen()
        when (currentGame) {
            Type_Game.THREE_CARDS -> Process_EI_Button_Three_Cards_Game()
            Type_Game.POKER -> Process_EI_Button_Poker_Game()
            Type_Game.NONE_TYPE -> { }
            else -> { }
        }
    }

    fun Process_EI_Button_Out_Table() {
        game_controller = null
        Reset_Preset_InGame_Screen()
        Update_Rank_Player()
    }

    fun Process_EI_Button_Card(value: Int) {
        game_controller?.let {
            it.player_ingame.cardOwned[value].Upside_Card()
            Update_Link_Cards()
        }
    }

    private fun Reset_Preset_InGame_Screen() {
        game_controller?.let {
            for (i in it.bot.cardOwned.indices) {
                it.bot.cardOwned[i].setStatus(false)
                it.player_ingame.cardOwned[i].setStatus(false)
            }

            val poker_game = it as? Poker
            poker_game?.let { game ->
                for (i in game.tableOwner.cardOwned.indices) {
                    game.tableOwner.cardOwned[i].setStatus(false)
                }
            }
        }
        timesBet = 0
    }

    private fun Update_Rank_Player() {
        val linkRank: String
        when (m_player?.Get_Rank()) {
            Player.Rank.BRONZE -> linkRank = "bronze_rank"
            Player.Rank.SILVER -> linkRank = "silver_rank"
            Player.Rank.GOLD -> linkRank = "gold_rank"
            Player.Rank.PLATINUM -> linkRank = "platinum_rank"
            Player.Rank.DIAMOND -> linkRank = "diamond_rank"
            Player.Rank.MASTER -> linkRank = "master_rank"
            Player.Rank.GRANDMASTER -> linkRank = "grandmaster_rank"
            Player.Rank.CHALLENGER -> linkRank = "challenger_rank"
            else -> linkRank = ""
        }
        setLinkRank(linkRank)
        m_player?.let { setMoneyPlayer(it.getMoney()) }
    }


    private fun isValidNewAccount(account: String, password: String): Boolean {
        val pattern: Pattern = Pattern.compile("[a-zA-Z0-9]*")
        val matcher: Matcher = pattern.matcher(account)
        val matcherPass: Matcher = pattern.matcher(password)
        return matcher.matches() && matcherPass.matches()
    }

    fun insertAccount(username: String, password: String) {
        val account:Account = Account(username = username, password = password, money = 10000)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mAccountRepository.insert(account)
            }
        }
    }

    fun updateMoneyAccount(username: String, money: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mAccountRepository.updateMoney(username, money)
            }
        }
    }

    fun updateAccount(account: Account) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mAccountRepository.update(account)
            }
        }
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mAccountRepository.delete(account)
            }
        }
    }

    fun getMoneyAccount(username: String): Int? {
        var result: Int? = null
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                result = mAccountRepository.getMoneyByUsername(username)
            }
        }
        return result
    }

    fun checkLogin(username: String, password: String): Account? {
        var account: Account? = null
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                account = mAccountRepository.checkLogin(username, password)
            }
        }
        Log.d("check", "checkLogin: ")
        return account
    }

    fun getAccountByUsername(username: String): Account? {
        var account: Account? = null
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                account = mAccountRepository.getAccountByUsername(username)
            }
        }
        return account
    }

}
