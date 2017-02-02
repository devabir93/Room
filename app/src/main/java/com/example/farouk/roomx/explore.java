package com.example.farouk.roomx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class explore extends AppCompatActivity {

private List<Room> roommList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DetailAdapter mAdapter;
    public ImageButton btlike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        btlike = (ImageButton) findViewById(R.id.imge_love);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
       // recyclerView.setHasFixedSize(true);
        mAdapter = new DetailAdapter(roommList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                    Room roomm = roommList.get(position);
                    Toast.makeText(getApplicationContext(), roomm.getUserName() + " is selected!", Toast.LENGTH_SHORT).show();

              //  Toast.makeText(getApplicationContext(), (String) btlike.getTag(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareDetailData();
    }

    private void prepareDetailData() {
        Room  rroom = new Room(R.drawable.ttt,"farouk albakri","@farouk","1990","gaza",R.drawable.room,1,"new flate you want to rent 120 m have agood beed ");
        roommList.add(rroom);

        rroom = new Room(R.drawable.tttt,"abir","@devabir","1993","gaza",R.drawable.flate,0,"new flate you want to rent 120 m have agood beed ");
        roommList.add(rroom);

        rroom = new Room(R.drawable.tttt,"abir","@devabir","1993","gaza",R.drawable.flate,0,"new flate you want to rent 120 m have agood beed ");
        roommList.add(rroom);


        mAdapter.notifyDataSetChanged();
    }

}
