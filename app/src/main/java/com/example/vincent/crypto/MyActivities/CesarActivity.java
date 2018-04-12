package com.example.vincent.crypto.MyActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vincent.crypto.ChiffrementClass.CesarChiffrement;
import com.example.vincent.crypto.R;

import java.util.Random;

public class CesarActivity extends AppCompatActivity {
    private static final String TAG = "CesarActivity";

    // widgets
    private Button btnMinus;
    private Button btnPlus;
    private Button btnCrypter;
    private Button btnDecrypter;
    private EditText edtDecalage;
    private EditText edtMessage;
    private TextView tvResponse;

    private int decalage = 0;
    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesar);
        setTitle("César");

        // récupérer les widgets
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnCrypter = findViewById(R.id.btnCrypter);
        btnDecrypter = findViewById(R.id.btnDecrypter);
        edtDecalage = findViewById(R.id.edtDecalage);
        edtMessage = findViewById(R.id.edtMessage);
        tvResponse = findViewById(R.id.tvResponse);

        // Choisir valeur par défaut aléatoire pour le décalage (entre 1-10)
        decalage = 1 + new Random().nextInt(10 - 1);
        edtDecalage.setText(String.valueOf(decalage));
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
            String messageFinal = CesarChiffrement.crypt(decalage, message);
            tvResponse.setText(messageFinal);
            Log.d(TAG, "messageFinal : " + messageFinal);
        }
    }

    public void decrypt(View view) {
        if (noError()) {
            String messageFinal = CesarChiffrement.decrypt(decalage, message);
            tvResponse.setText(messageFinal);
            Log.d(TAG, "messageFinal : " + messageFinal);
        }
    }

    private boolean noError() {
        // vérification du décalage
        try {
            decalage = Integer.parseInt(String.valueOf(edtDecalage.getText()));
            if (decalage > 1000)
                decalage = 1000;
            else if (decalage < 1)
                decalage = 1;
        } catch (Exception e) {

        }
        edtDecalage.setText(String.valueOf(decalage));

        // vérification du message
        message = String.valueOf(edtMessage.getText());
        if (message.trim().isEmpty()) {
            edtMessage.requestFocus();
            return false;
        }

        Log.d(TAG, "decalage = " + decalage);
        Log.d(TAG, "message = " + message);

        return true;
    }


    /*
     **************************************
     *
     *      BOUTON + et -
     *
     **************************************
     */

    public void plus(View view) {
        if (decalage < 1000) {
            decalage++;
            edtDecalage.setText(String.valueOf(decalage));
        }
    }

    public void minus(View view) {
        if (decalage > 1) {
            decalage--;
            edtDecalage.setText(String.valueOf(decalage));
        }
    }


}
