package ru.pavlenko.julia.loftmoney;

public enum ActionModeColors {
    actionModeOn(
            R.color.colorDarkBlueGray,
            R.color.colorWhite,
            R.color.tabs_unselected_text_color),
    actionModeOff(
            R.color.colorPrimary,
            R.color.colorWhite,
            R.color.tabs_with_action_mode_on);

    private int mBackgroundColor;
    private int mTabsSelectedColor;
    private int mTabsNormalColor;

    ActionModeColors(int backgroundColor, int tabsSelectedColor, int tabsNormalColor) {
        mBackgroundColor = backgroundColor;
        mTabsSelectedColor = tabsSelectedColor;
        mTabsNormalColor = tabsNormalColor;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public int getTabsSelectedColor() {
        return mTabsSelectedColor;
    }

    public int getTabsNormalColor() {
        return mTabsNormalColor;
    }
}
