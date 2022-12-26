package ru.sbt.store.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment extends BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payments_id_gen")
    @SequenceGenerator(name = "payments_id_gen", sequenceName = "payments_id_seq", allocationSize = 1)
    private Long id;

    private BigDecimal value;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Column(name = "card_pan")
    private String cardPan;

    public Long getCurrencyId() {
        return currency == null ? null : currency.getId();
    }

    public Long getOrderId() {
        return order == null ? null : order.getId();
    }

    public String getEncryptedCardPan() {
        String encoding = "************";
        if (cardPan != null) {
            return encoding + cardPan.substring(12);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", orderId=" + getOrderId() +
                ", currencyId=" + getCurrencyId() +
                ", cardPan='" + cardPan + '\'' +
                ", value=" + value +
                '}';
    }
}
