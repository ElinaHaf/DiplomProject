package ru.netology.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Payment {
    String id;
    String amount;
    String created;
    String status;
    String transaction_id;
}
