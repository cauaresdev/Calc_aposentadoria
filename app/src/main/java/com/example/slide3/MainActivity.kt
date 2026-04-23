package com.example.slide3

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.slide3.databinding.ActivityMainBinding
import com.example.slide3.R


class MainActivity : AppCompatActivity() {

    // O ViewBinding substitui o findViewById, garantindo segurança de nulos e tipos.
    private lateinit var binding: ActivityMainBinding

    companion object {
        // const val: Valores conhecidos em tempo de compilação e imutáveis[cite: 24].
        const val IDADE_MINIMA_HOMEM = 65 // [cite: 7, 10]
        const val IDADE_MINIMA_MULHER = 62 // [cite: 7, 11]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarSpinner()

        binding.btnCalcular.setOnClickListener {
            calcularAposentadoria()
        }
    }

    private fun configurarSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.generos_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerGenero.adapter = adapter
        }
    }

    private fun calcularAposentadoria() {
        // val: Definida uma única vez durante a execução[cite: 23].
        val idadeInput = binding.etIdade.text.toString()

        if (idadeInput.isEmpty()) {
            binding.etIdade.error = "Campo obrigatório"
            return
        }

        val idadeAtual = if (idadeInput.isNotEmpty()) idadeInput.toInt() else 0
        val generoSelecionado = binding.spinnerGenero.selectedItem.toString()

        // var: Valor que precisa ser alterado[cite: 22].
        var tempoRestante = 0

        // Lógica de cálculo conforme a especificação
        if (generoSelecionado == "Masculino") {
            tempoRestante = IDADE_MINIMA_HOMEM - idadeAtual
        } else {
            tempoRestante = IDADE_MINIMA_MULHER - idadeAtual
        }

        exibirResultado(tempoRestante)
    }

    private fun exibirResultado(tempo: Int) {
        if (tempo <= 0) {
            // Exibe mensagem caso a idade já tenha passado o limite [cite: 13]
            binding.tvResultado.text = getString(R.string.ja_aposentado)
        } else {
            // Estima o tempo restante [cite: 12]
            binding.tvResultado.text = getString(R.string.resultado_formato, tempo)
        }
    }
}
