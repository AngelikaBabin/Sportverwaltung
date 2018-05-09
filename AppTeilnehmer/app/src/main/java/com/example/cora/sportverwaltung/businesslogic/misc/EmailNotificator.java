package com.example.cora.sportverwaltung.businesslogic.misc;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by nicok on 09.05.2018 ^-^.
 */
public class EmailNotificator {
    private Context context;

    public EmailNotificator(Context context) {
        this.context = context;
    }

    public void send(String email, String subject, String content) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT   , content);
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
