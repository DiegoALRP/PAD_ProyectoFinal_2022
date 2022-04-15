package es.ucm.fdi.emtntr.internalStorage;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteIE {

    public WriteIE() {
        //Do Nothing
    }

    public void write(Context context, String filename, String data) {

        try {

            File file = new File(context.getFilesDir(), filename);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
