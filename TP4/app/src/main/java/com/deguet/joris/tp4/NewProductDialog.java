package com.deguet.joris.tp4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.deguet.joris.tp4.data.Product;

/**
 * Created by 1452284 on 2016-04-12.
 */
public class NewProductDialog extends DialogFragment {

    MainActivity act;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();


        final View v = inflater.inflate(R.layout.dialog_new_product, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = ((TextView) v.findViewById(R.id.dialog_name)).getText().toString();
                        String price = ((TextView) v.findViewById(R.id.dialog_price)).getText().toString();
                        String code = ((TextView) v.findViewById(R.id.dialog_code)).getText().toString();
                        if (name.length() == 0 || price.length() == 0 || Float.parseFloat(price) < 0 || !BarcodeValidator.isUPCValid(code)) {
                            Toast.makeText(getDialog().getContext(), R.string.product_invalid, Toast.LENGTH_SHORT).show();
                            getDialog().cancel();
                            return;
                        }
//                        if (nullText(name) && nullText(price) && nullText(code)) {

                        act.productCRUD.save(new Product(name, Float.parseFloat(price), code));
//                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public NewProductDialog setAct(MainActivity act) {
        this.act = act;
        return this;
    }
}
