package com.blogspot.raulfmiranda.oticanamao.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.blogspot.raulfmiranda.oticanamao.R
import com.blogspot.raulfmiranda.oticanamao.adapters.ClientesAdapter
import com.blogspot.raulfmiranda.oticanamao.dataclasses.Cliente
import com.blogspot.raulfmiranda.oticanamao.preferences.ClientesPrefs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_buscar_cliente.*

class BuscarClienteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar_cliente, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val clientes = ClientesPrefs.recuperarClientes(context)

        if(clientes.size > 0) {
            txtNenhumCliente.visibility = TextView.GONE
            recClientes.visibility = RecyclerView.VISIBLE
            recClientes.layoutManager = LinearLayoutManager(activity)
            val adapter = ClientesAdapter(clientes)
            recClientes.adapter = adapter
        } else {
            txtNenhumCliente.visibility = TextView.VISIBLE
            recClientes.visibility = RecyclerView.GONE
        }

    }
}
