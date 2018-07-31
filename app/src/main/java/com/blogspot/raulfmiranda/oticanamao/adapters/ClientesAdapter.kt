package com.blogspot.raulfmiranda.oticanamao.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blogspot.raulfmiranda.oticanamao.R
import com.blogspot.raulfmiranda.oticanamao.dataclasses.Cliente

class ClientesAdapter(val _clientes: MutableList<Cliente>): RecyclerView.Adapter<ClientesAdapter.ViewHolder>() {

    private val clientes = _clientes
    private var context: Context? = null

    override fun getItemCount() = clientes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_cliente, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNomeCliente?.text = clientes[position].nome
    }

    class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView) {
        var txtNomeCliente: TextView? = null
        init {
            txtNomeCliente = itemView?.findViewById(R.id.txtNomeCliente)
        }
    }
}