package ru.pavlenko.julia.loftmoney;

public enum FragmentType {
    expense(R.color.item_price_text_color_outcome, "expense"),
    income(R.color.item_price_text_color_income, "income");

    private int mTextColor;
    private String mTypeName;

    FragmentType(int textColor, String typeName) {
        this.mTextColor = textColor;
        this.mTypeName = typeName;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public String getTypeName() {
        return mTypeName;
    }
}
