package com.example.vincent.crypto.MyActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vincent.crypto.ChiffrementClass.DelastelleChiffrement;
import com.example.vincent.crypto.R;

import java.util.Random;

public class DelastelleActivity extends AppCompatActivity {
    private static final String TAG = "DelastelleActivity";

    // widgets
    private Button btnCrypter;
    private Button btnDecrypter;
    private EditText edtKey;
    private EditText edtMessage;
    private TextView tvResponse;
    private EditText edtN;

    private String key = "";
    private String message = "";
    private int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delastelle);
        setTitle("Delastelle");

        btnCrypter = findViewById(R.id.btnCrypter);
        btnDecrypter = findViewById(R.id.btnDecrypter);
        edtKey = findViewById(R.id.edtKey);
        edtMessage = findViewById(R.id.edtMessage);
        tvResponse = findViewById(R.id.tvResponse);
        edtN = findViewById(R.id.edtN);

        // Choisir valeur par défaut aléatoire pour le décalage (entre 1-10)
        n = 1 + new Random().nextInt(10 - 1);
        edtN.setText(String.valueOf(n));

    }

   /*
     **************************************
     *
     *      BOUTON Crypt et Decrypt
     *
     **************************************
     */

    public void crypt(View view) {
        if (noError()) {
            String messageFinal = DelastelleChiffrement.main(new String[]{key, message, String.valueOf(n), "c"});
            tvResponse.setText(messageFinal);
            Log.d(TAG, "messageFinal : " + messageFinal);
        }
    }

    public void decrypt(View view) {
        if (noError()) {
            String messageFinal = DelastelleChiffrement.main(new String[]{key, message, String.valueOf(n), "d"});
            tvResponse.setText(messageFinal);
            Log.d(TAG, "messageFinal : " + messageFinal);
        }
    }

    private boolean noError() {
        // vérification du décalage
        message = String.valueOf(edtMessage.getText());
        key = String.valueOf(edtKey.getText());

        if (key.length() != 25) {
            tvResponse.setText("La grille de chiffrement doit avoir 25 lettres");
            return false;
        } else if (DelastelleChiffrement.clear(key).length() != 25) {
            tvResponse.setText("La grille de chiffrement contient des caractères en doubles");
            return false;
        }

        // vérification du décalage
        try {
            n = Integer.parseInt(String.valueOf(edtN.getText()));
        } catch (Exception e) {
            edtN.requestFocus();
            return false;
        }
        edtN.setText(String.valueOf(n));


        // vérifier que les champs ne sont pas vides
        if (message.trim().isEmpty()) {
            edtMessage.requestFocus();
            return false;
        } else if (key.trim().isEmpty()) {
            edtKey.requestFocus();
            return false;
        } else
            return true;
    }
}
