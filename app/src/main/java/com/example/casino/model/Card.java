package com.example.casino.model;

public class Card {

    enum Material {
        SPADE,
        CLUB,
        DIAMOND,
        HEART,
        NONE_MATERIAL
    };

    enum Type {
        ACE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        NONE_TYPE
    };

    private Material material;
    private Type number;
    private Boolean status;
    private String link;

    public Card() {
        material = Material.NONE_MATERIAL;
        number = Type.NONE_TYPE;
        status = false;
    }

    public Card(Material materialInput, Type numberInput) {
        material = materialInput;
        number = numberInput;
        status = false;
        Update_Link();
    }

    public Material getMaterial() {
        return material;
    }

    public Type getNumber() {
        return number;
    }

    public Type getNumber(Boolean usingAce) {
        if (usingAce && (number == Type.ACE)) {
            return Type.NONE_TYPE;
        }
        return number;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean value) {
        status = value;
    }

    public void Upside_Card() {
        status = !status;
    }

    public String Get_Link() {
        String cardLink = Get_Link_None_Card();
        if (status) {
            cardLink = link;
        }
        return cardLink;
    }

    private String Get_Link_None_Card() {
        String card_link = "none_card";
        return card_link;
    }

    private void Update_Link() {
        String num, mater;
        switch (number) {
            case ACE:
                num = "ace";
                break;
            case TWO:
                num = "two";
                break;
            case THREE:
                num = "three";
                break;
            case FOUR:
                num = "four";
                break;
            case FIVE:
                num = "five";
                break;
            case SIX:
                num = "six";
                break;
            case SEVEN:
                num = "seven";
                break;
            case EIGHT:
                num = "eight";
                break;
            case NINE:
                num = "nine";
                break;
            case TEN:
                num = "ten";
                break;
            case JACK:
                num = "jack";
                break;
            case QUEEN:
                num = "queen";
                break;
            case KING:
                num = "king";
                break;
            default:
                num = "";
                break;
        }

        switch (material) {
            case SPADE:
                mater = "1";
                break;
            case CLUB:
                mater = "2";
                break;
            case DIAMOND:
                mater = "3";
                break;
            case HEART:
                mater = "4";
                break;
            default:
                mater = "";
                break;
        }

        link = num + "_" + mater;
    }
}
