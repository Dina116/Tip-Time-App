package com.training.tiptimeapp

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.training.tiptimeapp.databinding.ActivityMainBinding
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    var total = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        total = savedInstanceState?.getDouble("total") ?: 0.0
        //handle the design
        binding.tipAmountTv.text = total.toString()

        binding.calculateBtn.setOnClickListener {
            val cost = binding.serviceEt.text.toString().toDouble()
            val checked = binding.radioGroup.checkedRadioButtonId

            val tip = when (checked) {
                R.id.amazing_rb -> 0.2
                R.id.good_rb -> 0.18
                else -> 0.15
            }

            total = cost * tip

            if (binding.roundTipSwitch.isChecked) {
                total = ceil(total)
            }

            binding.tipAmountTv.text = getString(R.string.total_number)
            Snackbar
                .make(binding.root,"Reset",BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("proceed"){
                    binding.serviceEt.text?.clear()
                    binding.radioGroup.check(R.id.amazing_rb)
                    binding.tipAmountTv.setText(getString(R.string.tip_amount))
                    true.also { binding.roundTipSwitch.isChecked = it }
                }
                .setBackgroundTint(getColor(android.R.color.background_dark))
                .setTextColor(getColor(android.R.color.white))
                .show()

        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("total", total)
    }
}