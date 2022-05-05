package easv.oe.dicecup2

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.SQLOutput
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val TAG: String = "xyz"




    private val mRandomGenerator = Random()
    private var currentDice = 1
    private var index = 1
    private var mHistory = ArrayList<String>()
   // private var historyActivity = HistoryActivity()
    //private var diceDrawer = DrawDice()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRoll.setOnClickListener { v -> onClickRoll() }
        plusBtn.setOnClickListener { c -> addDie()}
        var btn_history = findViewById(R.id.btnHistory) as Button;
        btn_history.setOnClickListener {
            val intent = Intent(this,HistoryActivity::class.java);
            intent.putExtra("HISTORY_DATA",mHistory);
            startActivityForResult(intent,0);
        }
        minusBtn.setOnClickListener { x -> removeDie()}
        Log.d(TAG, "OnCreate")



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // If you have multiple activities returning results then you should include unique request codes for each
        if (requestCode == 0) {

            // The result code from the activity started using startActivityForResults
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    mHistory = data.getStringArrayListExtra("HISTORY") as ArrayList<String>
                }
            }
        }
    }

    private fun removeDie() {
        if(currentDice > 1){
            currentDice -= 1
            diceAmount.text = currentDice.toString()
        }
    }

    private fun addDie() {
        //Integer.getInteger(t)
        if(currentDice < 6) {
            currentDice += 1
            diceAmount.text = currentDice.toString()
        }
    }

    private fun changeDice (currentDice: Int, currentRoll: Int){

        var image: ImageView? = null
        if (currentDice == 1){
             image =  findViewById(R.id.imageView1) as ImageView;
        }
        else if (currentDice == 2){
             image =  findViewById(R.id.imageView2) as ImageView;
        }
        else if (currentDice == 3){
             image =  findViewById(R.id.imageView3) as ImageView;
        }
        else if (currentDice == 4){
             image =  findViewById(R.id.imageView4) as ImageView;
        }
        else if (currentDice == 5){
             image =  findViewById(R.id.imageView5) as ImageView;
        }
        else if (currentDice == 6){
             image =  findViewById(R.id.imageView6) as ImageView;
        }

        var uri = "@drawable/dice"+ currentRoll
        var imageResource = resources.getIdentifier(uri, null, packageName)
        var res = resources.getDrawable(imageResource) as Drawable
        if (image != null) {
            image.setImageDrawable(res)
        }

   }

   private fun onClickRoll() {
      var img1 =  findViewById(R.id.imageView1) as ImageView
       var img2 =  findViewById(R.id.imageView2) as ImageView
       var img3 =  findViewById(R.id.imageView3) as ImageView
       var img4 =  findViewById(R.id.imageView4) as ImageView
       var img5 =  findViewById(R.id.imageView5) as ImageView
       var img6 =  findViewById(R.id.imageView6) as ImageView

       img1.setImageDrawable(null)
       img2.setImageDrawable(null)
       img3.setImageDrawable(null)
       img4.setImageDrawable(null)
       img5.setImageDrawable(null)
       img6.setImageDrawable(null)
       val currentTimestamp: String = SimpleDateFormat("MM - dd HH:mm:ss").format(Date())
       var newAddition = index.toString() + " | " + currentTimestamp + " | "
        for (i in 1.. currentDice) {
            var newNumber = mRandomGenerator.nextInt((5) + 1) + 1;
            changeDice(i,newNumber)
            if(i < currentDice){
                newAddition = newAddition + newNumber.toString() + " - "

            }
            else{
                newAddition = newAddition + newNumber.toString()
            }
            print(newNumber)
        }
       mHistory.add(newAddition)
       index++
    }

    override fun onSaveInstanceState(outState: Bundle) { // Here You have to save count value
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")

        outState.putStringArrayList("HISTORY", mHistory)
        outState.putInt("CURRENTDICE", currentDice)
        outState.putInt("INDEX", index)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) { // Here You have to restore count value
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")

        mHistory = savedInstanceState.getStringArrayList("HISTORY") as ArrayList<String>
        currentDice = savedInstanceState.getInt("CURRENTDICE")
        index = savedInstanceState.getInt("INDEX")

    }

}
