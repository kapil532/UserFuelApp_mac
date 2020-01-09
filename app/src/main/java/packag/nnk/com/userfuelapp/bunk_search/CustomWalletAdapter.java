package packag.nnk.com.userfuelapp.bunk_search;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.CommonClass;
import packag.nnk.com.userfuelapp.model.wallet.History;


public class CustomWalletAdapter extends RecyclerView.Adapter<CustomWalletAdapter.ViewHolder> {

    private ArrayList<History> bunk;
    private Context mContext;

    public CustomWalletAdapter(Context context,ArrayList<History> bunk) {
        this.bunk = bunk;
        this.mContext=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallet_history_list, parent, false);



        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        if(bunk != null)
        {
            History history =bunk.get(position);
            holder.balance.setText(Html.fromHtml("Balance added : " + mContext.getResources().getString(R.string.symbol_rs)
                    + " " + CommonClass.getSaparatorIntoMoney(history.getBalance())));
            holder.date.setText("Time : "+dateFromat(history.getTransactionDate()));
        }



    }

    String dateFromat(String date) {

        return CommonClass.getUTCToLocalDate(date);
     /*   SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm a dd-MMM-yyyy");

        Date d = null;
        try {
            d = input.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(d);*/
    }

    @Override
    public int getItemCount()
    {

            return bunk.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {

        TextView balance;
        TextView date;

        ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            balance = (TextView) itemView.findViewById(R.id.balance);
            date = (TextView) itemView.findViewById(R.id.date);
        }


        @Override
        public void onClick(View view) {

        }
    }
}