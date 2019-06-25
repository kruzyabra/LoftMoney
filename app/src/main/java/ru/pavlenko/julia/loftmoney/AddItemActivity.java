package ru.pavlenko.julia.loftmoney;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    EditText titleEditText;
    EditText priceEditText;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        addButton = findViewById(R.id.add_button);
        titleEditText = findViewById(R.id.title_input);
        priceEditText = findViewById(R.id.price_input);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeButtonTextColor();
            }
        };

        titleEditText.addTextChangedListener(textWatcher);
        priceEditText.addTextChangedListener(textWatcher);

    }

    private void changeButtonTextColor() {
        if (!TextUtils.isEmpty(titleEditText.getText()) && !TextUtils.isEmpty(priceEditText.getText())) {
            addButton.setTextColor(getResources().getColor(R.color.active_add_button_color));
        } else
        {
            addButton.setTextColor(getResources().getColor(R.color.hint_color));
        }
    }
}
