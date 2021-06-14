/**
 ******************************************************************************
 * @file    /gpio_examples/cpp/gpio_output_example.cpp
 * @author  Adrian Wojcik
 * @version V1.0
 * @date    15-Mar-2020
 * @brief   Raspberry Pi digital output control: C++ with wiringPi lib
 ******************************************************************************
 */

#include <iostream>
#include <future>
#include <thread>
#include <chrono>
#include <wiringPi.h>

int main()
{
	const int led = 28; //< Red LED: Physical pin 38, BCM GPIO20, and WiringPi pin 28.
	
	std::chrono::milliseconds timeout(100);
	std::future<int> async_getchar = std::async(std::getchar);

	wiringPiSetup();

	pinMode(led, OUTPUT);
	
	std::cout << "Press ENTER to exit." << std::endl;
	
	while(1) 
	{	
		digitalWrite(led, HIGH);
		
		std::this_thread::sleep_for(timeout);

		digitalWrite(led, LOW);

		if(async_getchar.wait_for(timeout) == std::future_status::ready)
		{
			async_getchar.get();
			break;
		}
	}
	
	return 0;
}