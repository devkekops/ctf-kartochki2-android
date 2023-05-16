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
import ru.ctf.kartochki2.pojo.Word;

public class MainActivity extends AppCompatActivity {
    TextView labelView;
    TextView statusView;
    TextView wordView;
    EditText inputText;
    Button button;
    List<Word> espRus;
    int counter = 0;
    int wordCount = 0;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        labelView = (TextView)findViewById(R.id.labelView);
        statusView = (TextView)findViewById(R.id.statusView);
        wordView = (TextView)findViewById(R.id.wordView);
        inputText = (EditText)findViewById(R.id.inputText);
        button = (Button)findViewById(R.id.button);

        /**
         GET List Words
         **/
        Call<List<Word>> call = apiInterface.doGetWords();
        call.enqueue(new Callback<List<Word>>() {
            @Override
            public void onResponse(Call<List<Word>> call, Response<List<Word>> response) {
                espRus = response.body();
                wordCount = espRus.size();

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        check();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Word>> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
            }
        });
    }

    private void check() {
        if (inputText.getText().toString().equals(espRus.get(counter).rus)) {
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
        builder.setTitle("Бесплатные слова закончились");
        builder.setMessage("Свяжитесь с админом для оплаты подписки");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                counter = 0;
                showNextEsp();
            }
        });
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
        wordView.setText(espRus.get(counter).esp);
        statusView.setText("Слово " + String.valueOf(counter + 1) + "/" + String.valueOf(wordCount));
    }
}