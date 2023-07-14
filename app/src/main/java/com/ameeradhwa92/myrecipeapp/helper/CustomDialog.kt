package com.ameeradhwa92.myrecipeapp.helper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.ameeradhwa92.myrecipeapp.MainActivity
import com.ameeradhwa92.myrecipeapp.R
import com.ameeradhwa92.myrecipeapp.RecipeListActivity

class CustomDialog : DialogFragment() {
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var btnClosePopup: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.custom_dialog, container)

        sharedPreference = activity?.getSharedPreferences("USER_ACCOUNT", Context.MODE_PRIVATE)!!
        editor = sharedPreference.edit()

        this.dialog?.requestWindowFeature(STYLE_NO_TITLE)
        btnClosePopup = view.findViewById<View>(R.id.btnClosePopup) as Button
        btnClosePopup!!.setOnClickListener {
            editor.clear()
            editor.apply()

            this.dialog?.dismiss()

            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}