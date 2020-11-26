package com.muhtasim.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    MyDBHelper myDBHelper;
    ArrayAdapter customerArrayAdapter;

    //reference to the Buttons and other Controls on the Layout; ("These are called Member variables")
    Button btn_Add, btn_View;
    EditText et_Name, et_Age;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch swt_Active;
    ListView lv_CustomerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find all controls by their layout app id;
        btn_Add = findViewById(R.id.btn_Add);
        btn_View = findViewById(R.id.btn_View);
        et_Name = findViewById(R.id.et_Name);
        et_Age = findViewById(R.id.et_Age);
        swt_Active = findViewById(R.id.swt_Active);
        lv_CustomerList = findViewById(R.id.lv_CustomerList);

        myDBHelper = new MyDBHelper(MainActivity.this);
        showCustomerOnListView(myDBHelper);

        //set listener for buttons; //("Add New ", "View All");
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerModel customerModel;
                try{
                    customerModel =new CustomerModel(-1,et_Name.getText().toString(),
                            Integer.parseInt(et_Age.getText().toString()),swt_Active.isChecked());
                    //Toast Message;
                    Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_LONG).show();
                }
                catch(Exception e){
                    //Toast massage For Error
                    Toast.makeText(MainActivity.this, "Error Input", Toast.LENGTH_LONG).show();
                    customerModel = new CustomerModel(-1,"Error",0,false);
                }

                MyDBHelper myDBHelper = new MyDBHelper(MainActivity.this);
                boolean success = myDBHelper.addOne(customerModel);
                Toast.makeText(MainActivity.this,"Successful Insertion" +success, Toast.LENGTH_LONG).show();
                showCustomerOnListView(myDBHelper);
            }
        });
        btn_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDBHelper = new MyDBHelper(MainActivity.this);
                showCustomerOnListView(myDBHelper);
                //Toast.makeText(MainActivity.this, everyOne.toString(), Toast.LENGTH_LONG).show();
            }
        });

        lv_CustomerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CustomerModel clickCustomer = (CustomerModel) adapterView.getItemAtPosition(i);
                myDBHelper.deleteOne(clickCustomer);
                showCustomerOnListView(myDBHelper);
                Toast.makeText(MainActivity.this, "Deleetd"+clickCustomer.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showCustomerOnListView(MyDBHelper myDBHelper2) {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>
                (MainActivity.this, android.R.layout.simple_list_item_1, myDBHelper2.getEveryOne());
        lv_CustomerList.setAdapter(customerArrayAdapter);
    }
}