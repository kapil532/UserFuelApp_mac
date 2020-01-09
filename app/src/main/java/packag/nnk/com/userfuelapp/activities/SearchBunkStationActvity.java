package packag.nnk.com.userfuelapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.LocationRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.base.EasyWayLocation;
import packag.nnk.com.userfuelapp.base.Listener;
import packag.nnk.com.userfuelapp.bunk_search.CustomAdapter;
import packag.nnk.com.userfuelapp.interfaces.getBunkDetails;
import packag.nnk.com.userfuelapp.model.Bunk;
import packag.nnk.com.userfuelapp.model.BunkDetails;

public class SearchBunkStationActvity extends BaseActivity implements Listener
{



    EditText autoCompleteTextView;
    CustomAdapter _adapter;
    DatabaseReference mFirebaseDatabaseReference;
    private RecyclerView mResultList;
    public static getBunkDetails bunkDetails;


    private static final int ERROR_DIALOG_CODE = 101;
    private static final int PER_REQ_CODE = 102;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private String myPer[] = {Manifest.permission.ACCESS_FINE_LOCATION};

LocationRequest locationRequest;


    @BindView(R.id.back)
    ImageView back;

    EasyWayLocation easyWayLocation;

    private Double lati = 0.0, longi = 0.0;
    ArrayList<Bunk> bunkArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bunk_find);
         ButterKnife.bind(this);

        mFirebaseDatabaseReference   = FirebaseDatabase.getInstance().getReference("location");

       locationRequest = new LocationRequest();
       locationRequest.setInterval(10000);
        initAutoCompleteTextView();

        bunkArrayList=(ArrayList<Bunk> ) getIntent().getExtras().getSerializable("bunk_list");


        if(bunkArrayList != null)
        {
            if(bunkArrayList.size()>0)
            {
                adapter(null,bunkArrayList);
            }

        }


        openLocation();
        if (permissionIsGranted()) {
            doLocationWork();
        } else {
            // Permission not granted, ask for it
            getLocationPermission();
        }

    }


    private void initAutoCompleteTextView()
    {

        autoCompleteTextView = findViewById(R.id.search_field);
        setFont(autoCompleteTextView);
        autoCompleteTextView.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                getSearch(autoCompleteTextView.getText().toString());
            }
        });



        mResultList = (RecyclerView) findViewById(R.id.bunk_list);

        mResultList.addOnItemTouchListener(new RecyclerTouchListener(this,
                mResultList, new ClickListener() {
        @Override
        public void onClick(View view, final int position)
        {
            //Values are passing to activity & to fragment as well

            if(notes != null)
            {
                BunkDetails _bunkDetails=notes.get(position);
                bunkDetails.getBunkDe( null,_bunkDetails);
                finish();
            }
            else
            {
                Bunk _bunkDetails_=bunkArrayList.get(position);
                bunkDetails.getBunkDe( _bunkDetails_,null);
                finish();
            }



        }

        @Override
        public void onLongClick(View view, int position) {

        }
    }));


        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));


    }


   @OnClick(R.id.back)
    void clickBack()
    {
        finish();
    }


    void getSearch(String searchText)
    {

//        mFirebaseDatabaseReference.addValueEventListener(valueEventListener);

        Log.e("MESSAGE","URL"+searchText);
        if(searchText.length()>0)
        {
            mResultList.setVisibility(View.VISIBLE);
            Query query = mFirebaseDatabaseReference.orderByChild("bunk_name").startAt(searchText).endAt(searchText+ "\uf8ff");
            Log.e("MESSAGE","URL");
            query.addValueEventListener(valueEventListener);

        }
        else
        {
            mResultList.setVisibility(View.GONE);
        }


    }

    ArrayList<BunkDetails> notes;
    ValueEventListener valueEventListener = new ValueEventListener()
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {

             notes = new ArrayList<BunkDetails>();
            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren())
            {

                Object object = noteDataSnapshot.getValue(Object.class);
                String json = new Gson().toJson(object);
                BunkDetails note= new Gson().fromJson(json, BunkDetails.class);
//                    BunkDetails note = noteDataSnapshot.getValue(BunkDetails.class);
                notes.add(note);
            }


            adapter(notes,null);

        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {
            Log.e("MESSAGE","URL"+databaseError.getMessage());
        }
    };

    void adapter(ArrayList<BunkDetails> list,ArrayList<Bunk> bunklist)
    {
        _adapter = new CustomAdapter(list,bunklist);
        mResultList.setAdapter(_adapter);
//        mResultList.setOnApplyWindowInsetsListener();
    }


    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "3");

            // easyWayLocation.startLocation();

        } else {
            ActivityCompat.requestPermissions(this, myPer, PER_REQ_CODE);
        }
    }



    public boolean permissionIsGranted() {

        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void doLocationWork() {
       /* lati = easyWayLocation.getLatitude();
        longi = easyWayLocation.getLongitude();
        Log.e("LOC", "LOC" + lati);
        if (lati != 0.0) {
            packag.nnk.com.userfuelapp.model.Location loc = new packag.nnk.com.userfuelapp.model.Location();
            loc.setLatitude(easyWayLocation.getLatitude());
            loc.setLongitude(easyWayLocation.getLongitude());

        }*/
//        easyWayLocation.startLocation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EasyWayLocation.LOCATION_SETTING_REQUEST_CODE:
                easyWayLocation.onActivityResult(resultCode);
                break;
        }
    }

    void openLocation()
    {
        if (easyWayLocation == null) {

            Log.e("LOcation","Location---null");
            easyWayLocation = new EasyWayLocation(this, locationRequest,false, this);


        } else if (easyWayLocation.hasLocationEnabled()) {

            Log.e("LOcation","Location---has");
            easyWayLocation.startLocation();
            //easyWayLocation.endUpdates();
        }
    }



    @Override
    public void locationOn() {
       // easyWayLocation.endUpdates();
        Log.e("LOcation","Location---on");
//        easyWayLocation.b
        stopLocationUpdate();
    }

    @Override
    public void currentLocation(Location location)
    {


     Log.e("LOcation","Location"+location.getLatitude());
        stopLocationUpdate();

    }

    @Override
    public void locationCancelled() {
        Log.e("LOcation","Location---off");
        stopLocationUpdate();
      //  easyWayLocation.endUpdates();
        finish();

    }



void stopLocationUpdate()
{

    Log.e("LOcation","Location---2");
    if (easyWayLocation != null)
    {

        Log.e("LOcation","Location---1");
      //  easyWayLocation.endUpdates();
        if (easyWayLocation.hasLocationEnabled()) {
            Log.e("LOcation","Location---3");

            easyWayLocation.endUpdates();

        }
    }

}


    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdate();
    }

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    /**
     * RecyclerView: Implementing single item click and long press (Part-II)
     *
     * - creating an innerclass implementing RevyvlerView.OnItemTouchListener
     * - Pass clickListener interface as parameter
     * */

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(easyWayLocation != null)
        {
            easyWayLocation.startLocation();
        }
       // easyWayLocation.startLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  easyWayLocation.endUpdates();

    }


}
