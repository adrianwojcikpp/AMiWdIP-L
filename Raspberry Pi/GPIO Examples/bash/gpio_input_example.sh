#!/bin/bash
#**
#******************************************************************************
#* @file    /gpio_examples/bash/gpio_input_example.sh
#* @author  Adrian Wojcik
#* @version V1.0
#* @date    15-Mar-2020
#* @brief   Raspberry Pi digital input control: bash script
#******************************************************************************

sh ./gpio_input_init.sh
echo "Press any key to exit."
GPIO_STATE="1"
GPIO_STATE_LAST="1"
CNT=0
while [ true ] ;
do
read -t .1 -n 1
	if [ $? = 0 ] ; 
	then
		sh ./gpio_input_deinit.sh
		exit ;
	else
		GPIO_STATE=$(./gpio_input_read.sh)
		if [[ $GPIO_STATE -eq 0 ]] && [[ $GPIO_STATE_LAST -eq 1 ]]; 
		then
			CNT=$((CNT+1))
			echo "Push-button counter: $CNT"
		fi
		GPIO_STATE_LAST=$((GPIO_STATE))
	fi
done