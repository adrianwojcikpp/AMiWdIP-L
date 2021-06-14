/**
 ******************************************************************************
 * @file    /pwm_examples/cpp/pwm_setDuty_example.cpp
 * @author  Adrian Wojcik
 * @version V1.0
 * @date    15-Mar-2020
 * @brief   Raspberry Pi PWM control: C++ with wiringPi lib
 ******************************************************************************
 */

#include <iostream>
#include <future>
#include <thread>
#include <chrono>
#include <wiringPi.h>

int main(int argc, char *argv[]) 
{
	const int pwmPin = 1; //< Red LED: Physical pin 12, BCM GPIO18, and WiringPi pin 1.
	float duty = 0;
	const int range = 200;
	const int clock = 192;
	
	sscanf(argv[1],"%f",&duty); // C-style
	duty *= (float)range;
	duty /= 100.0;
	
	std::chrono::milliseconds timeout(100);
	std::future<int> async_getchar = std::async(std::getchar);

	wiringPiSetup();

	pinMode(pwmPin, PWM_OUTPUT);
	
	pwmSetMode(PWM_MODE_MS);
	pwmSetRange(range);
	pwmSetClock(clock);
	
	pwmWrite(pwmPin, (int)duty) ;
	
	std::cout << "Press ENTER to exit." << std::endl;
		
	while(1) 
	{	
		if(async_getchar.wait_for(timeout) == std::future_status::ready)
		{
			async_getchar.get();		
			break;
		}			
	}
    
	return 0;
}