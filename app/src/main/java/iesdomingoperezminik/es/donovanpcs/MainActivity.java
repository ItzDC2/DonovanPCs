package iesdomingoperezminik.es.donovanpcs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import iesdomingoperezminik.es.donovanpcs.adapters.PCAdapter;
import iesdomingoperezminik.es.donovanpcs.model.PC;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageButton backButton;
    private ImageButton addButton;

    private ListView itemsView;
    private ArrayList<PC> ordenadores = new ArrayList<>();

    private PCAdapter adapter;
    private int pos;
    private int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backButton = (ImageButton) findViewById(R.id.backButton);
        addButton = (ImageButton) findViewById(R.id.addButton);
        itemsView = (ListView) findViewById(R.id.itemsView);

        TextView totalView = (TextView) findViewById(R.id.totalView);
        totalView.setText(totalView.getText() + " " + total + "€");

        backButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

        itemsView.setOnItemClickListener(this);

        registerForContextMenu(itemsView);

        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.deleteContextMenu:
                removeElement(i.position);
                break;
            case R.id.editContextMenu:
                editElement(i.position);
                break;
        }
        return super.onContextItemSelected(item);
    }



    private void editElement(int position) {
        PC pc = ordenadores.get(position);
        View dialogView = getLayoutInflater().inflate(R.layout.form_dialog, null);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(dialogView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();

        EditText cpuText = (EditText) dialogView.findViewById(R.id.cpuText);
        EditText placaBaseText = (EditText) dialogView.findViewById(R.id.placaBaseText);
        EditText tarjetaGraficaText = (EditText) dialogView.findViewById(R.id.tarjetaGraficaText);
        EditText ramText = (EditText) dialogView.findViewById(R.id.ramText);
        EditText precioText = (EditText) dialogView.findViewById(R.id.precioText);
        ImageView imageView = dialogView.findViewById(R.id.defaultImageView);
        TextView totalView = (TextView) findViewById(R.id.totalView);

        imageView.setBackgroundResource(pc.getImageView());
        cpuText.setText(pc.getProcesador());
        placaBaseText.setText(pc.getPlacaBase());
        tarjetaGraficaText.setText(pc.getTarjetaGrafica());
        ramText.setText(pc.getRam());
        precioText.setText(String.valueOf(pc.getPrecio()));

        imageView.setOnClickListener(e -> {
            abrirArchivo();
        });

        Button aceptarButton = (Button) dialogView.findViewById(R.id.aceptarButton);

        aceptarButton.setOnClickListener(e -> {
            if(validateErrors(cpuText, placaBaseText, tarjetaGraficaText, ramText, precioText)) {
                ordenadores.set(position, newPC(cpuText.getText().toString(), placaBaseText.getText().toString(), tarjetaGraficaText.getText().toString(), ramText.getText().toString(), Integer.parseInt(precioText.getText().toString())));
                adapter = new PCAdapter(this, ordenadores);
                itemsView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                TextView errorView = dialogView.findViewById(R.id.errorView);
                errorView.setText("");
                total = 0;
                for(PC ordenador : ordenadores) {
                    total += ordenador.getPrecio();
                }

                totalView.setText(getString(R.string.total) + " " + total + " €");

                dialog.dismiss();
                crearNotificacion("Se editado el pc " + ordenadores.get(position).getProcesador() + ".");
            } else {
                checkErrors(cpuText, placaBaseText, tarjetaGraficaText, ramText, precioText, dialogView.findViewById(R.id.errorView));
            }
        });

    }

    private void abrirArchivo() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivity(i);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int colIndex = c.getColumnIndex(filePath[0]);
                    String path = c.getString(colIndex);
                    c.close();
                }
        }
    }

    private void checkErrors(EditText cpuText, EditText placaBaseText, EditText tarjetaGraficaText, EditText ramText, EditText precioText, TextView errorView) {
        if(cpuText.getText().toString().length() == 0)
            errorView.setText("¡El campo del CPU está vacío!");
        else if(placaBaseText.getText().toString().length() == 0)
            errorView.setText("¡El campo de la placa base está vacío!");
        else if(tarjetaGraficaText.getText().toString().length() == 0)
            errorView.setText("¡El campo de la tarjeta gráfica está vacío!");
        else if(ramText.getText().toString().length() == 0)
            errorView.setText("¡El campo de la RAM está vacío!");
        else if(precioText.getText().toString().length() == 0)
            errorView.setText("¡El campo del precio está vacío!");

    }

    private void removeElement(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar")
                .setMessage("¿Está seguro que desea eliminar este elemento?")
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton(R.string.aceptar, (d, i) -> {
                    ordenadores.remove(position);
                    adapter.notifyDataSetChanged();
                    total = 0;

                    for(PC pc : ordenadores) {
                        total += pc.getPrecio();
                    }

                    TextView totalView = (TextView) findViewById(R.id.totalView);
                    totalView.setText(getString(R.string.total) + " " + total + " €");
                })
                .setNegativeButton(R.string.cancelar, (d, i) -> {d.dismiss();}).show();

    }

    private boolean validateErrors(EditText cpuText, EditText placaBaseText, EditText tarjetaGraficaText, EditText ramText, EditText precioText) {
        return cpuText.getText().length() > 0 && placaBaseText.getText().length() > 0
                && tarjetaGraficaText.getText().length() > 0 && ramText.getText().length() > 0
                && precioText.getText().length() > 0;
    }

    private PC newPC(String cpu, String placaBase, String tarjetaGrafica, String ram, int precio) {
        return new PC(R.drawable.ic_computer_default, cpu, placaBase, ram, tarjetaGrafica, precio);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.addButton:
                addPC();
                break;
        }
    }

    private void addPC() {
        ordenadores.add(new PC(R.drawable.ic_computer_default, "CPU", "Placa Base", "32GB", "GTX 1650", 1000));
        adapter = new PCAdapter(this, ordenadores);
        itemsView.setAdapter(adapter);

        total = 0;

        for(PC pc : ordenadores) {
            total += pc.getPrecio();
        }

        TextView totalView = (TextView) findViewById(R.id.totalView);
        totalView.setText(getString(R.string.total) + " " + total + " €");

        crearNotificacion("Se ha agregado un pc correctamente.");
    }

    private void crearCanalDeNotificaciones(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel("NOTIFICACIONES", name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void crearNotificacion(String msg) {
        crearCanalDeNotificaciones();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "NOTIFICACIONES");
        builder.setSmallIcon(R.drawable.app_icon);
        builder.setContentTitle("DonovanPCs");
        builder.setContentText(msg);
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(0, builder.build());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        PC pc = ordenadores.get(pos);
        String procesador = pc.getProcesador();
        String placaBase = pc.getPlacaBase();
        String tarjetaGrafica = pc.getTarjetaGrafica();
        String ram = pc.getRam();
        String precio = String.valueOf(pc.getPrecio());

        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("procesador", procesador);
        i.putExtra("placaBase", placaBase);
        i.putExtra("tarjetaGrafica", tarjetaGrafica);
        i.putExtra("ram", ram);
        i.putExtra("precio", precio);
        startActivity(i);

    }
}