package ru.pavlenko.julia.loftmoney;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetFragment extends Fragment implements ItemAdapterListener, ActionMode.Callback{
    public static final int REQUEST_CODE = 1001;

    private static final String PRICE_COLOR = "price_color";
    private static final String PRICE_TYPE = "price_type";

    private ItemsAdapter mItemsAdapter;
    private SwipeRefreshLayout mRefresh;

    private Api mApi;

    private ActionMode mActionMode;

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
    }

    @Override
    public void onStart() {
        super.onStart();

        getItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View budgetFragment = inflater.inflate(R.layout.fragment_budget, container, false);

        RecyclerView recyclerView = budgetFragment.findViewById(R.id.item_list);

        mItemsAdapter = new ItemsAdapter(getArguments().getInt(PRICE_COLOR));
        mItemsAdapter.setListener(this);

        recyclerView.setAdapter(mItemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        mRefresh = budgetFragment.findViewById(R.id.refresh);

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getItems();
                mRefresh.setRefreshing(false);
            }
        });

        return budgetFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String title = data.getStringExtra("title");
            int price = Integer.valueOf(data.getStringExtra("price"));
            String authToken = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(MainActivity.AUTH_TOKEN, "");

            Call<Status> call = mApi.add(new AddItemRequest(price, title, getArguments().getString(PRICE_TYPE)), authToken);

            call.enqueue(new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {
                    getItems();
                }

                @Override
                public void onFailure(Call<Status> call, Throwable t) {

                }
            });
        }
    }

    private void getItems() {
        String authToken = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(MainActivity.AUTH_TOKEN, "");

        Call<List<Item>> call = mApi.getItems(getArguments().getString(PRICE_TYPE), authToken);

        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                mItemsAdapter.clearItemList();

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

    @Override
    public void onItemClick(int position) {
        if (mItemsAdapter.isItemSelected(position)) {
            switchItem(position);
        }
        if (mItemsAdapter.getNumberOfSelectedItems() == 0) {
            mActionMode.finish();
        }
    }

    @Override
    public void onItemLongClick(int position) {
        switchItem(position);
        mItemsAdapter.notifyDataSetChanged();
        if (mActionMode == null) {
            ((AppCompatActivity) getActivity()).startSupportActionMode(this);
        }
        if (mItemsAdapter.getNumberOfSelectedItems() == 0) {
            mActionMode.finish();
        }
    }

    private void switchItem(int position) {
        mItemsAdapter.switchItem(position);
        mItemsAdapter.notifyDataSetChanged();

        if (mActionMode != null) {
            setActionModeTitle();
        }
    }

    private void setActionModeTitle() {
        String title = getResources().getString(R.string.action_mode_title);
        mActionMode.setTitle(title + " " + mItemsAdapter.getNumberOfSelectedItems());
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        mActionMode = actionMode;
        mActionMode.getMenuInflater().inflate(R.menu.budget_menu, menu);

        setActionModeTitle();

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.remove:
                showDialog();
                return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        mItemsAdapter.clearSelections();
        mItemsAdapter.notifyDataSetChanged();
        mActionMode = null;
    }

    private void showDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.budget_activity_title)
                .setMessage(R.string.remove_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removeItems();
                        mActionMode.finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    private void removeItems() {
        List<Integer> selectedItems = mItemsAdapter.getSelectedItems();

        for (int i = 0; i < selectedItems.size(); i++) {
            removeItem(selectedItems.get(i));
        }
    }

    private void removeItem(int id) {
        String authToken = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(MainActivity.AUTH_TOKEN, "");

        Call<Status> call = mApi.remove(id, authToken);

        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                getItems();
                mItemsAdapter.clearSelections();
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }
}
