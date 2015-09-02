package princetronics.assignment3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBController extends SQLiteOpenHelper {

    private static final String NAME = "Assignment3";
    private static final int VERSION = 1;
    private static final String TAG = "DBController";

    private static final String CREATETABLEINCOMES = "CREATE TABLE IF NOT EXISTS Incomes" +
            "(_id INTEGER PRIMARY KEY, " +
            "Date TEXT, " +
            "Amount TEXT" +
            "title TEXT" +
            ")";

    private static final String CREATETABLEEXPENSES = "CREATE TABLE IF NOT EXISTS Expenses" +
            "(_id INTEGER PRIMARY KEY, " +
            "Date TEXT, " +
            "Amount TEXT" +
            "title TEXT" +
            ")";


    private SQLiteDatabase db;

    public DBController(Context context) {
        super(context, NAME, null, VERSION);

        Log.d(TAG, "Constructor");
        db = context.openOrCreateDatabase("Assignment3", context.MODE_PRIVATE, null); // Create or open already existing database

        db.execSQL(CREATETABLEINCOMES);
        db.execSQL(CREATETABLEEXPENSES);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void open() {
        db = getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long addIncome(String title, String amount) {
        return add(title, amount);
    }

    public long addExpense(String title, String amount) {
        return add(title, amount);
    }

    private long add(String title, String amount) {
        ContentValues values = new ContentValues();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String date = s.format(new Date());
        values.put("Date", date);
        values.put("Amount", amount);
        values.put("Title", title);
        return db.insert("Incomes", null, values);
    }

    public Cursor getIncomes() {
        return readDatabase("Incomes");
    }

    public Cursor getExpenses() {
        return readDatabase("Expenses");
    }

    private Cursor readDatabase(String tableName) {
        return db.query(
                tableName, //Table name
                new String[]{"_id", "Date", "Amount", "Title"}, // Columns to get
                null, // Selection
                null, //SelectionArgs
                null, //GroupBy
                null,  //Having
                "Date", //Order by date column
                null); // Limit
    }
}
