package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.User;
import spring.Repository.UserRepository;

import java.util.Base64;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void removeUserByUserId(String idUser) {
        userRepository.removeUserByUserId(idUser);
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
    public void editUserName(String nameUser, String userid) {
        userRepository.editUserName(nameUser,userid);
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
        userRepository.editUserSex(sex,userid);
    }
    public void editImage(Base64 image, String userid) {
        userRepository.editImage(image,userid);
    }

    public Page<User> getAllUser(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public Integer countUser(){
        return Math.toIntExact(userRepository.count());
    }
}
