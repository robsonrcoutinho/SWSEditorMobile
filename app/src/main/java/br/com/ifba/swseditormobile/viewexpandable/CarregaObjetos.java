package br.com.ifba.swseditormobile.viewexpandable;

import java.util.ArrayList;
import java.util.List;

import br.com.ifba.swseditormobile.model.ChildGrupo;

/**
 * Created by Robson on 06/04/2016.
 */
public class CarregaObjetos {
    private String a;
    private String b;
    private static List<String> listaAddress;

    public static List<ChildGrupo> preencheExpandable(){
        List<ChildGrupo> hRESTS = new ArrayList<>();
        ChildGrupo ch1 = new ChildGrupo();
        ch1.setNomeChildGrupo("Service");

        ChildGrupo ch2 = new ChildGrupo();
        ch2.setNomeChildGrupo("Address");

        ChildGrupo ch3 = new ChildGrupo();
        ch3.setNomeChildGrupo("Method");

        ChildGrupo ch4 = new ChildGrupo();
        ch4.setNomeChildGrupo("Input");

        ChildGrupo ch5 = new ChildGrupo();
        ch5.setNomeChildGrupo("Output");

        hRESTS.add(ch1);
        hRESTS.add(ch2);
        hRESTS.add(ch3);
        hRESTS.add(ch4);
        hRESTS.add(ch5);


        return hRESTS;
    }


}
