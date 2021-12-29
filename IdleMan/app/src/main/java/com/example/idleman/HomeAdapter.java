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
    //要展示的对应item的数据，imgs是上方的图片/视图
    //titles是标题，headsIcon是头像，username是用户名
//    private int[] imgs;
//    private String[] titles;
//    private int[] headsIcon;
//    private String[] usernames;

    private int[] imgs = {R.drawable.homedemo_myself, R.drawable.homedemo_message, R.drawable.homedemo_home};
    private String[] titles = {"电影\n看书的女人", "阿呆的沙雕绘画", "帅猫"};
    private int[] headsIcon = {R.drawable.homedemo_taskpublish, R.drawable.homedemo_taskranking, R.drawable.homedemo_taskreceive};
    private String[] usernames = {"阿呆", "Wacke", "肉团", "加密", "ajia"};


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
//        holder.title.setText(titles[position]);
//        //holder.head.setImageResource(headsIcon[position]);
//        holder.username.setText(usernames[position]);
        TaskItem item = list.get(position);
//        Glide.with (context).load (item.getImgUrl ()).into (holder.ivBookImage);
        holder.title.setText(item.getText());
//        holder.tvBookAuthor.setText (item.getBookAuthor ());
//        holder.tvBookPrice.setText (item.getBookPrice ());
//        holder.tvBookPages.setText (item.getBookPages ());
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



