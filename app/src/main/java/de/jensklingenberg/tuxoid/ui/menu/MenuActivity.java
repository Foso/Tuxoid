package de.jensklingenberg.tuxoid.ui.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import de.jensklingenberg.tuxoid.R;
import de.jensklingenberg.tuxoid.ui.AnleitungActivity;
import de.jensklingenberg.tuxoid.ui.MainActivity;
import de.jensklingenberg.tuxoid.ui.MainContract;

public class MenuActivity extends Activity implements MenuContract.View {

    ArrayList<Button> btnarr;
    Button btnAnleitung;
    LinearLayout ll;
    MenuContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        presenter = new MenuPresenter(this);
        presenter.onCreate();

    }


    @Override
    public void setData() {
        ll = (LinearLayout) findViewById(R.id.ll2);

        btnarr = new ArrayList<Button>();
        btnAnleitung = new Button(this);
        btnAnleitung.setText("Anleitung");
        btnAnleitung.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AnleitungActivity.class);
            startActivity(intent);
        });
        ll.addView(btnAnleitung);
        for (int i = 0; i < 20; i++) {

            Button btnEntry = new Button(this);
            btnEntry.setText("Level" + i);


            final int j = i;

            btnEntry.setOnClickListener(v -> {
                int x = j;
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra(MainActivity.ARG_LEVEL, x);
                startActivity(intent);


            });


            ll.addView(btnEntry);
        }
    }
}
