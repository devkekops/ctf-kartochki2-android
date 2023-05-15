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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.ctf.kartochki2.pojo.WordList;

public class MainActivity extends AppCompatActivity {
    TextView labelView;
    TextView statusView;
    TextView wordView;
    EditText inputText;
    Button button;
    ArrayList<Pair<String, String>> espRus = new ArrayList<Pair<String, String>>();
    int counter = 0;
    int wordCount = 0;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = APIClient.getClient().create(APIInterface.class);

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

        /**
         GET List Users
         **/
        Call<WordList> call = apiInterface.doGetWords();
        call.enqueue(new Callback<WordList>() {
            @Override
            public void onResponse(Call<WordList> call, Response<WordList> response) {
                WordList wordList = response.body();
                List<WordList.Word> words = wordList.words;

                for (WordList.Word word : words) {
                    Toast.makeText(MainActivity.this, "esp : " + word.esp + " rus: " + word.rus, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WordList> call, Throwable t) {
                call.cancel();
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