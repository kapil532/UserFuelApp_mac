package packag.nnk.com.userfuelapp.bunk_search;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.model.Bunk;
import packag.nnk.com.userfuelapp.model.BunkDetails;
import packag.nnk.com.userfuelapp.transaction.OnItemClickListener;
import packag.nnk.com.userfuelapp.transaction.Transaction;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
 
    private ArrayList<BunkDetails> bunk;
    ArrayList<Bunk> bunklist;
 
    public CustomAdapter(ArrayList<BunkDetails> bunk,ArrayList<Bunk> bunklist) {
        this.bunk = bunk;
        this.bunklist=bunklist;
    }
 
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);



        return new ViewHolder(v);
    }
 
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        if(bunk != null)
        {
            BunkDetails bunkDetails =bunk.get(position);
            holder.textViewName.setText(bunkDetails.getBunkName().toUpperCase());
            holder.textAddress.setText(bunkDetails.getBunkAddress().toLowerCase());
        }
        else
        {
            Bunk bunkDetails =bunklist.get(position);
            holder.textViewName.setText(bunkDetails.getPetrolBunkName().toUpperCase());
            holder.textAddress.setText(bunkDetails.getPetrolBunkAddress().toLowerCase());
        }


    }
 
    @Override
    public int getItemCount()
    {
        if(bunk != null)
        {
            return bunk.size();
        }
        else
        {
            return 5;
        }
    }
 
    class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
 
        TextView textViewName;
        TextView textAddress;

        ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            textViewName = (TextView) itemView.findViewById(R.id.name_text);
            textAddress = (TextView) itemView.findViewById(R.id.address);
        }


        @Override
        public void onClick(View view) {

        }
    }
}