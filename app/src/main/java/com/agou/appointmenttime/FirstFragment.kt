package com.agou.appointmenttime

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.agou.appointmenttime.databinding.FragmentFirstBinding
import java.util.HashMap

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }
    private  val TAG = "FirstFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.appointmentTimeView?.setOnPageChanged {start,end->

            Log.d(TAG, "onViewCreated: +_start $start end $end")
        }
        _binding?.appointmentTimeView?.setOnClickListener { calendarView, cell ->

            Log.d(TAG, "onViewCreated: +cell end ${cell.date.getKey()}  ${cell.date.status} ")
        }
    }

    override fun onResume() {
        super.onResume()
        val map: MutableMap<String, List<String>> = HashMap()
        var  array =ArrayList<String>()
        array.add("8:00-9:00-true")
        array.add("10:00-12:00-false")
        map["2021-9-29-1"] = array
        var  array2 =ArrayList<String>()
        array2.add("8:00-9:00-true")
        array2.add("10:00-12:00-true")
        array2.add("10:00-12:00-true")
        map["2021-9-29-2"] = array
        map["2021-10-2-2"] = array2
        map["2021-10-3-3"] =array
        _binding?.appointmentTimeView?.updateStatus(map)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}