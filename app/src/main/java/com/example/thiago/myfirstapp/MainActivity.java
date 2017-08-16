package com.example.thiago.myfirstapp;

import android.content.Intent;
import android.opengl.EGLDisplay;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import CKAN.APIJsonObjectResponse;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_list_item_2;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button clickButton = (Button) findViewById(R.id.btn_send);
//        clickButton.setOnClickListener( new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
////                Toast toast = Toast.makeText(getApplicationContext(), "TESTE", Toast.LENGTH_SHORT);
////                toast.show();
//            }
//        });
    }


    public ArrayList<String> getListCkan(String query) {
        ArrayList<String> myList = new ArrayList<>();

        String portalURL = "http://ckan.imd.ufrn.br/api/action/";
//        String portalURL = "http://dados.gov.br/api/action/";
        String action = "package_search";
        String charset = "UTF-8";

        URLConnection connection = null;
        try {
            connection = new URL(portalURL + action + "?q=" + query).openConnection();
        } catch (MalformedURLException ex) {

        } catch (IOException ex) {

        }

        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = null;
        try {
            response = connection.getInputStream();
        } catch (IOException ex) {

        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(response)
            String responseBody = scanner.useDelimiter("\\A").next();
            APIJsonObjectResponse ckan = new APIJsonObjectResponse(responseBody);

            if (ckan.getSuccess()) {
                int resultsFound = ckan.getResult().getInt("count");

                if (resultsFound == 0) {
                    return;
                }

                JSONObject myObj = ckan.getResult();
                int i = 1;
                for (Iterator iterator = myObj.getJSONArray("results").iterator(); iterator.hasNext(); ) {
                    JSONObject next = (JSONObject) iterator.next();
                    myList.add(next.getString("title"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        return myList;
    }

    private class ServiceTask extends AsyncTask {
        @Override
        protected Usuario doInBackground(String... params) {
            String nome = params[0];
            String sobrenome = params[1];
            String email = params[2];
            String senha = params[3];
            UsuarioService usuarioService = new UsuarioService();
            try {
                return usuarioService.cadastrarUsuario(getApplicationContext(), nome, sobrenome, email, senha);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            if (usuario == null) {
                Toast.makeText(getApplicationContext(), "Ocorreu um erro ao realizar o cadastro!", Toast.LENGTH_SHORT).show();
            } else if (usuario != null && usuario.getIdUsuario() > 0) {
                setPreferencias(usuario);
                Intent it = new Intent(CadastroUsuarioActivity.this, MainActivity.class);
                startActivity(it);
            } else {
                Toast.makeText(getApplicationContext(), "O usuário já se encontra cadastrado. Registre-se!", Toast.LENGTH_SHORT).show();
            }
        }

        private void setPreferencias(Usuario usuario) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(getString(R.string.pref_id_usuario), usuario.getIdUsuario());
            editor.putLong(getString(R.string.pref_id_tipo_usuario), usuario.getIdTipoUsuario());
            editor.putBoolean(getString(R.string.pref_is_usuario_logado), usuario.getIdUsuario() > 0);
            editor.apply();
        }
    }

    /**
     * Called when the user clicks the Send button
     */
    public void sendMessageList(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String s = editText.getText().toString();
        ArrayList<String> myList = getListCkan(s);
        ListView lista = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, simple_list_item_1, myList);
        lista.setAdapter(myAdapter);
    }

//    public void sendMessage(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }
}
