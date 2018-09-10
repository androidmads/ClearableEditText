package com.androidmads.materialedittext;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Mushtaq on 02/09/2018.

 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextInputLayout textInputLayout = findViewById(R.id.text_input_layout);
        final ClearableEditText editText = findViewById(R.id.edit_text);
        final Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().equals(""))
                    textInputLayout.setError("You need to enter a name");
                else
                    textInputLayout.setError(null);
            }
        });

        editText.setDrawableClickListener(new ClearableEditText.DrawableClickListener() {
            @Override
            public void onClick() {
                editText.setText(null);
            }
        });
    }
}
