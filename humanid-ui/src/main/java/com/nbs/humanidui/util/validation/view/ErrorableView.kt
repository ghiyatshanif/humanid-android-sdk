package com.nbs.humanidui.util.validation.view

interface ErrorableView {
    fun showError(errorMessage: String)
    fun hideError()
    fun isErrorShowing(): Boolean
}