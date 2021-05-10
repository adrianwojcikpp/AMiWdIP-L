/**
 ******************************************************************************
 * @file    LED Display Control Example/ViewModel/ButtonCommand.cs
 * @author  Adrian Wojcik  adrian.wojcik@put.poznan.pl
 * @version V2.0
 * @date    10-May-2021
 * @brief   LED display controller: button command implementation
 ******************************************************************************
 */

using System;
using System.Windows.Input;

namespace LedDisplayExample.ViewModel
{
    public class ButtonCommand : ICommand
    {
        /// Delegate command to register method to be executed
        private readonly Action handler;
        private bool isEnabled;

        /// Bind method to be executed to the handler
        /// So that it can direct on event execution
        public ButtonCommand(Action handler)
        {
            // Assign the method name to the handler
            this.handler = handler;

            // By default the button is enabled
            this.isEnabled = true;
        }

        public bool IsEnabled
        {
            get { return isEnabled; }
            set
            {
                if (value != isEnabled)
                {
                    isEnabled = value;
                    CanExecuteChanged?.Invoke(this, EventArgs.Empty);
                }
            }
        }

        public bool CanExecute(object parameter)
        {
            return IsEnabled;
        }

        public event EventHandler CanExecuteChanged;

        // Helps to execute the respective method using the handler
        public void Execute(object parameter)
        {
            //calls the respective method that has been registered with the handler
            handler();
        }
    }
}
