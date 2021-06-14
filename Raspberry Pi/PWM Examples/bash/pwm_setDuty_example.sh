#!/bin/bash
#**
#******************************************************************************
#* @file    /pwm_examples/bash/pwm_setDuty_example.sh
#* @author  Adrian Wojcik
#* @version V1.0
#* @date    15-Mar-2020
#* @brief   Raspberry Pi PWM control: bash with gpio app
#******************************************************************************

gpio -g mode 18 pwm
gpio pwm-ms
# pwmFrequency in Hz = 19 200 000 Hz / pwmClockDiv / pwmCounter.
gpio pwmc 192   # pwmClockDiv
gpio pwmr 200   # pwmCounter

if [ $# -eq 1 ] ; then
	pwm_duty=$(($1 * 2))
	gpio -g pwm 18 $pwm_duty
else
	gpio -g pwm 18 100
fi

echo "Press any key to continue"

while [ true ] ; 
do
	read -t 1 -n 1
	if [ $? = 0 ] ; then
		break ;
	fi
done

gpio -g mode 18 out
gpio unexport 18
