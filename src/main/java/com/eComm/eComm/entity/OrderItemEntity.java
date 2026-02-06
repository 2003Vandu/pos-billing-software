package com.eComm.eComm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name="tbl_orderItems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemEntity
{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String itemId;
    private String name;
    private Double price;
    private Integer quantity;

    @PrePersist
    public void prePersist() {
        if (this.itemId == null) {
            this.itemId = "ITEM-" + UUID.randomUUID().toString();
        }
    }


}
