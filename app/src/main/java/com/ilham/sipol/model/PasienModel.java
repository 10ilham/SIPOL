package com.ilham.sipol.model;

public class PasienModel {

    private String _id_pasien;
    private String _nama_pasien;
    private String _jk;
    private String _usia;
    private String _gol_darah;
    private String _poli;
    public PasienModel(){}

    public PasienModel(String _id_pasien, String _nama_pasien, String _jk, String _usia, String _gol_darah, String _poli) {
        this._id_pasien = _id_pasien;
        this._nama_pasien = _nama_pasien;
        this._jk = _jk;
        this._usia = _usia;
        this._gol_darah = _gol_darah;
        this._poli = _poli;
    }

    public String get_id_pasien(){
        return _id_pasien;
    }
    public void set_id_pasien(String _id_pasien) { this._id_pasien = _id_pasien;}

    public String get_nama_pasien() {return _nama_pasien;}
    public void set_nama_pasien(String _nama_pasien) {this._nama_pasien = _nama_pasien;}

    public String get_jk() {return _jk;}
    public void set_jk(String _jk) {this._jk = _jk;}

    public String get_usia() { return _usia; }
    public void set_usia(String _usia) { this._usia = _usia; }

    public String get_gol_darah() {return _gol_darah;}
    public void set_gol_darah(String _gol_darah) {this._gol_darah = _gol_darah;}

    public String get_poli() {return _poli;}
    public void set_poli(String _poli) {this._poli = _poli;}

}
