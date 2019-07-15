package ru.pavlenko.julia.loftmoney;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTitleAndPriceEmpty()) {
                    String title = titleEditText.getText().toString();
                    String price = priceEditText.getText().toString();

                    setResult(Activity.RESULT_OK, new Intent().putExtra("title", title)
                            .putExtra("price", price));
                    finish();
                } else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            getResources().getText(R.string.empty_item_add_warning),
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    private void changeButtonTextColor() {
        addButton.setEnabled(isTitleAndPriceEmpty());
    }

    private boolean isTitleAndPriceEmpty() {
        if (!TextUtils.isEmpty(titleEditText.getText()) && !TextUtils.isEmpty(priceEditText.getText())) {
            return true;
        } else
        {
            return false;
        }
    }
}
