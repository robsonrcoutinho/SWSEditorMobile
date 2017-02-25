package br.com.ifba.swseditormobile.viewexpandable;

import java.util.ArrayList;
import java.util.List;

import br.com.ifba.swseditormobile.model.ChildGrupo;

/**
 * Created by #### on 06/04/2016.
 */
public class CarregaObjetos {

    public static List<ChildGrupo> preencheExpandable(){
        List<ChildGrupo> hRESTS = new ArrayList<>();
        ChildGrupo ch1 = new ChildGrupo();
        ch1.setNomeChildGrupo("Service");

        ChildGrupo ch2 = new ChildGrupo();
        ch2.setNomeChildGrupo("Address");

        ChildGrupo ch3 = new ChildGrupo();
        ch3.setNomeChildGrupo("Operation");


        hRESTS.add(ch1);
        hRESTS.add(ch2);
        hRESTS.add(ch3);



        return hRESTS;
    }


}
