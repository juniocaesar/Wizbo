package edu.ftiuksw.tr_pam;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Iterator;

public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.ViewHolder> {

    private static final String TAG = "TourListAdapter";

    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mDate = new ArrayList<>();
    private ArrayList<String> mLoc = new ArrayList<>();
    private String reference;
    private String imageurl;
    private Context mContext;
    ArrayList<String> key = new ArrayList<>();

    public TourListAdapter(Context context, String url, String ref, ArrayList<String> title, ArrayList<String> date, ArrayList<String> loc) {
        reference = ref;
        imageurl = url;
        mTitle = title;
        mDate = date;
        mLoc = loc;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tour, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(mTitle.get(position));
        holder.date.setText(mDate.get(position));
        holder.loc.setText(mLoc.get(position));

        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference(reference).child("tour");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        int length = (int) dataSnapshot.getChildrenCount();
                        int i = 0;
                        while(i < length) {
                            key.add(iterator.next().getKey().toString());
                            i++;
                        }
                        FirebaseDatabase.getInstance().getReference(reference).child("tour").child(key.get(position)).removeValue();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent intent2 = new Intent(mContext, TourListActivity.class);
                intent2.putExtra("image_name", reference);
                intent2.putExtra("image_url", imageurl);
                mContext.startActivity(intent2);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTitle.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, date, loc;
        LinearLayout parentLayout;
        Button delButton;

        public ViewHolder(View itemView) {
            super(itemView);
            delButton = itemView.findViewById(R.id.listTourDelButton);
            title = itemView.findViewById(R.id.listTourTitle);
            date = itemView.findViewById(R.id.listTourDate);
            loc = itemView.findViewById(R.id.listTourLoc);
            parentLayout = itemView.findViewById(R.id.listTourParent);
        }
    }
}
