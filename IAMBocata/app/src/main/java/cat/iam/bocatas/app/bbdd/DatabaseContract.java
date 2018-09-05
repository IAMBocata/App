package cat.iam.bocatas.app.bbdd;

public final class DatabaseContract {

    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "IAMBocata.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String COMMA_SEP          = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DatabaseContract() {}

    public static abstract class TableUser {
        public static final String TABLE_NAME            = "USER";
        public static final String COLUMN_NAME           = "NAME";
        public static final String COLUMN_EMAIL          = "MAIL";
        public static final String COLUMN_MONEY          = "MONEY";
        public static final String COLUMN_PHOTOPATH      = "PHOTOPATH";
        public static final String COLUMN_QRPHOTOPATH    = "QRPHOTOPATH";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COLUMN_EMAIL + TEXT_TYPE + " PRIMARY KEY " +COMMA_SEP +
                COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_MONEY + " REAL " + COMMA_SEP +
                COLUMN_PHOTOPATH + TEXT_TYPE + COMMA_SEP +
                COLUMN_QRPHOTOPATH + TEXT_TYPE  + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class TableProduct {

        public static final String TABLE_NAME = "PRODUCT";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_PRICE = "PRICE";
        public static final String COLUMN_PHOTOPATH = "PHOTOPATH";
        public static final String COLUMN_ISLIKED = "ISLIKED";
        public static final String COLUMN_INGREDIENTS = "INGREDIENTS";
        public static final String COLUMN_ID_CATEGORY = "ID_CATEGORY";
        public static final String COLUMN_DESCRIPTION = "DESCRIPTION";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY " +COMMA_SEP +
                COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_PRICE + " REAL " + COMMA_SEP +
                COLUMN_PHOTOPATH + TEXT_TYPE + COMMA_SEP +
                COLUMN_ISLIKED + " INTEGER "  + COMMA_SEP +
                COLUMN_INGREDIENTS + TEXT_TYPE + COMMA_SEP +
                COLUMN_ID_CATEGORY + " INTEGER " + COMMA_SEP +
                COLUMN_DESCRIPTION + TEXT_TYPE +
                " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class TableCategory {

        public static final String TABLE_NAME = "CATEGORY";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_NAME = "NAME";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY " +COMMA_SEP +
                COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class TableHistorial {

        public static final String TABLE_NAME = "HISTORY";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_ID_CHECKOUT = "ID_CHECKOUT";
        public static final String COLUMN_ID_PRODUCT = "ID_PRODUCT";
        public static final String COLUMN_TIME = "TIME";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY " +COMMA_SEP +
                COLUMN_ID_CHECKOUT + " INTEGER "  + COMMA_SEP +
                COLUMN_ID_PRODUCT + " INTEGER "  + COMMA_SEP +
                COLUMN_TIME + TEXT_TYPE + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }



}