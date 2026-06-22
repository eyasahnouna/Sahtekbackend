package com.sahtek.sahtek.Service;

import com.sahtek.sahtek.Model.Users;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UtilisateurService {

    public Users obtenirOuCreer(String uid, String email, String nomComplet) {
        Users u = new Users();
        u.setUid(uid);
        u.setEmail(email);
        u.setNomComplet(nomComplet);
        u.setCreatedAt(LocalDateTime.now());
        return u;
    }

    public Users obtenirParId(String uid) {
        Users u = new Users();
        u.setUid(uid);
        return u;
    }
}
