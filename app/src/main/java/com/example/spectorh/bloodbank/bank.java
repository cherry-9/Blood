package com.example.spectorh.bloodbank;

public class bank {
    private int _sno;
    private String _bg;
    private int _pint;

    bank(){}

    bank(int sno,String bg,int pint){
        this._bg=bg;
        this._pint=pint;
        this._sno=sno;
    }

    public int get_sno() {
        return _sno;
    }

    public void set_sno(int _sno) {
        this._sno = _sno;
    }

    public String get_bg() {
        return _bg;
    }

    public void set_bg(String _bg) {
        this._bg = _bg;
    }

    public int get_pint() {
        return _pint;
    }

    public void set_pint(int _pint) {
        this._pint = _pint;
    }
}
