package com.example.thorin_lenain.autourdumonde.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Thorin_LeNain on 16/10/2014.
 */
public class Resto {
    private String id;
    private String titre;
    private Object image;

    @Override
    public String toString() {
        return titre + " " + adresse + " " +menu ;
    }

    private String adresse;
    private String menu;
    private LatLng latLng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMenu() {
        return menu;
    }

    public void setCarte(String menu) {
        this.menu = menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public Resto(String id, String titre, String adresse, String menu, Object image, LatLng latLng){
        this.image = image;
        this.id = id;
        this.titre =titre;
        this.adresse = adresse;
        this.menu = menu;
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
