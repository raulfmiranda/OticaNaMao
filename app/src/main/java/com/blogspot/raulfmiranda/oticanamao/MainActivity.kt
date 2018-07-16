package com.blogspot.raulfmiranda.oticanamao

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import co.zsmb.materialdrawerkt.builders.accountHeader
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.divider
import co.zsmb.materialdrawerkt.draweritems.profile.profile
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
    }

    // MaterialDrawerKt: https://github.com/zsmb13/MaterialDrawerKt
    // MaterialDrawer: https://github.com/mikepenz/MaterialDrawer
    private fun createDrawer() {

        mDrawer = drawer {
            accountHeader {
                background = R.color.colorPrimaryDark
                profile("Nome Sobrenome", "exemplo@email.com") { }
            }

            primaryItem(getString(R.string.menuItem1)) {
                onClick { _ ->
                    Toast.makeText(this@MainActivity, getString(R.string.menuItem1), Toast.LENGTH_SHORT).show()
                    false
                }
            }
            primaryItem(getString(R.string.menuItem2)) {
                onClick { _ ->
                    Toast.makeText(this@MainActivity, getString(R.string.menuItem2), Toast.LENGTH_SHORT).show()
                    false
                }
            }
            divider {  }
            primaryItem(getString(R.string.menuItem3)) {
                onClick { _ ->
                    Toast.makeText(this@MainActivity, getString(R.string.menuItem3), Toast.LENGTH_SHORT).show()
                    false
                }
            }
            primaryItem(getString(R.string.menuItem4)) {
                onClick { _ ->
                    Toast.makeText(this@MainActivity, getString(R.string.menuItem4), Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }
    }
}
