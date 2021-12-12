package com.popov.simpleweather.domain_logic

import androidx.viewbinding.ViewBinding

interface ViewItem<VB : ViewBinding> {
    fun bind(binding: VB)
}