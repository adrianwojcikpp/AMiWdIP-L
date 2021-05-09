using System.Windows;
using System.Windows.Controls;
using System.Windows.Media;

namespace MultiViewApp.View
{
    using ViewModel;
    /// <summary>
    /// Interaction logic for View1.xaml
    /// </summary>
    public partial class View1 : UserControl
    {
        private int color = 0;
        public View1()
        {
            InitializeComponent();
            DataContextChanged += new DependencyPropertyChangedEventHandler(DataContextChangedHandler);
        }

        /**
         * @brief Setting up ViewModel properties & handlers
         */
        private void DataContextChangedHandler(object sender, DependencyPropertyChangedEventArgs e)
        {
            var viewmodel = (DataContext as View1_ViewModel);
            if (viewmodel != null)
            {
                // Properties 
                /* ... */
                // Handlers
                viewmodel.CodeBehindHandler = ChangeTextColor;
            }
        }

        private void ChangeTextColor()
        {
            color = (color >= 2 ? 0 : color + 1);

            switch(color)
            {
                case 0:
                    txt1.Foreground = new SolidColorBrush(Color.FromRgb(0xff,0x00,0x00));
                    break;
                case 1:
                    txt1.Foreground = new SolidColorBrush(Color.FromRgb(0x00, 0xff, 0x00));
                    break;
                case 2:
                    txt1.Foreground = new SolidColorBrush(Color.FromRgb(0x00, 0x00, 0xff));
                    break;
            }
        }
    }
}
