package com.manwang.smartengine.demo.custom.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCar {
    private String userId;
    private String brand;
    private String model;
    private String displacement;
    private String vehicle;
}
