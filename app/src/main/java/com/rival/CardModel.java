package com.rival;

public class CardModel {
    private String text;
    private Boolean check = false;

    public CardModel(String text) {
        this.text = text;
    }
    public CardModel(Boolean check, String text) {
        this.text = text;
        this.check = check;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public boolean getChecked() {
        return check;
    }

    public void setChecked(Boolean check) {
        this.check = check;
    }
}
