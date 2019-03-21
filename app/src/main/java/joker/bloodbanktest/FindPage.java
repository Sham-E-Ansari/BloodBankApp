package joker.bloodbanktest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;



public class FindPage extends Activity {


    private ListView dList;
    private DonorListAdapter adapter;
    private List<DonorDetails> DonorList;
    private DatabaseHelper mDBHelper;

    EditText grpET, locET;
    Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_page);

        getActionBar().setTitle("Find Donor");

        dList = (ListView) findViewById(R.id.donorListTV);
        grpET = (EditText) findViewById(R.id.groupSearchET);
        locET= (EditText)findViewById(R.id.locationSearchET);
        search = (Button)findViewById(R.id.searchBTN);

        mDBHelper = new DatabaseHelper(this);


        DonorList = mDBHelper.getDonorList();
        //Init adapter
        adapter = new DonorListAdapter(this, DonorList);
        //Set adapter for listview
        dList.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bldGrp = grpET.getText().toString();
                String location = locET.getText().toString();
                if(bldGrp.isEmpty() || location.isEmpty())
                    Toast.makeText(getApplicationContext(),"Fill all field!!!",Toast.LENGTH_SHORT).show();

                else
                {
                    DonorList = mDBHelper.getSearchedDonorList(bldGrp,location);
                    adapter = new DonorListAdapter(getApplicationContext(), DonorList);
                    dList.setAdapter(adapter);
                }
            }
        });

        registerForContextMenu(dList);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.donorListTV) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        switch (item.getItemId()) {
            case R.id.call:
                String number = DonorList.get(info.position).getdMobile();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));

                if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
                startActivity(callIntent);
                return true;

            case R.id.sms:
                String number1 = DonorList.get(info.position).getdMobile();

                Intent i = new Intent(getApplicationContext(),SmsOptions.class);
                Bundle data = new Bundle();
                data.putCharSequence("DONOR_NUM",number1);
                i.putExtra("DATA",data);
                startActivity(i);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
