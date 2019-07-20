package ru.pavlenko.julia.loftmoney;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalanceFragment extends Fragment {
    private Api mApi;

    private DiagramView mDiagramView;

    private TextView mAvailableFinanceTextView;
    private TextView mExpenseTextView;
    private TextView mIncomeTextView;

    @Override
    public void onStart() {
        super.onStart();

        getBalance();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApi = ((LoftApp) getActivity().getApplication()).getApi();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View balanceFragment = inflater.inflate(R.layout.balance_fragment, container, false);

        mAvailableFinanceTextView = balanceFragment.findViewById(R.id.available_finance);
        mExpenseTextView = balanceFragment.findViewById(R.id.expense_balance);
        mIncomeTextView = balanceFragment.findViewById(R.id.income_balance);

        mDiagramView = balanceFragment.findViewById(R.id.diagram_view);

        return balanceFragment;
    }

    public static BalanceFragment newInstance() {
        BalanceFragment fragment = new BalanceFragment();

        return fragment;
    }

    private void getBalance() {
        String authToken = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(MainActivity.AUTH_TOKEN, "");

        Call<BalanceResponse> call = mApi.getBalance(authToken);

        call.enqueue(new Callback<BalanceResponse>() {
            @Override
            public void onResponse(Call<BalanceResponse> call, Response<BalanceResponse> response) {
                String availableFinance = String.valueOf(response.body().getTotalIncome() - response.body().getTotalExpenses());
                mAvailableFinanceTextView.setText(getString(R.string.available_finance_price, availableFinance));
                mExpenseTextView.setText(getString(R.string.total_expense_price, String.valueOf(response.body().getTotalExpenses())));
                mIncomeTextView.setText(getString(R.string.total_income_price, String.valueOf(response.body().getTotalIncome())));

                mDiagramView.update(response.body().getTotalExpenses(), response.body().getTotalIncome());
            }

            @Override
            public void onFailure(Call<BalanceResponse> call, Throwable t) {

            }
        });

    }
}
