package cat.iam.bocatas.app.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.List;

import cat.iam.bocatas.app.model.Product;

/**
 * Created by yous on 23/03/18.
 */

public class ProductsHelper extends SQLiteOpenHelper {

    private SQLiteDatabase dbW;
    private SQLiteDatabase dbR;


//    // Database name
//    private static final String DATABASE_NAME = "IAM_Bocata";
//
//    // ------------------------------ TABLE USER ---------------------------------------------------
//    private final String TABLE_USER = "USER";
//    private final String COLUMN_TABLE_USER_NAME = "NAME";
//    private final String COLUMN_TABLE_USER_MAIL = "MAIL";
//    private final String COLUMN_TABLE_USER_MONEY = "MONEY";
//    private final String COLUMN_TABLE_USER_PHOTOPATH = "PHOTOPATH";
//    private final String COLUMN_TABLE_USER_QRPHOTOPATH = "QRPHOTOPATH";
//
//    // ------------------------------ TABLE PRODUCT ------------------------------------------------
//    private final String TABLE_PRODUCT = "PRODUCT";
//    private final String COLUMN_TABLE_PRODUCT_ID = "ID";
//    private final String COLUMN_TABLE_PRODUCT_NAME = "NAME";
//    private final String COLUMN_TABLE_PRODUCT_PRICE = "PRICE";
//    private final String COLUMN_TABLE_PRODUCT_PHOTOPATH = "PHOTOPATH";
//    private final String COLUMN_TABLE_PRODUCT_ISLIKED = "ISLIKED";
//    private final String COLUMN_TABLE_PRODUCT_INGREDIENTS = "INGREDIENTS";
//    private final String COLUMN_TABLE_PRODUCT_ID_CATEGORY = "ID_CATEGORY";
//    private final String COLUMN_TABLE_PRODUCT_DATE_ADDED = "DATE_ADDED";
//
//    // ------------------------------ TABLE CATEGORY -----------------------------------------------
//    private final String TABLE_CATEGORY = "CATEGORY";
//    private final String COLUMN_TABLE_CATEGORY_ID = "ID";
//    private final String COLUMN_TABLE_CATEGORY_NAME = "NAME";
//
//    // ------------------------------ TABLE BUY ----------------------------------------------------
//    private final String TABLE_BUY = "BUY";
//    private final String COLUMN_TABLE_TABLE_ID_BUY = "ID_BUY";
//    private final String COLUMN_TABLE_TABLE_ID_CHECKOUT = "ID_CHECKOUT";
//    private final String COLUMN_TABLE_TABLE_ID_PRODUCT = "ID_PRODUCT";


    private static ProductsHelper sInstance;


    public static synchronized ProductsHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new ProductsHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    private ProductsHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.TableUser.CREATE_TABLE);
        db.execSQL(DatabaseContract.TableCategory.CREATE_TABLE);
        db.execSQL(DatabaseContract.TableProduct.CREATE_TABLE);
        db.execSQL(DatabaseContract.TableHistorial.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DatabaseContract.TableUser.DELETE_TABLE);
        db.execSQL(DatabaseContract.TableCategory.DELETE_TABLE);
        db.execSQL(DatabaseContract.TableProduct.DELETE_TABLE);
        db.execSQL(DatabaseContract.TableHistorial.DELETE_TABLE);
        onCreate(db);
    }


    public void actualitzarTaulaProductes(List<Product> products) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+ DatabaseContract.TableProduct.TABLE_NAME);

        for (Product product : products) {

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.TableProduct.COLUMN_ID, product.getId());
            values.put(DatabaseContract.TableProduct.COLUMN_NAME, product.getName());
            values.put(DatabaseContract.TableProduct.COLUMN_ID_CATEGORY, idCategoria(product.getCategory()));
            values.put(DatabaseContract.TableProduct.COLUMN_INGREDIENTS, product.toStringIngredients());
            values.put(DatabaseContract.TableProduct.COLUMN_PHOTOPATH, product.getUrlPhoto());
            values.put(DatabaseContract.TableProduct.COLUMN_PRICE, product.getPrice());
            values.put(DatabaseContract.TableProduct.COLUMN_DESCRIPTION, product.getDescription());
            values.put(DatabaseContract.TableProduct.COLUMN_ISLIKED, product.isLiked() ? 1 : 0);

            // Insert the new row, returning the primary key value of the new row
            db.insert(DatabaseContract.TableProduct.TABLE_NAME, null, values);

        }

    }

    public int idCategoria(String cat) {

        int id = -1;
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                DatabaseContract.TableCategory.COLUMN_ID,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection =  DatabaseContract.TableCategory.COLUMN_NAME + " = ?";
        String[] selectionArgs = {cat};

        Cursor cursor = db.query(
                DatabaseContract.TableCategory.TABLE_NAME,
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TableCategory.COLUMN_ID));
        }

        return id;

    }


}
