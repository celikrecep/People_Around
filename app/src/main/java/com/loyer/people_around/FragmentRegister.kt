package com.loyer.people_around

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by loyer on 22.02.2018.
 */
class FragmentRegister: Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        var view: View = inflater!!.inflate(R.layout.fragment_register,container,false)
        return view
    }


}