/**
 * @author AW
 * @date 20 May 2020
 *
 * Android app example based on Model-View-ViewModel design pattern.
 * App contains simple dynamically generated list of measurements acquired
 * from Raspberry Pi Sense Hat board.
 *
 * Example is based on:
 * https://www.journaldev.com/20292/android-mvvm-design-pattern
 * https://medium.com/upday-devs/android-architecture-patterns-part-3-model-view-viewmodel-e7eeee76b73b
 * https://medium.com/@ali.muzaffar/android-recyclerview-using-mvvm-and-databinding-d9c659236908
 * https://medium.com/@sanjeevy133/an-idiots-guide-to-android-recyclerview-and-databinding-4ebf8db0daff
 *
 */
package iot.examples.mvvmrecyclerview.view;

import android.os.Bundle;

// VIEW MODELS
import iot.examples.mvvmrecyclerview.viewmodel.MainViewModel;
import iot.examples.mvvmrecyclerview.viewmodel.MainViewModelMock;

// Automatically generated class
import iot.examples.mvvmrecyclerview.databinding.ActivityMainBinding;

import iot.examples.mvvmrecyclerview.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    // Since our layout file is activity_main.xml,
    // our auto generated binding class is ActivityMainBinding.
    //
    // "underscores" to "Pascal case"
    private ActivityMainBinding binding;

    // Main view model (can be bind with other activities)
    //private MainViewModelMock viewModel;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create new view model provider with MainViewModel class
        //viewModel = new ViewModelProvider(this).get(MainViewModelMock.class);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        if (savedInstanceState == null)
            viewModel.Init(this); // Initialize only if activity instance state is empty

        // Data binding utility
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // Binding data context of activity_main: setting view_model variable
        binding.setViewModel(viewModel);
    }
}
