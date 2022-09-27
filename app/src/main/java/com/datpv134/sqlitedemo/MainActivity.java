package com.datpv134.sqlitedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.datpv134.sqlitedemo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    SQLHelper sqlHelper;

    FoodAdapter foodAdapter;
    List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        sqlHelper = new SQLHelper(this);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.etName.getText().toString();
                String number = binding.etNumber.getText().toString();
                String price = binding.etPrice.getText().toString();

                Food food = new Food(1, name, number, price);

                sqlHelper.addFood(food);

                Toast.makeText(getBaseContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id =  binding.etId.getText().toString();
                String name =  binding.etName.getText().toString();
                String number =  binding.etNumber.getText().toString();
                String price =  binding.etPrice.getText().toString();

                Food food = new Food(1, name, number, price);

                sqlHelper.onUpdateFoods(id, food);

                Toast.makeText(getBaseContext(), "Update thành công", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] strings = {"Xóa hết", "Xóa theo id"};

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Thông báo")
                        .setSingleChoiceItems(strings, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ListView listView = ((AlertDialog) dialogInterface).getListView();
                                int id = listView.getCheckedItemPosition();
                                if (id == 0) sqlHelper.onDeleteAll();
                                else sqlHelper.onDeleteFoods(binding.etId.getText().toString());
                                Toast.makeText(getBaseContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();

                alertDialog.show();
            }
        });

        binding.btnGetFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodList = new ArrayList<>();

                foodList = sqlHelper.getAllFood();

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 1, RecyclerView.VERTICAL, false);
                foodAdapter = new FoodAdapter(foodList);

                binding.rvFoodsList.setLayoutManager(layoutManager);
                binding.rvFoodsList.setAdapter(foodAdapter);
            }
        });
    }
}