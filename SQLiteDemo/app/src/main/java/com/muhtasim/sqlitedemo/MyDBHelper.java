package com.muhtasim.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/* import androidx.annotation.Nullable; */

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "Customer_Table";
    public static final String COL1_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COL2_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COL3_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    public static final String COL0_ID = "ID";

    public MyDBHelper(Context context) {
        super(context,"Customer.db", null, 1);
    }

    //this called very first time when the database accessed; the code to create the database should be in here;
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CreateTable = "Create Table " + CUSTOMER_TABLE +
                " (" + COL0_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + COL1_CUSTOMER_NAME + " TEXT, " +
                "" + COL2_CUSTOMER_AGE + " INT, " +
                "" + COL3_ACTIVE_CUSTOMER + " BOOLEAN)";
        sqLiteDatabase.execSQL(CreateTable);

    }

    // this method is called when the database Version is changed and updated;
    // it prevents previous user apps from breaking when database design changed;
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean addOne(CustomerModel customerModel){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1_CUSTOMER_NAME, customerModel.getName());
        contentValues.put(COL2_CUSTOMER_AGE, customerModel.getAge());
        contentValues.put(COL3_ACTIVE_CUSTOMER, customerModel.isActive());
        long insert = sqLiteDatabase.insert(CUSTOMER_TABLE, null, contentValues);
        return insert != -1;
    }
    public boolean deleteOne(CustomerModel customerModel){
        // if find a customer return true and delete from customerList;
        //if do not find the customer return false value;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryString = "DELETE FROM " + CUSTOMER_TABLE + "WHERE " + COL0_ID + "= " + customerModel.getId();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        return cursor.moveToFirst();
    }
    public List<CustomerModel> getEveryOne(){
        List<CustomerModel> returnList = new ArrayList<>();

        // Get data from the dataBase;
        String queryString = "SELECT * FROM " + CUSTOMER_TABLE;
        // Use getWriteableDatabase only when to select content from database
        // or create update or delete data from database;
        // as we are just seeing data's from upside down, Readable is enough;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            //Loop through the cursor(result set) and new customer Objects,and Put them into the return list;
            do{
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerisActive = cursor.getInt(3) == 1 ? true : false;
                CustomerModel newCustomer = new CustomerModel(customerID, customerName, customerAge, customerisActive);
                returnList.add(newCustomer);

            }while (cursor.moveToNext());

        }else{
            // if falls apart;
        }
        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }
}
