package com.blogspot.raulfmiranda.oticanamao.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

import com.blogspot.raulfmiranda.oticanamao.R
import com.blogspot.raulfmiranda.oticanamao.dataclasses.Cliente
import com.blogspot.raulfmiranda.oticanamao.preferences.ClientesPrefs
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_cadastrar_cliente.*
import kotlinx.android.synthetic.main.fragment_cadastrar_cliente.view.*
import kotlinx.android.synthetic.main.row_cliente.*
import java.io.File

class CadastrarClienteFragment : Fragment() {

    private val CAMERA_PERMISSION_CODE_CONSULTA = 100
    private val CAMERA_PERMISSION_CODE_DOCUMENTO = 101
    private val CAMERA_REQUEST_CONSULTA = 1888
    private val CAMERA_REQUEST_DOCUMENTO  = 1889
    private val maxWidthHeight = 800
    private var arquivoFotoConsulta: File? =  null
    private var arquivoFotoDocumento: File? =  null
    private var clientes = mutableListOf<Cliente>()
    private var clienteCarregado: Cliente? = null

    companion object {
        val EXTRA_CLIENTE = "clientextra"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_cadastrar_cliente, container, false)
        criarMascaras(view)


        view.BtnAddFotoConsulta.setOnClickListener {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    activity?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), CAMERA_PERMISSION_CODE_CONSULTA)

            } else {
                val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                arquivoFotoConsulta = File(geraCaminhoFoto())
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFotoConsulta))
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CONSULTA)
            }
        }

        view.BtnAddFotoDocumento.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    activity?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), CAMERA_PERMISSION_CODE_DOCUMENTO)

            } else {
                val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                arquivoFotoDocumento = File(geraCaminhoFoto())
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFotoDocumento))
                startActivityForResult(cameraIntent, CAMERA_REQUEST_DOCUMENTO)
            }
        }

        view.btnCadEditCliente.setOnClickListener {

            if(!view.edtNomeCliente.text.isNullOrBlank()) {

                val cliente = Cliente(
                        edtNomeCliente.text.toString().trim(),
                        edtEnderecoCliente.text.toString().trim(),
                        edtFone1.text.toString(),
                        edtFone2.text.toString(),
                        edtEmail.text.toString().trim(),
                        edtLocalConta.text.toString().trim(),
                        arquivoFotoConsulta?.absolutePath.toString(),
                        arquivoFotoDocumento?.absolutePath.toString(),
                        edtDataRecOculos.text.toString(),
                        edtNomeRecebedorOculos.text.toString().trim(),
                        edtDataUltConsulta.text.toString()
                )

                clientes.add(cliente)
                edtNomeCliente.setText("")
                edtEnderecoCliente.setText("")
                edtFone1.setText("")
                edtFone2.setText("")
                edtEmail.setText("")
                edtLocalConta.setText("")
                arquivoFotoConsulta = null
                arquivoFotoDocumento = null
                edtDataRecOculos.setText("")
                edtNomeRecebedorOculos.setText("")
                edtDataUltConsulta.setText("")
                imgFotoConsulta.setImageResource(R.drawable.ic_add_a_photo_gray_48dp)
                imgFotoDocumento.setImageResource(R.drawable.ic_add_a_photo_gray_48dp)

                Toast.makeText(context, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Faltou o nome do cliente!", Toast.LENGTH_SHORT).show()
            }
        }

        clientes = ClientesPrefs.recuperarClientes(context)

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            if(!it.isEmpty) {
                clienteCarregado = it.getSerializable(EXTRA_CLIENTE) as Cliente

                if(clienteCarregado != null) {
                    edtNomeCliente.setText(clienteCarregado?.nome)
                    edtEnderecoCliente.setText(clienteCarregado?.endereco)
                    edtFone1.setText(clienteCarregado?.fone1)
                    edtFone2.setText(clienteCarregado?.fone2)
                    edtEmail.setText(clienteCarregado?.email)
                    edtLocalConta.setText(clienteCarregado?.localConta)
                    edtDataRecOculos.setText(clienteCarregado?.dataRecebimentoOculos)
                    edtNomeRecebedorOculos.setText(clienteCarregado?.nomeRecebedorOculos)
                    edtDataUltConsulta.setText(clienteCarregado?.dataUltimaConsulta)
                    btnCadEditCliente.setText("Editar Cliente")

                    arquivoFotoConsulta = File(clienteCarregado?.caminhoFotoConsulta)
                    arquivoFotoDocumento = File(clienteCarregado?.caminhoFotoDocumento)
                    setBitmapConsulta()
                    setBitmapDocumento()

                    edtNomeCliente.isFocusable = false
                    edtEnderecoCliente.isFocusable = false
                    edtFone1.isFocusable = false
                    edtFone2.isFocusable = false
                    edtEmail.isFocusable = false
                    edtLocalConta.isFocusable = false
                    edtDataRecOculos.isFocusable = false
                    edtNomeRecebedorOculos.isFocusable = false
                    edtDataUltConsulta.isFocusable = false
                    BtnAddFotoConsulta.isEnabled = false
                    BtnAddFotoDocumento.isEnabled = false
                    btnCadEditCliente.isEnabled = false
                }
            }
        }
    }

    override fun onDestroy() {
        val prefs = context?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val prefsEditor = prefs?.edit()
        val gson = Gson()
        val json = gson.toJson(clientes)
        prefsEditor?.putString("clientes", json)
        prefsEditor?.apply()

        super.onDestroy()
    }

    private fun geraCaminhoFoto(): String? {
        val caminho = activity?.getExternalFilesDir(null)?.path + "/" + System.currentTimeMillis() + ".jpg"
        return caminho
    }

    private fun criarMascaras(view: View) {

        // Adição de máscaras
        val smfFone = SimpleMaskFormatter("(NN)NNNNNNNNN")
        val smfData = SimpleMaskFormatter("NN/NN/NNNN")

        val mtwFone1 = MaskTextWatcher(view.edtFone1, smfFone)
        val mtwFone2 = MaskTextWatcher(view.edtFone2, smfFone)
        val mtwDataRecOculos = MaskTextWatcher(view.edtDataRecOculos, smfData)
        val mtwDataUltConsulta = MaskTextWatcher(view.edtDataUltConsulta, smfData)

        view.edtFone1.addTextChangedListener(mtwFone1)
        view.edtFone2.addTextChangedListener(mtwFone2)
        view.edtDataRecOculos.addTextChangedListener(mtwDataRecOculos)
        view.edtDataUltConsulta.addTextChangedListener(mtwDataUltConsulta)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == CAMERA_PERMISSION_CODE_CONSULTA) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "Permissão da Câmera Permitida", Toast.LENGTH_SHORT).show()
                val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                arquivoFotoConsulta = File(geraCaminhoFoto())
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFotoConsulta))
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CONSULTA)
            } else {
                Toast.makeText(activity, "Permissão da Câmera Consulta Negada", Toast.LENGTH_SHORT).show()
            }
        }

        if(requestCode == CAMERA_PERMISSION_CODE_DOCUMENTO) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "Permissão da Câmera Permitida", Toast.LENGTH_SHORT).show()
                val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                arquivoFotoDocumento = File(geraCaminhoFoto())
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFotoDocumento))
                startActivityForResult(cameraIntent, CAMERA_REQUEST_DOCUMENTO)
            } else {
                Toast.makeText(activity, "Permissão da Câmera Documento Negada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == CAMERA_REQUEST_CONSULTA && resultCode == Activity.RESULT_OK) {
            setBitmapConsulta()
        }

        if(requestCode == CAMERA_REQUEST_DOCUMENTO && resultCode == Activity.RESULT_OK) {
            setBitmapDocumento()
        }
    }

    private fun setBitmapConsulta() {
        val bitmapConsulta = BitmapFactory.decodeFile(arquivoFotoConsulta?.absolutePath)
        bitmapConsulta?.let {
            //                val bitmapConsultaReduzido = Bitmap.createScaledBitmap(it, 400, 400, true)
            val bitmapConsultaReduzido = resizeBitmap(it, maxWidthHeight, maxWidthHeight)
            imgFotoConsulta.setImageBitmap(bitmapConsultaReduzido)
            imgFotoConsulta.scaleType = ImageView.ScaleType.FIT_CENTER
            imgFotoConsulta.setTag(arquivoFotoConsulta?.absolutePath)
        }
    }

    private fun setBitmapDocumento() {
        val bitmapDocumento = BitmapFactory.decodeFile(arquivoFotoDocumento?.absolutePath)
        bitmapDocumento?.let {
            //                val bitmapDocumentoReduzido = Bitmap.createScaledBitmap(it, 200, 200, true)
            val bitmapDocumentoReduzido = resizeBitmap(it, maxWidthHeight, maxWidthHeight)
            imgFotoDocumento.setImageBitmap(bitmapDocumentoReduzido)
            imgFotoDocumento.scaleType = ImageView.ScaleType.FIT_CENTER
            imgFotoDocumento.setTag(arquivoFotoConsulta?.absolutePath)
        }
    }

    private fun resizeBitmap(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        var image = image
        if (maxHeight > 0 && maxWidth > 0) {
            val width = image.width
            val height = image.height
            val ratioBitmap = width.toFloat() / height.toFloat()
            val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

            var finalWidth = maxWidth
            var finalHeight = maxHeight
            if (ratioMax > ratioBitmap) {
                finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
            } else {
                finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
            return image
        } else {
            return image
        }
    }
}
