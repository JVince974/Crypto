package com.example.vincent.crypto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.vincent.crypto.MyActivities.AtbashActivity;
import com.example.vincent.crypto.MyActivities.CesarActivity;
import com.example.vincent.crypto.MyActivities.DelastelleActivity;
import com.example.vincent.crypto.MyActivities.PlayFairActivity;
import com.example.vincent.crypto.MyActivities.PolybeActivity;
import com.example.vincent.crypto.MyActivities.RectangulaireActivity;
import com.example.vincent.crypto.MyActivities.VigenereActivity;

/**
 * Le menu principal, contient tous les boutons qui permet
 * à l'utilisateur de choisir son algorithme de chiffrement
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        debug(); // TODO: 06/04/2018 comment this code after
    }

    public void cesar(View view) {
        Intent intent = new Intent(this, CesarActivity.class);
        startActivity(intent);
    }

    public void atbash(View view) {
        Intent intent = new Intent(this, AtbashActivity.class);
        startActivity(intent);
    }

    public void vigenere(View view) {
        Intent intent = new Intent(this, VigenereActivity.class);
        startActivity(intent);
    }

    public void playfair(View view) {
        Intent intent = new Intent(this, PlayFairActivity.class);
        startActivity(intent);
    }

    public void polybe(View view) {
        Intent intent = new Intent(this, PolybeActivity.class);
        startActivity(intent);
    }

    public void rectangulaire(View view) {
        Intent intent = new Intent(this, RectangulaireActivity.class);
        startActivity(intent);
    }

    public void delastelle(View view) {
        Intent intent = new Intent(this, DelastelleActivity.class);
        startActivity(intent);
    }


    /**
     * Debuggage
     */
    private void debug() {
        // TODO: 07/04/2018 remove this line
    }

}
