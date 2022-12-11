package iesdomingoperezminik.es.donovanpcs.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AcercaDeDialog extends AppCompatDialogFragment {

    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Acerca de")
                .setMessage("Esta aplicación ha sido creada en Android Studio programando en Java y su autor es Dónovan Castro Fariña estudiante de 2º de DAM.")
                .setPositiveButton("Aceptar", (d, i) -> {});
        return builder.create();
    }

}
