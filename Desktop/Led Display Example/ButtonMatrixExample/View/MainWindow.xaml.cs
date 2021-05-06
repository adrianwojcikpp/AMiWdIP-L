using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ButtonMatrixExample.View
{
    using ViewModel;

    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private readonly int ledMatrixWidth = 8;
        private readonly int ledMatrixHeigth = 8;

        private readonly MainViewModel viewmodel;

        public MainWindow()
        {
            InitializeComponent();

            viewmodel = new MainViewModel(SetButtonColor);
            DataContext = viewmodel;

            /* Button matrix grid definition */
            for (int i = 0; i < ledMatrixWidth; i++)
            {
                ButtonMatrixGrid.ColumnDefinitions.Add(new ColumnDefinition());
                ButtonMatrixGrid.ColumnDefinitions[i].Width = new GridLength(1, GridUnitType.Star);
            }

            for (int i = 0; i < ledMatrixHeigth; i++)
            {
                ButtonMatrixGrid.RowDefinitions.Add(new RowDefinition());
                ButtonMatrixGrid.RowDefinitions[i].Height = new GridLength(1, GridUnitType.Star);
            }

            for (int i = 0; i < ledMatrixWidth; i++)
            {
                for (int j = 0; j < ledMatrixHeigth; j++)
                {
                    // <Button
                    Button led = new Button()
                    {
                        // Name = "BMij"
                        Name = viewmodel.LedIndexToTag(i,j),
                        // CommandParameter = "BMij"
                        CommandParameter = viewmodel.LedIndexToTag(i,j),
                        // Style="{StaticResource LedButtonStyle}"
                        Style = (Style)FindResource("LedButtonStyle")
                    };
                    // Command="{Binding CommonButtonCommand}" 
                    led.SetBinding(Button.CommandProperty, new Binding("CommonButtonCommand"));
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

        private void SetButtonColor(String name, Color color)
        {
             (ButtonMatrixGrid.FindName(name) as Button).Background = new SolidColorBrush(color);
        }
    }
}
