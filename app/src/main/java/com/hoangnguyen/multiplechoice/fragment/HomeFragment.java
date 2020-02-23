package com.hoangnguyen.multiplechoice.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hoangnguyen.multiplechoice.R;
import com.hoangnguyen.multiplechoice.activity.MainActivity;
import com.hoangnguyen.multiplechoice.activity.NewsDetailActivity;
import com.hoangnguyen.multiplechoice.activity.ScreenSlidePagerActivity;
import com.hoangnguyen.multiplechoice.adapter.NewsAdapter;
import com.hoangnguyen.multiplechoice.commom.XMLDOMParser;
import com.hoangnguyen.multiplechoice.model.News;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private NewsAdapter newsAdapter;
    private ListView listNews;
    private ArrayList<News> newsArrayList = new ArrayList<News>();
    public static final String RSS_LINK = "https://thi.tuyensinh247.com/dap-an-de-thi-c16.rss";
    private RelativeLayout errorLayout;
    private Button btnRetry;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Home");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listNews = view.findViewById(R.id.lv_news);
        errorLayout = view.findViewById(R.id.errorLayout);
        btnRetry = view.findViewById(R.id.btn_retry);

        if (isOnline() == false) {

            listNews.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);

        } else {
            new ReadRSSData().execute(RSS_LINK);
        }
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline() == true) {
                    errorLayout.setVisibility(View.GONE);
                    listNews.setVisibility(View.VISIBLE);
                    Fragment fragment = new HomeFragment();
                    loadFragment(fragment);
                } else {
                    Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("link", newsArrayList.get(position).getLink());
                startActivity(intent);
            }
        });


        return view;
    }

    private class ReadRSSData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder builder = new StringBuilder();

            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return builder.toString();
        }


        @Override
        protected void onPostExecute(String s) {

            XMLDOMParser parser = new XMLDOMParser();

            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");

            String title = "";
            String link;
            String pubDate;

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
                link = parser.getValue(element, "link");
                pubDate = parser.getValue(element, "pubDate");
                newsArrayList.add(new News(title, link, pubDate));

            }
            newsAdapter = new NewsAdapter(getActivity(), R.layout.item_listview_news, newsArrayList);
            listNews.setAdapter(newsAdapter);
            super.onPostExecute(s);
        }


    }

    private Boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected()) {
            return true;
        }
        return false;
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.commit();
    }
}
