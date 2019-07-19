package ru.pavlenko.julia.loftmoney;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Item {
    private String name;
    private String price;
    private int id;

    @SerializedName("created_at")
    private String createdAt;

    private Date createAtDate;

    public Item(String name, int price, String createdAt, int id) {
        this.name = name;
        this.price = String.valueOf(price);
        this.createdAt = createdAt;
        this.id = id;
        setCreateAtDate();
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

    public String getCreatedAt() {
        return createdAt;
    }

    public Date getCreateAtDate() {
        return createAtDate;
    }

    public void setCreateAtDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            this.createAtDate = simpleDateFormat.parse(this.createdAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }
}
