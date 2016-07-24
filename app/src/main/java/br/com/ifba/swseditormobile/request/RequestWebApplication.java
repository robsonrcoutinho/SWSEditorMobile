package br.com.ifba.swseditormobile.request;

/**
 * Created by Robson on 17/01/2016.
 */
import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RequestWebApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                            .name(RealmConfiguration.DEFAULT_REALM_NAME)
                            .schemaVersion(0)
                            .deleteRealmIfMigrationNeeded()
                            .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

}
