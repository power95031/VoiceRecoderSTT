package com.example.voicerecoderstt.ObjectFactory

import com.google.gson.annotations.SerializedName

data class dataObject(
    @SerializedName("data")
    var dataArray : ArrayList<dataElement>
) {
    data class dataElement(
        @SerializedName("start")
        var start:String,
        @SerializedName("end")
        var end:String,
        @SerializedName("text")
        var text:String,
        @SerializedName("confidence")
        var confidence:Double,
        @SerializedName("diarization")
        var diarizationItem:diarizationItem,
        @SerializedName("speaker")
        var speakerItem:speakerItem,
        @SerializedName("words")
        var wordArray:ArrayList<ArrayList<String>>,
        @SerializedName("textEdited")
        var textEdited:String
    )

    data class diarizationItem(
        @SerializedName("label")
        var label:String
    )

    data class speakerItem(
        @SerializedName("label")
        var label:String,
        @SerializedName("name")
        var name:String,
        @SerializedName("edited")
        var edited:Boolean
    )
}