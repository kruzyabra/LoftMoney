package ru.pavlenko.julia.loftmoney;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    private List<Item> mItemList = new ArrayList<>();
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray();

    private int mPriceColor;
    private ItemAdapterListener mListener;

    public ItemsAdapter(int priceColor) {
        this.mPriceColor = priceColor;
    }

    public void setListener(ItemAdapterListener listener) {
        mListener = listener;
    }

    public void switchItem(int position) {
        mSelectedItems.put(position, !mSelectedItems.get(position));
    }

    public boolean isItemSelected(int position) {
        return mSelectedItems.get(position);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(viewGroup.getContext(), R.layout.item_view, null);

        TextView priceView = itemView.findViewById(R.id.item_price);
        priceView.setTextColor(itemView.getResources().getColor(mPriceColor));

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int position) {
        Item item = mItemList.get(position);

        viewHolder.bindItem(item, mSelectedItems.get(position));
        viewHolder.setListeners(mListener, position);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void addItem(Item item) {
        mItemList.add(item);
        notifyDataSetChanged();
    }

    public void clearItemList() {
        mItemList.clear();
        notifyDataSetChanged();
    }

    public void clearSelections() {
        mSelectedItems.clear();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> selectedItems = new ArrayList<>();

        for (int i = 0; i < mItemList.size(); i++) {
            if (mSelectedItems.get(i)) {
                selectedItems.add(mItemList.get(i).getId());
            }
        }

        return selectedItems;
    }

    public int getNumberOfSelectedItems() {
        int number = 0;

        for (int i = 0; i < mItemList.size(); i++) {
            if (mSelectedItems.get(i)) {
                number ++;
            }
        }

        return number;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mPrice;
        private View mItemView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mItemView = itemView;
            mTitle = itemView.findViewById(R.id.item_title);
            mPrice = itemView.findViewById(R.id.item_price);
        }

        public void bindItem(final Item item, boolean isSelected) {
            mItemView.setSelected(isSelected);
            mTitle.setText(item.getName());
            mPrice.setText(mPrice.getContext().getResources().getString(R.string.price_template, item.getPrice()));
        }

        public void setListeners(final ItemAdapterListener listener, final int position) {
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(position);
                }
            });

            mItemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(position);
                    return false;
                }
            });
        }
    }
}
