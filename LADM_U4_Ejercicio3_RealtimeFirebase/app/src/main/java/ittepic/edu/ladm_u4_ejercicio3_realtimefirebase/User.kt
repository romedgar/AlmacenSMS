package ittepic.edu.ladm_u4_ejercicio3_realtimefirebase


import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User (val username : String?=null, val email: String ?=null) {
}