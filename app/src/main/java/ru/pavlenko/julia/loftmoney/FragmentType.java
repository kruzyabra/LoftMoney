package ru.pavlenko.julia.loftmoney;

public enum FragmentType {
    outcome(R.color.item_price_text_color_outcome),
    income(R.color.item_price_text_color_income);

    private int mTextColor;

    FragmentType(int textColor) {
        this.mTextColor = textColor;
    }

    public int getTextColor() {
        return mTextColor;
    }
}
