package com.beachpartnerllc.beachpartner.user.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.beachpartnerllc.beachpartner.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AthleteProfileFragment : Fragment() {
	// TODO: Rename and change types of parameters
	private lateinit var pager: ViewPager
	private lateinit var tabs: SlidingTab
	private var slideAdapter: SliderAdapter? = null
	private val titles = arrayOf<CharSequence>("Basic Information", "More Information")
	
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

	}
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		val view:View = inflater.inflate(R.layout.fragment_athlete_profile, container, false)
		initActivity(view)
		return view
	}
	
	fun initActivity(view : View){
		tabs = view.findViewById(R.id.tabs)
		pager = view.findViewById(R.id.pager)
		slideAdapter = SliderAdapter(childFragmentManager,2,titles)
		pager.adapter = slideAdapter
		tabs.setViewPager(pager)
		tabs.setDistributeEvenly(true)
		slideAdapter!!.notifyDataSetChanged()
		tabs.setViewPager(pager)
		
		
	}
}
