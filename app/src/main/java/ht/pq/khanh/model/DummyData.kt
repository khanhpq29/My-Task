package ht.pq.khanh.model

/**
 * Created by khanhpq on 9/29/17.
 */
object DummyData {
    fun dummyReminder() : MutableList<Reminder>{
        val listReminder : MutableList<Reminder> = arrayListOf()
        listReminder.add(Reminder("kotlin", "new android", null))
        return listReminder
    }
}