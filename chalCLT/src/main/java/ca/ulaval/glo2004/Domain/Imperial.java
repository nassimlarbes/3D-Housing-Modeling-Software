/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.Domain;

import java.io.Serializable;


public class Imperial implements Serializable {
    private Integer feet;
    private Integer inch;
    private Integer numerator;
    private Integer denominator;

    public Imperial() {
        this.feet = 0;
        this.inch = 0;
        this.numerator = 0;
        this.denominator = 1;
    }

    public Imperial(int feet, int inch, int numerator, int denominator) {
        this.inch = inch;
        this.feet = feet;
        this.numerator = numerator;
        this.denominator = denominator;
        normalize();
    }

    public Imperial(Imperial imperial) {
        this.inch = imperial.getInch();
        this.feet = imperial.getFeet();
        this.numerator = imperial.getNumerator();
        this.denominator = imperial.getDenominator();
        normalize();
    }

    public Imperial(int feet, int inch) {
        this.inch = inch;
        this.feet = feet;
        this.numerator = 0;
        this.denominator = 1;
        normalize();
    }

    public Imperial(String feet, String inch, String fraction) throws IllegalArgumentException {
        if(feet == null || inch == null || fraction == null){
            throw new IllegalArgumentException("Feet, inch and fraction cannot be null");
        }
        feet = feet.isEmpty() ? "0" : feet.trim();
        inch = inch.isEmpty() ? "0" : inch.trim();
        fraction = fraction.isEmpty() ? "0/1" : fraction.trim();
        this.inch = Integer.parseInt(inch);
        this.feet = Integer.parseInt(feet);
        this.setDenominatorEtNumerator(fraction);
        normalize();
    }

    private int PGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private void normalize() {
        // Normalize numerator and denominator
        //calculate the greatest common divisor
        int gcd = PGCD(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;

        while (numerator >= denominator) {
            inch++;
            numerator -= denominator;
        }

        if(inch < 0){
            feet--;
            inch += 12;
        }

        while (inch >= 12) {
            feet++;
            inch -= 12;
        }
    }

    public Integer getInch() {
        return inch;
    }
    public Integer getFeet() {
        return feet;
    }
    public Integer getNumerator() {
        return numerator;
    }
    public Integer getDenominator() {
        return denominator;
    }

    public void setFeet(Integer feet) {
        this.feet = feet;
        normalize();
    }
    public void setInch(Integer inch) {
        this.inch = inch;
        normalize();
    }

    public void setNumerator(Integer numerator) {
        this.numerator = numerator;
        normalize();
    }

    public void setDenominatorEtNumerator(String fraction) throws IllegalArgumentException{
        if(fraction.equals("0")){
            this.numerator = 0;
            this.denominator = 1;
            return;
        }

        if(!fraction.contains("/")){
            throw new IllegalArgumentException("Fraction must be in the form of a/b");
        }

        String[] fractionParts = fraction.split("/");
        if(fractionParts.length != 2){
            throw new IllegalArgumentException("Fraction must be in the form of a/b");
        }

        int numerator = Integer.parseInt(fractionParts[0]);
        int denominator = Integer.parseInt(fractionParts[1]);
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        this.numerator = numerator;
        this.denominator = denominator;
        normalize();
    }

    public String fractionToString(){
        if(numerator == 0){
            return "0";
        }
        if(numerator == denominator){
            return "1";
        }
        return numerator + "/" + denominator;
    }

    public void setDenominator(Integer denominator) throws IllegalArgumentException{
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        this.denominator = denominator;
        normalize();
    }

    public double doubleValue() {
        return feet * 12 + inch + ((double)numerator / (double)denominator);
    }

    public int intValue() {
        return (int)doubleValue() ;
    }

    @Override
    public String toString() {
        return feet + "'" + inch + "“" + fractionToString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Imperial imperial)) return false;
        ((Imperial) o).normalize();
        normalize();
        return getFeet() == imperial.getFeet() && getInch() == imperial.getInch() && getNumerator() == imperial.getNumerator() && getDenominator() == imperial.getDenominator();
    }


}