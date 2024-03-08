package com.kerolosatya.currencyconverter.ui.base

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.kerolosatya.currencyconverter.R

open class BaseFragment : Fragment() {
    var dp = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dp = resources.displayMetrics.density

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }


    fun showToastSnack(word: String?, flag: Boolean) {
        val layout = LayoutInflater.from(requireActivity()).inflate(R.layout.snack_bar_layout, null, false)
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
        text.setTextColor(requireActivity().resources.getColor(R.color.white))
        val txtType = layout.findViewById<TextView>(R.id.txt_type)
        txtType.text = if (flag) "Error" else "Success"

        val toast = Toast(requireActivity())
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 50)

        val toastLayout = LinearLayout(requireActivity())


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
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(isCancelable)
        return builder
    }

    fun hideStatusBar() {
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    fun navigateTo(id: Int, bundle: Bundle?) {
        val navBuilder = NavOptions.Builder()
        Navigation.findNavController(requireView()).navigate(id, bundle, navBuilder.build())
    }

    fun navigateBack() {
        val flag = Navigation.findNavController(requireView()).popBackStack()
        if (!flag) {
            requireActivity().finish()
        }
    }


    fun validateIncreaseQuantity(qty: Int, max: Int): Boolean {
        return (qty < max)
    }

    fun validateDecreaseQuantity(qty: Int): Boolean {
        return (qty > 0)
    }

    fun showProgressDialog(view: View) {
        view.visibility = View.VISIBLE
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun hideProgressDialog(view: View) {
        view.visibility = View.GONE
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

}