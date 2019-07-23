package rpc;


import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RpcHelper {


//    Writes a JSONObject to http response
    public static void writeJsonObject(HttpServletResponse response, JSONObject obj) throws IOException{
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        out.print(obj);
        out.close();

    }

//    Writes a JSONArray to http response
    public static void writeJsonArray(HttpServletResponse response, JSONArray array) throws IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        out.print(array);
        out.close();
    }


}
