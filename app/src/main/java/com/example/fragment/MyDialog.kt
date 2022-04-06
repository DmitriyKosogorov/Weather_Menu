package com.example.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import java.util.*
import android.widget.Toast

class MyDialog(val ctx: Context): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var choice = 0
        Log.d("mytag", "creating dialog")
        val langs = arrayOf("Java", "Kotin", "C++", "Python")
        return ctx.let { AlertDialog.Builder(it).setMessage("Выберите Дизайн").
        setSingleChoiceItems(langs, 1, {dialog, which -> choice = which}).
        setPositiveButton("Ok", MyListener()).
        create()
        }
        return super.onCreateDialog(savedInstanceState)
    }
}