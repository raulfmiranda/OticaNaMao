package com.blogspot.raulfmiranda.oticanamao

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import co.zsmb.materialdrawerkt.builders.accountHeader
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.divider
import co.zsmb.materialdrawerkt.draweritems.profile.profile
import com.blogspot.raulfmiranda.oticanamao.fragments.*
import com.mikepenz.materialdrawer.Drawer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mDrawer: Drawer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupScreen()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        mDrawer?.openDrawer()
        return super.onOptionsItemSelected(item)
    }

    private fun setupScreen() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
        createDrawer()
        addFragment(TelaInicialFragment(), R.id.frmFragmentContainer)
    }

    // MaterialDrawerKt: https://github.com/zsmb13/MaterialDrawerKt
    // MaterialDrawer: https://github.com/mikepenz/MaterialDrawer
    private fun createDrawer() {

        mDrawer = drawer {
            accountHeader {
                background = R.color.colorPrimaryDark
                profile("Construcar", "construcar@gmail.com") { }
            }
            primaryItem(getString(R.string.tela_inicial)) {
                onClick { _ ->
                    supportActionBar?.title = getString(R.string.tela_inicial)
                    replaceFragment(TelaInicialFragment(), R.id.frmFragmentContainer)
                    false
                }
            }
            divider {  }
            primaryItem(getString(R.string.cadastrar_cliente)) {
                onClick { _ ->
                    supportActionBar?.title = getString(R.string.cadastrar_cliente)
                    replaceFragment(CadastrarClienteFragment(), R.id.frmFragmentContainer)
                    false
                }
            }
            primaryItem(getString(R.string.buscar_cliente)) {
                onClick { _ ->
                    supportActionBar?.title = getString(R.string.buscar_cliente)
                    replaceFragment(BuscarClienteFragment(), R.id.frmFragmentContainer)
                    false
                }
            }
//            divider {  }
//            primaryItem(getString(R.string.cadastrar_evento)) {
//                onClick { _ ->
//                    supportActionBar?.title = getString(R.string.cadastrar_evento)
//                    replaceFragment(CadastrarEventoFragment(), R.id.frmFragmentContainer)
//                    false
//                }
//            }
//            primaryItem(getString(R.string.buscar_evento)) {
//                onClick { _ ->
//                    supportActionBar?.title = getString(R.string.buscar_evento)
//                    replaceFragment(BuscarEventoFragment(), R.id.frmFragmentContainer)
//                    false
//                }
//            }
        }
    }
}
