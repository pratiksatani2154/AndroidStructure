package com.theone.demopermission.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

public abstract class BindingRecyclerAdapter<T, U extends ViewDataBinding>
        extends RecyclerView.Adapter<BindingRecyclerAdapter<T, U>.MyViewHolder> implements Filterable {

    private int layoutID;

    /**
     * @param layoutID item layout ID
     */
    public BindingRecyclerAdapter(@LayoutRes int layoutID) {
        this.layoutID = layoutID;
    }

    public BindingRecyclerAdapter<T, U>.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                        int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        U binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new BindingRecyclerAdapter<T, U>.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingRecyclerAdapter<T, U>.MyViewHolder holder,
                                 int position) {
        final T item = getItemForPosition(position);
        holder.bind(item);
        setView(holder.binding, position);
    }
    /**
     * @param position of current item
     * @return pojo model for current item
     * e.g. list.get(position)
     */
    protected abstract T getItemForPosition(int position);

    /**
     * extra method to perform extra operations on list items
     */
    protected abstract void setView(U binding, int position);


    protected abstract Filter filterList();
    @Override
    public Filter getFilter() {
        return filterList();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutID;
    }

    /**
     * @return give Binding variable name here
     * e.g. if variable name is vm then give BR.vm
     * import com.android.databinding.library.baseAdapters.BR
     */
    public abstract int getBindingVariableID();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private U binding;

        public MyViewHolder(U binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(T item) {
            binding.setVariable(getBindingVariableID(), item);
            binding.executePendingBindings();
        }

        public U getBinding() {
            return binding;
        }
    }
}
