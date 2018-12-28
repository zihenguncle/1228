package jx.com.quanjiatong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import jx.com.quanjiatong.R;
import jx.com.quanjiatong.activity.DetailsActivity;
import jx.com.quanjiatong.bean.DataBean;

public class LinearBookAdapter extends RecyclerView.Adapter<LinearBookAdapter.ViewHolder> {

    private List<DataBean> list;
    private Context context;

    public LinearBookAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LinearBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_linear_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinearBookAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(list.get(i).getTitle());
        viewHolder.price.setText(list.get(i).getPrice()+"");
        viewHolder.simpleDraweeView.setImageURI(list.get(i).splic());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("pid",list.get(i).getPid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView simpleDraweeView;
        private TextView title,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.image_list);
            title = itemView.findViewById(R.id.textView_title_list);
            price = itemView.findViewById(R.id.textView_price_list);
        }
    }
}
