package packag.nnk.com.userfuelapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.base.CommonClass;
import packag.nnk.com.userfuelapp.bunk_search.CustomAdapter;
import packag.nnk.com.userfuelapp.bunk_search.CustomWalletAdapter;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.model.Bunk;
import packag.nnk.com.userfuelapp.model.BunkDetails;
import packag.nnk.com.userfuelapp.model.wallet.History;
import packag.nnk.com.userfuelapp.model.wallet.WalletHistory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverWalletAddHistory extends BaseActivity
{
ApiInterface getApiInterfaces;

    CustomWalletAdapter _adapter;


    @BindView(R.id.wallet_list)
    RecyclerView wallet_list;

    @BindView(R.id.no_text)
    TextView no_text;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

          setContentView(R.layout.driver_wallet_history);
        ButterKnife.bind(this);
        getApiInterfaces = new ApiUtils().getApiInterfaces();

        wallet_list.setHasFixedSize(true);
        wallet_list.setLayoutManager(new LinearLayoutManager(this));

        setupNavigation();
        getWalletTrans();
    }
    List<History> list;


    private void setupNavigation()
    {

        toolbar.setTitle("Wallet History");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
               finish();
            }
        });


    }
    void getWalletTrans()
    {

        showProgressDialog();

        Call<WalletHistory> call =getApiInterfaces.getWalletTras(user.getUserId());
        call.enqueue(new Callback<WalletHistory>() {
            @Override
            public void onResponse(Call<WalletHistory> call, Response<WalletHistory> response) {

                hideProgressDialog();
                list  = response.body().getHistory();

                if (list != null)
                {
                    if(list.size()>0)
                    {

                        adapter(CommonClass.listToArrayListHis(list));
                        no_text.setVisibility(View.GONE);
                    }
                    else
                    {
                        no_text.setVisibility(View.VISIBLE);
                    }

                }
                else
                {
                    no_text.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<WalletHistory> call, Throwable t) {

                hideProgressDialog();
                no_text.setVisibility(View.VISIBLE);
            }
        });


    }


    void adapter(ArrayList<History> list)
    {
        _adapter = new CustomWalletAdapter(getApplicationContext(),list);
        wallet_list.setAdapter(_adapter);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(),getResources().getIdentifier("layout_animation_from_right","anim",getPackageName()));
        wallet_list.setLayoutAnimation(animation);
      // wallet_list.notifyDataSetChanged();
        wallet_list.scheduleLayoutAnimation();

//        mResultList.setOnApplyWindowInsetsListener();
    }
}
