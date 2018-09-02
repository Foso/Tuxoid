package jensklingenberg.de.tuxoid.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import de.jensklingenberg.tuxoid.R;
import jensklingenberg.de.tuxoid.MainActivity;

public class menuActivity extends Activity {

    Button[] btnarr;
    Button btnAnleitung;
    LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        ll = (LinearLayout) findViewById(R.id.ll2);

        btnarr = new Button[20];
        btnAnleitung = new Button(this);
        btnAnleitung.setText("Anleitung");
        btnAnleitung.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AnleitungActivity.class);
                startActivity(intent);
            }
        });
        ll.addView(btnAnleitung);
        for (int i = 0; i < btnarr.length; i++) {

            btnarr[i] = new Button(this);
            btnarr[i].setText("Level" + i);


            final int j = i;

            btnarr[i].setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    int x = j;
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra(MainActivity.ARG_LEVEL, x);
                    startActivity(intent);


                }
            });


            ll.addView(btnarr[i]);
        }

    }


}
