package com.beachpartnerllc.beachpartner.user.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.MoreInfoBinding
import com.beachpartnerllc.beachpartner.databinding.TopFinishesBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.user.auth.AuthViewModel
import kotlinx.android.synthetic.main.item_top_finishes.view.*
import javax.inject.Inject


class MoreInfoFragment : BaseFragment() {
    private lateinit var binding: MoreInfoBinding
    private lateinit var vm: AuthViewModel
    @Inject lateinit var factory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflater.bind(R.layout.fragment_more_info, container)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = getViewModel(factory)
        binding.vm = vm
        binding.handler = this
        binding.setLifecycleOwner(viewLifecycleOwner)
        vm.isTopFinishesSet.observe(viewLifecycleOwner, Observer {
            if (it == true) readTopFinishes()
        })
        vm.athlete.observe(viewLifecycleOwner, Observer {
            it.topFinishes.forEachIndexed { index, s ->
                if (index >= 1) addTopFinish(s)
            }
        })
    }

    fun getItemView(): Int = R.layout.simple_spinner_item_1line

    fun addTopFinish(topFinish: String? = null) {
        val inflater = LayoutInflater.from(context)
        val itemBinding: TopFinishesBinding =
            inflater.bind(R.layout.item_top_finishes, this.binding.topFinishesLL)
        itemBinding.handler = this
        itemBinding.vm = vm
        itemBinding.topFinishesAET.setText(topFinish)
        binding.topFinishesLL.addView(itemBinding.root)
        binding.topFinishesLL.invalidate()
        val child = this.binding.topFinishesLL.getChildAt(this.binding.topFinishesLL.childCount - 1)
        child.requestFocus()
        child.animate().translationY(-10f)
        itemBinding.setLifecycleOwner(viewLifecycleOwner)
        vm.addTopFinish()
    }

    fun removeTopFinish(view: View) {
        view.topFinishesAET.text = null
        binding.topFinishesLL.removeView(view)
        vm.removeTopFinish()
    }

    private fun readTopFinishes() {
        val topFinishes = ArrayList<String>()
        topFinishes.add(binding.editTopFinishes1.text.toString())
        binding.topFinishesLL.forEach {
            val topFinish = it.findViewById<AppCompatEditText>(R.id.topFinishesAET).text.toString()
            topFinishes.add(topFinish)
            vm.setTopFinishes(topFinishes)
        }
    }
}
