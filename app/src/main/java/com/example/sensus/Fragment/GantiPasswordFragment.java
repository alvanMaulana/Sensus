package com.example.sensus.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sensus.Api;
import com.example.sensus.MainActivity;
import com.example.sensus.R;
import com.example.sensus.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class GantiPasswordFragment extends Fragment {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    Context context;
    EditText password1,password2;
    TextView Id;
    Button ganti;
    public MainActivity mainActivity;

    private int id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ganti_password, container, false);
        context = getActivity();

             Id = (TextView) view.findViewById(R.id.Id_Password);
         password1 = (EditText) view.findViewById(R.id.editTextPassword1);
         password2 = (EditText) view.findViewById(R.id.editTextPassword2);
         ganti     = (Button) view.findViewById(R.id.ButtonGanti);


        mainActivity = (MainActivity) getActivity();
        this.id = mainActivity.id;
        Id.setText(""+this.id);

ganti.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        gantiPassword();


    }
});


        return view;
    }


    private void movetoLihat() {
        Fragment lihatFragment = new LihatFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, lihatFragment);
        transaction.addToBackStack(null);


        transaction.commit();
    }
    private void gantiPassword() {
        String ID =  Id.getText().toString();
        String Password1 =  password1.getText().toString();
        String Password2 =  password2.getText().toString();


        if (TextUtils.isEmpty(Password1)) {
            password1.setError("Please enter password");
            password1.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Password2)) {
            password2.setError("Please enter password");
            password2.requestFocus();
            return;
        }
        if (!Password1.equals(Password2)) {
            password2.setError("Password tidak sama");
            password2.requestFocus();
            return;
        }



        HashMap<String, String> params = new HashMap<>();
        params.put("id",ID);
        params.put("passwordbaru", Password2);



        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_USER, params, CODE_POST_REQUEST);
        request.execute();
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
