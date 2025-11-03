package edu.temple.dicethrow

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity(), ButtonFragment.ButtonInterface {

    private var hasTwoColumns = false
    private lateinit var dieViewModel: DieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hasTwoColumns = findViewById<View>(R.id.container2) != null
        dieViewModel = ViewModelProvider(this)[DieViewModel::class.java]

        if (savedInstanceState == null) {
            if (!hasTwoColumns) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container1, ButtonFragment())
                    .commit()
            }
            else {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container1, ButtonFragment())
                    .add(R.id.container2, DieFragment())
                    .commit()
            }
        }

        dieViewModel.getDieRoll().observe(this) {
            if (!hasTwoColumns) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container1, DieFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
            }

        }
    }


    override fun buttonClicked() {
        dieViewModel.rollDie()
    }
}
