package fine.koacaiia.examplecode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class FragmentTest extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentRe fragmentRe;
    FragmentOri fragmentOri;
    FragmentAll fragmentAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        fragmentManager=getSupportFragmentManager();
//        fragmentRe=new FragmentRe(this);
        FragmentTransaction action=fragmentManager.beginTransaction();
        action.add(R.id.framelayout,fragmentRe).commit();
    }
}