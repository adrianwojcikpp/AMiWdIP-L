/**
 ******************************************************************************
 * @file    LED Display Control Example/View/MainWindow.cs
 * @author  Adrian Wojcik  adrian.wojcik@put.poznan.pl
 * @version V2.0
 * @date    10-May-2021
 * @brief   LED display controller: Main window View
 ******************************************************************************
 */

using System.Windows;
using System.Windows.Controls;
using System.Windows.Media;

namespace LedDisplayExample.View
{
    using ViewModel;
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private readonly MainWindowViewModel _viewModel;
        public MainWindow()
        {
            InitializeComponent();

            _viewModel = new MainWindowViewModel();
            DataContext = _viewModel;

            /* Button matrix grid definition */
            for (int i = 0; i < _viewModel.DisplaySizeX; i++)
            {
                ButtonMatrixGrid.ColumnDefinitions.Add(new ColumnDefinition());
                ButtonMatrixGrid.ColumnDefinitions[i].Width = new GridLength(1, GridUnitType.Star);
            }

            for (int i = 0; i < _viewModel.DisplaySizeY; i++)
            {
                ButtonMatrixGrid.RowDefinitions.Add(new RowDefinition());
                ButtonMatrixGrid.RowDefinitions[i].Height = new GridLength(1, GridUnitType.Star);
            }

            for (int i = 0; i < _viewModel.DisplaySizeX; i++)
            {
                for (int j = 0; j < _viewModel.DisplaySizeY; j++)
                {
                    // <Button
                    Button led = new Button()
                    {
                        // Name = "LEDij"
                        Name = "LED" + i.ToString() + j.ToString(),
                        // Style="{StaticResource LedButtonStyle}"
                        Style = (Style)FindResource("LedIndicatorStyle"),
                        // Bacground="{StaticResource ... }"
                        Background = new SolidColorBrush(Color.FromRgb(0xb5, 0xb5, 0xb5)),
                        // BorderThicness="2"
                        BorderThickness = new Thickness(2),
                    };
                    // Command="{Binding LedCommands[i][j]}" 
                    led.SetBinding(Button.CommandProperty, _viewModel.GetCommandBinding(i,j));
                    // Color="{Binding Leds[i][j].ViewColor}" 
                    led.SetBinding(Button.BackgroundProperty, _viewModel.GetColordBinding(i, j));
                    // Grid.Column="i" 
                    Grid.SetColumn(led, i);
                    // Grid.Row="j"
                    Grid.SetRow(led, j);
                    // />

                    ButtonMatrixGrid.Children.Add(led);
                    ButtonMatrixGrid.RegisterName(led.Name, led);
                }
            }
        }
    }
}
