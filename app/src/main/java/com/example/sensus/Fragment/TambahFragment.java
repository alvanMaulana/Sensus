package com.example.sensus.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sensus.Adapter.PendudukAdapter;
import com.example.sensus.Api;
import com.example.sensus.MainActivity;
import com.example.sensus.R;
import com.example.sensus.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class TambahFragment extends Fragment  {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

   Context context;
    EditText editTextHeroId, editTextName, editTextPekerjaan,editTextNik;
    Spinner spinnerKecamatan;
    ProgressBar progressBar;
    int id,id_pesensus;
    public MainActivity mainActivity;

    Button buttonAddUpdate,buttonDelete;
    boolean isUpdating = false;
    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambahdata, container, false);

        context = getActivity();

        TextView judul = (TextView)view.findViewById(R.id.judul);

        editTextHeroId = (EditText) view.findViewById(R.id.editTextHeroId);
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextPekerjaan = (EditText) view.findViewById(R.id.editTextPekerjaan);
        editTextNik = (EditText) view.findViewById(R.id.editTextNik);
        spinnerKecamatan = (Spinner) view.findViewById(R.id.spinnerIdKecamatan);
        buttonAddUpdate = (Button) view.findViewById(R.id.buttonAddUpdate);
        buttonDelete = (Button) view.findViewById(R.id.buttonDelete);
        TextView Id_pesensus = (TextView) view.findViewById(R.id.id_pesensus);
        Bundle bundle = this.getArguments();

        mainActivity = (MainActivity) getActivity();
        this.id_pesensus = mainActivity.id;
        Id_pesensus.setText(""+this.id_pesensus);





        if (bundle != null) {

            int id =  bundle.getInt("ID");
            this.id = id;

            String Name =  bundle.getString("NAMA");

            int Nik =  bundle.getInt("NIK");

            String Pekerjaan =  bundle.getString("PEKERJAAN");

            String Kecamatan =  bundle.getString("KECAMATAN");


            editTextHeroId.setText(String.valueOf(id));
            editTextName.setText(Name);
            editTextPekerjaan.setText(Pekerjaan);
            editTextNik.setText(String.valueOf(Nik));
            spinnerKecamatan.setSelection(((ArrayAdapter<String>) spinnerKecamatan.getAdapter()).getPosition(Kecamatan));

            judul.setText("Ubah Data");
            buttonAddUpdate.setText("Ubah");

            isUpdating = true;
            buttonDelete.setVisibility(View.VISIBLE);



        }



buttonDelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        deleteHero();
        movetoLihat();
    }
});


        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdating) {
                    updateHero();
                } else {
                    createHero();
                }

            }
        });


    this.view = view;
          return  view;

    }

    private void movetoLihat() {
        Fragment lihatFragment = new LihatFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, lihatFragment);
        transaction.addToBackStack(null);


        transaction.commit();
    }

    private void deleteHero() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_HERO + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void updateHero() {

        String id = editTextHeroId.getText().toString();
        String name = editTextName.getText().toString().trim();
        String pekerjaan = editTextPekerjaan.getText().toString().trim();

        String nik = editTextNik.getText().toString().trim();

        String kecamatan = spinnerKecamatan.getSelectedItem().toString();


        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Please enter name");
            editTextName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pekerjaan)) {
            editTextPekerjaan.setError("Please enter Pekerjaan");
            editTextPekerjaan.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(nik)) {
            editTextNik.setError("Please enter nik");
            editTextNik.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id",id);
        params.put("nama", name);
        params.put("pekerjaan", pekerjaan);
        params.put("nik", nik);
        params.put("kecamatan", kecamatan);




        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_HERO, params, CODE_POST_REQUEST);
        request.execute();
        movetoLihat();
    }


    private void createHero() {

            String name = editTextName.getText().toString().trim();
            String pekerjaan = editTextPekerjaan.getText().toString().trim();

            String nik = editTextNik.getText().toString().trim();

            String kecamatan = spinnerKecamatan.getSelectedItem().toString();
            String id_pesensus = String.valueOf(this.id_pesensus);

            if (TextUtils.isEmpty(name)) {
                editTextName.setError("Please enter name");
                editTextName.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(pekerjaan)) {
                editTextPekerjaan.setError("Please enter job");
                editTextPekerjaan.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(nik)) {
                editTextNik.setError("Please enter nik");
                editTextNik.requestFocus();
                return;
            }

            HashMap<String, String> params = new HashMap<>();
            params.put("nama", name);
            params.put("pekerjaan", pekerjaan);
            params.put("nik", nik);
            params.put("kecamatan", kecamatan);
             params.put("id_pesensus", id_pesensus);


PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_HERO, params, CODE_POST_REQUEST);
         request.execute();

            editTextName.setText("");
            editTextPekerjaan.setText("");
            editTextNik.setText("");
            spinnerKecamatan.setSelection(0);
        movetoLihat();
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















