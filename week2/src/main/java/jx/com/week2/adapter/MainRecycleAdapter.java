package jx.com.week2.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
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

import jx.com.week2.R;
import jx.com.week2.bean.DataBean;
import jx.com.week2.bean.NetBookBean;

public class MainRecycleAdapter extends RecyclerView.Adapter<MainRecycleAdapter.ViewHolder> {

    private List<DataBean> mlist;
    private Context context;

    public MainRecycleAdapter(Context context) {
        this.context = context;
        mlist = new ArrayList<>();
    }

    public void setMlist(List<DataBean> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.main_recycle_adapter_item,null);
        return new ViewHolder(view);
    }


    private ObjectAnimator animator;

    @Override
    public void onBindViewHolder(@NonNull MainRecycleAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(mlist.get(i).getTitle());
        viewHolder.price.setText(mlist.get(i).getPrice()+"");
        viewHolder.simpleDraweeView.setImageURI(mlist.get(i).split());
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                animator = ObjectAnimator.ofFloat(view,"alpha",1.0f,0f,1.0f);
                animator.setDuration(2000);
                remove(i);
                return true;
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               callBack.longClick(mlist.get(i).getPid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView simpleDraweeView;
        private TextView title,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.main_adapter_item_simpledrawee);
            title = itemView.findViewById(R.id.main_adapter_item_title);
            price = itemView.findViewById(R.id.main_adapter_item_price);
        }
    }

    public void remove(final int i){
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mlist.remove(i);
                notifyItemRemoved(i);
                notifyDataSetChanged();
            }
        });
    }

    public OnLongCallBack callBack;
    public void setOnLongCallBack(OnLongCallBack callBack){
        this.callBack = callBack;
    }
    public interface OnLongCallBack{
        void longClick(int pid);
    }
}
