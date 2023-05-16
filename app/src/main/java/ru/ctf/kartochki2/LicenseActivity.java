package ru.ctf.kartochki2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.ctf.kartochki2.pojo.LicenseKey;
import ru.ctf.kartochki2.pojo.Word;

public class LicenseActivity extends AppCompatActivity {
    EditText inputText;
    Button button;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        inputText = (EditText)findViewById(R.id.inputText);
        button = (Button)findViewById(R.id.button);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activate();
            }
        });
    }

    private void activate() {
        String key = inputText.getText().toString();
        LicenseKey licenseKey = new LicenseKey(key);
        Call<Void> call = apiInterface.doActivate(licenseKey);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response){
                if (response.code() == 200) {
                    stateDialog(true);
                } else {
                    stateDialog(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
            }
        });
    }

    private void stateDialog(Boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Активация лицензии");
        if (success) {
            builder.setMessage("Лицензия активирована успешно!");
            builder.setPositiveButton("Oк", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(LicenseActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    LicenseActivity.this.startActivity(intent);
                    LicenseActivity.this.finish();
                }
            });
        } else {
            builder.setMessage("Лицензия не активирована! Проверьте корректность ключа");
            builder.setPositiveButton("Oк", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

