package com.example.androidcalculator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object NavigationManager {
    private fun placeFragement(fm: FragmentManager, fragment: Fragment) {
        val transition = fm.beginTransaction()
        transition.replace(R.id.frame, fragment)
        transition.addToBackStack(null)
        transition.commit()
    }

    fun goToCalculatorFragment (fm: FragmentManager) {
        placeFragement(fm,CalculatorFragment())
    }

    fun goToHistoryFragment (fm: FragmentManager) {
        placeFragement(fm, HistoryFragment())
    }
}