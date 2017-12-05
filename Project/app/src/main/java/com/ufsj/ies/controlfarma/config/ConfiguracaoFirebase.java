package com.ufsj.ies.controlfarma.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by JV on 21/09/2017.
 */
public final class ConfiguracaoFirebase {
    private static DatabaseReference databaseReference;
    private static FirebaseAuth firebaseAuth;

    public static DatabaseReference getFirebase(){
        if(databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){
        if(firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public static DatabaseReference getNodoNomeado(String nome){
        DatabaseReference referencia = ConfiguracaoFirebase.getFirebase();
        return referencia.child(nome);

    }

}
