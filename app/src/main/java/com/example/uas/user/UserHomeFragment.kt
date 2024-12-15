package com.example.uas.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.uas.database.FitRoomDatabase
import com.example.uas.databinding.FragmentUserHomeBinding
import com.example.uas.model.Fitness
import com.example.uas.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UserHomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding : FragmentUserHomeBinding? = null
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
        _binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchPhotos()
    }

    private fun fetchPhotos() {
        apiService.getAllFits().enqueue(object : Callback<List<Fitness>> {
            override fun onResponse(call: Call<List<Fitness>>, response: Response<List<Fitness>>) {
                if (response.isSuccessful) {
                    val photos = response.body()
                    if (photos != null) {
                        setupRecyclerView(photos)
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
        binding.rvFit.adapter = FitnesUserAdapter(fitnesses, FitRoomDatabase.getDatabase(requireContext()))
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}