package com.flagcamp.donationcollector.model;

import android.util.Log;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PostUtils {
    public static RequestBody createRouteBody(String ngoName, String startLocation, List<Item> scheduledItems) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"start\": {\"NGOName\": \"");
        sb.append(ngoName);
        sb.append("\", \"NGOAddress\": \"");
        sb.append(startLocation);
        sb.append("\"}, \"waypoints\": " + itemsToWaypoints(scheduledItems) + "}");

        Log.d("PostUtils", "requestBody: " + sb.toString());

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), sb.toString());

        return body;
    }

    private static String itemsToWaypoints(List<Item> scheduledItems) {

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        if(scheduledItems != null && scheduledItems.size() > 0) {
            for(Item item: scheduledItems) {
                sb.append("{\"itemId\": \"" + item.id + "\", \"itemName\": \"" + item.itemName + "\", ");
                sb.append("\"address\": \"" + item.location + "\"},");
            }
            // Delete the extra ',' in the end
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(']');
        return sb.toString();
    }
}
