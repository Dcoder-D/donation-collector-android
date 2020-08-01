package com.flagcamp.donationcollector;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.model.MyLatLng;
import com.flagcamp.donationcollector.model.RouteBody;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolyActivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener {

    private static RouteBody routeBody;

    public static void setRouteBody(RouteBody routeBody) {
        PolyActivity.routeBody = routeBody;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(this, MainActivityNGO.class);
        startActivity(intent);
        finish();
    }

    // [START EXCLUDE]
    // [START maps_poly_activity_get_map_async]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    // [END maps_poly_activity_get_map_async]

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this tutorial, we add polylines and polygons to represent routes and areas on the map.
     */
    // [END EXCLUDE]
    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Add polylines to the map.
        // Polylines are useful to show a route or some other connection between points.
        // [START maps_poly_activity_add_polyline_set_tag]
        // [START maps_poly_activity_add_polyline]
        List<LatLng> routePoints = new ArrayList<>();
//        List<LatLng> itemPoints = new ArrayList<>();
        if(routeBody != null) {
            Log.d("PolyActivity", "routeBody not null: " + routeBody);
            for(MyLatLng myLatLng: routeBody.getRoute()) {
                routePoints.add(new LatLng(myLatLng.getLat(), myLatLng.getLng()));
            }
            for(Item item: routeBody.getItems()) {
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(item.lat, item.lon))
                        .title(item.description));

            }
        }

//        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
//                .clickable(true)
//                .add(
//                        new LatLng(42.0431331, -87.68053960000002),
//                        new LatLng(42.027255, -87.67816020000001),
//                        new LatLng(42.0271897, -87.6706389),
//                        new LatLng(41.9982019, -87.6605947),
//                        new LatLng(41.987253, -87.66023709999999),
//                        new LatLng(41.9732378, -87.6597826),
//                        new LatLng(41.9732433, -87.65952469999999),
//                        new LatLng(41.9732433, -87.65952469999999),
//                        new LatLng(41.97327910000001, -87.65773270000001),
//                        new LatLng(41.9732813, -87.6563141),
//                        new LatLng(41.976367, -87.6564308),
//                        new LatLng(41.9764305, -87.651777),
//                        new LatLng(41.8932207, -87.61441769999999),
//                        new LatLng(41.892256, -87.6144053),
//                        new LatLng(41.8890746, -87.6142081),
//                        new LatLng(41.8876469, -87.615183),
//                        new LatLng(41.8879321, -87.6205717),
//                        new LatLng(41.8895663, -87.62030539999999),
//                        new LatLng(41.8895066, -87.6188561)
//                ));

        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .addAll(
                        routePoints
                ));


        // [END maps_poly_activity_add_polyline]
        // [START_EXCLUDE silent]
        // Store a data object with the polyline, used here to indicate an arbitrary type.
        polyline1.setTag("A");
        // [END maps_poly_activity_add_polyline_set_tag]

        // Style the polyline.
        stylePolyline(polyline1);

//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(42.0431331, -87.68053960000002))
//                .title("item A"));
//
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(41.9731009, -87.6594002))
//                .title("item B"));
//
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(41.8897136, -87.6188114))
//                .title("item C"));


        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.

//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.976367, -87.6564308), 10));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(routePoints.get(0), 10));

        // Set listeners for click events.
        googleMap.setOnPolylineClickListener(this);
    }
    // [END maps_poly_activity_on_map_ready]

    // [START maps_poly_activity_style_polyline]
    private static final int COLOR_BLACK_ARGB = 0xff0000ff;
    private static final int POLYLINE_STROKE_WIDTH_PX = 12;

    private void stylePolyline(Polyline polyline) {
        String type = "";
        // Get the data object stored with the polyline.
        if (polyline.getTag() != null) {
            type = polyline.getTag().toString();
        }

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "A":
                // Use a custom bitmap as the cap at the start of the line.
                polyline.setStartCap(
                        new CustomCap(
                                BitmapDescriptorFactory.fromResource(R.drawable.ic_arrow_down), 10));
                break;
            case "B":
                // Use a round cap at the start of the line.
                polyline.setStartCap(new RoundCap());
                break;
        }

        polyline.setEndCap(new RoundCap());
        polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline.setColor(COLOR_BLACK_ARGB);
        polyline.setJointType(JointType.ROUND);
    }
    // [END maps_poly_activity_style_polyline]

    // [START maps_poly_activity_on_polyline_click]
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dot.
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);

    @Override
    public void onPolylineClick(Polyline polyline) {
        // Flip from solid stroke to dotted stroke pattern.
        if ((polyline.getPattern() == null) || (!polyline.getPattern().contains(DOT))) {
            polyline.setPattern(PATTERN_POLYLINE_DOTTED);
        } else {
            // The default pattern is a solid stroke.
            polyline.setPattern(null);
        }

        Toast.makeText(this, "Route type " + polyline.getTag().toString(),
                Toast.LENGTH_SHORT).show();
    }
    // [END maps_poly_activity_on_polyline_click]

    // [START maps_poly_activity_style_polygon]
    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_PURPLE_ARGB = 0xff81C784;
    private static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_BLUE_ARGB = 0xffF9A825;

    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dash.
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    private static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);

    /**
     * Styles the polygon, based on type.
     * @param polygon The polygon object that needs styling.
     */
}
