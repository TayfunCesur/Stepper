package com.tayfuncesur.stepperdemo.frags

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.tayfuncesur.stepper.Stepper
import com.tayfuncesur.stepperdemo.R
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.fragment_a.*

class FragmentA : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PushDownAnim.setPushDownAnimTo(nextToB).setScale(PushDownAnim.MODE_STATIC_DP,5F).setOnClickListener {
            view.findNavController().navigate(R.id.fragmentAtoB)
            activity?.findViewById<Stepper>(R.id.Stepper)?.forward()
            activity?.findViewById<Stepper>(R.id.StepperRtl)?.forward()
            activity?.findViewById<Stepper>(R.id.StepperAuto)?.forward()
        }
    }
}
