package br.edu.ifrs.riogrande.tads.ppa.domain.services;

import br.edu.ifrs.riogrande.tads.ppa.domain.models.NewUser;
import br.edu.ifrs.riogrande.tads.ppa.domain.models.Profile;
import br.edu.ifrs.riogrande.tads.ppa.domain.models.Role;
import br.edu.ifrs.riogrande.tads.ppa.domain.models.User;
import br.edu.ifrs.riogrande.tads.ppa.domain.validators.EmailValidator;
import br.edu.ifrs.riogrande.tads.ppa.domain.validators.HandleValidator;
import br.edu.ifrs.riogrande.tads.ppa.domain.validators.PasswordValidator;
import br.edu.ifrs.riogrande.tads.ppa.domain.validators.RoleValidator;
import br.edu.ifrs.riogrande.tads.ppa.repository.RoleRepository;
import br.edu.ifrs.riogrande.tads.ppa.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private static UserRepository userRepository;
    private RoleRepository roleRepository;
    private Set<String> defaultRoles;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            @Value("${app.user.default.roles}") Set<String> defaultRoles) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.defaultRoles = defaultRoles;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void postMethodName(NewUser newUser) {

        EmailValidator.validator(newUser, userRepository);
        PasswordValidator.validator(newUser);
        HandleValidator.validator(newUser, userRepository);

        User user = new User();

        user.setEmail(newUser.email());
        user.setHandle(!newUser.handle().isEmpty() ? newUser.handle() : generateHandle(newUser.email()));
        user.setPassword(passwordEncoder.encode(newUser.password()));

        Set<Role> roles = new HashSet<>();
        roles.addAll(roleRepository.findByNameIn(defaultRoles));
        RoleValidator.validator(newUser, roles, roleRepository);

        user.setRoles(roles);

        Profile profile = new Profile();

        profile.setName(newUser.name());
        profile.setCompany(newUser.company());
        profile.setType(newUser.type() != null ? newUser.type() : Profile.AccountType.FREE);

        profile.setUser(user);
        user.setProfile(profile);

        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    private static String generateHandle(String email) {
        String[] parts = email.split("@");
        String handle = parts[0];
        int i = 1;
        while (userRepository.existsByHandle(handle)) {
            handle = parts[0] + i++;
        }
        return handle;
    }
}
