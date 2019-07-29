package rpc;


import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/*
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
    private static final long serialVersionUID = 1L;

    //    public SearchItem() {
    //        super();
    //        // TODO Auto-generated constructor stub
    //    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("application/json");

        double lat = Double.parseDouble(request.getParameter("lat"));
        double lon = Double.parseDouble(request.getParameter("lon"));
        // Term can be empty or null.
        String term = request.getParameter("term");

        DBConnection connection = DBConnectionFactory.getConnection();

        try {
            List<Item> items = connection.searchItems(lat, lon, term);

            JSONArray array = new JSONArray();
            for (Item item : items) {
                array.put(item.toJSONObject());
            }
            RpcHelper.writeJsonArray(response, array);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
