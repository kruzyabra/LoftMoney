package ru.pavlenko.julia.loftmoney;

public class Item {
    private String title;
    private String price;

    public Item(String title, int price) {
        this.title = title;
        this.price = String.valueOf(price);
    }

    public String getPrice() {

        return price;
    }

    public void setPrice(int price) {
        this.price = String.valueOf(price);
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
