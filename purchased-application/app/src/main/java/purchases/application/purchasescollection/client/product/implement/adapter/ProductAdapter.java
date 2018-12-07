package purchases.application.purchasescollection.client.product.implement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.product.contract.IlistListener;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductAdapter extends BaseAdapter {

    private List<ProductDto> products;
    private IlistListener<ProductDto> listener;

    public ProductAdapter(List<ProductDto> products, IlistListener<ProductDto> listener) {
        setList(products);
        this.listener = listener;
    }

    public void replaceData(List<ProductDto> products) {

        setList(products);
        notifyDataSetChanged();
    }

    public void resetData() {

        this.products.clear();
        notifyDataSetChanged();
    }

    private void setList(List<ProductDto> products) {

        this.products = checkNotNull(products);
    }

    @Override
    public int getCount() {
        return this.products.size();
    }

    @Override
    public ProductDto getItem(int position) {
        return this.products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowsView = convertView;

        if(rowsView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            rowsView = inflater.inflate(R.layout.product_item, parent, false);
        }

        final ProductDto product = getItem(position);

        TextView titleText = rowsView.findViewById(R.id.title);
        titleText.setText(product.getName());

        CheckBox checkBox = rowsView.findViewById(R.id.complete);
        checkBox.setChecked(product.isBuy());

        if (product.isBuy())
            rowsView.setBackground(parent.getContext().getDrawable(R.drawable.touch_completed_feedback));
         else
            rowsView.setBackground(parent.getContext().getDrawable(R.drawable.touch_feedback));


        checkBox.setOnClickListener(v -> {
            if (!product.isBuy()) {
                listener.completeAction(product);
            } else {
                listener.unCompleteAction(product);
            }
        });

        rowsView.setOnClickListener(v -> listener.click(product));

        return rowsView;
    }
}
