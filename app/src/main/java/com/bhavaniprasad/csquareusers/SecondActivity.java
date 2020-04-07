package com.bhavaniprasad.csquareusers;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.bhavaniprasad.csquareusers.Fragments.ProfileFragment;
import com.bhavaniprasad.csquareusers.Fragments.UsersFragment;
import com.bhavaniprasad.csquareusers.Interface.OnUsersclicklistener;

public class SecondActivity extends FragmentActivity implements OnUsersclicklistener{
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        repository=new Repository(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new UsersFragment()).commit();
    }

    @Override
    public void onclickuser(int adapterposition) {
        Bundle bundle=new Bundle();
        bundle.putInt("position",adapterposition);
        ProfileFragment fragment=new ProfileFragment(this,bundle);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new ProfileFragment(this, bundle)).addToBackStack(null).commit();
    }



    private long backPressedTime =0;
    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.maincontainer);
        if(f instanceof ProfileFragment){
            repository.deleteeverything();
            super.onBackPressed();
        }
        else{
            long t = System.currentTimeMillis();
            if (t - backPressedTime > 2000) {
                backPressedTime = t;
                Toast.makeText(this, "Press again to exit",Toast.LENGTH_SHORT).show();
            } else {
                System.exit(1);
            }
        }

    }

}
