package projects.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    List<String> items;
    Button button_add;
    EditText et_item;
    RecyclerView rv_items;
    Rv_items_adapter rv_items_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_add = findViewById(R.id.button_add);
        et_item = findViewById(R.id.et_item);
        rv_items = findViewById(R.id.rv_items);

        loadItems();

        Rv_items_adapter.OnLongClickListener onLongClickListener =
                new Rv_items_adapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position)
            {
                // Delete the item from the model
                items.remove(position);

                // Notify the Adapter that the model has been updated
                rv_items_adapter.notifyItemRemoved(position);

                Toast.makeText(getApplicationContext(), "Item removed.",
                        Toast.LENGTH_SHORT).show();

                saveItems();
            }
        };

        rv_items_adapter = new Rv_items_adapter(items, onLongClickListener);
        rv_items.setAdapter(rv_items_adapter);
        rv_items.setLayoutManager(new LinearLayoutManager(this));

        button_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String et_item_contents = et_item.getText().toString();

                // Add item to the model
                items.add(et_item_contents);

                // Notify the Adapter that the model has been updated
                rv_items_adapter.notifyItemInserted(items.size() - 1);

                // Clear et_item text
                et_item.setText("");
                Toast.makeText(getApplicationContext(), "Item added.",
                        Toast.LENGTH_SHORT).show();

                saveItems();
            }
        });
    }

    private File getDataFile()
    {
        return new File(getFilesDir(), "data.txt");
    }

    // Reads data.txt and populates items with all lines
    private void loadItems()
    {
        try
        {
            items = new ArrayList<String>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch (IOException e)
        {
            Log.e("MainActivity", "Error reading data.txt", e);
            items = new ArrayList<String>();
        }
    }

    // Writes all items to data.txt
    private void saveItems()
    {
        try
        {
            FileUtils.writeLines(getDataFile(), items);
        }
        catch (IOException e)
        {
            Log.e("MainActivity", "Error writing to data.txt", e);
        }
    }
}