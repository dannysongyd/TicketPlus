package entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

public class Item {
    private String itemId;
    private String name;
    private double rating;
    private String address;
    private Set<String> categories;
    private String imageUrl;
    private String url;
    private double distance;

    private Item(ItemBuilder builder) {
        this.itemId = builder.itemId;
        this.name = builder.name;
        this.rating = builder.rating;
        this.address = builder.address;
        this.categories = builder.categories;
        this.imageUrl = builder.imageUrl;
        this.url = builder.url;
        this.distance = builder.distance;

    }


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    public static class ItemBuilder {

        private String itemId;
        private String name;
        private double rating;
        private String address;
        private Set<String> categories;
        private String imageUrl;
        private String url;
        private double distance;

        public ItemBuilder setItemId(String itemId) {
            this.itemId = itemId;
            return this;
        }

        public ItemBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder setRating(double rating) {
            this.rating = rating;
            return this;
        }

        public ItemBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public ItemBuilder setCategories(Set<String> categories) {
            this.categories = categories;
            return this;
        }

        public ItemBuilder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ItemBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public ItemBuilder setDistance(double distance) {
            this.distance = distance;
            return this;
        }

        public Item build() {
            return new Item(this);
        }

    }


    // Transform an Item object to JSONObject so the front-end can read
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();

        try {
//            Populate the JSONObject with the fields in the Item object
            obj.put("item_id", itemId);
            obj.put("name", name);
            obj.put("rating", rating);
            obj.put("address", address);
            obj.put("categories", categories);
            obj.put("image_url", imageUrl);
            obj.put("url", url);
            obj.put("distance", distance);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }
}


