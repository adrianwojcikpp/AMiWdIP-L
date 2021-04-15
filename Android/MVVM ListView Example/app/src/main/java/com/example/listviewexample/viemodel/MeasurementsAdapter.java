package com.example.listviewexample.viemodel;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.listviewexample.databinding.ItemMeasurementDataBinding; // Automatically generated class

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MeasurementsAdapter extends
        RecyclerView.Adapter<MeasurementsAdapter.MeasurementViewHolder> {

    /**
     * @brief Provide a direct reference to each of the views within a data item.
     *        Used to cache the views within the item layout for fast access.
     */
    public class MeasurementViewHolder extends RecyclerView.ViewHolder {

        // Since our layout file is item_measurement_data.xml,
        // our auto generated binding class is ItemMeasurementDataBinding.
        //
        // "underscores" to "Pascal case"
        private ItemMeasurementDataBinding binding;

        /**
         * @brief MeasurementViewHolder parametric constructor.
         * @param binding Data binding for single 'recyclerview' item.
         */
        public MeasurementViewHolder(ItemMeasurementDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * @brief Binds measurement view model with 'recyclerview' item.
         * @param measurement View model for single measurement item.
         */
        public void bind(MeasurementViewModel measurement) {
            binding.setMeasurement(measurement);
            binding.executePendingBindings();
        }
    }

    // Store a member variable for the measurements
    private List<MeasurementViewModel> mMeasurements;

    /**
     * @brief MeasurementsAdapter parametric constructor.
     * @param measurements Measurements view models array.
     */
    public MeasurementsAdapter(List<MeasurementViewModel> measurements) {
        mMeasurements = measurements;
    }

    @NonNull
    @Override
    public MeasurementsAdapter.MeasurementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMeasurementDataBinding itemBinding = ItemMeasurementDataBinding.inflate(layoutInflater, parent, false);
        return new MeasurementViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MeasurementViewHolder holder, int position) {
        MeasurementViewModel measurement = mMeasurements.get(position);
        holder.bind(measurement);
    }

    @Override
    public int getItemCount() {
        return mMeasurements != null ? mMeasurements.size() : 0;
    }
}
