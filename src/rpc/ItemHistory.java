package rpc;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet("/history")
public class ItemHistory extends HttpServlet {
    //    Set favorite item
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection connection = DBConnectionFactory.getConnection();
        try {
            JSONObject input = RpcHelper.readJSONObject(request);
            String userId = input.getString("user_id");
            JSONArray array = input.getJSONArray("favorite");
            List<String> itemIds = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                itemIds.add(array.getString(i));
            }
            connection.setFavoriteItems(userId, itemIds);
            RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DBConnection connection = DBConnectionFactory.getConnection();
        String userId = request.getParameter("user_id");
        JSONArray array = new JSONArray();
        try {
            Set<Item> items = connection.getFavoriteItems(userId);
            System.out.println(items.size());
            for (Item item : items
            ) {
                JSONObject obj = item.toJSONObject();
                obj.append("favorite", true);
                array.put(obj);

            }
            RpcHelper.writeJsonArray(response, array);

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }


    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection connection = DBConnectionFactory.getConnection();
        try {
            JSONObject input = RpcHelper.readJSONObject(request);
            String userId = input.getString("user_id");
            JSONArray array = input.getJSONArray("favorite");
            List<String> itemIds = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                itemIds.add(array.getString(i));
            }
            connection.unsetFavoriteItems(userId, itemIds);
            RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
