package com.example.idleman;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

//public class Fragment_Home extends Fragment {
//    private View view;//定义view用来设置fragment的layout
//    public RecyclerView mCollectRecyclerView;//定义RecyclerView
//    //定义以goodsentity实体类为对象的数据集合
//    private ArrayList<GoodsEntity> goodsEntityList = new ArrayList<GoodsEntity>();
//    //自定义recyclerveiw的适配器
//    private CollectRecycleAdapter mCollectRecyclerAdapter;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        //获取fragment的layout
//        view = inflater.inflate(R.layout.collect_page, container, false);
//        //对recycleview进行配置
//        initRecyclerView();
//        //模拟数据
//        initData();
//        return view;
//    }
//
//    /**
//     * TODO 模拟数据
//     */
//    private void initData() {
//        for (int i=0;i<10;i++){
//            GoodsEntity goodsEntity=new GoodsEntity();
//            goodsEntity.setGoodsName("模拟数据"+i);
//            goodsEntity.setGoodsPrice("100"+i);
//            goodsEntityList.add(goodsEntity);
//        }
//    }
//
//    /**
//     * TODO 对recycleview进行配置
//     */
//
//    private void initRecyclerView() {
//        //获取RecyclerView
//        mCollectRecyclerView=(RecyclerView)view.findViewById(R.id.collect_recyclerView);
//        //创建adapter
//        mCollectRecyclerAdapter = new CollectRecycleAdapter(getActivity(), goodsEntityList);
//        //给RecyclerView设置adapter
//        mCollectRecyclerView.setAdapter(mCollectRecyclerAdapter);
//        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
//        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
//        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        //设置item的分割线
//        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
//        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
//        mCollectRecyclerAdapter.setOnItemClickListener(new CollectRecycleAdapter.OnItemClickListener() {
//            @Override
//            public void OnItemClick(View view, GoodsEntity data) {
//                //此处进行监听事件的业务处理
//                Toast.makeText(getActivity(),"我是item", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//}


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public Fragment_Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Fragment_Message.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Home newInstance(String param1) {
        Fragment_Home fragment = new Fragment_Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    //用户点击publish按钮之后的响应
    public void taskPublish(View view) {
        Intent intent = new Intent(getActivity(), Activity_Task_Publish.class);
        startActivity(intent);
    }
}
