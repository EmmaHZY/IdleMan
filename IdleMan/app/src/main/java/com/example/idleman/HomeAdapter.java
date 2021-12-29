package com.example.idleman;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {


    Context context;
    private List<TaskItem> list;

    private int space = 5;

    public HomeAdapter(Context context, List<TaskItem> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    //加载布局文件并返回MyViewHolder对象
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建view对象
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
//        view.setOnClickListener(this);//设置监听器
        //创建MyViewHolder对象
        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = myViewHolder.getAdapterPosition();
                Long message=list.get(position).getTaskID();
//                String message=position+"";
                Intent intent = new Intent(context, Activity_Task.class);
                intent.putExtra("taskID",message);
                context.startActivity(intent);
            }
        });
        return myViewHolder;
    }

    @Override
    //获取数据并显示到对应控件
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//
//        //给我的四个控件获取一下数据，注意不同类型调用不同的方法，设置图片用setImageResource（），设置文字用setText（）
//        //holder.img.setImageResource(imgs[position]);
        TaskItem item = list.get(position);
//        Glide.with (context).load (item.getImgUrl ()).into (holder.ivBookImage);
        holder.title.setText(item.getText());
//        holder.tvBookAuthor.setText (item.getBookAuthor ());
    }

    @Override
    public int getItemCount() {
        //获取列表条目总数
        return list.size();
    }
//
//    @Override
//    public void onClick(View view) {
//        Intent intent = new Intent(context, Activity_Task.class);
//
//        intent.putExtra("taskID",);
//        context.startActivity(intent);
//    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        //初始化控件
        ImageView img, head;
        TextView title, username;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.home_item_img);
            title = itemView.findViewById(R.id.home_item_title);
            // head=itemView.findViewById(R.id.home_item_head);
            username = itemView.findViewById(R.id.home_item_username);
        }
    }

    private OnItemClickListener onItemClickListener;//声明一下这个接口
    //提供setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}



