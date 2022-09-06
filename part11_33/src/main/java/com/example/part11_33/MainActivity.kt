package com.example.part11_33

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.part11_33.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        viewBinding.inheritanceTestBtn.setOnClickListener(this)
        viewBinding.propertyTestBtn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0 == viewBinding.inheritanceTestBtn) {
            var customer = Customer()
            customer.someFun()
        } else if (p0 == viewBinding.propertyTestBtn) {
            var obj = PropertyTest()
            obj.greeting="yoon"
            viewBinding.resultTextView.setText("Property \n greeting : ${obj.greeting}, name : ${obj.name}")
        }
    }
}