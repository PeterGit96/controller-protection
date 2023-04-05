package co.develhope.controllerprotection.order.repositories;

import co.develhope.controllerprotection.order.entities.Order;
import co.develhope.controllerprotection.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCreatedBy(User user);

}
