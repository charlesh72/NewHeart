/*
 * Copyright (C) 2017 Charles Hancock
 *
 * NewHeart is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * NewHeart is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.beakon.newheart.scripturestudy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.beakon.newheart.R;
import com.beakon.newheart.scripturestudy.widget.ScriptureAppWidget;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Dan on 10/22/2015.
 */
public class Scripture implements Parcelable {

    // The name of the directory for the notes files
    public static final String NOTES_DIR = "notes";
    public static final String EXTRA_SCRIPTURE_REFERENCE = "Scripture.reference";

    /**
     * CREATOR used by the Android OS to reconstruct a Scripture object that has been stored in a
     * Parcel
     */
    public static final Parcelable.Creator<Scripture> CREATOR = new Creator<Scripture>() {
        @Override
        public Scripture createFromParcel(Parcel source) {
            String reference = source.readString();
            String body = source.readString();
            NewMainActivity.Category cat = (NewMainActivity.Category) source.readSerializable();
            return new Scripture(reference, body, cat);
        }

        @Override
        public Scripture[] newArray(int size) {
            return new Scripture[size];
        }
    };

    public static String convertRefToFilename(String ref) {
        return ref.trim() // Example: start with 1 Nephi 3:7, 10-11
                .toLowerCase() // 1 nephi 3:7, 10-11
                .replace(':', '.') // 1 nephi 3.7, 10-11
                .replaceAll("[\\s\\\\\\?/\\*\"<>]", "") // 1nephi3.7,10-11
                .concat(".txt"); // 1nephi3.7,10-11.txt
    }

    public final String reference;
    public final String filename;
    public final String body;
    private NewMainActivity.Category mCategory;

    public static LinkedList<Scripture> loadScriptures(@NonNull Context c, NewMainActivity.Category category) {
        File dir = c.getDir(category.name(), Context.MODE_PRIVATE);

        File[] files = dir.listFiles();
        LinkedList<Scripture> scriptures = new LinkedList<>();
        for (File f : files) {
            if (f.isFile()) {
                try {
                    final long numOfChars = f.length() / 2;
                    long numRead = 0;
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    String reference = br.readLine();
                    numRead = reference.length() + 1; // length of first line, plus one for the newline.
                    StringBuilder body = new StringBuilder();
                    while (numRead < numOfChars) {
                        try {
                            String line = br.readLine();
                            if (line == null) {
                                break;
                            }
                            body.append(line).append("\n");
                        } catch (EOFException e) {
                            break;
                        }
                    }

                    // Add the new scripture
                    Scripture s = new Scripture(reference, body.toString(), category);
                    scriptures.add(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return scriptures;
    }

    public Scripture(@NonNull String reference, @NonNull String body,
                     @NonNull NewMainActivity.Category cat) {
        this.reference = reference.trim(); // Remove excess whitespace
        // Convert reference to a proper filename by
        // (1) replacing colons with dots,
        // (2) removing any whitespace or other illegal characters, and
        // (3) appending ".txt" to the end to make it a text file.
        this.filename = convertRefToFilename(this.reference);

        // Remove excess whitespace from the body text as well.
        this.body = body.trim();

        // This part's simple enough. :)
        this.mCategory = cat;
    }

    /**
     * Writes the contained scripture to a file in the appropriate directory.
     */
    public boolean writeToFile(Context c) {
        File dir = getDir(c, mCategory);
        File dest = new File(dir, filename);
        if (!dest.exists()) {
            try {
                dest.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            FileWriter fw = new FileWriter(dest);
            fw.write(reference + "\n" +
                    body + "\n");
            fw.flush();
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the scripture reference
     */
    @Override
    public String toString() {
        return reference;
    }

    // From Parcelable interface
    @Override
    public int describeContents() {
        return 0;
    }

    // From Parcelable interface
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reference);
        dest.writeString(body);
        dest.writeSerializable(mCategory);
    }

    /**
     * Returns the scripture reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Returns the text of the scripture
     */
    public String getBody() {
        return body;
    }

    public String getFilename() {
        return filename;
    }

    public NewMainActivity.Category getCategory() {
        return mCategory;
    }


    public void changeCategory(Context c, NewMainActivity.Category category) {
        if (category != mCategory) {
            // Delete file from old category directory
            File dir = getDir(c, mCategory);
            File file = new File(dir, filename);
            if (file.exists()) file.delete();

            // Write file to new category directory
            this.mCategory = category;
            writeToFile(c);
        }
    }

    public boolean isCompleted() {
        return mCategory == NewMainActivity.Category.COMPLETED;
    }

    /**
     * @param context
     * @param r       The Runnable to run after the user confirms the deletion
     */
    public void deleteWithConfirmation(final Context context, final Runnable r) {
        AlertDialog.Builder db = new AlertDialog.Builder(context);
        db.setMessage(R.string.dialog_confirm_delete_scripture);
        DialogInterface.OnClickListener l = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Scripture.this.delete(context);
                r.run();
            }
        };
        db.setPositiveButton(R.string.yes, l);
        db.setNegativeButton(R.string.no, null);
        db.show();
    }

    // Deletes this scripture
    public void delete(Context context) {
        // Delete scripture file
        File scripDir = getDir(context, mCategory);
        File scripFile = new File(scripDir, filename);
        if (scripFile.exists()) scripFile.delete();
        // Delete notes file
        File notesDir = context.getDir(Scripture.NOTES_DIR, 0);
        File noteFile = new File(notesDir, filename);
        if (noteFile.exists()) noteFile.delete();

        ScriptureAppWidget.updateAllAppWidgets(context);
    }

    public boolean fileExists(Context context) {
        File scripDir = getDir(context, mCategory);
        File scripFile = new File(scripDir, filename);
        return scripFile.exists();
    }

    public static File getDir(Context context, NewMainActivity.Category category) {
        return context.getDir(category.name(), 0);
    }

    public boolean hasNotes(Context c) {
        File notesFile = getNotesFile(c);
        return notesFile.exists(); // The file should not exist if there are no notes.
    }

    public File getNotesFile(Context c) {
        return new File(c.getDir(Scripture.NOTES_DIR, 0), filename);
    }

}
