package cat.iam.bocatas.app.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.adapters.HistorialExpandableListAdapter;
import cat.iam.bocatas.app.model.CheckOutObject;
import cat.iam.bocatas.app.model.Compra;

/**
 * Fragment que mostra el historial de compres del usuari
 */

public class HistorialCompresFragment extends Fragment {

    private static final String COMPRES_KEY = "savedCompres";

    private ExpandableListView listView;
    private HistorialExpandableListAdapter adapter;

    private List<Compra> compres;

    public static HistorialCompresFragment newInstance(ArrayList<Compra> compres) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(COMPRES_KEY, compres);

        HistorialCompresFragment fragment = new HistorialCompresFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            compres = (ArrayList<Compra>) bundle.getSerializable(COMPRES_KEY);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historial_compres, container, false);

        readBundle(getArguments());

        HashMap<Compra, List<CheckOutObject>> listHashMap = new HashMap();
        for (Compra com : compres) {
            listHashMap.put(com, com.getCheck());
        }

        listView = rootView.findViewById(R.id.expandableFavourites);
        adapter = new HistorialExpandableListAdapter(getContext(), compres, listHashMap);
        listView.setAdapter(adapter);

        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();



    }


}




/*private void addFakeData() {

        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Compra 22/03/2019 18:13   -   6,28 €");
        listDataHeader.add("Compra 24/04/2019 17:06   -   2,90 €");

        List<String> otraCompra = new ArrayList<String>();
        otraCompra.add("Item 1");

        List<String> otraCompra2 = new ArrayList<String>();
        otraCompra2.add("Item 1");
        otraCompra2.add("Item 2");
        otraCompra2.add("Item 3");
        otraCompra2.add("Item 4");
        otraCompra2.add("Item 5");

        listHash.put(listDataHeader.get(0), otraCompra);
        listHash.put(listDataHeader.get(1), otraCompra2);

    }*/