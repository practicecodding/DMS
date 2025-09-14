package com.hamidul.dms.Service.Network;

public interface ApiServices {
    String BASE_URL = "https://smhamidul.top/";
    String getUser = BASE_URL + "SMH_Durbeen/DMS/view_user_db_wise.php";
    String getOrderedOutlet = BASE_URL + "SMH_Durbeen/Admin/view_ordered_user_outlet_wise.php";
    String submitDelivery = BASE_URL + "SMH_Durbeen/Admin/insert_delivery.php";
    String cancelOrder = BASE_URL + "SMH_Durbeen/Admin/cancel_order.php";
}
