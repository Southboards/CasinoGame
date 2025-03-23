package com.example.casino.model;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;

import android.util.Log;

import java.util.Vector;

import java.util.Collections;

public class Poker extends Card_Game {
    // private final int NUMBER_PLAYER = 2;

    final int NUMBER_CARD_FOR_PLAYER = 2;
    final int NUMBER_CARD_FOR_TABLE = 5;

    private String resultFirstPlayer;
    private String resultSecondPlayer;

    public Poker(Player playerInput) {
        super(playerInput);
        resultFirstPlayer = "";
        resultSecondPlayer = "";
    }

    public void setResultFirstPlayer(String result){
        resultFirstPlayer = result;
    }

    public String getResultFirstPlayer(){
        return resultFirstPlayer;
    }

    public void setResultSecondPlayer(String result){
        resultSecondPlayer = result;
    }

    public String getResultSecondPlayer(){
        return resultSecondPlayer;
    }

    public EndGame Check_Win() {
        Card_Game.EndGame result = Card_Game.EndGame.LOSE;
        Vector<Card> cardsFirstPlayer = new Vector<Card>(tableOwner.cardOwned);
        Vector<Card> cardsSecondPlayer = new Vector<Card>(tableOwner.cardOwned);
        Vector<Card> bestCardsFirstPlayer = new Vector<Card>();
        Vector<Card> bestCardsSecondPlayer = new Vector<Card>();
        Type_Poker_Cards typeBestCardsFirstPlayer;
        Type_Poker_Cards typeBestCardsSecondPlayer;

        for (int i = 0; i < 2; i++) {
            cardsFirstPlayer.add(bot.cardOwned.get(i));
            cardsSecondPlayer.add(player_ingame.cardOwned.get(i));
        }
        Wrap_Vector_Cards wrapVectorFirstPlayer = new Wrap_Vector_Cards();
        Wrap_Vector_Cards wrapVectorSecondPlayer = new Wrap_Vector_Cards();

        typeBestCardsFirstPlayer = Get_Best_Cards(cardsFirstPlayer, wrapVectorFirstPlayer);
        typeBestCardsSecondPlayer = Get_Best_Cards(cardsSecondPlayer, wrapVectorSecondPlayer);

        if (typeBestCardsFirstPlayer.ordinal() < typeBestCardsSecondPlayer.ordinal()) {
            result = Card_Game.EndGame.WIN;
        } else if (typeBestCardsFirstPlayer.ordinal() > typeBestCardsSecondPlayer.ordinal()) {
            result = Card_Game.EndGame.LOSE;
        } else {
            bestCardsFirstPlayer = wrapVectorFirstPlayer.cards;
            bestCardsSecondPlayer = wrapVectorSecondPlayer.cards;
            switch (typeBestCardsFirstPlayer) {
                case STRAIGHT_FLUSH:
                    result = Compare_Straight(bestCardsFirstPlayer, bestCardsSecondPlayer);
                    break;
                case FOUR_OF_A_KIND:
                    result = Compare_Four(bestCardsFirstPlayer, bestCardsSecondPlayer);
                    break;
                case FULL_HOUSE:
                    result = Compare_Full_House(bestCardsFirstPlayer, bestCardsSecondPlayer);
                    break;
                case FLUSH:
                    result = Compare_All_Card(bestCardsFirstPlayer, bestCardsSecondPlayer);
                    break;
                case STRAIGHT:
                    result = Compare_Straight(bestCardsFirstPlayer, bestCardsSecondPlayer);
                    break;
                case THREE_OF_A_KIND:
                    result = Compare_Three_Cards(bestCardsFirstPlayer, bestCardsSecondPlayer);
                    break;
                case TWO_PAIR:
                    result = Compare_Two_Pair(bestCardsFirstPlayer, bestCardsSecondPlayer);
                    break;
                case ONE_PAIR:
                    result = Compare_One_Pair(bestCardsFirstPlayer, bestCardsSecondPlayer);
                    break;
                case HIGH_CARD:
                    result = Compare_All_Card(bestCardsFirstPlayer, bestCardsSecondPlayer);
                    break;
                default:
                    break;
            }
        }
        setResultFirstPlayer(typeBestCardsFirstPlayer.toString());
        setResultSecondPlayer(typeBestCardsSecondPlayer.toString());
        return result;
    };

    protected void Distribute_Cards() {
        for (int i = 0; i < NUMBER_CARD_FOR_PLAYER; i++) {
            Card card = Pop_Up_Card();
            bot.cardOwned.add(card);
            card = Pop_Up_Card();
            player_ingame.cardOwned.add(card);
        }

        for (int i = 0; i < NUMBER_CARD_FOR_TABLE; i++) {
            Card card = Pop_Up_Card();
            tableOwner.cardOwned.add(card);
        }
    }

    protected enum Type_Poker_Cards {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        STRAIGHT_FLUSH,
        ROYAL_FLUSH
    };

    protected void Remove_Vector(Wrap_Vector_Cards wrapVector, Vector<Card> remove) {
        wrapVector.cards.removeAll(remove);
    }

    protected boolean Get_Best_Card(Vector<Card> cardsInput, Wrap_Vector_Cards output_Vector) {
        Collections.sort(cardsInput, (a, b) -> (a.getNumber(true).ordinal() > b.getNumber(true).ordinal()) ? -1 : 1);
        output_Vector.cards.add(cardsInput.get(0));
        return true;
    }

    protected boolean Get_Best_One_Pair(Vector<Card> cardsInput, Wrap_Vector_Cards output_Vector) {
        boolean result = false;
        Vector<Card> cardsResult = new Vector<Card>();
        output_Vector.cards.clear();
        for (int i = 0; i < cardsInput.size(); i++) {
            for (int j = i + 1; j < cardsInput.size(); j++) {
                if (cardsInput.get(i).getNumber() == cardsInput.get(j).getNumber()) {
                    result = true;
                    cardsResult.add(cardsInput.get(i));
                    cardsResult.add(cardsInput.get(j));
                    break;
                }
            }
        }

        if (cardsResult.size() == 6) {
            if ((cardsResult.get(0).getNumber(true).ordinal() > cardsResult.get(2).getNumber(true).ordinal()) &&
                    (cardsResult.get(0).getNumber(true).ordinal() > cardsResult.get(4).getNumber(true).ordinal())) {
                output_Vector.cards.add(cardsResult.get(0));
                output_Vector.cards.add(cardsResult.get(1));
            } else if (cardsResult.get(2).getNumber(true).ordinal() > cardsResult.get(4).getNumber(true).ordinal()) {
                output_Vector.cards.add(cardsResult.get(2));
                output_Vector.cards.add(cardsResult.get(3));
            } else {
                output_Vector.cards.add(cardsResult.get(4));
                output_Vector.cards.add(cardsResult.get(5));
            }
        } else if (cardsResult.size() == 4) {
            if (cardsResult.get(0).getNumber(true).ordinal() >= cardsResult.get(2).getNumber(true).ordinal()) {
                output_Vector.cards.add(cardsResult.get(0));
                output_Vector.cards.add(cardsResult.get(1));
            } else {
                output_Vector.cards.add(cardsResult.get(2));
                output_Vector.cards.add(cardsResult.get(3));
            }
        } else if (cardsResult.size() == 2) {
            output_Vector.cards = cardsResult;
        } else {

        }
        return result;
    }

    protected boolean Get_Best_Two_Pair(Vector<Card> cardsInput, Wrap_Vector_Cards output_Vector) {
        boolean result = false;
        Wrap_Vector_Cards wrapPairFirst = new Wrap_Vector_Cards();
        Wrap_Vector_Cards wrapPairSecond = new Wrap_Vector_Cards();
        Wrap_Vector_Cards wrapCardInput = new Wrap_Vector_Cards(cardsInput);
        output_Vector.cards.clear();
        if (Get_Best_One_Pair(cardsInput, wrapPairFirst)) {
            Remove_Vector(wrapCardInput, wrapPairFirst.cards);
            if (Get_Best_One_Pair(wrapCardInput.cards, wrapPairSecond)) {
                output_Vector.cards = wrapPairFirst.cards;
                output_Vector.cards.add(wrapPairSecond.cards.get(0));
                output_Vector.cards.add(wrapPairSecond.cards.get(1));
                result = true;
            }
        }
        return result;
    }

    protected boolean Get_Best_Three_Of_A_Kind(Vector<Card> cardsInput, Wrap_Vector_Cards output_Vector) {
        boolean result = false;
        boolean result_step_1 = false;
        Vector<Card> cardsResult = new Vector<Card>();
        Card cardSecond = new Card();
        for (int i = 0; i < cardsInput.size(); i++) {
            for (int j = i + 1; j != cardsInput.size(); j++) {
                if (cardsInput.get(i).getNumber() == cardsInput.get(j).getNumber()) {
                    if (result_step_1 == false) {
                        cardSecond = cardsInput.get(j);
                        result_step_1 = true;
                    } else {
                        cardsResult.add(cardsInput.get(i));
                        cardsResult.add(cardSecond);
                        cardsResult.add(cardsInput.get(j));
                        break;
                    }
                }
            }
            result_step_1 = false;
        }

        if (cardsResult.size() == 6) {
            result = true;
            if (cardsResult.get(0).getNumber(true).ordinal() > cardsResult.get(3).getNumber(true).ordinal()) {
                output_Vector.cards.add(cardsResult.get(0));
                output_Vector.cards.add(cardsResult.get(1));
                output_Vector.cards.add(cardsResult.get(2));
            } else {
                output_Vector.cards.add(cardsResult.get(3));
                output_Vector.cards.add(cardsResult.get(4));
                output_Vector.cards.add(cardsResult.get(5));
            }
        } else if (cardsResult.size() == 3) {
            result = true;
            output_Vector.cards = cardsResult;
        } else {

        }
        return result;
    }

    protected boolean Get_Best_Straight(Vector<Card> cardsInput, Wrap_Vector_Cards output_Vector) {
        boolean result = false;
        Vector<Card> cardsInput_2 = new Vector<Card>(cardsInput);
        Vector<Card> cardsInput_3 = new Vector<Card>(cardsInput);
        for (int i = 0; i < cardsInput_3.size(); i++) {
            for (int j = 1; j < cardsInput_2.size(); j++) {
                if ((cardsInput_2.get(j).getNumber() == cardsInput_3.get(i).getNumber())
                        && (cardsInput_2.get(j).getMaterial() != cardsInput_3.get(i).getMaterial())) {
                    cardsInput_2.remove(j);
                    j--;
                }
            }
        }

        Collections.sort(cardsInput_2, (a, b) -> (a.getNumber(true).ordinal() < b.getNumber(true).ordinal()) ? -1 : 1);

        if (cardsInput_2.size() < 5) {
            result = false;
        } else if (cardsInput_2.size() == 5) {
            if (cardsInput_2.get(4).getNumber().ordinal() == (cardsInput_2.get(0).getNumber().ordinal() + 4)) {
                result = true;
                output_Vector.cards = new Vector<Card>(cardsInput_2);
            }
        } else if (cardsInput_2.size() == 6) {
            if ((cardsInput_2.get(0).getNumber() == Card.Type.ACE) && (cardsInput_2.get(2).getNumber() == Card.Type.TEN)) {
                for (int i=5;i>0;i--){
                    output_Vector.cards.add(cardsInput_2.get(i));
                }
                result = true;
            } else {
                if (cardsInput_2.get(5).getNumber().ordinal() == (cardsInput_2.get(1).getNumber().ordinal() + 4)) {
                    for (int i=5;i>0;i--){
                        output_Vector.cards.add(cardsInput_2.get(i));
                    }
                    result = true;
                }

                else if (cardsInput_2.get(4).getNumber().ordinal() == (cardsInput_2.get(0).getNumber().ordinal() + 4)) {
                    for (int i=4;i>=0;i--){
                        output_Vector.cards.add(cardsInput_2.get(i));
                    }
                    result = true;
                }
            }

        } else if (cardsInput_2.size() == 7) {
            if (cardsInput_2.get(6).getNumber().ordinal() == (cardsInput_2.get(2).getNumber().ordinal() + 4)) {
                for (int i=6;i>1;i--){
                    output_Vector.cards.add(cardsInput_2.get(i));
                }
                result = true;
            } else if (cardsInput_2.get(5).getNumber().ordinal() == (cardsInput_2.get(1).getNumber().ordinal() + 4)) {
                for (int i=5;i>0;i--){
                    output_Vector.cards.add(cardsInput.get(i));
                }
                result = true;
            } else if (cardsInput_2.get(4).getNumber().ordinal() == (cardsInput_2.get(0).getNumber().ordinal() + 4)) {
                for (int i=4;i>=0;i--){
                    output_Vector.cards.add(cardsInput_2.get(i));
                }
                result = true;
            }
        }

        return result;
    }

    protected boolean Get_Best_Flush(Vector<Card> cardsInput, Wrap_Vector_Cards output_Vector) {
        boolean result = false;
        Vector<Card> resultCardsSpade = new Vector<Card>();
        Vector<Card> resultCardsClube = new Vector<Card>();
        Vector<Card> resultCardsDiamond = new Vector<Card>();
        Vector<Card> resultCardsHeart = new Vector<Card>();
        for (Card itr : cardsInput) {
            switch (itr.getMaterial()) {
                case SPADE:
                    resultCardsSpade.add(itr);
                    break;
                case CLUB:
                    resultCardsClube.add(itr);
                    break;
                case DIAMOND:
                    resultCardsDiamond.add(itr);
                    break;
                case HEART:
                    resultCardsHeart.add(itr);
                    break;
                default:
                    break;
            }
        }
        if (resultCardsSpade.size() >= 5) {
            Collections.sort(resultCardsSpade,
                    (a, b) -> (a.getNumber(true).ordinal() > b.getNumber(true).ordinal()) ? -1 : 1);
            for (int i = 0; i < 5; i++) {
                output_Vector.cards.add(resultCardsSpade.get(i));
            }
            result = true;
        } else if (resultCardsClube.size() >= 5) {
            Collections.sort(resultCardsClube,
                    (a, b) -> (a.getNumber(true).ordinal() > b.getNumber(true).ordinal()) ? -1 : 1);
            for (int i = 0; i < 5; i++) {
                output_Vector.cards.add(resultCardsClube.get(i));
            }
            result = true;
        } else if (resultCardsDiamond.size() >= 5) {
            Collections.sort(resultCardsDiamond,
                    (a, b) -> (a.getNumber(true).ordinal() > b.getNumber(true).ordinal()) ? -1 : 1);
            for (int i = 0; i < 5; i++) {
                output_Vector.cards.add(resultCardsDiamond.get(i));
            }
            result = true;
        } else if (resultCardsHeart.size() >= 5) {
            Collections.sort(resultCardsHeart,
                    (a, b) -> (a.getNumber(true).ordinal() > b.getNumber(true).ordinal()) ? -1 : 1);
            for (int i = 0; i < 5; i++) {
                output_Vector.cards.add(resultCardsHeart.get(i));
            }
            result = true;
        }

        return result;
    }

    protected boolean Get_Best_Full_House(Vector<Card> cardsInput, Wrap_Vector_Cards output_Vector) {
        boolean result = false;
        Wrap_Vector_Cards wrapThreeCards = new Wrap_Vector_Cards();
        Wrap_Vector_Cards wrapPairCards = new Wrap_Vector_Cards();
        Wrap_Vector_Cards wrapCardsInput = new Wrap_Vector_Cards(cardsInput);
        if (Get_Best_Three_Of_A_Kind(cardsInput, wrapThreeCards)) {
            Remove_Vector(wrapCardsInput, wrapThreeCards.cards);
            if (Get_Best_One_Pair(wrapCardsInput.cards, wrapPairCards)) {
                output_Vector.cards = wrapThreeCards.cards;
                output_Vector.cards.add(wrapPairCards.cards.get(1));
                output_Vector.cards.add(wrapPairCards.cards.get(0));
                result = true;
            }
        }
        return result;
    }

    protected boolean Get_Four_Of_A_Kind(Vector<Card> cardsInput, Wrap_Vector_Cards output_Vector) {
        boolean result_step_1 = false;
        boolean result_step_2 = false;
        boolean result_step_3 = false;
        for (int i = 0; i < cardsInput.size(); i++) {
            for (int j = i + 1; j < cardsInput.size(); j++) {
                if (cardsInput.get(i).getNumber() == cardsInput.get(j).getNumber()) {
                    if (!result_step_1) {
                        output_Vector.cards.add(cardsInput.get(i));
                        output_Vector.cards.add(cardsInput.get(j));
                        result_step_1 = true;
                    } else if (!result_step_2) {
                        output_Vector.cards.add(cardsInput.get(j));
                        result_step_2 = true;
                    } else {
                        output_Vector.cards.add(cardsInput.get(j));
                        result_step_3 = true;
                        break;
                    }
                }
            }
            if (result_step_3) {
                break;
            } else {
                output_Vector.cards.clear();
                result_step_1 = false;
                result_step_2 = false;
            }
        }
        return result_step_3;
    }

    protected boolean Get_Straight_Flush(Vector<Card> cardsInput, Wrap_Vector_Cards output_Vector) {
        boolean result = false;
        Wrap_Vector_Cards wrapFlush = new Wrap_Vector_Cards();
        Wrap_Vector_Cards wrapFlushStraight = new Wrap_Vector_Cards();
        if (Get_Best_Flush(cardsInput, wrapFlush)) {
            if (Get_Best_Straight(wrapFlush.cards, wrapFlushStraight)) {
                result = true;
                output_Vector.cards = wrapFlushStraight.cards;
            }
        }
        return result;
    }

    protected boolean Get_Royal_Flush(Vector<Card> cardsInput, Wrap_Vector_Cards output_Vector) {
        boolean result = false;
        Wrap_Vector_Cards wrapRoyal = new Wrap_Vector_Cards();
        if (Get_Straight_Flush(cardsInput, wrapRoyal)) {
            if (wrapRoyal.cards.get(0).getNumber() == Card.Type.ACE
                    && wrapRoyal.cards.get(0).getMaterial() == Card.Material.SPADE) {
                output_Vector.cards = wrapRoyal.cards;
                result = true;
            }
        }
        return result;
    }

    protected EndGame Compare_Straight(Vector<Card> firstPlayer, Vector<Card> secondPlayer) {
        Card_Game.EndGame result;
        if (firstPlayer.get(0).getNumber().ordinal() < secondPlayer.get(0).getNumber().ordinal()) {
            result = Card_Game.EndGame.WIN;
        } else if (firstPlayer.get(0).getNumber().ordinal() > secondPlayer.get(0).getNumber().ordinal()) {
            result = Card_Game.EndGame.LOSE;
        } else {
            result = Card_Game.EndGame.DRAW;
        }
        return result;
    }

    protected EndGame Compare_Four(Vector<Card> firstPlayer, Vector<Card> secondPlayer) {
        Card_Game.EndGame result;
        if (firstPlayer.get(0).getNumber(true).ordinal() < secondPlayer.get(0).getNumber(true).ordinal()) {
            result = Card_Game.EndGame.WIN;
        } else if (firstPlayer.get(0).getNumber(true).ordinal() > secondPlayer.get(0).getNumber(true).ordinal()) {
            result = Card_Game.EndGame.LOSE;
        } else {
            if (firstPlayer.get(4).getNumber(true).ordinal() < secondPlayer.get(4).getNumber(true).ordinal()) {
                result = Card_Game.EndGame.WIN;
            } else if (firstPlayer.get(4).getNumber(true).ordinal() > secondPlayer.get(4).getNumber(true).ordinal()) {
                result = Card_Game.EndGame.LOSE;
            } else {
                result = Card_Game.EndGame.DRAW;
            }
        }
        return result;
    }

    protected EndGame Compare_Full_House(Vector<Card> firstPlayer, Vector<Card> secondPlayer) {
        Card_Game.EndGame result;
        if (firstPlayer.get(0).getNumber(true).ordinal() < secondPlayer.get(0).getNumber(true).ordinal()) {
            result = Card_Game.EndGame.WIN;
        } else if (firstPlayer.get(0).getNumber(true).ordinal() > secondPlayer.get(0).getNumber(true).ordinal()) {
            result = Card_Game.EndGame.LOSE;
        } else {
            if (firstPlayer.get(3).getNumber(true).ordinal() < secondPlayer.get(3).getNumber(true).ordinal()) {
                result = Card_Game.EndGame.WIN;
            } else if (firstPlayer.get(3).getNumber(true).ordinal() > secondPlayer.get(3).getNumber(true).ordinal()) {
                result = Card_Game.EndGame.LOSE;
            } else {
                result = Card_Game.EndGame.DRAW;
            }
        }
        return result;
    }

    protected EndGame Compare_All_Card(Vector<Card> firstPlayer, Vector<Card> secondPlayer) {
        Card_Game.EndGame result = Card_Game.EndGame.DRAW;
        for (int i = 0; i < 5; i++) {
            if (firstPlayer.get(i).getNumber(true).ordinal() < secondPlayer.get(i).getNumber(true).ordinal()) {
                result = Card_Game.EndGame.WIN;
                break;
            } else if (firstPlayer.get(i).getNumber(true).ordinal() > secondPlayer.get(i).getNumber(true).ordinal()) {
                result = Card_Game.EndGame.LOSE;
                break;
            }
        }
        return result;
    }

    protected EndGame Compare_Three_Cards(Vector<Card> firstPlayer, Vector<Card> secondPlayer) {
        Card_Game.EndGame result;
        if (firstPlayer.get(0).getNumber(true).ordinal() < secondPlayer.get(0).getNumber(true).ordinal()) {
            result = Card_Game.EndGame.WIN;
        } else if (firstPlayer.get(0).getNumber(true).ordinal() > secondPlayer.get(0).getNumber(true).ordinal()) {
            result = Card_Game.EndGame.LOSE;
        } else {
            if (firstPlayer.get(3).getNumber(true).ordinal() < secondPlayer.get(3).getNumber(true).ordinal()) {
                result = Card_Game.EndGame.WIN;
            } else if (firstPlayer.get(3).getNumber(true).ordinal() > secondPlayer.get(3).getNumber(true).ordinal()) {
                result = Card_Game.EndGame.LOSE;
            } else {
                if (firstPlayer.get(4).getNumber(true).ordinal() < secondPlayer.get(4).getNumber(true).ordinal()) {
                    result = Card_Game.EndGame.WIN;
                } else if (firstPlayer.get(4).getNumber().ordinal() > secondPlayer.get(4).getNumber().ordinal()) {
                    result = Card_Game.EndGame.LOSE;
                } else {
                    result = Card_Game.EndGame.DRAW;
                }
            }
        }
        return result;
    }

    protected EndGame Compare_Two_Pair(Vector<Card> firstPlayer, Vector<Card> secondPlayer) {
        Card_Game.EndGame result;
        if (firstPlayer.get(0).getNumber(true).ordinal() < secondPlayer.get(0).getNumber(true).ordinal()) {
            result = Card_Game.EndGame.WIN;
        } else if (firstPlayer.get(0).getNumber(true).ordinal() > secondPlayer.get(0).getNumber(true).ordinal()) {
            result = Card_Game.EndGame.LOSE;
        } else {
            if (firstPlayer.get(2).getNumber(true).ordinal() < secondPlayer.get(2).getNumber(true).ordinal()) {
                result = Card_Game.EndGame.WIN;
            } else if (firstPlayer.get(2).getNumber(true).ordinal() > secondPlayer.get(2).getNumber(true).ordinal()) {
                result = Card_Game.EndGame.LOSE;
            } else {
                if (firstPlayer.get(4).getNumber(true).ordinal() < secondPlayer.get(4).getNumber(true).ordinal()) {
                    result = Card_Game.EndGame.WIN;
                } else if (firstPlayer.get(4).getNumber(true).ordinal() > secondPlayer.get(4).getNumber(true)
                        .ordinal()) {
                    result = Card_Game.EndGame.LOSE;
                } else {
                    result = Card_Game.EndGame.DRAW;
                }
            }
        }
        return result;
    }

    protected EndGame Compare_One_Pair(Vector<Card> firstPlayer, Vector<Card> secondPlayer) {
        Card_Game.EndGame result;
        if (firstPlayer.get(0).getNumber(true).ordinal() < secondPlayer.get(0).getNumber(true).ordinal()) {
            result = Card_Game.EndGame.WIN;
        } else if (firstPlayer.get(0).getNumber(true).ordinal() > secondPlayer.get(0).getNumber(true).ordinal()) {
            result = Card_Game.EndGame.LOSE;
        } else {
            if (firstPlayer.get(2).getNumber(true).ordinal() < secondPlayer.get(2).getNumber(true).ordinal()) {
                result = Card_Game.EndGame.WIN;
            } else if (firstPlayer.get(2).getNumber(true).ordinal() > secondPlayer.get(2).getNumber(true).ordinal()) {
                result = Card_Game.EndGame.LOSE;
            } else {
                if (firstPlayer.get(3).getNumber(true).ordinal() < secondPlayer.get(3).getNumber(true).ordinal()) {
                    result = Card_Game.EndGame.WIN;
                } else if (firstPlayer.get(3).getNumber(true).ordinal() > secondPlayer.get(3).getNumber(true)
                        .ordinal()) {
                    result = Card_Game.EndGame.LOSE;
                } else {
                    if (firstPlayer.get(4).getNumber(true).ordinal() < secondPlayer.get(4).getNumber(true).ordinal()) {
                        result = Card_Game.EndGame.WIN;
                    } else if (firstPlayer.get(4).getNumber(true).ordinal() > secondPlayer.get(4).getNumber(true)
                            .ordinal()) {
                        result = Card_Game.EndGame.LOSE;
                    } else {
                        result = Card_Game.EndGame.DRAW;
                    }
                }
            }
        }
        return result;
    }

    protected Type_Poker_Cards Get_Best_Cards(Vector<Card> cardsInput, Wrap_Vector_Cards output_Vector) {
        Poker.Type_Poker_Cards resultType;
        Wrap_Vector_Cards wrapCardsInput = new Wrap_Vector_Cards(cardsInput);
        Wrap_Vector_Cards wrapResultCards = new Wrap_Vector_Cards();
        if (Get_Royal_Flush(cardsInput, wrapResultCards)) {
            resultType = Type_Poker_Cards.ROYAL_FLUSH;
        } else if (Get_Straight_Flush(cardsInput, wrapResultCards)) {
            resultType = Type_Poker_Cards.STRAIGHT_FLUSH;
        } else if (Get_Four_Of_A_Kind(cardsInput, wrapResultCards)) {
            resultType = Type_Poker_Cards.FOUR_OF_A_KIND;
            Vector<Card> fourCards = wrapResultCards.cards;
            Wrap_Vector_Cards cardLastOne = new Wrap_Vector_Cards();
            Remove_Vector(wrapCardsInput, fourCards);
            Get_Best_Card(wrapCardsInput.cards, cardLastOne);
            wrapResultCards.cards.add(cardLastOne.cards.get(0));
        } else if (Get_Best_Full_House(cardsInput, wrapResultCards)) {
            resultType = Type_Poker_Cards.FULL_HOUSE;
        } else if (Get_Best_Flush(cardsInput, wrapResultCards)) {
            resultType = Type_Poker_Cards.FLUSH;
        } else if (Get_Best_Straight(cardsInput, wrapResultCards)) {
            resultType = Type_Poker_Cards.STRAIGHT;
        } else if (Get_Best_Three_Of_A_Kind(cardsInput, wrapResultCards)) {
            resultType = Type_Poker_Cards.THREE_OF_A_KIND;
            Wrap_Vector_Cards cardLastOne = new Wrap_Vector_Cards();
            Remove_Vector(wrapCardsInput, wrapResultCards.cards);
            Get_Best_Card(wrapCardsInput.cards, cardLastOne);
            wrapResultCards.cards.add(cardLastOne.cards.get(0));
            Remove_Vector(wrapCardsInput, cardLastOne.cards);
            cardLastOne.cards.clear();
            Get_Best_Card(wrapCardsInput.cards, cardLastOne);
            wrapResultCards.cards.add(cardLastOne.cards.get(0));
        } else if (Get_Best_Two_Pair(cardsInput, wrapResultCards)) {
            resultType = Type_Poker_Cards.TWO_PAIR;
            Vector<Card> twoPair = wrapResultCards.cards;
            Wrap_Vector_Cards cardLastOne = new Wrap_Vector_Cards();
            Remove_Vector(wrapCardsInput, twoPair);
            Get_Best_Card(wrapCardsInput.cards, cardLastOne);
            wrapResultCards.cards.add(cardLastOne.cards.get(0));
        } else if (Get_Best_One_Pair(cardsInput, wrapResultCards)) {
            resultType = Type_Poker_Cards.ONE_PAIR;
            Vector<Card> pair = wrapResultCards.cards;
            Wrap_Vector_Cards cardLastOne = new Wrap_Vector_Cards();
            Remove_Vector(wrapCardsInput, pair);
            for (int i = 0; i < 3; i++) {
                Get_Best_Card(wrapCardsInput.cards, cardLastOne);
                wrapResultCards.cards.add(cardLastOne.cards.get(0));
                Remove_Vector(wrapCardsInput, cardLastOne.cards);
                cardLastOne.cards.clear();
            }
        } else {
            resultType = Type_Poker_Cards.HIGH_CARD;
            Wrap_Vector_Cards cardLastOne = new Wrap_Vector_Cards();
            for (int i = 0; i < 5; i++) {
                Get_Best_Card(wrapCardsInput.cards, cardLastOne);
                wrapResultCards.cards.add(cardLastOne.cards.get(0));
                Remove_Vector(wrapCardsInput, cardLastOne.cards);
                cardLastOne.cards.clear();
            }
        }
        output_Vector.cards = wrapResultCards.cards;

        for (Card card:output_Vector.cards) {
            Log.e(TAG, "Final: " + card.getNumber()+card.getMaterial());
        }
        Log.e(TAG, resultType.toString());
        return resultType;
    }

    protected class Wrap_Vector_Cards {
        public Vector<Card> cards;

        public Wrap_Vector_Cards(Vector<Card> input) {
            cards = new Vector<>(input);
        }

        public Wrap_Vector_Cards() {
            cards = new Vector<>();
        }
    }
}

