package com.Bank.BankCredit.Models.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SequenceCredit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sequence {
    @Id
    private String  id;
    private int sequence;
}