/**
 ******************************************************************************
 * @file    /pwm_examples/cpp/pwm_example.cpp
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
	const int range = 200;
	const int clock = 192;
	int step = 1;

	std::chrono::milliseconds timeout(1000);
	std::chrono::milliseconds delay(10);
	std::future<int> async_getchar = std::async(std::getchar);

	wiringPiSetup();

	pinMode(pwmPin, PWM_OUTPUT);
	
	pwmSetMode(PWM_MODE_MS);
	pwmSetRange(range);
	pwmSetClock(clock);
	
	pwmWrite(pwmPin, 0) ;
	
	std::cout << "Press ENTER to exit." << std::endl;
	
	while(1) 
	{	
		if(async_getchar.wait_for(timeout) == std::future_status::ready)
		{
			async_getchar.get();		
			break;
		}	

		for(int d = 0 ; d <= range ; d+=step)
		{
			pwmWrite(pwmPin, d) ;
			std::this_thread::sleep_for(delay);	
		}

		for(int d = range ; d >= 0 ; d-=step)
		{
			pwmWrite(pwmPin, d) ;
			std::this_thread::sleep_for(delay);	;
		}
	}
    
	return 0;
}