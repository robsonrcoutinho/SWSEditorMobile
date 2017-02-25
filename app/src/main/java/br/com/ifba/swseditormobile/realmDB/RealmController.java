package br.com.ifba.swseditormobile.realmDB;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import br.com.ifba.swseditormobile.model.Ontologias;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by #### on 06/06/2016.
 */
public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Atualize a realm istance
    public void refresh() {

        realm.refresh();
    }

    //Limpar todas objetos de Ontologias.class
    public void clearAll() {
        realm.beginTransaction();
        realm.clear(Ontologias.class);
        realm.commitTransaction();
    }

    //Todas objetos de Ontologias.class
    public RealmResults<Ontologias> getAllOntologias() {
        return realm.where(Ontologias.class).findAll();
    }

    public Ontologias getOntologias(String id) {
        return realm.where(Ontologias.class).equalTo("id", id).findFirst();
    }



}
