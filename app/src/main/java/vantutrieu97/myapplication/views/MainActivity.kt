package vantutrieu97.myapplication.views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import vantutrieu97.myapplication.R
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val TAG = "CHECK"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate:${Calendar.getInstance().getTime()}")
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart:${Calendar.getInstance().getTime()}")
        navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
