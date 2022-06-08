package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Model.User;
import spring.Entity.month_user;
import spring.Repository.UserRepository;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void removeUserByUserId(String idUser) {
        User user = userRepository.findUserByUserId(idUser);
        userRepository.delete(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findUserName(String username) {
        return userRepository.findByNameUser(username);
    }

    public User findUserByUserId(String idUser) {
        return userRepository.findUserByUserId(idUser);
    }

    public List<User> findUsersByRole(String roleName){
        return userRepository.findUsersByRole(roleName);
    }

    public void editUserName(String nameUser, String userid) {
        userRepository.editUserName(nameUser, userid);
    }


    public void editUserPass(String pass, String userid) {
        userRepository.editUserPass(pass, userid);
    }


    public void editUserAdress(String address, String userid) {
        userRepository.editUserAdress(address, userid);
    }


    public void editUserEmail(String email, String userid) {
        userRepository.editUserEmail(email, userid);
    }


    public void editUserTelephone(String telephone, String userid) {
        userRepository.editUserTelephone(telephone, userid);
    }


    public void editUserSex(String sex, String userid) {
        userRepository.editUserSex(sex, userid);
    }

    public void editImage(byte[] image, String userid) {
        userRepository.editImage(image, userid);
    }

    public void editUserFullname(String fullName, String userId) {
        userRepository.editUserFullname(fullName, userId);
    }

    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Integer countUser() {
        return Math.toIntExact(userRepository.count());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void setPassword(String password, String email) {
        userRepository.setPassword(password, email);
    }

    public List<month_user> getUserAndMonnth() {
        return userRepository.getUserAndMonnth();
    }

    User findByEmail(String mail) {
        return userRepository.findByEmail(mail);
    }
}
