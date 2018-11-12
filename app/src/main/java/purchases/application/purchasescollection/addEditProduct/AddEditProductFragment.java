package purchases.application.purchasescollection.addEditProduct;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import purchases.application.purchasescollection.R;
import static com.google.common.base.Preconditions.checkNotNull;

public class AddEditProductFragment extends Fragment implements AddEditProductContract.View  {

    public static final String ARGUMENT_EDIT_PRODUCT_ID = "EDIT_PRODUCT_ID";

    private AddEditProductContract.Presenter presenter;

    private TextView name;
    private TextView price;
    private TextView amount;
    private Switch buy;

    public static AddEditProductFragment newInstance() {
        return new AddEditProductFragment();
    }

    public AddEditProductFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_product_edit_done);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(v -> presenter.saveProduct(getName(), getPrice(), getAmount(), getBuy()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_edit_product, container, false);

        name = (TextView) root.findViewById(R.id.product_name);
        price = (TextView) root.findViewById(R.id.product_price);
        amount = (TextView) root.findViewById(R.id.product_amount);
        buy = (Switch) root.findViewById(R.id.product_buy);


        setHasOptionsMenu(true);
        return root;
    }
    @Override
    public void showEmptyProductError() {
        Snackbar.make(name, getString(R.string.empty_product_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProductList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showSwitch() {
        buy.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSwitch() {
        buy.setVisibility(View.GONE);
    }

    @Override
    public void setName(String name) {
        this.name.setText(name);
    }

    @Override
    public void setPrice(double price) {
        this.price.setText(String.valueOf(price));
    }

    @Override
    public void setAmount(int amount) {
        this.amount.setText(String.valueOf(amount));
    }

    @Override
    public void setBuy(boolean buy) {
        this.buy.setChecked(buy);
    }

    public String getName() {
        return name.getText().toString();
    }

    public double getPrice() {
       return Double.valueOf(price.getText().toString());
    }

    public int getAmount() {
        return Integer.valueOf(amount.getText().toString());
    }

    public boolean getBuy() {
        return buy.isChecked();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(AddEditProductContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }
}
