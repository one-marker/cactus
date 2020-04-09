package com.meCactus;

public class CardModel {
    private int id;
    private String title;
    private String text;
    private Boolean check = false;



    public CardModel(String title) {
        this.title = title;
    }
    public CardModel(int id, String title, String text, Integer check) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.check = intToBoolean(check);
    }
    public CardModel( String title, String text, Integer check) {

        this.title = title;
        this.text = text;
        this.check = intToBoolean(check);
    }
    public int getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public String getTitle() {
        return title;
    }
    //public boolean getChecked() {
//        return check;
//    }

    public void setText(String text) {
        this.text = text;
    }
    public void setChecked(Boolean check) {
        this.check = check;
    }

    public Boolean getCheck() {
        return check;
    }
    private boolean intToBoolean(int input) {
        if((input==0)||(input==1)) {
            return input!=0;
        }else {
            throw new IllegalArgumentException("Входное значение может быть равно только 0 или 1 !");
        }
    }

    @Override
    public String toString() {
        return "CardModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", check=" + check +
                '}';
    }
}
