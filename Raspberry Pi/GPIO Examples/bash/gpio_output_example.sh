#!/bin/bash
#**
#******************************************************************************
#* @file    /gpio_examples/bash/gpio_output_example.sh
#* @author  Adrian Wojcik
#* @version V1.0
#* @date    15-Mar-2020
#* @brief   Raspberry Pi digital output control: bash script
#******************************************************************************

sh ./gpio_output_init.sh
echo "Press any key to exit."
GPIO_STATE=0
while [ true ] ;
do
read -t .5 -n 1
	if [ $? = 0 ] ; 
	then
		sh ./gpio_output_deinit.sh
		exit ;
	else
		if (( GPIO_STATE == 0 )); then
			GPIO_STATE=1
			sh ./gpio_output_set.sh
		else
			GPIO_STATE=0
			sh ./gpio_output_reset.sh
		fi
	fi
done