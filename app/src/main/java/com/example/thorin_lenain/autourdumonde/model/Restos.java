package com.example.thorin_lenain.autourdumonde.model;

import com.example.thorin_lenain.autourdumonde.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thorin_LeNain on 16/10/2014.
 */
public class Restos {
    private static Restos instance;
    public static Restos getInstance() {
        if (instance == null) {
            instance = new Restos();
            instance.init();
        }
        return instance;
    }



    Map<String, Resto> lstRestos = new HashMap<String, Resto>();
    private Restos(){

    }

    public void addResto (Resto resto){
        if (!lstRestos.containsKey(resto.getId())){
            lstRestos.put(resto.getId(), resto);
        }
    }

    public ArrayList<Resto> getRestos(){
        return new ArrayList(lstRestos.values());
    }

    public Resto getRestoById(String id){
        return lstRestos.get(id);
    }

    private void init(){
        addResto(new Resto("0", "Japonais", "13 Rue Cloche Percé Paris", "20€ à volonté \n 8€ la barquette", R.drawable.logo_sushi, new LatLng(48.856642, 2.357330)));
        addResto(new Resto("1", "Mexicain", "85 rue vesal Paris", "20€ à volonté le soir \n 15€ à volonté le midi", R.drawable.mexicain, new LatLng(48.837980, 2.352183)));
        addResto(new Resto("2", "Americain", "9 port d'ivry Ivry", "20€ à volonté le soir \n 15€ à volonté le midi",R.drawable.americain, new LatLng(48.819529, 2.376525)));
        addResto(new Resto("3", "Japonais", "23 rue mouftard Paris", "Sushi promo 11€ les 20",R.drawable.logo_sushi, new LatLng(48.844306, 2.349521)));
        addResto(new Resto("4", "Chinois", "21 champs Elysees Paris", "20€ à volonté le soir \n" +
                " 15€ à volonté le midi", R.drawable.chinoi, new LatLng(48.869550, 2.309027)));
        addResto(new Resto("5", "Special", "10 Rue Papillon Paris", "Rognon de porc pour 10€ \n" , R.drawable.logo_smal, new LatLng(48.882683, 2.346030)));
    }

}
