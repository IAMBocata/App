package cat.iam.bocatas.app.views.fragments;

import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cat.iam.bocatas.app.Constants;
import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.adapters.CheckOutListAdapter;
import cat.iam.bocatas.app.model.AllInfoObject;
import cat.iam.bocatas.app.model.AllInfoObjectContainer;
import cat.iam.bocatas.app.model.CheckOutObject;
import cat.iam.bocatas.app.model.Product;
import cat.iam.bocatas.app.serverconnection.BuyConnection;
import cat.iam.bocatas.app.service.MyFirebaseInstanceIDService;

/**
 * Fragment per a realitzar el checkout de la compra
 */

public class CheckOutFragment extends Fragment implements View.OnClickListener {

    Button buttonCompra;
    TextInputEditText deliveryTime;

    RecyclerView rv;
    LinearLayoutManager layoutManager;
    CheckOutListAdapter adapter;

    List<CheckOutObject> checkOuts;

    public CheckOutFragment() {
        //checkOuts = new ArrayList<CheckOutObject>();
        checkOuts = AllInfoObjectContainer.getAllInfoObject().getCheckouts();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checkout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        //addFakeCheckOutItems();
        prepareRecyclerView();

        buttonCompra = (Button) getActivity().findViewById(R.id.compraButton);
        buttonCompra.setOnClickListener(this);

        prepareTotal();
        prepareTimePicker();
    }

    public void prepareTotal() {
        TextView checkOutTotal = (TextView) getActivity().findViewById(R.id.checkOutTotal);

        float total = Float.parseFloat(getTotalPrice().toString());

        checkOutTotal.setText( new DecimalFormat("#0.00").format(total));

        if (AllInfoObjectContainer.getAllInfoObject().getUser().getMoney() < total) {
            checkOutTotal.setTextColor(Color.RED);
            buttonCompra.setEnabled(false);
        } else {
            checkOutTotal.setTextColor(Color.BLACK);
            buttonCompra.setEnabled(true);
        }
    }

    private CharSequence getTotalPrice() {

        float result = 0;

        for (CheckOutObject c : checkOuts) {
            result += c.getTotalPrice();
        }

        return result + "";
    }

    private void prepareTimePicker() {
        deliveryTime = (TextInputEditText) getActivity().findViewById(R.id.deliveryTime);
        deliveryTime.setTextIsSelectable(false);
        deliveryTime.setClickable(false);

        deliveryTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if (selectedHour >= AllInfoObjectContainer.getAllInfoObject().timeOpen &&
                                selectedHour < AllInfoObjectContainer.getAllInfoObject().timeClose &&
                                    AllInfoObjectContainer.getAllInfoObject().running) {

                            int selectedMinuteWithMargin = selectedMinute -
                                    AllInfoObjectContainer.getAllInfoObject().marginMins;

                            int selectedHourWithMargin = selectedHour;

                            while (selectedMinuteWithMargin >= 60) {
                                selectedMinuteWithMargin += 60;
                                selectedHourWithMargin -= 1;
                            }

                            if (selectedHourWithMargin > hour) {
                                deliveryTime.setText(
                                        String.format("%02d", selectedHour)+ ":" +
                                                String.format("%02d", selectedMinute));
                            } else {

                                if (selectedHourWithMargin == hour && selectedMinuteWithMargin >= minute) {
                                    deliveryTime.setText(
                                            String.format("%02d", selectedHour)+ ":" +
                                                    String.format("%02d", selectedMinute));
                                } else {
                                    Toast.makeText(getContext(),
                                            "Hora del passat o amb marge insuficient\nMarge: " +
                                                    AllInfoObjectContainer.getAllInfoObject().marginMins +
                                            " minuts.", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            Toast.makeText(getContext(),
                                    "Fora d'horaris del bar o bé el bar no vol comandes en aquest moment ",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }, hour, minute, true);

                mTimePicker.setTitle("Delivery Time: \n");
                mTimePicker.show();
            }
        });
    }


    private void prepareRecyclerView() {
        rv = (RecyclerView) getActivity().findViewById(R.id.recyclerViewCheckOut);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter = new CheckOutListAdapter(checkOuts, getActivity(), this);
        rv.setAdapter(adapter);
    }

    public void addFakeCheckOutItems() {
        Product p = new Product("Hamburguesaka", "hamburguesaka", 2.15f, null, null);
        p.setUrlPhoto("https://unareceta.com/wp-content/uploads/2016/10/hamburguesa.jpg");
        checkOuts.add(new CheckOutObject(2, p));
        checkOuts.add(new CheckOutObject(1, p));
    }

    public void onClickCompra(View view) {
        Toast.makeText(getContext(), "Compraste wey!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        if (view.equals(buttonCompra)) {
            // http://labs.iam.cat/~a16josortmar/api/buy.php?checkout=1-2&iduser=23&total=4.40&API_KEY=5HU5pAPYrppjP&DELIVERY_DATE=2018-04-20+20:20

            if (deliveryTime.getText().toString().isEmpty()) {
                deliveryTime.setHintTextColor(Color.RED);
                deliveryTime.setTextColor(Color.RED);
                deliveryTime.setHighlightColor(Color.RED);
                Toast.makeText(getContext(), "Falta hora", Toast.LENGTH_SHORT).show();
                return;
            }

            if (checkOuts.size() > 0) {

                String url = "";

                String checkoutsString = "";
                float total = 0;
                boolean first = true;

                for (int i = 0; i < checkOuts.size(); i++) {

                    if (!first) {
                        checkoutsString += ",";
                    } else {
                        first = false;
                    }

                    CheckOutObject c = checkOuts.get(i);

                    checkoutsString += c.getProduct().getId()  + "-" + c.getQuantity();
                    total += c.getTotalPrice();
                }

                url += "checkout=" + checkoutsString;
                url += "&total=" + new DecimalFormat("#0.00").format(total).replace(',', '.');
                url += "&iduser=" + AllInfoObjectContainer.getAllInfoObject().getUser().getId();
                url += "&API_KEY=" + Constants.API_KEY;
                url += "&token=" + MyFirebaseInstanceIDService.getToken(getContext());

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                url += "&DELIVERY_DATE=" + df.format(c) + "+" + deliveryTime.getText();

                Log.d("DATE", url);

                BuyConnection buyConnection = new BuyConnection(this);
                buyConnection.execute(url);

            } else {

            }

        }

    }


    public CheckOutListAdapter getAdapter() {
        return adapter;
    }
}
