package gdgvitvellore.myffcs.CARDVIEWS;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.ArrayList;

import gdgvitvellore.myffcs.Activities.DetailedAddCourse;
import gdgvitvellore.myffcs.R;

/**
 * Created by chaman on 11/6/17.
 */
public class AcCardAdapter extends RecyclerView.Adapter<AcCardAdapter.ViewHolder> {

    ArrayList<String> codes,names;
    public AcCardAdapter(ArrayList<String> unique_crscd, ArrayList<String> unique_crsnm) {
        this.codes=unique_crscd;
        this.names=unique_crsnm;
    }

    @Override
    public AcCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_card,parent,false));
    }

    @Override
    public void onBindViewHolder(AcCardAdapter.ViewHolder holder, final int position) {
        holder.crscd.setText(codes.get(position));
        holder.crsnm.setText(names.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm=(InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromInputMethod(v.getWindowToken(),0);
                Bundle b=new Bundle();
                b.putString("code",codes.get(position));
                b.putString("name",names.get(position));
                v.getContext().startActivity(new Intent(v.getContext(),DetailedAddCourse.class).putExtras(b));
            }
        });
    }

    @Override
    public int getItemCount() {
        return codes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView crscd,crsnm;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            crscd=(TextView)itemView.findViewById(R.id.code_tv);
            crsnm=(TextView)itemView.findViewById(R.id.name_tv);
            cardView=(CardView)itemView.findViewById(R.id.offcou_cardview);
        }
    }
}
