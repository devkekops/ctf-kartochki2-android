package ru.ctf.kartochki2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
    Boolean paid = false;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labelView = (TextView)findViewById(R.id.labelView);
        statusView = (TextView)findViewById(R.id.statusView);
        wordView = (TextView)findViewById(R.id.wordView);
        inputText = (EditText)findViewById(R.id.inputText);
        button = (Button)findViewById(R.id.button);

        apiInterface = APIClient.getClient(this).create(APIInterface.class);

        Call<Void> call = apiInterface.doGetIcon();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });

        Call<List<Word>> call1 = apiInterface.doGetPaidWords();
        call1.enqueue(new Callback<List<Word>>() {
            @Override
            public void onResponse(Call<List<Word>> call, Response<List<Word>> response) {
                if (response.code() == 200) {
                    paid = true;
                    espRus = response.body();
                    wordCount = espRus.size();
                    showWord();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            check();
                        }
                    });
                } else {
                    Call<List<Word>> call2 = apiInterface.doGetFreeWords();
                    call2.enqueue(new Callback<List<Word>>() {
                        @Override
                        public void onResponse(Call<List<Word>> call, Response<List<Word>> response) {
                            paid = false;
                            espRus = response.body();
                            wordCount = espRus.size();
                            showWord();

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
            }

            @Override
            public void onFailure(Call<List<Word>> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (counter == wordCount && counter != 0) {
            stateDialog();
        }
    }

    private void check() {
        if (inputText.getText().toString().equals(espRus.get(counter).rus)) {
            Toast.makeText(MainActivity.this, "Правильно!", Toast.LENGTH_SHORT).show();
            counter++;
            if (counter == wordCount) {
                stateDialog();
            } else {
                showWord();
            }
        } else {
            Toast.makeText(MainActivity.this, "Неправильно!", Toast.LENGTH_SHORT).show();
        }
        inputText.getText().clear();
    }

    private void stateDialog() {
        if (!paid) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Бесплатные слова закончились");
            builder.setMessage("Введите лицензионный ключ чтобы получить больше слов");
            builder.setNegativeButton("Не хочу", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    counter = 0;
                    showWord();
                }
            });
            builder.setPositiveButton("Ввести", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MainActivity.this, LicenseActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            String message = ("sbmt{" + espRus.get(espRus.size()-1).esp.replaceAll(" ", "_") + "}");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Поздравляем! Вот флаг:");
            builder.setMessage(message);
            builder.setPositiveButton("Ура!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    counter = 0;
                    showWord();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void showWord() {
        int wordNumber = counter + 1;
        if (paid) {
            wordNumber = counter + 8;
        }
        wordView.setText(espRus.get(counter).esp);
        statusView.setText("Слово " + String.valueOf(wordNumber) + "/14");
    }
}