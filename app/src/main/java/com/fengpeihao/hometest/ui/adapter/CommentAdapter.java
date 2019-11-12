package com.fengpeihao.hometest.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fengpeihao.hometest.R;
import com.fengpeihao.hometest.ui.bean.TweetsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author fengpeihao
 * 评论适配器
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<TweetsBean.CommentsBean> mList;

    public CommentAdapter(List<TweetsBean.CommentsBean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment, null, true);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder viewHolder, int i) {
        TweetsBean.CommentsBean commentsBean = mList.get(i);
        String nick = String.format("%s：", commentsBean.getSender().getNick());
        String comment = commentsBean.getContent();
        String content = nick + comment;
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(viewHolder.tvContent.getContext(),android.R.color.holo_blue_dark)),
                0,nick.length(),SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(viewHolder.tvContent.getContext(),android.R.color.black)),
                nick.length(),content.length(),SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.tvContent.setText(spannableString);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
