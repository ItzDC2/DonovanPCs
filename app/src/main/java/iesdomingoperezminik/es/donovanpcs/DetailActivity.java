package iesdomingoperezminik.es.donovanpcs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private ImageView detailedImageView;
    private TextView cpuDetailedView;
    private TextView placaBaseDetailedView;
    private TextView tarjetaGraficaDetailedView;
    private TextView ramDetailedView;
    private TextView precioDetailedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String procesador = getIntent().getStringExtra("procesador") != null ? getIntent().getStringExtra("procesador") : null;
        String placaBase = getIntent().getStringExtra("placaBase") != null ? getIntent().getStringExtra("placaBase") : null;
        String tarjetaGrafica = getIntent().getStringExtra("tarjetaGrafica") != null ? getIntent().getStringExtra("tarjetaGrafica") : null;
        String ram = getIntent().getStringExtra("ram") != null ? getIntent().getStringExtra("ram") : null;
        String precio = getIntent().getStringExtra("precio") != null ? getIntent().getStringExtra("precio") : null;

        detailedImageView = (ImageView) findViewById(R.id.detailedImageView);
        cpuDetailedView = (TextView) findViewById(R.id.cpuDetailedView);
        placaBaseDetailedView = (TextView) findViewById(R.id.placaBaseDetailedView);
        tarjetaGraficaDetailedView = (TextView) findViewById(R.id.tarjetaGraficaDetailedView);
        ramDetailedView = (TextView) findViewById(R.id.ramDetailedView);
        precioDetailedView = (TextView) findViewById(R.id.precioDetailedView);

        detailedImageView.setImageResource(R.drawable.ic_computer_default);
        Button aceptarButton = (Button) findViewById(R.id.aceptarDetailedButton);
        aceptarButton.setOnClickListener(e -> finish());

        ImageButton imageButton = (ImageButton) findViewById(R.id.atrasButton);
        imageButton.setOnClickListener(e -> finish());

        if(procesador != null) {
            cpuDetailedView.setText(cpuDetailedView.getText() + " " + procesador);
            placaBaseDetailedView.setText(placaBaseDetailedView.getText() + " " + placaBase);
            tarjetaGraficaDetailedView.setText(tarjetaGraficaDetailedView.getText() + " " + tarjetaGrafica);
            ramDetailedView.setText(ramDetailedView.getText() + " " + ram);
            precioDetailedView.setText(precioDetailedView.getText() + " " + precio + "€");
        }

    }

}