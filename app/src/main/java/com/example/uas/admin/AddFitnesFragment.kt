package com.example.uas.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.uas.R
import com.example.uas.data.request.FitRequest
import com.example.uas.data.response.FitResponse
import com.example.uas.databinding.FragmentAddFitnesBinding
import com.example.uas.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddFitnesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding : FragmentAddFitnesBinding? = null
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
        _binding = FragmentAddFitnesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            addButton.setOnClickListener {
                val name = name.text.toString()
                val category = category.text.toString()
                val durORrep = durORrep.text.toString()

                if (name.isNotEmpty() && category.isNotEmpty() && durORrep.isNotEmpty()) {
                    val request = FitRequest(name, category, durORrep)
                    addFit(request)
                    findNavController().navigate(R.id.homeAdminFragment)
                } else {
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addFit(request : FitRequest) {
        apiService.createFit(request).enqueue(object : Callback<FitResponse> {
            override fun onResponse(call: Call<FitResponse>, response: Response<FitResponse>) {
                if (response.isSuccessful) {
                    val fit = response.body()
                    if (fit != null) {
                        Toast.makeText(requireContext(), "Fitnes added", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FitResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFitnesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}