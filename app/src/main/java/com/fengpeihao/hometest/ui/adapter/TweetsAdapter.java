package com.fengpeihao.hometest.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengpeihao.hometest.R;
import com.fengpeihao.hometest.ui.bean.TweetsBean;
import com.fengpeihao.hometest.utils.ImageUtil;
import com.fengpeihao.hometest.widget.TweetsImageLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author fengpeihao
 * tweets适配器
 */
public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private List<TweetsBean> mList;

    public void setList(List<TweetsBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TweetsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tweets, null, true);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TweetsAdapter.ViewHolder viewHolder, int i) {
        TweetsBean tweetsBean = mList.get(i);
        ImageUtil.getInstance().displayRoundPic(tweetsBean.getSender().getAvatar(),viewHolder.ivPortrait,6);
        viewHolder.tvNick.setText(tweetsBean.getSender().getNick());
        viewHolder.tvContent.setVisibility(TextUtils.isEmpty(tweetsBean.getContent()) ? View.GONE : View.VISIBLE);
        viewHolder.tvContent.setText(tweetsBean.getContent());
        viewHolder.mTweetView.setImageUrls(tweetsBean.getImages());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_portrait)
        ImageView ivPortrait;
        @BindView(R.id.tv_nick)
        TextView tvNick;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tweet_view)
        TweetsImageLayout mTweetView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
