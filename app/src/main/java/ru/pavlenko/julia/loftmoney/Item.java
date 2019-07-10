package ru.pavlenko.julia.loftmoney;

public class Item {
    private String name;
    private String price;

    public Item(String name, int price) {
        this.name = name;
        this.price = String.valueOf(price);
    }

    public String getPrice() {

        return price;
    }

    public void setPrice(int price) {
        this.price = String.valueOf(price);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
