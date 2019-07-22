package com.gibrielspiteri.quoteme;

public class Quote {
    String theQuote;
    String author;
    String genre;
    String id;
    int index;

    public Quote(){}

    public Quote(String theQuote, String author, String genre, String id, int index){
        this.theQuote = theQuote;
        this.author = author;
        this.genre = genre;
        this.id = id;
        this.index = index;
    }

    public String getTheQuote() {
        return theQuote;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }
}
