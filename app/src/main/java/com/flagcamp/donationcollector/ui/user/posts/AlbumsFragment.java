package com.flagcamp.donationcollector.ui.user.posts;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.FragmentAlbumsBinding;
import com.flagcamp.donationcollector.databinding.FragmentPostUserBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import static android.app.Activity.RESULT_OK;

public class AlbumsFragment extends Fragment {
    private FragmentAlbumsBinding binding;

    public AlbumsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAlbumsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        binding.imagePlaceholder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //动态申请权限
//                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission
//                        .WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//                }else{
//                    //执行启动相册的方法
//                    openAlbum();
//                }
//            }
//        });

        openAlbum();

//        binding.albumsBackButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_nav_albums_to_nav_post_user);
//            }
//        });
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

        // Pass the image path to post preview page
        AlbumsFragmentDirections.ActionTitleAlbumsToPostsPreview actionTitleAlbumsToPostsPreview
                = AlbumsFragmentDirections.actionTitleAlbumsToPostsPreview();
        actionTitleAlbumsToPostsPreview.setImagePath(path);
        NavHostFragment.findNavController(this).navigate(actionTitleAlbumsToPostsPreview);
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

    //根据路径展示图片的方法
//    private void displayImage(String imagePath){
//        if (imagePath != null){
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            binding.imagePlaceholder.setImageBitmap(bitmap);
//        }
//    }
}
