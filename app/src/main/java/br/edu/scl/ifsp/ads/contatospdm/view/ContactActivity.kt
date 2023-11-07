package br.edu.scl.ifsp.ads.contatospdm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.edu.scl.ifsp.ads.contatospdm.R
import br.edu.scl.ifsp.ads.contatospdm.databinding.ActivityContactBinding
import br.edu.scl.ifsp.ads.contatospdm.model.Constant
import br.edu.scl.ifsp.ads.contatospdm.model.Constant.EXTRA_CONTACT
import br.edu.scl.ifsp.ads.contatospdm.model.Contact
import kotlin.random.Random

class ContactActivity : AppCompatActivity() {
    private val acb: ActivityContactBinding by lazy{
        ActivityContactBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)

        setSupportActionBar(acb.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Contact details"

        val receivedContact = intent.getParcelableExtra<Contact>(EXTRA_CONTACT)
        receivedContact?.let{ _recevedContact ->
            val viewContact = intent.getBooleanExtra(Constant.VIEW_CONTACT,false)
            with(acb){
                if (viewContact) {
                    nameEt.isEnabled = false
                    addressEt.isEnabled = false
                    phoneEt.isEnabled = false
                    emailEt.isEnabled = false
                    saveBt.visibility = View.GONE
                }
                nameEt.setText(_recevedContact.name)
                addressEt.setText(_recevedContact.address)
                phoneEt.setText(_recevedContact.phone)
                emailEt.setText(_recevedContact.email)
            }
        }

        with (acb) {
            saveBt.setOnClickListener {
                val contact: Contact = Contact(
                    id = receivedContact?.id,
                    name = acb.nameEt.text.toString(),
                    address = acb.addressEt.text.toString(),
                    phone = acb.phoneEt.text.toString(),
                    email = acb.emailEt.text.toString()
                )

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_CONTACT, contact)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun generateId(): Int = Random(System.currentTimeMillis()).nextInt()
}