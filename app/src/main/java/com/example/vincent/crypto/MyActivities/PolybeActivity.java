package com.example.vincent.crypto.MyActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vincent.crypto.ChiffrementClass.PolybeChiffrement;
import com.example.vincent.crypto.R;

public class PolybeActivity extends AppCompatActivity {
    private static final String TAG = "PolybeActivity";

    // widgets
    private Button btnCrypter;
    private Button btnDecrypter;
    private EditText edtMessage;
    private TextView tvResponse;

    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polybe);
        setTitle("Carré Polybe");

        btnCrypter = findViewById(R.id.btnCrypter);
        btnDecrypter = findViewById(R.id.btnDecrypter);
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
            String messageFinal = PolybeChiffrement.crypt(message);
            tvResponse.setText(messageFinal);
            Log.d(TAG, "messageFinal : " + messageFinal);
        }
    }

    public void decrypt(View view) {
        if (noError()) {
            String messageFinal = null;
            try {
                messageFinal = PolybeChiffrement.decrypt(message);
            } catch (Exception e) {
                messageFinal = e.getMessage();
                e.printStackTrace();
            }
            tvResponse.setText(messageFinal);
            Log.d(TAG, "messageFinal : " + messageFinal);
        }
    }

    private boolean noError() {
        // vérification du décalage
        message = String.valueOf(edtMessage.getText());

        // vérifier que les champs ne sont pas vides
        if (message.trim().isEmpty()) {
            edtMessage.requestFocus();
            return false;
        } else
            return true;
    }
}
