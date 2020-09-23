package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 点赞 足迹 收藏
 */
public class CommonList extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String count;
    private ArrayList<Children> children=new ArrayList<>();

    public CommonList(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                count=get(jsonObject,"total");
                if (!jsonObject.isNull("rows")
                        && !isNull(jsonObject.getString("rows"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("rows");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        children.add(new Children(jsonList.getJSONObject(i)));
                }
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }


    public String getCount() {
        return count;
    }

    public ArrayList<Children> getChildren() {
        return children;
    }

    public class Children implements Serializable {

        private static final long serialVersionUID = 1265819772736012294L;
        private String id;
        private String user_id;
        private String t_id;
        private String order_id;
        private String p_id;
        private String c_star;
        private String c_content;
        private String status;
        private String created_at;
        private String updated_at;
        private String u_username;
        private String u_image_link;
        private String o_no;
        private String p_name;
        private String images;
        private String reply;
        private ArrayList<ImageList> imageLists=new ArrayList<>();
        public Children(JSONObject jsonObject) throws DataParseException {
            if (jsonObject != null) {
                try {
                    id = get(jsonObject, "id");
                    user_id = get(jsonObject, "user_id");
                    t_id = get(jsonObject, "t_id");
                    order_id = get(jsonObject, "order_id");
                    p_id=get(jsonObject,"p_id");
                    c_star=get(jsonObject,"c_star");
                    c_content = get(jsonObject, "c_content");
                    status = get(jsonObject, "status");
                    created_at = get(jsonObject, "created_at");
                    updated_at = get(jsonObject, "updated_at");
                    u_username = get(jsonObject, "u_username");
                    u_image_link = get(jsonObject, "u_image_link");
                    o_no= get(jsonObject, "o_no");
                    p_name= get(jsonObject, "p_name");
                    images= get(jsonObject, "images");
                    reply=get(jsonObject,"reply");
                    if (!jsonObject.isNull("images")
                            && !isNull(jsonObject.getString("images"))) {
                        JSONArray jsonList = jsonObject.getJSONArray("images");
                        int size = jsonList.length();
                        for (int i = 0; i < size; i++)
                            imageLists.add(new ImageList(jsonList.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                    throw new DataParseException(e);
                }
            }
        }


        @Override
        public String toString() {
            return "Children{" +
                    "id='" + id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", t_id='" + t_id + '\'' +
                    ", order_id='" + order_id + '\'' +
                    ", p_id='" + p_id + '\'' +
                    ", c_star='" + c_star + '\'' +
                    ", c_content='" + c_content + '\'' +
                    ", status='" + status + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    ", u_username='" + u_username + '\'' +
                    ", u_image_link='" + u_image_link + '\'' +
                    ", o_no='" + o_no + '\'' +
                    ", p_name='" + p_name + '\'' +
                    ", images='" + images + '\'' +
                    ", reply='" + reply + '\'' +
                    ", imageLists=" + imageLists +
                    '}';
        }

        public String getReply() {
            return reply;
        }

        public String getO_no() {
            return o_no;
        }

        public String getP_name() {
            return p_name;
        }

        public String getImages() {
            return images;
        }

        public String getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getT_id() {
            return t_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public String getP_id() {
            return p_id;
        }

        public String getC_star() {
            return c_star;
        }

        public String getC_content() {
            return c_content;
        }

        public String getStatus() {
            return status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getU_username() {
            return u_username;
        }

        public String getU_image_link() {
            return u_image_link;
        }

        public ArrayList<ImageList> getImageLists() {
            return imageLists;
        }
    }
}
