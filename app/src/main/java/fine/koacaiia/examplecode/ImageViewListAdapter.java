package fine.koacaiia.examplecode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageViewListAdapter extends RecyclerView.Adapter<ImageViewListAdapter.ListViewHolder>{
    ArrayList<String> list;

    public ImageViewListAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_imagelist,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        String str=list.get(position);

        Glide.with(holder.itemView)
                .load(str)
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView=itemView.findViewById(R.id.listImageView);
        }
    }
}
