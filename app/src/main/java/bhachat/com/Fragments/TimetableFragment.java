package bhachat.com.Fragments;




import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import bhachat.com.R;



public class TimetableFragment extends Fragment {
    public Elements content;
    public ArrayList<String> titleList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private ListView raspis;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);

        raspis = view.findViewById(R.id.lesson5);
        new NewThread().execute();
        adapter = new ArrayAdapter<>(getContext(), R.layout.list_item, R.id.pro_item, titleList);

        return view;
    }

    private class NewThread extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg) {
            Document doc;
            try {
                doc = Jsoup.connect("https://www.nstu.ru/studies/schedule/schedule_classes/schedule?group=%D0%94-72").get();
                content = doc.select(".schedule__table-time");
                titleList.clear();
                for (Element contents : content){
                    titleList.add(contents.text());
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return  null;
        }

        @Override
        protected void onPostExecute (@Nullable String result) {
            raspis.setAdapter(adapter);
        }
    }
    }



