package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 退款订单详情
 */
public class Refund extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String id;
    private String total_fee;
    private String refund_fee;
    private String order_refund_no;
    private String refund_reason;
    private String created_at;
    private String refund_time;
    private String order_discount;
    private String status;
    private String reject_status;

    private ArrayList<OrderItem> items = new ArrayList<>();
    private ArrayList<ItemStatus> item_status = new ArrayList<>();

    public Refund(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                if (!jsonObject.isNull("items")
                        && !isNull(jsonObject.getString("items"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("items");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        items.add(new OrderItem(jsonList.getJSONObject(i)));
                }

                if (!jsonObject.isNull("refund")
                        && !isNull(jsonObject.getString("refund"))) {
                    JSONObject info = jsonObject.getJSONObject("refund");

                    id = get(info, "id");
                    total_fee = get(info, "total_fee");
                    refund_reason = get(info, "refund_reason");
                    refund_fee = get(info, "refund_fee");
                    order_refund_no = get(info, "order_refund_no");
                    created_at = get(info, "created_at");
                    order_discount = get(info, "order_discount");
                    status = get(info, "status");
                    reject_status = get(info, "reject_status");

                    if (!jsonObject.isNull("item_status")
                            && !isNull(jsonObject.getString("item_status"))) {
                        JSONArray jsonList = jsonObject.getJSONArray("item_status");
                        int size = jsonList.length();
                        for (int i = 0; i < size; i++)
                            item_status.add(new ItemStatus(jsonList.getJSONObject(i)));
                    }
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "Refund{" +
                "id='" + id + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", refund_fee='" + refund_fee + '\'' +
                ", order_refund_no='" + order_refund_no + '\'' +
                ", refund_reason='" + refund_reason + '\'' +
                ", created_at='" + created_at + '\'' +
                ", refund_time='" + refund_time + '\'' +
                ", order_discount='" + order_discount + '\'' +
                ", status='" + status + '\'' +
                ", reject_status='" + reject_status + '\'' +
                ", items=" + items +
                '}';
    }

    public ArrayList<ItemStatus> getItem_status() {
        return item_status;
    }

    public String getOrder_discount() {
        return order_discount;
    }


    public String getStatus() {
        return status;
    }

    public String getReject_status() {
        return reject_status;
    }

    public String getId() {
        return id;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public String getRefund_fee() {
        return refund_fee;
    }

    public String getOrder_refund_no() {
        return order_refund_no;
    }

    public String getRefund_reason() {
        return refund_reason;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getRefund_time() {
        return refund_time;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public class OrderItem implements Serializable {

        private static final long serialVersionUID = 1265819772736012294L;
        private String tea_title;
        private String tea_img_link;
        private String order_count;
        private String order_price;
        private String order_real_total;
        private String order_status;
        private String reject_status;
        private String status_text;
        private String id;
        private String order_tea_id;
        private String order_comment_count;

        private String reserve_task_id; //	预购任务id
        private String reserve_type; //预购类型 1是0否
        private String reserve_status; //

        private String r_price;
        private String r_s_price;
        private String r_e_price;
        private String is_confirm;
        private String r_deposit;
        private String r_remain_price;
        private String r_discount;
        private String deposit_total; //定金总额
        private String remain_total; //尾款总额
        private String reserve_total; //商品的总价
        private String reserve_discount; //折扣总额

        private boolean flag = false;

        public OrderItem() {
        }

        public OrderItem(JSONObject jsonObject) throws DataParseException {
            if (jsonObject != null) {
                try {
                    id = get(jsonObject, "id");
                    tea_title = get(jsonObject, "tea_title");
                    tea_img_link = get(jsonObject, "tea_img_link");
                    order_count = get(jsonObject, "order_count");
                    order_price = get(jsonObject, "order_price");
                    order_real_total = get(jsonObject, "order_real_total");
                    order_status = get(jsonObject, "order_status");
                    reject_status = get(jsonObject, "reject_status");
                    status_text = get(jsonObject, "status_text");
                    order_tea_id = get(jsonObject, "order_tea_id");
                    order_comment_count = get(jsonObject, "order_comment_count");
                    reserve_task_id = get(jsonObject, "reserve_task_id");
                    reserve_type = get(jsonObject, "reserve_type");
                    reserve_status = get(jsonObject, "reserve_status");

                    r_price = get(jsonObject, "r_price");
                    r_s_price = get(jsonObject, "r_s_price");
                    r_e_price = get(jsonObject, "r_e_price");
                    is_confirm = get(jsonObject, "is_confirm");
                    r_deposit = get(jsonObject, "r_deposit");
                    r_remain_price = get(jsonObject, "r_remain_price");
                    r_discount = get(jsonObject, "r_discount");

                    deposit_total = get(jsonObject, "deposit_total");
                    remain_total = get(jsonObject, "remain_total");
                    reserve_total = get(jsonObject, "reserve_total");
                    reserve_discount = get(jsonObject, "reserve_discount");

                    log_i(toString());
                } catch (JSONException e) {
                    throw new DataParseException(e);
                }
            }
        }

        public String getReserve_task_id() {
            return reserve_task_id;
        }

        public String getReserve_type() {
            return reserve_type;
        }

        public String getDeposit_total() {
            return deposit_total;
        }

        public String getRemain_total() {
            return remain_total;
        }

        public String getReserve_total() {
            return reserve_total;
        }

        public String getReserve_discount() {
            return reserve_discount;
        }

        public String getReserve_status() {
            return reserve_status;
        }

        public String getOrder_tea_id() {
            return order_tea_id;
        }

        public String getR_discount() {
            return r_discount;
        }

        public String getOrder_comment_count() {
            return order_comment_count;
        }

        public String getId() {
            return id;
        }

        public String getR_price() {
            return r_price;
        }

        public String getR_s_price() {
            return r_s_price;
        }

        public String getR_e_price() {
            return r_e_price;
        }

        public String getIs_confirm() {
            return is_confirm;
        }

        public String getR_deposit() {
            return r_deposit;
        }

        public String getR_remain_price() {
            return r_remain_price;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public String getTea_title() {
            return tea_title;
        }

        public String getTea_img_link() {
            return tea_img_link;
        }

        public String getOrder_count() {
            return order_count;
        }

        public String getOrder_price() {
            return order_price;
        }

        public String getOrder_real_total() {
            return order_real_total;
        }

        public String getOrder_status() {
            return order_status;
        }

        public String getReject_status() {
            return reject_status;
        }

        public String getStatus_text() {
            return status_text;
        }
    }

    public class ItemStatus implements Serializable {
        private String id;
        private String order_status;
        private String reserve_step;
        private String reject_status;
        private String order_real_total;
        private String pay_time;
        private String pay_type;

        public ItemStatus() {

        }

        public ItemStatus(JSONObject jsonObject) throws DataParseException {
            if (jsonObject != null) {
                try {
                    id = get(jsonObject, "id");
                    order_status = get(jsonObject, "order_status");
                    reserve_step = get(jsonObject, "reserve_step");
                    reject_status = get(jsonObject, "reject_status");
                    order_real_total = get(jsonObject, "order_real_total");
                    pay_time = get(jsonObject, "pay_time");
                    pay_type = get(jsonObject, "pay_type");

                    log_i(toString());
                } catch (JSONException e) {
                    throw new DataParseException(e);
                }
            }
        }

        public String getId() {
            return id;
        }

        public String getOrder_status() {
            return order_status;
        }

        public String getReserve_step() {
            return reserve_step;
        }

        public String getReject_status() {
            return reject_status;
        }

        public String getOrder_real_total() {
            return order_real_total;
        }

        public String getPay_time() {
            return pay_time;
        }

        public String getPay_type() {
            return pay_type;
        }
    }
}
