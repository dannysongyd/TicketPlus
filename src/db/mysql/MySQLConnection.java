package db.mysql;

import db.DBConnection;
import entity.Item;
import external.TicketMasterClient;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Singleton pattern
@SuppressWarnings("ALL")
public class MySQLConnection implements DBConnection {

    private Connection conn;


    public MySQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            conn = DriverManager.getConnection(MySQLDBUtil.URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setFavoriteItems(String userId, List<String> itemIds) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return;
        }

        try {
            String sql = "INSERT IGNORE INTO history(user_id, item_id) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            for (String itemid : itemIds
            ) {
                ps.setString(2, itemid);
                ps.execute();

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void unsetFavoriteItems(String userId, List<String> itemIds) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return;
        }

        try {
            String sql = "DELETE FROM history WHERE user_id = ? AND item_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            for (String itemid : itemIds
            ) {
                ps.setString(2, itemid);
                ps.execute();

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Set<String> getFavoriteItemIds(String userId) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return new HashSet<>();
        }
        Set<String> favoriteItems = new HashSet<>();


        String sql = "SELECT item_id FROM history WHERE user_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String itemId = rs.getString("item_id");
                favoriteItems.add(itemId);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favoriteItems;
    }

    @Override
    public Set<Item> getFavoriteItems(String userId) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return new HashSet<>();
        }
        Set<String> itemIds = getFavoriteItemIds(userId);
        Set<Item> favoriteItems = new HashSet<>();
        String sql = "SELECT * FROM items WHERE item_id = ?";


        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (String itemId : itemIds
            ) {
                ps.setString(1, itemId);
                ResultSet rs = ps.executeQuery();
                Item.ItemBuilder itemBuilder = new Item.ItemBuilder();

                while (rs.next()) {
                    itemBuilder.setItemId(rs.getString("item_id"));
                    itemBuilder.setName(rs.getString("name"));
                    itemBuilder.setAddress(rs.getString("address"));
                    itemBuilder.setImageUrl(rs.getString("image_url"));
                    itemBuilder.setUrl(rs.getString("url"));
                    itemBuilder.setCategories(getCategories(itemId));
                    itemBuilder.setDistance(rs.getDouble("distance"));
                    itemBuilder.setRating(rs.getDouble("rating"));

                    favoriteItems.add(itemBuilder.build());
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favoriteItems;
    }

    @Override
    public Set<String> getCategories(String itemId) {

        if (conn == null) {
            System.err.println("DB connection failed");
            return new HashSet<>();
        }
        Set<String> categories = new HashSet<>();
        String sql = "SELECT category FROM categories WHERE item_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, itemId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String category = rs.getString("category");
                categories.add(category);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return categories;
    }

    @Override
    public List<Item> searchItems(double lat, double lon, String term) {
        TicketMasterClient ticketMasterClient = new TicketMasterClient();

        List<Item> items = ticketMasterClient.search(lat, lon, term);

        for (Item item : items) {
            saveItem(item);
        }

        return items;
    }

    @Override
    public void saveItem(Item item) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return;
        }

        try {
            String sql = "INSERT IGNORE INTO items VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, item.getItemId());
            ps.setString(2, item.getName());
            ps.setDouble(3, item.getRating());
            ps.setString(4, item.getAddress());
            ps.setString(5, item.getImageUrl());
            ps.setString(6, item.getUrl());
            ps.setDouble(7, item.getDistance());

            ps.execute();

            sql = "INSERT IGNORE INTO categories VALUES(?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, item.getItemId());
            for (String category : item.getCategories()) {
                ps.setString(2, category);
                ps.execute();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFullname(String userId) {
        return null;
    }

    @Override
    public boolean verifyLogin(String userId, String password) {
        return false;
    }
}
