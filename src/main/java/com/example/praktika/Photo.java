package com.example.praktika;

public class Photo {
    private String name_tour;
    private String description_tour;
    private int price_tour;
    private String photo;
    public Photo(String name_tour, String description_tour, int price_tour, String photo) {
        this.name_tour = name_tour;
        this.description_tour = description_tour;
        this.price_tour = price_tour;
        this.photo = photo;
    }

    public String getname_tour() {
        return this.name_tour;
    }

    public String getdescription_tour() {return this.description_tour;}

    public int getprice_tour() {return this.price_tour;}
    public String photo() {return this.photo;}


}
