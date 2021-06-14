#!/bin/bash
#**
#******************************************************************************
#* @file    /pwm_examples/bash/pwm_example.sh
#* @author  Adrian Wojcik
#* @version V1.0
#* @date    15-Mar-2020
#* @brief   Raspberry Pi PWM control: bash with gpio app
#******************************************************************************

gpio -g mode 18 pwm
gpio pwm-ms
# pwmFrequency in Hz = 19 200 000 Hz / pwmClockDiv / pwmCounter.
gpio pwmc 192    # pwmClockDiv
gpio pwmr 200   # pwmCounter

gpio -g pwm 18 0

echo "Press any key to continue"

while [ true ] ; 
do
	read -t 1 -n 1
	if [ $? = 0 ] ; then
		break ;
	else

		for i in $(seq 0 1 200)
		do
			gpio -g pwm 18 ${i}
		done
	
		for i in $(seq 200 -1 0)
		do
			gpio -g pwm 18 ${i}
		done
		
	fi
done

gpio -g mode 18 out
gpio unexport 18
