package ru.sbt.store.core.utils;

import lombok.NonNull;
import ru.sbt.store.core.entities.Currency;
import ru.sbt.store.core.entities.Order;
import ru.sbt.store.core.entities.OrderItem;
import ru.sbt.store.core.entities.Product;

import java.math.BigDecimal;
import java.util.Set;

public interface OrderCostCalculationService {
    static BigDecimal calculateOrderCost(@NonNull Order order, @NonNull Currency currency) {
        BigDecimal cost = BigDecimal.valueOf(0.);
        Set<OrderItem> items = order.getItems();
        for (OrderItem item : items) {
            Product product = item.getProduct();
            if (product != null) {
                BigDecimal itemCost = product.getPriceCU()
                        .multiply(BigDecimal.valueOf(item.getQuantity()))
                        .multiply(currency.getMultiplier());
                cost = cost.add(itemCost);
            }
        }
        return cost;
    }
}
