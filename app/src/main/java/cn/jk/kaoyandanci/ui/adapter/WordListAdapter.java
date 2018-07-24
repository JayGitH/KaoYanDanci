package cn.jk.kaoyandanci.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jk.kaoyandanci.R;
import cn.jk.kaoyandanci.model.Word;
import cn.jk.kaoyandanci.ui.activity.MainActivity;
import cn.jk.kaoyandanci.ui.activity.WordDetailActivity;
import cn.jk.kaoyandanci.ui.activity.WordListActivity;
import cn.jk.kaoyandanci.ui.activity.YoudaoWordActivity;
import cn.jk.kaoyandanci.util.Constant;

/**
 * Created by Administrator on 2017/6/8.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {


    List<Word> wordList;
    Context context;

    boolean showChinese = true;
    boolean showEdt = false;

    public WordListAdapter(List<Word> wordList, Context context) {

        this.context = context;
        this.wordList = wordList;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_word_brief, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Word word = wordList.get(position);
        holder.englishTxt.setText(word.getEnglish());
        holder.chineseTxt.setText(word.getChinese());
        if (showChinese) {

            holder.chineseTxt.setText(word.getChinese());
        } else {
            holder.chineseTxt.setText("");
        }
        if (showEdt) {
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wordList.remove(position);    //TODO 如果已经掌握呢????

                    ((WordListActivity) context).neverShow(word);

                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, wordList.size());
                    MainActivity.DATA_CHANGED = true;
                }
            });

            holder.collectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                       //TODO 更改星星颜色.
                }
            });
            holder.deleteBtn.setVisibility(View.VISIBLE);
            holder.collectBtn.setVisibility(View.VISIBLE);

        } else {
            holder.deleteBtn.setVisibility(View.GONE);
            holder.collectBtn.setVisibility(View.GONE);


        }

        View parentView = (View) (holder.chineseTxt.getParent().getParent());
        parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WordDetailActivity.class);
                intent.putExtra(Constant.ENGLISH, word.getEnglish());
                context.startActivity(intent);
            }
        });
        parentView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, YoudaoWordActivity.class);
                intent.putExtra(Constant.ENGLISH, word.getEnglish());
                context.startActivity(intent);
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public void setShowChinese(boolean showChinese) {
        this.showChinese = showChinese;
    }

    public void setShowEdt(boolean showEdt) {
        this.showEdt = showEdt;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.englishTxt)
        TextView englishTxt;
        @BindView(R.id.chineseTxt)
        TextView chineseTxt;
        @BindView(R.id.delete_btn)
        View deleteBtn;
        @BindView(R.id.collect_btn)
        View collectBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
