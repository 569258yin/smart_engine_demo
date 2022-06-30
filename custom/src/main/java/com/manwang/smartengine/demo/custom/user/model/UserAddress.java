package com.manwang.smartengine.demo.custom.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {

    private String userId;
    private String address;
    private String phoneNum;
}
