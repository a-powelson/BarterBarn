package com.example.barterbarn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private Context mContext;
    private List<ChatMessage> messagesList;

    public MessageListAdapter(Context context, List<ChatMessage> messagesList){
        this.mContext = context;
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == VIEW_TYPE_MESSAGE_SENT){
            view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.chat_message_bubble_sender, parent, false);
            return new SentMessageHolder(view);
        }else if(viewType == VIEW_TYPE_MESSAGE_RECEIVED){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_bubble, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        ChatMessage message = messagesList.get(pos);
        switch(holder.getItemViewType()){
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public int getItemViewType(int pos){
        ChatMessage message = messagesList.get(pos);
        if(message.getSender()){
            return VIEW_TYPE_MESSAGE_SENT;
        }else{
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    private static class SentMessageHolder extends RecyclerView.ViewHolder{
        TextView messageText, time;
        SentMessageHolder(View itemView){
            super(itemView);
            messageText =  itemView.findViewById(R.id.send_message_text);
            time = itemView.findViewById(R.id.send_message_time);
        }

        void bind(ChatMessage chatMessage){
            messageText.setText(chatMessage.getMessageText());
            time.setText(chatMessage.getStringTime());
        }
    }

    private static class ReceivedMessageHolder extends RecyclerView.ViewHolder{
        TextView messageText, time;
        ReceivedMessageHolder(View itemView){
            super(itemView);
            messageText =  itemView.findViewById(R.id.message_text);
            time = itemView.findViewById(R.id.message_time);
        }

        void bind(ChatMessage chatMessage){
            messageText.setText(chatMessage.getMessageText());
            time.setText(chatMessage.getStringTime());
        }
    }
}
