package co.develhope.controllerprotection.order.services;

import co.develhope.controllerprotection.order.dto.OrderDTO;
import co.develhope.controllerprotection.order.entities.Order;
import co.develhope.controllerprotection.order.repositories.OrderRepository;
import co.develhope.controllerprotection.user.dto.UserDTO;
import co.develhope.controllerprotection.user.entities.User;
import co.develhope.controllerprotection.user.services.UserService;
import co.develhope.controllerprotection.user.utils.Roles;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public OrderDTO createOrder(@NotNull Order order) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        order.setId(null);
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(user);
        order = orderRepository.saveAndFlush(order);

        OrderDTO orderDTO = this.convertToDTO(order);
        orderDTO.setCreatedBy(userService.convertToDTO(order.getCreatedBy()));
        orderDTO.setUpdatedBy(null);
        return orderDTO;
    }

    public List<OrderDTO> getAllOrders(@NotNull Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> Objects.equals(role.getName(), Roles.ADMIN));
        if(isAdmin) {
            List<Order> orders = orderRepository.findAll();
            return getOrdersDTO(orders);
        }
        List<Order> orders = orderRepository.findByCreatedBy(user);
        return getOrdersDTO(orders);
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        OrderDTO orderDTO = this.convertToDTO(order);
        orderDTO.setCreatedBy(userService.convertToDTO(order.getCreatedBy()));
        UserDTO updatedByDTO = order.getUpdatedBy() != null ? userService.convertToDTO(order.getUpdatedBy()) : null;
        orderDTO.setUpdatedBy(updatedByDTO);
        orderDTO.setUpdateAt(order.getUpdatedAt());
        return orderDTO;
    }

    public OrderDTO updateOrderById(@NotNull Order orderEdit, Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if(orderEdit.getDescription() != null) {
            order.setDescription(orderEdit.getDescription());
        }
        if(orderEdit.getAddress() != null) {
            order.setAddress(orderEdit.getAddress());
        }
        if(orderEdit.getHouseNumber() != null) {
            order.setHouseNumber(orderEdit.getHouseNumber());
        }
        if(orderEdit.getCity() != null) {
            order.setCity(orderEdit.getCity());
        }
        if(orderEdit.getZipCode() != null) {
            order.setZipCode(orderEdit.getZipCode());
        }
        if(orderEdit.getState() != null) {
            order.setState(orderEdit.getState());
        }
        if(orderEdit.getPrice() != null) {
            order.setPrice(orderEdit.getPrice());
        }

        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        order = orderRepository.saveAndFlush(order);

        OrderDTO orderDTO = this.convertToDTO(order);
        orderDTO.setCreatedBy(userService.convertToDTO(order.getCreatedBy()));
        orderDTO.setUpdatedBy(userService.convertToDTO(order.getUpdatedBy()));
        orderDTO.setUpdateAt(order.getUpdatedAt());
        return orderDTO;
    }

    public boolean canEditOrder(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Order> orderOpt = orderRepository.findById(id);
        if(!orderOpt.isPresent()) {
            return false;
        }
        return Objects.equals(orderOpt.get().getCreatedBy().getId(), user.getId());
    }

    public void deleteOrderById(Long id) {
        if(!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Order not found");
        }
        orderRepository.deleteById(id);
    }

    private List<OrderDTO> getOrdersDTO(List<Order> orders) {
        List<OrderDTO> ordersDTO = new ArrayList<>();
        for(Order order : orders) {
            OrderDTO orderDTO = this.convertToDTO(order);
            orderDTO.setCreatedBy(userService.convertToDTO(order.getCreatedBy()));
            UserDTO updatedByDTO = order.getUpdatedBy() != null ? userService.convertToDTO(order.getUpdatedBy()) : null;
            orderDTO.setUpdatedBy(updatedByDTO);
            orderDTO.setUpdateAt(order.getUpdatedAt());
            ordersDTO.add(orderDTO);
        }
        return ordersDTO;
    }

    public Order convertToEntity(@NotNull OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }

    public OrderDTO convertToDTO(@NotNull Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

}
