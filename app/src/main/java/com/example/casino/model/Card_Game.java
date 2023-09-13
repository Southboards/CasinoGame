package com.example.casino.model;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Card_Game {

    public static final int NUMBER_CARD = 52;

    public Card_Game(Player playerInput) {
        moneyBet = 0;
        Create_Card();
        bot = new Player_In_Game("bot");
        player_ingame = new Player_In_Game(playerInput.getName());
        tableOwner = new Player_In_Game("table");
    }

    public void Run() {
        Shuffle_Cards();
        Distribute_Cards();
    }

    public void Shuffle_Cards() {
        for (int i = 0; i < cards.size(); i++) {
            int randomValue = ThreadLocalRandom.current().nextInt(0, cards.size());
            Card temp = cards.get(i);
            cards.set(i, cards.get(randomValue));
            cards.set(randomValue, temp);
        }
    }

    public enum EndGame {
        WIN,
        DRAW,
        LOSE
    };

    public abstract EndGame Check_Win();

    public int moneyBet;

    protected Vector<Card> cards;

    protected abstract void Distribute_Cards();

    protected void Create_Card() {
        cards = new Vector<>();
        for (int i = 0; i < 13; i++) {
            Card.Type type = Card.Type.values()[i];
            for (int j = 0; j < 4; j++) {
                Card.Material material = Card.Material.values()[j];
                Card card = new Card(material, type);
                cards.add(card);
            }
        }
    }

    protected Card Pop_Up_Card() {
        Card card = cards.lastElement();
        cards.remove(card);
        return card;
    }

    public class Player_In_Game {
        public Player_In_Game(String nameInput){
            name = nameInput;
            cardOwned = new Vector<>();
        }
        public String name;
        public Vector<Card> cardOwned;
    };

    public Player_In_Game bot;
    public Player_In_Game player_ingame;
    public Player_In_Game tableOwner;
}
