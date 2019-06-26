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

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(viewGroup.getContext(), R.layout.item_view, null);
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
        mItemList.add(0, item);
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
            mTitle.setText(item.getTitle());
            mPrice.setText(item.getPrice());
        }
    }
}
