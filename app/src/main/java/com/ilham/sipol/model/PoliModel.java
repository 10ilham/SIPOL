package com.ilham.sipol.model;

public class PoliModel {

    private String _id_poli;
    private String _nama_poli;
    public PoliModel(){}

    public PoliModel(String _id_poli, String _nama_poli) {
        this._id_poli = _id_poli;
        this._nama_poli = _nama_poli;
    }

    public String get_id_poli(){
        return _id_poli;
    }

    public void set_id_poli(String _id_poli) { this._id_poli = _id_poli;}

    public String get_nama_poli() {return _nama_poli;}
    public void set_nama_poli(String _nama_poli) {this._nama_poli = _nama_poli;}
}
