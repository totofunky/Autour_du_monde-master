package com.example.thorin_lenain.autourdumonde;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.thorin_lenain.autourdumonde.model.Restos;
import com.facebook.Session;
import com.facebook.widget.LoginButton;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Thorin_LeNain on 15/10/2014.
 */
public class Fragment_list extends Fragment {
    private View view;
    private ListView listView;
    private SimpleAdapter schedule;
    private ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        listView =(ListView) view.findViewById(R.id.list_resto);
        editText =(EditText) view.findViewById(R.id.search_edit);

        final LoginButton button = (LoginButton) view.findViewById(R.id.test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session session = Session.getActiveSession();
                session.closeAndClearTokenInformation();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                //you can't return on first page
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        for (int i = 0; i<Restos.getInstance().getRestos().size();i++) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("id",Restos.getInstance().getRestos().get(i).getId().toString());
            hashMap.put("img",Restos.getInstance().getRestos().get(i).getImage().toString());
            hashMap.put("titre", Restos.getInstance().getRestos().get(i).getTitre());
            hashMap.put("com", Restos.getInstance().getRestos().get(i).getAdresse());
            hashMap.put("menu", Restos.getInstance().getRestos().get(i).getMenu());
            listItem.add(hashMap);
        }
        schedule = new SimpleAdapter(getActivity(), listItem, R.layout.list_affichage,
                new String[]{"img","titre","com","menu"}, new int[]{R.id.img, R.id.titre, R.id.com, R.id.menu});

        listView.setAdapter(schedule);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                schedule.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) listView.getItemAtPosition(position);
                Log.e("ID",hashMap.get("id").toString());
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailMenuActivity.class);
                intent.putExtra("EXTRA_ID", hashMap.get("id").toString());
                startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
    }
}
