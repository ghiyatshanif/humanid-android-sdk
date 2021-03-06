package com.nbs.humanidui.presentation

import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.humanid.auth.HumanIDAuth
import com.nbs.humanidui.presentation.main.MainDialogFragment
import com.nbs.humanidui.presentation.route.Route
import com.nbs.humanidui.presentation.userloggedin.UserLoggedInFragment
import com.nbs.humanidui.presentation.welcome.WelcomeDialogFragment
import com.nbs.humanidui.util.enum.LoginType
import com.nbs.humanidui.util.extensions.showToast

class HumanIDUI: WelcomeDialogFragment.OnWelcomeDialogListener,
        UserLoggedInFragment.OnButtonSwitchDeviceClickListener {

    private var supportFragmentManager: FragmentManager ?= null

    init {
        WelcomeDialogFragment.listener = this
    }

    companion object {
        val instance = HumanIDUI()
    }

    fun verifyLogin(fragmentManager: FragmentManager){
        this.supportFragmentManager = fragmentManager
        val route = Route()
        route.checkIsLoggedIn(onLoggedIn = {
            showDialogUserLoggedIn()
        }, onNotLoggedIn = {
            showDialogWelcome()
        }, onCheckInLoading = {
            showToast("Checking your authentication validity")
        })
    }

    private fun showDialogWelcome(){
        closeDialog()
        val welcomeDialogFragment = WelcomeDialogFragment.newInstance()
        supportFragmentManager?.let { welcomeDialogFragment.show(it, WelcomeDialogFragment::class.java.simpleName) }
    }

    private fun showDialogUserLoggedIn(){
        val userLoggedInFragment = UserLoggedInFragment.newInstance(this)
        supportFragmentManager?.let { userLoggedInFragment.show(it, UserLoggedInFragment::class.java.simpleName) }
    }

    private fun showDialogMain(){
        val mainDialogFragment = MainDialogFragment.newInstance()
        supportFragmentManager?.let { mainDialogFragment.show(it, MainDialogFragment::class.java.simpleName) }
    }

    override fun onButtonContinueClicked() {
        closeDialog()
        showDialogMain()
    }

    override fun onButtonSwitchDeviceClicked() {
        closeDialog()
        val mainDialogFragment = MainDialogFragment.newInstance(LoginType.SWITCH_DEVICE.type)
        supportFragmentManager?.let { mainDialogFragment.show(it, MainDialogFragment::class.java.simpleName) }
    }

    fun closeDialog(){
        supportFragmentManager?.fragments?.forEach {
            if (it is BottomSheetDialogFragment){
                it.dismissAllowingStateLoss()
            }
        }
    }

    fun isLoggedIn(): Boolean = HumanIDAuth.getInstance().currentUser != null
}