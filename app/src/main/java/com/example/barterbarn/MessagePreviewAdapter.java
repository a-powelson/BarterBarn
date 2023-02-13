package com.example.barterbarn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessagePreviewAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Conversation> convoList;

    public MessagePreviewAdapter(Context context, List<Conversation> convoList){
        this.mContext = context;
        this.convoList = convoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_preview_bubble, parent, false);

        return new MessagePreviewAdapter.ChatPreviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        Conversation convo = convoList.get(pos);

        ((MessagePreviewAdapter.ChatPreviewHolder) holder).bind(convo);
        ((ChatPreviewHolder) holder).sender.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context,MessagingActivity.class);
            intent.putExtra("userID",convo.getUserID());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return convoList.size();
    }

    private static class ChatPreviewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageButton sender;

        ChatPreviewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.preview_user);
            sender = itemView.findViewById(R.id.messageButton);
        }

        void bind(Conversation convo){
            name.setText(convo.getUserName());
        }
    }

}
