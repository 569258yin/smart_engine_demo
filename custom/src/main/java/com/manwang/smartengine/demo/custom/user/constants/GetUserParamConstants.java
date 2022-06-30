package com.manwang.smartengine.demo.custom.user.constants;

import java.util.Map;

public final class GetUserParamConstants {

    private GetUserParamConstants() {
    }

    public static final String USER_ID_PARAM = "userId";
    public static final String USER_PARAM = "user";
    public static final String USER_ADDRESS_PARAM = "userAddress";
    public static final String USER_PLATE_PARAM = "userPlate";
    public static final String USER_CAR_PARAM = "userCar";
    public static final String USER_RESULT_PARAM = "userResult";


    public static String getUserId(Map<String, Object> map) {
        return (String) map.get(USER_ID_PARAM);
    }
}
