package ru.netology.situations;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class CreditRequestSituations {
    String id;
    String bank_id;
    String created;
    String status;
}