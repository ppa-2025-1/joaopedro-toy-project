package br.edu.ifrs.riogrande.tads.ppa.domain.validators;

import br.edu.ifrs.riogrande.tads.ppa.domain.models.NewUser;

public class PasswordValidator {

    private PasswordValidator() {
    }
    
    public static void validator(NewUser newUser){
        if (newUser.password() == null) {
            throw new IllegalArgumentException("Senha são pode ser nula");
        }

        if (newUser.password().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode estar vazia");
        }

        if (!newUser.password().matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$")) {
            throw new IllegalArgumentException(
                "A senha deve ter pelo menos 8 caracteres e conter pelo menos uma letra e um número");
        }
    }
}
