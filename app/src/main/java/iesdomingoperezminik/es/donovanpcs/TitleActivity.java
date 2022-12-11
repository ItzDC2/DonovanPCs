package iesdomingoperezminik.es.donovanpcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import iesdomingoperezminik.es.donovanpcs.dialogs.AcercaDeDialog;

public class TitleActivity extends AppCompatActivity {

    private Button empezarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.setTitle("");
        setSupportActionBar(tb);
    }

    @Override
    protected void onResume() {
        super.onResume();

        empezarButton = (Button) findViewById(R.id.empezarButton);

    }

    public void onStart(View view) {

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.acercaDe)
            showHelp();
        return true;
    }

    private void showHelp() {
        AcercaDeDialog dialog = new AcercaDeDialog();
        dialog.show(getSupportFragmentManager(), "");
    }

}