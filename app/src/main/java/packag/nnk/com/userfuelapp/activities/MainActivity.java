package packag.nnk.com.userfuelapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.about_us.AboutUsScreen;
import packag.nnk.com.userfuelapp.about_us.CustomSupportScreenActivity;
import packag.nnk.com.userfuelapp.about_us.SuccessScreen;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.AppSharedPreUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.base.CommonClass;
import packag.nnk.com.userfuelapp.base.ErrorUtils;
import packag.nnk.com.userfuelapp.bunk_search.CustomAdapter;
import packag.nnk.com.userfuelapp.fragment_view.PinVerification;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.interfaces.GetMessage;
import packag.nnk.com.userfuelapp.interfaces.getBunkDetails;
import packag.nnk.com.userfuelapp.model.Balance;
import packag.nnk.com.userfuelapp.model.Bunk;
import packag.nnk.com.userfuelapp.model.BunkDetailWithLatLang;
import packag.nnk.com.userfuelapp.model.BunkDetails;
import packag.nnk.com.userfuelapp.model.Location;
import packag.nnk.com.userfuelapp.model.Payment;
import packag.nnk.com.userfuelapp.model.RangeTransaction;
import packag.nnk.com.userfuelapp.model.UserDetails;
import packag.nnk.com.userfuelapp.petrol_bunk_details.GetList;
import packag.nnk.com.userfuelapp.services.AutoCompleteAdapter;
import packag.nnk.com.userfuelapp.transaction.TransactionActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener
       /* FingerprintDialogSecureCallback,
        PasswordCallback*/ {

//    firebase database:set /location/ file.json -P project-id
    private String TAG = MainActivity.class.getSimpleName();
    private ApiInterface mApiService;
    private ApiInterface mApiService_;

    public Toolbar toolbar;

    public DrawerLayout drawerLayout;

    public NavController navController;

    public NavigationView navigationView;
    @BindView(R.id.image_icon)
    ImageView image_icon;


    @BindView(R.id.p_name)
    TextView p_name;

    @BindView(R.id.p_adress)
    TextView p_adress;

    @BindView(R.id.text_200)
    TextView text_200;


    @BindView(R.id.text_500)
    TextView text_500;

  /*  @BindView(R.id.appCompatTextView)
    TextView appCompatTextView;*/


    @BindView(R.id.text_1000)
    TextView text_1000;

    @BindView(R.id.text_800)
    TextView text_800;

    @BindView(R.id.text_100)
    TextView text_100;


    @BindView(R.id.other_money)
    EditText other_money;

    @BindView(R.id.submit)
    Button submit;

    TextView autoCompleteTextView;
    CustomAdapter _adapter;

    AutoCompleteAdapter adapter;

    TextView responseView;
    PlacesClient placesClient;


    Double paymentPrice = 0.0;
    String petrolBunkName = "";
    String petrolID = "";

    Location loc;

    DatabaseReference mFirebaseDatabaseReference;   private RecyclerView mResultList;

    AutoCompleteTextView auto;

    ImageView appCompatImageView;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //Retrofit

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.appCompatTextView);
         appCompatImageView = (ImageView) hView.findViewById(R.id.appCompatImageView);

        nav_user.setText(user.getUsername());
        appCompatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  userAct = new Intent(getApplicationContext(),UserCreateActivity.class);
                userAct.putExtra("key",true);
                startActivity(userAct);
            }
        });


        mApiService = new ApiUtils().getApiInterfacesForPetrolBunk();
        mApiService_ = new ApiUtils().getApiInterfaces();
        loc = AppSharedPreUtils.getInstance(getApplicationContext()).getLocation();
        mFirebaseDatabaseReference   = FirebaseDatabase.getInstance().getReference("location");

        setupNavigation();
        getPetrolList();
        moneySelection();


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), CommonClass.GCP_KEY);
        }

        initAutoCompleteTextView();


        setFont(other_money);
        other_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                makeUnselect();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    Log.e("EDIT", "editable" + Double.parseDouble(other_money.getText().toString()));
                    paymentPrice = Double.parseDouble(other_money.getText().toString());


                } catch (Exception e) {
                    paymentPrice = 0.0;
                }

            }
        });
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

    }

    // Setting Up One Time Navigation
    private void setupNavigation() {

        toolbar = findViewById(R.id.toolbar);
        ImageView menu = findViewById(R.id.menu);
        TextView title = findViewById(R.id.textHeader);
        setFont(title);
        title.setText(getResources().getString(R.string.go_fuels));
        drawerLayout = findViewById(R.id.drawer_layout);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        setFont(submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // callDilouge();
                if (paymentPrice == 0) {
                    Toast.makeText(MainActivity.this, "Please select payment amount", Toast.LENGTH_LONG).show();
                } else {
//                    showAlertBox("Hi you want to pay "+paymentPrice+" Rs. " +"to "+petrolBunkName +" .");
                    FragmentManager manager = getSupportFragmentManager();
                    PinVerification alertDialogFragment = new PinVerification();

                    alertDialogFragment.show(manager, "fragment_edit_name");
                    PinVerification.getMessage = new GetMessage() {
                        @Override

                        public void getSuccessMessage(String s) {


                            if(petrolID.length()>1)
                            {
                                doPayment("" + paymentPrice, petrolID);
                            }
                           else
                            {
                               Toast.makeText(getApplicationContext(),"Please select petrol bunk!",Toast.LENGTH_LONG).show();
                            }
                        }
                    };

                }

            }
        });

    }



    void showAlertBox(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        doPayment("200", petrolID);

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    List<Bunk> bunkList;
    void getPetrolList()
    {
        JsonObject json = new JsonObject();
        try {
            json.addProperty("longitude", "" + loc.getLongitude());
            json.addProperty("latitude", "" + loc.getLatitude());
        } catch (Exception e) {

        }



        Call<BunkDetailWithLatLang> getList = mApiService_.getBunkDetail(json);
        getList.enqueue(new Callback<BunkDetailWithLatLang>() {
            @Override
            public void onResponse(Call<BunkDetailWithLatLang> call, Response<BunkDetailWithLatLang> response) {
                try {
                    Bunk bunk=response.body().getBunks().get(0);
                    bunkList =response.body().getBunks();
                    setTheView(bunk,null);

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<BunkDetailWithLatLang> call, Throwable t) {
                Log.e("RESPONSE", "--fail");
            }
        });
    }





    void setTheView( Bunk bunk,BunkDetails bunkDetails)
    {

        if(bunkDetails != null)
        {
            petrolBunkName = bunkDetails.getBunkName();
            petrolID = bunkDetails.getBunkId();

            Picasso.with(this).load(bunkDetails.getIconId())
                    .error(R.drawable.icon_pb)
                    .placeholder(R.drawable.icon_pb)
                    .into(image_icon);
            p_name.setText(bunkDetails.getBunkName().toUpperCase());
            p_adress.setText(bunkDetails.getBunkAddress().toLowerCase());
        }
        else
        {
            petrolBunkName = bunk.getPetrolBunkName();
            petrolID = bunk.getId();

            Picasso.with(this).load(bunk.getIconId())
                    .error(R.drawable.icon_pb)
                    .placeholder(R.drawable.icon_pb)
                    .into(image_icon);
            p_name.setText(bunk.getPetrolBunkName().toUpperCase());
            p_adress.setText(bunk.getPetrolBunkAddress().toLowerCase());
        }





    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);

        drawerLayout.closeDrawers();

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.first:
                // navController.navigate(R.id.firstFragment);

                Intent about = new Intent(this, SetPinActivity.class);
                startActivity(about);
                break;

            case R.id.second:
                // navController.navigate(R.id.secondFragment);
                Intent su = new Intent(this, AboutUsScreen.class);
                startActivity(su);
                break;

            case R.id.third:

                Intent support = new Intent(this, CustomSupportScreenActivity.class);
                startActivity(support);
                //navController.navigate(R.id.thirdFragment);
                break;


            case R.id.fourt:
                //navController.navigate(R.id.thirdFragment);

                Intent trans = new Intent(this, TransactionActivity.class);
                startActivity(trans);
                break;
            case R.id.setting:
                //navController.navigate(R.id.thirdFragment);

                Intent set = new Intent(this, SettingActivity.class);
                startActivity(set);
                break;

        }
        return true;

    }


    void moneySelection() {
        text_1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentPrice = 1000.0;
                text_1000.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_sel_round));

                text_200.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_500.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_800.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_100.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                setPadding();
            }
        });

        text_200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentPrice = 200.0;
                text_200.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_sel_round));
                text_1000.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_500.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_800.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_100.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                setPadding();
            }
        });

        text_500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentPrice = 500.0;
                text_500.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_sel_round));
                text_200.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_1000.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_800.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_100.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                setPadding();
            }
        });


        text_800.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentPrice = 800.0;
                text_800.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_sel_round));
                text_200.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_1000.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_500.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_100.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                setPadding();
            }
        });

        text_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentPrice = 100.0;
                text_100.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_sel_round));
                text_200.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_1000.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_500.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_800.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                setPadding();
            }
        });


    }

    void makeUnselect() {
        text_500.setBackground(getResources().getDrawable(
                R.drawable.rect_select_round));
        text_200.setBackground(getResources().getDrawable(
                R.drawable.rect_select_round));
        text_1000.setBackground(getResources().getDrawable(
                R.drawable.rect_select_round));
        setPadding();
    }
//

    void setPadding() {
        text_1000.setPadding(0, 13, 0, 13);
        text_500.setPadding(0, 13, 0, 13);
        text_200.setPadding(0, 13, 0, 13);
        text_800.setPadding(0, 13, 0, 13);
        text_100.setPadding(0, 13, 0, 13);
    }

    private void initAutoCompleteTextView()
    {

        autoCompleteTextView = findViewById(R.id.search_field);
        setFont(autoCompleteTextView);
        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SearchBunkStationActvity.bunkDetails = new getBunkDetails() {
                    @Override
                    public void getBunkDe(Bunk bunk,BunkDetails bunkDetails)
                    {
                            setTheView(bunk,bunkDetails);
                    }
                };
                Intent search = new Intent(getApplicationContext(),SearchBunkStationActvity.class);
                search.putExtra("bunk_list",CommonClass.listToArrayList(bunkList));
                startActivity(search);


            }
        });


    }



    @Override
    public void onClick(View view) {

    }


    void doPayment(String price, String petrolID) {
        showProgressDialog();
        JsonObject json = new JsonObject();
        try {
            json.addProperty("driverId", "" + user.getUserId());
            json.addProperty("amount", "" + price);
            json.addProperty("petrolBunkId", "" + petrolID);
        } catch (Exception e) {

        }

        Call<Payment> payment = mApiService_.doPayment(json, user.getUserId());
        payment.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {

                hideProgressDialog();
                try {
                    Log.e("MAINACTIVITY", "VALUES" + response.body());
                    Payment payment = response.body();
                    if (payment.getStatus().equalsIgnoreCase("success")) {
//                       showSuccessScreen();
                        Intent success = new Intent(getApplicationContext(), SuccessScreen.class);
                        success.putExtra("petr_name", "" + petrolBunkName);
                        success.putExtra("petr_price", "" + paymentPrice);
                        success.putExtra("petrolID", "" + petrolID);
                        startActivity(success);

                    } else {
                        Toast.makeText(getApplicationContext(), "Please try again!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), ErrorUtils.getStatus(response).getMessage(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(), "Please try again!", Toast.LENGTH_LONG).show();
            }
        });


    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }









    @Override
    protected void onResume() {
        super.onResume();
getImageFromStorage();
        if (AppSharedPreUtils.getInstance(getApplicationContext()).getStringValues("FIREBASE_KEY").length() > 10) {
            sendFirebaseTokenToServer();
        }
    }

    void sendFirebaseTokenToServer() {
        JsonObject json = new JsonObject();
        try {
            Log.e("USERID", "----" + user.getUserId());
            json.addProperty("userId", "" + user.getUserId());
            json.addProperty("firebaseToken", "" + AppSharedPreUtils.getInstance(getApplicationContext()).getStringValues("FIREBASE_KEY"));
        } catch (Exception e) {

        }

        Call<UserDetails> tokenUpdate = mApiService_.updateToken(json);
        tokenUpdate.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {

                if (response.isSuccessful()) {
//                            Log.e("USERDETAILS","USERDETAI"+response.message());
                    AppSharedPreUtils.getInstance(getApplicationContext()).removeValues("FIREBASE_KEY");
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {

            }
        });
    }


    private void getImageFromStorage()
    {
        StorageReference islandRef = storageReference.child("images/"+ user.getUserId()+".png");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new  OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes)
            {
                // Data for "images/island.jpg" is returns, use this as needed
                Drawable d = Drawable.createFromStream(new ByteArrayInputStream(bytes), null);
                appCompatImageView.setImageDrawable(d);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                loadProfileDefault();
            }
        });
    }


    private void loadProfileDefault() {
        Picasso.with(this).load(R.drawable.iconfinder_je)
                .into(appCompatImageView);
        appCompatImageView.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }





}

