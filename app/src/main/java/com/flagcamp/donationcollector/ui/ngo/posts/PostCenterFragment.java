package com.flagcamp.donationcollector.ui.ngo.posts;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.flagcamp.donationcollector.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flagcamp.donationcollector.databinding.FragmentPostCenterBinding;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PostCenterFragment extends Fragment {

    private PostCenterViewModel viewModel;
    private static FragmentPostCenterBinding binding;
    ImageView mapIcon;
    private static String location;
    private static String distance;
    private TextView message;
    private LocationManager locationManager;


    public PostCenterFragment() {

    }

    public static void setLocationDistance(String location, String distance) {
        PostCenterFragment.location = location;
        PostCenterFragment.distance = distance;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == 1) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//
//
////                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
////                return;
//                } else {
//                    updateLocation();
//                }
//            } else {
//                Toast.makeText(getContext(),"你拒绝了",Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("PostCenterFragment", "requestcode: " + requestCode + "grantResults: " + grantResults.length);

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//        return inflater.inflate(R.layout.fragment_)
        binding = FragmentPostCenterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void updateLocation() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapIcon = view.findViewById(R.id.post_center_map_icon);

        Context context = getContext();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        message = binding.text;

        PostRepository repository = new PostRepository(getContext());

//        repository.getAllLocation().observe(getViewLifecycleOwner(), response -> {
//            location = response.get(0).location;
//            if(location == null) {
//                location = "235 Grand St, Jersey City, NJ 07302";
//            }
//            distance = response.get(0).distance;
//            if(distance == null) {
//                distance = "100";
//            }
//        });

//        location = PostCenterFragmentArgs.fromBundle(getArguments()).getLocation();
        if (location == null) {
//            location = "235 Grand St, Jersey City, NJ 07302";
            Log.d("PostCenter", "location is null");

//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//
//
//                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
////                return;
//            } else {
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
//                    @Override
//                    public void onLocationChanged(Location location) {
//                        String lat = String.valueOf(location.getLatitude());
//                        String lon = String.valueOf(location.getLongitude());
//                        Log.d("PostCenterFragment", "location from GPS service: " + lat + ", " + lon);
//                        Geocoder geocoder;
//                        List<Address> addresses;
//
//                        geocoder = new Geocoder(getContext(), Locale.getDefault());
//
//                        try {
//                            addresses = geocoder.getFromLocation(Double.valueOf(lat), Double.valueOf(lon), 1);
//                            if(!addresses.isEmpty()) {
//                                Address returnedAddress = addresses.get(0);
//                                StringBuilder sb = new StringBuilder();
//                                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
//                                    sb.append(returnedAddress.getAddressLine(i)).append(" ");
//                                }
//                                Log.d("PostCenter", "Address: " + sb.toString());
//                            } else {
//                                Log.d("PostCenter", "addresses is empty");
//                            }
//                        } catch (IOException e) {
//                            Log.d("PostCenter", e.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                    }
//
//                    @Override
//                    public void onProviderEnabled(String provider) {
//
//                    }
//
//                    @Override
//                    public void onProviderDisabled(String provider) {
//
//                    }
//
//                });
//            }

//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//
//
////                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
////                return;
//            } else {
//                updateLocation();
//            }

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    updateLocation();
//                }
//            });


        } else {
            Log.d("PostCenter", "location: " + location);
        }
//        distance = PostCenterFragmentArgs.fromBundle(getArguments()).getDistance();
        if(distance == null) {
            distance = "100";
        }

        mapIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NavHostFragment.findNavController(PostCenterFragment.this).navigate(R.id.action_title_postcenter_to_location_selector);

              PostCenterFragmentDirections.ActionTitlePostcenterToLocationSelector actionTitlePostcenterToLocationSelector =
              PostCenterFragmentDirections.actionTitlePostcenterToLocationSelector("PostCenterFragment");

              NavHostFragment.findNavController(PostCenterFragment.this).navigate(actionTitlePostcenterToLocationSelector);
            }
        });

        PostCenterAdapter postCenterAdapter = new PostCenterAdapter(this, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.postRecyclerView.setLayoutManager(linearLayoutManager);
        binding.postRecyclerView.setAdapter(postCenterAdapter);





        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository)).get(PostCenterViewModel.class);

        if(location != null) {
            viewModel.getPostsByLocation(location, distance).observe(getViewLifecycleOwner(), postResponse -> {
                if(postResponse != null) {
                    Log.d("PostCenterFragment", postResponse.toString());
                    postCenterAdapter.setItems(postResponse);
                } else {
                    Log.d("PostCenterFragment", "Null postResponse");
                }
            });
        } else {
            message.setText("Post Center\nPlease");
        }


//        placeHolder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PostCenterFragmentDirections.ActionTitlePostcenterToPostDetails actionTitlePostcenterToPostDetails =
//                        PostCenterFragmentDirections.actionTitlePostcenterToPostDetails();
//                actionTitlePostcenterToPostDetails.setDummy("test");
//                NavHostFragment.findNavController(PostCenterFragment.this).navigate(actionTitlePostcenterToPostDetails);
//            }
//        });


    }

//    private static class PostClickListener implements View.OnClickListener {
//        private final Context context;
//
//        private PostClickListener(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        public void onClick(View v) {
//            int selectedItemPosition = binding.postRecyclerView.getChildAdapterPosition(v);
//            RecyclerView.ViewHolder viewHolder = binding.postRecyclerView.findViewHolderForAdapterPosition(selectedItemPosition);
//            PostCenterFragmentDirections.ActionTitlePostcenterToPostDetails actionTitlePostcenterToPostDetails =
//                    PostCenterFragmentDirections.actionTitlePostcenterToPostDetails();
//
//        }
//    }
}
