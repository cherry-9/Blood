package com.example.spectorh.bloodbank;

/**
 * Created by SpectorH on 21-02-2017.
 */

public class recipient {
    private int _sno;
    private String _name;
    private String _uid;
    private String _phno;
    private String _add;
    private String _bg;
    private int _pint;

    recipient(){}

    recipient(int sno,String name,String uid,String phno,String add,String bg,int pint){
        this._sno=sno;
        this._name=name;
        this._add=add;
        this._bg=bg;
        this._phno=phno;
        this._uid=uid;
        this._pint=pint;
    }

    public int get_sno() {
        return _sno;
    }

    public void set_sno(int _sno) {
        this._sno = _sno;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_uid() {
        return _uid;
    }

    public void set_uid(String _uid) {
        this._uid = _uid;
    }

    public String get_phno() {
        return _phno;
    }

    public void set_phno(String _phno) {
        this._phno = _phno;
    }

    public String get_add() {
        return _add;
    }

    public void set_add(String _add) {
        this._add = _add;
    }

    public String get_bg() {
        return _bg;
    }

    public void set_bg(String _bg) {
        this._bg = _bg;
    }

    public void set_pint(int _pint) {
        this._pint = _pint;
    }
    public int get_pint(){return _pint;}
}

