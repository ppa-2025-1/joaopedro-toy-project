package br.edu.ifrs.riogrande.tads.ppa.domain.validators;

import br.edu.ifrs.riogrande.tads.ppa.domain.models.NewUser;
import br.edu.ifrs.riogrande.tads.ppa.repository.UserRepository;

public class EmailValidator {

    private EmailValidator() {
    }
    
    public static void validator(NewUser newUser, UserRepository userRepository){
        if (newUser.email() == null) {
            throw new IllegalArgumentException("Email não pode ser nulo");
        }

        if (newUser.email().isEmpty()) {
            throw new IllegalArgumentException("Email não pode estar vazio");
        }

        if (!newUser.email().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email não é válido");
        }

        userRepository.findByEmail(newUser.email())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Usuário com o email " + newUser.email() + " já existe");
                });
    }
}
