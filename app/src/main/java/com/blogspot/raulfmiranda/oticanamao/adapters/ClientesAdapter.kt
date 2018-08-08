package com.blogspot.raulfmiranda.oticanamao.adapters

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blogspot.raulfmiranda.oticanamao.R
import com.blogspot.raulfmiranda.oticanamao.dataclasses.Cliente
import com.blogspot.raulfmiranda.oticanamao.MainActivity
import com.blogspot.raulfmiranda.oticanamao.fragments.CadastrarClienteFragment
import com.blogspot.raulfmiranda.oticanamao.replaceFragment
import com.mikepenz.materialdrawer.Drawer


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
        holder.txtNomeCliente?.setOnClickListener {
            val frag = CadastrarClienteFragment()
            val bundle = Bundle()
            bundle.putSerializable(CadastrarClienteFragment.EXTRA_CLIENTE, clientes[position])
            frag.arguments = bundle
            replaceFragment(R.id.frmFragmentContainer, frag)
        }
    }

    fun replaceFragment(id: Int, fragment: Fragment) {
        if (context == null)
            return
        if (context is MainActivity) {
            val mainActivity = context as MainActivity
            mainActivity.mDrawer?.setSelectionAtPosition(3)
            mainActivity.replaceFragment(fragment, id)
        }
    }

    class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView) {
        var txtNomeCliente: TextView? = null
        init {
            txtNomeCliente = itemView?.findViewById(R.id.txtNomeCliente)
        }
    }
}