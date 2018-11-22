package purchases.application.purchasescollection.products;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.data.Product;
import purchases.application.purchasescollection.interfaces.ItemListListener;

import static com.google.common.base.Preconditions.checkNotNull;

class ProductsAdapter extends BaseAdapter {

    private List<Product> items;
    private ItemListListener<Product> itemListListener;

    public ProductsAdapter(List<Product> items, ItemListListener<Product> itemListListener) {
        setList(items);
        this.itemListListener = itemListListener;
    }

    public void replaceData(List<Product> items) {
        setList(items);
        notifyDataSetChanged();
    }

    public void resetData() {
        this.items.clear();
        notifyDataSetChanged();
    }

    private void setList(List<Product> itemList) {
        items = checkNotNull(itemList);
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Product getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowsView = convertView;

        if(rowsView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            rowsView = inflater.inflate(R.layout.product_item, parent, false);
        }

        final Product item = getItem(position);

        TextView titleTV = rowsView.findViewById(R.id.title);
        titleTV.setText(item.getName());

        CheckBox completeCB = rowsView.findViewById(R.id.complete);
        completeCB.setChecked(item.getBuy());

        if (item.getBuy()) {
            rowsView.setBackground(
                    parent.getContext().getDrawable(R.drawable.touch_completed_feedback)
            );
        } else {
            rowsView.setBackground(
                    parent.getContext().getDrawable(R.drawable.touch_feedback)
            );
        }

        completeCB.setOnClickListener(v -> {
            if (!item.getBuy()) {
                itemListListener.onCompleteClick(item);
            } else {
                itemListListener.onUnCompleteClick(item);
            }
        });

        rowsView.setOnClickListener(v -> itemListListener.onClick(item));

        return rowsView;
    }
}
