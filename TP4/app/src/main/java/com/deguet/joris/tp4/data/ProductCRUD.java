package com.deguet.joris.tp4.data;

import android.content.Context;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1452284 on 2016-04-25.
 */
public class ProductCRUD implements CRUD<Product> {


    Gson gson = new Gson();

    final Class<Product> classe = Product.class;

    Context context;

    public ProductCRUD(Context c){
        this.context = c;
        this.createStorage();
    }

    @Override
    public List<Product> getAll() {
        synchronized (classe) {
            List<Product> res = new ArrayList<Product>();
            File base = context.getFilesDir();
            for (File f : base.listFiles()){
                if(!f.getName().endsWith(".produit"))
                    continue;

                try{
                    //System.out.println("File is "+f.getName());
                    String content = FileUtils.readFileToString(f);
                    Product a = gson.fromJson(content, classe);
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

    @Override
    public long save(Product a) {
        synchronized (classe) {
            // set the id
            if (a.getId() == null) a.setId(this.nextAvailableId());
            //
            String serialise = gson.toJson(a);

            File base = context.getFilesDir();
            try {
                FileUtils.writeStringToFile(new File(base, a.getId()+".produit"), serialise);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return a.getId();

        }
    }

    @Override
    public void saveMany(Iterable<Product> list) {
        for (Product p : list ){
            this.save(p);
        }
    }

    @Override
    public void saveMany(Product... list) {
        for (Product p : list ){
            this.save(p);
        }
    }

    @Override
    public Product getById(Long id) {
        synchronized (classe) {
            String content;
            try {
                File base = context.getFilesDir();
                File f = new File(base,id+".produit");
                if (!f.exists()) return null;
                content = FileUtils.readFileToString(new File(base,id+".produit"));
                Product a = gson.fromJson(content, classe);
                return a;
            } catch (IOException e) {
                return null;
            }
        }
    }

    public Product getByUPC(String UPC) {
        synchronized (classe) {
            List<Product> products = getAll();
            for (Product p : products) {
                if(p.codeBarre.equals(UPC))
                    return p;
            }
            return null;
        }
    }

    @Override
    public void deleteOne(Product a) {
        synchronized (classe) {
            File base = context.getFilesDir();
            File f = new File(base, a.getId()+".produit");
            f.delete();
        }
    }

    @Override
    public void deleteAll() {
        deleteStorage();
        createStorage();
    }

    // autre methodes hors acces aux donnees pour la gestion.

    private long nextAvailableId(){
        synchronized (classe) {
            long max = 0;
            for (Product a : getAll()){
                if (a.getId() > max) max = a.getId();
            }
            return max+1;
        }
    }

    public void deleteStorage(){

        synchronized (classe) {
            List<Product> res = new ArrayList<Product>();
            File base = context.getFilesDir();
            for (File f : base.listFiles()){
                if(!f.getName().endsWith(".produit"))
                    continue;

                try{
                    //System.out.println("File is "+f.getName());
                    FileUtils.forceDelete(f);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
//        deleteFolder(base);
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
