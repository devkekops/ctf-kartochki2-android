package ru.ctf.kartochki2;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Pair;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView labelView;
    TextView statusView;
    TextView wordView;
    EditText inputText;
    Button button;
    ArrayList<Pair<String, String>> espRus = new ArrayList<Pair<String, String>>();
    int counter = 0;
    int wordCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        espRus.add(new Pair<String, String>("chica", "девушка"));
        espRus.add(new Pair<String, String>("padre", "отец"));
        espRus.add(new Pair<String, String>("сuatro", "четыре"));
        espRus.add(new Pair<String, String>("perro", "собака"));
        espRus.add(new Pair<String, String>("hora", "час"));
        espRus.add(new Pair<String, String>("raton", "мышь"));
        espRus.add(new Pair<String, String>("trabajo", "работа"));
        wordCount = espRus.size();

        labelView = (TextView)findViewById(R.id.labelView);
        statusView = (TextView)findViewById(R.id.statusView);
        wordView = (TextView)findViewById(R.id.wordView);
        inputText = (EditText)findViewById(R.id.inputText);
        button = (Button)findViewById(R.id.button);

        showNextEsp();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
    }

    private void check() {
        if (inputText.getText().toString().equals(espRus.get(counter).second)) {
            Toast.makeText(MainActivity.this, "Правильно!", Toast.LENGTH_SHORT).show();
            counter++;
            if (counter == wordCount) {
                stateDialog();
            } else {
                showNextEsp();
            }
        } else {
            Toast.makeText(MainActivity.this, "Неправильно!", Toast.LENGTH_SHORT).show();
        }
        inputText.getText().clear();
    }

    private void stateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Поздравляем!");
        builder.setMessage("Вы перевели все слова");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                counter = 0;
                showNextEsp();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showNextEsp() {
        wordView.setText(espRus.get(counter).first);
        statusView.setText("Слово " + String.valueOf(counter + 1) + "/" + String.valueOf(wordCount));
    }
}