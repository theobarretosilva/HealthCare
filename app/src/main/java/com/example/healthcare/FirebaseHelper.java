package com.example.healthcare;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseHelper {

    private static StorageReference storageReference;
    private static DatabaseReference databaseReference;
    private static String UIDUsuario;

    public static StorageReference getStorageReference(){
        if(storageReference == null){
            storageReference = FirebaseStorage.getInstance().getReference();
        }
        return storageReference;
    };

    public static DatabaseReference getDatabaseReference(){
        if(databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    public static String getUIDUsuario() {
        if (UIDUsuario == null){
            UIDUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        return UIDUsuario;
    }
}
