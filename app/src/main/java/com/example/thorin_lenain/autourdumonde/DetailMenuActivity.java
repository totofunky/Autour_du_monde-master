package com.example.thorin_lenain.autourdumonde;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thorin_lenain.autourdumonde.model.Restos;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.widget.WebDialog;

/**
 * Created by Thorin_LeNain on 30/10/2014.
 */
public class DetailMenuActivity extends Activity {
    private TextView titre_resto;
    private TextView menu;
    private ImageView img;
    private TextView address;
    private String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        titre_resto = (TextView) findViewById(R.id.titre_resto);
        menu = (TextView) findViewById(R.id.menu);
        address = (TextView) findViewById(R.id.address);
        img = (ImageView) findViewById(R.id.img);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("EXTRA_ID");

            if (extras != null) {
                titre_resto.setText(Restos.getInstance().getRestoById(id).getTitre().toString());
                menu.setText(Restos.getInstance().getRestoById(id).getMenu().toString());
                address.setText(Restos.getInstance().getRestoById(id).getAdresse().toString());
                img.setImageResource((Integer) Restos.getInstance().getRestoById(id).getImage());
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem myActionMenuItem;
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.plus:
                Bundle params = new Bundle();
                params.putString("message", "Veux tu venir au restaurant");

                WebDialog requestsDialog = (
                        new WebDialog.RequestsDialogBuilder(DetailMenuActivity.this,
                                Session.getActiveSession(),
                                params))
                        .setOnCompleteListener(new WebDialog.OnCompleteListener() {
                            @Override
                            public void onComplete(Bundle values,
                                                   FacebookException error) {
                                if (error != null) {
                                    if (error instanceof FacebookOperationCanceledException) {
                                        Toast.makeText(DetailMenuActivity.this.getApplicationContext(),
                                                "Request cancelled",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(DetailMenuActivity.this.getApplicationContext(),
                                                "Network Error",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    final String requestId = values.getString("request");
                                    if (requestId != null) {
                                        Intent intent = new Intent();
                                        intent.setClass(DetailMenuActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("LAT", Restos.getInstance().getRestoById(id).getLatLng().latitude);
                                        intent.putExtra("LON", Restos.getInstance().getRestoById(id).getLatLng().longitude);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(DetailMenuActivity.this.getApplicationContext(),
                                                "Request cancelled",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .build();
                requestsDialog.show();
                return true;
            case R.id.go:
                Intent intent = new Intent();
                intent.setClass(DetailMenuActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("LAT", Restos.getInstance().getRestoById(id).getLatLng().latitude);
                intent.putExtra("LON", Restos.getInstance().getRestoById(id).getLatLng().longitude);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
