package com.manwang.smartengine.demo.custom.user.facade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String userId;
    private String userName;
    private String plate;
    private String address;
    private String phoneNum;
    private String model;
    private String vehicle;
}
