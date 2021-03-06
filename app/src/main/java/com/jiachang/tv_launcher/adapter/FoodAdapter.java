package com.jiachang.tv_launcher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiachang.tv_launcher.R;
import com.jiachang.tv_launcher.bean.Food;
import com.jiachang.tv_launcher.utils.ViewUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Mickey.Ma
 * @date 2020-03-26
 * @description
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> implements View.OnClickListener {
    private List<Food> mFoodList;
    private Context context;
    private onItemClickListener itemClickListener;//ItemView的监听器
    private RecyclerView recyclerView;

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.food_image)
        ImageView foodImage;
        @BindView(R.id.food_name)
        TextView foodName;
        @BindView(R.id.food_card)
        AutoRelativeLayout foodCard;

        public ViewHolder (View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public  FoodAdapter (Context context,List <Food> foodList){
        this.context = context;
        this.mFoodList = foodList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dining_food_item,parent,false);
        view.setOnClickListener(this);
        final ViewHolder holder = new ViewHolder(view);
        context = view.getContext();

        holder.foodCard.setFocusable(true);

        holder.foodCard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ViewUtils.focusStatus(v);
                } else {
                    ViewUtils.normalStatus(v);
                }
            }
        });

        initView(holder);

        return holder;
    }

    private void initView(ViewHolder viewHolder){
        ViewGroup.LayoutParams layoutParams =viewHolder.foodCard.getLayoutParams();
        layoutParams.width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
    }

    public void setItemClickListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Food food = mFoodList.get(position);
        holder.foodImage.setImageResource(food.getImageId());
        holder.foodName.setText(food.getName());
    }

    @Override
    public int getItemCount(){
        return mFoodList.size();
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView=recyclerView;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null && recyclerView != null){
            //recyclerView 21以下使用,　22时作废
//             int position = recyclerView.getChildPosition(v);
            //22时用些方法替换上面的方法
            int position = recyclerView.getChildAdapterPosition(v);
            itemClickListener.onItemClick(position,v,mFoodList.get(position));
        }
    }

    public interface onItemClickListener{
        void onItemClick(int position,View v,Food food);
    }
}
