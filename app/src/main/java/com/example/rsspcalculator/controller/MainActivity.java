package com.example.rsspcalculator.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rsspcalculator.R;
import com.example.rsspcalculator.model.RsspModel;
import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {

    RsspModel model=null;
    EditText name=null;
    EditText salary=null;
    EditText age=null;
    EditText rsspContribution=null;
    TextView rsspLimit=null;
    TextView rsspCarryOver=null;
    TextView rsspNextYearLimit=null;
    TextView tax=null;
    TextView rsspPenalty=null;
    Slider valueSlider=null;
    Button submit;
    Button reload;
    Button save;
    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor myEdit=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        myEdit = sharedPreferences.edit();

        name=(EditText) findViewById(R.id.name);
        salary=(EditText) findViewById(R.id.salary);
        age=(EditText) findViewById(R.id.age);
        rsspContribution=(EditText) findViewById(R.id.rsspContribution);
        rsspLimit=(TextView) findViewById(R.id.rsspLimit);
        rsspCarryOver=(TextView) findViewById(R.id.rsspCarryOver);
        rsspNextYearLimit=(TextView) findViewById(R.id.rsspNextYearLimit);
        tax=(TextView) findViewById(R.id.tax);
        rsspPenalty=(TextView) findViewById(R.id.rsspPenalty);
        valueSlider=(Slider) findViewById(R.id.continuousSlider);
        submit=(Button) findViewById(R.id.submit);

        save=(Button) findViewById(R.id.save);
        reload=(Button) findViewById(R.id.reload);


        //listerners
        reload.setOnClickListener(v->{
            name.setText(sharedPreferences.getString("name", "Nothing Found"));
            age.setText(sharedPreferences.getString("age", "Nothing Found"));
            salary.setText(sharedPreferences.getString("salary", "Nothing Found"));
            rsspContribution.setText(sharedPreferences.getString("contribution", "Nothing Found"));
        });
        save.setOnClickListener(v->{
            myEdit.putString("name", name.getText().toString());
            myEdit.putString("age", age.getText().toString());
            myEdit.putString("salary", salary.getText().toString());
            myEdit.putString("contribution", rsspContribution.getText().toString());
            myEdit.apply();
        });
        submit.setOnClickListener(v->{
            model=new RsspModel(name.getText().toString(), Double.parseDouble(age.getText().toString()), Double.parseDouble(salary.getText().toString()), Double.parseDouble(rsspContribution.getText().toString()));
            model.calculate();
           result();
        });


        valueSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                //Use the value
                model=new RsspModel(name.getText().toString(), Double.parseDouble(age.getText().toString()), Double.parseDouble(salary.getText().toString()), Double.parseDouble(rsspContribution.getText().toString()));
                model.calculateSlider(value);
                result();
            }
        });

    }
    public  void result()
    {
        rsspContribution.setText((Double.toString(model.getContribution() )));
        rsspLimit.setText((Double.toString(model.getRsspLimit() )));
        rsspCarryOver.setText((Double.toString(model.getRsspCarryOver() )));
        rsspLimit.setText((Double.toString(model.getRsspLimit() )));
        tax.setText((Double.toString(model.getTax() )));
        rsspNextYearLimit.setText((Double.toString(model.getRsspNextYearLimit())));
        rsspPenalty.setText((Double.toString(model.getRsspPenalty()))+"%");

    }




}