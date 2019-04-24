package by.dchebotar.sprboot.service;

import by.dchebotar.sprboot.domain.Role;
import by.dchebotar.sprboot.domain.User;
import by.dchebotar.sprboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null){
            return false;
        } else {
            user.setActive(false);
            user.setRoles(Collections.singleton(Role.USER));
            user.setActivationCode(UUID.randomUUID().toString());
            userRepository.save(user);


            if (!StringUtils.isEmpty(user.getMail())) {
                try (FileReader reader = new FileReader("src\\main\\resources\\properties.properties")){
                    Properties properties = new Properties();
                    properties.load(reader);
                    String prop = properties.getProperty("message_to_activate_user");
                    String message;
                    StringBuilder builder = new StringBuilder("Hello, ");
                    builder.append(user.getUsername());
                    builder.append("! \n Welcome to Appeal. Please, visit next link: ");
                    builder.append(prop);
                    builder.append(user.getActivationCode());
                    System.out.println(builder);
                    message = builder.toString();
                    System.out.println(message);
                    mailSender.send(user.getMail(), "Activation Code", message);
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
            return true;
        }
    }

    public boolean activateUser(String activationcode) {
       User user = userRepository.findByActivationCode(activationcode);
       if (user == null){
           return false;
       }
       user.setActivationCode(null);
       userRepository.save(user);
       return true;
    }
}
