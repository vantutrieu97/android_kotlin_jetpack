package vantutrieu97.myapplication.views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        navController = findNavController(R.id.nav_host_fragment)
        findViewById<BottomNavigationView>(R.id.bottom_navigation_view).setupWithNavController(
            navController
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
