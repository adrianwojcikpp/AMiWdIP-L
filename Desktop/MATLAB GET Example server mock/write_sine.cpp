/**
 ******************************************************************************
 * @file    MATLAB GET Example sever mock/write_sine.cpp
 * @author  Adrian Wojcik
 * @version V1.2
 * @date    09-May-2020
 * @brief   Simple MATLAB web client example: mock CLI app 
 ******************************************************************************
 */

#include <iostream>
#include <fstream>
#include <iomanip>
#include <future>
#include <thread>
#include <chrono> 
#define _USE_MATH_DEFINES
#include <math.h>

#define FILE_NAME "sine.dat"

int main() 
{
	/* Sine wave params */
	const double f = 0.1;	// [Hz]
	double t = 0.5;			// [s]
	const double ts = 0.01;	// [s]
	double s = 0;			// [p.u.]
	
	/* Open text file and configure decimal precision */
	const int WRITE_POS = 0; 
	const int DECIMAL_PRECISION = 4;
	std::ofstream myfile;
	myfile.open (FILE_NAME, std::ios::out | std::ios::trunc);
	myfile << std::fixed << std::setprecision(DECIMAL_PRECISION);
	
	/* Create async getchar procedure for delay with stdin check */
	std::chrono::milliseconds timeout((int)(ts * 1000.0));
	std::future<int> async_getchar = std::async(std::getchar);
	
	std::cout << "Press ENTER to exit." << std::endl;
	
	while(1)
	{
		// compute signal value
		//t = (t > 1/f) ? (0.0) : (t+ts);
		t += ts;
		s = sin(2*M_PI*f*t);
		
		// save to file
		myfile << "{\"t\":" << t << ",\"s\":" << s << "}\n";
		myfile.seekp(WRITE_POS, std::ios::beg);
		
		// std::cout <<  "{\"t\":" << t << ",\"s\":" << s << "}\n";

		// wait for 10 ms
		if(async_getchar.wait_for(timeout) == std::future_status::ready)
		{
			async_getchar.get();
			break;
		}	
	}
	
	myfile.close();
	return 0;
}