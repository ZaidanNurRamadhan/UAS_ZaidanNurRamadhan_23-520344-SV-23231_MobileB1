package com.example.uas.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.uas.databinding.FragmentHomeAdminBinding
import com.example.uas.model.Fitness
import com.example.uas.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeAdminFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding : FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!
    private var apiService = ApiClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchFits()
    }

    private fun fetchFits() {
        apiService.getAllFits().enqueue(object : Callback<List<Fitness>> {
            override fun onResponse(call: Call<List<Fitness>>, response: Response<List<Fitness>>) {
                if (response.isSuccessful) {
                    val fit = response.body()
                    if (fit != null) {
                        setupRecyclerView(fit)
                    } else{
                        Toast.makeText(requireContext(), "Data Kosong", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<Fitness>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error : ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(fitnesses : List<Fitness>) {
        binding.rvFit.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.rvFit.adapter = FitAdapter(fitnesses) { fitnes ->
            deleteFit(fitnes)
        }
    }

    private fun deleteFit(fitness: Fitness) {
        apiService.deleteFit(fitness.id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Fit deleted successfully", Toast.LENGTH_SHORT).show()
                    fetchFits() // Refresh the list after deletion
                } else {
                    Toast.makeText(requireContext(), "Failed to delete fitnes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeAdminFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}