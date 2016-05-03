package com.deguet.joris.tp4.data;

import android.content.Context;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1452284 on 2016-04-26.
 */
public class PurchaseCRUD implements CRUD<Purchase>{


    Gson gson = new Gson();

    final Class<Purchase> classe = Purchase.class;

    Context context;

    public PurchaseCRUD(Context c){
        this.context = c;
        this.createStorage();
    }

    public List<Purchase> getAll() {
        synchronized (classe) {
            List<Purchase> res = new ArrayList<Purchase>();
            File base = context.getFilesDir();
            for (File f : base.listFiles()){
                if(!f.getName().endsWith(".purchase"))
                    continue;

                try{
                    //System.out.println("File is "+f.getName());
                    String content = FileUtils.readFileToString(f);
                    Purchase a = gson.fromJson(content, classe);
                    res.add(a);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            return res;
        }
    }

    @Override
    public void deleteOne(Long o) {
        this.deleteOne(this.getById(o));
    }

    public long save(Purchase a) {
        synchronized (classe) {
            // set the id
            if (a.getId() == null) a.setId(this.nextAvailableId());
            //
            String serialise = gson.toJson(a);
            File base = context.getFilesDir();
            try {
                FileUtils.writeStringToFile(new File(base, a.getId()+".purchase"), serialise);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return a.getId();
        }
    }

    @Override
    public void saveMany(Iterable<Purchase> list) {
        for (Purchase p : list ){
            this.save(p);
        }
    }

    @Override
    public void saveMany(Purchase... list) {
        for (Purchase p : list ){
            this.save(p);
        }
    }

    @Override
    public Purchase getById(Long id) {
        synchronized (classe) {
            String content;
            try {
                File base = context.getFilesDir();
                File f = new File(base,id+".purchase");
                if (!f.exists()) return null;
                content = FileUtils.readFileToString(new File(base,id+".purchase"));
                Purchase a = gson.fromJson(content, classe);
                return a;
            } catch (IOException e) {
                return null;
            }
        }
    }

    public void deleteOne(Purchase a) {
        synchronized (classe) {
            File base = context.getFilesDir();
            File f = new File(base, a.getId()+".purchase");
            f.delete();
        }
    }

    public void deleteAll() {
        deleteStorage();
        createStorage();
    }

    // autre methodes hors acces aux donnees pour la gestion.

    private long nextAvailableId(){
        synchronized (classe) {
            long max = 0;
            for (Purchase a : getAll()){
                if (a.getId() > max) max = a.getId();
            }
            return max+1;
        }
    }

    public void deleteStorage(){
        File base = context.getFilesDir();
        deleteFolder(base);
    }

    public void createStorage(){
        File base = context.getFilesDir();
        base.mkdirs();
    }

    private static void deleteFolder(File folder) {
        try{File[] files = folder.listFiles();
            if(files!=null) {
                for(File f: files) {
                    if(f.isDirectory())
                        deleteFolder(f);
                    else
                        f.delete();
                }
            }
            folder.delete();
        }catch(Exception e){}
    }
}
