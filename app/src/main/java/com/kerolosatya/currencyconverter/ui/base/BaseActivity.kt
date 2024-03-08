package com.kerolosatya.currencyconverter.ui.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kerolosatya.currencyconverter.R


open class BaseActivity : AppCompatActivity() {
    var dp = 0f

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR)
        super.onCreate(savedInstanceState, persistentState)
        dp = resources.displayMetrics.density

    }

    override fun onResume() {
        super.onResume()
    }

    fun showToastSnack(word: String?, flag: Boolean) {
        val layout = LayoutInflater.from(baseContext).inflate(R.layout.snack_bar_layout, null, false)
        layout.setBackgroundResource(
            if (flag)
                R.drawable.snack_background_error
            else
                R.drawable.snack_background_success
        )

        val image = layout.findViewById<ImageView>(R.id.image)
        image.setImageResource(if (flag) R.drawable.ic_error else R.drawable.ic_success)
        val text = layout.findViewById<TextView>(R.id.text)
        text.text = word
        text.setTextColor(resources.getColor(R.color.white))
        val txtType = layout.findViewById<TextView>(R.id.txt_type)
        txtType.text = if (flag) "Error" else "Success"

        val toast = Toast(baseContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 50)

        val toastLayout = LinearLayout(baseContext)


        toastLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        toastLayout.orientation = LinearLayout.HORIZONTAL
        toastLayout.setBackgroundResource(
            if (flag) R.drawable.snack_background_error
            else R.drawable.snack_background_success
        )
        toastLayout.addView(layout)

        toast.view = toastLayout
        toast.show()
    }


    fun showDialog(title: String, message: String, isCancelable: Boolean): AlertDialog.Builder {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@BaseActivity)

        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(isCancelable)
        return builder
    }

    fun showProgressDialog(view: View) {
        view.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun hideProgressDialog(view: View) {
        view.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

}