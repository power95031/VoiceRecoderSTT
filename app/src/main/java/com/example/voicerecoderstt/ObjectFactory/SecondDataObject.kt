package com.example.voicerecoderstt.ObjectFactory

import com.google.gson.annotations.SerializedName

data class SecondDataObject (
    @SerializedName("data")
    var dataArray:ArrayList<SecondDataItem>
)
{
    data class SecondDataItem(
        @SerializedName("start")
        var start:Long,
        @SerializedName("end")
        var end:Long,
        @SerializedName("text")
        var text:String
    )
}