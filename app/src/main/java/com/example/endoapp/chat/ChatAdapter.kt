package com.example.endoapp.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.endoapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatAdapter : ListAdapter<ChatMessage, ChatAdapter.MessageHolder>(Diff) {

    private val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    class MessageHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.messageText)
        val time: TextView = view.findViewById(R.id.messageTime)
    }

    override fun getItemViewType(position: Int) =
        if (getItem(position).isSentByMe) TYPE_SENT else TYPE_RECEIVED

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val layout = if (viewType == TYPE_SENT) R.layout.item_message_sent
        else R.layout.item_message_received
        return MessageHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false))
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val msg = getItem(position)
        holder.text.text = msg.text
        holder.time.text = timeFormat.format(Date(msg.timestamp))
    }

    private object Diff : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(a: ChatMessage, b: ChatMessage) = a.id == b.id
        override fun areContentsTheSame(a: ChatMessage, b: ChatMessage) = a == b
    }

    companion object {
        private const val TYPE_SENT = 0
        private const val TYPE_RECEIVED = 1
    }
}