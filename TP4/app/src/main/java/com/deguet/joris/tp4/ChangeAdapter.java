package com.deguet.joris.tp4;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.deguet.joris.tp4.monnayeur.deguet.ArgentPhysique;
import com.deguet.joris.tp4.monnayeur.deguet.Change;

import java.util.List;
/**
 * Created by 1146390 on 2016-05-03.
 */
public class ChangeAdapter extends ArrayAdapter<ArgentPhysique>{

    public ChangeAdapter(Context context, List<ArgentPhysique> objects) {
        super(context, R.layout.list_product, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // transformer le fichier XML en objets Java : gonfler
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        View row = inflater.inflate(R.layout.list_billet, parent, false);

        // le bouton plus se souvient qu'il correspond à cet item
        // seul moyen de distinguer tous les boutons +
        ArgentPhysique item = getItem(position);
        row.findViewById(R.id.btnPlus).setTag(item);
        row.findViewById(R.id.btnMinus).setTag(item);

        // remplir le layout avec les bonnes valeurs
        TextView prodTV = (TextView) row.findViewById(R.id.lblName);
        prodTV.setText(item.nomLisible());

        TextView qtyTV = (TextView) row.findViewById(R.id.lblQty);
        qtyTV.setText(String.valueOf(item.getAmounts()));

        // attache l'item a la ligne au complet si on veut réagir à un clic partout
        row.setTag(item);
        return row;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
