package rpc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/recommendation")
public class RecommendItem extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // allow access only if session exists
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(403);
            return;
        }

        // optional
        String userId = session.getAttribute("user_id").toString();


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // allow access only if session exists
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(403);
            return;
        }

        // optional
        String userId = session.getAttribute("user_id").toString();

        response.setContentType("application/json");
        PrintWriter wirter = response.getWriter();

        JSONArray array = new JSONArray();
        try {
            array.put(new JSONObject().put("name", "abcd").put("address", "San Francisco").put("time", "01/01/2018"));
            array.put(new JSONObject().put("name", "1234").put("address", "San Jose").put("time", "01/02/2018"));
            array.put(new JSONObject().put("name", "1234").put("address", "New York").put("time", "01/02/2019"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RpcHelper.writeJsonArray(response, array);

    }
}
