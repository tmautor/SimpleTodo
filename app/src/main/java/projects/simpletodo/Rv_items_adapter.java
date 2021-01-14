package projects.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Responsible for displaying data from the model into a row in the recycler view
public class Rv_items_adapter extends RecyclerView.Adapter<Rv_items_adapter.ViewHolder>
{
    public interface OnLongClickListener
    {
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;

    public Rv_items_adapter(List<String> i, OnLongClickListener lcl)
    {
        this.items = i;
        this.longClickListener = lcl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Use layout to inflate a view
        View rv_items_view = LayoutInflater.from(parent.getContext()).inflate(
                android.R.layout.simple_list_item_1, parent, false);

        // Wrap view inside a view holder and return it
        return new ViewHolder(rv_items_view);
    }

    // Responsible for binding data to a particular view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        // Get the item at the position
        String item = items.get(position);

        // Bind the item into the view holder
        holder.bind(item);
    }

    // Tells the Recycler View how many items are in the list
    @Override
    public int getItemCount()
    {
        return items.size();
    }

    // Container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_item;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_item = itemView.findViewById(android.R.id.text1);
        }

        // Update the view inside of the view holder with this data
        public void bind(String item)
        {
            tv_item.setText(item);
            tv_item.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    // Notify the long click listener of which item was long clicked
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
