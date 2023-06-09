package com.example.bunyamin_kiremit_9.models

data class Note( val key: String,
                 val value: NoteVal
                 )
data class NoteVal(
    val title: String = "",
    val city: String = "",
    val note:String=""
)
