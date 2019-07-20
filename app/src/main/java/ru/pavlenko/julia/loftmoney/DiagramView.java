package ru.pavlenko.julia.loftmoney;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DiagramView extends View {
    final Paint expensePaint = new Paint();
    final Paint incomePaint = new Paint();

    private float expense;
    private float income;

    public DiagramView(Context context) {
        super(context);
        init();
    }

    public DiagramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiagramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DiagramView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        expensePaint.setColor(getContext().getResources().getColor(R.color.diagram_expense));
        incomePaint.setColor(getContext().getResources().getColor(R.color.diagram_income));
    }

    public void update(float expense, float income) {
        this.expense = expense;
        this.income = income;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float expenseAngle = 360.0f * expense / (expense + income);
        float incomeAngle = 360.0f * income / (expense + income);

        int space = 10;

        int size = Math.min(getWidth(), getHeight()) - space * 2;

        final int xMargin = (getWidth() - size) / 2;
        final int yMargin = (getHeight() - size) / 2;

        canvas.drawArc(xMargin - space, yMargin
            ,getWidth() - xMargin - space
            , getHeight() - yMargin
            , 180 - expenseAngle / 2
            , expenseAngle
            , true
            , expensePaint);

        canvas.drawArc(xMargin + space, yMargin
                ,getWidth() - xMargin + space
                , getHeight() - yMargin
                , 360 - incomeAngle / 2
                , incomeAngle
                , true
                , incomePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
