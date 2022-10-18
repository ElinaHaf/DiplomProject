package ru.netology.situations;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {
    String id;
    String created;
    String creditId;
    String payment_id;
}