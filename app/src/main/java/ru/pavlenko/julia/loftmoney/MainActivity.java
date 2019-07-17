package ru.pavlenko.julia.loftmoney;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String AUTH_TOKEN = "authToken";

    Button authButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!TextUtils.isEmpty(getToken())) {
            startBudgetActivity();
        }

        authButton = findViewById(R.id.auth_button);

        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBudgetActivity();
            }
        });

        LoftApp loftApp = (LoftApp) getApplication();

        Api api = loftApp.getApi();

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Call<AuthResponse> authCall = api.auth(androidId);
        authCall.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(AUTH_TOKEN, response.body().getAuthToken());
                editor.commit();
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {

            }
        });
    }

    private void startBudgetActivity() {
        Intent intent = (new Intent(MainActivity.this, BudgetActivity.class)).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
        finish();
    }

    private String getToken() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        return sharedPreferences.getString(AUTH_TOKEN, "");
    }
}








