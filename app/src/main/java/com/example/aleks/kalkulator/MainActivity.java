package com.example.aleks.kalkulator;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private TextView racun;
    private TextView rez;
    private Double rezultat;
    private Button nic;
    private Button ena;
    private Button dve;
    private Button tri;
    private Button stiri;
    private Button pet;
    private Button sest;
    private Button sedem;
    private Button osem;
    private Button devet;
    private Button c;
    private Button deljeno;
    private Button krat;
    private Button minus;
    private Button plus;
    private Button enacaj;
    private String izraz = "";
    private Boolean evaluate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pripraviGumbe();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button0:
                izraz += "0";
                break;
            case R.id.button1:
                izraz += "1";
                break;
            case R.id.button2:
                izraz += "2";
                break;
            case R.id.button3:
                izraz += "3";
                break;
            case R.id.button4:
                izraz += "4";
                break;
            case R.id.button5:
                izraz += "5";
                break;
            case R.id.button6:
                izraz += "6";
                break;
            case R.id.button7:
                izraz += "7";
                break;
            case R.id.button8:
                izraz += "8";
                break;
            case R.id.button9:
                izraz += "9";
                break;
            case R.id.buttonC:
                izraz = "";
               rez.setText(izraz);
                break;
            case R.id.buttonmul:
                izraz += "*";
                break;
            case R.id.buttondiv:
                izraz += "/";
                break;
            case R.id.buttonsub:
                izraz += "-";
                break;
            case R.id.buttonadd:
                izraz += "+";
                break;
            case R.id.buttoneql:
                evaluate = true;
                break;
            default:
                izraz = "Napaka!";
        }
       // urejenIzraz = izraz;
        racun.setText(izraz);
        if (evaluate) {
            rezultat = eval(izraz);
            izraz = rezultat.toString();
            rez.setText(izraz);
            evaluate = false;
        }
    }

    private void pripraviGumbe() {
        nic = findViewById(R.id.button0);
        ena = findViewById(R.id.button1);
        dve = findViewById(R.id.button2);
        tri = findViewById(R.id.button3);
        stiri = findViewById(R.id.button4);
        pet = findViewById(R.id.button5);
        sest = findViewById(R.id.button6);
        sedem = findViewById(R.id.button7);
        osem = findViewById(R.id.button8);
        devet = findViewById(R.id.button9);
        c = findViewById(R.id.buttonC);

        deljeno = findViewById(R.id.buttondiv);
        krat = findViewById(R.id.buttonmul);
        minus = findViewById(R.id.buttonsub);
        plus = findViewById(R.id.buttonadd);
        enacaj = findViewById(R.id.buttoneql);

        racun = findViewById(R.id.edt1);
        rez = findViewById(R.id.textView);

        nic.setOnClickListener(this);
        ena.setOnClickListener(this);

        dve.setOnClickListener(this);
        tri.setOnClickListener(this);
        stiri.setOnClickListener(this);
        pet.setOnClickListener(this);
        sest.setOnClickListener(this);
        sedem.setOnClickListener(this);
        osem.setOnClickListener(this);
        devet.setOnClickListener(this);
        c.setOnClickListener(this);
        deljeno.setOnClickListener(this);
        krat.setOnClickListener(this);
        minus.setOnClickListener(this);
        plus.setOnClickListener(this);
        enacaj.setOnClickListener(this);

        ena.setOnLongClickListener(this);
        nic.setOnLongClickListener(this);
        dve.setOnLongClickListener(this);
        tri.setOnLongClickListener(this);
        stiri.setOnLongClickListener(this);
        pet.setOnLongClickListener(this);
        sest.setOnLongClickListener(this);
        sedem.setOnLongClickListener(this);
        osem.setOnLongClickListener(this);
        devet.setOnLongClickListener(this);
        deljeno.setOnLongClickListener(this);
        minus.setOnLongClickListener(this);
        plus.setOnLongClickListener(this);
        krat.setOnLongClickListener(this);
        enacaj.setOnLongClickListener(this);
        c.setOnLongClickListener(this);


    }


    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    public void openActivity2() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.button0:
                openActivity2();
                break;
            case R.id.button1:
                openActivity2();
                break;
            case R.id.button2:
                openActivity2();
                break;
            case R.id.button3:
                openActivity2();
                break;
            case R.id.button4:
                openActivity2();
                break;
            case R.id.button5:
                openActivity2();
                break;
            case R.id.button6:
                openActivity2();
                break;
            case R.id.button7:
                openActivity2();
                break;
            case R.id.button8:
                openActivity2();
                break;
            case R.id.button9:
                openActivity2();
                break;
            case R.id.buttonadd:
                openActivity2();
                break;
            case R.id.buttonC:
                openActivity2();
                break;
            case R.id.buttondiv:
                openActivity2();
                break;
            case R.id.buttoneql:
                openActivity2();
                break;
            case R.id.buttonmul:
                openActivity2();
                break;
            case R.id.buttonsub:
                openActivity2();
                break;
            default:
                break;
        }
        return false;
    }
}

