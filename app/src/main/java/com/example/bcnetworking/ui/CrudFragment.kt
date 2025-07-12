package com.example.bcnetworking.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bcnetworking.databinding.FragmentCrudBinding
import com.example.bcnetworking.di.AppModule
import com.example.bcnetworking.factory.ViewModelFactory
import com.example.bcnetworking.model.User
import com.example.bcnetworking.repository.UserRepository
import com.example.bcnetworking.viewmodel.UserViewModel
import com.example.bcnetworking.ui.adapter.UserAdapter

class CrudFragment : Fragment() {

    private var _binding: FragmentCrudBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrudBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo = UserRepository(AppModule.firestore)
        val factory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        setupRecyclerView()
        observeViewModel()

        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            if (name.isNotEmpty() && email.isNotEmpty()) {
                viewModel.addUser(name, email)
                binding.etName.text?.clear()
                binding.etEmail.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Isi semua field", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.fetchUsers()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter { user ->
            viewModel.deleteUser(user.id)
        }
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.users.observe(viewLifecycleOwner) {
            userAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
