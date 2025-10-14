package com.hamidul.dms.Service.Network;

public interface ApiServices {
    String BASE_URL = "https://smhamidul.top/";
    String getUser = BASE_URL + "SMH_Durbeen/DMS/view_user_db_wise.php";
    String getOrderedOutlet = BASE_URL + "SMH_Durbeen/Admin/view_ordered_user_outlet_wise.php";
    String submitDelivery = BASE_URL + "SMH_Durbeen/DMS/insert_delivery.php";
    String cancelOrder = BASE_URL + "SMH_Durbeen/DMS/cancel_order.php";
    String getReport = BASE_URL + "SMH_Durbeen/DMS/view_report_day_wise.php";
    String getDailyOrderSummary = BASE_URL + "SMH_Durbeen/DMS/view_order_summary_db_wise.php";
    String getDeliveredUser = BASE_URL + "SMH_Durbeen/DMS/view_delivered_user_db_wise.php";
    String getDeliveredOutlet = BASE_URL + "SMH_Durbeen/DMS/view_delivered_outlet_user_wise.php";

}
