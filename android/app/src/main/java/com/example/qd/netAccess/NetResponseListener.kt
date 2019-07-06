package com.example.qd.netAccess

interface NetResponseListener<T> {
    fun process(t:T)
}