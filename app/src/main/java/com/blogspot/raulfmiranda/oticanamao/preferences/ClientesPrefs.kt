package com.blogspot.raulfmiranda.oticanamao.preferences

import android.content.Context
import com.blogspot.raulfmiranda.oticanamao.dataclasses.Cliente
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ClientesPrefs {
    companion object {
        fun recuperarClientes(context: Context?): MutableList<Cliente> {
            val prefs = context?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val json = prefs?.getString("clientes", "")

            if(json != null && json.length > 0) {
                val clientes: MutableList<Cliente> = Gson().fromJson(json, object : TypeToken<MutableList<Cliente>>() {}.type)
                return clientes
            } else {
                return mutableListOf<Cliente>()
            }
        }
    }
}