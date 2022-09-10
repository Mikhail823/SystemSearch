package com.service.responseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class Statistics {

    Total total;
    Detailed[] detailed;

}
