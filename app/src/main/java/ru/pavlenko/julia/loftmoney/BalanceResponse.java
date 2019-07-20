package ru.pavlenko.julia.loftmoney;

import com.google.gson.annotations.SerializedName;

class BalanceResponse {

    private String status;

    @SerializedName("total_expenses")
    private float totalExpenses;

    @SerializedName("total_income")
    private float totalIncome;

    public float getTotalExpenses() {
        return totalExpenses;
    }

    public float getTotalIncome() {
        return totalIncome;
    }
}
