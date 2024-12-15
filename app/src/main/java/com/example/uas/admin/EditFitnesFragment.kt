package com.example.uas.admin

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.uas.data.request.FitRequest
import com.example.uas.data.response.FitResponse
import com.example.uas.databinding.FragmentEditFitnesBinding
import com.example.uas.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EditFitnesFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding : FragmentEditFitnesBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentEditFitnesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _id = arguments?.getString("id")
        binding.name.text = Editable.Factory.getInstance().newEditable(arguments?.getString("name"))
        binding.category.text = Editable.Factory.getInstance().newEditable(arguments?.getString("category"))
        binding.durORrep.text = Editable.Factory.getInstance().newEditable(arguments?.getString("durORrep"))

        binding.editButton.setOnClickListener {
            updateFit(_id!!)
            findNavController().navigateUp()
        }

    }

    private fun updateFit(id: String){
        val request = FitRequest(binding.name.text.toString(), binding.category.text.toString(), binding.durORrep.text.toString())

        ApiClient.getInstance().updateFit(id, request).enqueue(object : Callback<FitResponse> {
            override fun onResponse(call: Call<FitResponse>, response: Response<FitResponse>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null && body.success){
                        findNavController().navigateUp()
                    } else {
                        Toast.makeText(requireContext(), body?.message ?: "Failed to Update", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to Update Fitnes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FitResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditFitnesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}