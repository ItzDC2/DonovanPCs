package iesdomingoperezminik.es.donovanpcs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import iesdomingoperezminik.es.donovanpcs.MainActivity;
import iesdomingoperezminik.es.donovanpcs.R;
import iesdomingoperezminik.es.donovanpcs.model.PC;

public class PCAdapter extends ArrayAdapter<PC> {

    private List<PC> ordenadores;

    public PCAdapter(@NonNull Context context, List<PC> ordenadores) {
        super(context, R.layout.pc_row, ordenadores);
        this.ordenadores = new ArrayList<>(ordenadores);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pc_row, null);
        }

        PC pc = ordenadores.get(position);

        ImageView imageView = convertView.findViewById(R.id.pcView);
        imageView.setImageResource(pc.getImageView());

        TextView cpuView = (TextView) convertView.findViewById(R.id.cpuView);
        TextView placaBaseView = (TextView) convertView.findViewById(R.id.placaBaseView);
        TextView tarjetaGraficaView = (TextView) convertView.findViewById(R.id.tarjetaGraficaView);
        TextView ramView = (TextView) convertView.findViewById(R.id.ramView);
        TextView precioView = (TextView) convertView.findViewById(R.id.precioView);

        cpuView.setText(getContext().getString(R.string.model_cpu) + " " + pc.getProcesador());
        placaBaseView.setText(getContext().getString(R.string.model_placaBase) + " " + pc.getPlacaBase());
        tarjetaGraficaView.setText(getContext().getString(R.string.model_tarjetaGrafica) + " " + pc.getTarjetaGrafica());
        ramView.setText(getContext().getString(R.string.model_ram) + " " + pc.getRam());
        precioView.setText(getContext().getString(R.string.model_precio) + " " + pc.getPrecio() + "€");

        return convertView;
    }
}
