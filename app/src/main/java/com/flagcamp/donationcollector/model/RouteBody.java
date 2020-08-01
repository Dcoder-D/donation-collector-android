package com.flagcamp.donationcollector.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RouteBody {
    List<MyLatLng> route;
    List<Item> items;

    public List<MyLatLng> getRoute() {
        return route;
    }

    public List<Item> getItems() {
        return items;
    }

    public RouteBody(JSONObject routeObject) throws JSONException {
        int steps = Integer.valueOf(routeObject.getString("steps"));
        route = new ArrayList<>();
        items = new ArrayList<>();
        JSONObject routeJson = routeObject.getJSONObject("route");
        JSONArray itemsArray = routeObject.getJSONArray("items");
        for(int i = 0; i < steps; i++) {
            String index = String.valueOf(i);
            JSONObject latLngJson = routeJson.getJSONObject(index);
            route.add(new MyLatLng(latLngJson.getString("lat"), latLngJson.getString("lng")));
        }

        for(int i = 0; i < itemsArray.length(); i++) {
            JSONObject itemObject = itemsArray.getJSONObject(i);
            Item item = new Item();
            item.location = itemObject.getString("address");
            item.itemName = itemObject.getString("itemName");
            item.lat = Double.valueOf(itemObject.getString("lat"));
            item.lon = Double.valueOf(itemObject.getString("lon"));
            item.id = itemObject.getString("itemId");
            items.add(item);
        }

//        Log.d("RouteBody", "routeJson: " + routeJson.getString("0"));
//        Log.d("RouteBody", "itemsArray: " + itemsArray.getJSONObject(0).getString("itemId"));
    }

    @Override
    public String toString() {
        return "RouteBody{" +
                "route=" + route +
                ", items=" + items +
                '}';
    }
}
