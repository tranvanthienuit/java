package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Model.Orderss;
import spring.Entity.Model.User;
import spring.Repository.OrderssRepository;

import java.util.Date;
import java.util.List;

@Service
public class OrderssSevice {
    @Autowired
    OrderssRepository orderssRepository;

    public Orderss findOrderssByOrderssId(String idOrderss) {
        return orderssRepository.findOrderssByOrderssId(idOrderss);
    }

    public void removeOrderssByOrderssId(String idOrderss) {
        orderssRepository.removeOrderssByOrderssId(idOrderss);
    }


    public void saveOrderss(Orderss orderss) {
        orderssRepository.save(orderss);
    }

    public Orderss findOrderssByOrderssDateAndUserId(Date date, User user) {
        return orderssRepository.findOrderssByOrderssDateAndUserId(date, user);
    }

    public Page<Orderss> getAllOrderss(Pageable pageable) {
        return orderssRepository.findAll(pageable);
    }

    public List<Orderss> findOrdersssByUser(String username) {
        return orderssRepository.findOrdersssByUser(username);
    }

    public List<Orderss> getAllOrderss() {
        return orderssRepository.findAll();
    }
}
