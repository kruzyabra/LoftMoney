package ru.pavlenko.julia.loftmoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetFragment extends Fragment {
    private final int REQUEST_CODE = 1001;

    private static final String PRICE_COLOR = "price_color";
    private static final String PRICE_TYPE = "price_type";

    private ItemsAdapter mItemsAdapter;

    private Api mApi;

    public BudgetFragment() {
        // Required empty public constructor
    }

    public static BudgetFragment newInstance(FragmentType fragmentType) {
        BudgetFragment fragment = new BudgetFragment();

        Bundle args = new Bundle();
        args.putInt(PRICE_COLOR, fragmentType.getTextColor());
        args.putString(PRICE_TYPE, fragmentType.getTypeName());

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApi = ((LoftApp) getActivity().getApplication()).getApi();

        getItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View budgetFragment = inflater.inflate(R.layout.fragment_budget, container, false);

        RecyclerView recyclerView = budgetFragment.findViewById(R.id.item_list);

        mItemsAdapter = new ItemsAdapter(getArguments().getInt(PRICE_COLOR));

        recyclerView.setAdapter(mItemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        FloatingActionButton floatingActionButton = budgetFragment.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddItemActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        return budgetFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            String title = data.getStringExtra("title");
            int price = Integer.valueOf(data.getStringExtra("price"));
            String authToken = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("authToken", "");

            Call<Status> call = mApi.add(new AddItemRequest(price, title, getArguments().getString(PRICE_TYPE)), authToken);

            call.enqueue(new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {
                    mItemsAdapter.clearItemList();
                    getItems();
                }

                @Override
                public void onFailure(Call<Status> call, Throwable t) {

                }
            });
        }
    }

    private void getItems() {
        String authToken = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("authToken", "");

        Call<List<Item>> call = mApi.getItems(getArguments().getString(PRICE_TYPE), authToken);

        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                List<Item> items = response.body();

                for (Item item : items) {
                    item.setCreateAtDate();
                }

                Collections.sort(items, new Comparator<Item>() {
                    @Override
                    public int compare(Item o1, Item o2) {
                        return o1.getCreateAtDate().compareTo(o2.getCreateAtDate());
                    }
                });

                for (Item item : items) {
                    mItemsAdapter.addItem(item);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }
}
