package ru.netology.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class CreditRequest {
    String id;
    String bank_id;
    String created;
    String status;
}