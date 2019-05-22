package com.example.sensus.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.sensus.Adapter.PendudukAdapter;
import com.example.sensus.Api;
import com.example.sensus.MainActivity;
import com.example.sensus.model.Penduduk;
import com.example.sensus.R;
import com.example.sensus.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LihatFragment extends Fragment implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private Context context;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    public static ArrayList<Penduduk> heroList = new ArrayList<Penduduk>();
    private SearchView editsearch;
    private PendudukAdapter adapter;
    View view;
    public MainActivity mainActivity;
    public  int id_pesensus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lihatdata, container, false);

        context = getActivity();
        mainActivity = (MainActivity) getActivity();
        this.id_pesensus = mainActivity.id;

        editsearch = (SearchView)view.findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);
        recyclerView = view.findViewById(R.id.recycler_view_lihat);
        readHeroes();

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment tambahFragment = new TambahFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, tambahFragment);
                transaction.addToBackStack(null);


                transaction.commit();



            }
        });



        this.view = view;

        return  view;


    }
    private void readHeroes() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_HEROES_BY_ID + id_pesensus, null, CODE_GET_REQUEST);
        request.execute();

    }

    public void refreshHeroList(JSONArray heroes) throws JSONException {
        heroList.clear();
        List<Penduduk> isi = new ArrayList<>();

        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);

            heroList.add(new Penduduk(
                    obj.getInt("id"),
                    obj.getString("nama"),
                    obj.getString("pekerjaan"),
                    obj.getInt("nik"),
                    obj.getString("kecamatan")
            ));
        }


        adapter = new PendudukAdapter(context,heroList,heroList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context.getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        adapter.getFilter().filter(newText);
        return true;
    }


    public class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(context.getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshHeroList(object.getJSONArray("heroes"));



                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

}