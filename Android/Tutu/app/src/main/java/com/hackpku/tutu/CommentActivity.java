package com.hackpku.tutu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.hackpku.tutu.mylib.HorizontalScrollViewAdapter;
import com.hackpku.tutu.mylib.MyHorizontalScrollView;
import com.hackpku.tutu.mylib.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duyanpku on 16/4/9.
 */
public class CommentActivity extends Activity{

    private ListView listView;
    private NewsAdapter adapter;
    private ArrayList<String> mdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        listView = (ListView) findViewById(R.id.news);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Toast toast = Toast.makeText(getApplicationContext(), bundle.getString("url"), Toast.LENGTH_LONG);
        toast.show();
        mdata = new ArrayList<String>();
        mdata.add(bundle.getString("url"));
        mdata.add("沙雕啊沙雕啊沙雕啊沙雕啊沙雕啊沙雕啊沙雕啊沙雕啊沙雕啊沙雕啊沙雕啊沙雕啊沙雕啊沙雕啊沙雕啊沙雕啊沙雕啊");
        adapter = new NewsAdapter(this, 0, mdata);
        listView.setAdapter(adapter);
    }
}
