package br.edu.scl.ifsp.ads.contatospdm.controller

import android.os.AsyncTask
import androidx.room.Room
import br.edu.scl.ifsp.ads.contatospdm.model.Contact
import br.edu.scl.ifsp.ads.contatospdm.model.ContactRoomDao
import br.edu.scl.ifsp.ads.contatospdm.model.ContactRoomDao.Constant.CONTACT_DATABASE_FILE
import br.edu.scl.ifsp.ads.contatospdm.model.ContactRoomDaoDatabase
import br.edu.scl.ifsp.ads.contatospdm.view.MainActivity

class ContactRoomController (private val mainActivity: MainActivity) {
    private val contactDaoImpl: ContactRoomDao by lazy{
        Room.databaseBuilder(
            mainActivity,
            ContactRoomDaoDatabase::class.java,
            CONTACT_DATABASE_FILE
        ).build().getContactRoomDao()
    }

    fun insertContact(contact: Contact) {
        Thread {
            contactDaoImpl.createContact(contact)
            getContacts()
        }.start()
    }
    fun getContact(id: Int) = contactDaoImpl.retrieveContact(id)
    fun getContacts() {
        object: AsyncTask<Unit, Unit, MutableList<Contact>>(){
            override fun doInBackground(vararg p0: Unit?): MutableList<Contact> {
                return contactDaoImpl.retrieveContacts()
            }

            override fun onPostExecute(result: MutableList<Contact>?) {
                super.onPostExecute(result)
                if (result != null) {
                    mainActivity.updateContactList(result)
                }
//                result?.also {
//                    mainActivity.updateContactList(result)
//                } OUTRA FORMA DE EXECUTAR O IF
            }
        }.execute()

        contactDaoImpl.retrieveContacts()
    }

    fun editContact(contact: Contact) {
        Thread{
            contactDaoImpl.updateContact(contact)
            getContacts()
        }.start()

    }
    fun removeContact(contact: Contact){
        Thread {
            contactDaoImpl.deleteContact(contact)
            getContacts()
        }.start()
    }
}