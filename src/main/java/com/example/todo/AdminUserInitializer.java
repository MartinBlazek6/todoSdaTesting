package com.example.todo;

import com.example.todo.model.User;
import com.example.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// POZOR: Zde si vytvořím prvního admin usera s rolí ROLE_ADMIN. Aby po přihlášení na index bylo možno přejít na stránku
// admin přes link Manage Roles . User= admin, Heslo = adminPassword  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// Jsou i jiné způsoby vytvoření prvního ADMIN. Např. přímo zadat do DB tabulky.
@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("adminPassword"));
            adminUser.setRole("ADMIN"); // Ensure this matches your role naming convention
            userRepository.save(adminUser);
        }
    }
}
