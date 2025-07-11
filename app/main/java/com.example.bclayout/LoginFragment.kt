package com.example.bclayout

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bclayout.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _b: FragmentLoginBinding? = null
    private val b get() = _b!!
    override fun onViewCreated(v: View, s: Bundle?) {
        _b = FragmentLoginBinding.bind(v)
        super.onViewCreated(v, s)
        b.btnLogin.setOnClickListener {
            val e = b.etEmail.text.toString().trim()
            val p = b.etPassword.text.toString().trim()
            if (e.isEmpty()) b.tilEmail.error = "Email wajib"
            else if (p.isEmpty()) b.tilPassword.error = "Pass wajib"
            else findNavController().navigateUp()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
