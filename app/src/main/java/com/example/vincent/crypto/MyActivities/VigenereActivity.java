package com.example.vincent.crypto.MyActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vincent.crypto.ChiffrementClass.VigenereChiffrement;
import com.example.vincent.crypto.R;

public class VigenereActivity extends AppCompatActivity {
    private static final String TAG = "VigenereActivity";

    // widgets
    private Button btnCrypter;
    private Button btnDecrypter;
    private EditText edtKey;
    private EditText edtMessage;
    private TextView tvResponse;

    private String key = "";
    private String message = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigenere);
        setTitle("Vigenère");


        btnCrypter = findViewById(R.id.btnCrypter);
        btnDecrypter = findViewById(R.id.btnDecrypter);
        edtKey = findViewById(R.id.edtKey);
        edtMessage = findViewById(R.id.edtMessage);
        tvResponse = findViewById(R.id.tvResponse);
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
            String messageFinal = VigenereChiffrement.crypt(key, message);
            tvResponse.setText(messageFinal);
            Log.d(TAG, "messageFinal : " + messageFinal);
        }
    }

    public void decrypt(View view) {
        if (noError()) {
            String messageFinal = VigenereChiffrement.decrypt(key, message);
            tvResponse.setText(messageFinal);
            Log.d(TAG, "messageFinal : " + messageFinal);
        }
    }

    private boolean noError() {
        // vérification du décalage
        message = String.valueOf(edtMessage.getText());
        key = String.valueOf(edtKey.getText());

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
