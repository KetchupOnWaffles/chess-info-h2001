package com.example.chess

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentActivity


class CustomDialogClass(context: Context) : Dialog(context) {

    private var chrono = 0
    private val activity = context as FragmentActivity
    private val dialog = Dialog(activity)

    init {
        setCancelable(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_dialog)
    }

    fun getChrono(): Int {
        return chrono
    }

    fun setChrono(res: Int) {
        if (res in 0..99) {
            chrono = res
        }
    }

    fun showDialog() {
        val bouton1 = dialog.findViewById(R.id.bouton1) as Button
        val bouton2 = dialog.findViewById(R.id.bouton2) as Button
        val bouton3 = dialog.findViewById(R.id.bouton3) as Button
        val question = dialog.findViewById(R.id.question) as TextView
        val timer = dialog.findViewById(R.id.timer) as TextView
        val edittext = dialog.findViewById(R.id.editText) as EditText
        question.text = activity.getString(R.string.question)
        bouton1.visibility = VISIBLE
        bouton2.visibility = VISIBLE

        bouton1.setOnClickListener {
            bouton1.visibility = INVISIBLE
            bouton2.visibility = INVISIBLE
            bouton3.visibility = VISIBLE
            question.text = ""
            timer.text = activity.getString(R.string.timer)

            edittext.visibility = VISIBLE
        }
        bouton2.setOnClickListener {
            bouton1.visibility = INVISIBLE
            bouton2.visibility = INVISIBLE
            bouton3.visibility = INVISIBLE
            question.text = ""
            timer.text = ""
            edittext.isCursorVisible = false
            edittext.visibility = INVISIBLE
            dialog.dismiss()
        }
        bouton3.setOnClickListener {
            try {
                bouton3.visibility = INVISIBLE
                chrono = edittext.text.toString().toInt()
                timer.text = ""
                edittext.isClickable = false
                edittext.isCursorVisible = false
                edittext.visibility = INVISIBLE
                dialog.dismiss()
            } catch (e: NumberFormatException) {
                timer.setText("Entrer un nombre valide")
                bouton3.visibility = VISIBLE
            }
        }
        dialog.show()
    }


    fun showEndGameDialog(res: String, temps: Int) {
        activity.runOnUiThread(
            Runnable {
                val message = dialog.findViewById(R.id.message) as TextView
                val resultat = dialog.findViewById(R.id.score) as TextView
                resultat.text = res
                val duree = dialog.findViewById(R.id.duree) as TextView
                val bouton4 = dialog.findViewById(R.id.bouton4) as Button
                val minutes = (temps / 60)
                val secondes = (temps - minutes * 60)
                val formatted = String.format("%02d:%02d", minutes.toLong(), secondes.toLong())

                duree.text = activity.getString(R.string.duree, formatted)
                message.visibility = VISIBLE
                resultat.visibility = VISIBLE
                duree.visibility = VISIBLE
                bouton4.visibility = VISIBLE
                bouton4.setOnClickListener {
                    message.visibility = INVISIBLE
                    resultat.visibility = INVISIBLE
                    duree.visibility = INVISIBLE
                    bouton4.visibility = INVISIBLE
                    showDialog()
                }
                dialog.show()
            }
        )
    }
}

