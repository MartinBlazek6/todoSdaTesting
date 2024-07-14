package com.example.todo.model.DTO;

import lombok.Data;

// Vytvořím DATA TRANSFER OBJECT. Ten slouží jako schránka pro přenos informace (konkrétních DAT) o Todo Entity.
// Jsou zde jen důležitá data pro danou Entitu, které jsou důležitá pro různé výpočty!!!!!!!!!!!!!!!!
@Data
public class TodoDto {
    private String title;
    private String date;    //Pozor: Zde date jako String na rozdíl od Todo modelu
}
