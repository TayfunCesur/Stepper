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
import kotlinx.android.synthetic.main.fragment_b.*
import kotlinx.android.synthetic.main.fragment_c.*
import kotlinx.android.synthetic.main.fragment_c.backArrow
import kotlinx.android.synthetic.main.fragment_d.*

class FragmentD : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_d, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PushDownAnim.setPushDownAnimTo(nextToE).setScale(PushDownAnim.MODE_STATIC_DP,5F).setOnClickListener {
            view.findNavController().navigate(R.id.fragmentDtoE)
            activity?.findViewById<Stepper>(R.id.Stepper)?.forward()
            activity?.findViewById<Stepper>(R.id.StepperRtl)?.forward()
            activity?.findViewById<Stepper>(R.id.StepperAuto)?.forward()
        }
        PushDownAnim.setPushDownAnimTo(backArrow).setScale(PushDownAnim.MODE_STATIC_DP,5F).setOnClickListener {
            view.findNavController().popBackStack()
            activity?.findViewById<Stepper>(R.id.Stepper)?.back()
            activity?.findViewById<Stepper>(R.id.StepperRtl)?.back()
            activity?.findViewById<Stepper>(R.id.StepperAuto)?.back()
        }
    }
}
