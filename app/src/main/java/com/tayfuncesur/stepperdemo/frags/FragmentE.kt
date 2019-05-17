package com.tayfuncesur.stepperdemo.frags

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.tayfuncesur.stepper.Stepper
import com.tayfuncesur.stepperdemo.R
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.fragment_e.*
import kotlinx.android.synthetic.main.fragment_e.backArrow

class FragmentE : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_e, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PushDownAnim.setPushDownAnimTo(progress).setScale(PushDownAnim.MODE_STATIC_DP, 5F).setOnClickListener {
            activity?.findViewById<Stepper>(R.id.Stepper)?.progress(3)?.addOnCompleteListener {
                activity?.findViewById<View>(R.id.StepperView)?.background =
                    ContextCompat.getDrawable(context!!, R.drawable.success_gradient)
            }
        }



        PushDownAnim.setPushDownAnimTo(backArrow).setScale(PushDownAnim.MODE_STATIC_DP, 5F).setOnClickListener {
            view.findNavController().popBackStack()
            activity?.findViewById<Stepper>(R.id.Stepper)?.stop()
            activity?.findViewById<Stepper>(R.id.Stepper)?.back()
        }
        PushDownAnim.setPushDownAnimTo(progressStop).setScale(PushDownAnim.MODE_STATIC_DP, 5F).setOnClickListener {
            activity?.findViewById<Stepper>(R.id.Stepper)?.stop()
        }
    }
}
