package com.example.consumocombustivel

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.consumocombustivel.databinding.ActivityMainBinding
import com.nubank.nubankclone.Util.MascaraMonetaria


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCalulate.setOnClickListener(this)

        binding.buttonReset.setOnClickListener { reset() }

        var mascaraPrice = findViewById<EditText>(R.id.edit_price)
        mascaraPrice.addTextChangedListener(MascaraMonetaria(mascaraPrice))

        var mascaraKM = findViewById<EditText>(R.id.edit_Distance)
        mascaraKM.addTextChangedListener(MascaraMonetaria(mascaraKM))

    }

    override fun onClick(view: View) {
        if (view.id == R.id.button_calulate) {
            calculate()
        }
    }

    private fun isValid(): Boolean {
        return (binding.editDistance.text.toString() != ""
                && binding.editPrice.text.toString() != ""
                && binding.editAutonomy.text.toString() != ""
                && binding.editAutonomy.text.toString().toFloat() != 0f)
    }

    private fun calculate() {
        if (isValid()) {
            val distance = binding.editDistance.text.toString().replace(",", ".").toFloat()
            val price = binding.editPrice.text.toString().replace(",", ".").toFloat()
            val autonomy = binding.editAutonomy.text.toString().replace(",", ".").toFloat()

            val totalValue = (distance * price) / autonomy
            binding.textTotalValue.text = "R$ ${"%.2f".format(totalValue)}"
            Toast.makeText(this, "Calculado com sucesso", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.validation_fill_all_fields, Toast.LENGTH_SHORT).show()
        }
    }

    fun reset() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(R.string.titleAlert)
            .setMessage(R.string.alertText)
            .setPositiveButton("Ok") { dialogInterface, i ->
                binding.editDistance.setText("")
                binding.editPrice.setText("")
                binding.editAutonomy.setText("")
                binding.textTotalValue.text = ""
                Toast.makeText(this, "Dados resetados", Toast.LENGTH_SHORT).show()
            }
        alertDialog.setNegativeButton("Cancelar") { dialogInterface, i ->

        }
        val dialog = alertDialog.create()
        dialog.show()
    }
}
