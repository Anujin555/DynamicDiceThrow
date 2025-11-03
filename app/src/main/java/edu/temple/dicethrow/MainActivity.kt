package edu.temple.dicethrow

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider


/*
Our DieThrow application has been refactored to move the dieRoll() logic
into the ViewModel instead of the Fragment.
Study the DieViewModel, ButtonFragment, and DieFragment classes to
see the changes.

Follow the requirements below to have this app function
in both portrait and landscape configurations.
The Activity layout files for both Portrait and Landscape are already provided
*/

class MainActivity : AppCompatActivity(), ButtonFragment.ButtonInterface {
    private var hasTwoColumns = false
    private lateinit var dieViewModel: DieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hasTwoColumns = findViewById<View>(R.id.container2) != null

        dieViewModel = ViewModelProvider(this)[DieViewModel::class.java]
        val buttonFragment = ButtonFragment()
        val dieFragment = DieFragment()

        if (savedInstanceState == null) {
            if (!hasTwoColumns) {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container1, buttonFragment)
                    .commit()
            }
            if (hasTwoColumns) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container1, buttonFragment)
                    .add(R.id.container2, dieFragment)
                    .commit()
            }
        }

        dieViewModel.getDieRoll().observe(this) {
            if (!hasTwoColumns) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container1, dieFragment)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
            }
            if (hasTwoColumns) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container1, buttonFragment)
                    .replace(R.id.container2, dieFragment)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
            }

        }
    }

    override fun buttonClicked() {
        dieViewModel.rollDie()
    }

    // This callback function gets invoked when the child Fragment invokes it
        // Remember to place Fragment transactions on BackStack so then can be reversed

}

