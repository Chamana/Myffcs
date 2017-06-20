package gdgvitvellore.myffcs.CARDVIEWS;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import gdgvitvellore.myffcs.Activities.DeleteCourse;
import gdgvitvellore.myffcs.R;

/**
 * Created by chaman on 9/6/17.
 */
public class CoursesCardView extends RecyclerView.Adapter<CoursesCardView.ViewHolder> {

    ArrayList<String> slot,code,type,venue,title,faculty,id,mode;
    ArrayList<Integer> cred;

    public CoursesCardView(ArrayList<String> c_code, ArrayList<String> c_type, ArrayList<String> c_slot, ArrayList<String> c_name, ArrayList<String> c_faculty, ArrayList<String> c_venue,ArrayList<String> id,ArrayList<String> mode,ArrayList<Integer> cred) {
        this.code=c_code;
        this.type=c_type;
        this.venue=c_venue;
        this.title=c_name;
        this.faculty=c_faculty;
        this.slot=c_slot;
        this.id=id;
        this.mode=mode;
        this.cred=cred;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.course_cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.code_tv.setText(code.get(position));
        holder.type_tv.setText(type.get(position));
        holder.slot_tv.setText(slot.get(position));
        holder.title_tv.setText(title.get(position));
        holder.faculty_tv.setText(faculty.get(position));
        holder.venue_tv.setText(venue.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("id",id.get(position));
                b.putString("code",code.get(position));
                b.putString("name",title.get(position));
                b.putString("slot",slot.get(position));
                b.putString("faculty",faculty.get(position));
                b.putString("type",type.get(position));
                b.putString("mode",mode.get(position));
                b.putString("venue",venue.get(position));
                b.putInt("cred",cred.get(position));
                v.getContext().startActivity(new Intent(v.getContext(),DeleteCourse.class).putExtras(b));
            }
        });
    }

    @Override
    public int getItemCount() {
        return code.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView slot_tv,code_tv,type_tv,venue_tv,title_tv,faculty_tv;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.course_cardview);
            slot_tv=(TextView)itemView.findViewById(R.id.tv_slot);
            code_tv=(TextView)itemView.findViewById(R.id.tv_code);
            type_tv=(TextView)itemView.findViewById(R.id.tv_type);
            venue_tv=(TextView)itemView.findViewById(R.id.tv_venue);
            title_tv=(TextView)itemView.findViewById(R.id.tv_name);
            faculty_tv=(TextView)itemView.findViewById(R.id.tv_fac);
        }
    }
}
