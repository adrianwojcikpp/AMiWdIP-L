using System;

namespace MultiViewApp.ViewModel
{
    public class View1_ViewModel : BaseViewModel
    {
        public Action CodeBehindHandler { private get; set; }
        public ButtonCommand ButtonCommand { get; set; }

        public View1_ViewModel()
        {
            ButtonCommand = new ButtonCommand(ButtonClickHandler);
        }

        public void ButtonClickHandler()
        {
            CodeBehindHandler();
        }
    }
}