package com.sahtek.sahtek.Controller;

import com.sahtek.sahtek.Model.Users;
import com.sahtek.sahtek.Service.UtilisateurService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService service;
    private final FirebaseAuth firebaseAuth;

     @GetMapping("/moi")
    public Users moi(Authentication auth) throws Exception {
        UserRecord user = firebaseAuth.getUser(auth.getName());
        return service.obtenirOuCreer(auth.getName(), user.getEmail(), user.getDisplayName());
    }
}
