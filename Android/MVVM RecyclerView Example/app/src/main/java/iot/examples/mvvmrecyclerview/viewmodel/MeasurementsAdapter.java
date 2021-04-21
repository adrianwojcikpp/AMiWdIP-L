package iot.examples.mvvmrecyclerview.viewmodel;

import iot.examples.mvvmrecyclerview.databinding.MeasurementViewBinding; // Automatically generated class

import android.view.LayoutInflater;
import android.view.ViewGroup;

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
        private MeasurementViewBinding binding;

        /**
         * @brief MeasurementViewHolder parametric constructor.
         * @param binding Data binding for single 'recyclerview' item.
         */
        public MeasurementViewHolder(MeasurementViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * @brief Binds measurement view model with 'recyclerview' item.
         * @param measurement View model for single measurement item.
         */
        public void bind(MeasurementViewModel measurement) {
            binding.setMeasurementViewModel(measurement);
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
        MeasurementViewBinding itemBinding = MeasurementViewBinding.inflate(layoutInflater, parent, false);
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

