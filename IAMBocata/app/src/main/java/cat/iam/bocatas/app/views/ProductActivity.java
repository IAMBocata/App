package cat.iam.bocatas.app.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.DecimalFormat;
import java.util.List;

import cat.iam.bocatas.app.Constants;
import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.model.AllInfoObject;
import cat.iam.bocatas.app.model.AllInfoObjectContainer;
import cat.iam.bocatas.app.model.CheckOutObject;
import cat.iam.bocatas.app.model.Product;
import cat.iam.bocatas.app.serverconnection.DislikeConnection;
import cat.iam.bocatas.app.serverconnection.LikeConnection;


/**
 * Activitat que mostra un producte
 */


public class ProductActivity extends AppCompatActivity {

    FloatingActionButton likeButton;
    private static final String TAG = "ProductActivity";
    //Ingredients
    String[] ingredientsArray;
    ListView listViewIngredients;

    Product p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        p = getProdFromList((Product) getIntent().getSerializableExtra("product"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            //getWindow().setEnterTransition(new Fade());
        }

        showToolBar(p.getName(), true);
        ingredientsArray = p.getIngredients().toArray(new String[p.getIngredients().size()]);
        TextView price = findViewById(R.id.tvAPPrice);
        price.setText(new DecimalFormat("##.00").format(p.getPrice()) + " €");

        likeButton = (FloatingActionButton) findViewById(R.id.likeButton);

        if (p.isLiked()) {
            likeButton.setImageDrawable(getDrawable(R.drawable.heartfull));
        } else {
            likeButton.setImageDrawable(getDrawable(R.drawable.heartempty));
        }

        //CheckOutButton

        ImageButton ib = findViewById(R.id.ibCheckout);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductActivity.this, ContainerActivity.class);
                i.putExtra("checkout", true);
                startActivity(i);
            }
        });
        //But checkout

        //Ingredients al ListView
        listViewIngredients = findViewById(R.id.listViewIngredients);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_view_ingredients, ingredientsArray);
        listViewIngredients.setAdapter(adapter);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void showToolBar(String title, boolean upButton) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

        ImageView imageView = (ImageView) collapsingToolbarLayout.findViewById(R.id.imageHeader);
        Glide.with(getApplicationContext()).load(Constants.URL_SERVER + p.getUrlPhoto()).into(imageView);
    }

    public void onClickLikeButton(View view) {

        if (p.isLiked()) {
            new DislikeConnection().execute(p);
            likeButton.setImageDrawable(getDrawable(R.drawable.heartempty));
            p.setLiked(false);
        } else {
            new LikeConnection().execute(p);
            likeButton.setImageDrawable(getDrawable(R.drawable.heartfull));
            p.setLiked(true);
        }
    }

    public void addToCheckout(View view) {

        TextInputEditText input = (TextInputEditText) findViewById(R.id.etUnities);

        int qt = Integer.parseInt(input.getText().toString());

        List<CheckOutObject> checks = AllInfoObjectContainer.getAllInfoObject().getCheckouts();

        CheckOutObject c = checkIfExists(checks, p);

        if (c != null) {
            c.add(qt);
        } else {
            AllInfoObjectContainer.getAllInfoObject().addCheckout(new CheckOutObject(qt, p));
        }

        input.setText("");
        Toast.makeText(this, "Afegit", Toast.LENGTH_SHORT).show();

    }

    private CheckOutObject checkIfExists(List<CheckOutObject> checks, Product p) {

        for (CheckOutObject c : checks) {
            if (p.getId() == c.getProduct().getId()) {
                return c;
            }
        }
        return null;
    }

    private Product getProdFromList(Product p) {

        for (Product prod : AllInfoObjectContainer.getAllInfoObject().getProductList()) {
            if (prod.getName().equalsIgnoreCase(p.getName())) {
                return prod;
            }
        }

        return null;
    }
}
