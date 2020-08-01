package com.flagcamp.donationcollector.ui.user.posts;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;

import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.FragmentPostUserBinding;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;
import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.SignInRepository;
import com.flagcamp.donationcollector.signin.AppUser;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PostUserFragment extends Fragment {

    private FragmentPostUserBinding binding;
    private PostUserViewModel viewModel;
    ImageView addIcon;

    public PostUserFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentPostUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addIcon = view.findViewById(R.id.post_user_add_icon);

        PostUserAdapter postUserAdapter = new PostUserAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.postRecyclerView.setLayoutManager(linearLayoutManager);
        binding.postRecyclerView.setAdapter(postUserAdapter);

        postUserAdapter.setOnItemClickListener(new PostUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                PostDetailsUserFragment.setItem(item);
                Navigation.findNavController(view).navigate(R.id.action_title_postuser_to_post_details);
            }
        });

        PostRepository repository = new PostRepository(getContext());
        SignInRepository signInRepository = new SignInRepository();
        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository, signInRepository)).get(PostUserViewModel.class);

        AppUser[] appUsers = new AppUser[1];

        viewModel.getAppUser().observe(getViewLifecycleOwner(), response -> {
            if(response != null) {
                Log.d("PostUserFragment", "AppUser is: " + response.toString());
                appUsers[0] = response.get(0);

                viewModel.getUserPosts(appUsers[0].getUid()).observe(getViewLifecycleOwner(), postResponse -> {
                    if(postResponse != null) {
                        Log.d("PostUserFragment", postResponse.toString());
                        List<Item> notCollectedItems = new ArrayList<>();
                        for(Item item: postResponse) {
                            if(!item.status.toLowerCase().equals("collected")) {
                                notCollectedItems.add(item);
                            }
                        }
                        postUserAdapter.setItems(notCollectedItems);
                    } else {
                        Log.d("PostUserFragment", "Null postResponse");
                    }
                });

                Log.d("PostUserFragment", "appUsers[0] is: " + appUsers[0].toString());
            } else {
                Log.d("PostUserFragment", "AppUser is: null");
            }
        });

        /**
         * Currently the posterId is hardcoded to 0, later it should be obtained from the AppUser object
         */
//        Log.d("PostUserFragment", "appUsers[0] is: " + appUsers[0]);
//
//        if(appUsers[0] != null) {
//            Log.d("PostUserFragment", "appUser is: " + appUsers[0].toString());
//            viewModel.getUserPosts(appUsers[0].getUid()).observe(getViewLifecycleOwner(), postResponse -> {
//                if(postResponse != null) {
//                    Log.d("PostUserFragment", postResponse.toString());
//                    postUserAdapter.setItems(postResponse);
//                } else {
//                    Log.d("PostUserFragment", "Null postResponse");
//                }
//            });
//        }


        binding.postUserAddIcon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission
                       .WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                   ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
               }else{
                   //执行启动相册的方法
//                   Navigation.findNavController(v).navigate(R.id.action_nav_post_user_to_nav_albums);
                   openAlbum();
               }

           }

        });
    }


    //获取权限的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if (grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED) openAlbum();
            else Toast.makeText(getContext(),"你拒绝了",Toast.LENGTH_SHORT).show();
        }
    }

    //启动相册的方法
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2){
            //判断安卓版本
            if (resultCode == RESULT_OK&&data!=null){
                if (Build.VERSION.SDK_INT>=19)
                    handImage(data);
                else handImageLow(data);
            }
        }
    }

    //安卓版本大于4.4的处理方法
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handImage(Intent data) {
        String path =null;
        Uri uri = data.getData();
        //根据不同的uri进行不同的解析
        if (DocumentsContract.isDocumentUri(getContext(),uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID+"="+id;
                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                path = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            path = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            path = uri.getPath();
        }
        //展示图片
//        displayImage(path);


        try{
            PostUserFragmentDirections.ActionTitlePostUserToPostsPreview actionTitlePostUserToPostsPreview =
                    PostUserFragmentDirections.actionTitlePostUserToPostsPreview();
            actionTitlePostUserToPostsPreview.setImagePath(path);
            NavHostFragment.findNavController(PostUserFragment.this).navigate(actionTitlePostUserToPostsPreview);
        } catch (IllegalArgumentException e) {
            Log.d("POostUserFragment", e.toString());
        }

    }


    //安卓小于4.4的处理方法
    private void handImageLow(Intent data){
        Uri uri = data.getData();
        String path = getImagePath(uri,null);
//        displayImage(path);
    }

    //content类型的uri获取图片路径的方法
    private String getImagePath(Uri uri,String selection) {
        String path = null;
        Cursor cursor = getContext().getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}
