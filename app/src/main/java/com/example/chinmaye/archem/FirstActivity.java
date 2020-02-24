package com.example.chinmaye.archem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import archem.entities.Molecule;
import archem.entities.MoleculeCollection;

public class FirstActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private Button b1;
    ListView lst;
    String formula;

    List<Molecule> moleculeList;
    ArrayAdapter<String> arrayAdapder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        lst = findViewById(R.id.lst1);
        arrayAdapder = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        this.moleculeList = MoleculeCollection.getMoleculeList();

        for (Molecule m : moleculeList)
        {
            formula = m.formula;
            arrayAdapder.add(formula);
            lst.setAdapter(arrayAdapder);
        }
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //System.out.println(lst.getItemAtPosition(position));
                //  tv= (TextView)findViewById(R.id.textView);
                // String s = tv.getText().toString();
                //tv.setText(getIntent().getStringExtra("formula"));
                String formula=arrayAdapder.getItem(position);
                Toast.makeText(getApplicationContext(), formula, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("formula", formula);

                startActivity(intent);
            }
        });

        b1 = findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openActivity2();
            }
        });
    }

    public void openActivity2()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }
}
