package cf.soisi.labor_02_1_listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.soisi.labor_02_1_listview.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String KLASSE_EXTRA = "cf.soisi.labor_02_1_listview.main.extra";
        private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lvKlassen);
        List<Klasse> klassen = Klasse.readKlassen(this.getResources().openRawResource(R.raw.schueler));
        klassen.sort(null);
        ArrayAdapter<Klasse> mAdapter = new ArrayAdapter<Klasse>(this, android.R.layout.simple_list_item_1,klassen);

        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener((adapterView, view, pos, id) -> itemClickListener(mAdapter.getItem(pos)));
    }

    private void itemClickListener(Klasse klasse) {
        Intent intent = new Intent(this,SchuelerListActivity.class);
        intent.putExtra(KLASSE_EXTRA,klasse);
        startActivity(intent);
    }
}