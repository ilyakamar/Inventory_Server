package com.ilyakamar.inventory_server.Common;

import com.ilyakamar.inventory_server.Model.Request;
import com.ilyakamar.inventory_server.Model.User;

public class Common {

    public static User currentUser;
    public static Request currentRequest;

    public static final int PICK_IMAGE_REQUEST = 71;


//    public static final String UPDATE = "Update";
//    public static final String DELETE = "Delete";
    // try
    public static final String UPDATE = "עדכון";
    public static final String DELETE = "מחיקה";


    public static String convertCodeToStatus(String code)
    {
        if (code.equals("0")){
//            return "Placed";
            return "ההזמנה התקבלה";
        }
        else   if (code.equals("1")){
//            return "On my way";
            return "ההזמנה בטיפול";
        }
//        else return "Shipped";
        else return "המשלוח בדרך";
    }// end convertCodeToStatus





}// END
