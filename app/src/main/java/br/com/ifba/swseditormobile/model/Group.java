package br.com.ifba.swseditormobile.model;

/**
 * Created by Robson on 17/01/2016.
 */
import java.util.ArrayList;

public class Group {

    private String nameGroup;
    private ArrayList<ChildGrupo> childGrupos;

    public ArrayList<ChildGrupo> getChildGrupos() {
        return childGrupos;
    }

    public void setChildGrupos(ArrayList<ChildGrupo> childGrupos) {
        this.childGrupos = childGrupos;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String name) {
        this.nameGroup = name;
    }



}

