package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //buttons
        Button[] buttons = new Button[18];
        buttons[0] = findViewById(R.id.btn0);
        buttons[1] = findViewById(R.id.btn1);
        buttons[2] = findViewById(R.id.btn2);
        buttons[3] = findViewById(R.id.btn3);
        buttons[4] = findViewById(R.id.btn4);
        buttons[5] = findViewById(R.id.btn5);
        buttons[6] = findViewById(R.id.btn6);
        buttons[7] = findViewById(R.id.btn7);
        buttons[8] = findViewById(R.id.btn8);
        buttons[9] = findViewById(R.id.btn9);
        buttons[10] = findViewById(R.id.btndot);
        buttons[11] = findViewById(R.id.btnplus);
        buttons[12] = findViewById(R.id.btnminus);
        buttons[13] = findViewById(R.id.btnmultiply);
        buttons[14] = findViewById(R.id.btndivide);
        buttons[15] = findViewById(R.id.btnequal);
        buttons[16] = findViewById(R.id.btnClear);
        buttons[17] = findViewById(R.id.btnDelete);

        TextView inputText = findViewById(R.id.inputText);
        TextView result = findViewById(R.id.result);

        //input
        for (int i = 0; i <= 14; i++) {
            int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String txt = inputText.getText().toString();
                    String txt1 = buttons[finalI].getText().toString();
                    inputText.setText(txt+txt1);
                }
            });
        }

        //clear
        buttons[16].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputText.setText("");
            }
        });

        //delete
        buttons[17].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = inputText.getText().toString();
                String new_txt = "";
                int len = txt.length();
                for (int i = 0; i < len-1; i++) {
                    new_txt = new_txt + txt.charAt(i);
                }
                inputText.setText(new_txt);
            }
        });

        //equal
        buttons[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expression = inputText.getText().toString();
                int length = expression.length();
                Stack<String> operands = new Stack<>();
                Stack<String> operators = new Stack<>();
                String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."};
                String str = "";
                String temp = "";
                String character;

                if (expression.equals("")) {
                    result.setText("");

                } else {
                    try {

                        //getting operators and operands
                        for (int i = 0; i < length; i++) {
                            boolean flag = false;
                            character = Character.toString(expression.charAt(i));

                            if (character.equals("-") || character.equals("+")) {
                                if (i == 0 || temp.equals("×") || temp.equals("÷")) {
                                    str = str + character;
                                    flag = true;
                                }
                            }

                            for (int j = 0; j <= 10; j++) {
                                if (numbers[j].equals(character)) {
                                    str = str + numbers[j];
                                    flag = true;
                                }
                            }

                            if (!flag) {
                                operators.push(character);
                                operands.push(str);
                                str = "";
                            }

                            temp = character;
                        }

                        operands.push(str);


                        Stack<String> operands1 = new Stack<>();
                        Stack<String> operators1 = new Stack<>();
                        int size = operands.size();
                        int size1 = operators.size();

                        for (int i = 0; i < size; i++) {
                            operands1.push(operands.pop());
                        }
                        for (int i = 0; i < size1; i++) {
                            operators1.push(operators.pop());
                        }

                        boolean flag1 = true;

                        for (int i = 0; i <= 9; i++) {
                            if (Character.toString(expression.charAt(length - 1)).equals(numbers[i])) {
                                flag1 = true;
                                break;
                            } else {
                                flag1 = false;
                            }
                        }

                        if (Character.toString(expression.charAt(0)).equals("×") || Character.toString(expression.charAt(0)).equals("÷")) {
                            flag1 = false;
                        }

                        if (size > 1 && size1 > 0 && flag1) {
                            //calculation
                            for (int index = 0; index < size1; index++) {
                                String op1 = operators1.pop();

                                if (op1.equals("+")) {
                                    double x = Double.parseDouble(operands1.pop());
                                    double y = Double.parseDouble(operands1.pop());
                                    double z = x + y;

                                    operands1.push(Double.toString(z));
                                }
                                if (op1.equals("-")) {
                                    double x = Double.parseDouble(operands1.pop());
                                    double y = Double.parseDouble(operands1.pop());
                                    double z = x - y;

                                    operands1.push(Double.toString(z));
                                }
                                if (op1.equals("×")) {
                                    double x = Double.parseDouble(operands1.pop());
                                    double y = Double.parseDouble(operands1.pop());
                                    double z = x * y;

                                    operands1.push(Double.toString(z));
                                }
                                if (op1.equals("÷")) {
                                    double x = Double.parseDouble(operands1.pop());
                                    double y = Double.parseDouble(operands1.pop());
                                    double z = x / y;

                                    operands1.push(Double.toString(z));
                                }
                            }
                            result.setText(operands1.pop());


                        } else {
                            if (size == 1 && size1 == 0 && flag1) {
                                String op = operands1.pop();
                                double floating = Double.parseDouble(op);
                                result.setText(Double.toString(floating));
                            } else {
                                String error = "Invalid Expression";
                                result.setText(error);
                            }
                        }
                    } catch (Exception e) {
                        String error = "Invalid Expression";
                        result.setText(error);
                    }

                }
            }
        });
    }
}
