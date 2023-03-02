package com.example.rsspcalculator.model;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class RsspModel {

    DecimalFormat numberFormat =null;
    private final double RSSPLIMIT =29210;
    private String name;
    private double age;
    private double salary;
    private double contribution;
    private double rsspLimit;
    private double rsspNextYearLimit;
    private double rsspPenalty;
    private double rsspCarryOver;
    private double tax;
    private Map<String,String> taxRates;
    private double[] r ={4128, 33339, 11765, 3672, 4582, 43283, 15430, 54570, 15675};


    private double sliderValue;

    public RsspModel()
    {
        numberFormat=new DecimalFormat("#.00");
        name="";
        age=0;
        salary=0;
        contribution=0;
        rsspLimit=0;
        rsspNextYearLimit=0;
        rsspPenalty=0;
        rsspCarryOver=0;
        sliderValue=0;
        tax=0;

        taxRates = new HashMap<String,String>();

    }
    public RsspModel(String name, double age,double salary,double contribution)
    {

        numberFormat=new DecimalFormat("#.00");
        this.name=name;
        this.age=age;
        this.salary=salary;
        this.contribution=contribution;

        taxRates = new HashMap<String,String>();
        taxRates.put("49231", ".2005");
        taxRates.put("53359", ".2415");
        taxRates.put("86698", ".2965");
        taxRates.put("98463", ".3148");
        taxRates.put("102135", ".3389");
        taxRates.put("106717", ".3791");
        taxRates.put("150000", ".4341");
        taxRates.put("165430", ".4497");
        taxRates.put("220000", ".4829");
        taxRates.put("235675", ".4985");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAge() {
        return Double.parseDouble(numberFormat.format(age));
    }

    public void setAge(double age) {
        this.age = age;
    }

    public double getSalary() {
        return Double.parseDouble(numberFormat.format(salary));
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getContribution() {
        return Double.parseDouble(numberFormat.format(contribution));
    }

    public void setContribution(double contribution) {
        this.contribution = contribution;
    }

    public double getSliderValue() {
        return sliderValue;
    }

    public void setSliderValue(double sliderValue) {
        this.sliderValue = sliderValue;
    }

    //only getter starts

    public double getRsspLimit() {
        return Double.parseDouble(numberFormat.format(rsspLimit));
    }

    public double getRsspNextYearLimit() {
        return Double.parseDouble(numberFormat.format(rsspNextYearLimit));
    }

    public double getRsspPenalty() {
        return Double.parseDouble(numberFormat.format(rsspPenalty));
    }


    public double getRsspCarryOver() {
        return Double.parseDouble(numberFormat.format(rsspCarryOver));
    }

    public double getTax() {
        return Double.parseDouble(numberFormat.format(tax));
    }



    public void calculate()
    {
        if(salary*0.18>RSSPLIMIT)
        {
            this.rsspLimit=RSSPLIMIT;
        }
        else
        {
            this.rsspLimit=salary*0.18;
        }
        if(contribution>rsspLimit+2000)
        {
            rsspPenalty=5;
        }else {
            rsspPenalty=0;
        }

        this.rsspCarryOver=rsspLimit-contribution;
        this.rsspNextYearLimit=rsspLimit+rsspCarryOver;
        salary=salary-contribution;
        sliderValue=contribution;

        taxCalculate();

    }
    public void calculateSlider(double sliderValue)
    {
        if(salary*0.18>RSSPLIMIT)
        {
            this.rsspLimit=RSSPLIMIT;
        }
        else
        {
            this.rsspLimit=salary*0.18;
        }

        this.rsspCarryOver=rsspLimit-sliderValue;
        this.rsspNextYearLimit=rsspLimit+rsspCarryOver;
        salary=salary-sliderValue;
        contribution=sliderValue;
        if(contribution>rsspLimit+2000)
        {
            rsspPenalty=5;
        }else {
            rsspPenalty=0;
        }
        taxCalculate();

    }
    private void taxCalculate()
    {

        int i=0;
        for (Map.Entry<String,String> entry : taxRates.entrySet())
        {
            if(salary> Double.parseDouble(entry.getKey()))
            {
                if(i==0||i==10)
                {
                    tax=tax+(Double.parseDouble(entry.getKey()))*Double.parseDouble(entry.getValue());
                }
                else
                {
                    tax=tax+(r[i]*Double.parseDouble(entry.getValue()));
                }
            }
            else
            {
                tax=(salary*Double.parseDouble(entry.getValue()));
            }
            i++;

        }
    }

}
