package model;

import java.util.ArrayList;
import java.util.List;

public class Kosik {
    private List<PolozkaKosiku> seznamPolozek;

    public Kosik() {
        seznamPolozek = new ArrayList<>();
    }

    public void pridej(Zbozi zbozi) {
        seznamPolozek.add(new PolozkaKosiku(zbozi, 1));
    }

    public void odeber(int index) {
        seznamPolozek.remove(index);
    }

    public void vysypat() {
        seznamPolozek.clear();
    }

    public void zvysit(int index) {
        seznamPolozek.get(index).zvysit();
    }

    public void snizit(int index) {
        seznamPolozek.get(index).snizit();
    }
}
