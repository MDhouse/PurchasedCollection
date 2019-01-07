package purchases.application.purchasescollection.client.store.implement.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.common.contract.IStoreListener;
import purchases.application.purchasescollection.infrastructure.model.dto.StoreDto;

import static android.support.v4.content.ContextCompat.getDrawable;
import static com.google.common.base.Preconditions.checkNotNull;

public class StoreAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> storesHeaders = new ArrayList<>();
    private HashMap<String, StoreDto> storeChild = new HashMap<>();
    private IStoreListener<StoreDto> listener;

    public StoreAdapter(List<StoreDto> stores, Context context, IStoreListener<StoreDto> listener) {
        this.context = context;
        setStores(stores);
        this.listener = listener;
    }

    public void replaceData(List<StoreDto> stores){
        setStores(stores);
        notifyDataSetChanged();
    }

    public void resetData() {
        storesHeaders.clear();
        storeChild.clear();
    }

    private void setStores(List<StoreDto> stores){

        checkNotNull(stores);

        stores.forEach(x -> {
            storesHeaders.add(x.getName());
            storeChild.put(x.getName(), x);
        });
    }

    @Override
    public int getGroupCount() {
        return this.storesHeaders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public String getGroup(int groupPosition) {
        return storesHeaders.get(groupPosition);
    }

    @Override
    public StoreDto getChild(int groupPosition, int childPosition) {
        return storeChild.get(storesHeaders.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View groupView = convertView;

        if(groupView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            groupView = inflater.inflate(R.layout.store_header, parent, false);
        }

        TextView headerTextView = groupView.findViewById(R.id.store_header);

        final String header = getGroup(groupPosition);

        headerTextView.setText(header);

        if(isExpanded) {
            headerTextView.setTypeface(null, Typeface.BOLD);
            headerTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_expand_less, 0, 0, 0);
        } else {
            headerTextView.setTypeface(null, Typeface.NORMAL);

            headerTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_expand_more, 0, 0, 0);
        }

        return groupView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View childView = convertView;

        if(childView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            childView = inflater.inflate(R.layout.store_child, parent, false);
        }

        final StoreDto store = getChild(groupPosition, childPosition);

        TextView descTextView = childView.findViewById(R.id.store_child_desc);
        TextView placeTextView = childView.findViewById(R.id.store_child_place);

        TextView radiusTextView = childView.findViewById(R.id.store_child_radius);

        Button toEditRadius = childView.findViewById(R.id.edited_radius);
        Button toMap = childView.findViewById(R.id.to_map);

        descTextView.setText(store.getDescription());
        placeTextView.setText(store.getTextPosition());
        radiusTextView.setText(store.getTextRadius());

        toEditRadius.setOnClickListener(v -> listener.toRadiusEdit(store));
        toMap.setOnClickListener(v -> listener.toMap(store.getId()));

        return childView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
