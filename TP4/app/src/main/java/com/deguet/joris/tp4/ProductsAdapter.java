package com.deguet.joris.tp4;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.deguet.joris.tp4.data.Product;

import java.util.List;

/**
 * Created by 1452284 on 2016-04-12.
 */
public class ProductsAdapter extends ArrayAdapter<Product> {

    public ProductsAdapter(Context context, List<Product> objects) {
        super(context, R.layout.list_product, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // transformer le fichier XML en objets Java : gonfler
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        View row = inflater.inflate(R.layout.list_product, parent, false);

        // le bouton plus se souvient qu'il correspond à cet item
        // seul moyen de distinguer tous les boutons +
        Product item = getItem(position);
        row.findViewById(R.id.btnPlus).setTag(item);
        row.findViewById(R.id.btnMinus).setTag(item);

        // remplir le layout avec les bonnes valeurs
        TextView prodTV = (TextView) row.findViewById(R.id.lblName);
        prodTV.setText(item.name);

        TextView qtyTV = (TextView) row.findViewById(R.id.lblQty);
        qtyTV.setText(String.valueOf(item.amount));

        // attache l'item a la ligne au complet si on veut réagir à un clic partout
        row.setTag(item);
        return row;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        float total = 0;
        MainActivity c = (MainActivity)getContext();
        c.updatePrice(Utils.calculateTotalPrice(c.products));
    }
}
