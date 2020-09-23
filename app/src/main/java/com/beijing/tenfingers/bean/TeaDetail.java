package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 产品详情信息
 */
public class TeaDetail extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String isCollect;
    private String isThumb;
    private String count;
    private ArrayList<Batch> list = new ArrayList<>();
    private ArrayList<Like> likes = new ArrayList<>();
    private ArrayList<Like> sames = new ArrayList<>();
    private Detail detail;
    private String cartCount;
    private ArrayList<Comments> comments = new ArrayList<>();

    public TeaDetail(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                count=get(jsonObject,"count");
                isCollect = get(jsonObject, "isCollect");
                isThumb = get(jsonObject, "isThumb");
                cartCount=get(jsonObject,"cartCount");
                if (!jsonObject.isNull("detail")
                        && !isNull(jsonObject.getString("detail"))) {
                    JSONObject temp = jsonObject.getJSONObject("detail");
                    detail = new Detail(temp);
                }
                if (!jsonObject.isNull("batch")
                        && !isNull(jsonObject.getString("batch"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("batch");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++){
                        list.add(new Batch(jsonList.getJSONObject(i)));
                    }

                }
                if (!jsonObject.isNull("like")
                        && !isNull(jsonObject.getString("like"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("like");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        likes.add(new Like(jsonList.getJSONObject(i)));
                }
                if (!jsonObject.isNull("comments")
                        && !isNull(jsonObject.getString("comments"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("comments");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        comments.add(new Comments(jsonList.getJSONObject(i)));
                }
                if (!jsonObject.isNull("same")
                        && !isNull(jsonObject.getString("same"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("same");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        sames.add(new Like(jsonList.getJSONObject(i)));
                }


                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getCartCount() {
        return cartCount;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public void setIsThumb(String isThumb) {
        this.isThumb = isThumb;
    }

    public ArrayList<Like> getSames() {
        return sames;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public String getIsThumb() {
        return isThumb;
    }

    public ArrayList<Batch> getList() {
        return list;
    }

    public ArrayList<Like> getLikes() {
        return likes;
    }

    public Detail getDetail() {
        return detail;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public class Detail {
        private String tea_period;
        private String tea_date;
        private String tea_title;
        private String tea_price;
        private String tea_score;
        private String video_type;
        private String tea_detail;
        private String detail_url;
        private String tea_reason;
        private String id;
        private String tea_collect_count;
        private String tea_comment_count;
        private String tea_thumb_count;
        private String tea_vidio_link;
        private String tea_play_count;
        private String tea_img_link;
        private String tea_short_name;
        private String ingredients_name;
        private String place_name;
        private String tea_make;
        private String tea_format;
        private String storage_name;
        private String tea_quality;
        private String producted_at;
        private String tea_type_id;
        private String tea_sell_count;
        private String tea_no_same;
        private String can;
        private String activity_price;
        private String type_name;
        private String tea_taster_reason;
        private String tea_valuator_reason;
        private String tea_alltype; //0：普通商品，1：礼盒
        private String tea_q_rate; //好评率
        private ArrayList<String> tea_detail_array = new ArrayList<>();

        public String getType_name() {
            return type_name;
        }

        public String getTea_sell_count() {
            return tea_sell_count;
        }

        public void setTea_collect_count(String tea_collect_count) {
            this.tea_collect_count = tea_collect_count;
        }

        public void setTea_comment_count(String tea_comment_count) {
            this.tea_comment_count = tea_comment_count;
        }

        public void setTea_thumb_count(String tea_thumb_count) {
            this.tea_thumb_count = tea_thumb_count;
        }

        public String getTea_alltype() {
            return tea_alltype;
        }

        public String getTea_detail_url() {
            return detail_url;
        }

        public String getTea_period() {
            return tea_period;
        }

        public String getTea_date() {
            return tea_date;
        }

        public String getTea_title() {
            return tea_title;
        }

        public String getTea_price() {
            return tea_price;
        }

        public String getTea_score() {
            return tea_score;
        }

        public String getVideo_type() {
            return video_type;
        }

        public String getTea_detail() {
            return tea_detail;
        }

        public String getTea_reason() {
            return tea_reason;
        }

        public String getId() {
            return id;
        }

        public String getTea_collect_count() {
            return tea_collect_count;
        }

        public String getTea_comment_count() {
            return tea_comment_count;
        }

        public String getTea_thumb_count() {
            return tea_thumb_count;
        }

        public String getTea_vidio_link() {
            return tea_vidio_link;
        }

        public String getTea_play_count() {
            return tea_play_count;
        }

        public String getTea_img_link() {
            return tea_img_link;
        }

        public String getTea_short_name() {
            return tea_short_name;
        }

        public String getIngredients_name() {
            return ingredients_name;
        }

        public String getPlace_name() {
            return place_name;
        }

        public String getTea_make() {
            return tea_make;
        }

        public String getTea_format() {
            return tea_format;
        }

        public String getStorage_name() {
            return storage_name;
        }

        public String getTea_quality() {
            return tea_quality;
        }

        public String getProducted_at() {
            return producted_at;
        }

        public String getTea_type_id() {
            return tea_type_id;
        }

        public Valuator getValuator() {
            return valuator;
        }

        public Valuator getTaster() {
            return taster;
        }

        public Valuator getPlatation() {
            return platation;
        }

        public Valuator getFactory() {
            return factory;
        }

        public Valuator getArea() {
            return area;
        }

        public String getTea_q_rate() {
            return tea_q_rate;
        }

        public String getTea_no_same() {
            return tea_no_same;
        }

        public String getCan() {
            return can;
        }

        public String getActivity_price() {
            return activity_price;
        }

        public ArrayList<ImageAd> getImageLists() {
            return imageLists;
        }

        public String getTea_taster_reason() {
            return tea_taster_reason;
        }

        public String getTea_valuator_reason() {
            return tea_valuator_reason;
        }

        public ArrayList<String> getTea_detail_array() {
            return tea_detail_array;
        }

        private Valuator valuator;
        private Valuator taster;
        private Valuator platation;//
        private Valuator area;
        private Valuator factory;

        private ArrayList<ImageAd> imageLists=new ArrayList<>();
        public Detail(JSONObject jsonObject) throws DataParseException {
            if (jsonObject != null) {
                try {
                    tea_period = get(jsonObject, "tea_period");
                    tea_date = get(jsonObject, "tea_date");
                    tea_title = get(jsonObject, "tea_title");
                    tea_price = get(jsonObject, "tea_price");
                    tea_score = get(jsonObject, "tea_score");
                    video_type = get(jsonObject, "video_type");
                    tea_detail = get(jsonObject, "tea_detail");
                    tea_reason = get(jsonObject, "tea_reason");
                    id = get(jsonObject, "id");
                    tea_collect_count = get(jsonObject, "tea_collect_count");
                    tea_comment_count = get(jsonObject, "tea_comment_count");
                    tea_thumb_count = get(jsonObject, "tea_thumb_count");
                    tea_vidio_link = get(jsonObject, "tea_vidio_link");
                    tea_play_count = get(jsonObject, "tea_play_count");
                    tea_img_link = get(jsonObject, "tea_img_link");
                    tea_short_name = get(jsonObject, "tea_short_name");
                    ingredients_name = get(jsonObject, "ingredients_name");
                    place_name = get(jsonObject, "place_name");
                    tea_make = get(jsonObject, "tea_make");
                    tea_format = get(jsonObject, "tea_format");
                    storage_name = get(jsonObject, "storage_name");
                    tea_quality = get(jsonObject, "tea_quality");
                    producted_at = get(jsonObject, "producted_at");
                    tea_type_id = get(jsonObject, "tea_type_id");
                    detail_url=get(jsonObject,"detail_url");
                    tea_sell_count=get(jsonObject,"tea_sell_count");
                    tea_no_same=get(jsonObject,"tea_no_same");
                    can=get(jsonObject,"can");
                    type_name=get(jsonObject,"type_name");
                    activity_price=get(jsonObject,"activity_price");
                    tea_taster_reason=get(jsonObject,"tea_taster_reason");
                    tea_valuator_reason=get(jsonObject,"tea_valuator_reason");

                    tea_alltype = get(jsonObject, "tea_alltype");
                    tea_q_rate = get(jsonObject, "tea_q_rate");

                    if (!jsonObject.isNull("valuator")
                            && !isNull(jsonObject.getString("valuator"))) {
                        JSONObject temp = jsonObject.getJSONObject("valuator");
                        valuator=new Valuator(temp);
                    }
                    if (!jsonObject.isNull("taster")
                            && !isNull(jsonObject.getString("taster"))) {
                        JSONObject temp_taster = jsonObject.getJSONObject("taster");
                        taster=new Valuator(temp_taster);
                    }
                    if (!jsonObject.isNull("platation")
                            && !isNull(jsonObject.getString("platation"))) {
                        JSONObject temp_platation = jsonObject.getJSONObject("platation");
                        platation=new Valuator(temp_platation);
                    }
                    if (!jsonObject.isNull("factory")
                            && !isNull(jsonObject.getString("factory"))) {
                        JSONObject temp_factory = jsonObject.getJSONObject("factory");
                        factory=new Valuator(temp_factory);
                    }

                    if (!jsonObject.isNull("area")
                            && !isNull(jsonObject.getString("area"))) {
                        JSONObject temp_area = jsonObject.getJSONObject("area");
                        area=new Valuator(temp_area);
                    }
                    if (!jsonObject.isNull("images")
                            && !isNull(jsonObject.getString("images"))) {
                        JSONArray jsonList = jsonObject.getJSONArray("images");
                        int size = jsonList.length();
                        for (int i = 0; i < size; i++)
                            imageLists.add(new ImageAd(jsonList.getJSONObject(i)));
                    }

                    if (!jsonObject.isNull("tea_detail_array")
                            && !isNull(jsonObject.getString("tea_detail_array"))) {
                        JSONArray jsonList = jsonObject.getJSONArray("tea_detail_array");
                        int size = jsonList.length();
                        for (int i = 0; i < size; i++)
                            tea_detail_array.add(jsonList.getString(i));
                    }


                    log_i(toString());
                } catch (JSONException e) {
                    throw new DataParseException(e);
                }
            }


        }
    }




    public class Batch {
        private String id;
        private String tea_vidio_link;
        private String tea_img_link;

        public Batch(String id, String tea_img_link) {
            this.id = id;
            this.tea_img_link = tea_img_link;
        }

        public Batch(JSONObject jsonObject) throws DataParseException {
            if (jsonObject != null) {
                try {
                    id = get(jsonObject, "id");
                    tea_vidio_link = get(jsonObject, "tea_vidio_link");
                    tea_img_link = get(jsonObject, "tea_img_link");
                    log_i(toString());
                } catch (JSONException e) {
                    throw new DataParseException(e);
                }
            }
        }

        public String getTea_img_link() {
            return tea_img_link;
        }

        public String getId() {
            return id;
        }

        public String getTea_vidio_link() {
            return tea_vidio_link;
        }
    }

    public class Like {
        private String id;
        private String tea_period;
        private String tea_title;
        private String tea_date;
        private String tea_vidio_link;
        private String tea_img_link;
        private String flag;//0 同一款茶 1 相似茶
        private String tea_price;
        private String tea_sell_count;
        private String tea_video_time;
        private ArrayList<ImageAd> images=new ArrayList<>();
        private String can;
        private String activity_price;
        private String tea_alltype;
        private String tea_format;

        public Like(JSONObject jsonObject) throws DataParseException {
            if (jsonObject != null) {
                try {
                    id = get(jsonObject, "id");
                    tea_period = get(jsonObject, "tea_period");
                    tea_title = get(jsonObject, "tea_title");
                    tea_date = get(jsonObject, "tea_date");
                    tea_vidio_link = get(jsonObject, "tea_vidio_link");
                    tea_video_time = get(jsonObject, "tea_video_time");
                    tea_img_link = get(jsonObject, "tea_img_link");
                    tea_price=get(jsonObject,"tea_price");
                    tea_sell_count=get(jsonObject,"tea_sell_count");
                    can = get(jsonObject, "can");
                    activity_price = get(jsonObject, "activity_price");
                    tea_alltype = get(jsonObject, "tea_alltype");
                    tea_format = get(jsonObject, "tea_format");

                    if (!jsonObject.isNull("images")
                            && !isNull(jsonObject.getString("images"))) {
                        JSONArray jsonList = jsonObject.getJSONArray("images");
                        int size = jsonList.length();
                        for (int i = 0; i < size; i++)
                            images.add(new ImageAd(jsonList.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                    throw new DataParseException(e);
                }
            }
        }

        public ArrayList<ImageAd> getImages() {
            return images;
        }

        public String getTea_video_time() {
            return tea_video_time;
        }

        public String getTea_sell_count() {
            return tea_sell_count;
        }

        public String getTea_price() {
            return tea_price;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getTea_alltype() {
            return tea_alltype;
        }

        public String getTea_format() {
            return tea_format;
        }

        public String getId() {
            return id;
        }

        public String getTea_period() {
            return tea_period;
        }

        public String getCan() {
            return can;
        }

        public String getActivity_price() {
            return activity_price;
        }

        public String getTea_title() {
            return tea_title;
        }

        public String getTea_date() {
            return tea_date;
        }

        public String getTea_vidio_link() {
            return tea_vidio_link;
        }

        public String getTea_img_link() {
            return tea_img_link;
        }
    }

}
