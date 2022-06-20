package com.example.voicerecoderstt.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voicerecoderstt.ObjectFactory.SecondDataObject
import com.example.voicerecoderstt.databinding.ItemResultBinding
import java.util.concurrent.TimeUnit

class ResultAdapter(var resultObject:SecondDataObject) : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    var resultArray = resultObject.dataArray

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.binding.ItemStartTime.text = millisecondsToTime(resultArray[position].start)
        holder.binding.ItemEndTime.text = millisecondsToTime(resultArray[position].end)
        holder.binding.ItemText.text = resultArray[position].text
    }

    override fun getItemCount(): Int {
        return resultObject.dataArray.size
    }

    private fun millisecondsToTime(millis:Long) : String {
        return String.format("%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        )
    }

    class ResultViewHolder(var binding:ItemResultBinding) : RecyclerView.ViewHolder(binding.root)
}