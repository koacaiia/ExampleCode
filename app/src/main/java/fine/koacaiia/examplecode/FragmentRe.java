package fine.koacaiia.examplecode;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class FragmentRe extends Fragment {
    RecyclerView recyclerView;
    ImageViewListAdapter adapter;
    ArrayList<String> list;
    TextView txtView;
    MainActivity activity;
    public FragmentRe(MainActivity mainActivity) {
        this.activity=mainActivity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_re,container,false);
        recyclerView=view.findViewById(R.id.fragmentaRe_recyclerView);
        txtView=view.findViewById(R.id.fragmentRe_txt);
        GridLayoutManager manager=new GridLayoutManager(activity,2);
        recyclerView.setLayoutManager(manager);
//        list=new ArrayList<>();
//        Uri uri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        String[] projection={MediaStore.MediaColumns.DATA};
//        Cursor cursor= activity.getContentResolver().query(uri,projection,null,null,MediaStore.MediaColumns.DATE_ADDED+" desc");
//        int columnsDataIndex=cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//        while(cursor.moveToNext()){
//            String cursorUri=cursor.getString(columnsDataIndex);
//            File file=new  File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/Fine/입,출고/Resize");
//            String strFile=String.valueOf(file);
//            if(cursorUri.startsWith(strFile)){
//                list.add(cursorUri);
//                Log.i("TestValue","List Size:::"+list.size());
//            }
//        }
//        cursor.close();
//        Log.i("TestValue","List Size:::::::"+list.size());
        adapter=new ImageViewListAdapter(getPictureLists());
        Log.i("TestValue","List size:::"+getPictureLists().size());
        recyclerView.setAdapter(adapter);

        return view;
    }
    public ArrayList<String> getPictureLists(){
        ArrayList<String> imageViewLists=new ArrayList<>();
        Uri uri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection={MediaStore.MediaColumns.DATA};
        Cursor cursor=activity.getContentResolver().query(uri,projection,null,null,MediaStore.MediaColumns.DATE_ADDED+" desc");
        Log.i("TestValue","ContentResolver Value:::"+activity.getContentResolver().toString());
        int columnsDataIndex=cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while(cursor.moveToNext()){
            String uriI=cursor.getString(columnsDataIndex);
            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/Fine/입,출고/Resize");
            String strFile=String.valueOf(file);
            if(uriI.startsWith(strFile)){
                imageViewLists.add(uriI);
            }
        }
        cursor.close();
        return imageViewLists;
    }

}