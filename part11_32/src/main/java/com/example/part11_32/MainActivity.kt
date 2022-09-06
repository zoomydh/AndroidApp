package com.example.part11_32

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.part11_32.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {

    val typeTest = TypeTest()
    val controlTest = ControlTest()
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        viewBinding.typeTestBtn.setOnClickListener(this)
        viewBinding.controlTestBtn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if ( p0 == viewBinding.typeTestBtn) {
            val string = "${typeTest.testType()} \n ${typeTest.testArray()} \n ${typeTest.testAny("Hello")}"
            viewBinding.resultTextView.setText(string)
        } else if (p0 == viewBinding.controlTestBtn) {
            val string = "${controlTest.testIf(25)} \n ${controlTest.testWhen("http://www.google.com")} \n ${controlTest.testFor()}"
            viewBinding.resultTextView.setText(string)
        }
    }
}