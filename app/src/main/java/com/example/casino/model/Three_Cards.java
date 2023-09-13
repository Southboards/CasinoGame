package com.example.casino.model;

public class Three_Cards extends Card_Game {

    // private final int NUMBER_PLAYER = 2;
    private final int NUMBER_CARD_FOR_PLAYER = 3;

    public Three_Cards(Player playerInput) {
        super(playerInput);
    }

    public EndGame Check_Win() {
        int pointBot, pointPlayer;
        pointBot = Calculate_Point(bot);
        pointPlayer = Calculate_Point(player_ingame);

        if (pointPlayer > pointBot) {
            return Card_Game.EndGame.WIN;
        } else if (pointPlayer == pointBot) {
            return Card_Game.EndGame.DRAW;
        } else {
            return Card_Game.EndGame.LOSE;
        }
    }

    protected void Distribute_Cards() {
        for (int i = 0; i < NUMBER_CARD_FOR_PLAYER; i++) {
            Card card = Pop_Up_Card();
            bot.cardOwned.add(card);
            card = Pop_Up_Card();
            player_ingame.cardOwned.add(card);
        }
    }

    protected int Calculate_Point(Player_In_Game player) {
        int point = 0;
        boolean royal = true;
        for (Card itr : player.cardOwned) {
            if (itr.getNumber().ordinal() <= 9) {
                point += itr.getNumber().ordinal() + 1;
                royal = false;
            } else {
                point += 10;
            }
        }

        if (royal) {
            point = 10;
        } else {
            point %= 10;
        }
        return point;
    }
}
