package cf.soisi.tankmesser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_create).setOnClickListener(this::listenOnCreate);
        findViewById(R.id.button_show).setOnClickListener(this::listenOnShow);
        findViewById(R.id.button_delete).setOnClickListener(this::listenOnDelete);
    }

    private void listenOnCreate(View view) {
        Intent intent = new Intent(this,CreateEntryActivity.class);
        startActivity(intent);
    }

    private void listenOnShow(View view) {
        Intent intent = new Intent(this,CreateEntryActivity.class);
        startActivity(intent);
    }

    private void listenOnDelete(View view) {
        Intent intent = new Intent(this,CreateEntryActivity.class);
        startActivity(intent);
    }
}