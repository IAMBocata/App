package cat.iam.bocatas.app.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.model.CheckOutObject;
import cat.iam.bocatas.app.model.Compra;

/**
 * L'adaptador de la llista expandible del historial, aquesta classe gestiona com mostrar la informació del historial de compres.
 */

public class HistorialExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Compra> listDataHeader;
    private HashMap<Compra, List<CheckOutObject>> listHashMap;

    public HistorialExpandableListAdapter(Context context, List<Compra> listDataHeader, HashMap<Compra, List<CheckOutObject>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHeader.get(i)).get(i1); // i = Group Item, i1 = Child Item
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        Compra headerTitle = (Compra) getGroup(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group_historial, null);
        }

        TextView lblListHeader = (TextView) view.findViewById(R.id.lblListHeader);
        TextView lblListHeaderPrice = (TextView) view.findViewById(R.id.lblListHeaderPrice);
        TextView lblListChildState = view.findViewById(R.id.lblListHeaderState);

        lblListHeader.setText(headerTitle.getDate());
        lblListHeaderPrice.setText(String.format("%.2f", headerTitle.getPrice())+" €");
        lblListChildState.setText(headerTitle.getState());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final CheckOutObject child = (CheckOutObject) getChild(i, i1);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_historial, null);
        }

        TextView txtListChildName = view.findViewById(R.id.lblListItemName);
        TextView txtListChildQuan = view.findViewById(R.id.lblListItemQuant);


        txtListChildName.setText(child.getProduct().getName());
        txtListChildQuan.setText(child.getQuantity()+"");

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
