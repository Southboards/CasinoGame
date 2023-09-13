package com.example.casino.model;

public class Player {
    public static final int BRONZE_RANK_LIMIT = 5000;
    public static final int SILVER_RANK_LIMIT = 10000;
    public static final int GOLD_RANK_LIMIT = 50000;
    public static final int PLATINUM_RANK_LIMIT = 200000;
    public static final int DIAMOND_RANK_LIMIT = 500000;
    public static final int MASTER_RANK_LIMIT = 2000000;
    public static final int GRANDMASTER_RANK_LIMIT = 5000000;
    public static final int CHALLENGER_RANK_LIMIT = 10000000;

    private String name;
    private int money;

    public Player(String nameInput) {
        name = nameInput;
        // money = Databases::Get_Instance().Get_Amount(name);
        money = 10000;
    }

    public enum Rank {
        BRONZE,
        SILVER,
        GOLD,
        PLATINUM,
        DIAMOND,
        MASTER,
        GRANDMASTER,
        CHALLENGER,
        NONE
    };

    public void setName(String value) {
        name = value;
    }

    public String getName() {
        return name;
    }

    public void setMoney(int value) {
        money = value;
        // Databases::Get_Instance().Update_Money_Player(*this);
    }

    public int getMoney() {
        return money;
    }

    public Rank Get_Rank() {
        Rank rank;
        if (money >= CHALLENGER_RANK_LIMIT) {
            rank = Rank.CHALLENGER;
        } else if (money >= GRANDMASTER_RANK_LIMIT) {
            rank = Rank.GRANDMASTER;
        } else if (money >= MASTER_RANK_LIMIT) {
            rank = Rank.MASTER;
        } else if (money >= DIAMOND_RANK_LIMIT) {
            rank = Rank.DIAMOND;
        } else if (money >= PLATINUM_RANK_LIMIT) {
            rank = Rank.PLATINUM;
        } else if (money >= GOLD_RANK_LIMIT) {
            rank = Rank.GOLD;
        } else if (money >= SILVER_RANK_LIMIT) {
            rank = Rank.SILVER;
        } else if (money >= 0) {
            rank = Rank.BRONZE;
        } else {
            rank = Rank.NONE;
        }
        return rank;
    }
}
