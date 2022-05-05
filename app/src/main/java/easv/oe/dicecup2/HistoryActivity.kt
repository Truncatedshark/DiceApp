package easv.oe.dicecup2

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    private var history = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        var arrayAdapter = setupHistory()

        btnBack.setOnClickListener {back();}

        btnClearHistory.setOnClickListener {clearHistory(arrayAdapter);}

    }

    private fun setupHistory(): ArrayAdapter<String> {
    var listView = findViewById(R.id.lvHistory) as ListView;

        history = intent.getStringArrayListExtra("HISTORY_DATA") as ArrayList<String>

        var arrayAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            history

        )
        listView.adapter = arrayAdapter

        arrayAdapter.notifyDataSetChanged()
        return arrayAdapter;
    }

    private fun clearHistory(arrayAdapter: ArrayAdapter<String>) {
        history.clear();
        arrayAdapter.notifyDataSetChanged()
    }

    private fun back() {
       var intent = Intent()
       intent.putExtra("HISTORY",history)
    setResult(RESULT_OK,intent);
        finish();
    }
}