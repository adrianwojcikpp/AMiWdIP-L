   M S B A M L   `   `   `  ����M  IDataGrabberExample, Version=1.0.0.0, Culture=neutral, PublicKeyToken=nullK*clr-namespace:DataGrabberExample.ViewModelDataGrabberExample.ViewModel  5   	   (    "DataGrabberExample.View.MainWindow   5   	   R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35U QWPFToolkit, Version=3.5.40128.1, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   R NOxyPlot.Wpf, Version=2.0.0.0, Culture=neutral, PublicKeyToken=75e952ba404cdbb0 oxyhttp://oxyplot.org/wpf  5   	   3vm*clr-namespace:DataGrabberExample.ViewModel  5   	     � Title$!  Simple desktop data grabber��5	   	   $	��450��6-   $	��900��6:    � DataContext 5
      0   *DataGrabberExample.ViewModel.MainViewModel  5   
   .��5      �  � RowDefinitions 5   
   �� 5      $
��Auto �6   �� 5      $��* �6   �� 5      $
��Auto �6   �� 5      $
��Auto �6    � ColumnDefinitions 5   
   �� 5      $
��Auto �6   �� 5      $��* �6   .+�5   
   �� -   5         ��Name$ MenuBtn��5      $��0��5      $��1��6   $��62��5      $
��Left=�6   $
��15,5q�63   .��6A   Menu5      � 5    
   -   5         �Name$
 Menu��5!      $��0��5       $��0��6   $��4��6,   
����� �$	��150��5"   %    5#      �� 5$      $
��Auto �6    �� 5%      $
��Auto �6    �� 5&      $
��Auto �6    .+�5(      �� $��0��6   $	��100��6"   $��5q�6.   {�.  .��6K   CHART6R   �� 5)      $��1��6   $	��100��6"   $��5q�6.   {�.  .��6K   	DISPLAY6T   5*        OxyPlot.Wpf.PlotView  5,   
   -   5          Name$ DataPlotView��5,      $��1��5-      $��1��6$   $��0,0,10,0q�5.         Model 5/      �� *DataPlotModel+� 52   
   $��2��6   $��1��6    53      �� 54      $
��Auto �6#   �� 55      $��* �6#   �� 56      $
��Auto �6#   �� 57      $
��Auto �6#   �� 58      $
��Auto �6#   .+�5:      �� $��0��5;      $��
20,10,0,10q�5<      $��IP address: ��5=      $��Center=�6%   �� 5>      $��1��5?      $��	0,10,0,10q�5@      $	��100��5A      $
��Left=�5B      ��5C      �� *	IpAddress+�� 5D      $��2��5E      $��	0,10,0,10q�5F      $��Sample time: ��5G      $��Right=�5H      $��Center=�6-   �� 5I      $��3��5J      $��	0,10,0,10q�5K      $��50��5L      $
��Left=�5M      ��5N      �� *
SampleTime+�� 5O      $��4��5P      $��
0,10,20,10q�5Q      $	�� ms��5R      $��Right=�5S      $��Center=�6-   5T      � 5V   
   $��3��6   $��1��6    5W      �� 5X      $��* �6#   �� 5Y      $
��Auto �6#   �� 5Z      $
��Auto �6#   �� 5[      $��* �6#   .+�5]      �� $��0��5^      $��30��5_      $	��150��5`      $��
0,10,30,10q�5a      $��START��5c      $��Right=�5d      ��5b      �� *StartButton+.��5d   -   START5f      �� 5g      $��1��5h      $��30��5i      $	��150��5j      $��
0,10,15,10q�5k      ��5l      �� *
StopButton+.��60   STOP5n      �� 5o      $��2��5p      $��30��5q      $	��150��5r      $��
15,10,0,10q�5s      $
��Left=�5t   8   ��6   �� *UpdateConfigButton+.��6S   UPDATE CONFIG5v      �� 5w      $��3��5x      $��30��5y      $	��150��5z      $��
30,10,0,10q�5{      $
��Left=�5|   9   ��6   �� *DefaultConfigButton+.��6T   DEFAULT CONFIG5~      5      5�      5�      