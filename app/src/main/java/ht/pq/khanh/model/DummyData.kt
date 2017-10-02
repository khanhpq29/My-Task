package ht.pq.khanh.model

/**
 * Created by khanhpq on 9/29/17.
 */
object DummyData {
    fun dummyReminder() : MutableList<Reminder>{
        val listReminder : MutableList<Reminder> = arrayListOf()
        listReminder.add(Reminder("kotlin", "new android", null, false))
        listReminder.add(Reminder("JavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJava", "JavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJavaJava old android", null, true))
        return listReminder
    }

    fun dummyAlarm() : MutableList<Alarm> {
        val listAlarm : MutableList<Alarm> = arrayListOf()
        listAlarm.add(Alarm(8, 30, false, true))
        listAlarm.add(Alarm(10, 45, true, false))
        return listAlarm
    }
}