package fine.koacaiia.examplecode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
Camera camera;
SurfaceView surfaceView;
SurfaceHolder holder;
String[] permission_list={Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(permission_list,0);

        surfaceView=findViewById(R.id.surfaceView);
        holder=surfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(this);

        surfaceView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               fragmentIntent();
                return false;
            }
        });

        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureProcess();
            }
        });
        FragmentManager fragmentManager;
        fragmentManager=getSupportFragmentManager();
        FragmentRe fragmentRe=new FragmentRe(this);
        FragmentTransaction action=fragmentManager.beginTransaction();
        action.add(R.id.layoutFrame,fragmentRe).commit();
        }

    private void captureProcess() {
        Camera.PictureCallback callback;
        callback=new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                OutputStream fos=null;
                Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                ContentResolver contentResolver=getContentResolver();
                ContentValues contentValues=new ContentValues();
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
                contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
                contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Fine/입,출고/Ori");
                Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                try {
                    fos=contentResolver.openOutputStream(imageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);

                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                camera.startPreview();
            }
        };
        camera.takePicture(null,null,callback);
    }

    public void fragmentIntent(){
//        Intent intent=new Intent(this,FragmentTest.class);
//        startActivity(intent);
        FragmentManager fragmentManager;
        fragmentManager=getSupportFragmentManager();
        FragmentRe fragmentRe=new FragmentRe(this);
        FragmentTransaction action=fragmentManager.beginTransaction();
        action.add(R.id.layoutFrame,fragmentRe).commit();
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        preViewProcess();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        for(int result:grantResults){
            if(result==PackageManager.PERMISSION_DENIED){
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        preViewProcess();
    }

    private void preViewProcess() {
        camera=Camera.open();
        camera.setDisplayOrientation(90);
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }
}
