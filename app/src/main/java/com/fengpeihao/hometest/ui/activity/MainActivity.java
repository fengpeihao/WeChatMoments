package com.fengpeihao.hometest.ui.activity;


import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengpeihao.hometest.R;
import com.fengpeihao.hometest.base.BaseActivity;
import com.fengpeihao.hometest.ui.adapter.TweetsAdapter;
import com.fengpeihao.hometest.ui.bean.TweetsBean;
import com.fengpeihao.hometest.ui.bean.UserInfoBean;
import com.fengpeihao.hometest.ui.contract.MainContract;
import com.fengpeihao.hometest.ui.presenter.MainPresenter;
import com.fengpeihao.hometest.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author fengpeihao
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.iv_top_background)
    ImageView mIvTopBackground;
    @BindView(R.id.iv_portrait)
    ImageView mIvPortrait;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.tv_username)
    TextView mTvUsername;
    private TweetsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoading;//是否是加载中
    private int pageIndex = 1;//页码
    private int pageSize = 5;//每页条数
    private boolean isLoadMore;//是否是加载更多
    private boolean isLoadAll;//是否全部加载完毕
    private List<TweetsBean> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        setRecyclerView();
        initListener();
        showLoadingDialog();
        mPresenter.getUserInfo();
        mPresenter.getTweets(pageIndex, pageSize, isLoadMore);
    }

    /**
     * 设置监听
     */
    private void initListener() {
        mToolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        mAppBarLayout.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) (appBarLayout, verticalOffset) -> {
            if (verticalOffset >= 0) {
                //当滑动到顶部的时候开启下拉刷新
                mSwipeRefreshLayout.setEnabled(true);
            } else {
                //否则关闭下拉刷新
                mSwipeRefreshLayout.setEnabled(false);
            }
        });
        setLoadMoreListener();
        setRefreshListener();
    }

    /**
     * 设置下拉刷新时的操作
     */
    private void setRefreshListener() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            pageIndex = 1;
            isLoadMore = false;
            mPresenter.getTweets(pageIndex, pageSize, false);
        });
    }

    /**
     * 添加上拉加载监听
     */
    private void setLoadMoreListener(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取最后一个完全显示的ItemPosition
                int lastVisibleItem = mLayoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                if (totalItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == (totalItemCount - 1) && !isLoading && !isLoadAll) {
                    isLoading = true;
                    pageIndex++;
                    showLoadingDialog();
                    isLoadMore = true;
                    mPresenter.getTweets(pageIndex, pageSize, isLoadMore);
                }
            }
        });
    }

    /**
     * 设置recyclerView
     */
    private void setRecyclerView() {
        mAdapter = new TweetsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    /**
     * 绑定presenter
     * @return
     */
    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    /**
     * 获取用户信息
     * @param userInfoBean
     */
    @Override
    public void setUserInfo(UserInfoBean userInfoBean) {
        ImageUtil.getInstance().display(userInfoBean.getAvatar(), mIvTopBackground);
        ImageUtil.getInstance().displayRoundPic(userInfoBean.getProfileimage(), mIvPortrait, 6);
        mTvUsername.setText(userInfoBean.getNick());
    }

    /**
     * 获取Tweets 列表
     * @param tweetsBeans tweets列表
     * @param isLoadMore 是否是加载更多
     */
    @Override
    public void setTweets(List<TweetsBean> tweetsBeans, boolean isLoadMore) {
        mSwipeRefreshLayout.setRefreshing(false);
        this.isLoadMore = isLoadMore;
        isLoading = false;
        isLoadAll = tweetsBeans.size() < pageSize;
        if (!isLoadMore) {
            pageIndex = 1;
            mList.clear();
        }
        mList.addAll(tweetsBeans);
        mAdapter.setList(mList);
    }
}
