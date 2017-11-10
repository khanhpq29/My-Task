package ht.pq.khanh.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by khanhpq on 11/10/17.
 */
@Entity
open class User(@PrimaryKey(autoGenerate = true) val id : Long,
                val name : String)