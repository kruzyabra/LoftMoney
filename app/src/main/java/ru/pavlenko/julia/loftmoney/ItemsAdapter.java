package ru.pavlenko.julia.loftmoney;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    private List<Item> mItemList = new ArrayList<>();
    private int mPriceColor;

    public ItemsAdapter(int priceColor) {
        this.mPriceColor = priceColor;
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
    public void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int i) {
        viewHolder.bindItem(mItemList.get(i));
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

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.item_title);
            mPrice = itemView.findViewById(R.id.item_price);
        }

        public void bindItem(final Item item) {
            mTitle.setText(item.getName());
            mPrice.setText(mPrice.getContext().getResources().getString(R.string.price_template, item.getPrice()));
        }
    }
}
