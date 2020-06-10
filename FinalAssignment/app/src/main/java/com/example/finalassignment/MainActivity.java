package com.example.finalassignment;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements vAdapter.ListItemClickListener {
    private static final String TAG = "zdj";
    private RecyclerView videoListView;
    private List<VideoItem> videoItemList = new ArrayList<>();

    private vAdapter vA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {//Android 9 以上不允许在主线程中进行网络请求操作
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_main);
        videoListView = findViewById(R.id.rv_list);
        initvl();//list初始化
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        videoListView.setLayoutManager(layoutManager);
        videoListView.setHasFixedSize(true);
        vA=new vAdapter(videoItemList, (vAdapter.ListItemClickListener) this);
        videoListView.setAdapter(vA);

        videoListView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int lastCompletelyVisibleItemPosition;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (visibleItemCount > 0 && lastCompletelyVisibleItemPosition >= totalItemCount - 1) {
                        Toast.makeText(MainActivity.this, "已滑动到底部!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                    super.onScrolled(recyclerView, dx, dy);
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                    }
                    Log.d(TAG, "onScrolled: lastVisiblePosition=" + lastCompletelyVisibleItemPosition);
            }
        });

    }

    protected void initvl(){
        String api = "https://beiyou.bytedance.com/api/invoke/video/invoke/video";
        URL url;
        HttpURLConnection httpURLConnection = null;
        try {
            url=new URL(api);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(100000);
            httpURLConnection.setReadTimeout(100000);
            if (httpURLConnection.getResponseCode() == 200){//网络连接成功
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while((line=br.readLine())!= null){
                    buffer.append(line);
                }
                JSONArray jsonArray = new JSONArray(buffer.toString());
                Log.d(TAG, "analyzeJSONArray1 jsonArray:" + jsonArray);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String description = jsonObject.optString("description", null);
                    String nickname = jsonObject.optString("nickname",null);
                    String feedurl = jsonObject.optString("feedurl",null);
                    String avatar = jsonObject.optString("avatar",null);

                    VideoItem v1 = new VideoItem(description,nickname,avatar,feedurl);
                    videoItemList.add(v1);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, VideoPlayer.class);
        intent.putExtra("videoUrl", videoItemList.get(clickedItemIndex).getFeedurl());
        startActivity(intent);
    }
}
