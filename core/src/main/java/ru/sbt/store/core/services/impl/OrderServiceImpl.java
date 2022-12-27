package ru.sbt.store.core.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.store.core.entities.*;
import ru.sbt.store.core.entities.Currency;
import ru.sbt.store.core.exceptions.EntityAlreadyExistsException;
import ru.sbt.store.core.exceptions.EntityNotExistsException;
import ru.sbt.store.core.exceptions.FailedUpdateException;
import ru.sbt.store.core.repositories.*;
import ru.sbt.store.core.services.OrderService;


import ru.sbt.store.core.utils.OrderCostCalculationService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;
    private final OrderItemRepository orderItemRepository;
    private final CurrencyRepository currencyRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;


    @Override
    public Order createOrder(Client client, Currency currency, Set<OrderItem> items) {
        Order order = new Order();
        order.setClient(client);
        order.setCurrency(currency);
        order.setItems(items);
        client.addOrder(order);
        items.forEach(item -> item.setOrder(order));
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order createOrder(Order order) {
        Payment payment = order.getPayment();
        Shipment shipment = order.getShipment();
        Currency orderCurrency = getOrderCurrency(order);

        Client orderClient = clientRepository.findById(order.getClientId())
                .orElseThrow(() -> new EntityNotExistsException(order.getClientId(), Client.class.getName()));

        order.setCurrency(orderCurrency);
        order.setClient(orderClient);
        if (payment != null) {
            payment.setCurrency(currencyRepository.findById(payment.getCurrencyId()).orElse(null));
        }
        Set<OrderItem> items = new HashSet<>(order.getItems());

        order.setPayment(null);
        order.setShipment(null);
        order.setItems(new HashSet<>());
        Order savedOrder = orderRepository.save(order);

        List<Long> itemProductIds = items.stream().map(OrderItem::getProductId).collect(Collectors.toList());
        Map<Long, Product> productMap = new HashMap<>();
        productRepository.findAllById(itemProductIds).forEach(p -> productMap.put(p.getId(), p));
        items.forEach(item -> {
            Product product = productMap.get(item.getProductId());
            item.setProduct(product);
            item.setOrder(savedOrder);
        });
        List<OrderItem> orderItems = orderItemRepository.saveAll(items);
        savedOrder.setItems(new HashSet<>(orderItems));

        if(payment != null) {
            payment.setOrder(savedOrder);
            Payment savedPayment = paymentRepository.save(payment);
            savedOrder.setPayment(savedPayment);
        }
        if (shipment != null) {
            shipment.setOrder(savedOrder);
            Shipment savedShipment = shipmentRepository.save(shipment);
            savedOrder.setShipment(savedShipment);
        }
        return savedOrder;
    }

    private Currency getOrderCurrency(Order order) {
        if (order.getCurrencyId() != null) {
            return currencyRepository.findById(order.getCurrencyId())
                    .orElseThrow(() -> new EntityNotExistsException(order.getCurrencyId(), Currency.class.getName()));
        } else {
            String name = order.getCurrency().getName();
            return currencyRepository.findByName(name)
                    .orElseThrow(() -> new EntityNotExistsException("Failed to find currency by name " + name));
        }
    }

    @Override
    public void deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
    }


    @Override
    public OrderItem addProductToOrder(Order order, Product product, int quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        order.addItem(orderItem);
        return orderItemRepository.save(orderItem);
    }


    @Override
    public Shipment addShipmentToOrder(Order order, String address) {
        Shipment shipment = new Shipment();
        shipment.setAddress(address);
        shipment.setOrder(order);
        order.setShipment(shipment);
        return shipmentRepository.save(shipment);
    }

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findOrderById(id).orElse(null);
    }

    @Override
    public List<Order> getOrdersByClientId(Long clientId) {
        return orderRepository.findOrdersByClientId(clientId);
    }

    @Override
    public Payment addPaymentToOrder(Order order, String cardPan, Currency currency) {
        Payment payment = new Payment();
        payment.setCardPan(cardPan);
        payment.setOrder(order);
        payment.setCurrency(currency);
        payment.setValue(OrderCostCalculationService.calculateOrderCost(order, currency));
        order.setPayment(payment);
        return paymentRepository.save(payment);
    }


    @Override
    public OrderItem addOrderItem(OrderItem orderItem) {
        Product product = productRepository.findById(orderItem.getProductId())
                .orElseThrow(() -> new EntityNotExistsException(orderItem.getProductId(), Product.class.getName()));
        Order order = orderRepository.findById(orderItem.getProductId())
                .orElseThrow(() -> new EntityNotExistsException(orderItem.getOrderId(), Order.class.getName()));
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        if (orderItem.getId() == null) {
            throw new FailedUpdateException(OrderItem.class.getName());
        }
        OrderItem storedItem = orderItemRepository.findById(orderItem.getId())
                .orElseThrow(() -> new EntityNotExistsException(orderItem.getId(), OrderItem.class.getName()));

        if (storedItem != null) {
            storedItem.setQuantity(orderItem.getQuantity());
            storedItem = orderItemRepository.save(storedItem);
        }
        return storedItem;
    }


    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }


    @Override
    public Shipment addShipment(Shipment shipment) {
        Order order = orderRepository.findById(shipment.getOrderId())
                .orElseThrow(() -> new EntityNotExistsException(shipment.getOrderId(), Order.class.getName()));
        shipment.setOrder(order);
        return shipmentRepository.save(shipment);
    }

    @Override
    public Shipment updateShipment(Shipment shipment) {
        if (shipment.getId() == null) {
            throw new FailedUpdateException(Shipment.class.getName());
        }
        Shipment storedShipment = shipmentRepository.findById(shipment.getId())
                .orElseThrow(() -> new EntityNotExistsException(shipment.getId(), Shipment.class.getName()));
        if (storedShipment != null) {
            storedShipment.setAddress(shipment.getAddress());
            storedShipment = shipmentRepository.save(storedShipment);
        }
        return storedShipment;
    }

    @Override
    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }

    @Override
    public Payment addPayment(Payment payment) {
        Order order = orderRepository.findOrderById(payment.getOrderId())
                .orElseThrow(() -> new EntityNotExistsException(payment.getOrderId(), Order.class.getName()));

        Currency currency = currencyRepository.findById(payment.getCurrencyId())
                .orElseThrow(() -> new EntityNotExistsException(payment.getCurrencyId(), Currency.class.getName()));

        if (order.getPayment() != null) {
            throw new EntityAlreadyExistsException(Payment.class.getName(), Order.class.getName(), order.getId());
        }
        payment.setOrder(order);
        payment.setCurrency(currency);
        payment.setValue(OrderCostCalculationService.calculateOrderCost(order, currency));
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        if (payment.getId() == null) {
            throw new FailedUpdateException(Payment.class.getName());
        }
        Payment storedPayment = paymentRepository.findPaymentById(payment.getId())
                .orElseThrow(() -> new EntityNotExistsException(payment.getId(), Payment.class.getName()));
        Currency newCurrency = currencyRepository.findById(payment.getCurrencyId())
                .orElseThrow(() -> new EntityNotExistsException(payment.getCurrencyId(), Currency.class.getName()));

        Currency persistedCurrency = storedPayment.getCurrency();

        storedPayment.setCardPan(payment.getCardPan());

        if (!persistedCurrency.getId().equals(newCurrency.getId())) {
            BigDecimal newValue = storedPayment.getValue()
                    .divide(persistedCurrency.getMultiplier(), RoundingMode.HALF_UP)
                    .multiply(newCurrency.getMultiplier());
            storedPayment.setValue(newValue);
            storedPayment.setCurrency(newCurrency);
            persistedCurrency = newCurrency;
        }

        storedPayment = paymentRepository.save(storedPayment);
        storedPayment.setCurrency(persistedCurrency);
        log.info("payment = {}, currency = {}", storedPayment, storedPayment.getCurrency());
        return storedPayment;
    }

    @Override
    public Payment findPaymentById(Long id) {
        return paymentRepository.findPaymentById(id).orElse(null);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

}
