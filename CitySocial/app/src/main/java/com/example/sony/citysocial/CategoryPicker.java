package com.example.sony.citysocial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anton46.collectionitempicker.CollectionPicker;
import com.anton46.collectionitempicker.Item;
import com.anton46.collectionitempicker.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class CategoryPicker extends ActionBarActivity {

    CollectionPicker mPicker;
    TextView mTextView;
    List<Item> refList;

    int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorypicker);
        final ArrayList<String> selected=new ArrayList<String>();
        mPicker = (CollectionPicker) findViewById(R.id.collection_item_picker);
        mPicker.setItems(generateItems());
        mPicker.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(Item item, int position) {
                if (item.isSelected) {
                    selected.add(item.text);
                    counter++;
                } else {
                    counter--;
                }
                mTextView.setText("No."+counter + " Items Selected-"+selected.get(counter-1));
            }
        });

        mTextView = (TextView) findViewById(R.id.text);
        Button submit=(Button)findViewById(R.id.moveButt);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CategoryPicker.this, MainActivity.class);
                startActivity(i);
            }
        });



    }



    private List<Item> generateItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("a", "Restaurant and Bars"));
        items.add(new Item("b", "Entertainment"));
        items.add(new Item("c", "Tourist Places"));
        items.add(new Item("d", "Events and Concerts"));
        items.add(new Item("e", "Malls,Shops and Bazaars"));
        items.add(new Item("f", "Transportation"));
        items.add(new Item("g", "Hotels and Habitation"));
        items.add(new Item("h", "Medical and Healthcare"));
        refList=items;
        return items;
    }
}

