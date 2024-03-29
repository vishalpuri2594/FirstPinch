package www.firstpinch.com.firstpinch2;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import www.firstpinch.com.firstpinch2.ErrorLogs.Error_GlobalVariables;

/**
 * Created by Rianaa Admin on 29-11-2016.
 */

public class MonitoringEditText extends EditText {

    private final Context context;
    ClipboardManager clipboard;

    /*
        Just the constructors to create a new EditText...
     */
    public MonitoringEditText(Context context) {
        super(context);
        this.context = context;
        clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

    }

    public MonitoringEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

    }

    public MonitoringEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

    }

    /**
     * <p>This is where the "magic" happens.</p>
     * <p>The menu used to cut/copy/paste is a normal ContextMenu, which allows us to
     * overwrite the consuming method and react on the different events.</p>
     *
     * @see <a href="http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/2.3_r1/android/widget/TextView.java#TextView.onTextContextMenuItem%28int%29">Original Implementation</a>
     */
    @Override
    public boolean onTextContextMenuItem(int id) {
        // Do your thing:
        boolean consumed = super.onTextContextMenuItem(id);
        // React:
        switch (id) {
            case android.R.id.cut:
                onTextCut();
                break;
            case android.R.id.paste:
                onTextPaste();
                break;
            case android.R.id.copy:
                onTextCopy();
        }
        return consumed;
    }

    /**
     * Text was cut from this EditText.
     */
    public void onTextCut() {
        Toast.makeText(context, "Cut!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Text was copied from this EditText.
     */
    public void onTextCopy() {
        Toast.makeText(context, "Copy!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Text was pasted into the EditText.
     */
    public void onTextPaste() {
        try {
            Toast.makeText(context, "Paste!", Toast.LENGTH_SHORT).show();
            ClipData.Item cd_item = clipboard.getPrimaryClip().getItemAt(0);
            // Gets the clipboard as text.
            String pasteData = cd_item.getText().toString();
            String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

            Pattern p = Pattern.compile(URL_REGEX);
            Matcher m = p.matcher(cd_item.getText().toString());//replace with string to compare
            if (m.find()) {
                Log.e("pasteUri", " " + cd_item.getText().toString());
                ((PinchAPost) context).setPreview(cd_item.getText().toString());
            }
        } catch (Exception e) {
            Log.e("MonitoringEditText", "Exception" + e.toString());
            Error_GlobalVariables error_global = new Error_GlobalVariables();
            error_global.initializeVar(context, e.toString());
        }
    }
}