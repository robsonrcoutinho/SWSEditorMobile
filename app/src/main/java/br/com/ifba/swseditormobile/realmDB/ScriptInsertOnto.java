package br.com.ifba.swseditormobile.realmDB;

import java.util.ArrayList;
import java.util.List;

import br.com.ifba.swseditormobile.model.Ontologias;

/**
 * Created by Robson on 06/06/2016.
 */
public class ScriptInsertOnto {

    public static ArrayList<Ontologias> ontos(){

        ArrayList<Ontologias> onts = new ArrayList<>();
        Ontologias onto = new Ontologias();

        onto.setId(1 + System.currentTimeMillis());
        onto.setEndereco("http://www.daml.org/services/owl-s/1.0DL/CongoProcess.owl");
        onts.add(onto);


        onto = new Ontologias();
        onto.setId(2 + System.currentTimeMillis());
        onto.setEndereco("http://www.daml.org/services/owl-s/1.1/BravoAirProcess.owl");
        onts.add(onto);

        onto = new Ontologias();
        onto.setId(3 + System.currentTimeMillis());
        onto.setEndereco("http://watson.kmi.open.ac.uk/ontologies/LT4eL/CSnCSv0.01Lex.owl");
        onts.add(onto);

        onto = new Ontologias();
        onto.setId(4 + System.currentTimeMillis());
        onto.setEndereco("http://solitarywalker.livejournal.com/data/foaf");
        onts.add(onto);

        onto = new Ontologias();
        onto.setId(5 + System.currentTimeMillis());
        onto.setEndereco("http://www.daml.org/services/owl-s/1.0/Concepts.owl");
        onts.add(onto);

        onto = new Ontologias();
        onto.setId(6 + System.currentTimeMillis());
        onto.setEndereco("http://www.csl.sri.com/users/denker/owl-sec/ton/security_template.owl");
        onts.add(onto);

        onto = new Ontologias();
        onto.setId(7 + System.currentTimeMillis());
        onto.setEndereco("http://www.csl.sri.com/users/ton/ontologies/security_template.owl");
        onts.add(onto);

        onto = new Ontologias();
        onto.setId(8 + System.currentTimeMillis());
        onto.setEndereco("http://secse.atosorigin.es:10000/ontologies/cyc.owl");
        onts.add(onto);

        onto = new Ontologias();
        onto.setId(9 + System.currentTimeMillis());
        onto.setEndereco("http://i-lovemyfriends.livejournal.com/data/foaf");
        onts.add(onto);

        onto = new Ontologias();
        onto.setId(10 + System.currentTimeMillis());
        onto.setEndereco("http://exemplo.ifba.com.br/data/friends.xsparql");
        onts.add(onto);

        onto = new Ontologias();
        onto.setId(11 + System.currentTimeMillis());
        onto.setEndereco("http://exemplo.ifba.com.br/data/friends.owl");
        onts.add(onto);

        onto = new Ontologias();
        onto.setId(12 + System.currentTimeMillis());
        onto.setEndereco("http://exemplo.ifba.com.br/data/friends.xslt");
        onts.add(onto);

        return onts;

    }
}
