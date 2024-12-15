package com.example.uas.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.uas.database.FitRoomDatabase
import com.example.uas.databinding.FragmentUserBookmarkBinding
import com.example.uas.model.Fitness

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private var _binding : FragmentUserBookmarkBinding? = null
private val binding get() = _binding!!
private var database : FitRoomDatabase? = null

class UserBookmarkFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        _binding = FragmentUserBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FitRoomDatabase.getDatabase(requireContext())

        binding.rvGambar.layoutManager = GridLayoutManager(requireContext(), 1)

        database?.fitDao()?.getAllFits()?.observe(viewLifecycleOwner) { fitnes ->
            val fav = fitnes.map { fit ->
                Fitness(
                    id = fit._id.toString(),
                    name = fit.name,
                    category = fit.category,
                    durORrep = fit.durORrep
                )
            }

            val adapter = BookmarkAdapter(fav, database)
            binding.rvGambar.adapter = adapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserBookmarkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}