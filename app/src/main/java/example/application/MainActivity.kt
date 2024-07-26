package example.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import example.application.view.registration.RegistrationDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.search-> {
                    navController.navigate(R.id.landingFragment2)
                    true
                }
                R.id.saved -> {
                    navController.navigate(R.id.savedFragment)
                    true
                }
                R.id.bookings -> {
                    navController.navigate(R.id.serviceFragment)
                    true
                }
                R.id.profile -> {
                    showRegistrationDialog()
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun showRegistrationDialog() {
        val dialogFragment = RegistrationDialog()
        dialogFragment.show(supportFragmentManager, "RegistrationDialog")

    }
}