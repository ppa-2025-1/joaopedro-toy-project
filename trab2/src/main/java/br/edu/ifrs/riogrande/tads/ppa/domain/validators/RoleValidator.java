package br.edu.ifrs.riogrande.tads.ppa.domain.validators;

import br.edu.ifrs.riogrande.tads.ppa.domain.models.NewUser;
import br.edu.ifrs.riogrande.tads.ppa.domain.models.Role;
import br.edu.ifrs.riogrande.tads.ppa.repository.RoleRepository;

import java.util.Set;

public class RoleValidator {

    private RoleValidator() {
    }

    public static void validator(NewUser newUser, Set<Role> roles, RoleRepository roleRepository) {
        Set<Role> additionalRoles = roleRepository.findByNameIn(newUser.roles());

        if (additionalRoles.size() != newUser.roles().size()) {
            throw new IllegalArgumentException("Alguns papéis não existem");
        }

        if (roles.isEmpty()) {
            throw new IllegalArgumentException("O usuário deve ter pelo menos um papel");
        }
    }
}
